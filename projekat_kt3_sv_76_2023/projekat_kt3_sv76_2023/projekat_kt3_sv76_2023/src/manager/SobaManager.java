package manager;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

import entity.*;
import enumi.StatusRezervacije;
import enumi.StatusSobe;
import enumi.TipSobeEnum;

public class SobaManager {
	private static SobaManager instance = new SobaManager();
	
	//public static ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
	public static HashMap<Integer,Soba> sobe = new HashMap<Integer, Soba>();
	
	private SobaManager() {}
	
	public static SobaManager getInstance() {
		if(instance==null) {
			instance = new SobaManager();
		}
		return instance;
	}
	
	/*public ArrayList<Rezervacija> naCekanjuRezervacije(){
		ArrayList<Rezervacija> rezervacijeCekanje = new ArrayList<Rezervacija>();
		for(Rezervacija rez:this.rezervacije) {
			if(rez.getStatus().equals(StatusRezervacije.NA_CEKANJU))
				rezervacijeCekanje.add(rez);
		}
		return rezervacijeCekanje;
	}*/
	
	public ArrayList<Soba> slobodneSobeCheckIn(String tipSobe){
		ArrayList<Soba> slobodne = new ArrayList<Soba>();
		for(Soba s:this.sobe.values()) {
			if(s.getStatus().equals(StatusSobe.SLOBODNA) && s.getNazivSobe().equals(tipSobe)) {
				
				slobodne.add(s);
			}
		}
		
		return slobodne;
	}
	
	public ArrayList<Soba> slobodneSobe(Rezervacija rez) {
		TipSobe tipSobeRezervacije = rez.getTipSobe();
		ArrayList<Soba> sobePoTipu = new ArrayList<Soba>();
		
		for (Soba soba : this.sobe.values()) {
			if(soba.getNazivSobe().equals(tipSobeRezervacije.getNaziv()) && (soba.getStatus().equals(StatusSobe.SLOBODNA) || soba.getStatus().equals(StatusSobe.SPREMANJE))) {
				sobePoTipu.add(soba);
			}
		}
		
		if (sobePoTipu.isEmpty()) return new ArrayList<Soba>();
		
		for (Rezervacija rezervacija : RezervacijaManager.rezervacije) {
			if(rezervacija.getStatus().equals(StatusRezervacije.POTVRDJENA) && rezervacija.getDatumDolaska().equals(rez.getDatumDolaska()) && rezervacija.getDatumOdlaska().equals(rez.getDatumOdlaska())) {
				sobePoTipu.remove(0);
			}
			
			if (rezervacija.getDatumOdlaska().minusDays(1).isBefore(rez.getDatumDolaska()) || rezervacija.getDatumDolaska().isAfter(rez.getDatumOdlaska())) {
				continue;
			}
			
			if (rezervacija.equals(rez) && !sobePoTipu.isEmpty()) {
				return sobePoTipu;
			}
			
			if (sobePoTipu.isEmpty()) {
				return new ArrayList<Soba>();
			}
			
			if (rezervacija.getTipSobe().getNaziv().equals(rez.getTipSobe().getNaziv()) && rezervacija.getStatus().equals(StatusRezervacije.POTVRDJENA)){
				sobePoTipu.remove(0);
			}
			
			
		}
		if (sobePoTipu.isEmpty()) {
			return new ArrayList<Soba>();
		}
		return sobePoTipu;
	}
	
	public ArrayList<Soba> zauzeteSobe(){
		ArrayList<Soba> zauzeteSobe = new ArrayList<Soba>();
		
		for(Soba s:this.sobe.values()) {
			if (s.getStatus().equals(StatusSobe.ZAUZETO))
				zauzeteSobe.add(s);
		}
		
		return zauzeteSobe;
	}
	
	/*public void izbaciNepostojeceUsluge(String nazivUsluge) {
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
		
		for(Rezervacija rez:DodatnoManager.rezervacije) {
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
		
	}*/
	
