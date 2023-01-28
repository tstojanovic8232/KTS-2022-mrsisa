package com.Reservations.Modeli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="Korisnik")

public  class Korisnik implements UserDetails {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	@Column(name = "korisnickoIme")
	private String korisnickoIme;
	@Column(name = "ime")
	private String ime;
	@Column(name = "prezime")
	private String prezime;
	@Column(name = "email")
	private String email;
	@Column(name = "lozinka")
	private String lozinka;
	@Column(name = "adresa")
	private String adresa;
	@Column(name = "grad")
	private String grad;
	@Column(name = "drzava")
	private String drzava;
	@Column(name = "brojTel")
	private String brojTel;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uloga_id")
	private Uloga uloga;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name ="user_authority", joinColumns= @JoinColumn(name ="user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Uloga> uloge;
//	@Column(name = "poslednjiDatumPromeneLozinke")
//	private Date poslednjiDatumPromeneLozinke;
	@Column
	private boolean enabled;//TODO:progledati poslije
	
	@Column(name="link_slike")
	private String linkSlike;
	
	public Korisnik()
	{}
	
	
	public Korisnik(long iD, String korisnickoIme, String ime, String prezime, String email, String lozinka,
			String adresa, String grad, String drzava, String brojTel, Uloga uloga) {
		super();
		ID = iD;
		this.korisnickoIme = korisnickoIme;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTel = brojTel;
		this.uloga = uloga;
		this.uloge.add(uloga);
	}
	
	public Korisnik(long iD, String korisnickoIme, String ime, String prezime, String email, String lozinka,
			String adresa, String grad, String drzava, String brojTel, Uloga uloga, String linkSlike) {
		super();
		ID = iD;
		this.korisnickoIme = korisnickoIme;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.adresa = adresa;
		this.grad = grad;
		this.drzava = drzava;
		this.brojTel = brojTel;
		this.uloga = uloga;
		this.uloge.add(uloga);
		this.linkSlike = linkSlike;
	}

	public String getLinkSlike() {
		return linkSlike;
	}


	public void setLinkSlike(String linkSlike) {
		this.linkSlike = linkSlike;
	}


	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getDrzava() {
		return drzava;
	}

	public void setDrzava(String drzava) {
		this.drzava = drzava;
	}

	public String getBrojTel() {
		return brojTel;
	}

	public void setBrojTel(String brojTel) {
		this.brojTel = brojTel;
	}
	
	

	public Uloga getUloga() {
		return uloga;
	}


	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	

	

//	public Date getPoslednjiDatumPromeneLozinke() {
//		return poslednjiDatumPromeneLozinke;
//	}
//
//
//	public void setPoslednjiDatumPromeneLozinke(Date poslednjiDatumPromeneLozinke) {
//		this.poslednjiDatumPromeneLozinke = poslednjiDatumPromeneLozinke;
//	}


	public List<Uloga> getUloge() {
		return uloge;
	}


	public void setUloge(List<Uloga> uloge) {
		this.uloge = uloge;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public String toString() {
		return "Korisnik [ID=" + ID + ", korisnickoIme=" + korisnickoIme + ", ime=" + ime + ", prezime=" + prezime
				+ ", email=" + email + ", lozinka=" + lozinka + ", adresa=" + adresa + ", grad=" + grad + ", drzava="
				+ drzava + ", brojTel=" + brojTel + ", uloga="+ uloga.toString() +"]";
	}


	public Date getPoslednjiDatumPromeneLozinke() {
		return null;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() 
	{
		List<Uloga> uloga = new ArrayList<Uloga>();
		uloga.add(this.uloga);
		return uloga;
	}
	
	


	@Override
	public String getPassword() {
		return this.lozinka;
	}


	@Override
	public String getUsername() {
		return this.korisnickoIme;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
	
	
}
