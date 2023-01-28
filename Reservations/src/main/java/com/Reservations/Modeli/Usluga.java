package com.Reservations.Modeli;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.Reservations.Modeli.enums.TipoviUsluga;

@Entity
@Table(name="Usluge")
public class Usluga {
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
	
	@Column(name="naziv")
	private String naziv;
	
	@Column(name="adresa")
	private String adresa;
	
	@Column(name="opis")
	private String opis;
	
	@Column(name="biografijaInstruktora")
	private String biografijaInstruktora;
	
	@Column(name="maxOsoba")
	private int maxOsoba;
	
	@Column(name="pecaroskaOprema")
	private String pecaroskaOprema;
	
	@Column(name="cena")
	private double cena;
	
	@Column(name="tip")
	private TipoviUsluga tip;
	
	@ManyToOne
	@JoinColumn(name="instruktor_id")
	private Korisnik instruktor;
	
	@Column(name="linkSlike")
	private String linkSlike;
	
	@OneToMany
	private List<Termin> terminiZauzetosti;
	
	@ManyToMany
	@JoinTable(name="pretplateUsluga",joinColumns=@JoinColumn(name="usluga_id",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="klijent_id",referencedColumnName="id"))
	private List<Korisnik> pretplaceniKorisnici;
	public List<Korisnik> getPretplaceniKorisnici() {
		return pretplaceniKorisnici;
	}

	public void setPretplaceniKorisnici(List<Korisnik> pretplaceniKorisnici) {
		this.pretplaceniKorisnici = pretplaceniKorisnici;
	}

	public Usluga() {
		this.terminiZauzetosti = new ArrayList<Termin>();
	}
	
	public Usluga(long iD, String naziv, String adresa, String opis, String biografijaInstruktora, int maxOsoba,
			String pecaroskaOprema, double cena, Korisnik instruktor,String linkSlike) {
		super();
		ID = iD;
		this.naziv = naziv;
		this.adresa = adresa;
		this.opis = opis;
		this.biografijaInstruktora = biografijaInstruktora;
		this.maxOsoba = maxOsoba;
		this.pecaroskaOprema = pecaroskaOprema;
		this.cena = cena;
		this.instruktor = instruktor;
		this.linkSlike=linkSlike;
		this.terminiZauzetosti = new ArrayList<Termin>();
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}
	
	public String getBiografijaInstruktora() {
		return biografijaInstruktora;
	}

	public void setBiografijaInstruktora(String biografijaInstruktora) {
		this.biografijaInstruktora = biografijaInstruktora;
	}

	public int getMaxOsoba() {
		return maxOsoba;
	}

	public void setMaxOsoba(int maxOsoba) {
		this.maxOsoba = maxOsoba;
	}

	public String getPecaroskaOprema() {
		return pecaroskaOprema;
	}

	public void setPecaroskaOprema(String pecaroskaOprema) {
		this.pecaroskaOprema = pecaroskaOprema;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}
	

	public TipoviUsluga getTip() {
		return tip;
	}

	public void setTip(TipoviUsluga tip) {
		this.tip = tip;
	}


	public Korisnik getInstruktor() {
		return instruktor;
	}

	public void setInstruktor(Korisnik instruktor) {
		this.instruktor = instruktor;
	}

	
	public String getLinkSlike() {
		return linkSlike;
	}

	public void setLinkSlike(String linkSlike) {
		this.linkSlike = linkSlike;
	}

	public List<Termin> getTerminiZauzetosti() {
		return terminiZauzetosti;
	}

	public void setTerminiZauzetosti(List<Termin> terminiZauzetosti) {
		this.terminiZauzetosti = terminiZauzetosti;
	}

	@Override
	public String toString() {
		return "Usluga [ID=" + ID + ", naziv=" + naziv + ", adresa=" + adresa + ", opis=" + opis
				+ ", biografijaInstruktora=" + biografijaInstruktora + ", maxOsoba=" + maxOsoba + ", pecaroskaOprema="
				+ pecaroskaOprema + ", cena=" + cena + ", tip=" + tip + ", instruktor=" + instruktor + ", linkSlike="
				+ linkSlike + ", terminiZauzetosti=" + terminiZauzetosti + "]";
	}
	
	

	
	
}
