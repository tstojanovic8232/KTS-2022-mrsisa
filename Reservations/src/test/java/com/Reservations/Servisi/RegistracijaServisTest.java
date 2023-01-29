package com.Reservations.Servisi;

import com.Reservations.DTO.RegistracijaKorisnikaDTO;
import com.Reservations.DTO.RegistracijaVlasnikaInstruktoraDTO;
import com.Reservations.Modeli.Registracija;
import com.Reservations.Modeli.Usluga;
import com.Reservations.Modeli.enums.TipRegistracije;
import com.Reservations.Repozitorijumi.RegistracijaRepozitorijum;
import com.Reservations.Servis.RegistracijaServis;
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
public class RegistracijaServisTest {

    @Mock
    private RegistracijaRepozitorijum registracijaRepozitorijum;
    @InjectMocks
    private RegistracijaServis registracijaServis;

    private Registracija testReg;
    private Registracija testReg2;
    private RegistracijaVlasnikaInstruktoraDTO testRegDTO;
    private Long testId = 1L;

private List<Registracija> testRegistracije;
    @BeforeEach
    public void setUp() {
        testReg = new Registracija();
        testReg.setID(testId);
        testReg.setKorisnickoIme("testUsername");
        testReg.setTipRegistracije(TipRegistracije.VikendicaVlasnik);
        testReg.setAdresa("testAdresa");
        testReg.setDrzava("testDrzava");
        testReg.setEmail("testEmail");
        testReg.setGrad("testGrad");
        testReg.setBrojTel("testBroj");
        testReg.setIme("John");
        testReg.setPrezime("Doe");
        testReg.setLozinka("test");
        testReg.setRazlogRegistracije("testRazlog");

        testRegDTO = new RegistracijaVlasnikaInstruktoraDTO();
        testRegDTO.setId(2L);
        testRegDTO.setUsername("testUserDTO");
        testRegDTO.setEmail("testEmailDTO");
        testRegDTO.setAddress("testAddressDTO");
        testRegDTO.setCity("testCityDTO");
        testRegDTO.setCountry("testCountryDTO");
        testRegDTO.setFirstName("Jane");
        testRegDTO.setLastName("Doe");
        testRegDTO.setPhone("testPhoneDTO");
        testRegDTO.setPassword("testDTO");
        testRegDTO.setRegisterReason("testRazlogDTO");
        testRegDTO.setRegistrationType(TipRegistracije.InstruktorPecanja);
        testReg2 = new Registracija(testRegDTO);
        testReg2.setID(testRegDTO.getId());

        testRegistracije = new ArrayList<Registracija>();
        testRegistracije.add(testReg);
        testRegistracije.add(testReg2);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByIdSuccess() {
        when(registracijaRepozitorijum.findById(testId)).thenReturn(Optional.of(testReg));

        Registracija result = registracijaServis.findById(testId);
        Long resultId = result.getID();
        assertNotNull(result);
        assertEquals(testId, resultId);
        verify(registracijaRepozitorijum, times(1)).findById(testId);
    }

    @Test
    public void testFindByIdNotFound() {
        when(registracijaRepozitorijum.findById(testId)).thenReturn(Optional.empty());

        Registracija result = registracijaServis.findById(testId);

        assertNull(result);
        verify(registracijaRepozitorijum, times(1)).findById(testId);
    }

    @Test
    public void saveTest() {
        when(registracijaRepozitorijum.save(any(Registracija.class))).thenReturn(testReg2);

        Registracija result = registracijaServis.save(testRegDTO);
        Long resultId = result.getID();
        assertNotNull(result);
        assertEquals(testRegDTO.getId(), resultId);
        assertEquals(testRegDTO.getUsername(), result.getKorisnickoIme());
        assertEquals(testRegDTO.getRegistrationType(), result.getTipRegistracije());
        assertEquals(testRegDTO.getAddress(), result.getAdresa());
        assertEquals(testRegDTO.getCity(), result.getGrad());
        assertEquals(testRegDTO.getCountry(), result.getDrzava());
        assertEquals(testRegDTO.getEmail(), result.getEmail());
        assertEquals(testRegDTO.getPhone(), result.getBrojTel());
        assertEquals(testRegDTO.getFirstName(), result.getIme());
        assertEquals(testRegDTO.getLastName(), result.getPrezime());
        assertEquals(testRegDTO.getPassword(), result.getLozinka());
        assertEquals(testRegDTO.getRegisterReason(), result.getRazlogRegistracije());
        verify(registracijaRepozitorijum, times(1)).save(any(Registracija.class));
    }

    @Test
    public void whenListAll_thenReturnListOfRegistracija() {
        when(registracijaRepozitorijum.findAll()).thenReturn(testRegistracije);

        List<Registracija> returnedReg = registracijaServis.listAll();

        assertEquals(testRegistracije, returnedReg);
        verify(registracijaRepozitorijum, times(1)).findAll();
    }

    @Test
    public void testDelete() {
        registracijaServis.delete(testId);
        verify(registracijaRepozitorijum, times(1)).deleteById(testId);
    }

}
