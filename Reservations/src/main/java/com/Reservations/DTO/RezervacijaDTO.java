package com.Reservations.DTO;

public class RezervacijaDTO {

	
	
	private String datum;
	private String vreme;
	private String trajanje;
	private int maxOsoba;

	public RezervacijaDTO(String datum, String vreme, String trajanje, int maxOsoba
			) {
		super();
		
	
		this.datum = datum;
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.maxOsoba = maxOsoba;
		
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
	@Override
	public String toString() {
		return "RezervacijaDTO [ datum=" + datum + ", vreme=" + vreme + ", trajanje=" + trajanje
				+ ", maxOsoba=" + maxOsoba + "]";
	}
	
	


	
	
	
	
	
}
