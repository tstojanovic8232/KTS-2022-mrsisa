package com.Reservations.Repozitorijumi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Zalba;

public interface ZalbeRepozitorijum extends JpaRepository<Zalba, Long> {
	List<Zalba> findAll();
	Optional<Zalba> findById(Long Id);
	
}
