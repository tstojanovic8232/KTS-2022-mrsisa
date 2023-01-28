package com.Reservations.Kontroleri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Reservations.DTO.AdminDTO;
import com.Reservations.DTO.AzuriranjeInstruktoraDTO;
import com.Reservations.DTO.GVarijablaDTO;
import com.Reservations.DTO.PoslovanjeEntitetaDTO;
import com.Reservations.DTO.PrihodDTO;
import com.Reservations.DTO.PromenaLozinkeDTO;
import com.Reservations.DTO.RezervacijaSpisakDTO;
import com.Reservations.Exception.ResourceConflictException;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.GlobalnaVarijabla;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Modeli.Rezervacija;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Modeli.ZahtevZaBrisanje;
import com.Reservations.Modeli.Zalba;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Servis.BrisanjeNalogaServis;
import com.Reservations.Servis.BrodServis;
import com.Reservations.Servis.GVarijableServis;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RegistracijaServis;
import com.Reservations.Servis.RezervacijaServis;
import com.Reservations.Servis.TerminServis;
import com.Reservations.Servis.UslugaServis;
import com.Reservations.Servis.VikendicaServis;
import com.Reservations.Servis.ZalbaServis;

@Controller
@RequestMapping(value = "/admin/{id}")
public class AdminKontroler {

	@Autowired
	GVarijableServis gvServis;

	@Autowired
	KorisnikServis korisnikServis;

	@Autowired
	RegistracijaServis regServis;

	@Autowired
	RezervacijaServis rezServis;

	@Autowired
	BrodServis brodServis;

	@Autowired
	UslugaServis uslugaServis;

	@Autowired
	VikendicaServis vikendicaServis;

	@Autowired
	BrisanjeNalogaServis bnServis;

	@Autowired
	ZalbaServis zalbaServis;

	@Autowired
	TerminServis terminServis;

	@RequestMapping(value = "")
	public String getAdminPage(Model model, @PathVariable Long id) {
		System.out.println("Admin page was called!");
		List<Registracija> zahteviR = regServis.listAll();
		List<ZahtevZaBrisanje> zahteviB = bnServis.listAll();
		model.addAttribute("id", id);
		List<Zalba> zalbe = zalbaServis.listajZalbeBezOdgovora();
		model.addAttribute("zahtevi", zahteviR);
		model.addAttribute("zahteviB", zahteviB);
		model.addAttribute("zalbe", zalbe);
		return "admin/adminPocetna";
	}

	@RequestMapping(value = "/zalba/{rId}")
	public String viewIssue(Model model, @PathVariable Long rId, @PathVariable Long id) {
		System.out.println("Request " + String.valueOf(rId) + " was opened!");
		Zalba zalba = zalbaServis.findById(rId);
		model.addAttribute("id", id);
		model.addAttribute("zalba", zalba);
		return "admin/adminZalba";
	}

	@RequestMapping(value = "/zalba/{rId}/submit")
	public String sendBackIssue(Model model, @PathVariable Long rId, @PathVariable Long id,
			@RequestParam String textarea) throws AddressException, MessagingException, IOException {
		System.out.println("Request was processed!");
		Zalba zalba = zalbaServis.findById(rId);
		model.addAttribute("id", id);
		System.out.println("textarea = " + textarea);
		Korisnik zakupodavac = new Korisnik();
		Korisnik klijent = korisnikServis.findByUsername(zalba.getNaziv());
		Rezervacija rez = zalba.getRezervacija();
		if (rez.getTipEntiteta().equals(TipEntiteta.vikendica)) {
			Vikendica vik = vikendicaServis.findById(rez.getEntitetId());
			zakupodavac = vik.getVlasnik();
		} else if (rez.getTipEntiteta().equals(TipEntiteta.brod)) {
			Brod brod = brodServis.findById(rez.getEntitetId());
			zakupodavac = brod.getVlasnik();
		} else if (rez.getTipEntiteta().equals(TipEntiteta.usluga)) {
			Usluga usluga = uslugaServis.findById(rez.getEntitetId());
			zakupodavac = usluga.getInstruktor();
		} else
			return "redirect:/admin/" + String.valueOf(id);
		if (zalbaServis.upisiOdgovor(rId, textarea))
			this.sendEmailIssue(klijent, zakupodavac, zalba.getZalba(), textarea, rez.getNazivEntiteta());
		else
			System.out.println("Nemoguće upisati odgovor ako već postojji!");
		return "redirect:/admin/" + String.valueOf(id);
	}

