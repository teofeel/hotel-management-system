package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.*;
import enumi.*;

import manager.*;

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
		
	}
	
	@Test
	public void sredjenSobe() {
		
	}
	
	@Test
	public void potvrdjeneRezervacije() {
		
	}
	
	@Test
	public void obradjeneRezervacije() {
		
	}
	
	@Test
	public void sobaNocenja() {
		
	}
	
	@Test
	public void sobaPrihodi() {
		
	}
	
	@Test
	public void prihodiZadnjaGod() {
		
	}
	
	@Test
	public void opterecenostSobarica() {
		
	}
	
	@Test
	public void statusRezervacije() {
		
	}
}
