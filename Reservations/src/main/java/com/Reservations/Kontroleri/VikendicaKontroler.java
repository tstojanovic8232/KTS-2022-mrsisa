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

import com.Reservations.DTO.SlikaDTO;
import com.Reservations.DTO.VikendicaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;
import com.Reservations.Repozitorijumi.VikendicaRepozitorijum;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RezervacijaServis;
import com.Reservations.Servis.SnimanjeDatotekaServis;
import com.Reservations.Servis.VikendicaServis;

@Controller
public class VikendicaKontroler 
{
	@Autowired
	VikendicaServis vikendicaServis;

	@Autowired
	KorisnikServis korisnikServis;
	
	@Autowired
	RezervacijaRepozitorijum rezervacijaRepozitorijum;
	
	@Autowired
	SnimanjeDatotekaServis snimanjeDatotekaServis;
	
	@Autowired
	RezervacijaServis rezervacijaServis;
	
	public String putanjaSlika = "/img/vikendice/";

	@Autowired
	private VikendicaRepozitorijum vikendicaRepozitorijum;
	
	@RequestMapping(value = "/klijent/vikendice/{id}")
	public String getProfilePage(Model model, @PathVariable Long id) {
		System.out.println("BrodKlijentProfil page was called!");
		Vikendica usluga = vikendicaServis.findById(id);
		List<Vikendica> lista = vikendicaServis.findByVlasnik(3L);
		model.addAttribute("vik", usluga);
		model.addAttribute("vikVlas", lista);
		return "VikOsnovniProfil";
	}
	
	@RequestMapping(value = "/vikendice/{id}")
	public String getUnauthServicePage(Model model, @PathVariable Long id) {
		System.out.println("BrodProfil page was called!");
		Vikendica usluga = vikendicaServis.findById(id);
		List<Vikendica> lista = vikendicaServis.findByVlasnik(3L);
		model.addAttribute("vik", usluga);
		model.addAttribute("vikVlas", lista);
		System.out.println(model.toString());
		return "VikOsnovniProfil";
	}
	

