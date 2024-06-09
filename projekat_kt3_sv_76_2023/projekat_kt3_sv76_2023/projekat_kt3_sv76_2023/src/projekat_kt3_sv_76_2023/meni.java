package projekat_kt3_sv_76_2023;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

import enumi.*;
import controller.*;
import entity.*;
import views.*;
import manager.*;
public class meni {
	public static void main(String[] args) {
		
		/*FileManager.getInstance().ucitajCenovnike();
		FileManager.getInstance().ucitajSobe();
		FileManager.getInstance().ucitajRezervacije();
		FileManager.getInstance().ucitajGoste();
		FileManager.getInstance().ucitajZaposlene();
		
		HashMap<String, DodatneUsluge> dodatneUsluge = CenovnikManager.cenovnici.get(0).getDodatneUsluge();
		HashMap<String, TipSobe> tipoviSoba = CenovnikManager.cenovnici.get(0).getTipoviSoba();
		ArrayList<DodatneUsluge> milicaUsluge = new ArrayList<DodatneUsluge>();
		milicaUsluge.add(dodatneUsluge.get("Dorucak"));
		milicaUsluge.add(dodatneUsluge.get("Vecera"));*/
		
		AdminManager.getInstance().dodajZaposlenog("Administrator","Petar","Peric", "Musko", "1973-10-15","34387438", "Neka Adresa 22", "peraAdmin","lozinka1",StrucnaSprema.VISOKA, "10");
		AdminManager.getInstance().dodajZaposlenog("Recepcioner", "Mika", "Mikic", "Musko","1986-11-23", "1123545455", "Dunavska 14", "mikaRecepcioner", "sifraNeka123", StrucnaSprema.VISOKA, "20");
		AdminManager.getInstance().dodajZaposlenog("Recepcioner", "Nikola", "Nikolic", "Musko","1986-11-23", "1123545455", "Dunavska 14", "nikolaRecepcioner", "sifraNekaL123", StrucnaSprema.SREDNJA, "20");
		AdminManager.getInstance().dodajZaposlenog("Sobarica", "Jana", "Janic", "Zensko","1986-11-23", "1123545455", "Dunavska 14", "janaSobarica", "sifraJana123", StrucnaSprema.OSNOVNA, "20");
	
		AdminManager.getInstance().pregledPodatakaZaposlenih();
		AdminManager.getInstance().otpustiZaposlenog("nikolaRecepcioner");
		AdminManager.getInstance().pregledPodatakaZaposlenih();
		
		
		RecepcionerManager.getInstance().checkInProces("Milica", "Milic", "Zensko", "1999-01-01", "123123123", "Neka adresa 13", "64324732", "milica@example.com");
		RecepcionerManager.getInstance().checkInProces("Ana", "Anic", "Zensko", "1999-01-01", "123123123", "Neka adresa 13", "64324732", "ana@example.com");

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
		
		System.out.println(SobaManager.sobe.get(113).toString());
		System.out.println(SobaManager.sobe.get(113).toString());
		
		
		CenovnikManager.getInstance().dodajNovuUslugu("Dorucak","8");
		CenovnikManager.getInstance().dodajNovuUslugu("Rucak", "10");
		CenovnikManager.getInstance().dodajNovuUslugu("Vecera", "12");
		CenovnikManager.getInstance().dodajNovuUslugu("Bazen", "10");
		CenovnikManager.getInstance().dodajNovuUslugu("Spa centar", "10");

		
		HashMap<String, DodatneUsluge> dodatneUsluge = CenovnikManager.cenovnici.get(0).getDodatneUsluge();
		System.out.println(dodatneUsluge.toString());
		
		dodatneUsluge.remove("Spa centar");
		
		System.out.println(dodatneUsluge.toString());
		
		
		ArrayList<DodatneUsluge> milicaUsluge = new ArrayList<DodatneUsluge>();
		milicaUsluge.add(dodatneUsluge.get("Dorucak"));
		milicaUsluge.add(dodatneUsluge.get("Vecera"));
		
		
		DodatnoViews.pregledSlobodnihTipovaSoba("2024-08-01", "2024-08-31", CenovnikManager.cenovnici.get(0));
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Jednokrevetna (1)"),1,"2024-08-13","2024-08-23", milicaUsluge);

		
		
		DodatnoViews.pregledSlobodnihTipovaSoba("2024-06-01","2024-06-30", CenovnikManager.cenovnici.get(0));
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("ana@example.com"), tipoviSoba.get("Dvokrevetna (1+1)"),1,"2024-06-06","2024-06-12", milicaUsluge);
		
		
		
