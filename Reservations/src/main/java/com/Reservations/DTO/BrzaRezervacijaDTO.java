package com.Reservations.DTO;

import com.Reservations.Modeli.Rezervacija;

public class BrzaRezervacijaDTO {
	private Long entitetId; //entitet ID

	private String datum;
	
	private String vreme;
	
	private String trajanje;
	
	private int maxOsoba;
	
	private double cena;
	
	private double akcija;

	public BrzaRezervacijaDTO(Long entitetId, String datum, String vreme, String trajanje, int maxOsoba, double cena,
			double akcija) {

		this.entitetId = entitetId;
		this.datum = datum;
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.maxOsoba = maxOsoba;
		this.cena = cena;
		this.akcija = akcija;
	}
	
	public BrzaRezervacijaDTO(Rezervacija rez) {
		this.entitetId = rez.getEntitetId();
		this.datum = rez.getDatum();
		this.vreme = rez.getVreme();
		this.trajanje = rez.getTrajanje();
		this.maxOsoba = rez.getMaxOsoba();
		this.cena = rez.getCena();
		this.akcija = rez.getAkcija();
	}

	public BrzaRezervacijaDTO() {
	}

	public Long getEntitetId() {
		return entitetId;
	}

	public void setEntitetId(Long entitetId) {
		this.entitetId = entitetId;
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

	public String getTrajanje() {
		return trajanje;
	}

	public void setTrajanje(String trajanje) {
		this.trajanje = trajanje;
	}

	public int getMaxOsoba() {
		return maxOsoba;
	}

	public void setMaxOsoba(int maxOsoba) {
		this.maxOsoba = maxOsoba;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public double getAkcija() {
		return akcija;
	}

	public void setAkcija(double akcija) {
		this.akcija = akcija;
	}

	@Override
	public String toString() {

		return "BrzaRezervacijaDTO [entitetId=" + entitetId + ", datum=" + datum + ", vreme=" + vreme + ", trajanje=" + trajanje
				+ ", maxOsoba=" + maxOsoba + ", cena=" + cena + ", akcija=" + akcija + "]";
	}
	
	public void srediDatume()
	{
		String[] pocetni = this.getDatum().split("-");
		if(pocetni.length==3)
		{
			this.datum = pocetni[1]+"/"+pocetni[2]+"/"+pocetni[0];
			
		}
	}
	
	public void srediDatumeZaUpis()
	{
		String[] pocetni = this.getDatum().split("/");
		if(pocetni.length==3)
		{
			this.datum = pocetni[2]+"-"+pocetni[0]+"-"+pocetni[1];
			
		}
	}
}