	@RequestMapping(value = "/zahtevReg/{rId}")
	public String viewRequest(Model model, @PathVariable Long rId, @PathVariable Long id) {
		System.out.println("Request " + String.valueOf(rId) + " was opened!");
		Registracija reg = regServis.findById(rId);
		model.addAttribute("id", id);
		model.addAttribute("zahtev", reg);
		return "admin/adminZahtev";
	}

	@RequestMapping(value = "/zahtevReg/{rId}/submit")
	public String sendBackRequest(Model model, @PathVariable Long rId, @PathVariable Long id,
			@RequestParam String radio, @RequestParam String textarea)
			throws AddressException, MessagingException, IOException {
		System.out.println("Request was processed!");
		model.addAttribute("id", id);
		Registracija reg = regServis.findById(rId);
		System.out.println("radio = " + radio);
		System.out.println("textarea = " + textarea);
		System.out.println(reg.toPrivateString());
		if (radio.equals("deny")) {
			this.sendEmailToUser(false, textarea, reg.getEmail());
			regServis.delete(rId);
		} else if (radio.equals("accept")) {
			this.sendEmailToUser(true, textarea, reg.getEmail());
			try {
				korisnikServis.save(reg);
			} catch (Exception e) {
				System.out.println(e.getStackTrace().toString());
			}
			regServis.delete(rId);
		} else {
			System.out.println("Something went wrong!");
		}
		return "redirect:/admin/" + String.valueOf(id);
	}

	@RequestMapping(value = "/zahtevZB/{rId}")
	public String viewDeleteRequest(Model model, @PathVariable Long rId, @PathVariable Long id) {
		System.out.println("Request " + String.valueOf(rId) + " was opened!");
		ZahtevZaBrisanje zb = bnServis.findById(rId);
		model.addAttribute("id", id);
		model.addAttribute("zahtev", zb);
		return "admin/adminZahtevBrisanje";
	}

	@RequestMapping(value = "/zahtevZB/{rId}/submit")
	public String sendBackDeleteRequest(Model model, @PathVariable Long rId, @PathVariable Long id,
			@RequestParam String radio) throws AddressException, MessagingException, IOException {
		System.out.println("Request was processed!");
		model.addAttribute("id", id);
		ZahtevZaBrisanje zb = bnServis.findById(rId);
		System.out.println("radio = " + radio);
		System.out.println(zb.toPrivateString());
		if (radio.equals("deny")) {
			bnServis.delete(rId);
		} else if (radio.equals("accept")) {
			try {
				Korisnik k = korisnikServis.findByUsername(zb.getKorisnickoIme());
				this.obrisiVezaneEntitete(k);
				korisnikServis.delete(k.getID());
			} catch (Exception e) {
				System.out.println("I dalje postoje vrednosti vezane za korisnika!");
			}
			bnServis.delete(rId);
		} else {
			System.out.println("Something went wrong!");
		}
		return "redirect:/admin/" + String.valueOf(id);
	}

	@RequestMapping(value = "/pregled", method = RequestMethod.GET)
	public String getEntitiesPage(Model model, @PathVariable Long id) {
		System.out.println("All entities page was called!");
		List<Korisnik> lista = korisnikServis.listAll();
		List<Korisnik> klijenti = korisnikServis.findByUloga("Klijent");
		List<Korisnik> vlasniciV = korisnikServis.findByUloga("VikendicaVlasnik");
		List<Korisnik> vlasniciB = korisnikServis.findByUloga("BrodVlasnik");
		List<Korisnik> instruktori = korisnikServis.findByUloga("Instruktor");
		List<Brod> brodovi = brodServis.listAll();
		List<Vikendica> vikendice = vikendicaServis.listAll();
		List<Rezervacija> rezervacijeUcitaj = rezServis.listAll();
		List<RezervacijaSpisakDTO> rezervacije = new ArrayList<RezervacijaSpisakDTO>();

		for (Rezervacija rezervacija : rezervacijeUcitaj) {
			RezervacijaSpisakDTO rez = new RezervacijaSpisakDTO(rezervacija);
			rez.setTipEntiteta(rezervacija.getTipEntiteta().toString());
			rez.setTip(rezervacija.getTip().toString());
			rezervacije.add(rez);
		}

		model.addAttribute("klijenti", klijenti);
		model.addAttribute("vlasniciV", vlasniciV);
		model.addAttribute("vlasniciB", vlasniciB);
		model.addAttribute("instruktori", instruktori);
		model.addAttribute("brodovi", brodovi);
		model.addAttribute("vikendice", vikendice);
		model.addAttribute("rezervacije", rezervacije);
		model.addAttribute("id", id);
		System.out.println(model.toString());
		return "admin/adminLista";
	}

