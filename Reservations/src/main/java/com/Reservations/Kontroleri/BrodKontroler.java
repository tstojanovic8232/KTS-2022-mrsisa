package com.Reservations.Kontroleri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.Reservations.DTO.BrodDTO;
import com.Reservations.DTO.SlikaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Servis.BrodServis;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RezervacijaServis;
@Controller
public class BrodKontroler {
	@Autowired
	BrodServis uslugaServis;

	@Autowired
	KorisnikServis korisnikServis;
	

	

	@Autowired
	BrodServis brodServis;
	
	@Autowired
	RezervacijaServis rezervacijaServis;
	public String putanjaSlikaBrodova = "/img/brodovi/";
	
	//@RequestMapping(value = "/klijent/brod/{id}")
    @RequestMapping(value = "/brod/{id}")
	public String getProfilePage(Model model, @PathVariable Long id) {
		System.out.println("BrodKlijentProfil page was called!");
		Brod usluga = uslugaServis.findById(id);
		List<Brod> lista = uslugaServis.findByVlasnik(4L);
		model.addAttribute("brod", usluga);
		model.addAttribute("brodVlas", lista);
		return "brodOsnovniProfil";
	}
	
	@RequestMapping(value = "/brod/{id}/{id2}")
	public String getUnauthServicePage(Model model, @PathVariable Long id,@PathVariable Long id2) {
		System.out.println("BrodProfil page was called!");
		Brod usluga = uslugaServis.findById(id);
		Korisnik k=korisnikServis.findById(id2);
	
		List<String>li=rezervacijaServis.findByTerminBroda(usluga);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		List<LocalDate>d=new ArrayList<LocalDate>();
		List<String>datumi=new ArrayList<String>();
		
		for (String string : li) {
			d.add(LocalDate.parse(string,dtf2));
		}
		for (LocalDate localDate : d) {
			datumi.add(localDate.format(dtf));
		}
		System.out.println(datumi);
		//LocalDate.parse(datumi.toString(),dtf);
		
		model.addAttribute("datumi",datumi);
		List<Brod> listabrd = brodServis.findByVlasnik(usluga.getVlasnik().getID());
		model.addAttribute("brod", usluga);
		model.addAttribute("brodVlas", listabrd);
		model.addAttribute("kor",k);
		System.out.println(model.toString());
		return "ProfilBroda";
	}
	
