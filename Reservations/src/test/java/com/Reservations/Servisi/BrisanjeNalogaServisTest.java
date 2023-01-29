package com.Reservations.Servisi;

import com.Reservations.DTO.ZahtevZaBrisanjeDTO;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.ZahtevZaBrisanje;
import com.Reservations.Repozitorijumi.BrisanjeNalogaRepozitorijum;
import com.Reservations.Servis.BrisanjeNalogaServis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BrisanjeNalogaServisTest {

    @Mock
    private BrisanjeNalogaRepozitorijum brisanjeRepozitorijum;
    @InjectMocks
    private BrisanjeNalogaServis bnServis;
    private ZahtevZaBrisanje testZB;
    private ZahtevZaBrisanje testZB2;
    private ZahtevZaBrisanje testZB3;
    private ZahtevZaBrisanjeDTO testZBDTO;
    private Korisnik testKorisnik;
    private Long testId = 1L;
    private List<ZahtevZaBrisanje> testZahtevi;

    @BeforeEach
    public void setUp() {
        testZB = new ZahtevZaBrisanje();
        testZB.setID(testId);
        testZB.setKorisnickoIme("testUsername");
        testZB.setAdresa("testAdresa");
        testZB.setDrzava("testDrzava");
        testZB.setEmail("testEmail");
        testZB.setGrad("testGrad");
        testZB.setBrojTel("testBroj");
        testZB.setIme("John");
        testZB.setPrezime("Doe");
        testZB.setLozinka("test");
        testZB.setRazlogRegistracije("testRazlog");

        testZBDTO = new ZahtevZaBrisanjeDTO();
        testZBDTO.setId(2L);
        testZBDTO.setUsername("testUserDTO");
        testZBDTO.setEmail("testEmailDTO");
        testZBDTO.setAddress("testAddressDTO");
        testZBDTO.setCity("testCityDTO");
        testZBDTO.setCountry("testCountryDTO");
        testZBDTO.setFirstName("Jane");
        testZBDTO.setLastName("Doe");
        testZBDTO.setPhone("testPhoneDTO");
        testZBDTO.setPassword("testDTO");

        testZB2 = new ZahtevZaBrisanje();
        testZB2.setID(testZBDTO.getId());
        testZB2.setKorisnickoIme(testZBDTO.getUsername());
        testZB2.setAdresa(testZBDTO.getAddress());
        testZB2.setDrzava(testZBDTO.getCountry());
        testZB2.setEmail(testZBDTO.getEmail());
        testZB2.setGrad(testZBDTO.getCity());
        testZB2.setBrojTel(testZBDTO.getPhone());
        testZB2.setIme(testZBDTO.getFirstName());
        testZB2.setPrezime(testZBDTO.getLastName());
        testZB2.setLozinka(testZBDTO.getPassword());
        testZB2.setRazlogRegistracije(testZBDTO.getRazlog());

        testKorisnik = new Korisnik();
        testZB3 = new ZahtevZaBrisanje();
        testZB3.setKorisnickoIme(testKorisnik.getKorisnickoIme());
        testZB3.setLozinka(testKorisnik.getLozinka());
        testZB3.setID(testKorisnik.getID());
        testZB3.setIme(testKorisnik.getIme());
        testZB3.setPrezime(testKorisnik.getPrezime());
        testZB3.setAdresa(testKorisnik.getAdresa());
        testZB3.setGrad(testKorisnik.getGrad());
        testZB3.setDrzava(testKorisnik.getDrzava());
        testZB3.setBrojTel(testKorisnik.getBrojTel());
        testZB3.setRazlogRegistracije("testRazlogUser");
        testZB3.setEmail(testKorisnik.getEmail());

        testZahtevi = new ArrayList<>();
        testZahtevi.add(testZB);
        testZahtevi.add(testZB2);
        testZahtevi.add(testZB3);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByIdSuccess() {
        when(brisanjeRepozitorijum.findById(testId)).thenReturn(Optional.of(testZB));

        ZahtevZaBrisanje result = bnServis.findById(testId);
        Long resultId = result.getID();
        assertNotNull(result);
        assertEquals(testId, resultId);
        verify(brisanjeRepozitorijum, times(1)).findById(testId);
    }

    @Test
    public void testFindByIdNotFound() {
        when(brisanjeRepozitorijum.findById(testId)).thenReturn(Optional.empty());

        ZahtevZaBrisanje result = bnServis.findById(testId);

        assertNull(result);
        verify(brisanjeRepozitorijum, times(1)).findById(testId);
    }

    @Test
    public void whenListAll_thenReturnListOfZB() {
        when(brisanjeRepozitorijum.findAll()).thenReturn(testZahtevi);

        List<ZahtevZaBrisanje> returnedReg = bnServis.listAll();

        assertEquals(testZahtevi, returnedReg);
        verify(brisanjeRepozitorijum, times(1)).findAll();
    }

    @Test
    public void testDelete() {
        bnServis.delete(testId);
        verify(brisanjeRepozitorijum, times(1)).deleteById(testId);
    }

    @Test
    public void testFindByKorisnickoIme() {
        String ime = "testUsername";
        when(brisanjeRepozitorijum.findByKorisnickoIme(ime)).thenReturn(testZB);
        ZahtevZaBrisanje result = bnServis.findByKorisnickoIme(ime);
        assertEquals(testZB, result);
    }

    @Test
    public void saveTest() {
        when(brisanjeRepozitorijum.save(any(ZahtevZaBrisanje.class))).thenReturn(testZB2);
        ZahtevZaBrisanje savedZB = bnServis.save(testZBDTO);

        assertEquals(testZB2.getKorisnickoIme(), savedZB.getKorisnickoIme());
        assertEquals(testZB2.getLozinka(), savedZB.getLozinka());
        assertEquals(testZB2.getID(), savedZB.getID());
        assertEquals(testZB2.getIme(), savedZB.getIme());
        assertEquals(testZB2.getPrezime(), savedZB.getPrezime());
        assertEquals(testZB2.getAdresa(), savedZB.getAdresa());
        assertEquals(testZB2.getGrad(), savedZB.getGrad());
        assertEquals(testZB2.getDrzava(), savedZB.getDrzava());
        assertEquals(testZB2.getBrojTel(), savedZB.getBrojTel());
        assertEquals(testZB2.getRazlogRegistracije(), savedZB.getRazlogRegistracije());
        assertEquals(testZB2.getEmail(), savedZB.getEmail());

        verify(brisanjeRepozitorijum, times(1)).save(any(ZahtevZaBrisanje.class));
    }

    @Test
    public void testSave() {
        String razlog = "testRazlogUser";

        when(brisanjeRepozitorijum.save(any(ZahtevZaBrisanje.class))).thenReturn(testZB3);
        ZahtevZaBrisanje actual = bnServis.save(testKorisnik, razlog);

        assertEquals(testZB3, actual);
        verify(brisanjeRepozitorijum, times(1)).save(any(ZahtevZaBrisanje.class));
    }

}
