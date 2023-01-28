package com.Reservations.Servis;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.GVarijablaDTO;
import com.Reservations.Modeli.GlobalnaVarijabla;
import com.Reservations.Repozitorijumi.GVarijableRepozitorijum;

@Service
public class GVarijableServis {
	
	@Autowired
	GVarijableRepozitorijum gvRepozitorijum;
	
	public GlobalnaVarijabla save(GVarijablaDTO gvDTO) {
		GlobalnaVarijabla gv = new GlobalnaVarijabla();

		gv.setID(gvDTO.getID());
		gv.setIme(gvDTO.getIme());
		gv.setVrednost(gvDTO.getVrednost());
		
		return this.gvRepozitorijum.save(gv);
	}
	
	public GlobalnaVarijabla update(GVarijablaDTO gvDTO) {
		GlobalnaVarijabla gv = gvRepozitorijum.findByIme(gvDTO.getIme());

		gv.setID(gvDTO.getID());
		gv.setIme(gvDTO.getIme());
		gv.setVrednost(gvDTO.getVrednost());
		
		return this.gvRepozitorijum.save(gv);
	}
	
	public GlobalnaVarijabla findByName(String ime){
		return this.gvRepozitorijum.findByIme(ime);
	}

	public GlobalnaVarijabla findById(long i) {
		try {
			return gvRepozitorijum.findById(i).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}
