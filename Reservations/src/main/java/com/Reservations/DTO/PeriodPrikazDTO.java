package com.Reservations.DTO;

import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.enums.TipRezervacije;

public class PeriodPrikazDTO 
{
	private long ID;
	private long enteitetID;
	private long rezervacijaID;
	private long vlasnikID;
	private String tipRezervacije;
	private String datumPocetka;
	private String datumKraja;
	
	public PeriodPrikazDTO()
	{
		
	}
	
	public PeriodPrikazDTO(Termin termin)
	{
		this.ID = termin.getID();
		if(termin.getRezervacija()!=null)
		{
			this.rezervacijaID = termin.getRezervacija().getID();
			this.tipRezervacije = termin.getRezervacija().getTip().toString();
		}
		this.vlasnikID = termin.getVlasnik().getID();
		this.datumPocetka = termin.getDatumVremePocetak();
		this.datumKraja = termin.getDatumVremeKraj();
		
		
		
		switch(termin.getTipEntiteta())
		{
			case vikendica:
			{
				this.enteitetID = termin.getVikendica().getID();
				break;
			}
			case brod:
			{
				this.enteitetID = termin.getBrod().getID();
				break;
			}
			case usluga:
			{
				this.enteitetID = termin.getUsluga().getID();
				break;
			}
			
		}
	}
	

	public void srediDatume()
	{
		String[] pocetni = this.getDatumPocetka().split("-");
		String[] krajnji = this.getDatumKraja().split("-");
		if(pocetni.length==3 && krajnji.length==3)
		{
			this.datumPocetka = pocetni[1]+"/"+pocetni[2]+"/"+pocetni[0];
			this.datumKraja = krajnji[1]+"/"+krajnji[2]+"/"+krajnji[0];
		}
	}

	public PeriodPrikazDTO(long iD, long enteitetID, long rezervacijaID, long vlasnikID, String datumPocetka,
			String datumKraja, TipRezervacije tipRezervacije) {
		super();
		ID = iD;
		this.enteitetID = enteitetID;
		this.rezervacijaID = rezervacijaID;
		this.vlasnikID = vlasnikID;
		this.datumPocetka = datumPocetka;
		this.datumKraja = datumKraja;
		this.tipRezervacije = tipRezervacije.toString();
	}
	
	public PeriodPrikazDTO(PeriodPrikazDTO p) {
		super();
		this.ID = p.ID;
		this.enteitetID = p.enteitetID;
		this.rezervacijaID = p.rezervacijaID;
		this.vlasnikID = p.vlasnikID;
		this.datumPocetka = p.datumPocetka;
		this.datumKraja = p.datumKraja;
		this.tipRezervacije = p.tipRezervacije;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public long getEnteitetID() {
		return enteitetID;
	}

	public void setEnteitetID(long enteitetID) {
		this.enteitetID = enteitetID;
	}

	public long getRezervacijaID() {
		return rezervacijaID;
	}

	public void setRezervacijaID(long rezervacijaID) {
		this.rezervacijaID = rezervacijaID;
	}

	public long getVlasnikID() {
		return vlasnikID;
	}

	public void setVlasnikID(long vlasnikID) {
		this.vlasnikID = vlasnikID;
	}

	public String getDatumPocetka() {
		return datumPocetka;
	}

	public void setDatumPocetka(String datumPocetka) {
		this.datumPocetka = datumPocetka;
	}

	public String getDatumKraja() {
		return datumKraja;
	}

	public void setDatumKraja(String datumKraja) {
		this.datumKraja = datumKraja;
	}

	public String getTipRezervacije() {
		return tipRezervacije;
	}

	public void setTipRezervacije(String tipRezervacije) {
		this.tipRezervacije = tipRezervacije;
	}

	@Override
	public String toString() {
		return "PeriodPrikazDTO [ID=" + ID + ", enteitetID=" + enteitetID + ", rezervacijaID=" + rezervacijaID
				+ ", vlasnikID=" + vlasnikID + ", tipRezervacije=" + tipRezervacije + ", datumPocetka=" + datumPocetka
				+ ", datumKraja=" + datumKraja + "]";
	}

	
	
}
