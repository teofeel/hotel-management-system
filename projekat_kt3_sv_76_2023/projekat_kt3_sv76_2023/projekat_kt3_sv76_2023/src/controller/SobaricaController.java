package controller;
import entity.*;
public class SobaricaController {
private String korisnickoIme;
	
	public SobaricaController(String ki) {
		this.korisnickoIme = ki;
	}
	
	public String getKorisnickoIme() {
		return this.korisnickoIme;
	}
	public void setKorisnickoIme(String ki) {
		this.korisnickoIme = ki;
	}
}
