
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

@Entity
@Table(name="Brodovi")
public class Brod {
	
	@Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
	
	@Column(name="naziv")
    private String naziv;
	
	@Column(name="tip")
    private String tip;
	
	@Column(name="duzina")
    private double duzina;
	
	@Column(name="brojMotora")
    private String brojMotora;
	
    @Column(name="snaga")
    private int snaga;
	
    @Column(name="maxBrzina")
    private double maxBrzina;
	
    @Column(name="adresa")
    private String adresa;
	
    @Column(name="opis")
    private String opis;
	
    @Column(name="cena")
    private double cena;
	
    @Column(name="navigacionaOprema")
    private String navigacionaOprema;
	
    @Column(name="kapacitet")
    private String kapacitet;
	
    @Column(name="pecaroskaOprema")
    private String pecaroskaOprema;
	
    @ManyToOne
    @JoinColumn(name="vlasnik_id")
    private Korisnik vlasnik;
	
	
    @Column(name="linkSlike")
    private String linkSlike;
	
	@Column(name="pravila_ponasanja")
	private String pravilaPonasanja;
	
	@Column(name="link_kabine")
	private String linkKabine;
	
	@OneToMany
	private List<Termin> terminiZauzetosti;
	
	@ManyToMany
	@JoinTable(name="pretplateBrod",joinColumns=@JoinColumn(name="brod_id",referencedColumnName="id"),inverseJoinColumns=@JoinColumn(name="klijent_id",referencedColumnName="id"))
	private List<Korisnik> pretplaceniKorisnici;
	
	public List<Korisnik> getPretplaceniKorisnici() {
		return pretplaceniKorisnici;
	}

	public void setPretplaceniKorisnici(List<Korisnik> pretplaceniKorisnici) {
		this.pretplaceniKorisnici = pretplaceniKorisnici;
	}

	public Brod() {
		this.terminiZauzetosti = new ArrayList<Termin>();
	}

	public Brod(long iD, double cena, double maxBrzina, String adresa, String tip, String navigacionaOprema,
			String brojMotora, String naziv, int snaga, String kapacitet, String pecaroskaOprema, double duzina,
			String opis, Korisnik vlasnik,String linkSlike) {
		super();
		this.ID = iD;
		this.cena = cena;
		this.maxBrzina = maxBrzina;
		this.adresa = adresa;
		this.tip = tip;
		this.navigacionaOprema = navigacionaOprema;
		this.brojMotora = brojMotora;
		this.naziv = naziv;
		this.snaga = snaga;
		this.kapacitet = kapacitet;
		this.pecaroskaOprema = pecaroskaOprema;
		this.duzina = duzina;
		this.opis = opis;
		this.vlasnik = vlasnik;
		this.linkSlike=linkSlike;
		this.terminiZauzetosti = new ArrayList<Termin>();
	}
	
	
	public Brod(long iD, double cena, double maxBrzina, String adresa, String tip, String navigacionaOprema,
			String brojMotora, String naziv, int snaga, String kapacitet, String pecaroskaOprema, double duzina,
			String opis, Korisnik vlasnik,String linkSlike, String pravilaPonasanja) {
		super();
		this.ID = iD;
		this.cena = cena;
		this.maxBrzina = maxBrzina;
		this.adresa = adresa;
		this.tip = tip;
		this.navigacionaOprema = navigacionaOprema;
		this.brojMotora = brojMotora;
		this.naziv = naziv;
		this.snaga = snaga;
		this.kapacitet = kapacitet;
		this.pecaroskaOprema = pecaroskaOprema;
		this.duzina = duzina;
		this.opis = opis;
		this.vlasnik = vlasnik;
		this.linkSlike=linkSlike;
		this.pravilaPonasanja = pravilaPonasanja;
		this.terminiZauzetosti = new ArrayList<Termin>();
	}
	
	public Brod(long iD, double cena, double maxBrzina, String adresa, String tip, String navigacionaOprema,
			String brojMotora, String naziv, int snaga, String kapacitet, String pecaroskaOprema, double duzina,
			String opis, Korisnik vlasnik,String linkSlike, String linkKabine, String pravilaPonasanja) {
		super();
		this.ID = iD;
		this.cena = cena;
		this.maxBrzina = maxBrzina;
		this.adresa = adresa;
		this.tip = tip;
		this.navigacionaOprema = navigacionaOprema;
		this.brojMotora = brojMotora;
		this.naziv = naziv;
		this.snaga = snaga;
		this.kapacitet = kapacitet;
		this.pecaroskaOprema = pecaroskaOprema;
		this.duzina = duzina;
		this.opis = opis;
		this.vlasnik = vlasnik;
		this.linkSlike=linkSlike;
		this.linkKabine=linkKabine;
		this.pravilaPonasanja=pravilaPonasanja;
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

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public double getDuzina() {
		return duzina;
	}

	public void setDuzina(double duzina) {
		this.duzina = duzina;
	}

	public String getBrojMotora() {
		return brojMotora;
	}

	public void setBrojMotora(String brojMotora) {
		this.brojMotora = brojMotora;
	}

	public int getSnaga() {
		return snaga;
	}

	public void setSnaga(int snaga) {
		this.snaga = snaga;
	}

	public double getMaxBrzina() {
		return maxBrzina;
	}

	public void setMaxBrzina(double maxBrzina) {
		this.maxBrzina = maxBrzina;
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

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public String getNavigacionaOprema() {
		return navigacionaOprema;
	}

	public void setNavigacionaOprema(String navigacionaOprema) {
		this.navigacionaOprema = navigacionaOprema;
	}

	public String getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(String kapacitet) {
		this.kapacitet = kapacitet;
	}

	public String getPecaroskaOprema() {
		return pecaroskaOprema;
	}

	public void setPecaroskaOprema(String pecaroskaOprema) {
		this.pecaroskaOprema = pecaroskaOprema;
	}

	public Korisnik getVlasnik() {
		return vlasnik;
	}

	public void setVlasnik(Korisnik vlasnik) {
		this.vlasnik = vlasnik;
	}
	

	public String getLinkSlike() {
		return linkSlike;
	}

	public void setLinkSlike(String linkSlike) {
		this.linkSlike = linkSlike;
	}
	
	

	public String getPravilaPonasanja() {
		return pravilaPonasanja;
	}

	public void setPravilaPonasanja(String pravilaPonasanja) {
		this.pravilaPonasanja = pravilaPonasanja;
	}

	public String getLinkKabine() {
		return linkKabine;
	}

	public void setLinkKabine(String linkKabine) {
		this.linkKabine = linkKabine;
	}

	
	public List<Termin> getTerminiZauzetosti() {
		return terminiZauzetosti;
	}

	public void setTerminiZauzetosti(List<Termin> terminiZauzetosti) {
		this.terminiZauzetosti = terminiZauzetosti;
	}

	@Override
	public String toString() {
		return "Brod [ID=" + ID + ", naziv=" + naziv + ", tip=" + tip + ", duzina=" + duzina + ", brojMotora="
				+ brojMotora + ", snaga=" + snaga + ", maxBrzina=" + maxBrzina + ", adresa=" + adresa + ", opis=" + opis
				+ ", cena=" + cena + ", navigacionaOprema=" + navigacionaOprema + ", kapacitet=" + kapacitet
				+ ", pecaroskaOprema=" + pecaroskaOprema + ", vlasnik=" + vlasnik + ", linkSlike=" + linkSlike
				+ ", pravilaPonasanja=" + pravilaPonasanja + ", linkKabine=" + linkKabine + ", terminiZauzetosti="
				+ terminiZauzetosti + "]";
	}


	

	
	
	
}
