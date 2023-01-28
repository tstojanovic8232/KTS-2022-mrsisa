package com.Reservations.Servis;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.ZahtevZaBrisanjeDTO;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Modeli.ZahtevZaBrisanje;
import com.Reservations.Repozitorijumi.BrisanjeNalogaRepozitorijum;
@Service
public class BrisanjeNalogaServis {
	@Autowired
	private BrisanjeNalogaRepozitorijum brisanjeRepozitorijum;


	
	public ZahtevZaBrisanje findById(Long id) {
		try {
			return brisanjeRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public ZahtevZaBrisanje save(ZahtevZaBrisanjeDTO regRequest) {
		ZahtevZaBrisanje r = new ZahtevZaBrisanje();
		r.setKorisnickoIme(regRequest.getUsername());
		r.setLozinka(regRequest.getPassword());

		r.setID(regRequest.getId());
		r.setIme(regRequest.getFirstName());
		r.setPrezime(regRequest.getLastName());
		r.setAdresa(regRequest.getAddress());
		r.setGrad(regRequest.getCity());
		r.setDrzava(regRequest.getCountry());
		r.setBrojTel(regRequest.getPhone());
		
		r.setRazlogRegistracije(regRequest.getRazlog());
		r.setEmail(regRequest.getEmail());
		
		return this.brisanjeRepozitorijum.save(r);// TODO Auto-generated method stub
	}

	public List<ZahtevZaBrisanje> listAll() {
		return this.brisanjeRepozitorijum.findAll();
	}
	
	public void delete(long id) {
		this.brisanjeRepozitorijum.deleteById(id);
	}
	public ZahtevZaBrisanje findByKorisnickoIme(String ime){
		return this.brisanjeRepozitorijum.findByKorisnickoIme(ime);

		}

	public ZahtevZaBrisanje save(Korisnik k, String razlog) {
		ZahtevZaBrisanje r = new ZahtevZaBrisanje();
		r.setKorisnickoIme(k.getKorisnickoIme());
		r.setLozinka(k.getLozinka());

		r.setID(k.getID());
		r.setIme(k.getIme());
		r.setPrezime(k.getPrezime());
		r.setAdresa(k.getAdresa());
		r.setGrad(k.getGrad());
		r.setDrzava(k.getDrzava());
		r.setBrojTel(k.getBrojTel());
		
		r.setRazlogRegistracije(razlog);
		r.setEmail(k.getEmail());
		
		return this.brisanjeRepozitorijum.save(r);
		
	}

	public boolean posaljiZahtjev(ZahtevZaBrisanje zahtjevZaBrisanje) {
		ZahtevZaBrisanje duplikat = brisanjeRepozitorijum.findByKorisnickoIme(zahtjevZaBrisanje.getKorisnickoIme() );
		if(duplikat==null)
		{
			brisanjeRepozitorijum.save(zahtjevZaBrisanje);
			return true;
		}
		else return false;
	}

}