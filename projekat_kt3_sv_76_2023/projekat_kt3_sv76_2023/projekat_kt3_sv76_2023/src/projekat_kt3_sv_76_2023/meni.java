package projekat_kt3_sv_76_2023;
import java.io.*;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.time.LocalDate;
import java.util.*;


import enumi.*;
import controller.*;
import entity.*;
import views.*;
import manager.*;
import test.*;
public class meni {
	public static void main(String[] args) {
		AdminManager.getInstance().dodajZaposlenog("Administrator","Petar","Peric", "Musko", "1973-10-15","34387438", "Neka Adresa 22", "peraAdmin","lozinka1",StrucnaSprema.VISOKA, "10");
		AdminManager.getInstance().dodajZaposlenog("Recepcioner", "Mika", "Mikic", "Musko","1986-11-23", "1123545455", "Dunavska 14", "mikaRecepcioner", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		AdminManager.getInstance().dodajZaposlenog("Recepcioner", "Nikola", "Nikolic", "Musko","1986-11-23", "1123545455", "Dunavska 14", "nikolaRecepcioner", "sifraNekaL123", StrucnaSprema.SREDNJA, "20");
		AdminManager.getInstance().dodajZaposlenog("Sobarica", "Jana", "Janic", "Zensko","1986-11-23", "1123545455", "Dunavska 14", "janaSobarica", "sifraJana123", StrucnaSprema.OSNOVNA, "20");
	
		AdminManager.getInstance().otpustiZaposlenog("nikolaRecepcioner");
		
		
		RecepcionerManager.getInstance().checkInProces("Milica", "Milic", "Zensko", "1999-01-01", "123123123", "Neka adresa 13", "1", "milica@example.com");
		RecepcionerManager.getInstance().checkInProces("Ana", "Anic", "Zensko", "1999-01-01", "123123123", "Neka adresa 13", "1", "ana@example.com");

		CenovnikManager.cenovnici.add(new Cenovnik("2024-01-01", "2024-12-31", true));
		CenovnikManager.getInstance().dodajNovuSobu(new TipSobe(TipSobeEnum.JEDNOKREVETNA.toString(),50));
		CenovnikManager.getInstance().dodajNovuSobu(new TipSobe(TipSobeEnum.DVOKREVETNA.toString(),80));
		CenovnikManager.getInstance().dodajNovuSobu(new TipSobe(TipSobeEnum.ODVOKREVETNA_DVA.toString(),80));
		CenovnikManager.getInstance().dodajNovuSobu(new TipSobe(TipSobeEnum.TROKREVETNA.toString(),100));
		
		HashMap<String, TipSobe> tipoviSoba = CenovnikManager.cenovnici.get(0).getTipoviSoba();
		
		int sifraSobe = 111;
		for(TipSobe ts : tipoviSoba.values() ) {//DodatnoManager.tipoviSoba.values()
			ArrayList<String> dodaciSobe = new ArrayList<String>();
			dodaciSobe.add("Standardno");
			SobaManager.sobe.put(sifraSobe, new Soba(sifraSobe, ts, StatusSobe.SLOBODNA, dodaciSobe));
			sifraSobe++;
		}
		
		SobaManager.sobe.get(111).setAmenities("slon");
		
		CenovnikManager.getInstance().dodajNovuUslugu("Dorucak","8");
		CenovnikManager.getInstance().dodajNovuUslugu("Rucak", "10");
		CenovnikManager.getInstance().dodajNovuUslugu("Vecera", "12");
		CenovnikManager.getInstance().dodajNovuUslugu("Bazen", "10");
		CenovnikManager.getInstance().dodajNovuUslugu("Spa centar", "10");

		
		HashMap<String, DodatneUsluge> dodatneUsluge = CenovnikManager.cenovnici.get(0).getDodatneUsluge();
		
		dodatneUsluge.remove("Spa centar");
		
		
		ArrayList<DodatneUsluge> milicaUsluge = new ArrayList<DodatneUsluge>();
		milicaUsluge.add(dodatneUsluge.get("Dorucak"));
		milicaUsluge.add(dodatneUsluge.get("Vecera"));
		
		
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Jednokrevetna (1)"),1,"2024-08-13","2024-08-23", milicaUsluge);

		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("ana@example.com"), tipoviSoba.get("Dvokrevetna (1+1)"),1,"2024-06-06","2024-06-12", milicaUsluge);
		
		
		
		////////////////////////
		/* KT 3 TEST SCENARIO */
		////////////////////////
		System.out.println();
		System.out.println("KT3 TEST SCENARIO");
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-05-27", "2024-06-03", milicaUsluge);

		System.out.println(RecepcionerManager.getInstance().izmenaStatusaRezrvacije("milica@example.com", "2024-05-27", "2024-06-03", StatusRezervacije.POTVRDJENA));;
		
		System.out.println(RecepcionerManager.getInstance().checkInProcesRezervacija(GostManager.gosti.get("milica@example.com").getKorisnickoIme(),
							LocalDate.parse("2024-05-27"), LocalDate.parse("2024-06-03"), SobaManager.sobe.get(112)));
		
		RecepcionerManager.getInstance().dodajUsluguNaRezervaciju(CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Bazen"), "milica@example.com", "2024-05-27", "2024-06-03");
		RecepcionerManager.getInstance().checkOUTProces(SobaManager.sobe.get(112));
		System.out.println(SobaricaManager.sobarice.get("janaSobarica").getDodeljeneSobe());
		SobaricaManager.getInstance().sredjenaSoba(SobaManager.sobe.get(112), SobaricaManager.sobarice.get("janaSobarica"));
		System.out.println(SobaricaManager.sobarice.get("janaSobarica").getDodeljeneSobe());
		
		System.out.println("Ana rezervacije");
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("ana@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-05-29", "2024-06-05", null);
		RecepcionerManager.getInstance().izmenaStatusaRezrvacije("ana@example.com", "2024-05-29", "2024-06-05", StatusRezervacije.POTVRDJENA);
		//GostViews.pregledRezervacija(GostManager.gosti.get("ana@example.com"));
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("ana@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-06-10", "2024-06-15", null);
		GostManager.getInstance().otkaziRezervaciju(GostManager.gosti.get("ana@example.com"), "2024-06-10", "2024-06-15");
		
		
		RezervacijaManager.getInstance().pregledRezervacija(GostManager.gosti.get("ana@example.com"));
		
		
		
		AdminManager.getInstance().definisanjeCenovnika("2025-01-01", "2025-12-31",  true);
		
		FileManager.getInstance().upisiPodatke();
		
		/*Result result = JUnitCore.runClasses(AdminTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		
		result = JUnitCore.runClasses(RecepcionerTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		result = JUnitCore.runClasses(SobaricaTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		result = JUnitCore.runClasses(GostTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		result = JUnitCore.runClasses(CenovnikTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		result = JUnitCore.runClasses(RezervacijaTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		result = JUnitCore.runClasses(SobaTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());
		
		
		result = JUnitCore.runClasses(FileTest.class);
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
		System.out.println(result.wasSuccessful());*/
		
		
		LoginView loginWindow = new LoginView();
		
		
		
	}
	
}
