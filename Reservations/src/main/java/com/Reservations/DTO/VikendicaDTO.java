package com.Reservations.DTO;

import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Vikendica;

public class VikendicaDTO 
{
	private long ID;	

	private String naziv;
	
	

	private String adresa;
	
	

	private String opis;


	private int brojSoba;
	

	private int brojKreveta;
	
	private String linkSlike;
	
	private String linkInterijera;
	
	private String dodatneUsluge;
	
	private String pravilaPonasanja;
	
	
	private double cena;
	
	private Long vlasnik;

	
	public VikendicaDTO(Vikendica vikendica) 
	{
		this.ID = vikendica.getID();
		this.naziv = vikendica.getNaziv();
		this.adresa = vikendica.getAdresa();
		this.opis = vikendica.getOpis();
		this.brojSoba = vikendica.getBrojSoba();
		this.brojKreveta = vikendica.getBrojKreveta();
		this.cena = vikendica.getCena();
		this.vlasnik = vikendica.getVlasnik().getID();
		this.linkSlike = vikendica.getLinkSlike();
		this.dodatneUsluge = vikendica.getDodatneUsluge();
		this.pravilaPonasanja = vikendica.getPravilaPonasanja();
		this.linkInterijera = vikendica.getLinkInterijera();
	}
	
	

	public VikendicaDTO() 
	{
		this.naziv = "";
		this.adresa = "";
		this.opis = "";
		//this.brojSoba = 0;
		//this.brojKreveta = 0;
		//this.cena = 0;
		//this.vlasnik = 0L;
		this.linkSlike = "";
		this.linkInterijera = "";
		this.pravilaPonasanja ="";
		this.dodatneUsluge = "";
	}

	@Override
	public String toString() {
		return "VikendicaDTO [ID=" + ID + ", naziv=" + naziv + ", adresa=" + adresa + ", opis=" + opis + ", brojSoba="
				+ brojSoba + ", brojKreveta=" + brojKreveta + ", cena=" + cena + ", vlasnik=" + vlasnik + "]";
	}

	public VikendicaDTO(long iD, String naziv, String adresa, String opis, int brojSoba, int brojKreveta, double cena,
			Long vlasnik, String linkSlike) {
		super();
		ID = iD;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.brojSoba = brojSoba;
		this.brojKreveta = brojKreveta;
		this.cena = cena;
		this.vlasnik = vlasnik;
		this.linkSlike = linkSlike;
	}
	
	public VikendicaDTO(long iD, String naziv, String adresa, String opis, int brojSoba, int brojKreveta, double cena,
			Korisnik vlasnik, String linkSlike) {
		super();
		ID = iD;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.brojSoba = brojSoba;
		this.brojKreveta = brojKreveta;
		this.cena = cena;
		this.vlasnik = vlasnik.getID();
		this.linkSlike = linkSlike;
	}
	
	public VikendicaDTO(long iD, String naziv, String adresa, String opis, int brojSoba, int brojKreveta, double cena,
			Long vlasnik, String linkSlike, String linkSlikeInterijera, String dodatneUsluge, String pravilaPonasanja) {
		super();
		ID = iD;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.brojSoba = brojSoba;
		this.brojKreveta = brojKreveta;
		this.cena = cena;
		this.vlasnik = vlasnik;
		this.linkSlike = linkSlike;
		this.linkInterijera = linkSlikeInterijera;
		this.dodatneUsluge = dodatneUsluge;
		this.pravilaPonasanja = pravilaPonasanja;
	}
	
	
	public VikendicaDTO(long iD, String naziv, String adresa, String opis, int brojSoba, int brojKreveta, double cena,
			Korisnik vlasnik, String linkSlike, String linkSlikeInterijera, String dodatneUsluge, String pravilaPonasanja) {
		super();
		ID = iD;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.brojSoba = brojSoba;
		this.brojKreveta = brojKreveta;
		this.cena = cena;
		this.vlasnik = vlasnik.getID();
		this.linkSlike = linkSlike;
		this.linkInterijera = linkSlikeInterijera;
		this.dodatneUsluge = dodatneUsluge;
		this.pravilaPonasanja = pravilaPonasanja;
	}
	

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
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

	public int getBrojSoba() {
		return brojSoba;
	}

	public void setBrojSoba(int brojSoba) {
		this.brojSoba = brojSoba;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
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



	public String getLinkInterijera() {
		return linkInterijera;
	}



	public void setLinkInterijera(String linkSlikeInterijera) {
		this.linkInterijera = linkSlikeInterijera;
	}



	public String getDodatneUsluge() {
		return dodatneUsluge;
	}



	public void setDodatneUsluge(String dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}



	public String getPravilaPonasanja() {
		return pravilaPonasanja;
	}



	public void setPravilaPonasanja(String pravilaPonasanja) {
		this.pravilaPonasanja = pravilaPonasanja;
	}
	
	
	
}
