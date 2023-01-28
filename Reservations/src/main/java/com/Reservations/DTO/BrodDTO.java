package com.Reservations.DTO;

import com.Reservations.Modeli.Brod;

public class BrodDTO 
{
	private Long ID;
	private double cena; //ima-ima
	private double maxBrzina; //ima-ima
	private String adresa; //ima
	private String tip; //ima
	private String navigacionaOprema;
	private String brojMotora; //ima-ima
	private String naziv; //ima
	private int snaga; //ima-ima
	private double duzina; //ima-ima
	private String kapacitet; //ima-ima
	private String pecaroskaOprema; //ima
	private String opis; //ima
	private Long vlasnik; //ima
	private String linkSlike; //ima
	private String linkKabine; //ima
	private String pravilaPonasanja; //ima
	
	public BrodDTO() {}
	
	public BrodDTO(Brod brod)
	{
		ID = brod.getID();
		this.cena = brod.getCena();
		this.maxBrzina = brod.getMaxBrzina();
		this.adresa = brod.getAdresa();
		this.tip = brod.getTip();
		this.navigacionaOprema = brod.getNavigacionaOprema();
		
		this.naziv = brod.getNaziv();
		this.snaga = brod.getSnaga();
		this.duzina = brod.getDuzina();
		this.pecaroskaOprema = brod.getPecaroskaOprema();
		this.opis = brod.getOpis();
		this.vlasnik = brod.getVlasnik().getID();
		this.linkSlike = brod.getLinkSlike();
		this.linkKabine = brod.getLinkKabine();
		this.pravilaPonasanja = brod.getPravilaPonasanja();
		//TODO: String - Integer?
		this.brojMotora = brod.getBrojMotora();
		this.kapacitet = brod.getKapacitet();
	}

	public BrodDTO(Long iD, double cena, double maxBrzina, String adresa, String tip, String navigacionaOprema,
			String brojMotora, String naziv, int snaga, double duzina, String kapacitet, String pecaroskaOprema, String opis,
			Long vlasnik, String linkSlike, String linkKabine, String pravilaPonasanja) {
		super();
		ID = iD;
		this.cena = cena;
		this.maxBrzina = maxBrzina;
		this.adresa = adresa;
		this.tip = tip;
		this.navigacionaOprema = navigacionaOprema;
		this.brojMotora = brojMotora;
		this.naziv = naziv;
		this.snaga = snaga;
		this.duzina = duzina;
		this.kapacitet = kapacitet;
		this.pecaroskaOprema = pecaroskaOprema;
		this.opis = opis;
		this.vlasnik = vlasnik;
		this.linkSlike = linkSlike;
		this.linkKabine = linkKabine;
		this.pravilaPonasanja = pravilaPonasanja;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public double getMaxBrzina() {
		return maxBrzina;
	}

	public void setMaxBrzina(double maxBrzina) {
		this.maxBrzina = maxBrzina;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getNavigacionaOprema() {
		return navigacionaOprema;
	}

	public void setNavigacionaOprema(String navigacionaOprema) {
		this.navigacionaOprema = navigacionaOprema;
	}

	public String getBrojMotora() {
		return brojMotora;
	}

	public void setBrojMotora(String brojMotora) {
		this.brojMotora = brojMotora;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getSnaga() {
		return snaga;
	}

	public void setSnaga(int snaga) {
		this.snaga = snaga;
	}

	public String getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(String kapacitet) {
		this.kapacitet = kapacitet;
	}

	public String getPecaroskaOprema() {
		return pecaroskaOprema;
	}

	public void setPecaroskaOprema(String pecaroskaOprema) {
		this.pecaroskaOprema = pecaroskaOprema;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Long getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Long vlasnik) {
		this.vlasnik = vlasnik;
	}

	public String getLinkSlike() {
		return linkSlike;
	}

	public void setLinkSlike(String linkSlike) {
		this.linkSlike = linkSlike;
	}

	public String getLinkKabine() {
		return linkKabine;
	}

	public void setLinkKabine(String linkKabine) {
		this.linkKabine = linkKabine;
	}

	public String getPravilaPonasanja() {
		return pravilaPonasanja;
	}

	public void setPravilaPonasanja(String pravilaPonasanja) {
		this.pravilaPonasanja = pravilaPonasanja;
	}

	public double getDuzina() {
		return duzina;
	}

	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}
	
	
	

}
