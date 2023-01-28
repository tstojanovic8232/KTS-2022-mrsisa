package com.Reservations.Kontroleri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Reservations.DTO.BrisanjeKorisnikaZahtjevDTO;
import com.Reservations.DTO.BrodDTO;
import com.Reservations.DTO.BrzaRezervacijaDTO;
import com.Reservations.DTO.IzvjestajRezervacijaDTO;
import com.Reservations.DTO.KlijentSpisakDTO;
import com.Reservations.DTO.PeriodPrikazDTO;
import com.Reservations.DTO.PoslovanjeEntitetaDTO;
import com.Reservations.DTO.PromenaLozinkeDTO;
import com.Reservations.DTO.SlikaDTO;
import com.Reservations.DTO.VlasnikVikendiceDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Termin;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Servis.BrodServis;
import com.Reservations.Servis.GVarijableServis;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RezervacijaServis;
import com.Reservations.Servis.TerminServis;
import com.Reservations.Servis.UlogaServis;

@Controller
@RequestMapping(value = "/brodVlasnik")
public class VlasnikBrodaKontroler 
{

	public String putanjaSlikaKorisnika = "/img/korisnici/";
	
	@Autowired
	GVarijableServis gvServis;

	@Autowired
	KorisnikServis korisnikServis;

	@Autowired
	UlogaServis ulogaServis;

	@Autowired
	RezervacijaServis rezervacijaServis;
	
	@Autowired
	BrodServis brodServis;
	
	@Autowired
	TerminServis terminServis;
	
	@RequestMapping(value="/pocetna/{korisnickoIme}", method = RequestMethod.GET)
	public String prikaziPocetnu(Model model, @PathVariable String korisnickoIme)
	{
		System.out.println("prikaziPocetnu: "+korisnickoIme);
		Korisnik korisnik = korisnikServis.findByUsername(korisnickoIme);
		VlasnikVikendiceDTO vlasnik = new VlasnikVikendiceDTO(korisnik);
		model.addAttribute("vlasnikBroda", vlasnik);
		return "/brodovi/vlasnikBrodovaPocetna.html";
	}
	

		
	@RequestMapping(value = "/prikaziBrodove/{vrstaPrikaza}/{vlasnikID}", method = RequestMethod.GET)
	public String getEntitiesPage(Model model, @PathVariable String vrstaPrikaza, @PathVariable Long vlasnikID) 
	{
		System.out.println("Pregled brodova page was called!");
		List<Korisnik> korisnici = korisnikServis.listAll();
		Korisnik vlasnik= korisnikServis.findById(vlasnikID);
		if(vlasnik!=null)
		{
			System.out.println("Ubacujem brodove");
			List<Brod> brodovi = brodServis.nadjiBrodovePoVlasniku(vlasnik);
			model.addAttribute("brodovi", brodovi);
			
		}
		else
		{
			System.out.println("Vlasnik broda nije pronadjen ili je doslo do greske!");
			return "/loginFaiulure";
		}
		VlasnikVikendiceDTO vlasnikV = new VlasnikVikendiceDTO(vlasnik);
		model.addAttribute("vlasnikBroda", vlasnikV);
		System.out.println(model.toString());
		if(vrstaPrikaza.equals("upravljanje"))return "/brodovi/upravljanjeBrodovima.html";
		else if(vrstaPrikaza.equals("kalendar")) return "/brodovi/kalendarBrodova.html";
		else if(vrstaPrikaza.equals("periodiZauzetosti")) return "/brodovi/periodiZauzetostiBrodova.html";
		else if(vrstaPrikaza.equals("prikazi")) return "/brodovi/mojiBrodovi";
		else return "/brodovi/upravljanjeBrodovima.html";
	}
	
	@RequestMapping(value = "/profil/{vlasnikID}")
	public String moj_profil(Model model, @PathVariable Long vlasnikID) 
	{
		VlasnikVikendiceDTO vlasnikVikendice = new VlasnikVikendiceDTO(korisnikServis.findById(vlasnikID));//TODO:dodati ID iz tokena
		System.out.println("Pozvan profil od: "+vlasnikVikendice.getKorisnickoIme()+" !");
		if(vlasnikVikendice.getLinkSlike()==null || vlasnikVikendice.getLinkSlike().equals("") ) vlasnikVikendice.setLinkSlike("/img/avatar.png");
		model.addAttribute("vlasnikVikendice", vlasnikVikendice);
		return "/vikendice/profilVlasnikaVikendice";//TODO:vlasnikVikendiceMyData
	}

