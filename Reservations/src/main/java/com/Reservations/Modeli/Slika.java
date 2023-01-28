package com.Reservations.Modeli;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Slike")
public class Slika 
{
	@Id
	@Column(name="id")
	@GeneratedValue
	private Long ID;
	
	@Column(name="naziv")
	private String naziv;
	
	@Column(name="putanja")
	private String putanja;
	
	@Column(name="slika")
	private File slika;

	public Slika() {}
	
	public Slika(Long iD, String naziv, String putanja, File slika) {
		super();
		ID = iD;
		this.naziv = naziv;
		this.putanja = putanja;
		this.slika = slika;
	}
	
	

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPutanja() {
		return putanja;
	}

	public void setPutanja(String putanja) {
		this.putanja = putanja;
	}

	public File getSlika() {
		return slika;
	}

	public void setSlika(File slika) {
		this.slika = slika;
	}
	
	
	
}
