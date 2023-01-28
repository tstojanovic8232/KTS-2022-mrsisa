package com.Reservations.Modeli;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.Reservations.DTO.BrisanjeKorisnikaZahtjevDTO;
import com.Reservations.DTO.RegistracijaVlasnikaInstruktoraDTO;
import com.Reservations.Modeli.enums.TipRegistracije;
@Entity
@Table(name = "zahtevi_brisanje")
public class ZahtevZaBrisanje {

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
	
	// napomena: na osnovu polja tipRegistracije određujemo ulogu -> indeks u enumu mapiramo na odgovarajuću ulogu
	
		@Column(name="razlog")
		private String razlogRegistracije;

		public ZahtevZaBrisanje() {
			super();
		}

		public ZahtevZaBrisanje(long iD, String korisnickoIme, String ime, String prezime, String email,
				String lozinka, String adresa, String grad, String drzava, String brojTel, Uloga uloga,
				 String razlogRegistracije) {
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
			
			this.razlogRegistracije = razlogRegistracije;
		}

		public ZahtevZaBrisanje(RegistracijaVlasnikaInstruktoraDTO regRequest) {
			this.korisnickoIme = regRequest.getUsername();
			this.ime = regRequest.getFirstName();
			this.prezime = regRequest.getLastName();
			this.email = regRequest.getEmail();
			this.lozinka = regRequest.getPassword();
			this.adresa = regRequest.getAddress();
			this.grad = regRequest.getCity();
			this.drzava = regRequest.getCountry();
			this.brojTel = regRequest.getPhone();
			
			this.razlogRegistracije = regRequest.getRegisterReason();
		}

		public ZahtevZaBrisanje(BrisanjeKorisnikaZahtjevDTO zahtjev) {
			
			this.korisnickoIme = zahtjev.getKorisnickoIme();
			this.ime = zahtjev.getIme();
			this.prezime = zahtjev.getPrezime();
			this.email = zahtjev.getEmail();
			this.lozinka = zahtjev.getLozinka();
			this.adresa = zahtjev.getAdresa();
			this.grad = zahtjev.getGrad();
			this.drzava = zahtjev.getDrzava();
			this.brojTel = zahtjev.getBrojTel();
			this.razlogRegistracije = zahtjev.getRazlog();
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

		

		

		public String getRazlogRegistracije() {
			return razlogRegistracije;
		}

		public void setRazlogRegistracije(String razlogRegistracije) {
			this.razlogRegistracije = razlogRegistracije;
		}

		@Override
		public String toString() {
			return "Registracija [ID=" + ID + ", korisnickoIme=" + korisnickoIme + ", ime=" + ime + ", prezime="
					+ prezime + ", email=" + email + ", lozinka=" + lozinka + ", adresa=" + adresa + ", grad=" + grad
					+ ", drzava=" + drzava + ", brojTel=" + brojTel + ", tipRegistracije="
					+", razlogRegistracije=" + razlogRegistracije + "]";
		}
		
		public String toPrivateString() {
			return "ZahtevRegistracije [ID=" + ID + ", korisnickoIme=" + korisnickoIme + ", ime=" + ime + ", prezime="
					+ prezime + ", email=" + email + ", adresa=" + adresa + ", grad=" + grad
					+ ", drzava=" + drzava + ", brojTel=" + brojTel + ", tipRegistracije="
					 + ", razlogRegistracije=" + razlogRegistracije + "]";
		}
		

}