package com.Reservations.Kontroleri;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.VikendicaServis;

@Controller
public class VikendicePonude {
	@Autowired
	VikendicaServis vikendicaServis;
		
	@Autowired
	KorisnikServis korServis;
	
	
	@RequestMapping(value = "/prikazVikendica/{id}")
	  public String getTestPage(Model model,@PathVariable Long id){
		System.out.println("PrikazVikendica page was called!");
		List<Vikendica>vikendice=vikendicaServis.listAll();
		Korisnik k=korServis.findById(id);
		model.addAttribute("vikendice", vikendice);
		model.addAttribute("kor",k);
		
		List<Vikendica>vik1=vikendicaServis.VikSortCena();
		List<Vikendica>vik2=vikendicaServis.VikSortNaziv();
		List<Vikendica>vik3=vikendicaServis.VikSortAdresa();
		for (Vikendica vikendica : vikendice) {
			System.out.println(vikendica.toString());
		}
		model.addAttribute("sortvikendice", vik1);
		model.addAttribute("sortviknaziv", vik2);
		model.addAttribute("sortvikadresa",vik3);
	   for (Vikendica vikendica : vik1) {
		   System.out.println(vikendica.toString());
		
	} for (Vikendica vikendica : vik2) {
		   System.out.println(vikendica.toString());
			
	}
	 for (Vikendica vikendica : vik3) {
		   System.out.println(vikendica.toString());
		
	}
	   
	      return "prikazVikendica";
	  }
	@RequestMapping(value = "/OsnovniprikazVikendica")
	  public String getOsnPage(Model model){
			System.out.println("OsnPrikazVikendica page was called!");
			List<Vikendica>vikendice=vikendicaServis.listAll();
			model.addAttribute("vikendice", vikendice);
			System.out.println(model.toString());
	      return "OsnovniPodaciVik";
	  }
	@RequestMapping(value = "/OSPretragaVik")
	  public String getOsPage(Model model,@RequestParam String s){
		System.out.println("PrikazBrodova page was called!");
		List<Vikendica>vikendice=vikendicaServis.VikendicaPretraga(s);
		model.addAttribute("vikendice", vikendice);
		
	      return "OsnovniPodaciVik";
	  }
	@RequestMapping(value = "/PretragaVik/{id}")
	  public String getOsPage(Model model,@PathVariable Long id,@RequestParam String s){
		System.out.println("PrikazBrodova page was called!");
		   try {
		List<Vikendica>vikendice=vikendicaServis.VikendicaPretraga(s);
		Korisnik k=korServis.findById(id);
		model.addAttribute("vikendice", vikendice);
		model.addAttribute("kor",k);
		
		List<Vikendica>vik1=vikendicaServis.VikSortCena();
		List<Vikendica>vik2=vikendicaServis.VikSortNaziv();
		List<Vikendica>vik3=vikendicaServis.VikSortAdresa();
		   
		model.addAttribute("sortvikendice", vik1);
		model.addAttribute("sortviknaziv", vik2);
		model.addAttribute("sortvikadresa",vik3);
		System.out.println(model.toString());
		   } catch (DateTimeParseException e) {
				return "pogresanUnos";
		    }
	      return "prikazVikendica";
	  }
		
	@RequestMapping(value = "/FilterVik/{id}")
	  public String getOPage(Model model,@PathVariable Long id,@RequestParam String brodovifil){
		System.out.println("PrikazBrodova page was called!");
		List<Vikendica>br=vikendicaServis.VikFilter(brodovifil);
		System.out.println("velicina:"+br.size());
		System.out.println(br.toString());
		//List<Brod>brodovi=brodServis.BrodPretraga(s);
		Korisnik k=korServis.findById(id);
		model.addAttribute("kor", k);
		model.addAttribute("vikendice", br);
		System.out.println(model.toString());
		List<Vikendica>vik1=vikendicaServis.VikSortCena();
		List<Vikendica>vik2=vikendicaServis.VikSortNaziv();
		List<Vikendica>vik3=vikendicaServis.VikSortAdresa();
		
		model.addAttribute("sortvikendice", vik1);
		model.addAttribute("sortviknaziv", vik2);
		model.addAttribute("sortvikadresa",vik3);
		
	      return "prikazVikendica";
	  }
	   
}