	@RequestMapping(value = "/vikendice/napravi/{vlasnikID}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String napraviVikendicu(SlikaDTO slikaDTO, MultipartFile slikaInterijera, String putanja2, String nazivSlike2, Model model, @PathVariable Long vlasnikID, VikendicaDTO novaVikendica) throws IOException
	{
		String[] poruka = new String[2];
		List<SlikaDTO> slikeDTO = new ArrayList<SlikaDTO>();
		if(!slikaDTO.getNazivSlike().trim().equals(""))slikeDTO.add(slikaDTO);
		if(!nazivSlike2.trim().equals(""))slikeDTO.add(new SlikaDTO(putanja2, nazivSlike2, slikaInterijera));
		System.out.println("Napravi Vikendicu called!");
		System.out.println("slika2 vrijendost: "+ nazivSlike2);
		System.out.println(slikaDTO.getNazivSlike());
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikVikendice", vlasnik);
		System.out.println("Usao u snimi");
		File slika;
		Vikendica duplikat = vikendicaRepozitorijum.findByNaziv(novaVikendica.getNaziv());
		if(duplikat==null)
		{
			novaVikendica.setVlasnik(vlasnikID);
			if(slikeDTO.size()>0)
			{
				for(int i=0; i<slikeDTO.size(); i++)
				{
					String apsolutnaPutanja= (new File("src/main/resources/static")).getAbsolutePath();
					slika = new File(apsolutnaPutanja+this.putanjaSlika+slikeDTO.get(i).getNazivSlike());
					System.out.println(slika.getAbsolutePath());
					slika.createNewFile();
					
					System.out.println("Vikendica:" + novaVikendica);
					
					//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
					try(OutputStream os = new FileOutputStream(slika))
					{
						os.write(slikeDTO.get(i).getSlika().getBytes());
						os.close();
						novaVikendica.setVlasnik(vlasnikID);
						if(i==0)novaVikendica.setLinkSlike(this.putanjaSlika+slikaDTO.getNazivSlike());
						if(i==1)novaVikendica.setLinkInterijera(this.putanjaSlika+slikeDTO.get(i).getNazivSlike());
						System.out.println("Vikendica:" + novaVikendica);
						
						//TODO: Snimi sliku u bazu?
						
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
						poruka[0] = "Doslo je do greske u dodavanju!";
						poruka[1] = "IO error";
						model.addAttribute("poruka", poruka);
						return "/vikendice/pogresnaPoruka";
					}
	
				}
			}
			
			poruka = vikendicaServis.dodajVikendicu(novaVikendica);
			model.addAttribute("poruka", poruka[0]);	
			
			if(poruka[1].equalsIgnoreCase("success"))return "/vikendice/potvrdnaPoruka";
			else return "/vikendice/pogresnaPoruka"; 
		}
		else
		{
			poruka[0] = "Vikendica sa tim nazivom već postoji!";
			poruka[1] = "duplicate";
			model.addAttribute("poruka", poruka[0]);
			return "/vikendice/pogresnaPoruka";
		}
	}
	
	@RequestMapping(value = "/vikendice/izmijeni/{vlasnikID}/{vikendicaID}", method=RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String izmijeniVikendicu(SlikaDTO slikaDTO, MultipartFile slikaInterijera, String putanja2, String nazivSlike2, Model model, @PathVariable Long vlasnikID, @PathVariable Long vikendicaID, VikendicaDTO novaVikendica) throws IOException
	{	
		String poruka[] = new String[2];
		List<SlikaDTO> slikeDTO = new ArrayList<SlikaDTO>();
		if(!slikaDTO.getNazivSlike().trim().equals(""))slikeDTO.add(slikaDTO);
		if(!nazivSlike2.equals("")) slikeDTO.add(new SlikaDTO(putanja2, nazivSlike2, slikaInterijera));
		novaVikendica.setID(vikendicaID);
		Vikendica staraVikendica = vikendicaServis.findById(vikendicaID);
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		System.out.println("Izmijeni Vikendicu called!");
		System.out.println("Slika nazivi novi: "+nazivSlike2);
		System.out.println("nova: "+novaVikendica.getID()==null);
		System.out.println("slika vrijendost: ");
		System.out.println(slikaDTO.getNazivSlike());
		
		model.addAttribute("vlasnikVikendice", vlasnik);
		
		novaVikendica.setVlasnik(vlasnikID);
		
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		for(int i =0; i<slikeDTO.size(); i++)if(slikaDTO.getNazivSlike()!=null && !slikaDTO.getNazivSlike().trim().equals(""))
		{
			String apsolutnaPutanja= (new File("src/main/resources/static")).getAbsolutePath();
			File slika = new File(apsolutnaPutanja+this.putanjaSlika+slikeDTO.get(i).getNazivSlike());
			System.out.println(slika.getAbsolutePath());
			slika.createNewFile();
			System.out.println("Usao u snimi2");
			try(OutputStream os = new FileOutputStream(slika))
			{
				System.out.println("usao u try/catch");
				os.write(slikeDTO.get(i).getSlika().getBytes());
				os.close();
				
				if(i==0)novaVikendica.setLinkSlike(this.putanjaSlika+slikeDTO.get(i).getNazivSlike());
				if(i==1)novaVikendica.setLinkInterijera(this.putanjaSlika+slikeDTO.get(i).getNazivSlike());
				System.out.println("Vikendica:" + novaVikendica);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				model.addAttribute("poruka", "Doslo je do greske sa slikom vikendice!");
				return "/vikendice/pogresnaPoruka";
			}

		}
		/*
		if(!nazivSlike2.trim().equals("") && nazivSlike2!=null)
		{
			System.out.println("Usao u snimi3");
			System.out.println("Vikendica:" + novaVikendica);
			String apsolutnaPutanja= (new File("src/main/resources/static")).getAbsolutePath();
			File slika = new File(apsolutnaPutanja+this.putanjaSlika+nazivSlike2);
			System.out.println(slika.getAbsolutePath());
			slika.createNewFile();
			System.out.println("Usao u snimi2");
			try(OutputStream os = new FileOutputStream(slika))
			{
				System.out.println("usao u try/catch");
				os.write(slikaInterijera.getBytes());
				os.close();
				
				novaVikendica.setLinkInterijera(this.putanjaSlika+nazivSlike2);
				System.out.println("Vikendica:" + novaVikendica);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				model.addAttribute("poruka", "Doslo je do greske sa slikom interijera!");
				return "/vikendice/pogresnaPoruka";
			}
			
		}
		*/
		poruka = vikendicaServis.izmijeniVikendicu(novaVikendica, staraVikendica);
		model.addAttribute("poruka", poruka[0]);
		
		//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
		if(poruka[1].equalsIgnoreCase("success")) return "/vikendice/potvrdnaPoruka";
		else return "/vikendice/pogresnaPoruka";
	}
	
	@RequestMapping(value = "/vikendice/obrisi/{vlasnikID}/{vikendicaID}")
	public String obrisiVikendicu(Model model, @PathVariable Long vlasnikID, @PathVariable Long vikendicaID) throws IOException
	{		
		System.out.println("Obrisi Vikendicu called!");
		//slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		System.out.println("Usao u snimi");
		//TODO: zastita od brisanja ukoliko postoje rezervacije?s
		
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikVikendice", vlasnik);
		String poruka[] = vikendicaServis.obrisiVikendicu(vlasnikID, vikendicaID);
		
		model.addAttribute("poruka", poruka[0]);
		if(poruka[1].toLowerCase().equals("success"))
		{
			return "/vikendice/potvrdnaPoruka.html";
		}
		else return "/vikendice/pogresnaPoruka.html";
		//TODO:upis u bazu snimanjeDatotekaServis.snimiSlikuVikendice(slikaDTO);
		
		
	}

	@RequestMapping(value = "/Regvikendice/{id}/{id2}")
	public String getAuthServicePage(Model model, @PathVariable Long id,@PathVariable Long id2) 
	{
		System.out.println("ProfilVikendice page was called!");
		Korisnik k=korisnikServis.findById(id2);
		Vikendica usluga = vikendicaServis.findById(id);
		List<String>li=rezervacijaServis.findByTerminVikendice(usluga);
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
		
		//LocalDate.parse(datumi.toString(),dtf);
		
		model.addAttribute("datumi",datumi);
		List<Vikendica> lista = vikendicaServis.findByVlasnik(usluga.getVlasnik().getID());
		model.addAttribute("vik", usluga);
		model.addAttribute("vikVlas", lista);
		model.addAttribute("kor",k);
		
		return "ProfilVikendica";

	}
	@RequestMapping(value="/pretplataVik/{id}/{id2}")
	public String vikendiceZaBrzeRezervacije(Model model, @PathVariable Long id,@PathVariable Long id2)
	{
		System.out.println("Brze rezervacije page!");
		Korisnik klijent = korisnikServis.findById(id);
		Vikendica vik=vikendicaServis.findById(id2);
		Boolean uspelo = vikendicaServis.dodajPretplatuNaVik(klijent,vik);
		System.out.println(uspelo);
		model.addAttribute("vikendica", vik);
		model.addAttribute("pod",klijent );
		return "redirect:/pretplata/"+String.valueOf(id);
	}
	
	
	
	@RequestMapping(value="/vikendice"+"/brze-rezervacije/{vlasnikID}")
	public String vikendiceZaBrzeRezervacije(Model model, @PathVariable Long vlasnikID)
	{
		System.out.println("Brze rezervacije page!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		List<Vikendica> mojeVikendice = vikendicaServis.nadjiVikendicePoVlasniku(vlasnik);
		model.addAttribute("vlasnikVikendice", vlasnik);
		model.addAttribute("vikendice", mojeVikendice);
		return "/vikendice/brzeRezervacijeVikendica.html";
	}
	
	@RequestMapping(value="/vikendice"+"/brza-rezervacija/{vlasnikID}/{vikendicaID}")
	public String brzaRezervacijaVikendice(Model model, @PathVariable Long vlasnikID, @PathVariable Long vikendicaID)
	{
		System.out.println("Brzo-rezervisi page!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikVikendice", vlasnik);
		
		Vikendica vikendica = vikendicaServis.findById(vikendicaID);
		model.addAttribute("vikendica", vikendica);
		
		return "/vikendice/rezervacijaVikendiceBrza.html";
	}
	@RequestMapping(value="/vikendice"+"/dodajPeriod/{vlasnikID}/{vikID}")
	public String dodajPeriod(Model model, @PathVariable Long vlasnikID, @PathVariable Long vikID)
	{
		System.out.println("Brzo-rezervisi page!");
		Korisnik vlasnik = korisnikServis.findById(vlasnikID);
		model.addAttribute("vlasnikVikendice", vlasnik);
		
		Vikendica vikendica = vikendicaServis.findById(vikID);
		model.addAttribute("vikendica", vikendica);
		
		return "/vikendice/napraviPeriodVikendice.html";
	}
	
}
