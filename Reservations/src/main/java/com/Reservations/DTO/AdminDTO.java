package com.Reservations.DTO;

public class AdminDTO {
	
	private String korisnickoIme;
	
	private String email;
	
	private String ime;

	private String prezime;

	private String adresa;

	private String grad;

	private String drzava;

	private String brojTel;

	public AdminDTO() {
	}

	public AdminDTO(String korisnickoIme, String email, String ime, String prezime, String adresa, String grad,
			String drzava, String brojTel) {
		this.korisnickoIme = korisnickoIme;
		this.email = email;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTel = brojTel;
	}


	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "AdminDTO [korisnickoIme=" + korisnickoIme + ", email=" + email + ", ime=" + ime
				+ ", prezime=" + prezime + ", adresa=" + adresa + ", grad=" + grad + ", drzava=" + drzava + ", brojTel="
				+ brojTel + " ]";
	}
	
	

}
