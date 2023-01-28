package com.Reservations.Servis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Reservations.Modeli.Uloga;
import com.Reservations.Repozitorijumi.UlogaRepozitorijum;


@Service
public class UlogaServis
{

	@Autowired
	  private UlogaRepozitorijum ulogaRepozitorijum;

	  
	  public Uloga findById(Long id) {
	    Uloga auth = this.ulogaRepozitorijum.getOne(id);
	    return auth;
	  }

	  		
	  public Uloga findByName(String name) {
		  Uloga uloga = this.ulogaRepozitorijum.findByIme(name);
	    return uloga;
	  }

}
