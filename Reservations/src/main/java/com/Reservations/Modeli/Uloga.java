package com.Reservations.Modeli;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Uloga")
public class Uloga  implements GrantedAuthority{
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="ime")
	private String ime;
	
	public Uloga() {
		
	}
	
	public Uloga(long id, String ime) {
		super();
		this.id = id;
		this.ime = ime;
	}
	@JsonIgnore
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@JsonIgnore
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}

	@Override
	public String toString() {
		return "Uloga [id=" + id + ", ime=" + ime + "]";
	}

	@Override
	public String getAuthority() {
		return this.ime;
	}
	
	
	

}