	@RequestMapping(value = "/profil")
	public String getDataPage(Model model, @PathVariable Long id) {
		System.out.println("My profile page was called!");
		Korisnik admin = korisnikServis.findById(id);
		model.addAttribute("admin", admin);
		model.addAttribute("id", id);
		return "admin/adminProfil";
	}

	@RequestMapping(value = "/profil/azuriraj")
	public String azurirajPage(Model model, @PathVariable Long id, AzuriranjeInstruktoraDTO aiDTO) {
		System.out.println("Azuriraj page was called!");
		System.out.println(aiDTO.toString());
		korisnikServis.update(aiDTO);
		return "redirect:/admin/" + String.valueOf(id) + "/profil";
	}

	@RequestMapping(value = "/profil/promeniLozinku")
	public String lozinkaPage(Model model, @PathVariable Long id, PromenaLozinkeDTO plDTO) {
		System.out.println("Lozinka page was called!");
		System.out.println(plDTO.toString());
		Korisnik k = korisnikServis.findById(id);
		System.out.println(k.toString());
		if (plDTO.getStaraLozinka().equals(k.getLozinka())) {
			if (plDTO.getNovaLozinka().equals(plDTO.getNovaPonovo()))
				korisnikServis.changePassword(id, plDTO.getStaraLozinka(), plDTO.getNovaLozinka());
			else
				throw new ResourceConflictException(id, "Lozinke se ne poklapaju!");
		}
		return "redirect:/admin/" + String.valueOf(id) + "/profil";
	}

	@RequestMapping(value = "/prihodi")
	public String getRevenuePage(Model model, @PathVariable Long id) {
		System.out.println("Revenue page was called!");
		GlobalnaVarijabla gv = gvServis.findByName("procenat");
		if (gv != null) {
			double procDec = Double.parseDouble(gv.getVrednost());
			int proc = (int) (procDec * 100);
			String procenat = String.valueOf(proc) + "%";
			model.addAttribute("procenatDec", procDec);
			model.addAttribute("procenat", procenat);
		} else {
			model.addAttribute("procenatDec", "undefined");
			model.addAttribute("procenat", "undefined");
		}

		List<PrihodDTO> lista = rezServis.izracunajPrihodeSvihRezervacija();

		model.addAttribute("prihodi", lista);
		model.addAttribute("id", id);
		return "admin/adminPrihodi";
	}

	@RequestMapping(value = "/prihodi/procenat")
	public String changeRevenue(Model model, @PathVariable Long id, @RequestParam String procenat) {
		System.out.println("Revenue was changed!");
		GlobalnaVarijabla gv = gvServis.findByName("procenat");
		if (gv == null) {
			long idTemp = 0;
			for (long i = 0; i < Long.MAX_VALUE; i++) {
				GlobalnaVarijabla check = gvServis.findById(i);
				if (check != null) {
					continue;
				} else {
					idTemp = i;
					break;
				}
			}
			GVarijablaDTO gvDTO = new GVarijablaDTO(idTemp, "procenat", procenat);
			gvServis.save(gvDTO);
		} else {
			GVarijablaDTO gvDTO = new GVarijablaDTO(gv.getID(), gv.getIme(), procenat);
			gvServis.update(gvDTO);
		}
		return "redirect:/admin/" + String.valueOf(id) + "/prihodi";
	}

	@RequestMapping(value = "/administratori")
	public String getAdministrators(@PathVariable Long id, Model model) {
		System.out.println("Admin page was called!");
		List<Korisnik> admini = korisnikServis.findByUloga("Admin");
		model.addAttribute("id", id);
		model.addAttribute("admini", admini);
		return "admin/adminNoviAdmin";
	}

