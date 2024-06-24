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
}