	public ArrayList<String> pregledSlobodnihTipovaSoba(LocalDate pocetak, LocalDate kraj) {
		ArrayList<String> tipoviSobe = new ArrayList<String>();
		
		if(pocetak.isBefore(LocalDate.now()) || kraj.isBefore(LocalDate.now()))
			return tipoviSobe;
		
		for(TipSobeEnum ts:TipSobeEnum.values()) {
			if(this.pregledSlobodnihSoba(pocetak, kraj, ts.toString()) && 
					!tipoviSobe.contains(ts.toString())) {
				tipoviSobe.add(ts.toString());
			}
		}
		
		return tipoviSobe;
	}
	
	public ArrayList<String> pregledSlobodnihTipovaSoba(LocalDate pocetak, LocalDate kraj, String dodaci) {
		ArrayList<String> tipoviSobe = new ArrayList<String>();
		
		if(pocetak.isBefore(LocalDate.now()) || kraj.isBefore(LocalDate.now()))
			return tipoviSobe;
		
		for(TipSobeEnum ts:TipSobeEnum.values()) {
			if(this.pregledSlobodnihSoba(pocetak, kraj, ts.toString(), dodaci) && 
					!tipoviSobe.contains(ts.toString())) {
				tipoviSobe.add(ts.toString());
			}
		}
		return tipoviSobe;
	}
	
	public static boolean pregledSlobodnihSoba(LocalDate pocetak, LocalDate kraj, String tipSobe, String amenities) {
		ArrayList<Soba> sobePoTipu = new ArrayList<Soba>();

		for (Soba soba : SobaManager.sobe.values()) {
			int br=0;
			if(!amenities.equals("")) {
				for(String amenity:amenities.split(" ")) {
					if(amenity.equals("Standardno")) continue;
					if(!soba.getAmenities().contains(amenity))
						br+=1;
				}
			}
			
			if(soba.getNazivSobe().equals(tipSobe) && (soba.getStatus().equals(StatusSobe.SLOBODNA) || soba.getStatus().equals(StatusSobe.SPREMANJE)) && br==0) {
				sobePoTipu.add(soba);
			}
		}
		if (sobePoTipu.isEmpty()) {
			//System.out.println("Nema slbodnih soba");
			return false;
		}
		
		for (Rezervacija rezervacija : RezervacijaManager.rezervacije) {
			if (rezervacija.getDatumOdlaska().minusDays(1).isBefore(pocetak) || rezervacija.getDatumDolaska().isAfter(kraj)) {
				continue;
			}
			
			if (sobePoTipu.isEmpty()) {
				//System.out.println("Nema slobodnih soba");
				return false;
			}
			
			if (rezervacija.getTipSobe().getNaziv().equals(tipSobe) && rezervacija.getStatus().equals(StatusRezervacije.POTVRDJENA)){
				sobePoTipu.remove(0);
			}
		}
		if (sobePoTipu.isEmpty()) {
			//System.out.println("Nema slobodnih soba");
			return false;
		}
		
		/*for(Soba s:sobePoTipu) {
			System.out.println(s);
		}*/
		return true;
	}
	
	public static boolean pregledSlobodnihSoba(LocalDate pocetak, LocalDate kraj, String tipSobe) {
		ArrayList<Soba> sobePoTipu = new ArrayList<Soba>();
		
		
		for (Soba soba : SobaManager.sobe.values()) {
			if(soba.getNazivSobe().equals(tipSobe) && soba.getStatus().equals(StatusSobe.SLOBODNA)) {
				sobePoTipu.add(soba);
			}
		}
		if (sobePoTipu.isEmpty()) {
			//System.out.println("Nema slbodnih soba");
			return false;
		}
		
		for (Rezervacija rezervacija : RezervacijaManager.rezervacije) {
			if (rezervacija.getDatumOdlaska().minusDays(1).isBefore(pocetak) || rezervacija.getDatumDolaska().isAfter(kraj)) {
				continue;
			}
			
			if (sobePoTipu.isEmpty()) {
				//System.out.println("Nema slobodnih soba");
				return false;
			}
			
			if (rezervacija.getTipSobe().getNaziv().equals(tipSobe) && rezervacija.getStatus().equals(StatusRezervacije.POTVRDJENA)){
				sobePoTipu.remove(0);
			}
		}
		if (sobePoTipu.isEmpty()) {
			//System.out.println("Nema slobodnih soba");
			return false;
		}
		
		/*for(Soba s:sobePoTipu) {
			System.out.println(s);
		}*/
		return true;
	}
	
}
