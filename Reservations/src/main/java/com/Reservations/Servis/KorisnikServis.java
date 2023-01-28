package com.Reservations.Servis;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.AdminDTO;
import com.Reservations.DTO.AzuriranjeInstruktoraDTO;
import com.Reservations.DTO.BrisanjeKorisnikaZahtjevDTO;
import com.Reservations.DTO.RegistracijaKorisnikaDTO;
import com.Reservations.DTO.VlasnikVikendiceDTO;
import com.Reservations.DTO.ZahtevZaBrisanjeDTO;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Modeli.Uloga;
import com.Reservations.Modeli.ZahtevZaBrisanje;
import com.Reservations.Modeli.enums.TipRegistracije;
import com.Reservations.Repozitorijumi.KorisnikRepozitorijum;

@Service
public class KorisnikServis {
	@Autowired
	CustomUserDetailsService cuds;

	@Autowired
	public KorisnikRepozitorijum korisnikRepozitorijum;

	@Autowired
	private UlogaServis ulogaServis;

	@Autowired
	private BrisanjeNalogaServis brisanjeNalogaServis;

	public Korisnik findByUsername(String username) {
		return korisnikRepozitorijum.findByKorisnickoIme(username);
	}

	public void delete(long id) {
		this.korisnikRepozitorijum.deleteById(id);
	}

	public List<Korisnik> listAll() {
		return korisnikRepozitorijum.findAll();
	}

