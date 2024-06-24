package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Cenovnik;
import enumi.TipSobeEnum;
import manager.CenovnikManager;

public class CenovnikTest {
	public static CenovnikManager cm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak cenovnik testa");
		cm = CenovnikManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj cenovnik testa");
	}
	
	
	@Test
	public void izmeniCenuSobe() {
		String poruka = cm.izmeniCenuSobe(cm.cenovnici.get(0), TipSobeEnum.JEDNOKREVETNA.toString(), "50");
		assertEquals("Izmenjena cena sobe",poruka);
		
		String poruka1 = cm.izmeniCenuSobe(cm.cenovnici.get(0), "Cetv", "50");
		assertEquals("Cenovnik ne poseduje ovaj tip sobe",poruka1);
		
		String poruka2 = cm.izmeniCenuSobe(cm.cenovnici.get(0), "Cetv", "");
		assertEquals("Polje mora biti popunjeno",poruka2);
	}
	
	
	@Test
	public void dodajUslugu() {
		String poruka = cm.dodajNovuUslugu("Plaza", "10");
		assertEquals("Nova usluga je dodata u cenovnike",poruka);
		
		String poruka1 = cm.dodajNovuUslugu("Plaza", "");
		assertEquals("Sva polja moraju biti popunjena",poruka1);
		
	}
	
	@Test
	public void izmeniCenuUsluge() {
		String poruka = cm.izmeniCenuUsluge(cm.cenovnici.get(0), "Dorucak", "50");
		assertEquals("Izmenjena cena usluge",poruka);
		
		String poruka1 = cm.izmeniCenuUsluge(cm.cenovnici.get(0), "Cetv", "50");
		assertEquals("Cenovnik ne poseduje ovu uslugu",poruka1);
		
		String poruka2 = cm.izmeniCenuUsluge(cm.cenovnici.get(0), "Cetv", "");
		assertEquals("Polje mora biti popunjeno",poruka2);
	}
	
	@Test
	public void obrisiUslugu() {
		String poruka = cm.dodajNovuUslugu("Plaza", "10");
		assertEquals("Nova usluga je dodata u cenovnike",poruka);
		
		String poruka1 = cm.obrisiUslugu("Plaza");
		assertEquals("Uspenso obrisana usluga",poruka1);
	}
	
	@Test
	public void getCenovnik() {
		Cenovnik c = cm.getCenovnik(LocalDate.parse("2024-01-01"),LocalDate.parse("2024-12-31"));
		assertSame(cm.cenovnici.get(0), c);
		
		Cenovnik c1 = cm.getCenovnik(LocalDate.parse("2024-01-02"),LocalDate.parse("2024-12-29"));
		assertNotSame(cm.cenovnici.get(0), c1);
	}

}
