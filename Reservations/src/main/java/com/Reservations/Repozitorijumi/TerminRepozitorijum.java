package com.Reservations.Repozitorijumi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Modeli.enums.TipEntiteta;

public interface TerminRepozitorijum extends JpaRepository<Termin,Long>{
	
	
	List<Termin> findAll();
	
	
	List<Termin> findByBrod(Brod brod);
	List<Termin> findByBrod(Long brodId);
	
	List<Termin> findByVikendica(Vikendica vikendica);
	List<Termin> findByVikendica(Long vikendicaId);
	
	List<Termin> findByUsluga(Usluga usluga);
	List<Termin> findByUsluga(Long uslugaId);
	
	List<Termin> findByTipEntiteta(TipEntiteta tipEntiteta);
	
    
}
