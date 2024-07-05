package entity;
import java.io.File;
import enumi.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

import entity.*;

import java.util.ArrayList;

public class Recepcioner extends Zaposleni{
	
	public Recepcioner(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, 
			String korisnickoIme, String lozinka, StrucnaSprema sprema, int staz, String tip) {
		super(ime, prezime, pol, datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz, tip);
	}
	
	
}
