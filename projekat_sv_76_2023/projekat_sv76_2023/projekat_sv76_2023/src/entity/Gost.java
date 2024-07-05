package entity;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.*;
import enumi.StatusRezervacije;
public class Gost extends Korisnik{
	
	public Gost(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, String email, int brojPasosa) {
		this.ime = ime;
		this.prezime = prezime;
		this.pol=pol;
		this.datumRodjenja = datum;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = email;
		this.lozinka = Integer.toString(brojPasosa);
	}
	
	public Gost(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, String email, String brojPasosa) {
		this.ime = ime;
		this.prezime = prezime;
		this.pol=pol;
		this.datumRodjenja = datum;
		this.telefon = telefon;
		this.adresa = adresa;
		this.korisnickoIme = email;
		this.lozinka = brojPasosa;
	}
	
}
