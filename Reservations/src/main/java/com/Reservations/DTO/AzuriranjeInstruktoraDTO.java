package com.Reservations.DTO;

public class AzuriranjeInstruktoraDTO {
	private Long id;
	
	private String korisnickoIme;
	
	private String ime;

	private String prezime;

	private String adresa;

	private String grad;

	private String drzava;

	private String brojTel;
	

	public AzuriranjeInstruktoraDTO() {
		super();
	}


	public AzuriranjeInstruktoraDTO(String ime, String korisnickoIme, String prezime, String adresa, String grad, String drzava,
			String brojTel) {
		super();
		this.ime = ime;
		this.korisnickoIme = korisnickoIme;
		this.prezime = prezime;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTel = brojTel;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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
		return "AzuriranjeInstruktoraDTO [id=" + id + ", korisnickoIme=" + korisnickoIme + ", ime=" + ime + ", prezime="
				+ prezime + ", adresa=" + adresa + ", grad=" + grad + ", drzava=" + drzava + ", brojTel=" + brojTel
				+ "]";
	}
	
	
}
