package io.travelook.controller.test;

import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import io.travelook.controller.annuncio.AnnuncioController;
import io.travelook.model.Segnalazione;
import io.travelook.model.Stato;
import io.travelook.model.Utente;
import io.travelook.model.Viaggio;
import io.travelook.persistence.MssqlSegnalazioniDAO;

class SegnalazioniTest {

	@Test
	void testAggiungiSegnalazione() {
		Utente segnalato = new Utente(1, "asalvucci", "andrea@gmail.com", "Andrea", "Salvucci", new Date(1997,11,14) ,"C:/");
		Utente segnalante = new Utente(3, "nbartelucci", "nicolo1@gmail.com", "Nicolo", "Bartelucci", new Date(1997,11,14) ,"C:/");
		Viaggio v = new Viaggio(2, segnalato ,"TITOLO AGGIORNATO", "Montecosaro", "Dialetto Maceratese", 2, "se magna",
				"Macerata", new Date(2019,11,14), new Date(2019,11,17), Stato.INIZIO);
		AnnuncioController ac = new AnnuncioController();
		MssqlSegnalazioniDAO db = new MssqlSegnalazioniDAO(ac.getDbConnection());
		Segnalazione s = new Segnalazione(1, v, segnalato, segnalante, "HA OFFESO MIA MADRE");
		assertTrue(db.create(s));
	}
}