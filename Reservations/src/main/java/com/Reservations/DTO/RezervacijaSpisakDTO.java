package com.Reservations.DTO;

import com.Reservations.Modeli.Rezervacija;

public class RezervacijaSpisakDTO 
{
	Long rezervacijaID;
	String nazivEntiteta;
	String datum;
	String vreme;
	double cena;
	String klijent;//Ime+prezime
	Long klijentID;
	String tipEntiteta;
	String tip;
	
	public RezervacijaSpisakDTO() {}
	
	public RezervacijaSpisakDTO(Rezervacija rezervacija)
	{
		this.rezervacijaID = rezervacija.getID();
		this.nazivEntiteta = rezervacija.getNazivEntiteta();
		this.datum = rezervacija.getDatum();
		this.vreme = rezervacija.getVreme();
		this.cena = rezervacija.getCena();
		this.klijent = rezervacija.getKlijent().getIme()+" "+rezervacija.getKlijent().getPrezime();
		this.klijentID = rezervacija.getKlijent().getID();
	}
	
	
	
	public RezervacijaSpisakDTO(Long rezervacijaID, String naziv, String datum, String vreme, double cena, String klijent, Long klijentID) {
		super();
		this.rezervacijaID = rezervacijaID;
		this.nazivEntiteta = naziv;
		this.datum = datum;
		this.vreme = vreme;
		this.cena = cena;
		this.klijent = klijent;
		this.klijentID = klijentID;
	}
	
	
	public Long getRezervacijaID() {
		return rezervacijaID;
	}

	public void setRezervacijaID(Long rezervacijaID) {
		this.rezervacijaID = rezervacijaID;
	}

	public String getNazivEntiteta() {
		return nazivEntiteta;
	}
	public void setNazivEntiteta(String naziv) {
		this.nazivEntiteta = naziv;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getVreme() {
		return vreme;
	}
	public void setVreme(String vreme) {
		this.vreme = vreme;
	}
	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		this.cena = cena;
	}
	public String getKlijent() {
		return klijent;
	}
	public void setKlijent(String klijent) {
		this.klijent = klijent;
	}
	public Long getKlijentID() {
		return klijentID;
	}
	public void setKlijentID(Long klijentID) {
		this.klijentID = klijentID;
	}

	public String getTipEntiteta() {
		return tipEntiteta;
	}

	public void setTipEntiteta(String tipEntiteta) {
		this.tipEntiteta = tipEntiteta;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	@Override
	public String toString() {
		return "RezervacijaSpisakDTO [rezervacijaID=" + rezervacijaID + ", nazivEntiteta=" + nazivEntiteta + ", datum="
				+ datum + ", vreme=" + vreme + ", cena=" + cena + ", klijent=" + klijent + ", klijentID=" + klijentID
				+ "]";
	}
	
	
}
