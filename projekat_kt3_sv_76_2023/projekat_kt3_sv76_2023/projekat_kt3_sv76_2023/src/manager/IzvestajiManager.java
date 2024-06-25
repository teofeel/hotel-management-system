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
	
	
	public boolean prihodiRashodi(String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			double prihodi = 0;
			double rashodi = 0;
			for(Rezervacija rez:RezervacijaManager.rezervacije) {
				if(rez.getStatus().equals(StatusRezervacije.NA_CEKANJU) || rez.getStatus().equals(StatusRezervacije.ODBIJENA))
					continue;
				
				if((rez.getDatumDolaska().isBefore(pocetak) && rez.getDatumOdlaska().isBefore(pocetak)) ||
					(rez.getDatumDolaska().isAfter(kraj) && rez.getDatumOdlaska().isAfter(kraj))) 
					continue;
					
				
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public void sredjeneSobe(Sobarica sob, String p, String k) {
		try {
			if(p.equals("") || k.equals("")) throw new Exception();
			
			LocalDate pocetak = LocalDate.parse(p);
			LocalDate kraj = LocalDate.parse(k);
			
			

		}catch(Exception e) {
			
		}
	}
	
	public void potvrdjeneRezervacije(String p, String k) {
		try {
			
		}catch(Exception e) {
			
		}
	}
	
}
