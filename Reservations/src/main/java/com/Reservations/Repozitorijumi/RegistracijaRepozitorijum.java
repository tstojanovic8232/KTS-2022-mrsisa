package com.Reservations.Repozitorijumi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Registracija;

public interface RegistracijaRepozitorijum extends JpaRepository<Registracija, Long>{
	Registracija findByKorisnickoIme(String username);
    Iterable<Registracija> findByPrezime(String string);
}
