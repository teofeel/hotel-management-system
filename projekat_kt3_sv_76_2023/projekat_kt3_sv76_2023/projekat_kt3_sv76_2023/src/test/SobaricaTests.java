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

public class SobaricaTests {
	public static SobaricaManager sm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak sobarica testa");
		sm = SobaricaManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj sobarica testa");
	}
	
	@Test
	public void dodeliSobuMetoda() {
		Soba s = new Soba(114, TipSobeEnum.TROKREVETNA.toString());
		
		String poruka = sm.dodeliSobu(s);
		assertEquals("Soba nije markirana za sredjivanje", poruka);
		
		s.setStatus(StatusSobe.SPREMANJE);
		String poruka1 = sm.dodeliSobu(s);
		assertEquals("Soba dodeljena", poruka1);
		
	}
	
	
	@Test
	public void izbaciSobuMetoda() {
		Sobarica sobarica = new Sobarica("Sara", "Sara", "Zensko",LocalDate.parse("1986-11-23"), "1123545455", "Dunavska 14", "saraSobarica", "sifraNeka123", StrucnaSprema.SREDNJA, 10, "Sobarica");
		Soba s = new Soba(190909, TipSobeEnum.TROKREVETNA.toString());
		
		sm.sobarice.put("saraSobarica", sobarica);
		
		sobarica.addSoba(s);
		
		Boolean b = sm.izbaciSobuSobaricama(190909);
		assertTrue(b);
		
		sm.sobarice.remove("saraSobarica");
	}
	
	@Test
	public void sredjenaSobaMetoda() {
		Sobarica sobarica = new Sobarica("Sara", "Sara", "Zensko",LocalDate.parse("1986-11-23"), "1123545455", "Dunavska 14", "saraSobarica", "sifraNeka123", StrucnaSprema.SREDNJA, 10, "Sobarica");
		Soba s = new Soba(190909, TipSobeEnum.TROKREVETNA.toString());
		
		String poruka = sm.sredjenaSoba(s, sobarica);
		assertEquals("Soba nije markirana za sredjivanje", poruka);
		
		s.setStatus(StatusSobe.SPREMANJE);
		
		String poruka1 = sm.sredjenaSoba(s, sobarica);
		assertEquals("Soba je sredjena", poruka1);
	}
}
