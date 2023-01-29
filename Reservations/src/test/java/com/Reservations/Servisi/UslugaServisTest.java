package com.Reservations.Servisi;

import com.Reservations.DTO.UslugaDTO;
import com.Reservations.Modeli.*;
import com.Reservations.Modeli.enums.TipoviUsluga;
import com.Reservations.Repozitorijumi.UslugaRepozitorijum;
import com.Reservations.Servis.GVarijableServis;
import com.Reservations.Servis.RezervacijaServis;
import com.Reservations.Servis.TerminServis;
import com.Reservations.Servis.UslugaServis;
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
public class UslugaServisTest {
    @Mock
    private UslugaRepozitorijum uslugaRepozitorijum;
    @Mock
    private RezervacijaServis rezervacijaServis;
    @Mock
    private TerminServis terminServis;
    @Mock
    private GVarijableServis gVarijableServis;
    @InjectMocks
    private UslugaServis uslugaServis;
    private Usluga testUsluga;
    private UslugaDTO testUslugaDTO;
    private Termin testTermin;
    private List<Usluga> testUsluge;
    private List<Usluga> testUsluge2;
    private long testId = 1L;
    private Korisnik testInstruktor;
    private String testNaziv = "testNaziv";

    private Uloga testUloga;

    private TipoviUsluga testTip;


