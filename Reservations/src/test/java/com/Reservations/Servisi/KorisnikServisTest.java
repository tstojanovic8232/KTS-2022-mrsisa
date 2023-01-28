package com.Reservations.Servisi;

import com.Reservations.DTO.RegistracijaKorisnikaDTO;
import com.Reservations.Modeli.Korisnik;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Modeli.Uloga;
import com.Reservations.Modeli.enums.TipRegistracije;
import com.Reservations.Repozitorijumi.KorisnikRepozitorijum;
import com.Reservations.Servis.KorisnikServis;
import com.Reservations.Servis.UlogaServis;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class KorisnikServisTest {

    @Mock
    private KorisnikRepozitorijum korisnikRepozitorijum;
    @Mock
    private UlogaServis ulogaServis;
    @InjectMocks
    private KorisnikServis korisnikServis;
    private Korisnik testKorisnik;
    private RegistracijaKorisnikaDTO testRegistracijaKorisnikaDTO;
    private Uloga testUloga;
    private List<Korisnik> testKorisnici;
    private long testId = 1L;
    private String testUsername = "testUsername";

    private Registracija testReg;

    @BeforeEach
    public void setUp() {
        testKorisnik = new Korisnik();
        testKorisnik.setID(testId);
        testKorisnik.setKorisnickoIme(testUsername);

        testRegistracijaKorisnikaDTO = new RegistracijaKorisnikaDTO();
        testRegistracijaKorisnikaDTO.setUsername(testUsername);

        testUloga = new Uloga();
        testUloga.setIme("Klijent");

        testKorisnici = new ArrayList<>();
        testKorisnici.add(testKorisnik);
        testReg=new Registracija();
        testReg.setTipRegistracije(TipRegistracije.BrodVlasnik);
        testReg.setKorisnickoIme(testUsername);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkIfUsernameReturnsExpectedUser() {


        // given a user
        Korisnik expectedUser = new Korisnik();
        expectedUser.setKorisnickoIme("username");

        // mock the korisnikRepozitorijum to return the expected user when findByKorisnickoIme is called

        when(korisnikRepozitorijum.findByKorisnickoIme("username")).thenReturn(expectedUser);

        // set the mocked repository to the service
        korisnikServis.korisnikRepozitorijum = korisnikRepozitorijum;

        // when findByUsername is called
        Korisnik actualUser = korisnikServis.findByUsername("username");

        // then the expected user is returned
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void checkIfUserDeleted() {
        // given
        Long id = 1L;
        Korisnik user = new Korisnik();
        user.setID(id);

        when(korisnikRepozitorijum.findById(id)).thenReturn(java.util.Optional.of(user));

        // when
        korisnikServis.delete(id);

        // then
        verify(korisnikRepozitorijum).deleteById(id);
    }
    @Test
    public void areAllUsersListed() {
        Korisnik korisnik1 = new Korisnik();
        Korisnik korisnik2 = new Korisnik();
        korisnik1.setID(1L);
        korisnik1.setIme("Jane");
        korisnik1.setPrezime("Doe");
        korisnik1.setEmail("email");
        korisnik2.setID(2L);
        korisnik2.setIme("John");
        korisnik2.setPrezime("Doe");
        korisnik2.setEmail("email");
        List<Korisnik> expectedKorisnici = Arrays.asList(korisnik1, korisnik2);

        when(korisnikRepozitorijum.findAll()).thenReturn(expectedKorisnici);

        List<Korisnik> result = korisnikServis.listAll();

        assertEquals(expectedKorisnici, result);
    }
    @Test
    public void testFindById() {
        Korisnik korisnik1 = new Korisnik();
        korisnik1.setID(1L);
        korisnik1.setIme("Jane");
        korisnik1.setPrezime("Doe");
        korisnik1.setEmail("email");
        when(korisnikRepozitorijum.findById(1L)).thenReturn(Optional.of(korisnik1));
        Korisnik korisnik = korisnikServis.findById(1L);
        assertEquals(1L, korisnik.getID());
        assertEquals("Jane", korisnik.getIme());
    }

    @Test
    public void testSave() {
        when(korisnikServis.findByUsername(testRegistracijaKorisnikaDTO.getUsername())).thenReturn(testKorisnik);
        korisnikServis.save(this.testRegistracijaKorisnikaDTO);

        // retrieve the saved user from the system
        Korisnik savedUser = korisnikServis.findByUsername(testRegistracijaKorisnikaDTO.getUsername());

        // check if the saved user is not null
        assertNotNull(savedUser);

        // check if the saved user's username, password, and email match the userRequest
        assertEquals(testUsername, savedUser.getUsername());
        assertEquals(testId, savedUser.getID());
        assertEquals(testKorisnik, savedUser);
    }

    @Test
    public void testSaveByReg() {
        when(korisnikServis.findByUsername(testReg.getKorisnickoIme())).thenReturn(testKorisnik);
        korisnikServis.save(this.testReg);

        // retrieve the saved user from the system
        Korisnik savedUser = korisnikServis.findByUsername(testReg.getKorisnickoIme());

        // check if the saved user is not null
        assertNotNull(savedUser);

        // check if the saved user's username, password, and email match the userRequest
        assertEquals(testUsername, savedUser.getUsername());
        assertEquals(testId, savedUser.getID());
        assertEquals(testKorisnik, savedUser);
    }
}

