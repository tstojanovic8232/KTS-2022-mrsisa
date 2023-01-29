package com.Reservations.Servisi;

import com.Reservations.Modeli.Brod;
import com.Reservations.Modeli.Vikendica;
import com.Reservations.Repozitorijumi.BrodRepozitorijum;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;
import com.Reservations.Repozitorijumi.VikendicaRepozitorijum;
import com.Reservations.Servis.BrodServis;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.RezervacijaServis;
import com.Reservations.Servis.VikendicaServis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VikendicaServisTest {
    @Mock
    private VikendicaRepozitorijum vikendicaRepozitorijum;

    @Mock
    private KorisnikServis korisnikServis;

    @Mock
    private RezervacijaRepozitorijum rezervacijaRepozitorijum;

    @Mock
    private RezervacijaServis rezervacijaServis;

    @InjectMocks
    private VikendicaServis vikendicaServis;


    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testVikSortCena() {

        List<Vikendica> vikendice = new ArrayList<>();
        Vikendica vik1=new Vikendica();
        Vikendica vik2=new Vikendica();
        Vikendica vik3=new Vikendica();
        vik1.setID(1L);
        vik1.setCena(100);
        vik2.setID(2L);
        vik2.setCena(200);
        vik3.setID(3L);
        vik3.setCena(300);

        vikendice.add(vik3);
        vikendice.add(vik2);
        vikendice.add(vik1);

        vikendicaRepozitorijum.save(vik3);
        vikendicaRepozitorijum.save(vik2);
        vikendicaRepozitorijum.save(vik1);
        when(vikendicaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "cena"))).thenReturn(vikendice);

        List<Vikendica> result = vikendicaServis.VikSortCena();

        assertEquals(vikendice, result);
        verify(vikendicaRepozitorijum).findAll(Sort.by(Sort.Direction.ASC, "cena"));


        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getCena() >= result.get(i + 1).getCena());
        }
    }
    @Test
    public void testVikSortAdresa() {

        List<Vikendica> vikendice = new ArrayList<>();
        Vikendica vik1=new Vikendica();
        Vikendica vik2=new Vikendica();
        Vikendica vik3=new Vikendica();
        vik1.setID(1L);
        vik1.setAdresa("adres1");
        vik2.setID(2L);
        vik2.setAdresa("adres2");
        vik3.setID(3L);
        vik3.setAdresa("adres3");

        vikendice.add(vik1);
        vikendice.add(vik2);
        vikendice.add(vik3);

        vikendicaRepozitorijum.save(vik3);
        vikendicaRepozitorijum.save(vik2);
        vikendicaRepozitorijum.save(vik1);


        when(vikendicaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "adresa"))).thenReturn(vikendice);

        Vikendica vik = new Vikendica();

        List<Vikendica> result = vikendicaServis.VikSortAdresa();

        assertEquals(vikendice, result);
        verify(vikendicaRepozitorijum).findAll(Sort.by(Sort.Direction.ASC, "adresa"));


        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getAdresa().compareTo(result.get(i + 1).getAdresa()) <= 0);
        }
    }
    @Test
    public void testVikSortNaziv() {

        List<Vikendica> vikendice = new ArrayList<>();
        Vikendica vik1=new Vikendica();
        Vikendica vik2=new Vikendica();
        Vikendica vik3=new Vikendica();
        vik1.setID(1L);
        vik1.setNaziv("A");
        vik2.setID(2L);
        vik2.setNaziv("B");
        vik3.setID(3L);
        vik3.setNaziv("C");

        vikendice.add(vik1);
        vikendice.add(vik2);
        vikendice.add(vik3);

        vikendicaRepozitorijum.save(vik3);
        vikendicaRepozitorijum.save(vik2);
        vikendicaRepozitorijum.save(vik1);
        assertNotNull(vikendicaRepozitorijum.findAll());
        when(vikendicaRepozitorijum.findAll(Sort.by(Sort.Direction.ASC, "naziv"))).thenReturn(vikendice);

        Vikendica vik = new Vikendica();

        List<Vikendica> result = vikendicaServis.VikSortNaziv();

        assertEquals(vikendice, result);
        verify(vikendicaRepozitorijum).findAll(Sort.by(Sort.Direction.ASC, "naziv"));


        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i).getNaziv().compareTo(result.get(i + 1).getNaziv()) <= 0);
        }
    }
    @Test
    public void testVikPretraga() {

        List<Vikendica> expectedList = new ArrayList<Vikendica>();
        Vikendica b1 = new Vikendica();
        b1.setAdresa("address1");
        b1.setNaziv("type1");
        b1.setCena(100);

        Vikendica b2 = new Vikendica();
        b2.setAdresa("address2");
        b2.setNaziv("type2");
        b2.setCena(200);

        expectedList.add(b1);
        expectedList.add(b2);
        when(vikendicaRepozitorijum.findAll()).thenReturn(expectedList);
        vikendicaRepozitorijum.save(b1);
        vikendicaRepozitorijum.save(b2);



        List<Vikendica> actualList = vikendicaServis.VikendicaPretraga("address1");
        assertEquals(b1, actualList.get(0));

        actualList = vikendicaServis.VikendicaPretraga("200");
        assertEquals(b2, actualList.get(0));

        actualList = vikendicaServis.VikendicaPretraga("Type");
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testVikFilter() {
        Vikendica b1 = new Vikendica();
        b1.setNaziv("type1");
        b1.setAdresa("address1");

        Vikendica b2 = new Vikendica();
        b2.setNaziv("type2");
        b2.setAdresa("address2");

        vikendicaRepozitorijum.save(b1);
        vikendicaRepozitorijum.save(b2);
        List<Vikendica> expectedList = new ArrayList<Vikendica>();
        expectedList.add(b1);
        expectedList.add(b2);
        when(vikendicaRepozitorijum.findAll()).thenReturn(expectedList);
        List<Vikendica> filteredList = filteredList = vikendicaServis.VikFilter("address2");
        assertEquals(1, filteredList.size());
        assertEquals("type2", filteredList.get(0).getNaziv());
        assertEquals("address2", filteredList.get(0).getAdresa());

        filteredList = vikendicaServis.VikFilter("svi");
        assertEquals(2, filteredList.size());
    }


}
