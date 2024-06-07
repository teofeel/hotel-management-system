package manager;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.DodatneUsluge;
import entity.Gost;
import entity.Rezervacija;
import entity.Sobarica;
import entity.TipSobe;
import enumi.StatusRezervacije;

public class GostManager {
	private static GostManager instance = new GostManager();
	public static HashMap<String, Gost> gosti = new HashMap<String,Gost>();
	
	private GostManager() {}
	
	public static GostManager getInstance() {
		if(instance==null) {
			instance = new GostManager();
		}
		return instance;
	}
	
	public String zahtevRezervacija(Gost gost, TipSobe tipSobe, int brOsoba,String datumDolaska, String datumOdlaska,ArrayList<DodatneUsluge> usluga) {
		try {
			LocalDate datumOD = LocalDate.parse(datumDolaska);
			LocalDate datumDO = LocalDate.parse(datumOdlaska);
			
			if(datumOD.isAfter(datumDO)) throw new Exception();
			
			DodatnoManager.rezervacije.add(new Rezervacija(gost.getKorisnickoIme(), tipSobe, null,  brOsoba, datumOD, datumDO, usluga));
			
			return "Zahtev je poslat";
		}catch(Exception e) {
			return "Nije moguce napraviti rezervaciju";
		}
		
	}
	
	public String otkaziRezervaciju(Gost gost, String datumDolaska, String datumOdlaska) {
		try {
			for(int i=0;i<DodatnoManager.rezervacije.size();i++) {
				if(DodatnoManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme()) && DodatnoManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& DodatnoManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					DodatnoManager.rezervacije.get(i).setStatus(StatusRezervacije.OTKAZANA);
					return "Rezervacija je otkazana";
				}
			}
			throw new Exception("Trazena rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	
	public void dodajUsluguNaRezervaciju(Gost gost, DodatneUsluge dodatnaUsluga, String datumDolaska, String datumOdlaska) {
		try {
			for(int i=0;i<DodatnoManager.rezervacije.size();i++) {
				if(DodatnoManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme()) && DodatnoManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& DodatnoManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					DodatnoManager.rezervacije.get(i).dodajUslugu(dodatnaUsluga);
					return;
				}
			}
			throw new Exception("Rezervacija ne postoji");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void izbaciUsluguSaRezervacije(Gost gost, DodatneUsluge dodatnaUsluga, String datumDolaska, String datumOdlaska) {
		try {
			for(int i=0;i<DodatnoManager.rezervacije.size();i++) {
				if(DodatnoManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme()) && DodatnoManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& DodatnoManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					DodatnoManager.rezervacije.get(i).izbaciUslugu(dodatnaUsluga.getNaziv());
					return;
				}
			}
			throw new Exception("Rezervacija ne postoji");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
