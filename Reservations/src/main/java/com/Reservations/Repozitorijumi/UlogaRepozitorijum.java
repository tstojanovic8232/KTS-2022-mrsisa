package com.Reservations.Repozitorijumi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Uloga;

public interface UlogaRepozitorijum extends JpaRepository<Uloga, Long>
{
	Uloga findByIme(String name);
	//Uloga findById(Long id);
}


