package manager;

import java.time.LocalDate;
import java.util.*;

import entity.*;
import enumi.StatusRezervacije;
import java.time.temporal.ChronoUnit;
import manager.*;

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
	
	public HashMap<String, Integer> sredjeneSobe(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			HashMap<String, Integer> sobaricaSredjene = new HashMap<String, Integer>();
			for(Sobarica sob:SobaricaManager.sobarice.values()) {
				int br=0;
				for(LocalDate dan:sob.getSredjeneSobe()) {
					if(dan.isAfter(pocetak.minusDays(1)) && dan.isBefore(kraj.plusDays(1))) {
						br+=1;
					}
				}
				sobaricaSredjene.put(sob.getKorisnickoIme(), br);
			}
			
			return sobaricaSredjene;

		}catch(Exception e) {
			return new HashMap<String, Integer>();
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
	
	public HashMap<Integer, Integer> sobaNocenja(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			HashMap<Integer, Integer> sobeNocenja = new HashMap<Integer, Integer>();
			
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getSoba()==null) continue;
				
				if(rez.getDatumDolaska().isAfter(pocetak.minusDays(1)) && rez.getDatumDolaska().isBefore(kraj.plusDays(1)) && 
						rez.getDatumOdlaska().isAfter(pocetak.minusDays(1)) &&rez.getDatumOdlaska().isBefore(kraj.plusDays(1))) {
					int broj = sobeNocenja.containsKey(rez.getSoba().getSifra()) ? sobeNocenja.get(rez.getSoba().getSifra()):0;
					
					int i=0;
					while (rez.getDatumDolaska().plusDays(i).isBefore(rez.getDatumOdlaska())) {
						i+=1;
					}
					
					broj+=1;
					sobeNocenja.put(rez.getSoba().getSifra(), broj);
				}	
			}
			for(Soba s:SobaManager.sobe.values()) {
				if(!sobeNocenja.containsKey(s.getSifra()))
					sobeNocenja.put(s.getSifra(), 0);
			}
			return sobeNocenja;
		}catch(Exception e) {
			HashMap<Integer, Integer> sobeNocenja = new HashMap<Integer, Integer>();
			for(Soba s:SobaManager.sobe.values()) {
				sobeNocenja.put(s.getSifra(), 0);
			}
			
			return sobeNocenja;
		}
	}

	public HashMap<Integer, Float> sobaPrihodi(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			HashMap<Integer, Float> sobePrihodi = new HashMap<Integer, Float>();
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getSoba()==null) continue;
				
				if(rez.getDatumDolaska().isAfter(pocetak.minusDays(1)) && rez.getDatumDolaska().isBefore(kraj.plusDays(1)) && 
						rez.getDatumOdlaska().isAfter(pocetak.minusDays(1)) &&rez.getDatumOdlaska().isBefore(kraj.plusDays(1))) {
					
					float prihod = sobePrihodi.containsKey(rez.getSoba().getSifra()) ? sobePrihodi.get(rez.getSoba().getSifra()):(float) 0;
					
					prihod+=rez.getCena();
					
					sobePrihodi.put(rez.getSoba().getSifra(), prihod);
				}	
			}
			
			for(Soba s:SobaManager.sobe.values()) {
				if(!sobePrihodi.containsKey(s.getSifra()))
					sobePrihodi.put(s.getSifra(), 0.0f);
			}
			
			return sobePrihodi;
			
		}catch(Exception e) {
			HashMap<Integer, Float> sobePrihodi= new HashMap<Integer, Float>();
			for(Soba s:SobaManager.sobe.values()) {
				sobePrihodi.put(s.getSifra(), (float) 0);
			}
			
			return sobePrihodi;
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
