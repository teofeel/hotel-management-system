package entity;

public class TipSobe {
	private String naziv;
	private double cena;
	
	public TipSobe(String naziv, double cena) {
		this.naziv = naziv;
		this.cena = cena;
	}
	
	public TipSobe(TipSobe ts) {
		this(ts.getNaziv(), 0);
	}
	
	public TipSobe(String naziv) {
		this.naziv = naziv;
		this.cena = 0;
	}
	
	public TipSobe() {
		this.naziv = "";
		this.cena = 0;
	}
	
	public String getNaziv() {
		return this.naziv;
	}
	public double getCena() {
		return this.cena;
	}
	
	public void setNaziv(String n) {
		this.naziv = n;
	}
	public void setCena(double c) {
		this.cena = c;
	}
	
	@Override
	public String toString() {
		return this.naziv+" | Cena: "+this.cena;
	}
}
