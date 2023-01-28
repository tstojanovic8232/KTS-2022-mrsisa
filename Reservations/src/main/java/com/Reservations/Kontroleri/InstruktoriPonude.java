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
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.UslugaServis;
@Controller
public class InstruktoriPonude {
	@Autowired
	UslugaServis uslugaServis;
		
	@Autowired
	KorisnikServis korServis;
	@RequestMapping(value = "/prikazInstruktora/{id}")
	  public String getTestPage(Model model,@PathVariable Long id){
		System.out.println("PrikazInstruktora page was called!");
		List<Usluga>usluga=uslugaServis.listAll();
		Korisnik k=korServis.findById(id);
		model.addAttribute("kor", k);
		
		model.addAttribute("usluga", usluga);
		
		
		List<Usluga>vik1=uslugaServis.UslugaSortCena();
		List<Usluga>vik2=uslugaServis.UslugaSortNaziv();
		List<Usluga>vik3=uslugaServis.UslugaSortAdresa();
		
		model.addAttribute("sortcena", vik1);
		model.addAttribute("sortnaziv", vik2);
		model.addAttribute("sortadresa",vik3);
		System.out.println(model.toString());
	      return "prikazInstruktora";
	  }
	
	@RequestMapping(value = "/OsnovniprikazInstruktora")
	  public String getOsnPage(Model model){
		System.out.println("OsnovniPrikazInstruktora page was called!");
		List<Usluga>usluga=uslugaServis.listAll();
		model.addAttribute("usluga", usluga);
		System.out.println(model.toString());
	      return "OsnovniPodaciInstruktora";
	  }
	
	@RequestMapping(value = "/PretragaUsl/{id}")
	  public String getOsPage(Model model,@PathVariable Long id,@RequestParam String s){
		
		List<Usluga>usluge=uslugaServis.UslugaPretraga(s);
		Korisnik k=korServis.findById(id);
		model.addAttribute("usluga", usluge);
		model.addAttribute("kor",k);
		
		List<Usluga>vik1=uslugaServis.UslugaSortCena();
		List<Usluga>vik2=uslugaServis.UslugaSortNaziv();
		List<Usluga>vik3=uslugaServis.UslugaSortAdresa();
		
		model.addAttribute("sortcena", vik1);
		model.addAttribute("sortnaziv", vik2);
		model.addAttribute("sortadresa",vik3);
		System.out.println(model.toString());
	      return "prikazInstruktora";
	  }
	
	@RequestMapping(value = "/OSPretragaInst")
	  public String getOsPage(Model model,@RequestParam String s){
		System.out.println("PrikazBrodova page was called!");
		List<Usluga>usluga=uslugaServis.UslugaPretraga(s);
		model.addAttribute("usluga", usluga);
		
	      return "OsnovniPodaciInstruktora";
	  }
	
	
	@RequestMapping(value = "/FilterUsl/{id}")
	  public String getOPage(Model model,@PathVariable Long id,@RequestParam String brodovifil){
		System.out.println("PrikazBrodova page was called!");
		List<Usluga>br=uslugaServis.UslFilter(brodovifil);
		System.out.println("velicina:"+br.size());
		System.out.println(br.toString());
		//List<Brod>brodovi=brodServis.BrodPretraga(s);
		Korisnik k=korServis.findById(id);
		model.addAttribute("kor", k);
		model.addAttribute("usluga", br);
		System.out.println(model.toString());
		List<Usluga>usl1=uslugaServis.UslugaSortCena();
		List<Usluga>usl2=uslugaServis.UslugaSortNaziv();
		List<Usluga>usl3=uslugaServis.UslugaSortAdresa();
		
		model.addAttribute("sortcena", usl1);
		model.addAttribute("sortnaziv", usl2);
		model.addAttribute("sortadresa",usl3);
		
	      return "prikazInstruktora";
	  }
}
