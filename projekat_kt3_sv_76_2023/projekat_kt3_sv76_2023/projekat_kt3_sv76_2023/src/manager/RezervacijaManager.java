package manager;

import java.time.LocalDate;
import java.util.*;

import entity.*;
import enumi.StatusRezervacije;

public class RezervacijaManager {
	private static RezervacijaManager instance = new RezervacijaManager();
	
	public static ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
	
	private RezervacijaManager() {}
	
	public static RezervacijaManager getInstance() {
		if(instance==null) {
			instance = new RezervacijaManager();
		}
		return instance;	
	}
	
	public ArrayList<Rezervacija> naCekanjuRezervacije(){
		ArrayList<Rezervacija> rezervacijeCekanje = new ArrayList<Rezervacija>();
		for(Rezervacija rez:this.rezervacije) {
			if(rez.getStatus().equals(StatusRezervacije.NA_CEKANJU))
				rezervacijeCekanje.add(rez);
		}
		return rezervacijeCekanje;
	}
	
	public void izbaciNepostojeceUsluge(String nazivUsluge) {
		for(Rezervacija rez:this.rezervacije) {
			for(DodatneUsluge du:rez.getUsluge()) {
				if(du.getNaziv().equals(nazivUsluge)) {
					rez.izbaciUslugu(nazivUsluge);
				}
			}
		}
	}
	public void odbijNepostojeceSobe(String tip) {
		for(Rezervacija rez:this.rezervacije) {
			if(rez.getTipSobe().getNaziv().equals(tip)) {
				rez.setStatus(StatusRezervacije.ODBIJENA);
			}
		}
	}
	
	public void odbijIstekleRezervacije() {
		LocalDate danasnjiDan = LocalDate.now();
		
		for(Rezervacija rez : this.rezervacije) {
			if (rez.getDatumDolaska().isBefore(danasnjiDan) && rez.getStatus().equals(StatusRezervacije.NA_CEKANJU)) {
				rez.setStatus(StatusRezervacije.ODBIJENA);
			}
		}
	}
	
	public ArrayList<Rezervacija> potvrdjeneRezervacije(){
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		
		for(Rezervacija rez:this.rezervacije) {
			if(rez.getSoba()==null && rez.getStatus().equals(StatusRezervacije.POTVRDJENA) && rez.getDatumOdlaska().isAfter(LocalDate.now())) {
				rezervacije.add(rez);
			}
		}
		
		return rezervacije;
	}
	
	public boolean izbaciSobuRezervacije(String tipSobe) {
		try {
			for(Rezervacija rez:this.rezervacije) {
				if(rez.getTipSobe().getNaziv().equals(tipSobe) && rez.getDatumDolaska().isAfter(LocalDate.now()) && rez.getStatus().equals(StatusRezervacije.POTVRDJENA)) {
					RecepcionerManager.getInstance().izmenaStatusaRezrvacije(rez.getGost(), rez.getDatumDolaska().toString(),
							rez.getDatumOdlaska().toString(), StatusRezervacije.POTVRDJENA);
				}
			}
			
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public ArrayList<Rezervacija> pregledRezervacija(Gost gost) {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
			if (RezervacijaManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme())) {
				rezervacije.add(RezervacijaManager.rezervacije.get(i));
				System.out.println(RezervacijaManager.rezervacije.get(i).toString());
			}
		}
		return rezervacije;
	}
}
