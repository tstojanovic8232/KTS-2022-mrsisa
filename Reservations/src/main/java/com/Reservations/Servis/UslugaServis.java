package com.Reservations.Servis;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Reservations.DTO.PeriodPrikazDTO;
import com.Reservations.DTO.PoslovanjeEntitetaDTO;
import com.Reservations.DTO.UslugaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Modeli.enums.TipRezervacije;
import com.Reservations.Modeli.enums.TipoviUsluga;
import com.Reservations.Repozitorijumi.UslugaRepozitorijum;

@Service
public class UslugaServis {

	@Autowired
	private UslugaRepozitorijum uslugaRepozitorijum;
	
	@Autowired
	private RezervacijaServis rezervacijaServis;

	@Autowired
	private TerminServis terminServis;
	
	@Autowired
	private GVarijableServis globalneVarijable;

	public List<Usluga> listAll() {
		return uslugaRepozitorijum.findAll();
	}

	public Usluga findById(Long id) {
		try {
			return uslugaRepozitorijum.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<Usluga> findByInstruktor(long id) {
		List<Usluga> lista = uslugaRepozitorijum.findAll();
		List<Usluga> rez = new ArrayList<Usluga>();
		for (Usluga u : lista) {
			if (u.getInstruktor().getID() == id)
				rez.add(u);
		}
		return rez;
	}

	public Usluga save(UslugaDTO dto, Korisnik instruktor) {
		Usluga u = new Usluga();
		List<Usluga> instUsluge = this.findByInstruktor(instruktor.getID());
		String bio = instUsluge.get(0).getBiografijaInstruktora();

		u.setNaziv(dto.getNaziv());
		u.setAdresa(dto.getAdresa());
		u.setBiografijaInstruktora(bio);
		u.setOpis(dto.getOpis());
		u.setMaxOsoba(dto.getMaxOsoba());
		u.setCena(dto.getCena());
		u.setPecaroskaOprema(dto.getPecaroskaOprema());
		u.setInstruktor(instruktor);
		u.setTip(TipoviUsluga.valueOf(dto.getTip()));
		u.setLinkSlike(dto.getLinkSlike());

		return this.uslugaRepozitorijum.save(u);
	}

	public Usluga update(UslugaDTO dto, long id) {
		Usluga u = this.findById(id);
		if (u != null) {
			if (!dto.getNaziv().equals("")) {
				u.setNaziv(dto.getNaziv());
			}
			if (!dto.getAdresa().equals("")) {
				u.setAdresa(dto.getAdresa());
			}
			if (!dto.getOpis().equals("")) {
				u.setOpis(dto.getOpis());
			}
			if (!String.valueOf(dto.getMaxOsoba()).equals("")) {
				u.setMaxOsoba(dto.getMaxOsoba());
			}
			if (!String.valueOf(dto.getCena()).equals("")) {
				u.setCena(dto.getCena());
			}
			if (!dto.getPecaroskaOprema().equals("")) {
				u.setPecaroskaOprema(dto.getPecaroskaOprema());
			}
			if (!dto.getTip().equals("")) {
				u.setTip(TipoviUsluga.valueOf(dto.getTip()));
			}
			if (!dto.getLinkSlike().equals("")) {
				u.setLinkSlike(dto.getLinkSlike());
			}

			System.out.println(u.toString());
		}
		return this.uslugaRepozitorijum.save(u);
	}

	public void deleteById(long id) {
		this.uslugaRepozitorijum.deleteById(id);
	}
	

	public List<Usluga>UslugaSortCena(){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Usluga>li=uslugaRepozitorijum.findAll(Sort.by(Sort.Direction.DESC, "cena"));
		System.out.println(li.toString());
		return li;
		}
	
	public List<Usluga>UslugaSortNaziv(){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Usluga>li=uslugaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "naziv"));
		System.out.println(li.toString());
		return li;
		}
	
