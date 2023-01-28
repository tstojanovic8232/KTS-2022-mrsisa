package com.Reservations.Repozitorijumi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Korisnik;

public interface KorisnikRepozitorijum extends JpaRepository<Korisnik, Long> 
{
    Korisnik findByKorisnickoIme(String korisnickoIme);
    Iterable<Korisnik> findByPrezime(String string);
	Korisnik findByLozinka(String staraLozinka);
}
