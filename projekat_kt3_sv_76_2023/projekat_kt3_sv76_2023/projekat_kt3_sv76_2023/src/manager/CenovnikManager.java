package manager;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Cenovnik;
import entity.DodatneUsluge;
import entity.Rezervacija;
import entity.Soba;
import entity.TipSobe;

import enumi.*;

import java.io.File;
import java.io.FileWriter;
import java.time.*;

public class CenovnikManager {
	private static CenovnikManager instance = new CenovnikManager();
	
	public static ArrayList<Cenovnik> cenovnici = new ArrayList<Cenovnik>();
	
	private CenovnikManager() {}
	
	public static CenovnikManager getInstance() {
		if(instance==null) {
			instance = new CenovnikManager();
		}
		return instance;
	}
	
	/*public void izmeniVazenjeCenovnika(LocalDate vaziOD, LocalDate vaziDO, LocalDate novoOD, LocalDate novoDO) {
		try {
			for(Cenovnik c:CenovnikManager.cenovnici) {
				if(c.getVaziOd().equals(vaziOD) && c.getVaziDo().equals(vaziDO)) {
					c.setVaziOD(novoOD);
					c.setVaziDO(novoDO);
					return;
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	/*public void obrisiTipSobe(String nazivSobe) {
		try {
			for(Cenovnik c : CenovnikManager.cenovnici) {
				for (TipSobe ts : c.getTipoviSoba().values()) {
					if(ts.getNaziv().equals(nazivSobe))
						c.getDodatneUsluge().remove(nazivSobe);
				}
			}
			DodatnoManager.getInstance().odbijNepostojeceSobe(nazivSobe);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	/*public void izmeniNazivSobe(String nazivSobe, String noviNaziv) {
		try {
			for(Cenovnik c : CenovnikManager.cenovnici) {
				for (TipSobe ts : c.getTipoviSoba().values()) {
					if(ts.getNaziv().equals(nazivSobe)) {
						c.getTipoviSoba().remove(ts.getNaziv());
						ts.setNaziv(noviNaziv);
						c.getTipoviSoba().put(noviNaziv, ts);
						break;
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}*/
	public String izmeniCenuSobe(Cenovnik c, String nazivSobe, String novaCena) {
		try {
			if(nazivSobe.equals("") || novaCena.equals("")) throw new Exception("Polje mora biti popunjeno");
			
			double cena = Double.parseDouble(novaCena);
			if(!c.getTipoviSoba().containsKey(nazivSobe)) throw new Exception("Cenovnik ne poseduje ovaj tip sobe");
			
			c.getTipoviSoba().get(nazivSobe).setCena(cena);
			
			return "Izmenjena cena sobe";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	public void dodajNovuSobu(TipSobe soba) {
		try {
			for (Cenovnik c:CenovnikManager.cenovnici) {
				if(c.getTipoviSoba().containsKey(soba.getNaziv())) continue;
				
				c.getTipoviSoba().put(soba.getNaziv(), soba);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void obrisiUslugu(String nazivUsluge) {
		try {
			for(Cenovnik c : this.cenovnici) {
				for (DodatneUsluge du : c.getDodatneUsluge().values()) {
					if(du.getNaziv().equals(nazivUsluge)) {
						c.getDodatneUsluge().remove(nazivUsluge);
						break;
					}
				}
			}
			RezervacijaManager.getInstance().izbaciNepostojeceUsluge(nazivUsluge);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public String dodajNovuUslugu(String naziv, String cena) {
		try {
			if(naziv.equals("") || cena.equals("")) throw new Exception("Sva polja moraju biti popunjena");
			
			int cenaUsluge = Integer.parseInt(cena);
			
			DodatneUsluge dodatnaUsluga = new DodatneUsluge(naziv, cenaUsluge);
			
			for (Cenovnik c:CenovnikManager.cenovnici) {
				if(c.getDodatneUsluge().containsKey(dodatnaUsluga.getNaziv())) continue;
				
				c.getDodatneUsluge().put(dodatnaUsluga.getNaziv(), dodatnaUsluga);
			}
			return "Nova dodatne usluga je dodata u cenovnike";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	/*public void izmeniNazivUsluge(String usluga, String noviNaziv) {
		try {
			for(Cenovnik c : CenovnikManager.cenovnici) {
				for (DodatneUsluge du : c.getDodatneUsluge().values()) {
					if(du.getNaziv().equals(usluga)) {
						c.getDodatneUsluge().remove(du.getNaziv());
						du.setNaziv(noviNaziv);
						c.getDodatneUsluge().put(noviNaziv, du);
						break;
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}*/
	public String izmeniCenuUsluge(Cenovnik c, String usluga, String novaCena) {
		try {
			if(usluga.equals("") || novaCena.equals("")) throw new Exception("Polje mora biti popunjeno");
			
			double cena = Double.parseDouble(novaCena);
			if(!c.getDodatneUsluge().containsKey(usluga)) throw new Exception("Cenovnik ne poseduje ovu uslugu");
			
			c.getDodatneUsluge().get(usluga).setCena(cena);

			return "Izmenjena cena usluge";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public Cenovnik getCenovnik(LocalDate datumPocetka, LocalDate datumKraja) {
		try {
			for(Cenovnik c: this.cenovnici) {
				if(c.getVaziOd().equals(datumPocetka) && c.getVaziDo().equals(datumKraja))
					return c;
			}
			
			throw new Exception("Cenovnik u ovom terminu ne postoji");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
	}
	
	public void obrisiNevazeceCenovnike() {
		try {
			for(Cenovnik c : this.cenovnici) {
				if (c.getVaziDo().isBefore(LocalDate.now())) {
					this.cenovnici.remove(c);
				}
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