	@RequestMapping(value = "/brodovi/napravi/{vlasnikID}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String napraviBrod(SlikaDTO slikaDTO, MultipartFile slikaInterijera, String putanja2, String nazivSlike2, Model model, @PathVariable Long vlasnikID, BrodDTO noviBrod) throws IOException
	{
		String[] poruka = new String[2];
		List<SlikaDTO> slikeDTO = new ArrayList<SlikaDTO>();
		if(!slikaDTO.getNazivSlike().trim().equals(""))slikeDTO.add(slikaDTO);
		if(!nazivSlike2.trim().equals(""))slikeDTO.add(new SlikaDTO(putanja2, nazivSlike2, slikaInterijera));
		System.out.println("Napravi Brod called!");
		System.out.println("slika2 vrijendost: "+ nazivSlike2);
		System.out.println(slikaDTO.getNazivSlike());
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		System.out.println("Usao u snimi");
		File slika;
		Brod duplikat = brodServis.pronadjiPoNazivu(noviBrod.getNaziv());
		if(duplikat==null)
		{
			noviBrod.setVlasnik(vlasnikID);
			if(slikeDTO.size()>0)
			{
				for(int i=0; i<slikeDTO.size(); i++)
				{
					String apsolutnaPutanja= (new File("src/main/resources/static")).getAbsolutePath();
					slika = new File(apsolutnaPutanja+this.putanjaSlikaBrodova+slikeDTO.get(i).getNazivSlike());
					System.out.println(slika.getAbsolutePath());
					slika.createNewFile();
					
					System.out.println("brod: " + noviBrod);
					
					//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
					try(OutputStream os = new FileOutputStream(slika))
					{
						os.write(slikeDTO.get(i).getSlika().getBytes());
						os.close();
						noviBrod.setVlasnik(vlasnikID);
						if(i==0)noviBrod.setLinkSlike(this.putanjaSlikaBrodova+slikaDTO.getNazivSlike());
						if(i==1)noviBrod.setLinkKabine(this.putanjaSlikaBrodova+slikeDTO.get(i).getNazivSlike());
						System.out.println("Pravim brod:" + noviBrod);
						
						//TODO: Snimi sliku u bazu?
						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						poruka[0] = "Doslo je do greske u dodavanju!";
						poruka[1] = "IO error";
						model.addAttribute("poruka", poruka[0]);
						return "/vikendice/pogresnaPoruka";
					}
	
				}
			}

		
		poruka = brodServis.dodajBrod(noviBrod);
		model.addAttribute("poruka", poruka[0]);	
		
		if(poruka[1].equalsIgnoreCase("success"))return "/vikendice/potvrdnaPoruka";
		else return "/vikendice/pogresnaPoruka"; 
		}
		else
		{
			poruka[0] = "Brod sa tim nazivom već postoji!";
			poruka[1] = "duplicate";
			model.addAttribute("poruka", poruka[0]);
			return "/vikendice/pogresnaPoruka";
		}
	}
	
	@RequestMapping(value = "/brodovi"+"/izmijeni/{vlasnikID}/{brodID}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String izmijeniBrod(SlikaDTO slikaDTO, MultipartFile slikaInterijera, String putanja2, String nazivSlike2, Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID, BrodDTO noviBrod) throws IOException
	{	
		String poruka[] = new String[2];
		List<SlikaDTO> slikeDTO = new ArrayList<SlikaDTO>();
		if(!slikaDTO.getNazivSlike().trim().equals(""))slikeDTO.add(slikaDTO);
		if(!nazivSlike2.equals("")) slikeDTO.add(new SlikaDTO(putanja2, nazivSlike2, slikaInterijera));
		noviBrod.setID(brodID);
		Brod stariBrod = brodServis.findById(brodID);
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		System.out.println("Izmijeni Brod called!");
		System.out.println("Slika nazivi novi: "+nazivSlike2);
		System.out.println("nova: "+noviBrod.getID()==null);
		System.out.println("slika vrijendost: ");
		System.out.println(slikaDTO.getNazivSlike());
		
		model.addAttribute("vlasnikBroda", vlasnik);
		
		noviBrod.setVlasnik(vlasnikID);
		
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		for(int i =0; i<slikeDTO.size(); i++)if(slikaDTO.getNazivSlike()!=null && !slikaDTO.getNazivSlike().trim().equals(""))
		{
			String apsolutnaPutanja= (new File("src/main/resources/static")).getAbsolutePath();
			File slika = new File(apsolutnaPutanja+this.putanjaSlikaBrodova+slikeDTO.get(i).getNazivSlike());
			System.out.println(slika.getAbsolutePath());
			slika.createNewFile();
			System.out.println("Usao u snimi2");
			try(OutputStream os = new FileOutputStream(slika))
			{
				System.out.println("usao u try/catch");
				os.write(slikeDTO.get(i).getSlika().getBytes());
				os.close();
				
				if(i==0)noviBrod.setLinkSlike(this.putanjaSlikaBrodova+slikeDTO.get(i).getNazivSlike());
				if(i==1)noviBrod.setLinkKabine(this.putanjaSlikaBrodova+slikeDTO.get(i).getNazivSlike());
				System.out.println("Brod:" + noviBrod);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				model.addAttribute("poruka", "Doslo je do greske sa slikom broda!");
				return "/vikendice/pogresnaPoruka";
			}

		}
		poruka = brodServis.izmijeniBrod(noviBrod, stariBrod);
		model.addAttribute("poruka", poruka[0]);
		
		//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
		if(poruka[1].equalsIgnoreCase("success")) return "/vikendice/potvrdnaPoruka";
		else return "/vikendice/pogresnaPoruka";
	}
	
	@RequestMapping(value="/pretplataBrod/{id}/{id2}")
	public String vikendiceZaBrzeRezervacije(Model model, @PathVariable Long id,@PathVariable Long id2)
	{
		System.out.println("Brze rezervacije page!");
		Korisnik klijent = korisnikServis.findById(id);
		Brod brod=brodServis.findById(id2);
		Boolean uspelo = brodServis.dodajPretplatuNaBrod(klijent,brod);
		
		model.addAttribute("brodovi", brod);
		model.addAttribute("pod", klijent);
		
		return "redirect:/pretplata/"+String.valueOf(id);
	}
	
	@RequestMapping(value = "/brodovi"+"/obrisi/{vlasnikID}/{brodID}")
	public String obrisiBrod(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID) throws IOException
	{		
		System.out.println("Obrisi Brod called!");
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		System.out.println("Usao u snimi");
		//TODO: zastita od brisanja ukoliko postoje rezervacije?s
		
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		String poruka[] = brodServis.obrisiBrod(vlasnikID, brodID);
		
		model.addAttribute("poruka", poruka[0]);
		if(poruka[1].toLowerCase().equals("success"))
		{
			return "/vikendice/potvrdnaPoruka.html";
		}
		else return "/vikendice/pogresnaPoruka.html";
		//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
		
	}
	
	@RequestMapping(value="/brodovi"+"/brze-rezervacije/{vlasnikID}")
	public String vikendiceZaBrzeRezervacije(Model model, @PathVariable Long vlasnikID)
	{
		System.out.println("Brze rezervacije page!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		List<Brod> mojiBrodovi = brodServis.nadjiBrodovePoVlasniku(vlasnik);
		model.addAttribute("vlasnikBroda", vlasnik);
		model.addAttribute("brodovi", mojiBrodovi);
		return "/brodovi/brzeRezervacijeBrodova.html";
	}
	
	
	@RequestMapping(value="/brodovi"+"/brza-rezervacija/{vlasnikID}/{brodID}")
	public String brzaRezervacijaVikendice(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID)
	{
		System.out.println("Brzo-rezervisi page!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Brod brod = brodServis.findById(brodID);
		model.addAttribute("brod", brod);
		
		return "/brodovi/rezervacijaBrodaBrza.html";
	}
	
	@RequestMapping(value="/brodovi"+"/dodajPeriod/{vlasnikID}/{brodID}")
	public String dodajPeriod(Model model, @PathVariable Long vlasnikID, @PathVariable Long brodID)
	{
		System.out.println("Brzo-rezervisi page!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikBroda", vlasnik);
		
		Brod brod = brodServis.findById(brodID);
		model.addAttribute("brod", brod);
		
		return "/brodovi/napraviPeriodBroda.html";
	}
}
