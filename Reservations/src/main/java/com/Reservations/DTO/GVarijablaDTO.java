package com.Reservations.DTO;

import javax.persistence.Column;

public class GVarijablaDTO {
private long ID;
	
	private String ime;
	
	private String vrednost;


	public GVarijablaDTO(long iD, String ime, String vrednost) {
		super();
		ID = iD;
		this.ime = ime;
		this.vrednost = vrednost;
	}

	public GVarijablaDTO() {
		// TODO Auto-generated constructor stub
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getVrednost() {
		return vrednost;
	}

	public void setVrednost(String vrednost) {
		this.vrednost = vrednost;
	}
}
