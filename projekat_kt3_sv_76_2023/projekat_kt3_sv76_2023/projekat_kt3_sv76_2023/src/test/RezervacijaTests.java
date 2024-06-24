package test;
import java.util.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import enumi.*;
import manager.*;
import entity.*;

public class RezervacijaTests {
	public static RezervacijaManager rm;
	public static GostManager gm = GostManager.getInstance();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak rezervacija testa");
		rm = RezervacijaManager.getInstance(); 
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj rezervacija testa");
	}
	
	
	@Test
	public void naCekanjuRez() {
		ArrayList<Rezervacija> rez = rm.naCekanjuRezervacije();
		assertTrue(rez.size()>0);
		assertNotNull(rez);
	}
	
	@Test
	public void potvrdjeneRezervacije() {
		ArrayList<Rezervacija> rez = rm.potvrdjeneRezervacije();
		System.out.println(rez);
		assertNotNull(rez);
	}
	
	@Test
	public void pregledRezervacija() {
		ArrayList<Rezervacija> rez = rm.pregledRezervacija(gm.gosti.get("milica@example.com"));
		assertTrue(rez.size()>0);
		assertNotNull(rez);
	}
	
	@Test
	public void izbaciSobuRezervacije() {
		boolean izbacena = rm.izbaciSobuRezervacije("Mala soba");
		assertTrue(!izbacena);
	}
	
	@Test
	public void izbaciUsluguRezervacije() {
		boolean izbacena = rm.izbaciNepostojeceUsluge("Mala soba");
		assertTrue(!izbacena);
	}
}
