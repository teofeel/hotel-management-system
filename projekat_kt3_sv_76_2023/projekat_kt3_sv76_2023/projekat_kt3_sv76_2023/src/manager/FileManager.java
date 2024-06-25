package manager;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.io.*;
import java.util.*;
import entity.*;
import manager.*;
import enumi.StatusRezervacije;
import enumi.StatusSobe;
import enumi.StrucnaSprema;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
public class FileManager {
	private static FileManager instance = new FileManager();
	private FileManager() {}
	
	public static FileManager getInstance() {
		if(instance==null) {
			instance = new FileManager();
		}
		return instance;
	}
	public void upisiPodatke() {
		this.ocistiFolder();
		this.upisiRezervacije();
		this.upisiSobe();
		this.upisiGoste();
		this.upisiZaposlene();
		this.upisiCenovnike();
	}
	public boolean ucitajPodatke() {
		System.out.println("ucitavanje");
		boolean cenovnici = this.ucitajCenovnike();
		boolean sobe = this.ucitajSobe();
		boolean rezervacije = this.ucitajRezervacije();
		boolean gosti = this.ucitajGoste();
		boolean zaposleni = this.ucitajZaposlene();
		
		return cenovnici && sobe && rezervacije && gosti && zaposleni;
	}
	public void ocistiPodatkeAplikacija() {
		AdminManager.admini.clear();
		CenovnikManager.cenovnici.clear();
		RezervacijaManager.rezervacije.clear();
		SobaManager.sobe.clear();
		GostManager.gosti.clear();
		RecepcionerManager.recepcioneri.clear();
		SobaricaManager.sobarice.clear();
	}
	public void ocistiFolder() {
		try {
			Path path = Paths.get("../data");

	        Files.walkFileTree(path,
	            new SimpleFileVisitor<>() {

	                @Override
	                public FileVisitResult postVisitDirectory(Path dir,IOException exc)
	                	throws IOException {
	                    	Files.delete(dir);
	                    	return FileVisitResult.CONTINUE;
	                }

	                @Override
	                public FileVisitResult visitFile(Path file,BasicFileAttributes attrs)
	                	throws IOException {
	                    	Files.delete(file);
	                    	return FileVisitResult.CONTINUE;
	                }
	            }
	        );
	        return;
		}catch(Exception e) {
			
		}
	}
	private FileWriter getFileWriter(String nazivFajla) {
		try {
			//String currentDir = System.getProperty("user.dir");
			//File parentFolderPath = new File("data");
			//File parentFolderPath = new File(currentDir).getParentFile();
			//String folderPath = parentFolderPath.getPath()+File.separator+"data";
			String folderPath = "../data";
			
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdir();
			}
		
			String filePath = folderPath + File.separator + nazivFajla;
		
		
			FileWriter writer = new FileWriter(filePath, false);
		
			return writer;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public void upisiRezervacije() {
		try {
			FileWriter writer = this.getFileWriter("rezervacije.csv");
			
			writer.write("Email,Status,Tip Sobe,Soba,Datum Dolaska,Datum Odlaska,Broj Osoba,Dodatne Usluge,Cena"+"\n");
			for(Rezervacija rez : RezervacijaManager.rezervacije) {
				String dodatneUsluge = "";
				for(DodatneUsluge du:rez.getUsluge()) {
					dodatneUsluge += du.getNaziv()+"|";
				}
				StatusRezervacije status = rez.getStatus();
				String statusRez = status.toString();
				if(status.equals(StatusRezervacije.NA_CEKANJU)) {
					statusRez = "NA_CEKANJU";
				}
				String sifraSobe = rez.getSoba()!= null ? Integer.toString(rez.getSoba().getSifra()):"";
				
				String str = (rez.getGost()+","+statusRez+","+rez.getTipSobe().getNaziv()+","+
						sifraSobe+","+rez.getDatumDolaska().toString()+","+rez.getDatumOdlaska().toString()+","
						+Integer.toString(rez.getBrOsoba())+","+dodatneUsluge+","+Double.toString(rez.izracunajCenu()))+"\n";
				
				writer.write(str);
			}
			writer.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void upisiSobe() {
		try {
			FileWriter writer = this.getFileWriter("sobe.csv");
			
			writer.write("Sifra,Tip Sobe,Status,Dodaci"+"\n");
			
			for(Soba s:SobaManager.sobe.values()) {
				String dodaci = "";
				
				for(String dodatak:s.getAmenities()) {
					dodaci+=dodatak+"|";
				}
				String str = Integer.toString(s.getSifra())+","+s.getNazivSobe()+","+
							s.getStatus().toString()+","+dodaci+"\n";
				
				writer.write(str);
			}
			writer.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void upisiGoste() {
		try {
			FileWriter writer = this.getFileWriter("gosti.csv");
			
			writer.write("Korisnicko Ime,Ime,Prezime,Pol,Datum Rodjenja,Telefon,Adresa,Lozinka"+"\n");
			
			for(Gost g:GostManager.gosti.values()) {
				String str = g.getKorisnickoIme()+","+g.getIme()+","+g.getPrezime()+","+g.getPol()+","+
							g.getDatumRodjenja().toString()+","+g.getTelefon()+","+g.getAdresa()+","+g.getLozinka()+"\n";
				writer.write(str);
			}
			writer.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void upisiDodeljeneSobe(Sobarica sobarica) {
		try {
			/*String currentDir = System.getProperty("user.dir");
			File parentFolderPath = new File(currentDir).getParentFile();
			String folderPath = parentFolderPath.getPath()+File.separator+"data"+File.separator+"SobeSpremanje";*/
			String folderPath = "../data/SobeSpremanje";
			
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdir();
			}
		
			String filePath = folderPath + File.separator + sobarica.getKorisnickoIme()+"Sobe.csv";
		
			
			FileWriter writer = new FileWriter(filePath, false);
			writer.write("Sobe"+"\n");
			for(Soba s:sobarica.getDodeljeneSobe().values()) {
				writer.write(Integer.toString(s.getSifra())+"\n");
			}
			writer.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	private void upisiSredjeneSobe(Sobarica sobarica) {
		try {
			String folderPath = "../data/SobeSredjene";
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdir();
			}
		
			String filePath = folderPath + File.separator + sobarica.getKorisnickoIme()+"Sobe.csv";
			FileWriter writer = new FileWriter(filePath, false);
			writer.write("Sobe"+"\n");
			for(LocalDate dan:sobarica.getSredjeneSobe()) {
				writer.write(dan.toString()+"\n");
			}
			writer.close();
		}catch(Exception e) {
			
		}
	}
	public void upisiZaposlene() {
		try {
			FileWriter writer = this.getFileWriter("zaposleni.csv");
			
			writer.write("Korisnicko Ime,Ime,Prezime,Pol,Datum Rodjenja,Telefon,Adresa,Lozinka,Tip,Strucna Sprema,Staz,Plata"+"\n");
			
			for(Administrator admin : AdminManager.admini.values()) {
				String str = admin.getKorisnickoIme()+","+admin.getIme()+","+admin.getPrezime()+","+admin.getPol()+","+
							admin.getDatumRodjenja().toString()+","+admin.getTelefon()+","+admin.getAdresa()+","+admin.getLozinka()+","
							+admin.getTip()+","+admin.getStrucnaSprema()+","+Integer.toString(admin.getStaz())+","+Float.toString(admin.primanja())+"\n";
				writer.write(str);
			}
			for(Recepcioner recp : RecepcionerManager.recepcioneri.values()) {
				String str = recp.getKorisnickoIme()+","+recp.getIme()+","+recp.getPrezime()+","+recp.getPol()+","+
						recp.getDatumRodjenja().toString()+","+recp.getTelefon()+","+recp.getAdresa()+","+recp.getLozinka()+","
							+recp.getTip()+","+recp.getStrucnaSprema()+","+Integer.toString(recp.getStaz())+","+Float.toString(recp.primanja())+"\n";
				writer.write(str);
			}
			for(Sobarica sob : SobaricaManager.sobarice.values()) {
				String str = sob.getKorisnickoIme()+","+sob.getIme()+","+sob.getPrezime()+","+sob.getPol()+","+
						sob.getDatumRodjenja().toString()+","+sob.getTelefon()+","+sob.getAdresa()+","+sob.getLozinka()+","
							+sob.getTip()+","+sob.getStrucnaSprema()+","+Integer.toString(sob.getStaz())+","+Float.toString(sob.primanja())+"\n";
				
				this.upisiDodeljeneSobe(sob);
				this.upisiSredjeneSobe(sob);
				
				writer.write(str);
			}
			writer.close();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void upisiCenovnike() {
		try {
			/*String currentDir = System.getProperty("user.dir");
			File parentFolderPath = new File(currentDir).getParentFile();
			String folderPath = parentFolderPath.getPath()+File.separator+"data";*/
			String folderPath = "../data";
			
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdir();
			}
		
			String childFolderPath= folder.getPath();
		
			for(Cenovnik c:CenovnikManager.cenovnici) {
				String aktivan = c.getAktivan() ? "Aktivan" : "Neaktivan";
				
				File cenovnikFolder = new File(childFolderPath+File.separator+aktivan+" Cenovnik "+c.getVaziOd().toString()+" "+c.getVaziDo().toString());
				
				if(!cenovnikFolder.exists()) {
					cenovnikFolder.mkdir();
				}
				
				String sobePath = cenovnikFolder + File.separator + "tipoviSoba.csv";
				String uslugePath = cenovnikFolder + File.separator + "usluge.csv";
				
				FileWriter sobeWriter = new FileWriter(sobePath);
				FileWriter uslugeWriter = new FileWriter(uslugePath);
				
				sobeWriter.write("Tip,Cena"+"\n");
				for(TipSobe ts:c.getTipoviSoba().values()) {
					sobeWriter.write(ts.getNaziv()+","+Double.toString(ts.getCena())+"\n");
				}
				sobeWriter.close();
				
				uslugeWriter.write("Naziv,Cena"+"\n");
				for(DodatneUsluge du:c.getDodatneUsluge().values()) {
					uslugeWriter.write(du.getNaziv()+","+Double.toString(du.getCena())+"\n");
				}
				uslugeWriter.close();
			}
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private BufferedReader getBufferedReader(String naziv) {
		try {
			//String currentDir = System.getProperty("user.dir");
			//File parentFolderPath = new File(currentDir).getParentFile();
			//String folderPath = parentFolderPath.getPath()+File.separator+"../data";
		
			String folderPath = "../data";
			
			File folder = new File(folderPath);
			if(!folder.exists()) {
				folder.mkdir();
				throw new Exception("Podataka nema");
			}
		
			String filePath = folderPath + File.separator + naziv;
			
			FileReader reader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(reader);
			
			return br;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public boolean ucitajRezervacije() {
		try {
			BufferedReader br = this.getBufferedReader("rezervacije.csv");
			
			if (br==null) return false;
			
			br.readLine();
			
			String line;
			while((line=br.readLine())!=null) {
				String korisnickoIme = line.split(",")[0];
				
				StatusRezervacije statusRez = StatusRezervacije.valueOf(line.split(",")[1]); 
				
				TipSobe ts = CenovnikManager.cenovnici.get(0).getTipoviSoba().get(line.split(",")[2]);
				
				Soba s = null;
				if (line.split(",")[3]!="") {
					if(!SobaManager.sobe.containsKey(Integer.parseInt(line.split(",")[3])))
						s = new Soba(Integer.parseInt(line.split(",")[3]));
					else
						s = SobaManager.sobe.get(Integer.parseInt(line.split(",")[3]));
				}
				
				LocalDate datumDolaska = LocalDate.parse(line.split(",")[4]);
				LocalDate datumOdlaska = LocalDate.parse(line.split(",")[5]);
				ArrayList<DodatneUsluge> du = new ArrayList<DodatneUsluge>();
				
				if(line.split(",")[7]!="") {
					String[] dodatne = line.split(",")[7].split("[|]");
					
					for(String str : dodatne) {
						du.add(CenovnikManager.cenovnici.get(0).getDodatneUsluge().get(str));
					}
				
				}
				
				int brOsoba = Integer.parseInt(line.split(",")[6]);
				float cena = Float.parseFloat(line.split(",")[8]);
				
				Rezervacija rez = new Rezervacija(korisnickoIme,statusRez,ts,s, brOsoba, datumDolaska, datumOdlaska, du, cena);
				
				RezervacijaManager.rezervacije.add(rez);
			}
			br.close();
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean ucitajSobe() {
		try {
			BufferedReader br = this.getBufferedReader("Sobe.csv");
			if (br==null) return false;
			br.readLine();
			
			String line;
			while((line=br.readLine())!=null) {
				
				int sifraSobe = Integer.parseInt(line.split(",")[0]);
				
				TipSobe ts = CenovnikManager.cenovnici.get(0).getTipoviSoba().get(line.split(",")[1]);
				
				StatusSobe statusSobe = StatusSobe.valueOf(line.split(",")[2]);
				
				ArrayList<String> amenities = new ArrayList<String>();
				
				if(line.split(",")[3]!=null) {
					for(String str:line.split(",")[3].split("[|]")) {
						amenities.add(str);
					}
				}
				
				Soba soba = new Soba(sifraSobe, ts, statusSobe, amenities);
				SobaManager.sobe.put(sifraSobe, soba);
			}
			br.close();
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean ucitajGoste() {
		try {
			BufferedReader br = this.getBufferedReader("gosti.csv");
			if (br==null) return false;
			br.readLine();
			
			String line;
			while((line=br.readLine())!=null) {
				String korisnickoIme=line.split(",")[0];
				String ime=line.split(",")[1];
				String prezime=line.split(",")[2];
				String pol=line.split(",")[3];
				LocalDate datumRodjenja=LocalDate.parse(line.split(",")[4]);
				String telefon = line.split(",")[5];
				String adresa = line.split(",")[6];
				String lozinka = line.split(",")[7];
				
				Gost gost = new Gost(ime, prezime, pol, datumRodjenja, telefon, adresa, korisnickoIme, lozinka);
				
				GostManager.gosti.put(korisnickoIme, gost);
			}
			br.close();
			
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean ucitajDodeljeneSobe(Sobarica sob) {
		try{
			/*String currentDir = System.getProperty("user.dir");
			File parentFolderPath = new File(currentDir).getParentFile();
			String folderPath = parentFolderPath.getPath()+File.separator+"data"+File.separator+"SobeSpremanje";*/
			String folderPath = "../data"+File.separator+"SobeSpremanje";
			
			File folder = new File(folderPath);
			//System.out.println(folderPath);
			
			if(!folder.exists()) {
				folder.mkdir();
				throw new Exception("Podataka nema");
			}
			
			
			String filePath = folderPath + File.separator + sob.getKorisnickoIme()+"Sobe.csv";
			//System.out.println(filePath);
			//System.out.println(new File(filePath));
			FileReader reader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(reader);
			//BufferedReader br = this.getBufferedReader("Sobe.csv");
			if(br==null) return false;
			
			br.readLine();
			String line;
			while((line=br.readLine())!=null) {
				sob.addSoba(SobaManager.sobe.get(Integer.parseInt(line)));
			}
			br.close();
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public boolean ucitajSredjeneSobe(Sobarica sob) {
		try{
			/*String currentDir = System.getProperty("user.dir");
			File parentFolderPath = new File(currentDir).getParentFile();
			String folderPath = parentFolderPath.getPath()+File.separator+"data"+File.separator+"SobeSpremanje";*/
			String folderPath = "../data"+File.separator+"SobeSredjene";
			
			File folder = new File(folderPath);
			//System.out.println(folderPath);
			
			if(!folder.exists()) {
				folder.mkdir();
				throw new Exception("Podataka nema");
			}
			
			
			String filePath = folderPath + File.separator + sob.getKorisnickoIme()+"Sobe.csv";
			//System.out.println(filePath);
			//System.out.println(new File(filePath));
			FileReader reader = new FileReader(filePath);
			BufferedReader br = new BufferedReader(reader);
			//BufferedReader br = this.getBufferedReader("Sobe.csv");
			if(br==null) return false;
			
			br.readLine();
			String line;
			while((line=br.readLine())!=null) {
				sob.dodajSredjenuSobu(LocalDate.parse(line));
			}
			br.close();
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	public boolean ucitajZaposlene() {
		try {			
			BufferedReader br = this.getBufferedReader("zaposleni.csv");
			if (br==null) return false;
			br.readLine();
			
			String line;
			while((line=br.readLine())!=null) {
				String korisnickoIme=line.split(",")[0];
				String ime=line.split(",")[1];
				String prezime=line.split(",")[2];
				String pol=line.split(",")[3];
				LocalDate datumRodjenja=LocalDate.parse(line.split(",")[4]);
				String telefon = line.split(",")[5];
				String adresa = line.split(",")[6];
				String lozinka = line.split(",")[7];
				
				String tip = line.split(",")[8];
				
				StrucnaSprema strucnaSprema;
				if(line.split(",")[9].equals("Visoka sprema")) {
					strucnaSprema = StrucnaSprema.VISOKA;
				}else if(line.split(",")[9].equals("Srednja sprema")) {
					strucnaSprema = StrucnaSprema.SREDNJA;
				}else if(line.split(",")[9].equals("Osnovna sprema")) {
					strucnaSprema = StrucnaSprema.OSNOVNA;
				}else throw new Exception("Strucna sprema nije registrovana u sistemu");
				
				
				int staz = Integer.parseInt(line.split(",")[10]);
				
				if(tip.equals("Administrator")) {
					Administrator admin = new Administrator(ime,prezime,pol,datumRodjenja,telefon,adresa,korisnickoIme,lozinka,strucnaSprema,staz,tip);
					AdminManager.admini.put(korisnickoIme, admin);
				}else if(tip.equals("Recepcioner")) {
					Recepcioner recp = new Recepcioner(ime,prezime,pol,datumRodjenja,telefon,adresa,korisnickoIme,lozinka,strucnaSprema,staz,tip);
					RecepcionerManager.recepcioneri.put(korisnickoIme, recp);
				}else if(tip.equals("Sobarica")) {
					Sobarica sobarica = new Sobarica(ime,prezime,pol,datumRodjenja,telefon,adresa,korisnickoIme,lozinka,strucnaSprema,staz,tip);
					this.ucitajDodeljeneSobe(sobarica);
					this.ucitajSredjeneSobe(sobarica);
					SobaricaManager.sobarice.put(korisnickoIme, sobarica);
				}else throw new Exception("Tip zaposlenog nije registrovan u sistemu");
			}
			br.close();
			
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	public boolean ucitajCenovnike() {
		try {
			/*String currentDir = System.getProperty("user.dir");
			File parentFolderPath = new File(currentDir).getParentFile();
			String folderPath = parentFolderPath.getPath()+File.separator+"data";*/
			String folderPath = "../data";
			
			File dataFolder = new File(folderPath);
			if(!dataFolder.exists()) {
				dataFolder.mkdir();
				throw new Exception("Podataka nema");
			}
			
			
			File[] cenovniciFolder = dataFolder.listFiles();
			if(cenovniciFolder!=null) {
				
				for(File cenovnik : cenovniciFolder) {
					if(cenovnik.isDirectory()) {
						String nazivCenovnika = cenovnik.getName();
						
						String aktivanCenovnik = nazivCenovnika.split(" ")[0];
						boolean aktivan;
						if(aktivanCenovnik.equals("Aktivan")) {
							aktivan = true;
						}else if(aktivanCenovnik.equals("Neaktivan")) {
							aktivan = false;
						}else if(aktivanCenovnik.equals("SobeSpremanje")) continue;
						else if (aktivanCenovnik.equals("SobeSredjene")) continue;
						else throw new Exception("Fali podatak o aktivnosti cenovnika (pre reci Cenovnik u nazivu folder)");
						
						String pocetak = nazivCenovnika.split(" ")[2];
						String kraj = nazivCenovnika.split(" ")[3];
						
						Cenovnik c = new Cenovnik(pocetak, kraj, aktivan);
						
						FileReader tipoviSobaReader = new FileReader(folderPath+File.separator+cenovnik.getName()+File.separator+"tipoviSoba.csv");
						BufferedReader bf = new BufferedReader(tipoviSobaReader);
						
						String tipSobeLine = bf.readLine();
						while((tipSobeLine=bf.readLine())!=null) {
							String tip = tipSobeLine.split(",")[0];
							double cena = Double.parseDouble(tipSobeLine.split(",")[1]);
							
							TipSobe ts = new TipSobe(tip, cena);
							c.getTipoviSoba().put(tip, ts);
						}
						
						
						FileReader dodatneUslugeReader = new FileReader(folderPath+File.separator+cenovnik.getName()+File.separator+"usluge.csv");
						BufferedReader bf1 = new BufferedReader(dodatneUslugeReader);
						
						String uslugaLine = bf1.readLine();
						while((uslugaLine=bf1.readLine())!=null) {
							String tip = uslugaLine.split(",")[0];
							double cena = Double.parseDouble(uslugaLine.split(",")[1]);
							
							DodatneUsluge du = new DodatneUsluge(tip, cena);
							c.getDodatneUsluge().put(tip, du);
						}
						CenovnikManager.cenovnici.add(c);
						bf.close();
						bf1.close();
					}
				}
				return true;
			}else throw new Exception("Nema cenovnika u bazi");
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
