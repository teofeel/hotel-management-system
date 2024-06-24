package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import enumi.StatusRezervacije;
import enumi.StatusSobe;
import enumi.StrucnaSprema;
import enumi.TipSobeEnum;
import manager.*;
import entity.*;
public class GostTests {
	public static GostManager gm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak gost testa");
		gm = GostManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj gost testa");
	}
	
	@Test
	public void zahtevRezervacje() {
		Gost g = new Gost("Petar", "Petrovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "8979723", "zahtevRezerevacijeTest@example.com");
		gm.gosti.put("zahtevRezerevacijeTest@example.com",g);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		String poruka = gm.zahtevRezervacija(GostManager.gosti.get("zahtevRezerevacijeTest@example.com"), CenovnikManager.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-01-10", "2025-01-13", usluge);
		assertEquals("Zahtev je poslat", poruka);
		
		String poruka1 = gm.zahtevRezervacija(GostManager.gosti.get("zahtevRezerevacijeTest@example.com"), CenovnikManager.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-01-13", "2025-01-10", usluge);
		assertEquals("Nije moguce napraviti rezervaciju", poruka1);
	}
	
	@Test 
	public void otkaziRezervaciju(){
		Gost g = new Gost("Petar", "Petrovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "8979723", "otkaziRezerevacijeTest@example.com");
		gm.gosti.put("otkaziRezerevacijeTest@example.com",g);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		String poruka = gm.zahtevRezervacija(GostManager.gosti.get("otkaziRezerevacijeTest@example.com"), CenovnikManager.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-01-10", "2025-01-13", usluge);
		assertEquals("Zahtev je poslat", poruka);
		
		String poruka1 = gm.otkaziRezervaciju(g,  "2025-12-10", "2025-12-13");
		assertEquals("Trazena rezervacija ne postoji", poruka1);
		
		String poruka2 = gm.otkaziRezervaciju(g,  "2025-01-10", "2025-01-13");
		assertEquals("Rezervacija je otkazana", poruka2);
	}
	
	@Test
	public void dodajUsluguNaRezervaciju() {
		Gost g = new Gost("Petar", "Petrovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "8979723", "dodajUsluguTest@example.com");
		gm.gosti.put("dodajUsluguTest@example.com",g);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		String poruka = gm.zahtevRezervacija(g, CenovnikManager.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-01-10", "2025-01-13", usluge);
		assertEquals("Zahtev je poslat", poruka);
		
		String poruka1 = gm.dodajUsluguNaRezervaciju(g, CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "2025-01-10", "2025-01-13");
		assertEquals("Dodata usluga na rezervaciju", poruka1);
		
		String poruka2 = gm.dodajUsluguNaRezervaciju(g, CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "2025-01-12", "2025-01-18");
		assertEquals("Rezervacija ne postoji", poruka2);
	}
	
	@Test
	public void izbaciUsluguSaRezervaciju() {
		Gost g = new Gost("Petar", "Petrovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "8979723", "dodajUsluguTest@example.com");
		gm.gosti.put("dodajUsluguTest@example.com",g);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		String poruka = gm.zahtevRezervacija(g, CenovnikManager.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-01-10", "2025-01-13", usluge);
		assertEquals("Zahtev je poslat", poruka);
		
		String poruka1 = gm.dodajUsluguNaRezervaciju(g, CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "2025-01-10", "2025-01-13");
		assertEquals("Dodata usluga na rezervaciju", poruka1);
		
		String poruka2 = gm.izbaciUsluguSaRezervacije(g, CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "2025-01-10", "2025-01-13");
		assertEquals("Izbacena usluga sa rezervaciju", poruka2);
		
		String poruka3 = gm.izbaciUsluguSaRezervacije(g, CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "2025-01-12", "2025-01-13");
		assertEquals("Rezervacija ne postoji", poruka3);
	}

}
