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
import enumi.StrucnaSprema;
import enumi.TipSobeEnum;
import manager.*;
import entity.*;

public class RecepcionerTests {
	public static RecepcionerManager rm;
	public static GostManager gm = GostManager.getInstance();
	public static CenovnikManager cm = CenovnikManager.getInstance();
	public static SobaricaManager sm = SobaricaManager.getInstance();
	public static SobaManager sobam = SobaManager.getInstance();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak recepcioner testa");
		rm = RecepcionerManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj recepcioner testa");
	}
	
	@Test
	public void checkInProcesMetoda() {
		String poruka = rm.checkInProces("Lanmi", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testCehckIn@example.com");
		assertEquals("Gost je uspesno dodat u sistem", poruka);
		
		String poruka1 = rm.checkInProces("Lanmi", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testCehckIn@example.com");
		assertEquals("Gost je vec regsistrovan u sistemu", poruka1);
	}
	
	@Test
	public void checkInRezervacijaMetoda() {
		String poruka = rm.checkInProces("Lanmi", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testCehckInRez@example.com");
		assertEquals("Gost je uspesno dodat u sistem", poruka);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		
		String poruka1 = GostManager.getInstance().zahtevRezervacija(gm.gosti.get("testCehckInRez@example.com"), cm.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-01-01", "2025-01-03", usluge);
		assertEquals("Zahtev je poslat", poruka1);
		
		String poruka2 = rm.izmenaStatusaRezrvacije("testCehckInRez@example.com", "2025-01-01", "2025-01-03", StatusRezervacije.POTVRDJENA);
		assertEquals("Status je izmenjen", poruka2);
		
		
		String poruka3 = rm.checkInProcesRezervacija("testCehckInRez@example.com", LocalDate.parse("2025-01-01"), LocalDate.parse("2025-01-03"), SobaManager.sobe.get(155));
		assertEquals("Tipovi se ne poklapaju", poruka3);
		
		String poruka4 = rm.checkInProcesRezervacija("testCehckInRez@example.com", LocalDate.parse("2025-01-01"), LocalDate.parse("2025-01-03"), SobaManager.sobe.get(113));
		assertEquals("Uspesno izvrsen checkin", poruka4);
		
		checkOutMetoda();
	}
	
	@Test
	public void izmenaStatusaMetoda() {
		String poruka = rm.checkInProces("Lanmi", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testIzmenaStatusa@example.com");
		assertEquals("Gost je uspesno dodat u sistem", poruka);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		
		String poruka1 = GostManager.getInstance().zahtevRezervacija(gm.gosti.get("testIzmenaStatusa@example.com"), cm.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-02-01", "2025-02-03", usluge);
		assertEquals("Zahtev je poslat", poruka1);
		
		String poruka2 = rm.izmenaStatusaRezrvacije("testIzmenaStatusa@example.com", "2025-02-01", "2025-02-03", StatusRezervacije.POTVRDJENA);
		assertEquals("Status je izmenjen", poruka2);
		
		String poruka3 = GostManager.getInstance().zahtevRezervacija(gm.gosti.get("testIzmenaStatusa@example.com"), cm.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-02-01", "2025-02-03", usluge);
		assertEquals("Zahtev je poslat", poruka3);
		
		String poruka4 = rm.izmenaStatusaRezrvacije("testIzmenaStatusa@example.com", "2025-04-01", "2025-04-03", StatusRezervacije.POTVRDJENA);
		assertEquals("Trazena rezervacija ne postoji", poruka4);
		
		String poruka5 = rm.izmenaStatusaRezrvacije("testIzmenaStatusa@example.com", "2025-02-01", "2025-02-03", StatusRezervacije.POTVRDJENA);
		assertEquals("Nema slobodne sobe, rezervacja odbijena", poruka5);
	}
	
	@Test
	public void dodatnaUslugaMetoda() {
		String poruka = rm.checkInProces("Lanmi", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testDodatnaUsluga@example.com");
		assertEquals("Gost je uspesno dodat u sistem", poruka);
		
		ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
		
		String poruka1 = GostManager.getInstance().zahtevRezervacija(gm.gosti.get("testDodatnaUsluga@example.com"), cm.cenovnici.get(0).getTipoviSoba().get(TipSobeEnum.ODVOKREVETNA_DVA.toString()), 2, "2025-07-01", "2025-07-03", usluge);
		assertEquals("Zahtev je poslat", poruka1);
		
		String poruka2 = rm.dodajUsluguNaRezervaciju(cm.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "testDodatnaUsluga@example.com", "2025-07-01", "2025-07-03");
		assertEquals("Uspesno dodata usluga", poruka2);
		
		String poruka3 = rm.izbaciUsluguSaRezervacije ("testDodatnaUsluga@example.com", cm.cenovnici.get(0).getDodatneUsluge().get("Dorucak"), "2025-07-01", "2025-07-03");
		assertEquals("Uspesno izbacena usluga", poruka3);
	}
	
	
	private void checkOutMetoda() {
		String poruka = rm.checkOUTProces(sobam.sobe.get(113));
		assertEquals("Check out obavljen",poruka);
		
		String poruka1 = sm.sredjenaSoba(sobam.sobe.get(114), sm.sobarice.get("janaSobarica"));
		assertEquals("Soba nije markirana za sredjivanje",poruka1);
		
		String poruka2 = sm.sredjenaSoba(sobam.sobe.get(113), sm.sobarice.get("janaSobarica"));
		assertEquals("Soba je sredjena",poruka2);
	}
}
