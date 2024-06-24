package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Soba;
import enumi.TipSobeEnum;
import manager.GostManager;
import manager.RezervacijaManager;
import manager.SobaManager;

public class SobaTest {
	public static SobaManager sm;
	public static GostManager gm = GostManager.getInstance();
	public static RezervacijaManager rm = RezervacijaManager.getInstance();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak soba testa");
		sm = SobaManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj soba testa");
	}
	
	@Test
	public void slobodneSobeCheckIn() {
		ArrayList<Soba> sobe = sm.slobodneSobeCheckIn(TipSobeEnum.DVOKREVETNA.toString());
		assertNotNull(sobe);
		assertTrue(sobe.size()>0);
		
		ArrayList<Soba> sobe1 = sm.slobodneSobeCheckIn(TipSobeEnum.TROKREVETNA.toString());
		assertNotNull(sobe1);
		assertTrue(sobe1.size()>0);
	}
	
	@Test
	public void slobodneSobe() {
		ArrayList<Soba> sobe = sm.slobodneSobe(rm.pregledRezervacija(gm.gosti.get("milica@example.com")).get(0));
		assertTrue(sobe.size()>0);
		
		ArrayList<Soba> sobe1 = sm.slobodneSobe(rm.pregledRezervacija(gm.gosti.get("zahtevRezerevacijeTest@example.com")).get(0));
		assertTrue(sobe1.size()>0);
	}
	
	@Test
	public void zauzeteSobe() {
		ArrayList<Soba> sobe = sm.zauzeteSobe();
		assertTrue(sobe.size()==0);
	}
	
	@Test
	public void pregledSlobodnihTipova() {
		ArrayList<String> sobe = sm.pregledSlobodnihTipovaSoba(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-09-10"));
		assertTrue(sobe.size()==0);
		
		ArrayList<String> sobe1 = sm.pregledSlobodnihTipovaSoba(LocalDate.parse("2024-09-10"), LocalDate.parse("2024-09-15"));
		assertTrue(sobe1.size()>0);
	}
	
	@Test
	public void pregledSlobodnihSoba() {
		boolean test = sm.pregledSlobodnihSoba(LocalDate.parse("2024-09-10"), LocalDate.parse("2024-09-15"), TipSobeEnum.DVOKREVETNA.toString());
		boolean test1 = sm.pregledSlobodnihSoba(LocalDate.parse("2024-09-10"), LocalDate.parse("2024-09-15"), TipSobeEnum.TROKREVETNA.toString());
		boolean test2 = sm.pregledSlobodnihSoba(LocalDate.parse("2024-09-10"), LocalDate.parse("2024-09-15"), TipSobeEnum.ODVOKREVETNA_DVA.toString());
		boolean test3 = sm.pregledSlobodnihSoba(LocalDate.parse("2024-09-10"), LocalDate.parse("2024-09-15"), TipSobeEnum.JEDNOKREVETNA.toString());
	
		
		assertTrue(test && test1 && test2 && test3);
	}

}
