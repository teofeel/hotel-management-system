package manager;

import java.util.*;

import entity.*;
import enumi.*;

public class SobaricaManager {
	private static SobaricaManager instance = new SobaricaManager();
	public static HashMap<String, Sobarica> sobarice = new HashMap<String,Sobarica>();
	
	private SobaricaManager() {}
	
	public static SobaricaManager getInstance() {
		if(instance==null) {
			instance = new SobaricaManager();
		}
		return instance;
	}
	
	/*public void sredjenaSoba(Sobarica sobarica, String brojSobe) {
		//Obrisi iz hashmape dodeljenu sobu i upisi je u fajl
		try {
			if(!sobarica.getDodeljeneSobe().containsKey(brojSobe)) throw new Exception("Ova soba nije dodeljena");
			
			sobarica.getDodeljeneSobe().remove(brojSobe);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}*/
	
	public String sredjenaSoba(Soba soba, Sobarica sobarica) {
		try {
			if (soba.getStatus().equals(StatusSobe.SLOBODNA) || soba.getStatus().equals(StatusSobe.ZAUZETO)) 
				throw new Exception("Soba nije markirana za sredjivanje");
			
			if (!sobarica.removeSoba(soba)) throw new Exception("Soba nije dodeljena");
			
			soba.setStatus(StatusSobe.SLOBODNA);
			
			return "Soba je sredjena";
		}catch(Exception e) {
			return e.getMessage();
		}
	}

	public void dodeliSobu(Soba s) {
		try {
			if (this.sobarice.isEmpty()) throw new Exception("Nema sobarica");
			if (s.getStatus().equals(StatusSobe.SLOBODNA) || s.getStatus().equals(StatusSobe.ZAUZETO)) 
				throw new Exception("Soba nije markirana za sredjivanje");
			
			Sobarica najnezaposlenijaSobarica = this.sobarice.values()
											.stream()
											.min((s1,s2) -> Integer.compare(s1.getDodeljeneSobe().size(), s2.getDodeljeneSobe().size()))
											.orElse(null);
			
			najnezaposlenijaSobarica.addSoba(s);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean izbaciSobuSobaricama(int sifra) {
		try {
			for(Sobarica s:this.sobarice.values()) {
				if(s.getDodeljeneSobe().containsKey(sifra)) {
					s.getDodeljeneSobe().remove(sifra);
				}	
			}
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
