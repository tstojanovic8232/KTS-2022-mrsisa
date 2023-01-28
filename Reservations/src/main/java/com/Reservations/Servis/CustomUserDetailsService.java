package com.Reservations.Servis;

import com.Reservations.Modeli.Korisnik;
import com.Reservations.Repozitorijumi.KorisnikRepozitorijum;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private KorisnikRepozitorijum korisnickiRepozitorijum;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public UserDetails loadUserByUsername(String korisnickoIme) throws UsernameNotFoundException {
        Korisnik korisnik = korisnickiRepozitorijum.findByKorisnickoIme(korisnickoIme);
        if (korisnik == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", korisnickoIme));
        } else {
            return korisnik;
        }
    }


    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String korisnickoIme = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '"+ korisnickoIme + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(korisnickoIme, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");

            return;
        }

        LOGGER.debug("Changing password for user '"+ korisnickoIme + "'");

        Korisnik korisnik = (Korisnik) loadUserByUsername(korisnickoIme);

        //pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
        //ne zelimo da u bazi cuvamo lozinke u plain text formatu
        //korisnik.setLozinka(korisnik.getLozinka(lozinka));
        korisnik.setLozinka(passwordEncoder.encode(newPassword));
        korisnickiRepozitorijum.save(korisnik);

    }

}
