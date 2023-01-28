package com.Reservations.Repozitorijumi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Slika;

public interface SlikaRepozitorijum extends JpaRepository<Slika,Long>{
	Slika findByNaziv(String naziv);
	//Slika findbyId(Long id);

	
    
}