	public Korisnik findById(Long id) {
		try {
			return korisnikRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Korisnik save(RegistracijaKorisnikaDTO userRequest) {
		Korisnik u = new Korisnik();

		u.setKorisnickoIme(userRequest.getUsername());
		u.setLinkSlike("/img/avatar.png");
		u.setLozinka(userRequest.getPassword());

		u.setIme(userRequest.getFirstName());
		u.setPrezime(userRequest.getLastName());
		u.setAdresa(userRequest.getAddress());
		u.setGrad(userRequest.getCity());
		u.setDrzava(userRequest.getCountry());
		u.setBrojTel(userRequest.getPhone());
		// u.setEnabled(true);
		u.setEmail(userRequest.getEmail());

		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i
		// dodeljuje samo rola USER
		Uloga role = ulogaServis.findByName("Klijent");
		u.setUloga(role);

		return this.korisnikRepozitorijum.save(u);// TODO Auto-generated method stub
	}

	public Korisnik save(Registracija reg) {
		Korisnik u = new Korisnik();

		u.setKorisnickoIme(reg.getKorisnickoIme());

		u.setLozinka(reg.getLozinka());

		u.setIme(reg.getIme());
		u.setPrezime(reg.getPrezime());
		u.setAdresa(reg.getAdresa());
		u.setGrad(reg.getGrad());
		u.setDrzava(reg.getDrzava());
		u.setBrojTel(reg.getBrojTel());
		// u.setEnabled(true);
		u.setEmail(reg.getEmail());

		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i
		// dodeljuje samo rola USER
		Uloga role = new Uloga();
		if (reg.getTipRegistracije().equals(TipRegistracije.VikendicaVlasnik)) {
			role = ulogaServis.findByName("VikendicaVlasnik");
		} else if (reg.getTipRegistracije().equals(TipRegistracije.BrodVlasnik)) {
			role = ulogaServis.findByName("BrodVlasnik");
		} else if (reg.getTipRegistracije().equals(TipRegistracije.InstruktorPecanja)) {
			role = ulogaServis.findByName("Instruktor");
		}

		u.setUloga(role);
		u.setEnabled(true);

		return this.korisnikRepozitorijum.save(u);// TODO Auto-generated method stub
	}

	public Korisnik update(ZahtevZaBrisanjeDTO regRequest) {
		Korisnik r = korisnikRepozitorijum.findByKorisnickoIme(regRequest.getUsername());
		r.setKorisnickoIme(regRequest.getUsername());

		r.setIme(regRequest.getFirstName());
		r.setPrezime(regRequest.getLastName());
		r.setAdresa(regRequest.getAddress());
		r.setGrad(regRequest.getCity());
		r.setDrzava(regRequest.getCountry());
		r.setBrojTel(regRequest.getPhone());

		return this.korisnikRepozitorijum.save(r);// TODO Auto-generated method stub
	}

	public Korisnik update(AzuriranjeInstruktoraDTO userRequest) {
		Korisnik k = this.findById(userRequest.getId());
		if (!userRequest.getAdresa().equals("")) {
			k.setAdresa(userRequest.getAdresa());
		}
		if (!userRequest.getBrojTel().equals("")) {
			k.setBrojTel(userRequest.getBrojTel());
		}
		if (!userRequest.getDrzava().equals("")) {
			k.setDrzava(userRequest.getDrzava());
		}
		if (!userRequest.getGrad().equals("")) {
			k.setGrad(userRequest.getGrad());
		}
		if (!userRequest.getKorisnickoIme().equals("")) {
			k.setKorisnickoIme(userRequest.getKorisnickoIme());
		}
		if (!userRequest.getIme().equals("")) {
			k.setIme(userRequest.getIme());
		}
		if (!userRequest.getPrezime().equals("")) {
			k.setPrezime(userRequest.getPrezime());
		}
		System.out.println(k.toString());
		return this.korisnikRepozitorijum.save(k);
	}

	public Korisnik changePassword(Long id, String staraLozinka, String novaLozinka) {
		Korisnik k = this.findById(id);
		k.setLozinka(novaLozinka);
		// cuds.changePassword(staraLozinka, novaLozinka);
		return this.korisnikRepozitorijum.save(k);

	}

	public Korisnik azurirajPodatkeVlasnika(VlasnikVikendiceDTO vlasnik) {
		List<Korisnik> korisnici = korisnikRepozitorijum.findAll();
		Korisnik korisnik = this.findById(vlasnik.getId());
		
		if (korisnik != null) 
		{
			try
			{
				for(int i = 0; i<korisnici.size(); i++)
				{
					if(vlasnik.getKorisnickoIme().equals(korisnici.get(i).getKorisnickoIme()) 
					&& !vlasnik.getKorisnickoIme().equals(korisnik.getKorisnickoIme()))
					{
						
						return null;
					}
				}
				korisnik.setAdresa(vlasnik.getAdresa());
				korisnik.setBrojTel(vlasnik.getBrojTel());
				korisnik.setDrzava(vlasnik.getDrzava());
				korisnik.setEmail(vlasnik.getEmail());
				korisnik.setGrad(vlasnik.getGrad());
				korisnik.setIme(vlasnik.getIme());
				korisnik.setKorisnickoIme(vlasnik.getKorisnickoIme());
				korisnik.setPrezime(vlasnik.getPrezime());
				if(vlasnik.getLinkSlike()!=null && !vlasnik.getLinkSlike().trim().equals(""))korisnik.setLinkSlike(vlasnik.getLinkSlike());
			}
			catch(Exception e)
			{
				return null;
			}
		}
		return this.korisnikRepozitorijum.save(korisnik);
	}

	public String save(AdminDTO admin) {
		Korisnik u = new Korisnik();

		u.setKorisnickoIme(admin.getKorisnickoIme());
		u.setLinkSlike("/img/avatar.png");
	    final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    String lozinka = RandomStringUtils.random(6, chars);
		u.setLozinka(lozinka);
		u.setIme(admin.getIme());
		u.setPrezime(admin.getPrezime());
		u.setAdresa(admin.getAdresa());
		u.setGrad(admin.getGrad());
		u.setDrzava(admin.getDrzava());
		u.setBrojTel(admin.getBrojTel());
		//u.setEnabled(true);
		u.setEmail(admin.getEmail());
		// u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
		Uloga role = ulogaServis.findByName("Admin");
		u.setUloga(role);
		
		this.korisnikRepozitorijum.save(u);// TODO Auto-generated method stub
		return lozinka;
		}

	public List<Korisnik> findByUloga(String uloga) {
		List<String> uloge = new ArrayList<String>();
		uloge.add("Admin");
		uloge.add("Klijent");
		uloge.add("VikendicaVlasnik");
		uloge.add("BrodVlasnik");
		uloge.add("Instruktor");
		if (!uloge.contains(uloga)) return null;
		List<Korisnik> svi = this.korisnikRepozitorijum.findAll();
		List<Korisnik> korisnici = new ArrayList<Korisnik>();
		Uloga role = ulogaServis.findByName(uloga);
		for (Korisnik k : svi)
		{
			if(k.getUloga().equals(role)) korisnici.add(k);
		}
		return korisnici;
	}

	public String[] posaljiZahtjevZaBrisanje(Korisnik vlasnik, BrisanjeKorisnikaZahtjevDTO zahtjev) 
	{
		String poruka[] = new String[2];
		poruka[1] = "error";
		if(!zahtjev.getIme().equals(vlasnik.getIme()))
		{
			poruka[0] = "Pogrešno unijeto ime!";
		}
		else if(!zahtjev.getPrezime().equals(vlasnik.getPrezime()))
		{
			poruka[0] = "Pogrešno unijeto prezime!";
		}
		else if(!zahtjev.getKorisnickoIme().equals(vlasnik.getKorisnickoIme()))
		{
			poruka[0] = "Pogrešno unijeto korisničko ime!";
		}
		else if(!zahtjev.getEmail().equals(vlasnik.getEmail()))
		{
			poruka[0] = "Pogrešno unijeta email adresa!";
		}
		else if(!zahtjev.getLozinka().equals(vlasnik.getLozinka()) || !zahtjev.getLozinkaPonovo().equals(vlasnik.getLozinka()))
		{
			poruka[0] = "Pogrešno unijeta lozinka!";
		}
		else
		{
			try
			{
				ZahtevZaBrisanje zahtjevZaBrisanje = new ZahtevZaBrisanje(zahtjev);
				boolean poslat = brisanjeNalogaServis.posaljiZahtjev(zahtjevZaBrisanje);
				if(poslat)
				{
					poruka[1]="success";
					poruka[0]="Zahtjev za brisanje naloga poslat!";
				}
				else
				{
					poruka[1]="duplicate";
					poruka[0]="Zahtjev za brisanje naloga već postoji!";
				}
			}
			catch(Exception e)
			{
				poruka[0] = "Došlo je do greške prilikom slanja zahtjeva!";
				poruka[1]="error";
			}
			
		}
		return poruka;
	}


}