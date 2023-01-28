package com.Reservations.Kontroleri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Uloga;
import com.Reservations.Servis.KorisnikServis;



@Controller
public class PrijavaKontroler 
{
	
	@Autowired
	KorisnikServis userService;
		
	@RequestMapping("")
	  public String getWelcomePage(){
		System.out.println("Homepage for unauthenticated users was called!");
	      return "index";
	  }
	
	@RequestMapping(value = "/login")
	  public String getLoginPage(){
		System.out.println("Login page was called!");
	      return "login";
	  }
	
	@RequestMapping(value = "/login-process")
	  public String login(@RequestParam("username") String username, @RequestParam("password") String password)
	{
		System.out.println("Login is in progress!");
		Korisnik existUser = userService.findByUsername(username);
		Uloga u=new Uloga();
		if (existUser == null) {
			System.out.println("Login failure!");
			return "loginFailure";
		}
		else
		{
			if (password.equals(existUser.getLozinka()) )
			{
				if(existUser.getUloga().getIme().equals("Klijent")) 
				{
					System.out.println("Login successful!");
					
					return "redirect:/profilKorisnika/"+String.valueOf(existUser.getID());
				}
				else if(existUser.getUloga().getIme().equals("Admin")) 
				{
					return "redirect:/admin/" + String.valueOf(existUser.getID());
				}
				else if(existUser.getUloga().getIme().equals("VikendicaVlasnik"))
				{
					return "redirect:/vikendicaVlasnik/pocetna/"+username;
				}
				else if(existUser.getUloga().getIme().equals("BrodVlasnik"))
				{
					return "redirect:/brodVlasnik/pocetna/"+username;
				}
				else if(existUser.getUloga().getIme().equals("Instruktor")) 
				{
					return "redirect:/instruktor/" + String.valueOf(existUser.getID());
				}
				else {return "loginSuccess";}
			}
			
			else
			{
				System.out.println("Login failure!");
				return "loginFailure";
			}
		}
	  }
	
}