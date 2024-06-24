package test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import enumi.StrucnaSprema;
import enumi.TipSobeEnum;
import manager.AdminManager;

public class AdminTest {

	public static AdminManager am;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		System.out.println("Pocetak admin testa");
		am = AdminManager.getInstance(); 
		am.dodajZaposlenog("Administrator","Petar","Peric", "Musko", "1973-10-15","34387438", "Neka Adresa 22", "peraAdmin","lozinka1",StrucnaSprema.VISOKA, "10");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		System.out.println("Kraj admin testa");
	}
	
	@Test
	public void dodajZaposlenogMetoda() {
		String poruka1 = am.dodajZaposlenog("Recepcioner", "Nikola", "Nikolica", "Musko","1986-11-23", "1123545455", "Dunavska 14", "nidzaRecepcioner", "sifraNeka123", StrucnaSprema.VISOKA, "20");
	
		assertEquals(poruka1, "Uspesno dodat recepcioner");
		assertTrue(!poruka1.equals("Uspesno dodata sobarica"));
		
		String poruka2 = am.dodajZaposlenog("Recepcioner", "Nikola", "Nikolica", "Musko","1986-11-23", "1123545455", "Dunavska 14", "nidzaRecepcioner", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		assertEquals(poruka2, "Postoji recepcioner sa ovim korisnickim imenom");
		
		String poruka3 = am.dodajZaposlenog("Sobarica", "Sara", "Sara", "Zensko","1986-11-23", "1123545455", "Dunavska 14", "saraSobarica", "sifraNeka123", StrucnaSprema.SREDNJA, "10");
		assertEquals(poruka3, "Uspesno dodata sobarica");
		
		String poruka4 = am.otpustiZaposlenog("saraSobarica");
		assertEquals("Otpusten", poruka4);
	}
	
	@Test
	public void dodajGostaMetoda() {
		String poruka = am.dodajGosta("Milan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "milan@example.com");
		assertEquals("Uspesno dodat gost", poruka);
		
		String poruka1 = am.dodajGosta("Milan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "milan@example.com");
		assertEquals("Gost je vec regsistrovan u sistemu", poruka1);
		
		String poruka2 = am.dodajGosta("Milan", "", "Musko", "1999-01-01", "", "Cara Lazara 21", "8979723", "lanmi@example.com");
		assertEquals("Sva polja moraju biti popunjena", poruka2);
		
	}
	
	@Test
	public void otpustiZaposlenogMetoda() {
		String poruka = am.dodajZaposlenog("Recepcioner", "Nikola", "Nikolica", "Musko","1986-11-23", "1123545455", "Dunavska 14", "testOtpusti", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		assertEquals(poruka, "Uspesno dodat recepcioner");
		
		String poruka1 = am.otpustiZaposlenog("testOtpusti");
		assertEquals(poruka1, "Otpusten");
		
		String poruka2 = am.otpustiZaposlenog("nekiZaposleni");
		assertEquals(poruka2, "Trazeni zaposleni ne postoji");
		
	}
	
	@Test
	public void izmeniPodatkeGostaMetoda() {
		String poruka = am.dodajGosta("Milan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testIzmena@example.com");
		assertTrue(poruka.equals("Uspesno dodat gost"));
		
		String poruka1 = am.izmeniPodatkeGosta("testIzmena@example.com", "testIzmenaaaa@example.com", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "8979723");
		assertEquals("Uspesno izmenjeni podaci", poruka1);
		
		String poruka2 = am.dodajGosta("Jovan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testIzmena1@example.com");
		assertEquals("Uspesno dodat gost", poruka2);
		
		String poruka3 = am.izmeniPodatkeGosta("testIzmenaaaa@example.com", "testIzmena1@example.com", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "8979723");
		assertEquals("Ovo korisnicko ime je vec registrovano sa drugim gostom", poruka3);
		
		
		String poruka4 = am.izmeniPodatkeGosta("lkaiii@example.com", "lazaar@example.com", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "8979723");
		assertEquals("Gost ne postoji", poruka4);
	}
	
	@Test
	public void definisanjeCenovnikaMetoda() {
		String poruka = am.definisanjeCenovnika("2025-01-01", "2025-05-05", false);
		assertEquals("Uspesno dodat novi cenovnik", poruka);
		
		String poruka1 = am.definisanjeCenovnika("2024-01-01", "2024-05-05", false);
		assertEquals("Cenovnik se moze definisati samo sa naredne datume", poruka1);
	}
	
	@Test
	public void dodajSobuMetoda() {
		String poruka = am.dodajSobu("155", TipSobeEnum.JEDNOKREVETNA);
		assertEquals("Uspesno dodata soba", poruka);
		
		String poruka1 = am.dodajSobu("155", TipSobeEnum.JEDNOKREVETNA);
		assertEquals("Soba je vec registrovana u sistemu", poruka1);
		
		String poruka2 = am.dodajSobu("", TipSobeEnum.JEDNOKREVETNA);
		assertEquals("Sva polja moraju biti popunjena", poruka2);
	}

}
