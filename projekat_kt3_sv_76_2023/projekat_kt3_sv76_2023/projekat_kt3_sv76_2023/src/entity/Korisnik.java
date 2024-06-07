package entity;
import java.time.LocalDate;

public class Korisnik {
	protected String ime;
	protected String prezime;
	protected String pol;
	protected LocalDate datumRodjenja;
	protected String telefon;
	protected String adresa;
	protected String korisnickoIme;
	protected String lozinka;
	
	
	public String getIme() {
		return this.ime;
	}
	public String getPrezime() {
		return this.prezime;
	}
	public String getPol() {
		return this.pol;
	}
	public LocalDate getDatumRodjenja() {
		return this.datumRodjenja;
	}
	public String getTelefon() {
		return this.telefon;
	}
	public String getAdresa() {
		return this.adresa;
	}
	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}
	public String getLozinka() {
		return this.lozinka;
	}
	
	
	public void setIme(String i) {
		this.ime=i;
	}
	public void setPrezime(String p) {
		this.prezime=p;
	}
	public void setPol(String p) {
		this.pol = p;
	}
	public void setDatumRodjenja(LocalDate dr) {
		this.datumRodjenja = dr;
	}
	public void setTelefon(String t) {
		this.telefon = t;
	}
	public void setAdresa(String a) {
		this.adresa=a;
	}
	public void setKorisnickoIme(String ki) {
		this.korisnickoIme = ki;
	}
	public void setLozinka(String l) {
		this.lozinka=l;
	}
	
	public boolean checkLogin(String korisnickoIme, String lozinka) {
		return (this.korisnickoIme.equals(korisnickoIme) && this.lozinka.equals(lozinka));
	}
	
	@Override
	public String toString() {
		return "Ime: "+this.ime+" | Prezime: "+this.prezime+" | Korisnicko ime: "+this.korisnickoIme+" | Pol: "+this.pol+" | Datum rodjenja: "+this.datumRodjenja.toString()+" | Telefon: "+this.telefon+" | Adresa: "+this.adresa;
	}
}
