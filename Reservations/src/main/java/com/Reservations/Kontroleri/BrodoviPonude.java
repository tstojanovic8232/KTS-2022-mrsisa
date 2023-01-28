package com.Reservations.Kontroleri;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Servis.BrodServis;
import com.Reservations.Servis.KorisnikServis;
@Controller
public class BrodoviPonude {
	@Autowired
	BrodServis brodServis;
		
	@Autowired
	KorisnikServis korServis;
	@RequestMapping(value = "/prikazBrodova/{id}")
	  public String getTestPage(Model model,@PathVariable Long id){
		System.out.println("PrikazBrodova page was called!");
		List<Brod>brodovi=brodServis.listAll();
		Korisnik k=korServis.findById(id);
		model.addAttribute("kor", k);
		model.addAttribute("brodovi", brodovi);
		System.out.println(model.toString());
		List<Brod>brod=brodServis.BrodSortCena();
		List<Brod>brod2=brodServis.BrodSortNaziv();
		List<Brod>brod3=brodServis.BrodSortAdresa();
		
		model.addAttribute("sortbrodovi", brod);
		model.addAttribute("sortbrodovinaziv", brod2);
		model.addAttribute("sortbrdadresa",brod3);
		for(Brod b:brod) {
			System.out.println(b.getCena());
		}
		
		
	      return "prikazBrodova";
	      
	      
	  }

	@RequestMapping(value = "/OsnovniprikazBrodova")
	  public String getOsnPage(Model model){
		System.out.println("PrikazBrodova page was called!");
		List<Brod>brodovi=brodServis.listAll();
		model.addAttribute("brodovi", brodovi);
		
		

		
	      return "OsnovniPodaciBrod";
	  }

	@RequestMapping(value = "/Pretraga/{id}")
	  public String getOsPage(Model model,@PathVariable Long id,@RequestParam String s){
		System.out.println("PrikazBrodova page was called!");
		List<Brod>brodovi=brodServis.BrodPretraga(s);
		Korisnik k=korServis.findById(id);
		model.addAttribute("kor", k);
		model.addAttribute("brodovi", brodovi);
		System.out.println(model.toString());
		List<Brod>brod=brodServis.BrodSortCena();
		List<Brod>brod2=brodServis.BrodSortNaziv();
		List<Brod>brod3=brodServis.BrodSortAdresa();
		
		model.addAttribute("sortbrodovi", brod);
		model.addAttribute("sortbrodovinaziv", brod2);
		model.addAttribute("sortbrdadresa",brod3);
		
	      return "prikazBrodova";
	  }
	
	@RequestMapping(value = "/OSPretragaBrod")
	  public String getOsPage(Model model,@RequestParam String s){
		System.out.println("PrikazBrodova page was called!");
		List<Brod>brodovi=brodServis.BrodPretraga(s);
		model.addAttribute("brodovi", brodovi);
		
	      return "OsnovniPodaciBrod";
	  }
	
	@RequestMapping(value = "/Filter/{id}")
	  public String getOPage(Model model,@PathVariable Long id,@RequestParam String brodovifil){
		System.out.println("PrikazBrodova page was called!");
		List<Brod>br=brodServis.BrodFilter(brodovifil);
		System.out.println("velicina:"+br.size());
		System.out.println(br.toString());
		//List<Brod>brodovi=brodServis.BrodPretraga(s);
		Korisnik k=korServis.findById(id);
		model.addAttribute("kor", k);
		model.addAttribute("brodovi", br);
		System.out.println(model.toString());
		List<Brod>brod=brodServis.BrodSortCena();
		List<Brod>brod2=brodServis.BrodSortNaziv();
		List<Brod>brod3=brodServis.BrodSortAdresa();
		
		model.addAttribute("sortbrodovi", brod);
		model.addAttribute("sortbrodovinaziv", brod2);
		model.addAttribute("sortbrdadresa",brod3);
		
	      return "prikazBrodova";
	  }
	
	
	
}
