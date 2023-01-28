package com.Reservations.DTO;

import javax.persistence.Column;

import com.Reservations.Modeli.ZahtevZaBrisanje;

public class BrisanjeKorisnikaZahtjevDTO 
{
	private long ID;
	private String korisnickoIme;
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	private String lozinkaPonovo;
	private String adresa;
	private String grad;
	private String drzava;
	private String brojTel;
	private String razlog;
	


	public BrisanjeKorisnikaZahtjevDTO(ZahtevZaBrisanje zahtev)
	{
		this.adresa = zahtev.getAdresa();
		this.brojTel = zahtev.getBrojTel();
		this.drzava = zahtev.getDrzava();
		this.email = zahtev.getEmail();
		this.grad = zahtev.getGrad();
		this.ID = zahtev.getID();
		this.ime = zahtev.getIme();
		this.korisnickoIme = zahtev.getKorisnickoIme();
		this.lozinka = zahtev.getLozinka();
		this.lozinkaPonovo = zahtev.getLozinka();
		this.prezime = zahtev.getPrezime();
		this.razlog = zahtev.getRazlogRegistracije();
	}



	public BrisanjeKorisnikaZahtjevDTO() {
		// TODO Auto-generated constructor stub
	}



	public long getID() {
		return ID;
	}



	public void setID(long iD) {
		ID = iD;
	}



	public String getKorisnickoIme() {
		return korisnickoIme;
	}



	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getLozinka() {
		return lozinka;
	}



	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}



	public String getAdresa() {
		return adresa;
	}



	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}



	public String getGrad() {
		return grad;
	}



	public void setGrad(String grad) {
		this.grad = grad;
	}



	public String getDrzava() {
		return drzava;
	}



	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}



	public String getBrojTel() {
		return brojTel;
	}



	public void setBrojTel(String brojTel) {
		this.brojTel = brojTel;
	}



	public String getRazlog() {
		return razlog;
	}



	public void setRazlog(String razlog) {
		this.razlog = razlog;
	}



	public String getLozinkaPonovo() {
		return lozinkaPonovo;
	}



	public void setLozinkaPonovo(String lozinkaPonovo) {
		this.lozinkaPonovo = lozinkaPonovo;
	}
	
	
}
