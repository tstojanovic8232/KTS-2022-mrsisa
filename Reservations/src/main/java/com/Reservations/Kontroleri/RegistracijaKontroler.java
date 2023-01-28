package com.Reservations.Kontroleri;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.HttpMediaTypeException;
import com.Reservations.DTO.RegistracijaKorisnikaDTO;
import com.Reservations.DTO.RegistracijaVlasnikaInstruktoraDTO;
import com.Reservations.Exception.ResourceConflictException;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RegistracijaServis;

@Controller
public class RegistracijaKontroler {

	@Autowired
	KorisnikServis korisnikServis;

	@Autowired
	RegistracijaServis regServis;

	@RequestMapping(value = "/register-owner")
	public String getRegisterOwnerPage() {
		System.out.println("Register owner page was called!");
		return "registerOwner";
	}

	@RequestMapping(value = "/register-client")
	public String getRegisterClientPage() {
		System.out.println("Register client page was called!");
		return "registerClient";
	}

	@RequestMapping(value = "/request-sent", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String registerOwner(@RequestBody RegistracijaVlasnikaInstruktoraDTO regRequest) {
		
		System.out.println("Owner registration request sent!");
		Korisnik existKorisnik = this.korisnikServis.findByUsername(regRequest.getUsername());
		if (existKorisnik != null) {
			throw new ResourceConflictException(regRequest.getId(), "Username already exists");
		}
		this.regServis.save(regRequest);
		try {
			sendEmailToAdmin(regRequest);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "registerRequest";
	}

	@RequestMapping(value = "/verify", consumes = MediaType.ALL_VALUE)
	public String registerClient( RegistracijaKorisnikaDTO userRequest) {

		System.out.println(userRequest.toString());
		System.out.println("Client registration in progress!");
		Korisnik existUser = korisnikServis.findByUsername(userRequest.getUsername());
		if (existUser != null) {
			throw new ResourceConflictException(existUser.getID(), "Username already exists");
		}

		try 
		{
			this.korisnikServis.save(userRequest);
		}
		catch (Exception e)
		{
			System.out.println("Registration failure!");
			return "registerFailure";
		}
		System.out.println("Registration successful!");

		return "registerSuccess";
	}

	private void sendEmailToAdmin(RegistracijaVlasnikaInstruktoraDTO regRequest) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mrs.isa.test@outlook.com", "123456789mrs.");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("mrs.isa.test@outlook.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("teateodora2000@gmail.com"));
		msg.setSubject("Novi zahtev za registraciju");
		msg.setContent("Novi zahtev za registraciju", "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		Registracija reg = new Registracija(regRequest);
		messageBodyPart.setContent(reg.toPrivateString(), "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}
}
