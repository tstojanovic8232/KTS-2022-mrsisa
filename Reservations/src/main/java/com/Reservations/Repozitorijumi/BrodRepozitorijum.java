package com.Reservations.Repozitorijumi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Brod;

public interface BrodRepozitorijum extends JpaRepository<Brod, Long>
{
	Brod findByNaziv(String name);
	List<Brod> findAll();


	Brod findByAdresa(String adres);
	Brod findByCena(Double cena);
}
