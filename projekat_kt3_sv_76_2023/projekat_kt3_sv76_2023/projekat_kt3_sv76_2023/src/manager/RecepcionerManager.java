package manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.*;

import enumi.StatusRezervacije;
import enumi.StatusSobe;

public class RecepcionerManager {
	private static RecepcionerManager instance = new RecepcionerManager();
	public static HashMap<String, Recepcioner> recepcioneri = new HashMap<String,Recepcioner>();
	
	private RecepcionerManager() {}
	
	public static RecepcionerManager getInstance() {
		if(instance==null) {
			instance = new RecepcionerManager();
		}
		return instance;
	}
	
	public String checkInProces(String ime, String prezime, String pol,String datum, String telefon, String adresa, 
			String brojPasosa, String email) {
		try {
			if(ime.equals("") || prezime.equals("") || pol.equals("") || datum.equals("") || telefon.equals("") || adresa.equals("") 
					|| brojPasosa.equals("") || email.equals("")) throw new Exception("Sva polja moraju biti popunjena");
			
			if(GostManager.gosti.containsKey(email)) throw new Exception("Gost je vec regsistrovan u sistemu");
			
			return dodajGosta(ime, prezime, pol, LocalDate.parse(datum), telefon, adresa, Integer.parseInt(brojPasosa), email);
			
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	public String checkInProcesRezervacija(String gost, LocalDate datumDolaska, LocalDate datumOdlaska, Soba soba) {
		/* 
		 * u slucaju da gost ima rezervaciju
		 * da bi se kasnije moglo filtrirati po sobama
		*/
		try {
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().equals(datumOdlaska)) {
					
					if(!soba.getNazivSobe().equals(RezervacijaManager.rezervacije.get(i).getTipSobe().getNaziv())) throw new Exception("Tipovi se ne poklapaju");
					
					RezervacijaManager.rezervacije.get(i).setSoba(soba);
					soba.setStatus(StatusSobe.ZAUZETO);
					return "Uspesno izvrsen checkin";
				}
			}
			throw new Exception("Trazena rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	private String dodajGosta(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, 
			int brojPasosa, String email) {
		try {
			GostManager.gosti.put(email, new Gost(ime,prezime, pol,datum,telefon,adresa,email,brojPasosa));
			return "Gost je uspesno dodat u sistem";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String izmenaStatusaRezrvacije(String gost, String datumDolaska, String datumOdlaska, StatusRezervacije status) {
		try {
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					
					if (SobaManager.getInstance().slobodneSobe(RezervacijaManager.rezervacije.get(i)).size() == 0 && status.equals(StatusRezervacije.POTVRDJENA) ) {
						RezervacijaManager.rezervacije.get(i).setStatus(StatusRezervacije.ODBIJENA);
						throw new Exception("Nema slobodne sobe, rezervacja odbijena");
					}
					
					RezervacijaManager.rezervacije.get(i).setStatus(status);
					return "Status je izmenjen";
				}
			}
			throw new Exception("Trazena rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String dodajUsluguNaRezervaciju(DodatneUsluge dodatnaUsluga, String gost, String datumDolaska, String datumOdlaska) {
		try {
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					RezervacijaManager.rezervacije.get(i).dodajUslugu(dodatnaUsluga);
					return "Uspesno dodata usluga";
				}
			}
			throw new Exception("Rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String  izbaciUsluguSaRezervacije(String gost, DodatneUsluge dodatnaUsluga, String datumDolaska, String datumOdlaska) {
		try {
			for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
				if(RezervacijaManager.rezervacije.get(i).getGost().equals(gost) && RezervacijaManager.rezervacije.get(i).getDatumDolaska().toString().equals(datumDolaska) 
						&& RezervacijaManager.rezervacije.get(i).getDatumOdlaska().toString().equals(datumOdlaska)) {
					RezervacijaManager.rezervacije.get(i).izbaciUslugu(dodatnaUsluga.getNaziv());
					return "Uspesno izbacena usluga";
				}
			}
			throw new Exception("Rezervacija ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String checkOUTProces(Soba soba) {
		try {
			if (!soba.getStatus().equals(StatusSobe.ZAUZETO)) throw new Exception("Ne moze se izvrsiti check out za ovu sobu");
			
			soba.setStatus(StatusSobe.SPREMANJE);
			SobaricaManager.getInstance().dodeliSobu(soba);
			return "Check out obavljen";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
}
