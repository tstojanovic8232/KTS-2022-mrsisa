package com.Reservations.Servis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.BrzaRezervacijaDTO;
import com.Reservations.DTO.IzvjestajRezervacijaDTO;
import com.Reservations.DTO.KlijentSpisakDTO;
import com.Reservations.DTO.PrihodDTO;
import com.Reservations.DTO.RezervacijaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.GlobalnaVarijabla;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Modeli.enums.TipRezervacije;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;

@Service
public class RezervacijaServis {

	@Autowired
	private RezervacijaRepozitorijum rezervacijaRepozitorijum;
	@Autowired
	private UslugaServis uslugaServis;

	@Autowired
	private KorisnikServis korisnikServis;

	@Autowired
	VikendicaServis vikendicaServis;

	@Autowired
	TerminServis terminServis;

	@Autowired
	BrodServis brodServis;

	@Autowired
	GVarijableServis gvServis;

	public Rezervacija findById(Long id) {
		try {
			return rezervacijaRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Rezervacija save(RezervacijaDTO regRequest, TipEntiteta e, Long id, TipRezervacije tip, Long id2) {

		Rezervacija r = new Rezervacija();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		r.setTip(tip);
		r.setEntitetId(id);
		r.setTipEntiteta(e);
		r.setAkcija(1);
		r.setKlijent(korisnikServis.findById(id2));
		if (e.equals(TipEntiteta.vikendica)) {
			Vikendica v = vikendicaServis.findById(id);
			r.setNazivEntiteta(v.getNaziv());
			r.setCena(v.getCena());
			
		}
		if (e.equals(TipEntiteta.brod)) {
			System.out.println("Rezervacija broda: ");
			Brod v = brodServis.findById(id);
			r.setNazivEntiteta(v.getNaziv());
			r.setCena(v.getCena());
			
		}
		if (e.equals(TipEntiteta.usluga)) {
			System.out.println("Rezervacija usluge: ");
			Usluga v = uslugaServis.findById(id);
			r.setNazivEntiteta(v.getNaziv());
			r.setCena(v.getCena());
			
		}
		
		r.setDatum(regRequest.getDatum());
		r.setVreme(regRequest.getVreme());
		r.setTrajanje(regRequest.getTrajanje());
		r.setMaxOsoba(regRequest.getMaxOsoba());
	
		
		return this.rezervacijaRepozitorijum.save(r);// TODO Auto-generated method stub

	}

	public List<Rezervacija> listAll() {
		return this.rezervacijaRepozitorijum.findAll();
	}

	public void delete(long id) {
		this.rezervacijaRepozitorijum.deleteById(id);
	}

	public Rezervacija findByIme(String ime) {
		return this.rezervacijaRepozitorijum.findByNazivEntiteta(ime);

	}

	public List<Rezervacija> findByKlijent(long id, Sort sort) {
		List<Rezervacija> li2 = new ArrayList<Rezervacija>();
		List<Rezervacija> li = rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now();
		for (Rezervacija r : li) {
			if (r.getKlijent().getID() == id) {

				if (LocalDate.parse(r.getDatum(), dtf).isAfter(now))

					li2.add(r);

			}
		}
		return li2;

	}

	public List<Rezervacija> findByKlijentDateBrod(long id) {
		List<Rezervacija> li2 = new ArrayList<Rezervacija>();
		List<Rezervacija> li = rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now();
		for (Rezervacija r : li) {
			if (r.getKlijent().getID() == id) {
				if (LocalDate.parse(r.getDatum(), dtf).isBefore(now))
					if (r.getTipEntiteta().ordinal() == 1)
						li2.add(r);

			}
		}
		return li2;

	}

	public List<Rezervacija> findByKlijentDateVik(long id) {
		List<Rezervacija> li2 = new ArrayList<Rezervacija>();
		List<Rezervacija> li = rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now();
		for (Rezervacija r : li) {
			if (r.getKlijent().getID() == id) {
				if (LocalDate.parse(r.getDatum(), dtf).isBefore(now))
					if (r.getTipEntiteta().ordinal() == 0)
						li2.add(r);

			}
		}
		return li2;
	}

	public List<Rezervacija> findByKlijentDateUsluga(long id) {
		List<Rezervacija> li2 = new ArrayList<Rezervacija>();
		List<Rezervacija> li = rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now();
		for (Rezervacija r : li) {
			if (r.getKlijent().getID() == id) {
				if (LocalDate.parse(r.getDatum(), dtf).isBefore(now))
					if (r.getTipEntiteta().ordinal() == 2)
						li2.add(r);

			}
		}
		return li2;
	}

	public List<Rezervacija> findByKlijentDate(long id) {
		List<Rezervacija> li2 = new ArrayList<Rezervacija>();
		List<Rezervacija> li = rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now();
		for (Rezervacija r : li) {
			if (r.getKlijent().getID() == id) {
				if (LocalDate.parse(r.getDatum(), dtf).isBefore(now))
					li2.add(r);

			}
		}
		return li2;
	}

	public Boolean findByDate(Rezervacija r) {

		LocalDate now = LocalDate.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		String datum = r.getDatum();
		if (now.isBefore(LocalDate.parse(datum, dtf).minusDays(3))) {

			return true;

		}
		return false;

	}

	public List<Rezervacija> nadjiRezervacijeVikendica() {
		return rezervacijaRepozitorijum.findByTipEntiteta(TipEntiteta.vikendica);
	}

	public List<Rezervacija> pronadjiRezervacijePoVlasniku(Korisnik vlasnik, TipEntiteta tipEntiteta) {
		List<Rezervacija> mojeRezervacije = new ArrayList<Rezervacija>();
		System.out.println("TipoviEntiteta jednaki: " + tipEntiteta.equals(TipEntiteta.vikendica));
		if (tipEntiteta.equals(TipEntiteta.vikendica)) {
			List<Rezervacija> rezervacijeVikendica = rezervacijaRepozitorijum.findByTipEntiteta(tipEntiteta);
			List<Vikendica> mojeVikendice = vikendicaServis.nadjiVikendicePoVlasniku(vlasnik);
			for (int rezID = 0; rezID < rezervacijeVikendica.size(); rezID++) {
				for (int vikID = 0; vikID < mojeVikendice.size(); vikID++) {
					// System.out.println("ID iz rezervacije: " +
					// rezervacijeVikendica.get(rezID).getEntitetId());
					// System.out.println("ID iz vikendice:" + mojeVikendice.get(vikID).getID());
					if (rezervacijeVikendica.get(rezID).getTip().equals(TipRezervacije.obicna)
							&& rezervacijeVikendica.get(rezID).getEntitetId() == mojeVikendice.get(vikID).getID()) {

						mojeRezervacije.add(rezervacijeVikendica.get(rezID));
						System.out.println("Ubacen!");
						break;
					}
				}
			}
		} else if (tipEntiteta.equals(TipEntiteta.brod)) {
			List<Rezervacija> rezervacijeBrodova = rezervacijaRepozitorijum.findByTipEntiteta(tipEntiteta);
			List<Brod> mojiBrodovi = brodServis.nadjiBrodovePoVlasniku(vlasnik);
			System.out.println("AAAAA brodovi po vlasniku: " + vlasnik.getKorisnickoIme());
			for (int rezID = 0; rezID < rezervacijeBrodova.size(); rezID++) {
				for (int brodID = 0; brodID < mojiBrodovi.size(); brodID++) {
					// System.out.println("ID rezervacije: "+rezervacijeBrodova.get(rezID).getID());
					// System.out.println("ID iz rezervacije: " +
					// rezervacijeBrodova.get(rezID).getEntitetId());
					// System.out.println("ID iz broda:" + mojiBrodovi.get(brodID).getID());
					if (rezervacijeBrodova.get(rezID).getTip().equals(TipRezervacije.obicna)
							&& rezervacijeBrodova.get(rezID).getEntitetId() == mojiBrodovi.get(brodID).getID()) {

						mojeRezervacije.add(rezervacijeBrodova.get(rezID));
						System.out.println("Ubacen!");
						break;
					}
				}
			}
		} else if (tipEntiteta.equals(TipEntiteta.usluga)) {
			List<Rezervacija> rezervacijeUsluga = rezervacijaRepozitorijum.findByTipEntiteta(tipEntiteta);
			List<Usluga> mojeUsluge = uslugaServis.findByInstruktor(vlasnik.getID());
			for (int rezID = 0; rezID < rezervacijeUsluga.size(); rezID++) {
				for (int brodID = 0; brodID < mojeUsluge.size(); brodID++) {
					// System.out.println("ID iz rezervacije: " +
					// rezervacijeUsluga.get(rezID).getEntitetId());
					// System.out.println("ID iz usluge:" + mojeUsluge.get(brodID).getID());
					if (rezervacijeUsluga.get(rezID).getTip().equals(TipRezervacije.obicna)
							&& rezervacijeUsluga.get(rezID).getEntitetId() == mojeUsluge.get(brodID).getID()) {

						mojeRezervacije.add(rezervacijeUsluga.get(rezID));
						System.out.println("Ubacen!");
						break;
					}
				}
			}
		} else
			System.out.println("Not Implemented");
		return mojeRezervacije;
	}

	public List<Rezervacija> pronadjiBrzeRezervacijePoVlasniku(Korisnik vlasnik, TipEntiteta tipEntiteta) {
		List<Rezervacija> mojeRezervacije = new ArrayList<Rezervacija>();
		System.out.println("TipoviEntiteta jednaki: " + tipEntiteta.equals(TipEntiteta.vikendica));
		if (tipEntiteta.equals(TipEntiteta.vikendica)) {
			List<Rezervacija> rezervacijeVikendica = rezervacijaRepozitorijum.findByTipEntiteta(tipEntiteta);
			List<Vikendica> mojeVikendice = vikendicaServis.nadjiVikendicePoVlasniku(vlasnik);
			for (int rezID = 0; rezID < rezervacijeVikendica.size(); rezID++) {
				for (int vikID = 0; vikID < mojeVikendice.size(); vikID++) {
					// System.out.println("ID iz rezervacije: " +
					// rezervacijeVikendica.get(rezID).getEntitetId());
					// System.out.println("ID iz vikendice:" + mojeVikendice.get(vikID).getID());
					if (rezervacijeVikendica.get(rezID).getTip().equals(TipRezervacije.brza)
							&& rezervacijeVikendica.get(rezID).getEntitetId() == mojeVikendice.get(vikID).getID()) {

						mojeRezervacije.add(rezervacijeVikendica.get(rezID));
						System.out.println("Ubacen!");
						break;
					}
				}
			}
		} else if (tipEntiteta.equals(TipEntiteta.brod)) {
			List<Rezervacija> rezervacijeBrodova = rezervacijaRepozitorijum.findByTipEntiteta(tipEntiteta);
			List<Brod> mojiBrodovi = brodServis.nadjiBrodovePoVlasniku(vlasnik);
			System.out.println("AAAAA brodovi po vlasniku: " + vlasnik.getKorisnickoIme());
			for (int rezID = 0; rezID < rezervacijeBrodova.size(); rezID++) {
				for (int brodID = 0; brodID < mojiBrodovi.size(); brodID++) {
					// System.out.println("ID rezervacije: "+rezervacijeBrodova.get(rezID).getID());
					// System.out.println("ID iz rezervacije: " +
					// rezervacijeBrodova.get(rezID).getEntitetId());
					// System.out.println("ID iz broda:" + mojiBrodovi.get(brodID).getID());
					if (rezervacijeBrodova.get(rezID).getTip().equals(TipRezervacije.brza)
							&& rezervacijeBrodova.get(rezID).getEntitetId() == mojiBrodovi.get(brodID).getID()) {

						mojeRezervacije.add(rezervacijeBrodova.get(rezID));
						System.out.println("Ubacen!");
						break;
					}
				}
			}
		} else if (tipEntiteta.equals(TipEntiteta.usluga)) {
			List<Rezervacija> rezervacijeUsluga = rezervacijaRepozitorijum.findByTipEntiteta(tipEntiteta);
			List<Usluga> mojeUsluge = uslugaServis.findByInstruktor(vlasnik.getID());
			for (int rezID = 0; rezID < rezervacijeUsluga.size(); rezID++) {
				for (int brodID = 0; brodID < mojeUsluge.size(); brodID++) {
					// System.out.println("ID iz rezervacije: " +
					// rezervacijeUsluga.get(rezID).getEntitetId());
					// System.out.println("ID iz usluge:" + mojeUsluge.get(brodID).getID());
					if (rezervacijeUsluga.get(rezID).getTip().equals(TipRezervacije.brza)
							&& rezervacijeUsluga.get(rezID).getEntitetId() == mojeUsluge.get(brodID).getID()) {

						mojeRezervacije.add(rezervacijeUsluga.get(rezID));
						System.out.println("Ubacen!");
						break;
					}
				}
			}
		} else
			System.out.println("Not Implemented");
		return mojeRezervacije;
	}

	public List<Rezervacija> findByVlasnikInst(long id, boolean before) {
		List<Rezervacija> lista = rezervacijaRepozitorijum.findAll();
		List<Rezervacija> rez = new ArrayList<Rezervacija>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate now = LocalDate.now();
		Vikendica v = new Vikendica();
		Brod b = new Brod();
		Usluga u = new Usluga();
		for (Rezervacija r : lista) {
			if (r.getTipEntiteta().equals(TipEntiteta.vikendica)) {
				v = vikendicaServis.findById(r.getEntitetId());
				if (v.getVlasnik().getID() == id) {
					if (LocalDate.parse(r.getDatum(), dtf).isBefore(now) && before)
						rez.add(r);
					else if (LocalDate.parse(r.getDatum(), dtf).isAfter(now) && !before)
						rez.add(r);
				}
			} else if (r.getTipEntiteta().equals(TipEntiteta.brod)) {
				b = brodServis.findById(r.getEntitetId());
				if (b.getVlasnik().getID() == id) {
					if (LocalDate.parse(r.getDatum(), dtf).isBefore(now) && before)
						rez.add(r);
					else if (LocalDate.parse(r.getDatum(), dtf).isAfter(now) && !before)
						rez.add(r);
				}
			} else if (r.getTipEntiteta().equals(TipEntiteta.usluga)) {
				u = uslugaServis.findById(r.getEntitetId());
				if (u.getInstruktor().getID() == id) {
					if (LocalDate.parse(r.getDatum(), dtf).isBefore(now) && before)
						rez.add(r);
					else if (LocalDate.parse(r.getDatum(), dtf).isAfter(now) && !before)
						rez.add(r);
				}
			}

		}
		return rez;
	}

	public List<KlijentSpisakDTO> nadjiKlijenteVlasnikaVikendice(Korisnik vlasnik) {

		List<Rezervacija> mojeRezervacije = this.pronadjiRezervacijePoVlasniku(vlasnik, TipEntiteta.vikendica);
		List<Korisnik> korisnici = korisnikServis.listAll();
		List<KlijentSpisakDTO> mojiKlijenti = new ArrayList<KlijentSpisakDTO>();

		Long brojRezervacija;
		for (int i = 0; i < korisnici.size(); i++) {
			brojRezervacija = 0L;
			for (Rezervacija rezervacija : mojeRezervacije) {
				if (rezervacija.getKlijent().equals(korisnici.get(i))) {
					// System.out.println("Pronadjen klijent:
					// "+korisnici.get(i).getKorisnickoIme());
					brojRezervacija++;
				}
			}
			if (brojRezervacija > 0) {
				// System.out.println("Ubacen "+korisnici.get(i).getKorisnickoIme()+" u
				// listu!");
				mojiKlijenti.add(new KlijentSpisakDTO(korisnici.get(i), brojRezervacija));
			}
		}
		return mojiKlijenti;

	}

	public List<KlijentSpisakDTO> nadjiKlijenteVlasnikaBroda(Korisnik vlasnik) {

		List<Rezervacija> mojeRezervacije = this.pronadjiRezervacijePoVlasniku(vlasnik, TipEntiteta.brod);

		List<Korisnik> korisnici = korisnikServis.listAll();
		List<KlijentSpisakDTO> mojiKlijenti = new ArrayList<KlijentSpisakDTO>();

		Long brojRezervacija;
		for (int i = 0; i < korisnici.size(); i++) {
			brojRezervacija = 0L;
			for (Rezervacija rezervacija : mojeRezervacije) {
				if (rezervacija.getKlijent().equals(korisnici.get(i))) {
					// System.out.println("Pronadjen klijent:
					// "+korisnici.get(i).getKorisnickoIme());
					brojRezervacija++;
				}
			}
			if (brojRezervacija > 0) {
				// System.out.println("Ubacen "+korisnici.get(i).getKorisnickoIme()+" u
				// listu!");
				mojiKlijenti.add(new KlijentSpisakDTO(korisnici.get(i), brojRezervacija));
			}
		}
		return mojiKlijenti;

	}

	public List<Rezervacija> RezSortCena(List<Rezervacija> user) {
		List<Rezervacija> li = new ArrayList<Rezervacija>(user);
		Collections.sort(li, Comparator.comparing(Rezervacija::getCena));
		return li;

	}

	public List<Rezervacija> RezSortDatum(List<Rezervacija> user) {
		// Collections.sort(user, Rezervacija.getAttribute1Comparator());
		List<Rezervacija> li = new ArrayList<Rezervacija>(user);
		Collections.sort(li, Comparator.comparing(Rezervacija::getDatum));

		return li;

	}

	public List<Rezervacija> RezSortTrajanje(List<Rezervacija> user) {
		List<Rezervacija> li = new ArrayList<Rezervacija>(user);
		Collections.sort(li, Comparator.comparing(Rezervacija::getTrajanje));

		return li;
	}

	public List<IzvjestajRezervacijaDTO> nadjiRezervacijeBezIzvjestaja(TipEntiteta tipEntiteta, Korisnik vlasnik) {
		List<Rezervacija> mojeRezervacije = this.pronadjiRezervacijePoVlasniku(vlasnik, tipEntiteta);
		List<IzvjestajRezervacijaDTO> rezultat = new ArrayList<IzvjestajRezervacijaDTO>();
		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		for (Rezervacija rez : mojeRezervacije) {
			if (rez.getIzvjestaj() == null || rez.getIzvjestaj().trim().equals("")) {
				IzvjestajRezervacijaDTO izvjestaj = new IzvjestajRezervacijaDTO(rez);

				/*
				 * try { Long trajanje = Long.parseLong(rez.getTrajanje()); LocalDate kraj =
				 * LocalDate.parse(rez.getDatum(), dtf).plusDays(trajanje);
				 * System.out.println("Trajanje: "+ trajanje+" kraj: "+ kraj.format(dtf));
				 * izvjestaj.setDatumKraja(kraj.format(dtf) ); } catch(Exception e) {
				 * izvjestaj.setDatumKraja(rez.getDatum()); }
				 */
				rezultat.add(izvjestaj);
			}

		}
		return rezultat;
	}

	public List<IzvjestajRezervacijaDTO> nadjiRezervacijeSaIzvjestajem(TipEntiteta tipEntiteta, Korisnik vlasnik) {
		List<Rezervacija> mojeRezervacije = this.pronadjiRezervacijePoVlasniku(vlasnik, tipEntiteta);
		List<IzvjestajRezervacijaDTO> rezultat = new ArrayList<IzvjestajRezervacijaDTO>();
		// DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		for (Rezervacija rez : mojeRezervacije) {
			if (rez.getIzvjestaj() != null && !rez.getIzvjestaj().trim().equals("")) {
				IzvjestajRezervacijaDTO izvjestaj = new IzvjestajRezervacijaDTO(rez);
				System.out.println("Trajanje: " + Integer.parseInt(rez.getTrajanje()));
				/*
				 * try { Long trajanje = Long.parseLong(rez.getTrajanje()); LocalDate kraj =
				 * LocalDate.parse(rez.getDatum(), dtf).plusDays(trajanje);
				 * System.out.println("Trajanje: "+ trajanje+" kraj: "+ kraj.format(dtf));
				 * izvjestaj.setDatumKraja(kraj.format(dtf) ); } catch(Exception e) {
				 * izvjestaj.setDatumKraja(rez.getDatum()); }
				 */
				rezultat.add(izvjestaj);
			}
		}
		return rezultat;
	}

	public boolean upisiRezervaciju(Rezervacija rezervacija) {
		try {
			System.out.println("Upisujem izvjestaj rezervacije");
			rezervacijaRepozitorijum.save(rezervacija);
			System.out.println("Izvjestaj upisan");
			return true;
		} catch (Exception e) {
			System.out.println("doslo je do greske pri upisu!");
			return false;
		}
	}

	public boolean popraviTermineRezervacija(List<Rezervacija> rezervacije) {
		boolean termini = false;
		for (Rezervacija rez : rezervacije) {
			try {
				termini = terminServis.popraviTerminRezervacije(rez);
			} catch (Exception e) {
				return false;
			}
		}
		return termini;
	}

	public List<String> findByVikendica(Vikendica usluga) {
		List<Rezervacija> li2 = rezervacijaRepozitorijum.findAll();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		List<String> li = new ArrayList<String>();
		for (Rezervacija rezervacija : li2) {
			if (rezervacija.getTipEntiteta().equals(TipEntiteta.vikendica)) {
				li.add(rezervacija.getDatum());
				for (int i = 1; i < Integer.parseInt(rezervacija.getTrajanje()); i++) {
					LocalDate lokal = LocalDate.parse(rezervacija.getDatum(), dtf).plusDays(i);
					li.add(lokal.format(dtf));
				}
			}
		}
		return li;
	}
	public List<String> findByTerminVikendice(Vikendica usluga) {
		List<Rezervacija>li2=this.findByTip(TipEntiteta.vikendica);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
		List<String>li=new ArrayList<String>();
		for (Rezervacija rezervacija : li2) {
			System.out.println(rezervacija.toString());
			for(Termin ter: usluga.getTerminiZauzetosti()) {
			if(rezervacija.getTermin().equals(ter)) {
				li.add(rezervacija.getTermin().getDatumVremePocetak());
				for (int i=1;i<=Integer.parseInt(rezervacija.getTrajanje());i++) {
					LocalDate lokal=LocalDate.parse(rezervacija.getDatum(),dtf).plusDays(i);
					li.add(lokal.format(dtf));
				}
			}}
	
		}
		return li;
	}
	public List<String> findByTerminBroda(Brod usluga) {
		List<Rezervacija>li2=this.findByTip(TipEntiteta.brod);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
		List<String>li=new ArrayList<String>();
		for (Rezervacija rezervacija : li2) {
			System.out.println(rezervacija.toString());
			for(Termin ter: usluga.getTerminiZauzetosti()) {
			if(rezervacija.getTermin().equals(ter)) {
				li.add(rezervacija.getTermin().getDatumVremePocetak());
				for (int i=1;i<=Integer.parseInt(rezervacija.getTrajanje());i++) {
					LocalDate lokal=LocalDate.parse(rezervacija.getDatum(),dtf).plusDays(i);
					li.add(lokal.format(dtf));
				}
			}}
	
		}
		return li;
	}

	public List<String> findByTerminUsluge(Usluga usluga) {
		List<Rezervacija>li2=this.findByTip(TipEntiteta.usluga);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
		List<String>li=new ArrayList<String>();
		for (Rezervacija rezervacija : li2) {
			System.out.println(rezervacija.toString());
			for(Termin ter: usluga.getTerminiZauzetosti()) {
			if(rezervacija.getTermin().equals(ter)) {
				li.add(rezervacija.getTermin().getDatumVremePocetak());
				for (int i=1;i<=Integer.parseInt(rezervacija.getTrajanje());i++) {
					LocalDate lokal=LocalDate.parse(rezervacija.getDatum(),dtf).plusDays(i);
					li.add(lokal.format(dtf));
				}
			}}
	
		}
		return li;
	}
	

	public List<Rezervacija> findByTip(TipEntiteta tip) {
		return this.rezervacijaRepozitorijum.findByTipEntiteta(tip);
	}

	public List<Rezervacija> nadjiPoTipuRezervacije(TipRezervacije tip) {
		return this.rezervacijaRepozitorijum.findByTip(tip);
	}

	public List<PrihodDTO> izracunajPrihodeSvihRezervacija() {
		List<PrihodDTO> prihodi = new ArrayList<PrihodDTO>();
		List<Rezervacija> rezervacije = this.listAll();

		GlobalnaVarijabla gvProcenat = gvServis.findByName("procenat");
		double procenat = Double.parseDouble(gvProcenat.getVrednost());

		for (Rezervacija rezervacija : rezervacije) {
			double vrednost = rezervacija.getCena() * procenat;
			long rezID = rezervacija.getID();
			String pocetni = rezervacija.getDatum();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			LocalDate ld = LocalDate.parse(pocetni, dtf);
			int trajanje = Integer.parseInt(rezervacija.getTrajanje());
			String krajnji = ld.plusDays(trajanje).format(dtf);
			PrihodDTO prihod = new PrihodDTO(vrednost, rezID, krajnji);
			prihodi.add(prihod);
		}

		return prihodi;
	}

	public List<PrihodDTO> izracunajPrihodeSvihRezervacija(String pocetniDatum, String krajnjiDatum) {
		List<PrihodDTO> prihodi = new ArrayList<PrihodDTO>();
		List<Rezervacija> rezervacije = this.listAll();

		GlobalnaVarijabla gvProcenat = gvServis.findByName("procenat");
		double procenat = Double.parseDouble(gvProcenat.getVrednost());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate pocetniDatumLD = LocalDate.parse(pocetniDatum, dtf);
		LocalDate krajnjiDatumLD = LocalDate.parse(krajnjiDatum, dtf);

		for (Rezervacija rezervacija : rezervacije) {
			String pocetni = rezervacija.getDatum();
			LocalDate rez_pocetniDatum = LocalDate.parse(pocetni, dtf);
			int trajanje = Integer.parseInt(rezervacija.getTrajanje());
			LocalDate rez_krajnjiDatum = rez_pocetniDatum.plusDays(trajanje);
			if ((rez_pocetniDatum.isAfter(pocetniDatumLD) || rez_pocetniDatum.isEqual(pocetniDatumLD))
					&& (rez_krajnjiDatum.isBefore(krajnjiDatumLD) || rez_krajnjiDatum.isEqual(krajnjiDatumLD))) {

				String krajnji = rez_krajnjiDatum.format(dtf);
				double vrednost = rezervacija.getCena() * procenat;
				long rezID = rezervacija.getID();
				PrihodDTO prihod = new PrihodDTO(vrednost, rezID, krajnji);
				prihodi.add(prihod);
			} else
				continue;
		}

		return prihodi;
	}


	

	public Rezervacija napraviBrzuRezervaciju(BrzaRezervacijaDTO brza, TipEntiteta tip) {
		Rezervacija r = new Rezervacija();
		r.setDatum(brza.getDatum());
		r.setVreme(brza.getVreme());
		r.setAkcija(brza.getAkcija());
		r.setCena(brza.getCena());
		r.setTipEntiteta(tip);
		r.setTip(TipRezervacije.brza);
		r.setEntitetId(brza.getEntitetId());
		r.setMaxOsoba(brza.getMaxOsoba());
		r.setTrajanje(brza.getTrajanje());
		if(tip.equals(TipEntiteta.vikendica)) {
			Vikendica v = vikendicaServis.findById(brza.getEntitetId());
			r.setNazivEntiteta(v.getNaziv());
		}
		else if(tip.equals(TipEntiteta.brod)) {
			Brod b = brodServis.findById(brza.getEntitetId());
			r.setNazivEntiteta(b.getNaziv());
		}
		else if(tip.equals(TipEntiteta.usluga)) {
			Usluga u = uslugaServis.findById(brza.getEntitetId());
			r.setNazivEntiteta(u.getNaziv());
		}
		
		return this.rezervacijaRepozitorijum.save(r);
	}

}