	@RequestMapping(value = "/moj-profil/{korisnickoIme}")
	public String getDataPage(Model model, @PathVariable String korisnickoIme) 
	{
		System.out.println("Pozvan profil od: "+korisnickoIme+" !");
		VlasnikVikendiceDTO vlasnikVikendice = new VlasnikVikendiceDTO(korisnikServis.findByUsername(korisnickoIme));//TODO:dodati ID iz tokena
		System.out.println("slika profila: "+vlasnikVikendice.getLinkSlike());
		if(vlasnikVikendice.getLinkSlike()==null || vlasnikVikendice.getLinkSlike().equals("") ) vlasnikVikendice.setLinkSlike("/img/avatar.png");
		model.addAttribute("vlasnikVikendice", vlasnikVikendice);
		return "/vikendice/vlasnikVikendicePodaci";//TODO:vlasnikVikendiceMyData
	}
	
	@RequestMapping(value = "/azuriraj-podatke/{vlasnikID}")
	 public String prikupiPodatke(Model model, @PathVariable Long vlasnikID){
	  		System.out.println("AzurirajPodatke page was called!");
	  		Korisnik korisnik=korisnikServis.findById(vlasnikID);
	  		VlasnikVikendiceDTO vlasnik = new VlasnikVikendiceDTO(korisnik);
	  		
	  		if(vlasnik.getLinkSlike()==null || vlasnik.getLinkSlike().trim().equals("")) vlasnik.setLinkSlike("/img/avatar.png");
	  		model.addAttribute("vlasnikVikendice", vlasnik);
	  		System.out.println("VLASNIK ID: "+vlasnik.getId());
	  		System.out.println(model.toString());
	  		 return "/vikendice/azurirajPodatkeVlasnika.html";
	  	  }
	
