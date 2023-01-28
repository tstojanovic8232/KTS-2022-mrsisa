package com.Reservations.DTO;

import com.Reservations.Modeli.Korisnik;

public class KlijentSpisakDTO 
{
	Long klijentID;
	String ime;
	String prezime;
	String punoIme;
	String drzava;
	String grad;
	Long brojRezervacija;
	
	public KlijentSpisakDTO() {}
	
	public KlijentSpisakDTO(Korisnik korisnik, Long rezervacije)
	{
		this.klijentID = korisnik.getID();
		this.ime = korisnik.getIme();
		this.prezime = korisnik.getPrezime();
		this.punoIme = korisnik.getIme()+" "+korisnik.getPrezime();
		this.grad = korisnik.getGrad();
		this.brojRezervacija = rezervacije;
		this.drzava = korisnik.getDrzava();
	}

	public KlijentSpisakDTO(Long klijentID, String ime, String prezime, String punoIme, String drzava, String grad,
			Long brojRezervacija) {
		super();
		this.klijentID = klijentID;
		this.ime = ime;
		this.prezime = prezime;
		this.punoIme = punoIme;
		this.drzava = drzava;
		this.grad = grad;
		this.brojRezervacija = brojRezervacija;
	}



	public Long getKlijentID() {
		return klijentID;
	}

	public void setKlijentID(Long klijentID) {
		this.klijentID = klijentID;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getPunoIme() {
		return punoIme;
	}

	public void setPunoIme(String punoIme) {
		this.punoIme = punoIme;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public Long getBrojRezervacija() {
		return brojRezervacija;
	}

	public void setBrojRezervacija(Long brojRezervacija) {
		this.brojRezervacija = brojRezervacija;
	}

	
	
}
