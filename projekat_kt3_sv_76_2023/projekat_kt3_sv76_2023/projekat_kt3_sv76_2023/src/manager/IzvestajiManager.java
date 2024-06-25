package manager;

import java.time.LocalDate;
import java.util.*;

import entity.*;
import enumi.StatusRezervacije;

public class IzvestajiManager {
	private static IzvestajiManager instance = new IzvestajiManager();
	
	private IzvestajiManager() {}
	
	public static IzvestajiManager getInstance() {
		if(instance==null) {
			instance = new IzvestajiManager();
		}
		return instance;
	}
	
	
	public ArrayList<Float> prihodiRashodi(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			float prihodi = 0;
			float rashodi = 0;
			
			int i=0;
			while (pocetak.plusDays(i).isBefore(kraj.plusDays(1))) {
				if(pocetak.plusDays(i).getDayOfMonth()!=1) {
					i+=1;
					continue;
				}
				
				for(Administrator admin:AdminManager.admini.values()) {
						rashodi += admin.primanja();
				}
				for(Sobarica sob:SobaricaManager.sobarice.values()) {
					rashodi += sob.primanja();
				}
				for(Recepcioner recp:RecepcionerManager.recepcioneri.values()) {
					rashodi += recp.primanja();
				}
				i+=1;
			}
			
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getStatus().equals(StatusRezervacije.NA_CEKANJU) || rez.getStatus().equals(StatusRezervacije.ODBIJENA))
					continue;
				
				if(rez.getDatumOdlaska().isAfter(pocetak.minusDays(1)) && rez.getDatumOdlaska().isBefore(kraj.plusDays(1)))
					prihodi += rez.getCena();
			}
			
			ArrayList<Float> finansije = new ArrayList<Float>();
			finansije.add(prihodi);
			finansije.add(rashodi);
			
			return finansije;
		}catch(Exception e) {
			ArrayList<Float> finansije = new ArrayList<Float>();
			finansije.add((float) 0);
			finansije.add((float) 0);
			
			return finansije;
		}
	}
	
	public int sredjeneSobe(Sobarica sob, String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			int br=0;
			for(LocalDate dan:sob.getSredjeneSobe()) {
				if(dan.isAfter(pocetak.minusDays(1)) && dan.isBefore(kraj.plusDays(1))) {
					br+=1;
				}
			}
			return br;

		}catch(Exception e) {
			return 0;
		}
	}
	
	public int potvrdjeneRezervacije(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			int potvrdjene = 0;
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getStatus().equals(StatusRezervacije.POTVRDJENA) && 
						rez.getDatumDolaska().isAfter(pocetak.minusDays(1)) && rez.getDatumDolaska().isBefore(kraj.plusDays(1))) {
					potvrdjene+=1;
				}
			}
			return potvrdjene;
		}catch(Exception e) {
			return 0;
		}
	}
	
	public ArrayList<Integer> obradjeneRezervacije(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			int potvrdjene = 0;
			int otkazane = 0;
			int odbijene = 0;
			
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if (rez.getDatumDolaska().isAfter(pocetak.minusDays(1)) && rez.getDatumDolaska().isBefore(kraj.plusDays(1))){
					if (rez.getStatus().equals(StatusRezervacije.POTVRDJENA)) {
						potvrdjene+=1;
					}else if(rez.getStatus().equals(StatusRezervacije.OTKAZANA)) {
						otkazane+=1;
					}else if(rez.getStatus().equals(StatusRezervacije.ODBIJENA)) {
						odbijene+=1;
					}
				}
			}
			ArrayList<Integer> rezultati = new ArrayList<Integer>();
			rezultati.add(potvrdjene);
			rezultati.add(otkazane);
			rezultati.add(odbijene);
			
			return rezultati;
			
		}catch(Exception e) {
			ArrayList<Integer> rezultati = new ArrayList<Integer>();
			rezultati.add(0);
			rezultati.add(0);
			rezultati.add(0);
			
			return rezultati;
		}
	}
	
	public void sobaStatistic(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			
		}catch(Exception e) {
			
		}
	}

	
	public ArrayList<Float> prihodiZadnjaGodina(String tipSobe) {
		ArrayList<Float> prihodi = new ArrayList<Float>();
		
		LocalDate sadasnjiDatum = LocalDate.now();

		for (int i=0;i<12;i++) {
			LocalDate prethodniDatumKraj = sadasnjiDatum.minusMonths(i);
			LocalDate prethodniDatumPocetak = sadasnjiDatum.minusMonths(i+1);
			
			float prihod = 0;
			
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getStatus().equals(StatusRezervacije.ODBIJENA) || rez.getStatus().equals(StatusRezervacije.NA_CEKANJU))
					continue;
				
				if(rez.getTipSobe().getNaziv().equals(tipSobe) && ( rez.getDatumOdlaska().isAfter(prethodniDatumPocetak.minusDays(1)) 
						&& rez.getDatumOdlaska().isBefore(prethodniDatumKraj.plusDays(1))))
					prihod+=rez.getCena();
			}
			
			prihodi.add(prihod);
		}
		
		return prihodi;
	}
	
	public HashMap<String, Integer>  opterecenostSobarica() {
		HashMap<String, Integer> opterecenostSobarica = new HashMap<String, Integer>();
		
		LocalDate prosliMesec = LocalDate.now().minusMonths(1);
		
		for(Sobarica s:SobaricaManager.sobarice.values()) {
			int i=0;
			for(LocalDate datum:s.getSredjeneSobe()) {
				if(datum.isAfter(prosliMesec))
					i+=1;
			}
			opterecenostSobarica.put(s.getKorisnickoIme(), i);
		}
		
		return opterecenostSobarica;
	}
	
	public HashMap<String, Integer> statusRezervacijaPrethodniMesec() {
		HashMap<String, Integer> rezervacije = new HashMap<String, Integer>();
		
		LocalDate prosliMesec = LocalDate.now().minusMonths(1);
		
		int naCekanju = 0;
		int potvrdjene = 0;
		int otkazane = 0;
		int odbijene = 0;
		
		for(Rezervacija rez:RezervacijaManager.rezervacije) {
			if(rez.getDatumKreiranja().isBefore(prosliMesec)) continue;
			
			if(rez.getStatus().equals(StatusRezervacije.NA_CEKANJU))
				naCekanju+=1;
			
			if(rez.getStatus().equals(StatusRezervacije.POTVRDJENA))
				potvrdjene+=1;
			
			if(rez.getStatus().equals(StatusRezervacije.ODBIJENA))
				otkazane+=1;
			
			if(rez.getStatus().equals(StatusRezervacije.OTKAZANA))
				odbijene+=1;
			
		}
		rezervacije.put("NA_CEKANJU", naCekanju);
		rezervacije.put("POTVRDJENE", potvrdjene);
		rezervacije.put("OTKAZANE", otkazane);
		rezervacije.put("ODBIJENE", odbijene);
		
		return rezervacije;
		
	}
	
	
}
