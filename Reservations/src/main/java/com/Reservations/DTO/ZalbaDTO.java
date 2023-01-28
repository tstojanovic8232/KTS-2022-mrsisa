package com.Reservations.DTO;

import com.Reservations.Modeli.Rezervacija;

public class ZalbaDTO {

	
	private String naziv_klijenta;
	private String zalba;
	private long rez_id;
	@Override
	public String toString() {
		return "ZalbaDTO [ naziv_klijenta=" + naziv_klijenta + ", zalba=" + zalba + ", rez_id=" + rez_id
				+ "]";
	}
	public ZalbaDTO( String naziv_klijenta, String zalba, long rez_id) {
		super();
		
		this.naziv_klijenta = naziv_klijenta;
		this.zalba = zalba;
		this.rez_id = rez_id;
	}
	
	public String getNaziv_klijenta() {
		return naziv_klijenta;
	}
	public void setNaziv_klijenta(String naziv_klijenta) {
		this.naziv_klijenta = naziv_klijenta;
	}
	public String getZalba() {
		return zalba;
	}
	public void setZalba(String zalba) {
		this.zalba = zalba;
	}
	public long getRez_id() {
		return rez_id;
	}
	public void setRez_id(long rez_id) {
		this.rez_id = rez_id;
	}
	
	
	
	
	
}
