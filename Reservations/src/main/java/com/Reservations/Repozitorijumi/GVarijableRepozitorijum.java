package com.Reservations.Repozitorijumi;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Reservations.Modeli.GlobalnaVarijabla;

public interface GVarijableRepozitorijum extends JpaRepository<GlobalnaVarijabla, Long> {
	GlobalnaVarijabla findByIme(String ime);
}
