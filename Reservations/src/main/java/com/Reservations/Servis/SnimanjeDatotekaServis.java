package com.Reservations.Servis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Reservations.DTO.SlikaDTO;
import com.Reservations.Modeli.Slika;
import com.Reservations.Repozitorijumi.SlikaRepozitorijum;

@Service
public class SnimanjeDatotekaServis 
{
	
	@Autowired
	SlikaRepozitorijum slikaRepozitorijum;
	
	public final String putanjaSlikaVikendica = "/img/vikendice/";
	public static String snimiDatoteku(String putanja, String nazivDatoteke, MultipartFile datoteka) throws IOException
	{
		
		return nazivDatoteke;
		/*Path putanjaSnimanja = Paths.get(putanja);
        
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }*/   
	}

	public boolean snimiSlikuVikendice(SlikaDTO slikaDTO) throws IOException
	{
		if(slikaDTO.getPutanja()==null) System.out.println("Putanja je null");
	    if(slikaDTO.getNazivSlike()==null) System.out.println("Naziv je null");
	    if(slikaDTO.getSlika()!=null) System.out.println("Slika ne postoji");
		try
		{
			File img = new File(this.putanjaSlikaVikendica+slikaDTO.getNazivSlike());
			//this.putanjaSlika
			System.out.println("Usao u snimi");
			List<Slika> slike = slikaRepozitorijum.findAll();
			Slika slika = new Slika((long) slike.size(), slikaDTO.getNazivSlike(), this.putanjaSlikaVikendica, img);
			slikaRepozitorijum.save(slika);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