	@RequestMapping(value = "/dodajAdmina")
	public String addAdmin(@PathVariable Long id, Model model, AdminDTO admin) {
		System.out.println("Admin page was called!");
		model.addAttribute("id", id);
		String lozinka = korisnikServis.save(admin);
		Korisnik k = korisnikServis.findByUsername(admin.getKorisnickoIme());
		try {
			this.sendEmailToNewAdmin(k.getEmail(), lozinka);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/admin/" + String.valueOf(id) + "/administratori";
	}

	@RequestMapping(value = "/izvestaji")
	public String getReportsGraph(@PathVariable Long id, Model model) {
		System.out.println("Report page was called!");
		model.addAttribute("id", id);

		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanjaU = uslugaServis.izracunajSedmicnaPoslovanjaUsluga();
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanjaU = uslugaServis.izracunajMjesecnaPoslovanjaUsluga();
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanjaU = uslugaServis.izracunajGodisnjaPoslovanjaUsluga();

		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanjaV = vikendicaServis.izracunajSedmicnaPoslovanjaVikendica();
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanjaV = vikendicaServis.izracunajMjesecnaPoslovanjaVikendica();
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanjaV = vikendicaServis.izracunajGodisnjaPoslovanjaVikendica();

		List<PoslovanjeEntitetaDTO> sedmicnaPoslovanjaB = brodServis.izracunajSedmicnaPoslovanjaBrodova();
		List<PoslovanjeEntitetaDTO> mjesecnaPoslovanjaB = brodServis.izracunajMjesecnaPoslovanjaBrodova();
		List<PoslovanjeEntitetaDTO> godisnjaPoslovanjaB = brodServis.izracunajGodisnjaPoslovanjaBrodova();

		List<String> sedmicneLabele = new ArrayList<String>();
		List<String> mesecneLabele = new ArrayList<String>();
		List<String> godisnjeLabele = new ArrayList<String>();

		List<Double> sedmicniPrihodi = new ArrayList<Double>();
		List<Double> mesecniPrihodi = new ArrayList<Double>();
		List<Double> godisnjiPrihodi = new ArrayList<Double>();

		for (int i = 0; i < sedmicnaPoslovanjaU.size(); i++) {
			sedmicneLabele.add(sedmicnaPoslovanjaU.get(i).getNazivEntiteta());
			double temp = sedmicnaPoslovanjaU.get(i).getPrihod() + sedmicnaPoslovanjaU.get(i).getPrihod()
					+ sedmicnaPoslovanjaU.get(i).getPrihod();
			sedmicniPrihodi.add(temp);
		}

		for (int i = 0; i < mjesecnaPoslovanjaU.size(); i++) {
			mesecneLabele.add(mjesecnaPoslovanjaU.get(i).getNazivEntiteta());
			double temp = mjesecnaPoslovanjaU.get(i).getPrihod() + mjesecnaPoslovanjaU.get(i).getPrihod()
					+ mjesecnaPoslovanjaU.get(i).getPrihod();
			mesecniPrihodi.add(temp);
		}

		for (int i = 0; i < godisnjaPoslovanjaU.size(); i++) {
			godisnjeLabele.add(godisnjaPoslovanjaU.get(i).getNazivEntiteta());
			double temp = godisnjaPoslovanjaU.get(i).getPrihod() + godisnjaPoslovanjaU.get(i).getPrihod()
					+ godisnjaPoslovanjaU.get(i).getPrihod();
			godisnjiPrihodi.add(temp);
		}

		model.addAttribute("sedmicnaPoslovanja", sedmicniPrihodi);
		model.addAttribute("mjesecnaPoslovanja", mesecniPrihodi);
		model.addAttribute("godisnjaPoslovanja", godisnjiPrihodi);

		model.addAttribute("sedmicnaPoslovanjaLabele", sedmicneLabele);
		model.addAttribute("mjesecnaPoslovanjaLabele", mesecneLabele);
		model.addAttribute("godisnjaPoslovanjaLabele", godisnjeLabele);

		return "admin/adminIzvestaji";
	}

	@RequestMapping(value = "/izvestaji/print")
	public String getReportsDates(@PathVariable Long id, Model model, @RequestParam String pocetniDatum,
			@RequestParam String krajnjiDatum) {
		System.out.println("Report page was called!");
		model.addAttribute("id", id);

		String[] pocetni = pocetniDatum.split("-");
		String[] krajnji = krajnjiDatum.split("-");
		if (pocetni.length == 3 && krajnji.length == 3) {
			pocetniDatum = pocetni[1] + "/" + pocetni[2] + "/" + pocetni[0];
			krajnjiDatum = krajnji[1] + "/" + krajnji[2] + "/" + krajnji[0];
		}

		List<PrihodDTO> lista = rezServis.izracunajPrihodeSvihRezervacija(pocetniDatum, krajnjiDatum);

		model.addAttribute("prihodi", lista);

		return "admin/adminIzvestajiTabela";
	}

	@RequestMapping(value = "/obrisi/klijent/{kID}")
	public String deleteClient(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Korisnik k = korisnikServis.findById(kID);
		this.obrisiVezaneEntitete(k);
		korisnikServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	@RequestMapping(value = "/obrisi/vvlasnik/{kID}")
	public String deleteOwnerV(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Korisnik k = korisnikServis.findById(kID);
		this.obrisiVezaneEntitete(k);
		korisnikServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	@RequestMapping(value = "/obrisi/bvlasnik/{kID}")
	public String deleteOwnerB(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Korisnik k = korisnikServis.findById(kID);
		this.obrisiVezaneEntitete(k);
		korisnikServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	@RequestMapping(value = "/obrisi/instruktor/{kID}")
	public String deleteInstructor(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Korisnik k = korisnikServis.findById(kID);
		this.obrisiVezaneEntitete(k);
		korisnikServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	@RequestMapping(value = "/obrisi/vikendica/{kID}")
	public String deleteHouse(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Vikendica v = vikendicaServis.findById(kID);
		this.obrisiVezaneEntitete(v);
		vikendicaServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	@RequestMapping(value = "/obrisi/brod/{kID}")
	public String deleteBoat(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Brod b = brodServis.findById(kID);
		this.obrisiVezaneEntitete(b);
		brodServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	@RequestMapping(value = "/obrisi/rezervacija/{kID}")
	public String deleteReservation(@PathVariable Long id, @PathVariable Long kID, Model model) {
		System.out.println("Delete page was called!");
		Rezervacija r = rezServis.findById(kID);
		this.obrisiVezaneEntitete(r);
		rezServis.delete(kID);
		return "redirect:/admin/" + String.valueOf(id) + "/pregled";
	}

	// METODE ZA SLANJE MAILA
	private void sendEmailToUser(Boolean prihvacen, String razlog, String mail)
			throws AddressException, MessagingException, IOException {
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

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject("Zahtev za registraciju");
		msg.setContent("Zahtev za registraciju", "text/html");
		msg.setSentDate(new Date());
		MimeBodyPart messageAccept = new MimeBodyPart();
		MimeBodyPart messageDenyReason = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();
		if (prihvacen) {
			msg.setSubject("Zahtev za registraciju");
			msg.setContent("Zahtev za registraciju", "text/html");

			messageAccept.setContent("Cestitamo! Vas zahtev za registraciju je odobren! Mozete se ulogovati vec danas.",
					"text/html");

			multipart.addBodyPart(messageAccept);
		} else {
			messageAccept.setContent("Zao nam je. Vas zahtev za registraciju je odbijen.", "text/html");
			messageDenyReason.setContent("Razlog odbijanja: " + razlog, "text/html");

			multipart.addBodyPart(messageAccept);
			multipart.addBodyPart(messageDenyReason);
		}

		msg.setContent(multipart);

		Transport.send(msg);
	}

	private void sendEmailToNewAdmin(String mail, String lozinka)
			throws AddressException, MessagingException, IOException {
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

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject("Napravljen administratorski nalog!");
		msg.setContent("Napravljen administratorski nalog", "text/html");
		msg.setSentDate(new Date());
		MimeBodyPart message = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();

		message.setContent(
				"Cestitamo! Vas administratorski nalog je napravljen! Mozete se ulogovati vec danas.\nVasa privremena lozinka je: "
						+ lozinka + "\nObavezno promenite lozinku!",
				"text/html");

		multipart.addBodyPart(message);

		msg.setContent(multipart);

		Transport.send(msg);
	}

	private void sendEmailIssue(Korisnik klijent, Korisnik zakupodavac, String zalba, String odgovor, String entitet)
			throws AddressException, MessagingException, IOException {
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

		msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(klijent.getEmail() + "," + zakupodavac.getEmail()));
		msg.setSubject("Zalba klijenta");
		msg.setContent("Zalba klijenta", "text/html");
		msg.setSentDate(new Date());
		MimeBodyPart message = new MimeBodyPart();
		Multipart multipart = new MimeMultipart();

		message.setContent("Zalba klijenta " + klijent.getIme() + " " + klijent.getPrezime()
				+ " za rezervaciju entiteta \"" + entitet + "\": " + zalba + "\nOdgovor administratora: " + odgovor,
				"text/html");

		multipart.addBodyPart(message);

		msg.setContent(multipart);

		Transport.send(msg);
	}

	// BRISANJE POVEZANIH ENTITETA DA BI UOPŠTE MOGLO DA SE OBRIŠE IZ BAZE
	public void obrisiVezaneEntitete(Korisnik kor) {
		kor.setUloge(null);
		if (kor.getUloga().getIme().equals("Klijent")) {
			List<Rezervacija> rezervacije = rezServis.findByKlijent(kor.getID(), null);
			for (Rezervacija rezervacija : rezervacije) {
				this.obrisiVezaneEntitete(rezervacija);
				rezServis.delete(rezervacija.getID());
			}
		} else if (kor.getUloga().getIme().equals("VikendicaVlasnik")) {
			List<Rezervacija> rezervacije = rezServis.pronadjiRezervacijePoVlasniku(kor, TipEntiteta.vikendica);
			for (Rezervacija rezervacija : rezervacije) {
				this.obrisiVezaneEntitete(rezervacija);
				rezServis.delete(rezervacija.getID());
			}
			List<Vikendica> vikendice = vikendicaServis.findByVlasnik(kor.getID());
			for (Vikendica vikendica : vikendice) {
				this.obrisiVezaneEntitete(vikendica);
				vikendicaServis.obrisiVikendicu(kor.getID(), vikendica.getID());
			}
		} else if (kor.getUloga().getIme().equals("BrodVlasnik")) {
			List<Rezervacija> rezervacije = rezServis.pronadjiRezervacijePoVlasniku(kor, TipEntiteta.brod);
			for (Rezervacija rezervacija : rezervacije) {
				this.obrisiVezaneEntitete(rezervacija);
				rezServis.delete(rezervacija.getID());
			}
			List<Brod> brodovi = brodServis.findByVlasnik(kor.getID());
			for (Brod brod : brodovi) {
				this.obrisiVezaneEntitete(brod);
				brodServis.obrisiBrod(kor.getID(), brod.getID());
			}
		} else if (kor.getUloga().getIme().equals("Instruktor")) {
			List<Rezervacija> rezervacije = rezServis.pronadjiRezervacijePoVlasniku(kor, TipEntiteta.usluga);
			for (Rezervacija rezervacija : rezervacije) {
				this.obrisiVezaneEntitete(rezervacija);
			}
			List<Usluga> usluge = uslugaServis.findByInstruktor(kor.getID());
			for (Usluga usluga : usluge) {
				this.obrisiVezaneEntitete(usluga);
				uslugaServis.deleteById(usluga.getID());
			}
		} else
			System.out.println("Nepostojeca uloga!");
	}

	public void obrisiVezaneEntitete(Vikendica vik) {
		List<Rezervacija> rezervacije = rezServis.nadjiRezervacijeVikendica();
		List<Rezervacija> rezervacijeVikendice = new ArrayList<Rezervacija>();
		for (Rezervacija rezervacija : rezervacije) {
			if (rezervacija.getEntitetId() == vik.getID())
				rezervacijeVikendice.add(rezervacija);
		}
		for (Rezervacija rez : rezervacijeVikendice) {
			this.obrisiVezaneEntitete(rez);
			rezServis.delete(rez.getID());
		}
	}

	public void obrisiVezaneEntitete(Brod brod) {
		List<Rezervacija> rezervacije = rezServis.findByTip(TipEntiteta.brod);
		List<Rezervacija> rezervacijeVikendice = new ArrayList<Rezervacija>();
		for (Rezervacija rezervacija : rezervacije) {
			if (rezervacija.getEntitetId() == brod.getID())
				rezervacijeVikendice.add(rezervacija);
		}
		for (Rezervacija rez : rezervacijeVikendice) {
			this.obrisiVezaneEntitete(rez);
			rezServis.delete(rez.getID());
		}
	}

	public void obrisiVezaneEntitete(Usluga usluga) {
		List<Rezervacija> rezervacije = rezServis.findByTip(TipEntiteta.usluga);
		List<Rezervacija> rezervacijeVikendice = new ArrayList<Rezervacija>();
		for (Rezervacija rezervacija : rezervacije) {
			if (rezervacija.getEntitetId() == usluga.getID())
				rezervacijeVikendice.add(rezervacija);
		}
		for (Rezervacija rez : rezervacijeVikendice) {
			this.obrisiVezaneEntitete(rez);
			rezServis.delete(rez.getID());
		}
	}

	public void obrisiVezaneEntitete(Rezervacija rez) {
		long terminID = rez.getTermin().getID();
		rez.setTermin(null);
		terminServis.obrisiPoID(terminID);
		List<Zalba> zalbe = zalbaServis.listAll();
		for (Zalba zalba : zalbe) {
			if (zalba.getRezervacija().equals(rez))
				zalbaServis.delete(zalba.getID());
		}
	}
}
