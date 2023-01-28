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

import com.Reservations.DTO.SlikaDTO;
import com.Reservations.DTO.UslugaDTO;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Servis.BrodServis;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RezervacijaServis;
import com.Reservations.Servis.UslugaServis;

@Controller
public class UslugaKontroler {
	@Autowired
	UslugaServis uslugaServis;

	@Autowired
	KorisnikServis korisnikServis;
	
	public String putanjaSlika = "/img/usluge/";

	@Autowired
	RezervacijaServis rezervacijaServis;
	
	@RequestMapping(value = "/klijent/{klijent_id}/usluga/{id}")
	public String getProfilePage(Model model, @PathVariable Long id, @PathVariable Long klijent_id) {
		System.out.println("Usluga page was called!");
		Usluga usluga = uslugaServis.findById(id);
		Korisnik k = korisnikServis.findById(klijent_id);
		List<String>li=rezervacijaServis.findByTerminUsluge(usluga);
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
		
		
		List<Usluga> lista = uslugaServis.findByInstruktor(usluga.getInstruktor().getID());
		model.addAttribute("usluga", usluga);
		model.addAttribute("uslugeInst", lista);
		model.addAttribute("kor", k);
		return "usluge/uslugaProfil";
	}

	@RequestMapping(value = "/usluga/{id}")
	public String getUnauthServicePage(Model model, @PathVariable Long id) {
		System.out.println("Usluga page was called!");
		Usluga usluga = uslugaServis.findById(id);
		List<Usluga> lista = uslugaServis.findByInstruktor(usluga.getInstruktor().getID());
		model.addAttribute("usluga", usluga);
		model.addAttribute("uslugeInst", lista);
		return "usluge/uslugaOsnovniProfil";
	}

	@RequestMapping(value = "/instruktor/{id}/novaUsluga", method = RequestMethod.GET)
	public String addService(Model model, @PathVariable Long id) {
		System.out.println("Dodajemo uslugu!");
		return "usluge/dodajUslugu";
	}

	@RequestMapping(value = "/instruktor/{id}/novaUsluga/dodaj", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public String addServiceProcess(SlikaDTO slikaDTO, Model model, @PathVariable Long id, UslugaDTO usluga)
			throws IOException {
		System.out.println("Dodata usluga! " + usluga.toString());
		System.out.println("Napravi Vikendicu called!");
		System.out.println("slika vrednost: " + slikaDTO.getSlika());
		System.out.println(slikaDTO.getNazivSlike());
		// slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
		String apsolutnaPutanja = (new File("src/main/resources/static")).getAbsolutePath();
		File slika = new File(apsolutnaPutanja + this.putanjaSlika + slikaDTO.getNazivSlike());
		System.out.println(slika.getAbsolutePath());
		slika.createNewFile();
		System.out.println("Usao u snimi");

		try (OutputStream os = new FileOutputStream(slika)) {
			os.write(slikaDTO.getSlika().getBytes());
			os.close();
			Korisnik instruktor = korisnikServis.findById(id);
			usluga.setLinkSlike(this.putanjaSlika + slikaDTO.getNazivSlike());
			System.out.println(usluga.toString());
			uslugaServis.save(usluga, instruktor);
			return "redirect:/instruktor/" + String.valueOf(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/instruktor/" + String.valueOf(id) + "/novaUsluga";
		}
	}

	@RequestMapping(value = "/instruktor/{instruktor_id}/usluga/{id}")
	public String getServiceForOwner(Model model, @PathVariable Long id, @PathVariable Long instruktor_id) {
		System.out.println("Usluga page was called!");
		Usluga usluga = uslugaServis.findById(id);
		model.addAttribute("id", instruktor_id);
		model.addAttribute("usluga", usluga);
		return "usluge/uslugaProfilInstruktor";
	}

	@RequestMapping(value = "/instruktor/{instruktor_id}/usluga/{id}/izmeni")
	public String modifyServiceForOwner(Model model, @PathVariable Long id, @PathVariable Long instruktor_id) {
		System.out.println("Usluga page was called!");
		Usluga usluga = uslugaServis.findById(id);
		model.addAttribute("id", instruktor_id);
		model.addAttribute("usluga", usluga);
		return "usluge/izmeniUslugu";
	}
	@RequestMapping(value="/pretplataUsluga/{id}/{id2}")
	public String vikendiceZaBrzeRezervacije(Model model, @PathVariable Long id,@PathVariable Long id2)
	{
		System.out.println("Brze rezervacije page!");
		Korisnik klijent = korisnikServis.findById(id);
		Usluga brod=uslugaServis.findById(id2);
		Boolean uspelo = uslugaServis.dodajPretplatuNaUsluga(klijent,brod);
		
		model.addAttribute("usluge", brod);
		model.addAttribute("pod", klijent);
		return "redirect:/pretplata/"+String.valueOf(id);
		
	}
	@RequestMapping(value = "/instruktor/{instruktor_id}/usluga/{id}/izmena", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public String serviceModified(Model model, @PathVariable Long id, @PathVariable Long instruktor_id,
			UslugaDTO usluga, SlikaDTO slikaDTO) throws IOException {
		System.out.println("Dodata usluga! " + usluga.toString());
		System.out.println("Izmeni Uslugu called!");
		if (!slikaDTO.getNazivSlike().equals("")) {
			System.out.println(usluga.toString());
			System.out.println(slikaDTO.getNazivSlike());
			// slikaDTO.setNazivSlike(slikaDTO.getNazivSlike().split("\\")[2]);
			String apsolutnaPutanja = (new File("src/main/resources/static")).getAbsolutePath();
			File slika = new File(apsolutnaPutanja + this.putanjaSlika + slikaDTO.getNazivSlike());
			System.out.println(slika.getAbsolutePath());
			slika.createNewFile();
			System.out.println("Usao u snimi");

			try (OutputStream os = new FileOutputStream(slika)) {
				os.write(slikaDTO.getSlika().getBytes());
				os.close();
				usluga.setLinkSlike(this.putanjaSlika + slikaDTO.getNazivSlike());
				System.out.println(usluga.toString());
				uslugaServis.update(usluga, id);
				return "redirect:/instruktor/" + String.valueOf(instruktor_id) + "/usluga/" + String.valueOf(id);
			} catch (Exception e) {
				e.printStackTrace();
				return "/loginFailure";
			}
		} else {
			System.out.println("Izmena usluge bez slike!");
			usluga.setLinkSlike("");
			uslugaServis.update(usluga, id);
			return "redirect:/instruktor/" + String.valueOf(instruktor_id) + "/usluga/" + String.valueOf(id);
		}
	}

	@RequestMapping(value = "/instruktor/{instruktor_id}/usluga/{id}/obrisi")
	public String deleteServiceForOwner(Model model, @PathVariable Long id, @PathVariable Long instruktor_id) {
		System.out.println("Usluga page was called!");
		uslugaServis.deleteById(id);
		return "redirect:/instruktor/" + String.valueOf(instruktor_id);
	}
}
