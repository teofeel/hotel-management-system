package manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.*;
import enumi.*;

public class AdminManager {
	private static AdminManager instance = new AdminManager();
	public static HashMap<String, Administrator> admini = new HashMap<String,Administrator>();
	
	private AdminManager() {}
	
	public static AdminManager getInstance() {
		if(instance==null) {
			instance = new AdminManager();
		}
		return instance;
	}
	
	public String dodajGosta(String ime, String prezime, String pol,String datum, String telefon, String adresa, 
			String brojPasosa, String email) {
		try {
			if(ime.equals("") || prezime.equals("") || pol.equals("") || datum.equals("") || telefon.equals("") || adresa.equals("") 
					|| brojPasosa.equals("") || email.equals("")) throw new Exception("Sva polja moraju biti popunjena");
			
			if(GostManager.gosti.containsKey(email)) throw new Exception("Gost je vec regsistrovan u sistemu");
			
			LocalDate datumRodj = LocalDate.parse(datum);
			GostManager.gosti.put(email, new Gost(ime,prezime, pol, datumRodj,telefon,adresa,email,brojPasosa));
			
			return "Uspesno dodat gost";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	public String dodajZaposlenog(String tip, String ime, String prezime, String pol,String datumRodj, String telefon, 
			String adresa, String korisnickoIme, String lozinka, StrucnaSprema sprema, String stazZaposlenog) {
		try {
			if(tip.equals("") || ime.equals("") || prezime.equals("") || pol.equals("") || datumRodj.equals("") || telefon.equals("") || adresa.equals("") 
					|| korisnickoIme.equals("") || lozinka.equals("") || stazZaposlenog.equals(""))
				throw new Exception("Sva polja moraju biti popunjena");
			
			if (RecepcionerManager.recepcioneri.containsKey(korisnickoIme)) {
				throw new Exception("Postoji recepcioner sa ovim korisnickim imenom");
			}else if(SobaricaManager.sobarice.containsKey(korisnickoIme)) {
				throw new Exception("Postoji sobarica sa ovim korisnickim imenom");
			}else if(admini.containsKey(korisnickoIme)) {
				throw new Exception("Postoji admin sa ovim korisnickim imenom");
			}

			int staz = Integer.parseInt(stazZaposlenog);
			LocalDate datum = LocalDate.parse(datumRodj);
			
			if(tip.equals("Recepcioner")) {
				RecepcionerManager.recepcioneri.put(korisnickoIme,  new Recepcioner(ime, prezime, pol, datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz, tip));
				return "Uspesno dodat recepcioner";
			}
			else if(tip.equals("Sobarica")) {
				SobaricaManager.sobarice.put(korisnickoIme, new Sobarica(ime, prezime, pol, datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz, tip));
				return "Uspesno dodata sobarica";
			}
			else if(tip.equals("Administrator")) {
				admini.put(korisnickoIme, new Administrator(ime, prezime, pol, datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz, tip));
				return "Uspesno dodat admin";
			}
			throw new Error("Dati tip zaposlenog nije podrzan");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String otpustiZaposlenog(String korisnickoIme) {
		try {
			if (RecepcionerManager.recepcioneri.containsKey(korisnickoIme)) {
				RecepcionerManager.recepcioneri.remove(korisnickoIme);
				return "Otpusten";
			}else if(SobaricaManager.sobarice.containsKey(korisnickoIme)) {
				SobaricaManager.sobarice.remove(korisnickoIme);
				return "Otpusten";
			}else if(admini.containsKey(korisnickoIme)) {
				admini.remove(korisnickoIme);
				return "Otpusten";
			}
			
			throw new Exception("Trazeni zaposleni ne postoji");
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	
	public String izmeniPodatkeZaposlenog(String korisnickoIme,String novoKorisnickoIme, String ime, String prezime, String pol,String datum, String telefon, 
										String adresa, String lozinka, String sprema, String staz, String tip) {
		try {
			if(!korisnickoIme.equals(novoKorisnickoIme) && (RecepcionerManager.recepcioneri.containsKey(novoKorisnickoIme) || 
					AdminManager.admini.containsKey(novoKorisnickoIme)  || 
					SobaricaManager.sobarice.containsKey(novoKorisnickoIme)))
				throw new Exception("Ovo korisnicko ime je vec dodeljeno drugom zaposlenom");
			
			if(korisnickoIme.equals("") || ime.equals("") || prezime.equals("") || pol.equals("") || telefon.equals("") || 
				adresa.equals("") || lozinka.equals("") || staz.equals("") || tip.equals("") || datum.equals("") || sprema.equals(""))
				
				throw new Exception("Sva polja moraju biti popunjena");
			
			Zaposleni zaposleni;
			if(tip.equals("Administrator")) {
				if(!this.admini.containsKey(korisnickoIme)) throw new Error("Ne postoji recepcioner");
				Administrator admin = this.admini.get(korisnickoIme);
				
				admin.setKorisnickoIme(novoKorisnickoIme);
				admin.setIme(ime);
				admin.setPrezime(prezime);
				admin.setPol(pol);
				admin.setDatumRodjenja(LocalDate.parse(datum));
				admin.setTelefon(telefon);
				admin.setAdresa(adresa);
				admin.setLozinka(lozinka);
				admin.setStrucnaSprema(StrucnaSprema.valueOf(sprema));
				admin.setStaz(Integer.parseInt(staz));
				
				AdminManager.admini.remove(korisnickoIme);
				AdminManager.admini.put(novoKorisnickoIme, admin);
				
			}else if(tip.equals("Recepcioner")){
				if(!RecepcionerManager.recepcioneri.containsKey(korisnickoIme)) throw new Error("Ne postoji recepcioner");
				Recepcioner recp = RecepcionerManager.recepcioneri.get(korisnickoIme);
				
				recp.setKorisnickoIme(novoKorisnickoIme);
				recp.setIme(ime);
				recp.setPrezime(prezime);
				recp.setPol(pol);
				recp.setDatumRodjenja(LocalDate.parse(datum));
				recp.setTelefon(telefon);
				recp.setAdresa(adresa);
				recp.setLozinka(lozinka);
				recp.setStrucnaSprema(StrucnaSprema.valueOf(sprema));
				recp.setStaz(Integer.parseInt(staz));
				
				RecepcionerManager.recepcioneri.remove(korisnickoIme);
				RecepcionerManager.recepcioneri.put(novoKorisnickoIme, recp);
				
			}else if(tip.equals("Sobarica")){
				if(!SobaricaManager.sobarice.containsKey(korisnickoIme)) throw new Error("Ne postoji recepcioner");
				Sobarica sobarica = SobaricaManager.sobarice.get(korisnickoIme);
				
				sobarica.setKorisnickoIme(novoKorisnickoIme);
				sobarica.setIme(ime);
				sobarica.setPrezime(prezime);
				sobarica.setPol(pol);
				sobarica.setDatumRodjenja(LocalDate.parse(datum));
				sobarica.setTelefon(telefon);
				sobarica.setAdresa(adresa);
				sobarica.setLozinka(lozinka);
				sobarica.setStrucnaSprema(StrucnaSprema.valueOf(sprema));
				sobarica.setStaz(Integer.parseInt(staz));
				
				SobaricaManager.sobarice.remove(korisnickoIme);
				SobaricaManager.sobarice.put(novoKorisnickoIme, sobarica);
			}

			
			return "Uspesno izmenjeni podaci";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	
	public String izmeniPodatkeGosta(String korisnickoIme, String novoKorisnickoIme, String ime, String prezime, String pol,String datum, String telefon, 
			String adresa, String lozinka) {
		try {
			if(!GostManager.gosti.containsKey(korisnickoIme)) throw new Exception("Gost ne postoji");
			Gost gost = GostManager.gosti.get(korisnickoIme);
			
			if(!korisnickoIme.equals(novoKorisnickoIme) && GostManager.gosti.containsKey(novoKorisnickoIme))
				throw new Exception("Ovo korisnicko ime je vec registrovano sa drugim gostom");
			
			if(korisnickoIme.equals("") || novoKorisnickoIme.equals("") || ime.equals("") || prezime.equals("") || 
					pol.equals("") || datum.equals("") || telefon.equals("") || adresa.equals("") || lozinka.equals(""))
				throw new Exception("Sva polja moraju biti popunjena");
			
			gost.setKorisnickoIme(novoKorisnickoIme);
			
			gost.setIme(ime);
			gost.setPrezime(prezime);
			gost.setPol(pol);
			gost.setDatumRodjenja(LocalDate.parse(datum));
			gost.setTelefon(telefon);
			gost.setAdresa(adresa);
			gost.setLozinka(lozinka);
			
			GostManager.gosti.remove(korisnickoIme);
			GostManager.gosti.put(novoKorisnickoIme, gost);
			
			return "Uspesno izmenjeni podaci";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String obrisiGosta(String korisnickoIme) {
		try {
			if(!GostManager.gosti.containsKey(korisnickoIme)) throw new Exception("Gost ne postoji");
			
			GostManager.gosti.remove(korisnickoIme);
			return "Gost je izbrisan iz sistema";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	
	public String definisanjeCenovnika(String vaziOD, String vaziDO, boolean aktivan) {
		try {
			if(vaziOD.equals("") || vaziDO.equals("")) throw new Exception("Sva polja moraju biti popunjena");
			
			if(LocalDate.parse(vaziOD).isBefore(LocalDate.now()) || LocalDate.parse(vaziDO).isBefore(LocalDate.now())) {
				throw new Exception("Cenovnik se moze definisati samo sa naredne datume");
			}
				
				
			if(CenovnikManager.cenovnici.size()==0) {
				HashMap<String, TipSobe> tipoviSoba = new HashMap<String, TipSobe>();
				
				tipoviSoba.put(TipSobeEnum.JEDNOKREVETNA.toString(), new TipSobe(TipSobeEnum.JEDNOKREVETNA.toString(), 0));
				tipoviSoba.put(TipSobeEnum.DVOKREVETNA.toString(), new TipSobe(TipSobeEnum.DVOKREVETNA.toString(), 0));
				tipoviSoba.put(TipSobeEnum.ODVOKREVETNA_DVA.toString(), new TipSobe(TipSobeEnum.ODVOKREVETNA_DVA.toString(), 0));
				tipoviSoba.put(TipSobeEnum.TROKREVETNA.toString(), new TipSobe(TipSobeEnum.TROKREVETNA.toString(), 0));
				
				HashMap<String, DodatneUsluge> dodatne = new HashMap<String, DodatneUsluge>();
				
				
				Cenovnik novi = new Cenovnik(tipoviSoba, dodatne, vaziOD, vaziDO, aktivan);
				
				return "Uspesno dodat novi cenovnik";
			}
			HashMap<String, TipSobe> tipoviSoba = new HashMap<String, TipSobe>();
			HashMap<String, DodatneUsluge> dodatne = new HashMap<String, DodatneUsluge>();
			
			for(TipSobe ts:CenovnikManager.cenovnici.get(0).getTipoviSoba().values()) {
				TipSobe noviTip = new TipSobe(ts);
				tipoviSoba.put(noviTip.getNaziv(), noviTip);
			}
			
			for(DodatneUsluge du:CenovnikManager.cenovnici.get(0).getDodatneUsluge().values()) {
				DodatneUsluge novaUsluga = new DodatneUsluge(du);
				dodatne.put(novaUsluga.getNaziv(), novaUsluga);
			}
			
			Cenovnik novi = new Cenovnik(tipoviSoba, dodatne, vaziOD, vaziDO, aktivan);
			
			CenovnikManager.cenovnici.add(novi);
			
			return "Uspesno dodat novi cenovnik";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public void obrisiCenovnik(String vaziOD, String vaziDO) {
		try {
			for(Cenovnik c:CenovnikManager.cenovnici) {
				if(c.getVaziOd().equals(vaziOD) && c.getVaziDo().equals(vaziDO)) {
					CenovnikManager.cenovnici.remove(c);
					return;
				}
			}
			throw new Exception("Cenovnik nije pronadjen");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String izmeniSobu(String sifra, String novaSifra, String tipSobe) {
		try {
			if(sifra.equals("") || novaSifra.equals("") || tipSobe.equals(""))
				throw new Exception("Polja moraju biti popunjena");
			
			int sifraSobe = Integer.parseInt(sifra);
			int novaSifraSobe = Integer.parseInt(novaSifra);
			
			if(!SobaManager.sobe.containsKey(sifraSobe)) throw new Exception("Sifra sobe ne postoji");
			
			if(sifraSobe!=novaSifraSobe && SobaManager.sobe.containsKey(novaSifraSobe))
				throw new Exception("Nova sifra sobe je vec registrovana u sistemu");
			
			Soba soba = SobaManager.sobe.get(sifraSobe);
			
			soba.setSifra(novaSifraSobe);
			soba.setTipSobe(CenovnikManager.cenovnici.get(0).getTipoviSoba().get(tipSobe));
			
			SobaManager.sobe.remove(sifraSobe);
			SobaManager.sobe.put(novaSifraSobe, soba);
			
			return "Podaci uspesno izmenjeni";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	public String dodajDodatnoSoba(Soba s, String noviDodatak) {
		try {
			if(noviDodatak.equals("")) throw new Exception("Polje za dodatak mora biti popunjeno");
			if(s==null) throw new Exception("Soba ne postoji");
			
			s.getAmenities().add(noviDodatak);
			
			return "Uspesno dodat dodatak";
		}catch(Exception e) {
			return e.getMessage();
		}

	}
	
	public String izbaciDodatnoSoba(Soba s, String dodatak) {
		try {
			if(dodatak.equals("")) throw new Exception("Dodatak mora biti naveden");
			if(s==null) throw new Exception("Soba ne postoji");
			
			if(!s.getAmenities().contains(dodatak)) throw new Exception("Soba ne poseduje ovaj dodatak");
			
			
			s.getAmenities().remove(dodatak);
			
			
			return "Uspesno izbacen dodatak";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public String izbrisiSobu(String sifraSobe) {
		try {
			int sifra = Integer.parseInt(sifraSobe);
			
			if(sifraSobe.equals("")) throw new Exception("Sifra soba nije navedena");
			
			if(!SobaManager.sobe.containsKey(sifra)) throw new Exception("Ova soba ne postoji");
			if (!SobaricaManager.getInstance().izbaciSobuSobaricama(sifra)) 
				throw new Exception("Doslo je do greske prilikom brisanja");
			
			Soba s = SobaManager.sobe.get(sifra);
			String nazivSobe = new String(s.getNazivSobe());	
			SobaManager.sobe.remove(sifra);
			
			if(!RezervacijaManager.getInstance().izbaciSobuRezervacije(nazivSobe))
				throw new Exception("Doslo je do greske");
			
			
			return "Soba uspesno izbrisana";
		}catch(Exception e) {
			return e.getMessage();
		}
		
	}
	
	public String dodajSobu(String sifra,TipSobeEnum nazivTipa) {
		try {
			if(sifra.equals("") || nazivTipa.equals(""))
				throw new Exception("Sva polja moraju biti popunjena");
			
			int sifraSobe = Integer.parseInt(sifra);
			
			if(SobaManager.sobe.containsKey(sifraSobe))
				throw new Exception("Soba je vec registrovana u sistemu");
			
			SobaManager.sobe.put(sifraSobe, new Soba(sifraSobe, nazivTipa.toString()));
			
			return "Uspesno dodata soba";
		}catch(Exception e) {
			return e.getMessage();
		}
	}
	
	public static void pregledPodatakaZaposlenih() {
		try {
			System.out.println("Zaposleni");
			for(Administrator admin : AdminManager.admini.values()) {
				System.out.println(admin.toString());
			}
			for(Recepcioner r : RecepcionerManager.recepcioneri.values()) {
				System.out.println(r.toString());
			}
			for(Sobarica s : SobaricaManager.sobarice.values()) {
				System.out.println(s.toString());
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void dispose() {
		instance = new AdminManager();

	}
	
}
