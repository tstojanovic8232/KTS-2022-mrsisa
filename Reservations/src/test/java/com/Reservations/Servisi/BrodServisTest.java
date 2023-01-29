package com.Reservations.Servisi;

import com.Reservations.DTO.RegistracijaKorisnikaDTO;
import com.Reservations.Kontroleri.BrodKontroler;
import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Modeli.Uloga;
import com.Reservations.Modeli.enums.TipRegistracije;
import com.Reservations.Repozitorijumi.BrodRepozitorijum;
import com.Reservations.Repozitorijumi.KorisnikRepozitorijum;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;
import com.Reservations.Servis.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BrodServisTest {

    @Mock
    private BrodRepozitorijum brodRepozitorijum;

    @Mock
    private KorisnikServis korisnikServis;




    @Mock
    private RezervacijaRepozitorijum rezervacijaRepozitorijum;

    @Mock
    private RezervacijaServis rezervacijaServis;

    @InjectMocks
    private BrodServis brodServis;



    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBrodSortCena() {

        List<Brod> brods = new ArrayList<>();
        Brod brod1=new Brod();
        Brod brod2=new Brod();
        Brod brod3=new Brod();
        brod1.setID(1L);
        brod1.setCena(300);
        brod2.setID(2L);
        brod2.setCena(200);
        brod3.setID(3L);
        brod3.setCena(100);

        brods.add(brod1);
        brods.add(brod2);
        brods.add(brod3);

        brodRepozitorijum.save(brod3);
        brodRepozitorijum.save(brod2);
        brodRepozitorijum.save(brod1);
        when(brodRepozitorijum.findAll(Sort.by(Sort.Direction.DESC, "cena"))).thenReturn(brods);

        List<Brod> result = brodServis.BrodSortCena();

        assertEquals(brods, result);
        verify(brodRepozitorijum).findAll(Sort.by(Sort.Direction.DESC, "cena"));


        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getCena() >= result.get(i + 1).getCena());
        }
    }
    @Test
    public void testBrodSortAdresa() {

        List<Brod> brods = new ArrayList<>();
        Brod brod1=new Brod();
        Brod brod2=new Brod();
        Brod brod3=new Brod();
        brod1.setID(1L);
        brod1.setAdresa("ADI ENDREA 33");
        brod2.setID(2L);
        brod2.setAdresa("BDI ENDREA 33");
        brod3.setID(3L);
        brod3.setAdresa("CDI ENDREA 33");

        brods.add(brod1);
        brods.add(brod2);
        brods.add(brod3);

        brodRepozitorijum.save(brod3);
        brodRepozitorijum.save(brod2);
        brodRepozitorijum.save(brod1);
        when(brodRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "adresa"))).thenReturn(brods);

        Brod brod = new Brod();

        List<Brod> result = brodServis.BrodSortAdresa();

        assertEquals(brods, result);
        verify(brodRepozitorijum).findAll(Sort.by(Sort.Direction.ASC, "adresa"));


        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getAdresa().compareTo(result.get(i + 1).getAdresa()) <= 0);
        }
    }

    @Test
    public void testBrodSortNaziv() {

        List<Brod> brods = new ArrayList<>();
        Brod brod1=new Brod();
        Brod brod2=new Brod();
        Brod brod3=new Brod();
        brod1.setID(1L);
        brod1.setTip("A");
        brod2.setID(2L);
        brod2.setTip("B");
        brod3.setID(3L);
        brod3.setTip("C");

        brods.add(brod1);
        brods.add(brod2);
        brods.add(brod3);

        brodRepozitorijum.save(brod3);
        brodRepozitorijum.save(brod2);
        brodRepozitorijum.save(brod1);
        assertNotNull(brodRepozitorijum.findAll());
        when(brodRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "tip"))).thenReturn(brods);

        Brod brod = new Brod();

        List<Brod> result = brodServis.BrodSortNaziv();

        assertEquals(brods, result);
        verify(brodRepozitorijum).findAll(Sort.by(Sort.Direction.ASC, "tip"));


        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getTip().compareTo(result.get(i + 1).getTip()) <= 0);
        }
    }
    @Test
    public void testBrodPretraga() {

        List<Brod> expectedList = new ArrayList<Brod>();
        Brod b1 = new Brod();
        b1.setAdresa("address1");
        b1.setTip("type1");
        b1.setCena(100);

        Brod b2 = new Brod();
        b2.setAdresa("address2");
        b2.setTip("type2");
        b2.setCena(200);

        expectedList.add(b1);
        expectedList.add(b2);
        when(brodRepozitorijum.findAll()).thenReturn(expectedList);
        brodRepozitorijum.save(b1);
        brodRepozitorijum.save(b2);



        List<Brod> actualList = brodServis.BrodPretraga("address1");
        assertEquals(b1, actualList.get(0));

        actualList = brodServis.BrodPretraga("200");
        assertEquals(b2, actualList.get(0));

        actualList = brodServis.BrodPretraga("Type");
        assertEquals(expectedList, actualList);
    }
    @Test
    public void testBrodFilter() {
        Brod b1 = new Brod();
        b1.setTip("type1");
        b1.setAdresa("address1");

        Brod b2 = new Brod();
        b2.setTip("type2");
        b2.setAdresa("address2");

        brodRepozitorijum.save(b1);
        brodRepozitorijum.save(b2);
        List<Brod> expectedList = new ArrayList<Brod>();
        expectedList.add(b1);
        expectedList.add(b2);
        when(brodRepozitorijum.findAll()).thenReturn(expectedList);
        List<Brod> filteredList = brodServis.BrodFilter("type1");

        assertEquals(1, filteredList.size());
        assertEquals("type1", filteredList.get(0).getTip());
        assertEquals("address1", filteredList.get(0).getAdresa());

        filteredList = brodServis.BrodFilter("address2");
        assertEquals(1, filteredList.size());
        assertEquals("type2", filteredList.get(0).getTip());
        assertEquals("address2", filteredList.get(0).getAdresa());

        filteredList = brodServis.BrodFilter("svi");
        assertEquals(2, filteredList.size());
    }

}
