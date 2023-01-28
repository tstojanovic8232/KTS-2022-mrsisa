package com.Reservations.Repozitorijumi;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Vikendica;

public interface VikendicaRepozitorijum extends JpaRepository<Vikendica, Long> 
{
	Vikendica findByNaziv(String naziv);

	List<Vikendica> findAll();

	
}
