package com.Reservations.Repozitorijumi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.ZahtevZaBrisanje;

public interface BrisanjeNalogaRepozitorijum extends JpaRepository<ZahtevZaBrisanje, Long> {
	ZahtevZaBrisanje findByKorisnickoIme(String name);
}