	public List<Usluga>UslugaSortAdresa(){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Usluga>li=uslugaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "adresa"));
		System.out.println(li.toString());
		return li;
		}
	
	public List<Usluga>UslugaPretraga(String pretraga){
		//List<Brod>li2=new ArrayList<Brod>();
		List<Usluga>li=new ArrayList<Usluga>();
		List<Usluga>li2=uslugaRepozitorijum.findAll();
		for (Usluga e : li2) {
			if(e.getAdresa().toLowerCase().contains(pretraga.toLowerCase())) {
				li.add(e);
				continue;
			}
			if(e.getNaziv().toLowerCase().contains(pretraga.toLowerCase())) {
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

	public List<Usluga> nadjiUsluguPoInstruktoru(Korisnik vlasnik) {
		List<Usluga> lista = uslugaRepozitorijum.findAll();
		List<Usluga> rez = new ArrayList<Usluga>();
		for (Usluga u : lista) {
			if (u.getInstruktor().getID() == vlasnik.getID())
				rez.add(u);
		}
		return rez;
	}
	
	public List<PoslovanjeEntitetaDTO> poslovanjeUslugaPeriod(PoslovanjeEntitetaDTO poslovanje, Korisnik instruktor) 
	{
		List<Rezervacija> mojeRezervacije = rezervacijaServis.pronadjiRezervacijePoVlasniku(instruktor, TipEntiteta.usluga);
		List<Usluga> mojeUsluge = this.nadjiUsluguPoInstruktoru(instruktor);
		System.out.println("Rez datum: "+mojeRezervacije.get(0).getDatum());
		System.out.println("Pocetni dat: "+poslovanje.getPocetniDatum());
		List<PoslovanjeEntitetaDTO> poslovanjaUsluga = new ArrayList<PoslovanjeEntitetaDTO>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate pocetni = LocalDate.parse(poslovanje.getPocetniDatum(), dtf);
		LocalDate krajnji = LocalDate.parse(poslovanje.getKrajnjiDatum(), dtf);
		double vlasnikovProcenat = 1-Double.parseDouble(globalneVarijable.findByName("procenat").getVrednost());
		
		for(Usluga usluga : mojeUsluge)
		{
			PoslovanjeEntitetaDTO poslovanjeUsluge = new PoslovanjeEntitetaDTO(poslovanje);
			poslovanjeUsluge.setVlasnikID(instruktor.getID());
			poslovanjeUsluge.setEntitetID(usluga.getID());
			poslovanjeUsluge.setNazivEntiteta(usluga.getNaziv());
			poslovanje.setOcjenaEntiteta(0.0);
			double prihod = 0;
			Long brojRezervacija = 0L;
			double zarada = 0;
			for(Rezervacija rezervacija: mojeRezervacije) 
			{
				if(rezervacija.getEntitetId()==usluga.getID())
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
			poslovanjeUsluge.setZarada(zarada);
			poslovanjeUsluge.setBrojRezervacija(brojRezervacija);
			prihod = zarada*vlasnikovProcenat;
			poslovanjeUsluge.setPrihod(prihod);
			poslovanjaUsluga.add(poslovanjeUsluge);
		}
		return poslovanjaUsluga;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajSedmicnaPoslovanjaUsluga(Korisnik instruktor) 
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
			dnevno.setVlasnikID(instruktor.getID());
			dnevno.setPocetniDatum(pocetni.format(dtf).toString());
			dnevno.setKrajnjiDatum(krajnji.format(dtf).toString());
			dnevno.setNazivEntiteta(pocetni.getDayOfWeek().toString());
			svaPoslovanjaUdanu = this.poslovanjeUslugaPeriod(dnevno, instruktor);
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
	
	public List<PoslovanjeEntitetaDTO> izracunajMjesecnaPoslovanjaUsluga(Korisnik instruktor) {
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
			sedmicno.setVlasnikID(instruktor.getID());
			sedmicno.setPocetniDatum(pocetni.format(dtf).toString());
			sedmicno.setKrajnjiDatum(krajnji.format(dtf).toString());
			sedmicno.setNazivEntiteta(pocetni.toString());
			svaPoslovanjaUsedmici = this.poslovanjeUslugaPeriod(sedmicno, instruktor);
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
	public List<PoslovanjeEntitetaDTO> izracunajGodisnjaPoslovanjaUsluga(Korisnik instruktor) {
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
			mjesecno.setVlasnikID(instruktor.getID());
			mjesecno.setPocetniDatum(pocetni.format(dtf).toString());
			mjesecno.setKrajnjiDatum(krajnji.format(dtf).toString());
			mjesecno.setNazivEntiteta(pocetni.getMonth().toString());
			svaPoslovanjaUmjesecu = this.poslovanjeUslugaPeriod(mjesecno, instruktor);
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

	public List<Usluga> UslFilter(String brodovifil) {
		List<Usluga>li2=uslugaRepozitorijum.findAll();
	    List<Usluga>li=new ArrayList<Usluga>();
		 for (Usluga usluga : li2) {
			if(usluga.getAdresa().equals(brodovifil)) {
				li.add(usluga);
			}else if (brodovifil.equals("svi")) {
				return li2;
			}
		}
		 return li;
		
	}

	public List<PoslovanjeEntitetaDTO> poslovanjeUslugaPeriod(PoslovanjeEntitetaDTO poslovanje) 
	{
		List<Rezervacija> mojeRezervacije = rezervacijaServis.findByTip(TipEntiteta.usluga);
		List<Usluga> mojeUsluge = new ArrayList<Usluga>();
		for (Rezervacija rez : mojeRezervacije) {
			mojeUsluge.add(this.findById(rez.getEntitetId()));
		}
		System.out.println("Rez datum: "+mojeRezervacije.get(0).getDatum());
		System.out.println("Pocetni dat: "+poslovanje.getPocetniDatum());
		List<PoslovanjeEntitetaDTO> poslovanjaUsluga = new ArrayList<PoslovanjeEntitetaDTO>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		LocalDate pocetni = LocalDate.parse(poslovanje.getPocetniDatum(), dtf);
		LocalDate krajnji = LocalDate.parse(poslovanje.getKrajnjiDatum(), dtf);
		double procenat = Double.parseDouble(globalneVarijable.findByName("procenat").getVrednost());
		
		for(Usluga usluga : mojeUsluge)
		{
			PoslovanjeEntitetaDTO poslovanjeUsluge = new PoslovanjeEntitetaDTO(poslovanje);
			poslovanjeUsluge.setVlasnikID(null);
			poslovanjeUsluge.setEntitetID(usluga.getID());
			poslovanjeUsluge.setNazivEntiteta(usluga.getNaziv());
			poslovanje.setOcjenaEntiteta(0.0);
			double prihod = 0;
			Long brojRezervacija = 0L;
			double zarada = 0;
			for(Rezervacija rezervacija: mojeRezervacije) 
			{
				if(rezervacija.getEntitetId()==usluga.getID())
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
			poslovanjeUsluge.setZarada(zarada);
			poslovanjeUsluge.setBrojRezervacija(brojRezervacija);
			prihod = zarada*procenat;
			poslovanjeUsluge.setPrihod(prihod);
			poslovanjaUsluga.add(poslovanjeUsluge);
		}
		return poslovanjaUsluga;
	}
	
	public List<PoslovanjeEntitetaDTO> izracunajSedmicnaPoslovanjaUsluga() 
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
			svaPoslovanjaUdanu = this.poslovanjeUslugaPeriod(dnevno);
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
	
	public List<PoslovanjeEntitetaDTO> izracunajMjesecnaPoslovanjaUsluga() {
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
			svaPoslovanjaUsedmici = this.poslovanjeUslugaPeriod(sedmicno);
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
	public List<PoslovanjeEntitetaDTO> izracunajGodisnjaPoslovanjaUsluga() {
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
			svaPoslovanjaUmjesecu = this.poslovanjeUslugaPeriod(mjesecno);
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

	public void ubaciUsluguBazu(Usluga usluga) {
		uslugaRepozitorijum.save(usluga);
		
	}

	public List<PeriodPrikazDTO> dobaviPeriode(Usluga usluga) 
	{
		terminServis.popraviPeriodeUsluge(usluga);
				
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : usluga.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()==null)
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				System.out.println();
				periodi.add(period);
				System.out.println("Dodat period: "+ period);
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermine(Usluga usluga) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.listAll();
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		System.out.println("termini vikendice: "+usluga.getTerminiZauzetosti().size());
		for(Termin termin : usluga.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null)
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat termin zauzetosti: "+ period);
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermineBrzihRezervacija(Usluga usluga) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.nadjiPoTipuRezervacije(TipRezervacije.brza);
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : usluga.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null && termin.getRezervacija().getTip().equals(TipRezervacije.brza))
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat termin brze: "+ period);
			}
		}
		return periodi;
	}
	
	public List<PeriodPrikazDTO> dobaviTermineObicnihRezervacija(Usluga usluga) 
	{
		List<Rezervacija> rezervacije = rezervacijaServis.nadjiPoTipuRezervacije(TipRezervacije.obicna);
		for(Rezervacija rez : rezervacije)
		{
			terminServis.popraviTerminRezervacije(rez);
		}
		
		List<PeriodPrikazDTO> periodi = new ArrayList<PeriodPrikazDTO>();
		for(Termin termin : usluga.getTerminiZauzetosti() )
		{
			if(termin.getRezervacija()!=null && termin.getRezervacija().getTip().equals(TipRezervacije.obicna))
			{
				PeriodPrikazDTO period = new PeriodPrikazDTO(termin);
				periodi.add(period);
				System.out.println("Dodat termin obicne: "+ period);
			}
		}
		return periodi;
	}

	public List<Usluga> findByPretplaceniKorisnikUsluga(Korisnik user) {
List<Usluga>li=new ArrayList<Usluga>();
		
		List<Usluga>li2=this.uslugaRepozitorijum.findAll();
		for (Usluga v : li2) {
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
	
	
	public Boolean dodajPretplatuNaUsluga(Korisnik klijent, Usluga usluga) {
		Usluga br=this.findById(usluga.getID());
		if(klijent==null || br==null) {
			return false;
		}
		List<Korisnik>li=br.getPretplaceniKorisnici();
		li.add(klijent);
		br.setPretplaceniKorisnici(li);
		this.uslugaRepozitorijum.save(br);
		return true;
	}
	}
