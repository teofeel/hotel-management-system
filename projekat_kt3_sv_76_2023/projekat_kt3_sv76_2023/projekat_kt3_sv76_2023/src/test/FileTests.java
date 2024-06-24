package test;

import org.junit.*;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;
import static org.junit.Assert.*;

import manager.*;
import entity.*;
import enumi.*;

public class FileTests {
	private static FileManager fileManager;
    private static File testDataDir;

    @BeforeClass
    public static void setUp() throws Exception{
        fileManager = FileManager.getInstance();
        testDataDir = new File("../data");
        if (!testDataDir.exists()) {
            testDataDir.mkdir();
        }
        // Setup initial data
        createDummyData();
    }

    @AfterClass
    public static void tearDown() throws Exception{
        deleteDir(testDataDir);
        fileManager.ocistiPodatkeAplikacija();
    }

    private static void createDummyData() {
        // Create dummy data for testing
    	Administrator admin = new Administrator("Petar","Peric", "Musko", LocalDate.parse("1973-10-15"),"34387438", "Neka Adresa 22", "peraTestAdmin","lozinka1",StrucnaSprema.VISOKA, 10,"Administrator");
        AdminManager.admini.put("peraTestAdmin",admin);

        Cenovnik cenovnik = new Cenovnik("2023-01-01", "2023-12-31", true);
        
        TipSobe tipSobe = new TipSobe("Standard", 100.0);
        
        DodatneUsluge dodatnaUsluga = new DodatneUsluge("WiFi", 10.0);
        
        AdminManager.getInstance().definisanjeCenovnika("2023-01-01", "2023-12-31", true);
        
        CenovnikManager.getInstance().dodajNovuSobu(tipSobe);
        CenovnikManager.getInstance().dodajNovuUslugu("WiFi","10");

        Gost gost = new Gost("John", "Doe", "M", LocalDate.of(1990, 1, 1), "123456789", "123 Street", "johndoe", "password");
        GostManager.gosti.put(gost.getKorisnickoIme(), gost);

        Recepcioner recepcioner = new Recepcioner("Petar","Peric", "Musko", LocalDate.parse("1973-10-15"),"34387438", "Neka Adresa 22", "peraTestRecepcioner","lozinka1",StrucnaSprema.VISOKA, 10,"Recepcioner");
        RecepcionerManager.recepcioneri.put("peraTestRecepcioner", recepcioner);

        Sobarica sobarica = new Sobarica("Petar","Peric", "Musko", LocalDate.parse("1973-10-15"),"34387438", "Neka Adresa 22", "peraTestSobarica","lozinka1",StrucnaSprema.VISOKA, 10,"Sobarica");
        SobaricaManager.sobarice.put("peraTestSobarica",sobarica);

        Soba soba = new Soba(101, tipSobe, StatusSobe.SLOBODNA, new ArrayList<>(Arrays.asList("TV", "AC")));
        SobaManager.sobe.put(soba.getSifra(), soba);

        ArrayList<DodatneUsluge> usluge = new ArrayList<>();
        usluge.add(dodatnaUsluga);
        Rezervacija rezervacija = new Rezervacija(gost.getKorisnickoIme(), StatusRezervacije.NA_CEKANJU, tipSobe, soba, 2, LocalDate.now(), LocalDate.now().plusDays(3), usluge);
        RezervacijaManager.rezervacije.add(rezervacija);
        
        fileManager.upisiPodatke();
    }

    private static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDir(child);
                }
            }
        }
        dir.delete();
    }

    @Test
    public void testUpisiPodatke() {
    	//createDummyData();
    	System.out.println("obde");
        fileManager.upisiPodatke();
        File rezervacijeFile = new File("../data/rezervacije.csv");
        assertTrue(rezervacijeFile.exists());
        File sobeFile = new File("../data/sobe.csv");
        assertTrue(sobeFile.exists());
        File gostiFile = new File("../data/gosti.csv");
        assertTrue(gostiFile.exists());
        File zaposleniFile = new File("../data/zaposleni.csv");
        assertTrue(zaposleniFile.exists());
    }

   @Test
    public void testUcitajPodatke() {
        fileManager.upisiPodatke();
        fileManager.ocistiPodatkeAplikacija();
        boolean result = fileManager.ucitajPodatke();
       	assertTrue(result);

       	
       	assertFalse(RezervacijaManager.rezervacije.isEmpty());
       	assertFalse(SobaManager.sobe.isEmpty());
       	assertFalse(GostManager.gosti.isEmpty());
       	assertFalse(CenovnikManager.cenovnici.isEmpty());
    }

    @Test
    public void testOcistiFolder() {
        File dummyFile = new File("../data/dummy.txt");
        try {
            FileWriter writer = new FileWriter(dummyFile);
            writer.write("test");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(dummyFile.exists());
        fileManager.ocistiFolder();
        assertFalse(dummyFile.exists());
    }

    @Test
    public void testOcistiPodatkeAplikacija() {
        assertFalse(AdminManager.admini.isEmpty());
        assertFalse(CenovnikManager.cenovnici.isEmpty());
        assertFalse(RezervacijaManager.rezervacije.isEmpty());
        assertFalse(SobaManager.sobe.isEmpty());
        assertFalse(GostManager.gosti.isEmpty());
        assertFalse(RecepcionerManager.recepcioneri.isEmpty());
        assertFalse(SobaricaManager.sobarice.isEmpty());

        fileManager.ocistiPodatkeAplikacija();
        
        assertTrue(AdminManager.admini.isEmpty());
        assertTrue(CenovnikManager.cenovnici.isEmpty());
        assertTrue(RezervacijaManager.rezervacije.isEmpty());
        assertTrue(SobaManager.sobe.isEmpty());
        assertTrue(GostManager.gosti.isEmpty());
        assertTrue(RecepcionerManager.recepcioneri.isEmpty());
        assertTrue(SobaricaManager.sobarice.isEmpty());
    }
}
