package com.Reservations.DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.Reservations.Modeli.Rezervacija;

public class IzvjestajRezervacijaDTO 
{
	Long rezervacijaID;
	String nazivEntiteta;
	String datumPocetka;
	String datumKraja;
	double cena;
	String klijent;//Ime+prezime
	Long klijentID;
	String izvjestaj;
	
	public IzvjestajRezervacijaDTO() {}
	
	public IzvjestajRezervacijaDTO(Rezervacija rezervacija)
	{
		this.rezervacijaID = rezervacija.getID();
		this.nazivEntiteta = rezervacija.getNazivEntiteta();
		this.datumPocetka = rezervacija.getDatum();
		
		this.cena = rezervacija.getCena();
		this.klijent = rezervacija.getKlijent().getIme()+" "+rezervacija.getKlijent().getPrezime();
		this.klijentID = rezervacija.getKlijent().getID();
		this.izvjestaj = rezervacija.getIzvjestaj();
		this.datumKraja = this.izracunajDatumKraja(rezervacija.getTrajanje());
	}
	
	
	
	public IzvjestajRezervacijaDTO(Long rezervacijaID, String naziv, String datum, String vreme, double cena, String klijent, Long klijentID) {
		super();
		this.rezervacijaID = rezervacijaID;
		this.nazivEntiteta = naziv;
		this.datumPocetka = datum;
		this.datumKraja = vreme;
		this.cena = cena;
		this.klijent = klijent;
		this.klijentID = klijentID;
		this.izvjestaj = "";
	}
	
	
	public IzvjestajRezervacijaDTO(Long rezervacijaID, String nazivEntiteta, String datumPocetka, String datumKraja,
			double cena, String klijent, Long klijentID, String izvjestaj) {
		super();
		this.rezervacijaID = rezervacijaID;
		this.nazivEntiteta = nazivEntiteta;
		this.datumPocetka = datumPocetka;
		this.datumKraja = datumKraja;
		this.cena = cena;
		this.klijent = klijent;
		this.klijentID = klijentID;
		this.izvjestaj = izvjestaj;
	}

	private String izracunajDatumKraja(String period)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String datumKraja;
		try
		{
			Long trajanje = Long.parseLong(period);
			LocalDate kraj = LocalDate.parse(this.getDatumPocetka(), dtf).plusDays(trajanje);
			System.out.println("Trajanje: "+ trajanje+" kraj: "+ kraj.format(dtf));
			datumKraja = kraj.format(dtf);
		}
		catch(Exception e)
		{
			datumKraja = this.datumPocetka;
		}
		return datumKraja;
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
	public String getDatumPocetka() {
		return datumPocetka;
	}
	public void setDatumPocetka(String datum) {
		this.datumPocetka = datum;
	}
	public String getDatumKraja() {
		return datumKraja;
	}
	public void setDatumKraja(String vreme) {
		this.datumKraja = vreme;
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

	
	public String getIzvjestaj() {
		return izvjestaj;
	}

	public void setIzvjestaj(String izvjestaj) {
		this.izvjestaj = izvjestaj;
	}

	@Override
	public String toString() {
		return "RezervacijaSpisakDTO [rezervacijaID=" + rezervacijaID + ", nazivEntiteta=" + nazivEntiteta + ", datum="
				+ datumPocetka + ", vreme=" + datumKraja + ", cena=" + cena + ", klijent=" + klijent + ", klijentID=" + klijentID
				+ "]";
	}
	
	
}
