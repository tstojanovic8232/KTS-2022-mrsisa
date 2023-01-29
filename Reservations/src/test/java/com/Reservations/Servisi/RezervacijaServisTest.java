package com.Reservations.Servisi;

import com.Reservations.DTO.RezervacijaDTO;
import com.Reservations.Modeli.*;
import com.Reservations.Modeli.enums.TipEntiteta;
import com.Reservations.Modeli.enums.TipRezervacije;
import com.Reservations.Repozitorijumi.RezervacijaRepozitorijum;
import com.Reservations.Repozitorijumi.VikendicaRepozitorijum;
import com.Reservations.Servis.*;
import org.checkerframework.checker.units.qual.K;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RezervacijaServisTest {

    @Mock
    private RezervacijaRepozitorijum rezervacijaRepozitorijum;

    @Mock
    private UslugaServis uslugaServis;


    @Mock
    private VikendicaServis vikendicaServis;

    @Mock
    private BrodServis brodServis;


    @Mock
    private KorisnikServis korisnikServis;

    @Mock
    private TerminServis terminServis;

    @InjectMocks
    private RezervacijaServis rezervacijaServis;



    private TipEntiteta e;
    private Long id;
    private TipRezervacije tip;
    private Long id2;

    @BeforeEach
    public void setUp() {
       RezervacijaDTO regRequest = new RezervacijaDTO();
        regRequest.setDatum("01/01/2022");
        regRequest.setVreme("12:00");
        regRequest.setTrajanje("5");
        regRequest.setMaxOsoba(10);

        e = TipEntiteta.brod;
        id = 1L;
        tip = TipRezervacije.brza;
        id2 = 2L;

        Brod brod = new Brod();
        brod.setID(id);
        brod.setNaziv("Test Brod");
        brod.setCena(1000);

        when(brodServis.findById(id)).thenReturn(brod);

        Korisnik korisnik = new Korisnik();
        korisnik.setID(id2);

        when(korisnikServis.findById(id2)).thenReturn(korisnik);
    }
    @Test
    public void testSave() {
        RezervacijaDTO regRequest = new RezervacijaDTO();

        regRequest.setDatum("01/01/2022");
        regRequest.setVreme("10:00");
        regRequest.setTrajanje("2h");
        regRequest.setMaxOsoba(5);
        TipEntiteta e = TipEntiteta.vikendica;
        Long id = 1L;
        TipRezervacije tip = TipRezervacije.obicna;
        Long id2 = 2L;
        Rezervacija r=new Rezervacija();
        r.setDatum(regRequest.getDatum());
        r.setVreme(regRequest.getVreme());
        r.setTrajanje(regRequest.getTrajanje());
        r.setTrajanje("5h");
        r.setID(1L);
        r.setCena(2000);
        r.setEntitetId(1L);
        r.setNazivEntiteta("vikendica");
        r.setTipEntiteta(e);
        r.setTip(tip);
        r.setKlijent(new Korisnik());
        r.setIzvjestaj("sd");
        r.setMaxOsoba(5);
        when(vikendicaServis.findById(any(Long.class))).thenReturn(new Vikendica());
        when(rezervacijaRepozitorijum.save(any(Rezervacija.class))).thenReturn(r);
        Rezervacija rezervacija = rezervacijaServis.save(regRequest, e, id, tip, id2);
        assertNotNull(rezervacija);

        assertEquals(TipRezervacije.obicna, rezervacija.getTip());
        assertEquals(TipEntiteta.vikendica, rezervacija.getTipEntiteta());

        assertEquals(0L, rezervacija.getKlijent().getID());
        assertEquals("01/01/2022", rezervacija.getDatum());
        assertEquals("10:00", rezervacija.getVreme());
        assertEquals("5h", rezervacija.getTrajanje());
        assertEquals(5, rezervacija.getMaxOsoba());
    }

    @Test
    public void testFindByKlijent() {
        // Arrange
        Korisnik k=new Korisnik();
        k.setID(1L);
        k.setUloga(new Uloga(0L,"Klijent"));
        List<Rezervacija> mockList = new ArrayList<>();
        Rezervacija r1 = new Rezervacija();
        r1.setTip(TipRezervacije.obicna);
        r1.setDatum("01/01/2022");
        r1.setKlijent(k);
        mockList.add(r1);
        Rezervacija r2 = new Rezervacija();
        r2.setTip(TipRezervacije.obicna);
        r2.setDatum("01/01/2024");
        r2.setKlijent(k);
        mockList.add(r2);
        when(rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna)).thenReturn(mockList);

        // Act
        List<Rezervacija> result = rezervacijaServis.findByKlijent(1L, Sort.unsorted());

        // Assert
        assertEquals(1, result.size());
        assertEquals(r2, result.get(0));
    }

    @Test
    public void testFindByKlijentDateBrod() {
        List<Rezervacija> li = new ArrayList<Rezervacija>();
        Rezervacija r1 = new Rezervacija();
        r1.setTip(TipRezervacije.obicna);
        r1.setDatum("01/01/2022");
        r1.setTipEntiteta(TipEntiteta.brod);
        Korisnik k=new Korisnik();
        k.setID(1L);
        k.setUloga(new Uloga(0L,"Klijent"));
        r1.setKlijent(k);
        li.add(r1);

        Rezervacija r2 = new Rezervacija();
        r2.setTip(TipRezervacije.obicna);
        r2.setDatum("01/01/2023");
        r2.setTipEntiteta(TipEntiteta.vikendica);
        Korisnik k2=new Korisnik();
        k2.setID(2L);
        k2.setUloga(new Uloga(0L,"Klijent"));
        r2.setKlijent(k2);
        li.add(r2);

        when(rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna)).thenReturn(li);


        List<Rezervacija> result = rezervacijaServis.findByKlijentDateBrod(1L);
        assertEquals(1, result.size());
        assertEquals(TipEntiteta.brod, result.get(0).getTipEntiteta());
        assertEquals("01/01/2022", result.get(0).getDatum());
    }

    @Test
    public void testFindByKlijentDateVik() {
        List<Rezervacija> li = new ArrayList<Rezervacija>();
        Rezervacija r1 = new Rezervacija();
        r1.setTip(TipRezervacije.obicna);
        r1.setDatum("01/01/2022");
        r1.setTipEntiteta(TipEntiteta.brod);
        Korisnik k=new Korisnik();
        k.setID(1L);
        k.setUloga(new Uloga(0L,"Klijent"));
        r1.setKlijent(k);
        li.add(r1);

        Rezervacija r2 = new Rezervacija();
        r2.setTip(TipRezervacije.obicna);
        r2.setDatum("01/01/2023");
        r2.setTipEntiteta(TipEntiteta.vikendica);
        Korisnik k2=new Korisnik();
        k2.setID(2L);
        k2.setUloga(new Uloga(0L,"Klijent"));
        r2.setKlijent(k2);
        li.add(r2);

        when(rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna)).thenReturn(li);


        List<Rezervacija> result = rezervacijaServis.findByKlijentDateVik(2L);
        assertEquals(1, result.size());
        assertEquals(TipEntiteta.vikendica, result.get(0).getTipEntiteta());
        assertEquals("01/01/2023", result.get(0).getDatum());
    }

    @Test
    public void testFindByKlijentDateUsluga() {
        List<Rezervacija> li = new ArrayList<Rezervacija>();
        Rezervacija r1 = new Rezervacija();
        r1.setTip(TipRezervacije.obicna);
        r1.setDatum("01/01/2022");
        r1.setTipEntiteta(TipEntiteta.brod);
        Korisnik k=new Korisnik();
        k.setID(1L);
        k.setUloga(new Uloga(0L,"Klijent"));
        r1.setKlijent(k);
        li.add(r1);

        Rezervacija r2 = new Rezervacija();
        r2.setTip(TipRezervacije.obicna);
        r2.setDatum("01/01/2023");
        r2.setTipEntiteta(TipEntiteta.usluga);
        Korisnik k2=new Korisnik();
        k2.setID(2L);
        k2.setUloga(new Uloga(0L,"Klijent"));
        r2.setKlijent(k2);
        li.add(r2);

        when(rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna)).thenReturn(li);


        List<Rezervacija> result = rezervacijaServis.findByKlijentDateUsluga(2L);
        assertEquals(1, result.size());
        assertEquals(TipEntiteta.usluga, result.get(0).getTipEntiteta());
        assertEquals("01/01/2023", result.get(0).getDatum());
    }

    @Test
    public void testFindByKlijentDate() {
        List<Rezervacija> li = new ArrayList<Rezervacija>();
        Rezervacija r1 = new Rezervacija();
        r1.setTip(TipRezervacije.obicna);
        r1.setDatum("01/01/2022");
        r1.setTipEntiteta(TipEntiteta.brod);
        Korisnik k=new Korisnik();
        k.setID(1L);
        k.setUloga(new Uloga(0L,"Klijent"));
        r1.setKlijent(k);
        li.add(r1);

        Rezervacija r2 = new Rezervacija();
        r2.setTip(TipRezervacije.obicna);
        r2.setDatum("02/01/2023");
        r2.setTipEntiteta(TipEntiteta.usluga);

        r2.setKlijent(k);
        li.add(r2);

        when(rezervacijaRepozitorijum.findByTip(TipRezervacije.obicna)).thenReturn(li);


        List<Rezervacija> result = rezervacijaServis.findByKlijentDate(1L);
        assertEquals(1, result.size());

        assertEquals("01/01/2022", result.get(0).getDatum());
    }


}
