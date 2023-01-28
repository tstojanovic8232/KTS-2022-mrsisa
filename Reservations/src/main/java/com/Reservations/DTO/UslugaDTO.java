package com.Reservations.DTO;

public class UslugaDTO {
	private String naziv;

	private String adresa;

	private String opis;

	private String biografijaInstruktora;

	private int maxOsoba;

	private String pecaroskaOprema;

	private double cena;

	private String tip;

	private String linkSlike;

	public UslugaDTO() {
	}

	public UslugaDTO(String naziv, String adresa, String opis, String biografijaInstruktora, int maxOsoba,
			String pecaroskaOprema, double cena, String tip, String linkSlike) {
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.biografijaInstruktora = biografijaInstruktora;
		this.maxOsoba = maxOsoba;
		this.pecaroskaOprema = pecaroskaOprema;
		this.cena = cena;
		this.tip = tip;
		this.linkSlike = linkSlike;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getBiografijaInstruktora() {
		return biografijaInstruktora;
	}

	public void setBiografijaInstruktora(String biografijaInstruktora) {
		this.biografijaInstruktora = biografijaInstruktora;
	}

	public int getMaxOsoba() {
		return maxOsoba;
	}

	public void setMaxOsoba(int maxOsoba) {
		this.maxOsoba = maxOsoba;
	}

	public String getPecaroskaOprema() {
		return pecaroskaOprema;
	}

	public void setPecaroskaOprema(String pecaroskaOprema) {
		this.pecaroskaOprema = pecaroskaOprema;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getLinkSlike() {
		return linkSlike;
	}

	public void setLinkSlike(String linkSlike) {
		this.linkSlike = linkSlike;
	}

	@Override
	public String toString() {
		return "UslugaDTO [naziv=" + naziv + ", adresa=" + adresa + ", opis=" + opis + ", biografijaInstruktora="
				+ biografijaInstruktora + ", maxOsoba=" + maxOsoba + ", pecaroskaOprema=" + pecaroskaOprema + ", cena="
				+ cena + ", tip=" + tip + ", linkSlike=" + linkSlike + "]";
	}
	
	
}
