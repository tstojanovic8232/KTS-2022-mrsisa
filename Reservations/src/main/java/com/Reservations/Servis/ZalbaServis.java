package com.Reservations.Servis;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.ZalbaDTO;
import com.Reservations.Modeli.Zalba;
import com.Reservations.Repozitorijumi.ZalbeRepozitorijum;

@Service
public class ZalbaServis {
	@Autowired
	private ZalbeRepozitorijum zalbeRepozitorijum;

	@Autowired
	private RezervacijaServis rezser;

	public Zalba findById(Long id) {
		try {
			return zalbeRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public Zalba save(ZalbaDTO regRequest) {
		Zalba r = new Zalba();

		r.setNaziv(regRequest.getNaziv_klijenta());

		r.setRezervacija(rezser.findById(regRequest.getRez_id()));
		r.setZalba(regRequest.getZalba());

		return this.zalbeRepozitorijum.save(r);// TODO Auto-generated method stub
	}

	public List<Zalba> listAll() {
		// TODO Auto-generated method stub
		return this.zalbeRepozitorijum.findAll();
	}
	
	public List<Zalba> listajZalbeBezOdgovora() {
		List<Zalba> zalbe = this.zalbeRepozitorijum.findAll();
		List<Zalba> zalbeBez = new ArrayList<Zalba>();
		
		for (Zalba zalba : zalbe) {
			System.out.println("Zalba " + zalba.getID());
			System.out.println("Odgovor: " + zalba.getOdgovor());
			if(zalba.getOdgovor()==null) zalbeBez.add(zalba);
		}
		
		return zalbeBez;
	}

	public void delete(long id) {
		// TODO Auto-generated method stub
		this.zalbeRepozitorijum.deleteById(id);
	}

	public boolean upisiOdgovor(long id, String odgovor) {
		Zalba zalba = this.findById(id);
		if (zalba != null)
			zalba.setOdgovor(odgovor);
		else
			return false;
		this.zalbeRepozitorijum.save(zalba);
		return true;
	}
}
