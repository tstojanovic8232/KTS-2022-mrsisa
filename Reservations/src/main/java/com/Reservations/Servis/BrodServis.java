package com.Reservations.Servis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.BrodDTO;
import com.Reservations.DTO.PeriodPrikazDTO;
import com.Reservations.DTO.PoslovanjeEntitetaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Modeli.enums.TipRezervacije;
import com.Reservations.Repozitorijumi.BrodRepozitorijum;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;

@Service
public class BrodServis 
{
	@Autowired
	private BrodRepozitorijum brodRepozitorijum;
	
	@Autowired
	private KorisnikServis korisnikServis;

	@Autowired
	private RezervacijaRepozitorijum rezervacijaRepozitorijum;
	
	@Autowired
	private RezervacijaServis rezervacijaServis;
	
	@Autowired
	private GVarijableServis globalneVarijable;
	
	@Autowired
	private TerminServis terminServis;
	
	public List<Brod> listAll(){
		return brodRepozitorijum.findAll();
	}
	
	public void delete(Long id){
		this.brodRepozitorijum.deleteById(id);
	}

	public Brod findById(Long id){
		try {
			return brodRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public List<Brod> findByVlasnik(long id) {
		List<Brod> lista = brodRepozitorijum.findAll();
		List<Brod> brodoviVlasnika = new ArrayList<Brod>();
		for(Brod u : lista) {
			if (u.getVlasnik().getID() == id) brodoviVlasnika.add(u);
		}
		return brodoviVlasnika;
	}

	public List<Brod> nadjiBrodovePoVlasniku(Korisnik vlasnik) {
		List<Brod> brodovi = this.listAll();
		List<Brod> mojiBrodovi = new ArrayList<Brod>();
		for(Brod brod : brodovi)
		{
			
			System.out.println("Naziv: "+ brod.getNaziv());
			System.out.println("Vlasnik: "+brod.getVlasnik().getKorisnickoIme());
			System.out.println(" Trazim: "+vlasnik.getKorisnickoIme());
			if(brod.getVlasnik().equals(vlasnik))
			{
				mojiBrodovi.add(brod);
				System.out.println("Dodata!");
			}
			else System.out.println("Odbacena!");
		}
		System.out.println("Size: "+mojiBrodovi.size());
		return mojiBrodovi;
	}

	public Brod pronadjiPoNazivu(String naziv) {
		Brod brod = brodRepozitorijum.findByNaziv(naziv);
		return brod;
	}

	public String[] dodajBrod(BrodDTO noviBrod) {
		String[] poruka = new String[2];
		Korisnik vlasnik = korisnikServis.findById(noviBrod.getVlasnik());
		try
		{
			System.out.println("Dodaj brod servis!");
			Brod brod = brodRepozitorijum.findByNaziv(noviBrod.getNaziv());
					//findByNaziv(novaVikendica.getNaziv());
			System.out.println("find by naziv: "+ brod);
			if(brod==null)
			{
				brod = new Brod();
				brod.setAdresa(noviBrod.getAdresa());
				brod.setBrojMotora(noviBrod.getBrojMotora().toString());
				brod.setCena(noviBrod.getCena());
				brod.setDuzina(noviBrod.getDuzina());
				brod.setKapacitet(noviBrod.getKapacitet()+" osoba");
				brod.setLinkKabine(noviBrod.getLinkKabine());
				brod.setLinkSlike(noviBrod.getLinkSlike());
				brod.setMaxBrzina(noviBrod.getMaxBrzina());
				brod.setNavigacionaOprema(noviBrod.getNavigacionaOprema());
				brod.setNaziv(noviBrod.getNaziv());
				brod.setOpis(noviBrod.getOpis());
				brod.setPecaroskaOprema(noviBrod.getPecaroskaOprema());
				brod.setPravilaPonasanja(noviBrod.getPravilaPonasanja());
				brod.setSnaga(noviBrod.getSnaga());
				brod.setTip(noviBrod.getTip());
				brod.setVlasnik(vlasnik);
				
				
				List<Brod> brodovi = this.listAll();
				Long ID = 0L;
				for(Brod brodd : brodovi)
				{	
					BrodDTO bbb = new BrodDTO(brodd);
					System.out.println("ParseLong za kapacitet: "+ bbb.getKapacitet());
					if(brodd.getID()==ID)
					{
						ID++;
					}
				}
				//brod.setID(ID);
				brodRepozitorijum.save(brod);
				poruka[0] = "Brod je uspjesno dodat!";
				poruka[1] = "success";
				return poruka;
			}
			else 
			{
				poruka[0] = "Brod sa tim nazivom već postoji";
				poruka[1] = "duplicate";
				return poruka;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			poruka[0] = "Doslo je do greske pri dodavanju!";
			poruka[1] = "error";
			
		}
		return poruka;
	}

	public String[] izmijeniBrod(BrodDTO noviBrod, Brod stariBrod) {
		String[] poruka = new String[2];
		List<Rezervacija> rezervacije = rezervacijaRepozitorijum.findByEntitetId(stariBrod.getID());
		List<Rezervacija> rezervacijeBroda = new ArrayList<Rezervacija>();
		for(Rezervacija rez : rezervacije)
		{
			System.out.println("ID rezervacije: "+rez.getID());
			System.out.println("Trazi se brod: "+stariBrod.getID());
			System.out.println("ID entiteta: "+rez.getEntitetId()+" Tip: "+rez.getTipEntiteta() );
			if(stariBrod.getID()==rez.getEntitetId() && rez.getTipEntiteta().equals(TipEntiteta.brod))
			{
				rezervacijeBroda.add(rez);
			}
				
		}
		if(rezervacijeBroda==null || rezervacijeBroda.isEmpty())
		{
			System.out.println("Izmjena vikendice servis!");
			Brod brodProvjere = brodRepozitorijum.findByNaziv(noviBrod.getNaziv());
			if(brodProvjere==null || brodProvjere.getID()==stariBrod.getID())
			{
				stariBrod.setAdresa(noviBrod.getAdresa());
				stariBrod.setBrojMotora(noviBrod.getBrojMotora());
				stariBrod.setCena(noviBrod.getCena());
				stariBrod.setDuzina(noviBrod.getDuzina());
				stariBrod.setKapacitet(noviBrod.getKapacitet());
				stariBrod.setMaxBrzina(noviBrod.getMaxBrzina());
				stariBrod.setNavigacionaOprema(noviBrod.getNavigacionaOprema());
				stariBrod.setOpis(noviBrod.getOpis());
				stariBrod.setPecaroskaOprema(noviBrod.getPecaroskaOprema());
				stariBrod.setPravilaPonasanja(noviBrod.getPravilaPonasanja());
				stariBrod.setSnaga(noviBrod.getSnaga());
				stariBrod.setTip(noviBrod.getTip());
				System.out.println("prva slika: "+noviBrod.getLinkSlike()+"\ndruga slika: "+noviBrod.getLinkKabine());
				if(noviBrod.getLinkSlike()!=null && !noviBrod.getLinkSlike().trim().equals(""))
				{			
					System.out.println("Prva slika je: "+noviBrod.getLinkSlike());
					stariBrod.setLinkSlike(noviBrod.getLinkSlike());
				}
				if(noviBrod.getLinkKabine()!=null && !noviBrod.getLinkKabine().trim().equals(""))
				{
					System.out.println("Druga slika je: "+noviBrod.getLinkKabine());
					stariBrod.setLinkKabine(noviBrod.getLinkKabine());
				}
				
				stariBrod.setNaziv(noviBrod.getNaziv());
				stariBrod.setOpis(noviBrod.getOpis());
				System.out.println(noviBrod.getVlasnik());
				
				brodRepozitorijum.save(stariBrod);
				poruka[0] = "Izmjena broda je uspjesna";
				poruka[1] = "success";
			}
			else
			{
				poruka[0] = "Brod sa unijetim nazivom već postoji!";
				poruka[1] = "duplicate";
			
			}
		}	
		else
		{	
			poruka[0]= "Doslo je do greske, brod je vec rezervisan!";
			poruka[1] = "failure";
		}
		return poruka;
			
	}

	
	public List<Brod>BrodSortCena(){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Brod>li=brodRepozitorijum.findAll(Sort.by(Sort.Direction.DESC, "cena"));
		System.out.println(li.toString());
		return li;
		}
	
	public List<Brod>BrodSortNaziv(){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Brod>li=brodRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "tip"));
		System.out.println(li.toString());
		return li;
		}
	
	public List<Brod>BrodSortAdresa(){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Brod>li=brodRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "adresa"));
		System.out.println(li.toString());
		return li;
		}
	
	public List<Brod>BrodPretraga(String pretraga){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Brod>li=new ArrayList<Brod>();
		List<Brod>li2=brodRepozitorijum.findAll();
		for (Brod e : li2) {
			if(e.getAdresa().toLowerCase().contains(pretraga.toLowerCase())) {
				li.add(e);
				continue;
			}
			if(e.getTip().toLowerCase().contains(pretraga.toLowerCase())) {
				li.add(e);
				continue;
			}
			if(String.valueOf(e.getCena()).contains(pretraga)) {
				li.add(e);
				continue;
			}
		}
		return li;
		}
	

	public List<Brod>BrodFilter(String tip){


		//List<Brod>li2=new ArrayList<Brod>();
		
		List<Brod>li2=brodRepozitorijum.findAll();
	    List<Brod>li=new ArrayList<Brod>();
		 for (Brod brod : li2) {
			if(brod.getTip().equals(tip)) {
				li.add(brod);
				}
				else if(brod.getAdresa().equals(tip)) {
					li.add(brod);
				
			}else if (tip.equals("svi")) {
				return li2;
			}
		}
		 return li;
		}
		
	public String[] obrisiBrod(Long vlasnikID, Long brodID) 
	{
		String poruka[] = new String[2];
		Brod stariBrod = this.findById(brodID);
		List<Rezervacija> rezervacije = rezervacijaRepozitorijum.findByEntitetId(stariBrod.getID());
		List<Rezervacija> rezervacijeBroda = new ArrayList<Rezervacija>();
		
		for(Rezervacija rez : rezervacije)
		{
			System.out.println("ID rezervacije: "+rez.getID());
			System.out.println("Trazi se brod: "+stariBrod.getID());
			System.out.println("ID entiteta: "+rez.getEntitetId()+" Tip: "+rez.getTipEntiteta() );
			if(stariBrod.getID()==rez.getEntitetId() && rez.getTipEntiteta().equals(TipEntiteta.brod))
			{
				rezervacijeBroda.add(rez);
			}
				
		}
		if(rezervacijeBroda==null || rezervacijeBroda.isEmpty())
		{	
			brodRepozitorijum.delete(stariBrod);
			poruka[0] = "Brisanje broda je uspjesno!";
			poruka[1] = "success";
		}
		else 
		{
			poruka[0] =  "Doslo je do greske, brod je vec rezervisan!";
			poruka[1] = "reserved";
		}
		return poruka;
	}
	
	public List<PoslovanjeEntitetaDTO> poslovanjeBrodovaPeriod(PoslovanjeEntitetaDTO poslovanje, Korisnik vlasnik) 
	{
		List<Rezervacija> mojeRezervacije = rezervacijaServis.pronadjiRezervacijePoVlasniku(vlasnik, TipEntiteta.brod);
		List<Brod> mojiBrodovi = this.nadjiBrodovePoVlasniku(vlasnik);
		System.out.println("Rez datum: "+mojeRezervacije.get(0).getDatum());
		System.out.println("Pocetni dat: "+poslovanje.getPocetniDatum());
		List<PoslovanjeEntitetaDTO> poslovanjaBrodova = new ArrayList<PoslovanjeEntitetaDTO>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate pocetni = LocalDate.parse(poslovanje.getPocetniDatum(), dtf);
		LocalDate krajnji = LocalDate.parse(poslovanje.getKrajnjiDatum(), dtf);
		double vlasnikovProcenat = 1-Double.parseDouble(globalneVarijable.findByName("procenat").getVrednost());
		
		for(Brod brod : mojiBrodovi)
		{
			PoslovanjeEntitetaDTO poslovanjeBroda = new PoslovanjeEntitetaDTO(poslovanje);
			poslovanjeBroda.setVlasnikID(vlasnik.getID());
			poslovanjeBroda.setEntitetID(brod.getID());
			poslovanjeBroda.setNazivEntiteta(brod.getNaziv());
			poslovanje.setOcjenaEntiteta(0.0);
			double prihod = 0;
			Long brojRezervacija = 0L;
			double zarada = 0;
			for(Rezervacija rezervacija: mojeRezervacije) 
			{
				if(rezervacija.getEntitetId()==brod.getID())
				{
					LocalDate datum = LocalDate.parse(rezervacija.getDatum(), dtf);
					if(  (datum.isAfter(pocetni) || datum.isEqual(pocetni) ) && datum.isBefore(krajnji))
					{
						brojRezervacija++;
						zarada += rezervacija.getCena();
					}
				}
				//if(LocalDate.parse(r.getDatum(), dtf).isAfter())
			}
			poslovanjeBroda.setZarada(zarada);
			poslovanjeBroda.setBrojRezervacija(brojRezervacija);
			prihod = zarada*vlasnikovProcenat;
			poslovanjeBroda.setPrihod(prihod);
			poslovanjaBrodova.add(poslovanjeBroda);
		}
		return poslovanjaBrodova;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajSedmicnaPoslovanjaBrodova(Korisnik vlasnik) 
	{
		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUdanu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusDays(6);
		LocalDate krajnji = pocetni.plusDays(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 7; i++)
		{
			PoslovanjeEntitetaDTO dnevno =  new PoslovanjeEntitetaDTO();
			dnevno.setVlasnikID(vlasnik.getID());
			dnevno.setPocetniDatum(pocetni.format(dtf).toString());
			dnevno.setKrajnjiDatum(krajnji.format(dtf).toString());
			dnevno.setNazivEntiteta(pocetni.getDayOfWeek().toString());
			svaPoslovanjaUdanu = this.poslovanjeBrodovaPeriod(dnevno, vlasnik);
			double dnevniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUdanu.size(); j++)
			{
				dnevniPrihod += svaPoslovanjaUdanu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUdanu.get(j).getBrojRezervacija();
			}
			dnevno.setBrojRezervacija(brojRezervacija);
			dnevno.setPrihod(dnevniPrihod);
			sedmicnaPoslovanja.add(dnevno);
			pocetni = pocetni.plusDays(1);
			krajnji = krajnji.plusDays(1);
		}
		
		return sedmicnaPoslovanja;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajMjesecnaPoslovanjaBrodova(Korisnik vlasnik) {
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUsedmici;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusWeeks(4);
		LocalDate krajnji = pocetni.plusWeeks(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 4; i++)
		{
			PoslovanjeEntitetaDTO sedmicno =  new PoslovanjeEntitetaDTO();
			sedmicno.setVlasnikID(vlasnik.getID());
			sedmicno.setPocetniDatum(pocetni.format(dtf).toString());
			sedmicno.setKrajnjiDatum(krajnji.format(dtf).toString());
			sedmicno.setNazivEntiteta(pocetni.toString());
			svaPoslovanjaUsedmici = this.poslovanjeBrodovaPeriod(sedmicno, vlasnik);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUsedmici.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUsedmici.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUsedmici.get(j).getBrojRezervacija();
			}
			sedmicno.setBrojRezervacija(brojRezervacija);
			sedmicno.setPrihod(mjesecniPrihod);
			mjesecnaPoslovanja.add(sedmicno);
			pocetni = pocetni.plusWeeks(1);
			krajnji = krajnji.plusWeeks(1);
		}
		
		return mjesecnaPoslovanja;
	}
	public List<PoslovanjeEntitetaDTO> izracunajGodisnjaPoslovanjaBrodova(Korisnik vlasnik) {
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUmjesecu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusMonths(11);
		LocalDate krajnji = pocetni.plusMonths(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 12; i++)
		{
			PoslovanjeEntitetaDTO mjesecno =  new PoslovanjeEntitetaDTO();
			mjesecno.setVlasnikID(vlasnik.getID());
			mjesecno.setPocetniDatum(pocetni.format(dtf).toString());
			mjesecno.setKrajnjiDatum(krajnji.format(dtf).toString());
			mjesecno.setNazivEntiteta(pocetni.getMonth().toString());
			svaPoslovanjaUmjesecu = this.poslovanjeBrodovaPeriod(mjesecno, vlasnik);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUmjesecu.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUmjesecu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUmjesecu.get(j).getBrojRezervacija();
			}
			mjesecno.setBrojRezervacija(brojRezervacija);
			mjesecno.setPrihod(mjesecniPrihod);
			godisnjaPoslovanja.add(mjesecno);
			pocetni = pocetni.plusMonths(1);
			krajnji = krajnji.plusMonths(1);
		}
		
		return godisnjaPoslovanja;
	}
	
	
	// ADMINISTRATORSKI IZVESTAJI
	// 				|
	// 				|
	// 				V

	public List<PoslovanjeEntitetaDTO> poslovanjeBrodovaPeriod(PoslovanjeEntitetaDTO poslovanje) 
	{
		List<Rezervacija> mojeRezervacije = rezervacijaServis.findByTip(TipEntiteta.brod);
		List<Brod> mojeBrodovi = new ArrayList<Brod>();
		for (Rezervacija rez : mojeRezervacije) {
			mojeBrodovi.add(this.findById(rez.getEntitetId()));
		}
		System.out.println("Rez datum: "+mojeRezervacije.get(0).getDatum());
		System.out.println("Pocetni dat: "+poslovanje.getPocetniDatum());
		List<PoslovanjeEntitetaDTO> poslovanjaBrodova = new ArrayList<PoslovanjeEntitetaDTO>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate pocetni = LocalDate.parse(poslovanje.getPocetniDatum(), dtf);
		LocalDate krajnji = LocalDate.parse(poslovanje.getKrajnjiDatum(), dtf);
		double procenat = Double.parseDouble(globalneVarijable.findByName("procenat").getVrednost());
		
		for(Brod brod : mojeBrodovi)
		{
			PoslovanjeEntitetaDTO poslovanjeBrodovi = new PoslovanjeEntitetaDTO(poslovanje);
			poslovanjeBrodovi.setVlasnikID(null);
			poslovanjeBrodovi.setEntitetID(brod.getID());
			poslovanjeBrodovi.setNazivEntiteta(brod.getNaziv());
			poslovanje.setOcjenaEntiteta(0.0);
			double prihod = 0;
			Long brojRezervacija = 0L;
			double zarada = 0;
			for(Rezervacija rezervacija: mojeRezervacije) 
			{
				if(rezervacija.getEntitetId()==brod.getID())
				{
					LocalDate datum = LocalDate.parse(rezervacija.getDatum(), dtf);
					if(  (datum.isAfter(pocetni) || datum.isEqual(pocetni) ) && datum.isBefore(krajnji))
					{
						brojRezervacija++;
						zarada += rezervacija.getCena();
					}
				}
				//if(LocalDate.parse(r.getDatum(), dtf).isAfter())
			}
			poslovanjeBrodovi.setZarada(zarada);
			poslovanjeBrodovi.setBrojRezervacija(brojRezervacija);
			prihod = zarada*procenat;
			poslovanjeBrodovi.setPrihod(prihod);
			poslovanjaBrodova.add(poslovanjeBrodovi);
		}
		return poslovanjaBrodova;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajSedmicnaPoslovanjaBrodova() 
	{
		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUdanu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusWeeks(3);
		LocalDate krajnji = pocetni.plusWeeks(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 7; i++)
		{
			PoslovanjeEntitetaDTO dnevno =  new PoslovanjeEntitetaDTO();
			dnevno.setVlasnikID(null);
			dnevno.setPocetniDatum(pocetni.format(dtf).toString());
			dnevno.setKrajnjiDatum(krajnji.format(dtf).toString());
			dnevno.setNazivEntiteta(pocetni.getDayOfWeek().toString());
			svaPoslovanjaUdanu = this.poslovanjeBrodovaPeriod(dnevno);
			double dnevniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUdanu.size(); j++)
			{
				dnevniPrihod += svaPoslovanjaUdanu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUdanu.get(j).getBrojRezervacija();
			}
			dnevno.setBrojRezervacija(brojRezervacija);
			dnevno.setPrihod(dnevniPrihod);
			sedmicnaPoslovanja.add(dnevno);
			pocetni = pocetni.plusDays(1);
			krajnji = krajnji.plusDays(1);
		}
		
		return sedmicnaPoslovanja;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajMjesecnaPoslovanjaBrodova() {
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUsedmici;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusWeeks(4);
		LocalDate krajnji = pocetni.plusWeeks(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 4; i++)
		{
			PoslovanjeEntitetaDTO sedmicno =  new PoslovanjeEntitetaDTO();
			sedmicno.setVlasnikID(null);
			sedmicno.setPocetniDatum(pocetni.format(dtf).toString());
			sedmicno.setKrajnjiDatum(krajnji.format(dtf).toString());
			sedmicno.setNazivEntiteta(pocetni.toString());
			svaPoslovanjaUsedmici = this.poslovanjeBrodovaPeriod(sedmicno);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUsedmici.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUsedmici.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUsedmici.get(j).getBrojRezervacija();
			}
			sedmicno.setBrojRezervacija(brojRezervacija);
			sedmicno.setPrihod(mjesecniPrihod);
			mjesecnaPoslovanja.add(sedmicno);
			pocetni = pocetni.plusWeeks(1);
			krajnji = krajnji.plusWeeks(1);
		}
		
		return mjesecnaPoslovanja;
	}
	public List<PoslovanjeEntitetaDTO> izracunajGodisnjaPoslovanjaBrodova() {
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanja = new ArrayList<PoslovanjeEntitetaDTO>();
		List<PoslovanjeEntitetaDTO> svaPoslovanjaUmjesecu;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate trenutni = LocalDate.now();
		LocalDate pocetni = trenutni.minusMonths(12);
		LocalDate krajnji = pocetni.plusMonths(1);
		System.out.println("Local date krajnji: "+krajnji.format(dtf).toString());
		
		for(int i = 0; i< 12; i++)
		{
			PoslovanjeEntitetaDTO mjesecno =  new PoslovanjeEntitetaDTO();
			mjesecno.setVlasnikID(null);
			mjesecno.setPocetniDatum(pocetni.format(dtf).toString());
			mjesecno.setKrajnjiDatum(krajnji.format(dtf).toString());
			mjesecno.setNazivEntiteta(pocetni.getMonth().toString());
			svaPoslovanjaUmjesecu = this.poslovanjeBrodovaPeriod(mjesecno);
			double mjesecniPrihod = 0;
			Long brojRezervacija = 0L;
			for(int j = 0; j < svaPoslovanjaUmjesecu.size(); j++)
			{
				mjesecniPrihod += svaPoslovanjaUmjesecu.get(j).getPrihod();
				brojRezervacija += svaPoslovanjaUmjesecu.get(j).getBrojRezervacija();
			}
			mjesecno.setBrojRezervacija(brojRezervacija);
			mjesecno.setPrihod(mjesecniPrihod);
			godisnjaPoslovanja.add(mjesecno);
			pocetni = pocetni.plusMonths(1);
			krajnji = krajnji.plusMonths(1);
		}
		
		return godisnjaPoslovanja;
	}
	
	public List<PeriodPrikazDTO> dobaviPeriode(Brod brod) 
	{
		terminServis.popraviPeriodeBroda(brod);
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : brod.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()==null)
			{
				periodi.add(new PeriodPrikazDTO(termin));
			}
		}
		return periodi;
	}

	
	public List<PeriodPrikazDTO> dobaviTermine(Brod brod) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.listAll();
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : brod.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null)
			{
				periodi.add(new PeriodPrikazDTO(termin));
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermineBrzihRezervacija(Brod brod) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.nadjiPoTipuRezervacije(TipRezervacije.brza);
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : brod.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null && termin.getRezervacija().getTip().equals(TipRezervacije.brza))
			{
				periodi.add(new PeriodPrikazDTO(termin));
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermineObicnihRezervacija(Brod brod) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.nadjiPoTipuRezervacije(TipRezervacije.obicna);
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : brod.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null && termin.getRezervacija().getTip().equals(TipRezervacije.obicna))
			{
				periodi.add(new PeriodPrikazDTO(termin));
			}
		}
		return periodi;
	}
	
	public void ubaciBrodUbazu(Brod brod) {
		brodRepozitorijum.save(brod);
		
	}



	public List<Brod> findByPretplaceniKorisnikBrod(Korisnik user) {
     List<Brod>li=new ArrayList<Brod>();
		
		List<Brod>li2=this.brodRepozitorijum.findAll();
		for (Brod v : li2) {
			System.out.println(v.getPretplaceniKorisnici().size());
		if(v.getPretplaceniKorisnici().size()==0)
		
			continue;
		
			for (Korisnik k : v.getPretplaceniKorisnici()) {
				  System.out.println(user);
				if(k.equals(user)) {
					li.add(v);
				}
			}
		}
	   
		  System.out.println(li);
		return li;
	}

	public Boolean dodajPretplatuNaBrod(Korisnik klijent, Brod brod) {
		Brod br=this.findById(brod.getID());
		if(klijent==null || brod==null) {
			return false;
		}
		List<Korisnik>li=br.getPretplaceniKorisnici();
		li.add(klijent);
		br.setPretplaceniKorisnici(li);
		
		this.brodRepozitorijum.save(br);
		
		return true;
	}
}
