package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.DodatneUsluge;
import entity.Gost;
import entity.Rezervacija;
import entity.Soba;
import enumi.StatusRezervacije;
import enumi.StatusSobe;

public class RecepcionerController {
	
	private String korisnickoIme;
	
	public RecepcionerController(String ki) {
		this.korisnickoIme = ki;
	}
	
	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}
	public void setKorisnickoIme(String ki) {
		this.korisnickoIme = ki;
	}
}