	@RequestMapping(value = "/azuriranje-podataka/{idVlasnika}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String izmijeniPodatke(@PathVariable Long idVlasnika, SlikaDTO slikaDTO, Model model, VlasnikVikendiceDTO vlasnikVikendice) throws IOException
	{		String poruka;
			System.out.println("slika naziv: "+slikaDTO.getNazivSlike());
	 		if(slikaDTO.getNazivSlike()!=null || !slikaDTO.getNazivSlike().trim().equals(""))
	 		{
				String apsolutnaPutanja= (new File("src/main/resources/static")).getAbsolutePath();
				File slika = new File(apsolutnaPutanja+this.putanjaSlikaKorisnika+slikaDTO.getNazivSlike());
				System.out.println(slika.getAbsolutePath());
				slika.createNewFile();
				//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
				try(OutputStream os = new FileOutputStream(slika))
				{
					os.write(slikaDTO.getSlika().getBytes());
					os.close();
					System.out.println("Ucitana slika korisnika: "+slikaDTO.getPutanja()+slikaDTO.getNazivSlike());
					vlasnikVikendice.setLinkSlike(putanjaSlikaKorisnika+slikaDTO.getNazivSlike());
				}
				catch(Exception e)
				{
					poruka = "Doslo je do greške kod učitavanja slike";
					model.addAttribute("poruka", poruka);
					return "/vikendice/pogresnaPoruka";
				}
	 		}
			System.out.println("Azuriranje Podataka page was called!");
	 		System.out.println(vlasnikVikendice);
	 		Korisnik podaci=korisnikServis.azurirajPodatkeVlasnika(vlasnikVikendice);
	 		model.addAttribute("vlasnikVikendice", podaci);
	 		System.out.println(model.toString());
	 		poruka = "Uspjesno azurirani podaci korisnika";
	 		model.addAttribute("poruka", poruka);
	 		return "/vikendice/potvrdnaPoruka";		
	 	  }
	
	@RequestMapping(value = "/promjenaLozinke/{ID}")
	 public String promjenaLozinke(Model model, @PathVariable Long ID){
	  		System.out.println("PromjenaLozinke page was called!");
	  		Korisnik korisnik=korisnikServis.findById(ID);
	  		VlasnikVikendiceDTO vlasnik = new VlasnikVikendiceDTO(korisnik);
	  		System.out.println(vlasnik);
	  		PromenaLozinkeDTO lozinke = new PromenaLozinkeDTO();
	  		model.addAttribute("vlasnikVikendice", vlasnik);
	  		model.addAttribute("lozinke", lozinke);
	  		System.out.println(model.toString());
	  		 return "/vikendice/promjenaLozinkeVlasnika";
	  	  }
	
	@RequestMapping(value ="/promijeniLozinku/{ID}")
	 public String promijeniLozinku(Model model, @PathVariable Long ID, String ime, String prezime, String korisnickoIme, PromenaLozinkeDTO lozinke )
	{
  		System.out.println("PromijeniLozinku page was called!");
  		Korisnik korisnik=korisnikServis.findById(ID);
  		model.addAttribute("vlasnikBroda", korisnik);
  		if(!lozinke.getStaraLozinka().equals(korisnik.getLozinka()))
  		{
  			model.addAttribute("poruka", "Pogrešan unos stare lozinke!");
  			return "/promjenaLozinkeNeuspjesna";
  		}
  		else if(lozinke.getNovaLozinka().equals(lozinke.getNovaPonovo()))
  		{
  			korisnikServis.changePassword(korisnik.getID(), lozinke.getStaraLozinka(), lozinke.getNovaLozinka());
  	  		return "/vikendice/azurirajPodatkeVlasnika.html";
  		}
  		else 
  		{
  			model.addAttribute("poruka", "Došlo je do greške kod unosa nove lozinke!");
  			return "/promjenaLozinkeNeuspjesna";
  		}
  		
  	  }
	
   @RequestMapping(value ="/napraviBrod/{ID}")
   public String napraviBrod(Model model, @PathVariable Long ID)
   {
	   System.out.println("Pravljenje broda was called!");
	   Korisnik vlasnik = korisnikServis.findById(ID);
	   BrodDTO brod = new BrodDTO();
	   model.addAttribute("brod", brod);
	   model.addAttribute("vlasnikBroda", vlasnik);
	   return "/brodovi/napraviBrod.html";
   }
   
   @RequestMapping(value ="/izmijeniBrod/{vlasnikID}/{brodID}")
   public String izmijeniBrod(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID)
   {
	   System.out.println("Izmjena broda was called!");
	   Korisnik vlasnik = korisnikServis.findById(vlasnikID);
	   Brod stariBrod = brodServis.findById(brodID);
	   System.out.println("Prikaz Slika1: "+stariBrod.getLinkSlike());
	   System.out.println("Prikaz Slika2: "+stariBrod.getLinkKabine());

	   model.addAttribute("slika1", stariBrod.getLinkSlike());
	   model.addAttribute("slika2", stariBrod.getLinkKabine());
	   model.addAttribute("brod", stariBrod);
	   model.addAttribute("vlasnikBroda", vlasnik);
	   
	   return "/brodovi/izmijeniBrod.html";
   }
   
   @RequestMapping(value ="/obrisiBrod/{vlasnikID}/{brodID}")
   public String obrisiVikendicu(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID)
   {
	   System.out.println("Brisanje vikendice was called!");
	   Korisnik vlasnik = korisnikServis.findById(vlasnikID);
	   Brod brod = brodServis.findById(brodID);
	   model.addAttribute("brod", brod);
	   model.addAttribute("vlasnikBroda", vlasnik);
	   return "/brodovi/obrisiBrod.html";
   }

   @RequestMapping(value ="/profilMogBroda/{vlasnikID}/{brodID}")
   public String profilMogBroda(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID)
   {
	   System.out.println("Profil broda was called!");
	   Korisnik vlasnik = korisnikServis.findById(vlasnikID);
	   Brod brod = brodServis.findById(brodID);
	   System.out.println("Prikaz Slika1: "+brod.getLinkSlike());
	   System.out.println("Prikaz Slika2: "+brod.getLinkKabine());

	   model.addAttribute("slika1", brod.getLinkSlike());
	   model.addAttribute("slika2", brod.getLinkKabine());
	   model.addAttribute("brod", brod);
	   model.addAttribute("vlasnikBroda", vlasnik);
	   
	   return "/brodovi/profilMogBroda.html";
   }
   

   @RequestMapping(value="/moji-klijenti/{vlasnikID}")
   public String mojiKlijenti(Model model, @PathVariable Long vlasnikID)
   {
	   System.out.println("Prikaz mojih klijenata!");
	   Korisnik vlasnik = korisnikServis.findById(vlasnikID);
	   List<KlijentSpisakDTO> mojiKlijenti = rezervacijaServis.nadjiKlijenteVlasnikaBroda(vlasnik);
	   

	   model.addAttribute("vlasnikBroda", vlasnik);
	   model.addAttribute("mojiKlijenti", mojiKlijenti);
	   
	   return "/brodovi/klijentiMojihBrodova.html";
	   
   }
   
   
   @RequestMapping(value = "/izvjestajiPoslovanja/{vlasnikID}")
	public String izvjestajiPoslovanja(Model model, @PathVariable Long vlasnikID) 
	{
		System.out.println("Izvjestaji poslovanja page was called!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanja = brodServis.izracunajSedmicnaPoslovanjaBrodova(vlasnik);
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanja = brodServis.izracunajMjesecnaPoslovanjaBrodova(vlasnik);
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanja = brodServis.izracunajGodisnjaPoslovanjaBrodova(vlasnik);
		model.addAttribute("sedmicnaPoslovanja", sedmicnaPoslovanja);
		model.addAttribute("mjesecnaPoslovanja", mjesecnaPoslovanja);
		model.addAttribute("godisnjaPoslovanja", godisnjaPoslovanja);
		
		return "/brodovi/izvjestajiOposlovanjuBrodova.html";
	}
	
	@RequestMapping(value = "/izvjestajPoslovanjaPeriod/{vlasnikID}")
	public String izvjestajPoslovanjaPeriod(Model model, @PathVariable Long vlasnikID, PoslovanjeEntitetaDTO poslovanje) 
	{
		System.out.println("Izvjestaji poslovanja period page was called!");
		System.out.println("pocetak: "+poslovanje.getPocetniDatum());
		System.out.println("kraj: "+poslovanje.getKrajnjiDatum());
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		poslovanje.srediDatume();
		List<PoslovanjeEntitetaDTO> poslovanjaBrodova = brodServis.poslovanjeBrodovaPeriod(poslovanje, vlasnik);
		model.addAttribute("poslovanja", poslovanjaBrodova);
		model.addAttribute("period", poslovanje);
		for(int i = 0; i< poslovanjaBrodova.size(); i++) System.out.println(poslovanjaBrodova.get(i));
		return "/brodovi/izvjestajPoslovanjaPeriod.html";
	}
	
	@RequestMapping(value = "/izvjestajiRezervacija/{vlasnikID}")
	public String izvjestajiRezervacija(Model model, @PathVariable Long vlasnikID) 
	{
		System.out.println("Izvjestaji rezervacija page was called!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		List<IzvjestajRezervacijaDTO> rezervacijeBezIzvjestaja = rezervacijaServis.nadjiRezervacijeBezIzvjestaja(TipEntiteta.brod, vlasnik);
		List<IzvjestajRezervacijaDTO> rezervacijeSaIzvjestajem = rezervacijaServis.nadjiRezervacijeSaIzvjestajem(TipEntiteta.brod, vlasnik);
		
		model.addAttribute("rezervacijeBez", rezervacijeBezIzvjestaja);
		model.addAttribute("rezervacijeSa", rezervacijeSaIzvjestajem);
		
		return "/brodovi/izvjestajiOrezervacijamaBrodova.html";
	}
	
	@RequestMapping(value = "/napisiIzvjestajRezervacije/{vlasnikID}/{rezID}")
	public String izvjestajiRezervacija(Model model, @PathVariable Long vlasnikID, @PathVariable Long rezID) 
	{
		System.out.println("Napisi izvjestaj page was called!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Rezervacija rezervacija = rezervacijaServis.findById(rezID);
		IzvjestajRezervacijaDTO izvjestaj = new IzvjestajRezervacijaDTO(rezervacija);
		
		model.addAttribute("rezervacija", izvjestaj);
		
		return "/brodovi/napisiIzvjestajRezervacije.html";
	}
	
	@RequestMapping(value = "/upisIzvjestajaRezervacija/{vlasnikID}/{rezID}", method=RequestMethod.POST)
	public String upisIzvjestajaRezervacija(Model model, @PathVariable Long vlasnikID, @PathVariable Long rezID, IzvjestajRezervacijaDTO izvjestaj) 
	{
		System.out.println("Unos izvjestaja page was called!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Rezervacija rezervacija = rezervacijaServis.findById(rezID);
		rezervacija.setIzvjestaj(izvjestaj.getIzvjestaj());
		boolean uspjesan = rezervacijaServis.upisiRezervaciju(rezervacija);
		if(uspjesan)
		{
			model.addAttribute("poruka", "Izvještaj uspješno dodat!");
			return "/vikendice/potvrdnaPoruka.html";
		}
		else
		{
			model.addAttribute("poruka", "Došlo je do greške prilikom upisa!");
			return "/vikendice/pogresnaPoruka.html";
		}
	}
	
	@RequestMapping(value = "/prikaziIzvjestajRezervacije/{vlasnikID}/{rezID}")
	public String prikaziIzvjestajRezervacije(Model model, @PathVariable Long vlasnikID, @PathVariable Long rezID) 
	{
		System.out.println("Prikazi izvjestaj page was called!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Rezervacija rezervacija = rezervacijaServis.findById(rezID);
		IzvjestajRezervacijaDTO izvjestaj = new IzvjestajRezervacijaDTO(rezervacija);
		
		model.addAttribute("rezervacija", izvjestaj);
		
		return "/brodovi/prikaziIzvjestajRezervacije.html";
	}
	
	@RequestMapping(value = "/brisanjeNaloga/{vlasnikID}")
	public String formaZaBrisanje(Model model, @PathVariable Long vlasnikID) throws IOException
	{		
		System.out.println("Obrisi nalog zahtjev called!");
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		BrisanjeKorisnikaZahtjevDTO zahtjev = new BrisanjeKorisnikaZahtjevDTO();
		model.addAttribute("zahtjev", zahtjev);
		
		return "/brodovi/brisanjeNaloga.html";
	}
	
	@RequestMapping(value = "/obrisiNalog/zahtjev/{vlasnikID}")
	public String zahtjevZaBrisanje(Model model, @PathVariable Long vlasnikID, BrisanjeKorisnikaZahtjevDTO zahtjev) throws IOException
	{		
		System.out.println("Obrisi nalog zahtjev called!");
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		
		//TODO: zastita od brisanja ukoliko postoje rezervacije?s
		
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		
		String poruka[] = korisnikServis.posaljiZahtjevZaBrisanje(vlasnik, zahtjev);
		model.addAttribute("poruka", poruka[0]);
		
		if(poruka[1].toLowerCase().equals("success"))
		{
			return "/vikendice/potvrdnaPoruka.html";
		}
		else return "/vikendice/pogresnaPoruka.html";
		//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
	}
	
	@RequestMapping(value = "/osvjeziTermine/{vlasnikID}")
	public String osvjeziTermine(Model model, @PathVariable Long vlasnikID) 
	{
		System.out.println("Osvjezi termine page was called!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		List<Rezervacija> rezervacije = rezervacijaServis.pronadjiRezervacijePoVlasniku(vlasnik, TipEntiteta.brod);
		rezervacije.addAll(rezervacijaServis.pronadjiBrzeRezervacijePoVlasniku(vlasnik, TipEntiteta.brod));
		boolean uspjesan = rezervacijaServis.popraviTermineRezervacija(rezervacije);
		
		if(uspjesan)
		{
			model.addAttribute("poruka", "Termini uspješno popravljeni!");
			return "/vikendice/potvrdnaPoruka.html";
		}
		else
		{
			model.addAttribute("poruka", "Došlo je do greške prilikom upisa!");
			return "/vikendice/pogresnaPoruka.html";
		}
	}
	
	@RequestMapping(value = "/periodiZauzetosti/{vlasnikID}/{vikID}")
	public String spisakPeriodaZauzetosti(Model model, @PathVariable Long vlasnikID, @PathVariable Long vikID)
	{
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Brod brod = brodServis.findById(vikID);
		model.addAttribute("brod", brod);
		
		List<PeriodPrikazDTO> periodi = brodServis.dobaviPeriode(brod);
		model.addAttribute("periodi", periodi);
		
		List<PeriodPrikazDTO> termini = brodServis.dobaviTermineBrzihRezervacija(brod);
		model.addAttribute("termini", termini);
		return "/brodovi/periodiDostupnostiBroda.html";
	}
	
	@RequestMapping(value = "/kalendarZauzetosti/{vlasnikID}/{vikID}")
	public String kalendarTerminaZauzetosti(Model model, @PathVariable Long vlasnikID, @PathVariable Long vikID)
	{
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Brod brod = brodServis.findById(vikID);
		model.addAttribute("brod", brod);
		
		List<PeriodPrikazDTO> periodi = brodServis.dobaviPeriode(brod);
		model.addAttribute("periodi", periodi);
		
		List<PeriodPrikazDTO> termini = brodServis.dobaviTermine(brod);
		Collections.sort(termini, Comparator.comparing(PeriodPrikazDTO::getDatumPocetka));
		model.addAttribute("termini", termini);
		return "/brodovi/kalendarZauzetostiBroda.html";
	}
	
	@RequestMapping(value="/napraviBrzuRezervaciju/{vlasnikID}/{brodID}")
	public String napraviBrzuRezervaciju(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID, BrzaRezervacijaDTO rezervacija)
	{
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Brod brod = brodServis.findById(brodID);
		model.addAttribute("brod", brod);
		
		rezervacija.srediDatume();
		Rezervacija rez = rezervacijaServis.napraviBrzuRezervaciju(rezervacija, TipEntiteta.brod);
		terminServis.popraviTerminRezervacije(rez);
		if(rez!=null)
		{
			model.addAttribute("poruka", "Brza rezervacija uspješna!");
			return "/vikendice/potvrdnaPoruka.html";
		}
		else
		{
			model.addAttribute("poruka", "Došlo je do greške prilikom upisa!");
			return "/vikendice/pogresnaPoruka.html";
		}
	}
	
	@RequestMapping(value="/dodajPeriodUbazu/{vlasnikID}/{brodID}")
	public String dodajPeriod(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID, PeriodPrikazDTO period)
	{
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Brod brod = brodServis.findById(brodID);
		model.addAttribute("brod", brod);
		
		Termin rez = this.terminServis.napraviPeriodDostupnosti(period, TipEntiteta.brod, vlasnik);
		terminServis.popraviPeriodeBroda(brod);
		if(rez!=null)
		{
			model.addAttribute("poruka", "Brza rezervacija uspješna!");
			return "/vikendice/potvrdnaPoruka.html";
		}
		else
		{
			model.addAttribute("poruka", "Došlo je do greške prilikom upisa!");
			return "/vikendice/pogresnaPoruka.html";
		}
	}
/*
	@RequestMapping(value = "/admin/reports")
	public String getReportsDates() 
	{
		System.out.println("Report page was called!");
		return "adminReports";
	}

	@GetMapping("/admin/reports/print")
    public void exportToPDF(HttpServletResponse response, @RequestParam String pocDatum, @RequestParam String krajDatum) throws DocumentException, IOException, ParseException 
	{
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        Date pocetni =new SimpleDateFormat("dd/MM/yyyy").parse(pocDatum);  
        Date krajnji =new SimpleDateFormat("dd/MM/yyyy").parse(krajDatum); 
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=prihodi_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Prihod> lista = prihodServis.listAll();
         
        PrihodPDFGenerator pdf = new PrihodPDFGenerator(lista, pocetni, krajnji);
        pdf.export(response);
         
    }
/*
	private void sendEmailToUser(Boolean prihvacen, String razlog, String mail) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mrs.isa.test@gmail.com", "123456789mrs.");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("mrs.isa.test@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject("Zahtev za registraciju");
		msg.setContent("Zahtev za registraciju", "text/html");
		msg.setSentDate(new Date());
		MimeBodyPart messageAccept = new MimeBodyPart();
		MimeBodyPart messageDenyReason = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		if(prihvacen) {
			msg.setSubject("Zahtev za registraciju");
			msg.setContent("Zahtev za registraciju", "text/html");
			
			messageAccept.setContent("Cestitamo! Vas zahtev za registraciju je odobren! Mozete se ulogovati vec danas.", "text/html");

			
			multipart.addBodyPart(messageAccept);
		}
		else {
			messageAccept.setContent("Zao nam je. Vas zahtev za registraciju je odbijen.", "text/html");
			messageDenyReason.setContent("Razlog odbijanja: " + razlog, "text/html");

			multipart.addBodyPart(messageAccept);
			multipart.addBodyPart(messageDenyReason);
		}

		msg.setContent(multipart);
		
		Transport.send(msg);
	}
	*/
}
