package entity;

import java.time.LocalDate;
import enumi.StrucnaSprema;
import java.util.*;
public class Sobarica extends Zaposleni{
	private HashMap<Integer, Soba> dodeljeneSobe;
	private ArrayList<LocalDate> sredjeneSobe;
	
	public Sobarica() {super();}
	public Sobarica(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, 
			String korisnickoIme, String lozinka, StrucnaSprema sprema, int staz, String tip) {
		
		super(ime, prezime, pol, datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz, tip);
		
		this.dodeljeneSobe = new HashMap<Integer, Soba>();
		this.sredjeneSobe = new ArrayList<LocalDate>();
	}
	

	public void dodajSredjenuSobu(LocalDate dan) {
		this.sredjeneSobe.add(dan);
	}
	public void addSoba(Soba s) {
		this.dodeljeneSobe.put(s.getSifra(), s);
	}
	public boolean removeSoba(Soba s) {
		try {
			this.dodeljeneSobe.remove(s.getSifra());
			this.dodajSredjenuSobu(LocalDate.now());
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	
	public ArrayList<LocalDate> getSredjeneSobe() {
		return this.sredjeneSobe;
	}
	public HashMap<Integer, Soba> getDodeljeneSobe() {
		return this.dodeljeneSobe;
	}
	
}
