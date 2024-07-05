package entity;
import java.util.HashMap;

import entity.*;

import java.time.LocalDate;
import java.io.*;
import java.util.*;

import enumi.*;
public class Administrator extends Zaposleni{
	public Administrator() {super();}
	public Administrator(String ime, String prezime, String pol,LocalDate datum, String telefon, String adresa, 
												String korisnickoIme, String lozinka, StrucnaSprema sprema, int staz, String tip) {
		super(ime, prezime, pol, datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz, tip);
	}
	

}