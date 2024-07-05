package entity;

public class DodatneUsluge {
	private String naziv;
	private double cena;
	
	public DodatneUsluge(String naziv, double cena) {
		this.naziv = naziv;
		this.cena = cena;
	}
	public DodatneUsluge(DodatneUsluge du) {
		this(du.getNaziv(), 0);
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
		return "Naziv: "+this.naziv+" | Cena: "+this.cena;
	}
}
