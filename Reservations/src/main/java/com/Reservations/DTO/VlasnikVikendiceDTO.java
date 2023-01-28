package com.Reservations.DTO;

import java.util.Date;

import com.Reservations.Modeli.Korisnik;

public class VlasnikVikendiceDTO 
{
	private Long ID;
	
	private String korisnickoIme;

	private String ime;
	
	private String prezime;

	private String lozinka;

	private String adresa;

	private String email;
	
	private String grad;

	private String drzava;
	
	private String brojTel;
	
	private String linkSlike;
	
	public VlasnikVikendiceDTO()
	{}
	
	
	public VlasnikVikendiceDTO(Long id, String korisnickoIme, String ime, String prezime, String email, String lozinka,
			String adresa, String grad, String drzava, String brojTel) {
		super();
		this.ID = id;
		this.korisnickoIme = korisnickoIme;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTel = brojTel;
		this.linkSlike = "/img/avatar.png";
	}
	
	public VlasnikVikendiceDTO(Long id, String korisnickoIme, String ime, String prezime, String email, String lozinka,
			String adresa, String grad, String drzava, String brojTel, String linkSlike) {
		super();
		this.ID = id;
		this.korisnickoIme = korisnickoIme;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTel = brojTel;
		this.linkSlike = linkSlike;
	}


	public Long getID() {
		return ID;
	}


	public void setID(Long iD) {
		ID = iD;
	}


	public VlasnikVikendiceDTO(Korisnik korisnik) {
		this.ID = korisnik.getID();
		this.ime = korisnik.getIme();
		this.prezime=korisnik.getPrezime();
		this.adresa = korisnik.getAdresa();
		this.grad = korisnik.getGrad();
		this.brojTel = korisnik.getBrojTel();
		this.email = korisnik.getEmail();
		this.drzava = korisnik.getDrzava();
		this.lozinka = korisnik.getLozinka();
		this.korisnickoIme = korisnik.getKorisnickoIme();
		this.linkSlike = korisnik.getLinkSlike();
	}

	
	public Long getId()
	{
		return this.ID;
	}
	
	public void setId(Long id)
	{
		this.ID = id;
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
	
	

	public String getLinkSlike() {
		return linkSlike;
	}


	public void setLinkSlike(String linkSlike) {
		this.linkSlike = linkSlike;
	}


	public Date getPoslednjiDatumPromeneLozinke() {
		return null;
	}


	@Override
	public String toString() {
		return "VlasnikVikendiceDTO [ID=" + ID + ", korisnickoIme=" + korisnickoIme + ", ime=" + ime + ", prezime="
				+ prezime + ", lozinka=" + lozinka + ", adresa=" + adresa + ", email=" + email + ", grad=" + grad
				+ ", drzava=" + drzava + ", brojTel=" + brojTel + ", linkSlike=" + linkSlike + "]";
	}


}
