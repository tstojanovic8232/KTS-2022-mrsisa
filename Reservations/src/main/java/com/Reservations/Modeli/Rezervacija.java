package com.Reservations.Modeli;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Modeli.enums.TipRezervacije;
@Entity
@Table(name="Rezervacije")
public class Rezervacija {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;

	@Column(name="entitet_id")
	private long entitetId;
	
	@Column(name="tip_entiteta")
	private TipEntiteta tipEntiteta;
	
	@Column(name="nazivEntiteta")
	private String nazivEntiteta;
	
	@Column(name="datum")
	private String datum;
	
	@Column(name="vreme")
	private String vreme;
	
	@Column(name="trajanje")
	private String trajanje;
	
	@Column(name="maxOsoba")
	private int maxOsoba;
	
	@Column(name="cena")
	private double cena;
	
	@Column(name="akcija")
	private double akcija;
	
	@ManyToOne
	@JoinColumn(name="klijent_id")
	private Korisnik klijent;
	
	@Column(name="tip")
	private TipRezervacije tip;
	
	@Column(name="izvjestaj")
	private String izvjestaj;
	
	@OneToOne
	@JoinColumn(name="termin_id")
	private Termin termin;
	
	public Rezervacija () {
		this.akcija = 1;
	}
	

	public Rezervacija(long iD, long entitetId, TipEntiteta tipEntiteta, String nazivEntiteta, String datum,
			String vreme, String trajanje, int maxOsoba, double cena, Korisnik klijent, TipRezervacije tip,
			String izvjestaj) 
	{
		super();
		ID = iD;
		this.entitetId = entitetId;
		this.tipEntiteta = tipEntiteta;
		this.nazivEntiteta = nazivEntiteta;
		this.datum = datum;
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.maxOsoba = maxOsoba;
		this.cena = cena;
		this.klijent = klijent;
		this.tip = tip;
		this.izvjestaj = izvjestaj;
		this.akcija = 1;
		this.termin = new Termin(this);
	}
	
	
	
	public Rezervacija(long iD, long entitetId, TipEntiteta tipEntiteta, String nazivEntiteta, String datum,
			String vreme, String trajanje, int maxOsoba, double cena, double akcija, Korisnik klijent,
			TipRezervacije tip, String izvjestaj, Termin termin) {
		super();
		ID = iD;
		this.entitetId = entitetId;
		this.tipEntiteta = tipEntiteta;
		this.nazivEntiteta = nazivEntiteta;
		this.datum = datum;
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.maxOsoba = maxOsoba;
		this.cena = cena;
		this.akcija = akcija;
		this.klijent = klijent;
		this.tip = tip;
		this.izvjestaj = izvjestaj;
		this.termin = termin;
	}


	public Rezervacija(long iD, long entitetId, TipEntiteta tipEntiteta, String nazivEntiteta, String datum,
			String vreme, String trajanje, int maxOsoba, double cena, Korisnik klijent, TipRezervacije tip,
			String izvjestaj, Termin termin) 
	{
		super();
		ID = iD;
		this.entitetId = entitetId;
		this.tipEntiteta = tipEntiteta;
		this.nazivEntiteta = nazivEntiteta;
		this.datum = datum;
		this.vreme = vreme;
		this.trajanje = trajanje;
		this.maxOsoba = maxOsoba;
		this.cena = cena;
		this.klijent = klijent;
		this.tip = tip;
		this.izvjestaj = izvjestaj;
		this.termin = termin;
		this.akcija = 1;
	}

	public Rezervacija(Rezervacija r) 
	{
		super();
		ID = r.ID;
		this.entitetId = r.entitetId;
		this.tipEntiteta = r.tipEntiteta;
		this.nazivEntiteta = r.nazivEntiteta;
		this.datum = r.datum;
		this.vreme = r.vreme;
		this.trajanje = r.trajanje;
		this.maxOsoba = r.maxOsoba;
		this.cena = r.cena;
		this.klijent = r.klijent;
		this.tip = r.tip;
		this.izvjestaj = r.izvjestaj;
		this.termin = new Termin(this);
		this.akcija = r.akcija;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}
	
	

	public long getEntitetId() {
		return entitetId;
	}

	public void setEntitetId(long entitetId) {
		this.entitetId = entitetId;
	}

	public TipEntiteta getTipEntiteta() {
		return tipEntiteta;
	}

	public void setTipEntiteta(TipEntiteta tipEntiteta) {
		this.tipEntiteta = tipEntiteta;
	}

	public String getNazivEntiteta() {
		return nazivEntiteta;
	}

	public void setNazivEntiteta(String nazivEntiteta) {
		this.nazivEntiteta = nazivEntiteta;
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

	public TipRezervacije getTip() {
		return tip;
	}

	public void setTip(TipRezervacije tip) {
		this.tip = tip;
	}

	public Korisnik getKlijent() {
		return klijent;
	}

	public void setKlijent(Korisnik klijent) {
		this.klijent = klijent;
	}
	
	

	public String getIzvjestaj() {
		return izvjestaj;
	}

	public void setIzvjestaj(String izvjestaj) {
		this.izvjestaj = izvjestaj;
	}
	
	

	public Termin getTermin() {
		return termin;
	}


	public void setTermin(Termin termin) {
		this.termin = termin;
	}

	

	public double getAkcija() {
		return akcija;
	}


	public void setAkcija(double akcija) {
		this.akcija = akcija;
	}


	@Override
	public String toString() {
		return "Rezervacija [ID=" + ID + ", entitetId=" + entitetId + ", tipEntiteta=" + tipEntiteta
				+ ", nazivEntiteta=" + nazivEntiteta + ", datum=" + datum + ", vreme=" + vreme + ", trajanje="
				+ trajanje + ", maxOsoba=" + maxOsoba + ", cena=" + cena + ", akcija=" + akcija + ", klijent=" + klijent
				+ ", tip=" + tip + ", izvjestaj=" + izvjestaj + ", termin=" + termin + "]";
	}


	




	 
	

	
	
		 
	
	
}
