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
			
			RezervacijaManager.rezervacije.add(new Rezervacija(gost.getKorisnickoIme(), tipSobe, null,  brOsoba, datumOD, datumDO, usluga, LocalDate.now()));
			
			return "Zahtev je poslat";
		}catch(Exception e) {
			return "Nije moguce napraviti rezervaciju";
		}
		
	}
	
	public String otkaziRezervaciju(Gost gost, String datumDolaska, String datumOdlaska) {
		try {
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme()) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					
					if(RezervacijaManager.rezervacije.get(i).getDatumDolaska().isBefore(LocalDate.now()) && 
							RezervacijaManager.rezervacije.get(i).getStatus().equals(StatusRezervacije.POTVRDJENA))
						throw new Exception("Rezervacija je prosla, ne moze se otkazati");
						
					RezervacijaManager.rezervacije.get(i).setStatus(StatusRezervacije.OTKAZANA);
					return "Rezervacija je otkazana";
				}
			}
			throw new Exception("Trazena rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	
	public String dodajUsluguNaRezervaciju(Gost gost, DodatneUsluge dodatnaUsluga, String datumDolaska, String datumOdlaska) {
		try {
			if (!CenovnikManager.cenovnici.get(0).getDodatneUsluge().containsKey(dodatnaUsluga.getNaziv()))
				throw new Exception("Ova usluga ne postoji");
			
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme()) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					
					if(RezervacijaManager.rezervacije.get(i).getDatumDolaska().isBefore(LocalDate.now()))
						throw new Exception("Rezervacija je prosla, ne mogu se dodati usluge na nju");
					
					RezervacijaManager.rezervacije.get(i).dodajUslugu(dodatnaUsluga);
					return "Dodata usluga na rezervaciju";
				}
			}
			throw new Exception("Rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String ukupanTrosak(String gost) {
		try {
			float trosak = 0;
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getStatus().equals(StatusRezervacije.ODBIJENA))
					continue; 
				
				if(rez.getGost().equals(gost)) {
					trosak +=  rez.getCena();
				}
			}
			return Float.toString(trosak);
		}catch(Exception e) {
			return "0";
		}
	}
	
	public String izbaciUsluguSaRezervacije(Gost gost, DodatneUsluge dodatnaUsluga, String datumDolaska, String datumOdlaska) {
		try {
			if (!CenovnikManager.cenovnici.get(0).getDodatneUsluge().containsKey(dodatnaUsluga.getNaziv()))
				throw new Exception("Ova usluga ne postoji");
			
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme()) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					
					if(RezervacijaManager.rezervacije.get(i).getDatumDolaska().isBefore(LocalDate.now()))
						throw new Exception("Rezervacija je prosla, ne mogu se izbaciti usluge sa nje");
					
					RezervacijaManager.rezervacije.get(i).izbaciUslugu(dodatnaUsluga.getNaziv());
					return "Izbacena usluga sa rezervaciju";
				}
			}
			throw new Exception("Rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
}
