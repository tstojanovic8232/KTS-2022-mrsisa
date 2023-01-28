package com.Reservations.DTO;

public class PoslovanjeEntitetaDTO 
{
	private Long entitetID;
	private Long vlasnikID;
	private String nazivEntiteta;
	private String pocetniDatum;
	private String krajnjiDatum;
	private double ocjenaEntiteta;
	private double zarada;
	private double prihod;
	private Long brojRezervacija;
	
	public PoslovanjeEntitetaDTO(){}

	public PoslovanjeEntitetaDTO(Long iDentiteta, Long vlasnikID, String nazivEntiteta, String pocetniDatum, String krajnjiDatum,
			double ocjenaEntiteta, Long brojRezervacija, double zarada, double prihod ) {
		super();
		this.entitetID = iDentiteta;
		this.vlasnikID = vlasnikID;
		this.nazivEntiteta = nazivEntiteta;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.ocjenaEntiteta = ocjenaEntiteta;
		this.prihod = prihod;
		this.zarada = zarada;
		this.brojRezervacija = brojRezervacija;
	}

	public PoslovanjeEntitetaDTO(String pocetniDatum, String krajnjiDatum)
	{
		
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.vlasnikID = 0L;
		this.entitetID = 0L;
		this.prihod = 0;
		this.ocjenaEntiteta = 0.0;
		this.zarada = 0.0;
		this.brojRezervacija = 0L;
	}


	

	public PoslovanjeEntitetaDTO(PoslovanjeEntitetaDTO poslovanje) {
		super();
		this.entitetID = poslovanje.entitetID;
		this.vlasnikID = poslovanje.vlasnikID;
		this.nazivEntiteta = poslovanje.nazivEntiteta;
		this.pocetniDatum = poslovanje.pocetniDatum;
		this.krajnjiDatum = poslovanje.krajnjiDatum;
		this.ocjenaEntiteta = poslovanje.ocjenaEntiteta;
		this.prihod = poslovanje.prihod;
		this.zarada = poslovanje.zarada;
		this.brojRezervacija = poslovanje.brojRezervacija;
	}

	public void srediDatume()
	{
		String[] pocetni = this.getPocetniDatum().split("-");
		String[] krajnji = this.getKrajnjiDatum().split("-");
		if(pocetni.length==3 && krajnji.length==3)
		{
			this.pocetniDatum = pocetni[1]+"/"+pocetni[2]+"/"+pocetni[0];
			this.krajnjiDatum = krajnji[1]+"/"+krajnji[2]+"/"+krajnji[0];
		}
	}
	
	public Long getEntitetID() {
		return entitetID;
	}

	public void setEntitetID(Long entitetID) {
		this.entitetID = entitetID;
	}

	public String getNazivEntiteta() {
		return nazivEntiteta;
	}

	public void setNazivEntiteta(String nazivEntiteta) {
		this.nazivEntiteta = nazivEntiteta;
	}

	public String getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(String pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public String getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(String krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	public double getOcjenaEntiteta() {
		return ocjenaEntiteta;
	}

	public void setOcjenaEntiteta(double ocjenaEntiteta) {
		this.ocjenaEntiteta = ocjenaEntiteta;
	}

	public double getPrihod() {
		return prihod;
	}

	public void setPrihod(double prihod) {
		this.prihod = prihod;
	}
	
	

	public Long getVlasnikID() {
		return vlasnikID;
	}

	public void setVlasnikID(Long vlasnikID) {
		this.vlasnikID = vlasnikID;
	}

	
	public double getZarada() {
		return zarada;
	}

	public void setZarada(double cijena) {
		this.zarada = cijena;
	}

	
	public Long getBrojRezervacija() {
		return brojRezervacija;
	}

	public void setBrojRezervacija(Long brojRezervacija) {
		this.brojRezervacija = brojRezervacija;
	}

	@Override
	public String toString() {
		return "PoslovanjeEntitetaDTO [entitetID=" + entitetID + ", nazivEntiteta=" + nazivEntiteta
				+ ", pocetniDatum=" + pocetniDatum + ", krajnjiDatum=" + krajnjiDatum + ", ocjenaEntiteta="
				+ ocjenaEntiteta + ", zarada=" + zarada + ", prihod=" + prihod + "]";
	}
	
	
	
}