    @BeforeEach
    public void setUp() {
        testUloga = new Uloga(4,"Instruktor");
        testTip = TipoviUsluga.avantura;

        testInstruktor = new Korisnik();
        testInstruktor.setID(testId);
        testInstruktor.setUloga(testUloga);

        Korisnik testInstruktor2 = new Korisnik();
        testInstruktor2.setID(2L);
        testInstruktor2.setUloga(testUloga);

        testUsluga = new Usluga();
        testUsluga.setID(testId);
        testUsluga.setInstruktor(testInstruktor);
        testUsluga.setTip(testTip);

        Usluga testUsluga2 = new Usluga();
        testUsluga2.setID(2L);
        testUsluga2.setInstruktor(testInstruktor);
        testUsluga2.setTip(testTip);

        Usluga testUsluga3 = new Usluga();
        testUsluga3.setID(3L);
        testUsluga3.setInstruktor(testInstruktor);
        testUsluga3.setTip(TipoviUsluga.casovi_pecanja);

        Usluga testUsluga4 = new Usluga();
        testUsluga4.setID(4L);
        testUsluga4.setInstruktor(testInstruktor2);
        testUsluga4.setTip(testTip);

        Usluga testUsluga5 = new Usluga();
        testUsluga5.setID(5L);
        testUsluga5.setInstruktor(testInstruktor2);
        testUsluga5.setTip(TipoviUsluga.casovi_pecanja);

        testUslugaDTO = new UslugaDTO();
        testUslugaDTO.setNaziv(testNaziv);
        testUslugaDTO.setTip(testTip.toString());

        testTermin = new Termin();
        testTermin.setID(testId);
        testTermin.setUsluga(testUsluga);

        testUsluge = new ArrayList<>();
        testUsluge2 = new ArrayList<>();
        testUsluge.add(testUsluga);
        testUsluge.add(testUsluga2);
        testUsluge.add(testUsluga3);
        testUsluge2.add(testUsluga4);
        testUsluge2.add(testUsluga5);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenListAll_thenReturnListOfUsluga() {
        when(uslugaRepozitorijum.findAll()).thenReturn(testUsluge);

        List<Usluga> returnedUsluge = uslugaServis.listAll();

        assertEquals(testUsluge, returnedUsluge);
        verify(uslugaRepozitorijum, times(1)).findAll();
    }

    @Test
    public void testFindById_Success() {
        Usluga expectedUsluga = new Usluga();
        expectedUsluga.setID(testId);
        expectedUsluga.setInstruktor(testInstruktor);

        when(uslugaRepozitorijum.findById(testId)).thenReturn(Optional.of(expectedUsluga));

        Usluga result = uslugaServis.findById(testId);

        verify(uslugaRepozitorijum).findById(testId);
        assertEquals(expectedUsluga, result);
    }

    @Test
    public void testFindById_NotFound() {
        when(uslugaRepozitorijum.findById(testId)).thenReturn(Optional.empty());

        Usluga result = uslugaServis.findById(testId);

        verify(uslugaRepozitorijum).findById(testId);
        assertNull(result);
    }

    @Test
    public void testFindByInstruktor() {
        when(uslugaRepozitorijum.findAll()).thenReturn(testUsluge);

        List<Usluga> result = uslugaServis.findByInstruktor(testId);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(testUsluga, result.get(0));

        verify(uslugaRepozitorijum, times(1)).findAll();
    }

    @Test
    public void testSave() {
        // arrange
        when(uslugaRepozitorijum.save(any(Usluga.class))).thenReturn(testUsluga);
        when(uslugaServis.findByInstruktor(testId)).thenReturn(testUsluge);

        // act
        Usluga result = uslugaServis.save(testUslugaDTO, testInstruktor);

        // assert
        assertNotNull(result);
        assertEquals(testUsluga, result);
        verify(uslugaRepozitorijum).save(any(Usluga.class));
    }

    @Test
    public void update_Successful() {
        // Given
        UslugaDTO uslugaDTO = new UslugaDTO();
        uslugaDTO.setNaziv("newNaziv");
        uslugaDTO.setAdresa("newAdresa");
        uslugaDTO.setOpis("newOpis");
        uslugaDTO.setPecaroskaOprema("new");
        uslugaDTO.setMaxOsoba(5);
        uslugaDTO.setCena(100.0);
        uslugaDTO.setTip(TipoviUsluga.casovi_pecanja.toString());
        uslugaDTO.setLinkSlike("testSlika");

        testUsluga.setNaziv(testNaziv);
        testUsluga.setAdresa("oldAdresa");
        testUsluga.setOpis("oldOpis");
        testUsluga.setMaxOsoba(3);
        testUsluga.setCena(50.0);

        Usluga expectedUsluga = new Usluga();
        expectedUsluga.setNaziv("newNaziv");
        expectedUsluga.setAdresa("newAdresa");
        expectedUsluga.setOpis("newOpis");
        expectedUsluga.setPecaroskaOprema("new");
        expectedUsluga.setMaxOsoba(5);
        expectedUsluga.setCena(100.0);
        expectedUsluga.setTip(TipoviUsluga.casovi_pecanja);
        expectedUsluga.setLinkSlike("testSlika");
        expectedUsluga.setInstruktor(testUsluga.getInstruktor());
        expectedUsluga.setID(1L);

        when(uslugaRepozitorijum.findById(testId)).thenReturn(Optional.of(testUsluga));
        when(uslugaRepozitorijum.save(any(Usluga.class))).thenReturn(expectedUsluga);
        // When
        Usluga updatedUsluga = uslugaServis.update(uslugaDTO, testId);
        System.out.println(updatedUsluga);
        // Then
        assertNotNull(updatedUsluga);
        assertEquals("newNaziv", updatedUsluga.getNaziv());
        assertEquals("newAdresa", updatedUsluga.getAdresa());
        assertEquals("newOpis", updatedUsluga.getOpis());
        assertEquals(5, updatedUsluga.getMaxOsoba());
        assertEquals(100.0, updatedUsluga.getCena(), 0.0);
    }

    @Test
    public void update_UslugaNotFound() {
        // Given
        UslugaDTO uslugaDTO = new UslugaDTO();
        uslugaDTO.setNaziv("newNaziv");
        uslugaDTO.setAdresa("newAdresa");
        uslugaDTO.setOpis("newOpis");
        uslugaDTO.setMaxOsoba(5);
        uslugaDTO.setCena(100.0);

        when(uslugaRepozitorijum.findById(1L)).thenReturn(Optional.empty());

        // When
        Usluga updatedUsluga = uslugaServis.update(uslugaDTO, 1L);

        // Then
        assertNull(updatedUsluga);
    }

    @Test
    public void testDeleteById() {
        uslugaServis.deleteById(testId);
        verify(uslugaRepozitorijum, times(1)).deleteById(testId);
    }

}
