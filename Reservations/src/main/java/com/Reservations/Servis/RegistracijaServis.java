package com.Reservations.Servis;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.RegistracijaVlasnikaInstruktoraDTO;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Repozitorijumi.RegistracijaRepozitorijum;

@Service
public class RegistracijaServis 
{

	
	@Autowired
	private RegistracijaRepozitorijum registracijaRepozitorijum;


	
	public Registracija findById(Long id) {
		try {
			return registracijaRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Registracija save(RegistracijaVlasnikaInstruktoraDTO regRequest) {
		Registracija r = new Registracija();
		r.setKorisnickoIme(regRequest.getUsername());
		r.setLozinka(regRequest.getPassword());

		r.setID(regRequest.getId());
		r.setIme(regRequest.getFirstName());
		r.setPrezime(regRequest.getLastName());
		r.setAdresa(regRequest.getAddress());
		r.setGrad(regRequest.getCity());
		r.setDrzava(regRequest.getCountry());
		r.setBrojTel(regRequest.getPhone());
		r.setTipRegistracije(regRequest.getRegistrationType());
		r.setRazlogRegistracije(regRequest.getRegisterReason());
		r.setEmail(regRequest.getEmail());
		
		return this.registracijaRepozitorijum.save(r);// TODO Auto-generated method stub
	}

	public List<Registracija> listAll() {
		return this.registracijaRepozitorijum.findAll();
	}
	
	public void delete(long id) {
		this.registracijaRepozitorijum.deleteById(id);
	}
}