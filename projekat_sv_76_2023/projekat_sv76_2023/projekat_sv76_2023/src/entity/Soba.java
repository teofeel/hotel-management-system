package entity;
import java.util.ArrayList;
import enumi.StatusSobe;
public class Soba {
	private int sifraSobe;
	private TipSobe tipSobe;
	private StatusSobe status;
	private ArrayList<String> amenities = new ArrayList<String>();

	public Soba(int sifra,TipSobe tipSobe, StatusSobe status, ArrayList<String> usluge) {
		this.sifraSobe = sifra;
		this.tipSobe = new TipSobe(tipSobe);
		this.status = status;
		
		for(String u : usluge) {
			this.amenities.add(u);
		}
	}
	
	public Soba(int sifra, String tip) {
		this.sifraSobe = sifra;
		this.tipSobe = new TipSobe(tip);
		this.status = StatusSobe.SLOBODNA;
		amenities.add("Standardno");
	}
	
	public Soba(int sifra) {
		this.sifraSobe = sifra;
		this.tipSobe = new TipSobe();
		this.status = StatusSobe.SLOBODNA;
	}
	
	public int getSifra() {
		return this.sifraSobe;
	}
	public String getNazivSobe() {
		return this.tipSobe.getNaziv();
	}
	public double getCenaSobe() {
		return this.tipSobe.getCena();
	}
	public StatusSobe getStatus() {
		return this.status;
	}
	public ArrayList<String> getAmenities() {
		
		return this.amenities;
	}
	
	
	public void setSifra(int s) {
		this.sifraSobe = s;
	}
	public void setStatus(StatusSobe s) {
		this.status = s;
	}
	public void setAmenities(String a) {
		this.amenities.add(a);
	}
	public void setTipSobe(TipSobe ts) {
		this.tipSobe = ts;
	}
	
	@Override
	public String toString() {
		String dodatne = "";
		for(String a : this.amenities) {
			dodatne+=a+" ";
		}

		return "Sifra sobe: "+this.sifraSobe+" | Tip Sobe: "+this.tipSobe.toString()+" | Status: "+this.status+" | Pogodnosti: "+dodatne;
	}
	
}