		////////////////////////
		/* KT 3 TEST SCENARIO */
		////////////////////////
		System.out.println();
		System.out.println("KT3 TEST SCENARIO");
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-05-27", "2024-06-03", milicaUsluge);
		//GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-05-29", "2024-06-03", milicaUsluge);
		//GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-05-28", "2024-06-05", milicaUsluge);
		
		System.out.println(RecepcionerManager.getInstance().izmenaStatusaRezrvacije("milica@example.com", "2024-05-27", "2024-06-03", StatusRezervacije.POTVRDJENA));;
		
		System.out.println(RecepcionerManager.getInstance().checkInProcesRezervacija(GostManager.gosti.get("milica@example.com").getKorisnickoIme(),
							LocalDate.parse("2024-05-27"), LocalDate.parse("2024-06-03"), SobaManager.sobe.get(113)));
		
		RecepcionerManager.getInstance().dodajUsluguNaRezervaciju(CenovnikManager.cenovnici.get(0).getDodatneUsluge().get("Bazen"), "milica@example.com", "2024-05-27", "2024-06-03");
		RecepcionerManager.getInstance().checkOUTProces(SobaManager.sobe.get(113));
		System.out.println(SobaricaManager.sobarice.get("janaSobarica").getDodeljeneSobe());
		SobaricaManager.getInstance().sredjenaSoba(SobaManager.sobe.get(113), SobaricaManager.sobarice.get("janaSobarica"));
		System.out.println(SobaricaManager.sobarice.get("janaSobarica").getDodeljeneSobe());
		
		System.out.println("Ana rezervacije");
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("ana@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-05-29", "2024-06-05", null);
		RecepcionerManager.getInstance().izmenaStatusaRezrvacije("ana@example.com", "2024-05-29", "2024-06-05", StatusRezervacije.POTVRDJENA);
		//GostViews.pregledRezervacija(GostManager.gosti.get("ana@example.com"));
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("ana@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-06-10", "2024-06-15", null);
		GostManager.getInstance().otkaziRezervaciju(GostManager.gosti.get("ana@example.com"), "2024-06-10", "2024-06-15");
		
		
		//DodatnoManager.getInstance().odbijIstekleRezervacije();
		RezervacijaManager.getInstance().pregledRezervacija(GostManager.gosti.get("ana@example.com"));
		
		
		//System.out.println("Milicne rezervacije");
		//GostViews.pregledRezervacija(GostManager.gosti.get("milica@example.com"));
		//System.out.println(DodatnoManager.sobe);
		
		
		AdminManager.getInstance().definisanjeCenovnika("2025-01-01", "2025-12-31",  true);
		
		FileManager.getInstance().upisiPodatke();
		
		LoginView loginWindow = new LoginView();
		//new LoginController(loginWindow);
	
		
		/*GostViews.pregledRezervacija(GostManager.gosti.get("milica@example.com"));
		
		GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get("milica@example.com"), tipoviSoba.get("Trokrevetna (2+1)"), 1, "2024-06-03", "2024-06-05", milicaUsluge);
		GostViews.pregledRezervacija(GostManager.gosti.get("milica@example.com"));
		RecepcionerManager.getInstance().izmenaStatusaRezrvacije("milica@example.com", "2024-06-03", "2024-06-05", "POTVRDJENA");
		
		GostViews.pregledRezervacija(GostManager.gosti.get("milica@example.com"));*/
		
		
	}
	
}
