package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import enumi.StrucnaSprema;
import enumi.TipSobeEnum;
import entity.*;
import manager.AdminManager;
import manager.CenovnikManager;
import manager.GostManager;
import manager.RecepcionerManager;
import manager.SobaManager;
import manager.SobaricaManager;

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
		
		String poruka3 = am.dodajZaposlenog("Sobarica", "Sara", "Sara", "Zensko","1986-11-23", "1123545455", "Dunavska 14", "saraSobarica1", "sifraNeka123", StrucnaSprema.SREDNJA, "10");
		assertEquals(poruka3, "Uspesno dodata sobarica");
		
		
		SobaricaManager.sobarice.remove("saraSobarica");
		//String poruka4 = am.otpustiZaposlenog("saraSobarica");
		//assertEquals("Otpusten", poruka4);
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
		RecepcionerManager.recepcioneri.put("testOtpusti", new Recepcioner("Nikola", "Nikolica", "Musko",LocalDate.parse("1986-11-23"), "1123545455", "Dunavska 14", "testOtpusti", "sifraNeka123", StrucnaSprema.VISOKA, 20,"Recepcioner"));
		//String poruka = am.dodajZaposlenog("Recepcioner", "Nikola", "Nikolica", "Musko","1986-11-23", "1123545455", "Dunavska 14", "testOtpusti", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		//assertEquals(poruka, "Uspesno dodat recepcioner");
		
		String poruka1 = am.otpustiZaposlenog("testOtpusti");
		assertEquals(poruka1, "Otpusten");
		
		String poruka2 = am.otpustiZaposlenog("nekiZaposleni");
		assertEquals(poruka2, "Trazeni zaposleni ne postoji");
		
	}
	
	@Test
	public void izmeniPodatkeZaposlenog() {
		RecepcionerManager.recepcioneri.put("izmenaPodatakaZaposlenog", new Recepcioner("Nikola", "Nikolica", "Musko",LocalDate.parse("1986-11-23"), "1123545455", "Dunavska 14", "izmenaPodatakaZaposlenog", "sifraNeka123", StrucnaSprema.VISOKA, 20,"Recepcioner"));
		//String poruka = am.dodajZaposlenog("Recepcioner", "Nikola", "Nikolica", "Musko","1986-11-23", "1123545455", "Dunavska 14", "izmenaPodatakaZaposlenog", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		//assertEquals(poruka, "Uspesno dodat recepcioner");
		
		String poruka1 = am.izmeniPodatkeZaposlenog("izmenaPodatakaZaposlenog", "izmenaPodatakaZaposlenogggg", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "sifraNeka123", StrucnaSprema.VISOKA.toString(), "15", "Recepcioner");
		assertEquals("Uspesno izmenjeni podaci", poruka1);
		
		
		RecepcionerManager.recepcioneri.put("testOtpusti", new Recepcioner("Nikola", "Nikolica", "Musko",LocalDate.parse("1986-11-23"), "1123545455", "Dunavska 14", "izmenaPodatakaZaposlenogggg", "sifraNeka123", StrucnaSprema.VISOKA, 20,"Recepcioner"));
		//String poruka2 = am.dodajZaposlenog("Recepcioner", "Nikola", "Nikolica", "Musko","1986-11-23", "1123545455", "Dunavska 14", "izmenaPodatakaZaposlenogggg", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		//assertEquals("Postoji recepcioner sa ovim korisnickim imenom", poruka2);
		
		String poruka3 = am.izmeniPodatkeZaposlenog("izmenaPodatakaZaposlenogggg", "peraAdmin", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "sifraNeka123", StrucnaSprema.VISOKA.toString(), "15", "Recepcioner");
		assertEquals("Ovo korisnicko ime je vec dodeljeno drugom zaposlenom", poruka3);
		
		
		String poruka4 = am.izmeniPodatkeZaposlenog("izmenaPodatakaZaposlenog121", "novo", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "sifraNeka123", StrucnaSprema.VISOKA.toString(), "15", "Recepcioner");
		assertEquals("Ne postoji recepcioner", poruka4);
	}
	
	@Test
	public void izmeniPodatkeGostaMetoda() {
		GostManager.gosti.put("testIzmena@example.com", new Gost("Milan", "Stankovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "testIzmena@example.com", "8979723"));
		//String poruka = am.dodajGosta("Milan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testIzmena@example.com");
		//assertTrue(poruka.equals("Uspesno dodat gost"));
		
		String poruka1 = am.izmeniPodatkeGosta("testIzmena@example.com", "testIzmenaaaa@example.com", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "8979723");
		assertEquals("Uspesno izmenjeni podaci", poruka1);
		
		GostManager.gosti.put("testIzmena1@example.com", new Gost("Milan", "Stankovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "testIzmena1@example.com", "8979723"));
		//String poruka2 = am.dodajGosta("Jovan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "testIzmena1@example.com");
		//assertEquals("Uspesno dodat gost", poruka2);
		
		String poruka3 = am.izmeniPodatkeGosta("testIzmenaaaa@example.com", "testIzmena1@example.com", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "8979723");
		assertEquals("Ovo korisnicko ime je vec registrovano sa drugim gostom", poruka3);
		
		
		String poruka4 = am.izmeniPodatkeGosta("lkaiii@example.com", "lazaar@example.com", "Miki", "Stankovic", "Musko", "1999-01-01", "1122112",  "Cara Lazara 21", "8979723");
		assertEquals("Gost ne postoji", poruka4);
	}
	
	
	@Test
	public void izbrisiGosta() {
		GostManager.gosti.put("izbrisiGosta", new Gost("Milan", "Stankovic", "Musko", LocalDate.parse("1999-01-01"), "1122112", "Cara Lazara 21", "izbrisiGosta", "8979723"));

		//String poruka = am.dodajGosta("Milan", "Stankovic", "Musko", "1999-01-01", "1122112", "Cara Lazara 21", "8979723", "izbrisiGosta");
		//assertEquals("Uspesno dodat gost", poruka);
		
		String poruka1 = am.obrisiGosta("fdsgfdgfhkfgfsdkjgfsdj");
		assertEquals("Gost ne postoji", poruka1);
		
		String poruka2 = am.obrisiGosta("izbrisiGosta");
		assertEquals("Gost je izbrisan iz sistema", poruka2);
	}
	
	@Test
	public void definisanjeCenovnikaMetoda() {
		String poruka = am.definisanjeCenovnika("2025-01-01", "2025-05-05", false);
		assertEquals("Uspesno dodat novi cenovnik", poruka);
		
		String poruka1 = am.definisanjeCenovnika("2024-01-01", "2024-05-05", false);
		assertEquals("Cenovnik se moze definisati samo sa naredne datume", poruka1);
	}
	
	@Test 
	public void obrisiCenovnik(){
		CenovnikManager.cenovnici.add(new Cenovnik("2029-01-01", "2029-05-05", false));
		//String poruka = am.definisanjeCenovnika("2029-01-01", "2029-05-05", false);
		//assertEquals("Uspesno dodat novi cenovnik", poruka);
		
		String poruka1 = am.obrisiCenovnik("2020-01-01", "2020-05-05");
		assertEquals("Cenovnik nije pronadjen", poruka1);
		
		String poruka2 = am.obrisiCenovnik("2029-01-01", "2029-05-05");
		assertEquals("Obrisan", poruka2);
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
	
	@Test 
	public void izmeniSobuMetoda() {
		SobaManager.sobe.put(166, new Soba(166, TipSobeEnum.JEDNOKREVETNA.toString()));
		//String poruka = am.dodajSobu("166", TipSobeEnum.JEDNOKREVETNA);
		//assertEquals("Uspesno dodata soba", poruka);
		
		String poruka1 = am.izmeniSobu("199", "177", TipSobeEnum.JEDNOKREVETNA.toString());
		assertEquals("Sifra sobe ne postoji", poruka1);
		
		String poruka2 = am.izmeniSobu("166", "112", TipSobeEnum.JEDNOKREVETNA.toString());
		assertEquals("Nova sifra sobe je vec registrovana u sistemu", poruka2);
		
		String poruka3 = am.izmeniSobu("166", "177", TipSobeEnum.JEDNOKREVETNA.toString());
		assertEquals("Podaci uspesno izmenjeni", poruka3);
		
		String poruka4 = am.dodajDodatnoSoba(SobaManager.sobe.get(177), "slon");
		assertEquals("Uspesno dodat dodatak", poruka4);
		
		String poruka5 = am.izbaciDodatnoSoba(SobaManager.sobe.get(177), "slon");
		assertEquals("Uspesno izbacen dodatak", poruka5);
	}
	
	@Test 
	public void izbrisiSobu() {
		SobaManager.sobe.put(205, new Soba(205, TipSobeEnum.JEDNOKREVETNA.toString()));
		//String poruka = am.dodajSobu("205", TipSobeEnum.JEDNOKREVETNA);
		//assertEquals("Uspesno dodata soba", poruka);
	
		String poruka1 = am.izbrisiSobu("452454545");
		assertEquals("Ova soba ne postoji", poruka1);
		
		String poruka2 = am.izbrisiSobu("205");
		assertEquals("Soba uspesno izbrisana", poruka2);
	}

}