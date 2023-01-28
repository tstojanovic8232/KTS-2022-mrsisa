package com.Reservations.DTO;

public class PrihodDTO {

	private double vrednost;

	private long rezervacija;
	
	private String datum;

	public PrihodDTO(double vrednost, long rezervacija, String datum) {
		this.vrednost = vrednost;
		this.rezervacija = rezervacija;
		this.datum = datum;
	}

	public PrihodDTO() {
	}

	public double getVrednost() {
		return vrednost;
	}

	public void setVrednost(double vrednost) {
		this.vrednost = vrednost;
	}

	public long getRezervacija() {
		return rezervacija;
	}

	public void setRezervacija(long rezervacija) {
		this.rezervacija = rezervacija;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "PrihodDTO [vrednost=" + vrednost + ", rezervacija=" + rezervacija + ", datum=" + datum + "]";
	}
	
	
}
