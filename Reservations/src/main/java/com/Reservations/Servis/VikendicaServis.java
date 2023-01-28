package com.Reservations.Servis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.PeriodPrikazDTO;
import com.Reservations.DTO.PoslovanjeEntitetaDTO;
import com.Reservations.DTO.VikendicaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Modeli.enums.TipRezervacije;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;
import com.Reservations.Repozitorijumi.VikendicaRepozitorijum;

@Service	
public class VikendicaServis {
	
	@Autowired
	private TerminServis terminServis;
	@Autowired
	private VikendicaRepozitorijum vikendicaRepozitorijum;
	
	@Autowired
	private RezervacijaRepozitorijum rezervacijaRepozitorijum;
	
	@Autowired
	private RezervacijaServis rezervacijaServis;
	
	@Autowired
	private KorisnikServis korisnikServis;
	
	@Autowired
	GVarijableServis globalneVarijable;
	
	public List<Vikendica> listAll(){
		return vikendicaRepozitorijum.findAll();
	}
	

	public void delete(Long id){
		this.vikendicaRepozitorijum.deleteById(id);
	}
	public Vikendica findById(Long id) {
		try {
			return vikendicaRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	public List<Vikendica> findByVlasnik(long id) {
		List<Vikendica> lista = vikendicaRepozitorijum.findAll();
		List<Vikendica> vikendiceVlasnika = new ArrayList<Vikendica>();
		System.out.println("broj vikendica: "+lista.size());
		if(lista!=null)for(Vikendica u : lista) 
		{
			System.out.println("Compare: "+u.getVlasnik().getID()+" - "+id);
			if (u.getVlasnik().getID() == id) vikendiceVlasnika.add(u);
		}
		return vikendiceVlasnika;
	}
	
	
	public String[] dodajVikendicu(VikendicaDTO novaVikendica) {
		String[] poruka = new String[2];
		try
		{
			System.out.println("Dodaj vikendicu servis!");
			Vikendica vikendica = vikendicaRepozitorijum.findByNaziv(novaVikendica.getNaziv());
					//findByNaziv(novaVikendica.getNaziv());
			System.out.println("find by naziv: "+ vikendica);
			if(vikendica==null)
			{
				vikendica = new Vikendica();
				vikendica.setAdresa(novaVikendica.getAdresa());
				vikendica.setBrojKreveta(novaVikendica.getBrojKreveta());
				vikendica.setBrojSoba(novaVikendica.getBrojSoba());
				vikendica.setCena(novaVikendica.getCena());
				vikendica.setLinkSlike(novaVikendica.getLinkSlike());
				vikendica.setNaziv(novaVikendica.getNaziv());
				vikendica.setOpis(novaVikendica.getOpis());
				vikendica.setLinkInterijera(novaVikendica.getLinkInterijera());
				vikendica.setDodatneUsluge(novaVikendica.getDodatneUsluge());
				vikendica.setPravilaPonasanja(novaVikendica.getPravilaPonasanja());
				System.out.println(novaVikendica.getVlasnik());
				Korisnik vlasnik = korisnikServis.findById(novaVikendica.getVlasnik());
				vikendica.setVlasnik(vlasnik);
			/*
				List<Vikendica> vikendice = vikendicaRepozitorijum.findAll();
				Long ID = 0L;
				for(Vikendica vik : vikendice)
				{	
					if(vik.getID()==ID)
					{
						ID++;
					}
				}
				//vikendica.setID(ID);
				 
				 */
				vikendicaRepozitorijum.save(vikendica);
				poruka[0] = "Vikendica je uspjesno dodata!";
				poruka[1] = "success";
				return poruka;
			}
			else 
			{
				poruka[0] = "Vikendica sa tim nazivom već postoji";
				poruka[1] = "duplicate";
				return poruka;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			poruka[0] = "Doslo je do greske pri dodavanju!";
			poruka[1] = "error";
			
		}
		return poruka;
	}
	
	public String[] izmijeniVikendicu(VikendicaDTO novaVikendica, Vikendica staraVikendica) {
		String[] poruka = new String[2];
		List<Rezervacija> rezervacije = rezervacijaRepozitorijum.findByEntitetId(staraVikendica.getID());
		List<Rezervacija> rezervacijeVikendice = new ArrayList<Rezervacija>();
		for(Rezervacija rez : rezervacije)
		{
			System.out.println("ID rezervacije: "+rez.getID());
			System.out.println("Trazi se brod: "+staraVikendica.getID());
			System.out.println("ID entiteta: "+rez.getEntitetId()+" Tip: "+rez.getTipEntiteta() );
			if(staraVikendica.getID()==rez.getEntitetId() && rez.getTipEntiteta().equals(TipEntiteta.vikendica))
			{
				rezervacijeVikendice.add(rez);
			}
				
		}
		if(rezervacijeVikendice==null || rezervacijeVikendice.isEmpty())
		{
			System.out.println("Izmjena vikendice servis!");
			Vikendica vikendicaProvjere = vikendicaRepozitorijum.findByNaziv(novaVikendica.getNaziv());
			if(vikendicaProvjere==null || vikendicaProvjere.getID()==staraVikendica.getID())
			{
				staraVikendica.setAdresa(novaVikendica.getAdresa());
				staraVikendica.setBrojKreveta(novaVikendica.getBrojKreveta());
				staraVikendica.setBrojSoba(novaVikendica.getBrojSoba());
				staraVikendica.setCena(novaVikendica.getCena());
				staraVikendica.setPravilaPonasanja(novaVikendica.getPravilaPonasanja());
				staraVikendica.setDodatneUsluge(novaVikendica.getDodatneUsluge());
				System.out.println("prva slika: "+novaVikendica.getLinkSlike()+"\ndruga slika: "+novaVikendica.getLinkInterijera());
				if(novaVikendica.getLinkSlike()!=null && !novaVikendica.getLinkSlike().trim().equals(""))
				{			
					System.out.println("Prva slika je: "+novaVikendica.getLinkSlike());
					staraVikendica.setLinkSlike(novaVikendica.getLinkSlike());
				}
				if(novaVikendica.getLinkInterijera()!=null && !novaVikendica.getLinkInterijera().trim().equals(""))
				{
					System.out.println("Druga slika je: "+novaVikendica.getLinkInterijera());
					staraVikendica.setLinkInterijera(novaVikendica.getLinkInterijera());
				}
				
				staraVikendica.setNaziv(novaVikendica.getNaziv());
				staraVikendica.setOpis(novaVikendica.getOpis());
				System.out.println(novaVikendica.getVlasnik());
				vikendicaRepozitorijum.save(staraVikendica);
				poruka[0] = "Izmjena vikendice je uspjesna";
				poruka[1] = "success";
			}
			else
			{
				poruka[0] = "Vikendica sa unijetim nazivom već postoji!";
				poruka[1] = "duplicate";
			
			}
		}	
		else
		{	
			poruka[0]= "Doslo je do greske, vikendica je vec rezervisana!";
			poruka[1] = "failure";
		}
		return poruka;
			
	}
	
	public String[] obrisiVikendicu(Long vlasnikID, Long vikendicaID) 
	{
		String[] poruka = new String[2];
		Vikendica staraVikendica = this.findById(vikendicaID);
		List<Rezervacija> rezervacije = rezervacijaRepozitorijum.findByEntitetId(staraVikendica.getID());
		List<Rezervacija> rezervacijeVikendice = new ArrayList<Rezervacija>();
		
		for(Rezervacija rez : rezervacije)
		{
			System.out.println("ID rezervacije: "+rez.getID());
			System.out.println("Trazi se vikendica: "+staraVikendica.getID());
			System.out.println("ID entiteta: "+rez.getEntitetId()+" Tip: "+rez.getTipEntiteta() );
			if(staraVikendica.getID()==rez.getEntitetId() && rez.getTipEntiteta().equals(TipEntiteta.vikendica))
			{
				rezervacijeVikendice.add(rez);
			}
				
		}
		if(rezervacijeVikendice==null || rezervacijeVikendice.isEmpty()){	
			vikendicaRepozitorijum.delete(staraVikendica);
			poruka[0] = "Brisanje vikendice je uspjesno!";
			poruka[1] = "success";
		}
		else 
		{
			poruka[0] = "Došlo je do greške, vikendica je vec rezervisana!";
			poruka[1] = "reserved";
		}
		return poruka;
	}
	
	public List<Vikendica> nadjiVikendicePoVlasniku(Korisnik vlasnik) 
	{
		List<Vikendica> vikendice = vikendicaRepozitorijum.findAll();
		List<Vikendica> mojeVikendice = new ArrayList<Vikendica>();
		for(Vikendica vikendica : vikendice)
		{
			
			//System.out.println("Naziv: "+ vikendica.getNaziv());
			//System.out.println("Vlasnik: "+vikendica.getVlasnik().getKorisnickoIme());
			//System.out.println(" Trazim: "+vlasnik.getKorisnickoIme());
			if(vikendica.getVlasnik().equals(vlasnik))
			{
				mojeVikendice.add(vikendica);
				//System.out.println("Dodata!");
			}
			//else System.out.println("Odbacena!");
		}
		return mojeVikendice;
	}
	public List<PoslovanjeEntitetaDTO> poslovanjeVikendicaPeriod(PoslovanjeEntitetaDTO poslovanje, Korisnik vlasnik) {
		List<Rezervacija> mojeRezervacije = rezervacijaServis.pronadjiRezervacijePoVlasniku(vlasnik, TipEntiteta.vikendica);
		List<Vikendica> mojeVikendice = this.nadjiVikendicePoVlasniku(vlasnik);
		System.out.println("Rezervacije size: "+mojeRezervacije.size());
		System.out.println("Pocetni dat: "+poslovanje.getPocetniDatum());
		List<PoslovanjeEntitetaDTO> poslovanjaVikendica = new ArrayList<PoslovanjeEntitetaDTO>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate pocetni = LocalDate.parse(poslovanje.getPocetniDatum(), dtf);
		LocalDate krajnji = LocalDate.parse(poslovanje.getKrajnjiDatum(), dtf);
		double vlasnikovProcenat = 1-Double.parseDouble(globalneVarijable.findByName("procenat").getVrednost());
		
		for(Vikendica vikendica : mojeVikendice)
		{
			PoslovanjeEntitetaDTO poslovanjeVikendice = new PoslovanjeEntitetaDTO(poslovanje);
			poslovanjeVikendice.setVlasnikID(vlasnik.getID());
			poslovanjeVikendice.setEntitetID(vikendica.getID());
			poslovanjeVikendice.setNazivEntiteta(vikendica.getNaziv());
			poslovanje.setOcjenaEntiteta(0.0);
			double prihod = 0;
			Long brojRezervacija = 0L;
			double zarada = 0;
			for(Rezervacija rezervacija: mojeRezervacije) 
			{
				if(rezervacija.getEntitetId()==vikendica.getID())
				{
					LocalDate datum = LocalDate.parse(rezervacija.getDatum(), dtf);
					if( (datum.isAfter(pocetni) || datum.isEqual(pocetni) ) && datum.isBefore(krajnji))
					{
						brojRezervacija++;
						zarada += rezervacija.getCena();
					}
				}
				//if(LocalDate.parse(r.getDatum(), dtf).isAfter())
			}
			poslovanjeVikendice.setZarada(zarada);
			poslovanjeVikendice.setBrojRezervacija(brojRezervacija);
			prihod = zarada*vlasnikovProcenat;
			poslovanjeVikendice.setPrihod(prihod);
			//System.out.println("poslovanjeVikendice: "+ poslovanjeVikendice);
			poslovanjaVikendica.add(poslovanjeVikendice);
		}
		return poslovanjaVikendica;
	}
		
	public PoslovanjeEntitetaDTO srediDatume(PoslovanjeEntitetaDTO poslovanje) {
		String[] pocetni = poslovanje.getPocetniDatum().split("-");
		System.out.println("Duzina: "+pocetni.length);
		return poslovanje;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajSedmicnaPoslovanjaVikendica(Korisnik vlasnik) 
	{
		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUdanu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.parse(LocalDate.now().format(dtf), dtf);
		LocalDate pocetni = trenutni.minusDays(6);
		LocalDate krajnji = pocetni.plusDays(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 7; i++)
		{
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAA");
			PoslovanjeEntitetaDTO dnevno =  new PoslovanjeEntitetaDTO();
			dnevno.setVlasnikID(vlasnik.getID());
			dnevno.setPocetniDatum(pocetni.format(dtf).toString());
			dnevno.setKrajnjiDatum(krajnji.format(dtf).toString());
			dnevno.setNazivEntiteta(pocetni.getDayOfWeek().toString());
			svaPoslovanjaUdanu = this.poslovanjeVikendicaPeriod(dnevno, vlasnik);
			double sedmicniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUdanu.size(); j++)
			{
				System.out.println("poslovanje: "+ svaPoslovanjaUdanu.get(j).getNazivEntiteta() );
				System.out.println("broj rez: "+svaPoslovanjaUdanu.get(j).getBrojRezervacija() );
				System.out.println("prihodi: "+svaPoslovanjaUdanu.get(j).getPrihod() );
				sedmicniPrihod += svaPoslovanjaUdanu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUdanu.get(j).getBrojRezervacija();
			}
			dnevno.setBrojRezervacija(brojRezervacija);
			dnevno.setPrihod(sedmicniPrihod);
			sedmicnaPoslovanja.add(dnevno);
			System.out.println("pocenti datum: "+pocetni);
			pocetni = pocetni.plusDays(1);
			System.out.println("promijenjen datum: "+pocetni);
			krajnji = krajnji.plusDays(1);
		}
		
		return sedmicnaPoslovanja;
	}
	public List<PoslovanjeEntitetaDTO> izracunajMjesecnaPoslovanjaVikendica(Korisnik vlasnik) {
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUsedmici;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusWeeks(4);
		LocalDate krajnji = pocetni.plusWeeks(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 4; i++)
		{
			PoslovanjeEntitetaDTO sedmicno =  new PoslovanjeEntitetaDTO();
			sedmicno.setVlasnikID(vlasnik.getID());
			sedmicno.setPocetniDatum(pocetni.format(dtf).toString());
			sedmicno.setKrajnjiDatum(krajnji.format(dtf).toString());
			sedmicno.setNazivEntiteta(pocetni.toString());
			svaPoslovanjaUsedmici = this.poslovanjeVikendicaPeriod(sedmicno, vlasnik);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUsedmici.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUsedmici.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUsedmici.get(j).getBrojRezervacija();
			}
			sedmicno.setBrojRezervacija(brojRezervacija);
			sedmicno.setPrihod(mjesecniPrihod);
			mjesecnaPoslovanja.add(sedmicno);
			pocetni = pocetni.plusWeeks(1);
			krajnji = krajnji.plusWeeks(1);
		}
		
		return mjesecnaPoslovanja;
	}
	public List<PoslovanjeEntitetaDTO> izracunajGodisnjaPoslovanjaVikendica(Korisnik vlasnik) {
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUmjesecu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusMonths(11);
		LocalDate krajnji = pocetni.plusMonths(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 12; i++)
		{
			PoslovanjeEntitetaDTO mjesecno =  new PoslovanjeEntitetaDTO();
			mjesecno.setVlasnikID(vlasnik.getID());
			mjesecno.setPocetniDatum(pocetni.format(dtf).toString());
			mjesecno.setKrajnjiDatum(krajnji.format(dtf).toString());
			mjesecno.setNazivEntiteta(pocetni.getMonth().toString());
			svaPoslovanjaUmjesecu = this.poslovanjeVikendicaPeriod(mjesecno, vlasnik);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUmjesecu.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUmjesecu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUmjesecu.get(j).getBrojRezervacija();
			}
			mjesecno.setBrojRezervacija(brojRezervacija);
			mjesecno.setPrihod(mjesecniPrihod);
			godisnjaPoslovanja.add(mjesecno);
			pocetni = pocetni.plusMonths(1);
			krajnji = krajnji.plusMonths(1);
		}
		
		return godisnjaPoslovanja;
	}
	public List<Vikendica> VikendicaPretraga(String s) {
		
		List<Vikendica>li=new ArrayList<Vikendica>();
		List<Vikendica>li2=vikendicaRepozitorijum.findAll();
		
		for (Vikendica e : li2) {
			List<Termin>termini=e.getTerminiZauzetosti();
			if(e.getAdresa().toLowerCase().contains(s.toLowerCase())) {
				li.add(e);
				continue;
			}
			if(e.getNaziv().toLowerCase().contains(s.toLowerCase())) {
				li.add(e);
				continue;
			}
			if(String.valueOf(e.getCena()).contains(s)) {
				li.add(e);
				continue;
			}
			Boolean postoji=false;
			if(s.contains("/")) {
			for (Termin ter : termini) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
				LocalDate datum_pocetka=LocalDate.parse(ter.getDatumVremePocetak(),dtf);
				LocalDate datum_kraja=LocalDate.parse(ter.getDatumVremeKraj(),dtf);
				LocalDate datum_unosa=LocalDate.parse((s),dtf);
				
				
				if(datum_pocetka.isAfter(datum_unosa) || datum_kraja.isBefore(datum_unosa)) {
					continue;
				}else {
					postoji=true;
					break;
				}
				
			}
			if(!postoji) {
				li.add(e);
			}
			
		}	}
			
		
		return li;
}
	public List<Vikendica> VikSortCena() {
		List<Vikendica>li=vikendicaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "cena"));
		System.out.println(li.toString());
		return li;
	}
	public List<Vikendica> VikSortNaziv() {
		List<Vikendica>li=vikendicaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "naziv"));
		System.out.println(li.toString());
		return li;
	}
	public List<Vikendica> VikSortAdresa() {
		List<Vikendica>li=vikendicaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "adresa"));
		System.out.println(li.toString());
		return li;
	}
	public List<Vikendica> VikFilter(String brodovifil) {
		List<Vikendica>li2=vikendicaRepozitorijum.findAll();
	    List<Vikendica>li=new ArrayList<Vikendica>();
		 for (Vikendica vik : li2) {
			 if(brodovifil.equals("svi")){
				return li2;
			}else if(vik.getAdresa().equals(brodovifil)) {
				li.add(vik);
			}
		}
		 return li;
	}

	public List<PoslovanjeEntitetaDTO> poslovanjeVikendicaPeriod(PoslovanjeEntitetaDTO poslovanje) 
	{
		List<Rezervacija> mojeRezervacije = rezervacijaServis.findByTip(TipEntiteta.vikendica);
		List<Vikendica> mojeVikendice = new ArrayList<Vikendica>();
		for (Rezervacija rez : mojeRezervacije) {
			System.out.println(rez.toString());
			mojeVikendice.add(this.findById(rez.getEntitetId()));
		}
		System.out.println("Rez datum: "+mojeRezervacije.get(0).getDatum());
		System.out.println("Pocetni dat: "+poslovanje.getPocetniDatum());
		List<PoslovanjeEntitetaDTO> poslovanjaVikendica = new ArrayList<PoslovanjeEntitetaDTO>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate pocetni = LocalDate.parse(poslovanje.getPocetniDatum(), dtf);
		LocalDate krajnji = LocalDate.parse(poslovanje.getKrajnjiDatum(), dtf);
		double procenat = Double.parseDouble(globalneVarijable.findByName("procenat").getVrednost());
		
		for(Vikendica vikendica : mojeVikendice)
		{
			PoslovanjeEntitetaDTO poslovanjeVikendice = new PoslovanjeEntitetaDTO(poslovanje);
			poslovanjeVikendice.setVlasnikID(null);
			poslovanjeVikendice.setEntitetID(vikendica.getID());
			poslovanjeVikendice.setNazivEntiteta(vikendica.getNaziv());
			poslovanje.setOcjenaEntiteta(0.0);
			double prihod = 0;
			Long brojRezervacija = 0L;
			double zarada = 0;
			for(Rezervacija rezervacija: mojeRezervacije) 
			{
				if(rezervacija.getEntitetId()==vikendica.getID())
				{
					LocalDate datum = LocalDate.parse(rezervacija.getDatum(), dtf);
					if(  (datum.isAfter(pocetni) || datum.isEqual(pocetni) ) && datum.isBefore(krajnji))
					{
						brojRezervacija++;
						zarada += rezervacija.getCena();
					}
				}
				//if(LocalDate.parse(r.getDatum(), dtf).isAfter())
			}
			poslovanjeVikendice.setZarada(zarada);
			poslovanjeVikendice.setBrojRezervacija(brojRezervacija);
			prihod = zarada*procenat;
			poslovanjeVikendice.setPrihod(prihod);
			poslovanjaVikendica.add(poslovanjeVikendice);
		}
		return poslovanjaVikendica;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajSedmicnaPoslovanjaVikendica() 
	{
		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUdanu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusWeeks(3);
		LocalDate krajnji = pocetni.plusWeeks(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 7; i++)
		{
			PoslovanjeEntitetaDTO dnevno =  new PoslovanjeEntitetaDTO();
			dnevno.setVlasnikID(null);
			dnevno.setPocetniDatum(pocetni.format(dtf).toString());
			dnevno.setKrajnjiDatum(krajnji.format(dtf).toString());
			dnevno.setNazivEntiteta(pocetni.getDayOfWeek().toString());
			svaPoslovanjaUdanu = this.poslovanjeVikendicaPeriod(dnevno);
			double dnevniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUdanu.size(); j++)
			{
				dnevniPrihod += svaPoslovanjaUdanu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUdanu.get(j).getBrojRezervacija();
			}
			dnevno.setBrojRezervacija(brojRezervacija);
			dnevno.setPrihod(dnevniPrihod);
			sedmicnaPoslovanja.add(dnevno);
			pocetni = pocetni.plusDays(1);
			krajnji = krajnji.plusDays(1);
		}
		
		return sedmicnaPoslovanja;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajMjesecnaPoslovanjaVikendica() {
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUsedmici;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusWeeks(4);
		LocalDate krajnji = pocetni.plusWeeks(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 4; i++)
		{
			PoslovanjeEntitetaDTO sedmicno =  new PoslovanjeEntitetaDTO();
			sedmicno.setVlasnikID(null);
			sedmicno.setPocetniDatum(pocetni.format(dtf).toString());
			sedmicno.setKrajnjiDatum(krajnji.format(dtf).toString());
			sedmicno.setNazivEntiteta(pocetni.toString());
			svaPoslovanjaUsedmici = this.poslovanjeVikendicaPeriod(sedmicno);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUsedmici.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUsedmici.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUsedmici.get(j).getBrojRezervacija();
			}
			sedmicno.setBrojRezervacija(brojRezervacija);
			sedmicno.setPrihod(mjesecniPrihod);
			mjesecnaPoslovanja.add(sedmicno);
			pocetni = pocetni.plusWeeks(1);
			krajnji = krajnji.plusWeeks(1);
		}
		
		return mjesecnaPoslovanja;
	}
	public List<PoslovanjeEntitetaDTO> izracunajGodisnjaPoslovanjaVikendica() {
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUmjesecu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusMonths(12);
		LocalDate krajnji = pocetni.plusMonths(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 12; i++)
		{
			PoslovanjeEntitetaDTO mjesecno =  new PoslovanjeEntitetaDTO();
			mjesecno.setVlasnikID(null);
			mjesecno.setPocetniDatum(pocetni.format(dtf).toString());
			mjesecno.setKrajnjiDatum(krajnji.format(dtf).toString());
			mjesecno.setNazivEntiteta(pocetni.getMonth().toString());
			svaPoslovanjaUmjesecu = this.poslovanjeVikendicaPeriod(mjesecno);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUmjesecu.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUmjesecu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUmjesecu.get(j).getBrojRezervacija();
			}
			mjesecno.setBrojRezervacija(brojRezervacija);
			mjesecno.setPrihod(mjesecniPrihod);
			godisnjaPoslovanja.add(mjesecno);
			pocetni = pocetni.plusMonths(1);
			krajnji = krajnji.plusMonths(1);
		}
		
		return godisnjaPoslovanja;
	}
	public List<PeriodPrikazDTO> dobaviPeriode(Vikendica vikendica) 
	{
		terminServis.popraviPeriodeVikendice(vikendica);
				
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : vikendica.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()==null)
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat period: "+ period);
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermine(Vikendica vikendica) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.listAll();
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		System.out.println("termini vikendice: "+vikendica.getTerminiZauzetosti().size());
		for(Termin termin : vikendica.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null)
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat termin zauzetosti: "+ period);
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermineBrzihRezervacija(Vikendica vikendica) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.nadjiPoTipuRezervacije(TipRezervacije.brza);
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : vikendica.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null && termin.getRezervacija().getTip().equals(TipRezervacije.brza))
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat termin brze: "+ period);
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermineObicnihRezervacija(Vikendica vikendica) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.nadjiPoTipuRezervacije(TipRezervacije.obicna);
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : vikendica.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null && termin.getRezervacija().getTip().equals(TipRezervacije.obicna))
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat termin obicne: "+ period);
			}
		}
		return periodi;
	}
	
	
	public void ubaciVikendicuUbazu(Vikendica vikendica) {
		vikendicaRepozitorijum.save(vikendica);
		
	}


	public Boolean dodajPretplatuNaVik(Korisnik klijent, Vikendica vik) {
		Vikendica br=this.findById(vik.getID());
		if(klijent==null || br==null) {
			return false;
		}
		List<Korisnik>li=br.getPretplaceniKorisnici();
		li.add(klijent);
		br.setPretplaceniKorisnici(li);
		this.vikendicaRepozitorijum.save(br);
		return true;
	}
	
	public List<Vikendica> findByPretplaceniKorisnikVik(Korisnik kor) {
		
		
	
		List<Vikendica>li=new ArrayList<Vikendica>();
		
		List<Vikendica>li2=this.vikendicaRepozitorijum.findAll();
		for (Vikendica v : li2) {
			System.out.println(v.getPretplaceniKorisnici().size());
		if(v.getPretplaceniKorisnici().size()==0)
		
			continue;
		
			for (Korisnik k : v.getPretplaceniKorisnici()) {
				  System.out.println(kor);
				if(k.equals(kor)) {
					li.add(v);
				}
			}
		}
	   
		  System.out.println(li);
		return li;
	}

	
	
	
	
	
}