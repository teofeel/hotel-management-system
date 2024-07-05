package entity;
import java.time.LocalDate;
import enumi.StrucnaSprema;
public class Zaposleni extends Korisnik{
	protected StrucnaSprema strucnaSprema;
	protected int staz;
	protected String tip;
	protected Zaposleni() {super();}
	
	public Zaposleni(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, 
												String korisnickoIme, String lozinka, StrucnaSprema sprema, int staz, String tip) {
		this.ime = ime;
		this.prezime = prezime;
		this.pol=pol;
		this.datumRodjenja = datum;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.strucnaSprema = sprema;
		this.staz = staz;
		this.tip = tip;
	}
	
	public String getTip() {
		return this.tip;
	}
	public StrucnaSprema getStrucnaSprema() {
		return this.strucnaSprema;
	}
	public int getStaz() {
		return this.staz;
	}
	
	public void setTip(String t) {
		this.tip=t;
	}
	public void setStrucnaSprema(StrucnaSprema sp) {
		this.strucnaSprema=sp;
	}
	public void setStaz(int s) {
		this.staz=s;
	}
	
	public float primanja() {
		int osnova = 5;
		float koeficijent_staza = (float)this.staz/10;
		int satnica = 8*5*4;
		if (this.strucnaSprema.equals(StrucnaSprema.VISOKA)){
			return osnova*koeficijent_staza*3*satnica;
		}else if(this.strucnaSprema.equals(StrucnaSprema.SREDNJA)) {
			return osnova*koeficijent_staza*2*satnica;
		}
		return osnova*koeficijent_staza*satnica;
	}
	
	@Override
	public String toString() {
		return super.toString()+" | Radno mesto: "+this.tip+" | Strucna sprema: "+this.strucnaSprema+" | Staz: "+this.staz + " | Primanja: "+this.primanja();
	}
}
