package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.*;
import enumi.*;

import manager.*;
import java.util.*;
public class IzvestajiTest {
	public static IzvestajiManager im;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak izvestaj testa");
		im = IzvestajiManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj izvestaj testa");
	}
	
	@Test
	public void prihodiRashodi() {
		ArrayList<Float> prihodiRashodi = new ArrayList<Float>();
		prihodiRashodi = IzvestajiManager.getInstance().prihodiRashodi("2024-05-01", "2024-07-01");
		assertTrue(prihodiRashodi.size()>0);
		assertTrue(prihodiRashodi.get(1) == 26400.0f);
	}
	
	@Test
	public void sredjenSobe() {
		HashMap<String, Integer> sredjeneSobe = new HashMap<String, Integer>();
		sredjeneSobe = IzvestajiManager.getInstance().sredjeneSobe("2024-05-01", "2024-07-01");
		assertNotNull(sredjeneSobe);
		assertTrue(sredjeneSobe.get("janaSobarica")==1);
	}
	
	@Test
	public void potvrdjeneRezervacije() {
		int potvrdjene = IzvestajiManager.getInstance().potvrdjeneRezervacije("2024-05-01", "2024-07-01");
		assertTrue(potvrdjene==1);
	}
	
	@Test
	public void obradjeneRezervacije() {
		ArrayList<Integer> obradjene = new ArrayList<Integer>();
		obradjene = IzvestajiManager.getInstance().obradjeneRezervacije("2024-05-01", "2024-07-01");
		assertTrue(obradjene.get(0)==1);
		assertTrue(obradjene.get(1)==1);
		assertTrue(obradjene.get(2)==1);
	}
	
	@Test
	public void sobaNocenja() {
		HashMap<Integer, Integer> sobaNocenja = new HashMap<Integer, Integer>();
		sobaNocenja = IzvestajiManager.getInstance().sobaNocenja("2024-05-01", "2024-07-01");
		
		assertTrue(sobaNocenja.get(112)==1);
		assertFalse(sobaNocenja.get(113)==12);
	}
	
	@Test
	public void sobaPrihodi() {
		HashMap<Integer, Float> sobaPrihodi = new HashMap<Integer, Float>();
		sobaPrihodi = IzvestajiManager.getInstance().sobaPrihodi("2024-05-01", "2024-07-01");
		assertTrue(sobaPrihodi.get(112)==840f);
		assertTrue(sobaPrihodi.get(111)==0f);
		assertFalse(sobaPrihodi.get(113)==12f);
	}
	
	@Test
	public void prihodiZadnjaGod() {
		ArrayList<Float> prihodiZadnjaGodina = new ArrayList<Float>();
		prihodiZadnjaGodina = IzvestajiManager.getInstance().prihodiZadnjaGodina("Trokrevetna (2+1)");
		ArrayList<Float> prihodiZadnjaGodina1 = new ArrayList<Float>();
		prihodiZadnjaGodina1 = IzvestajiManager.getInstance().prihodiZadnjaGodina("Dvokrevetna (1+1)");
		
		assertNotNull(prihodiZadnjaGodina);
		assertTrue(prihodiZadnjaGodina.get(0)>50);
		assertTrue(prihodiZadnjaGodina1.get(0)==0);
	}
	
	@Test
	public void opterecenostSobarica() {
		HashMap<String, Integer> opterecenostSobarica = new HashMap<String, Integer>();
		opterecenostSobarica = IzvestajiManager.getInstance().opterecenostSobarica();
		assertTrue(opterecenostSobarica.get("janaSobarica")>0);
	}
	
	@Test
	public void statusRezervacije() {
		HashMap<String, Integer> statusRezervacijaPrethodniMesec = new HashMap<String, Integer>();
		statusRezervacijaPrethodniMesec = IzvestajiManager.getInstance().statusRezervacijaPrethodniMesec();
		assertTrue(statusRezervacijaPrethodniMesec.get("NA_CEKANJU")>0);
		assertTrue(statusRezervacijaPrethodniMesec.get("POTVRDJENA")>0);
		assertTrue(statusRezervacijaPrethodniMesec.get("ODBIJENA")>0);
		assertTrue(statusRezervacijaPrethodniMesec.get("OTKAZANA")>0);
	}
}
