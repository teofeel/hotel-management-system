package entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import enumi.StatusRezervacije;
import manager.CenovnikManager;
public class Rezervacija {
	private String emailKorisnika;
	private StatusRezervacije status;
	private TipSobe tipSobe;
	private Soba soba;
	private LocalDate datumDolaska;
	private LocalDate datumOdlaska;
	private ArrayList<DodatneUsluge> usluge = new ArrayList<DodatneUsluge>();
	private int brOsoba;
	private float cena;
	private boolean checkedIn;
	
	public Rezervacija(String emailKorisnika, TipSobe tipSobe, Soba soba, int brOsoba,LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<DodatneUsluge> usluga) {
		
		this.emailKorisnika = emailKorisnika;
		this.status = StatusRezervacije.valueOf("NA_CEKANJU");
		this.tipSobe = tipSobe;
		this.soba = soba;
		this.brOsoba = brOsoba;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		if(usluga != null) {
			for(int i=0;i<usluga.size();i++) {
				this.usluge.add(usluga.get(i));
			
			}
		}
		this.cena = this.izracunajCenu();
		this.checkedIn = false;
	}
	
	public Rezervacija(String emailKorisnika, StatusRezervacije status, TipSobe tipSobe, Soba soba, int brOsoba,LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<DodatneUsluge> usluga) {
		this.emailKorisnika = emailKorisnika;
		this.status = status;
		this.tipSobe = tipSobe;
		this.soba = soba;
		this.brOsoba = brOsoba;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		if(usluga != null) {
			for(int i=0;i<usluga.size();i++) {
				this.usluge.add(usluga.get(i));
			
			}
		}
		this.cena = this.izracunajCenu();
		this.checkedIn = false;
	}
	
	public Rezervacija(String emailKorisnika, StatusRezervacije status, TipSobe tipSobe, Soba soba, int brOsoba,LocalDate datumDolaska, LocalDate datumOdlaska, ArrayList<DodatneUsluge> usluga, float cena) {
		this.emailKorisnika = emailKorisnika;
		this.status = status;
		this.tipSobe = tipSobe;
		this.soba = soba;
		this.brOsoba = brOsoba;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		if(usluga != null) {
			for(int i=0;i<usluga.size();i++) {
				this.usluge.add(usluga.get(i));
			
			}
		}
		this.cena = cena;
		this.checkedIn = false;
	}
	
	public boolean getCheckedIn() {
		return this.checkedIn;
	}
	public String getGost() {
		return this.emailKorisnika;
	}
	public StatusRezervacije getStatus() {
		return this.status;
	}
	public TipSobe getTipSobe() {
		return this.tipSobe;
	}
	public Soba getSoba() {
		return this.soba;
	}
	public LocalDate getDatumDolaska() {
		return this.datumDolaska;
	}
	public LocalDate getDatumOdlaska() {
		return this.datumOdlaska;
	}
	public int getBrOsoba() {
		return this.brOsoba;
	}
	public ArrayList<DodatneUsluge> getUsluge(){
		return this.usluge;
	}
	public float getCena() {
		return this.cena;
	}
	
	public void setGost(String e) {
		this.emailKorisnika = e;
	}
	public void setStatus(StatusRezervacije s) {
		this.status=s;
	}
	public void setTipSobe(TipSobe ts) {
		this.tipSobe=ts;
	}
	public void setSoba(Soba s) {
		this.soba=s;
	}
	public void setDatumDolaska(String d) {
		this.datumDolaska = LocalDate.parse(d);
	}
	public void setDatumOdlaska(String o) {
		this.datumOdlaska=LocalDate.parse(o);
	}
	public void setBrOsoba(int br) {
		this.brOsoba=br;
	}
	public void setCheckedIn(boolean check) {
		this.checkedIn = check;
	}
	public void dodajUslugu(DodatneUsluge d) {
		for(int i=0;i<this.usluge.size();i++) {
			if(this.usluge.get(i).getNaziv().equals(d.getNaziv())) {
				return;
			}
		}
		this.usluge.add(d);
		this.cena = this.izracunajCenu();	
	}
	
	public void izbaciUslugu(String naziv) {
		for(int i=0;i<this.usluge.size();i++) {
			if(this.usluge.get(i).getNaziv().equals(naziv)) {
				this.usluge.remove(i);
				this.cena = this.izracunajCenu();
				return;
			}
		}
	}
	
	public float izracunajCenu() {
		
		if(CenovnikManager.cenovnici.size()==0) return 0;
		
		ArrayList<LocalDate> daniRezervacije = new ArrayList<LocalDate>();
		
		LocalDate pocetakRez = this.datumDolaska;
		
		int plusDani = 0;
		while (pocetakRez.plusDays(plusDani).isBefore(this.datumOdlaska) || pocetakRez.plusDays(plusDani).equals(this.datumOdlaska.minusDays(1))) {
			daniRezervacije.add(pocetakRez.plusDays(plusDani));
			plusDani++;
		}
		
		
		if(CenovnikManager.cenovnici.size()==0) return 0;
		
		double ukupnaCena = 0;
		for(LocalDate dan : daniRezervacije) {
			double najvecaCenaSoba = CenovnikManager.cenovnici.get(0).getTipoviSoba().containsKey(this.tipSobe.getNaziv()) ? CenovnikManager.cenovnici.get(0).getTipoviSoba().get(this.tipSobe.getNaziv()).getCena():0;
			for(Cenovnik c:CenovnikManager.cenovnici) {
				if(c.getTipoviSoba().get(this.tipSobe.getNaziv()).getCena() > najvecaCenaSoba && dan.isAfter(c.getVaziOd()) && dan.isBefore(c.getVaziDo()) && c.getAktivan())
					najvecaCenaSoba = c.getTipoviSoba().containsKey(this.tipSobe.getNaziv()) ? c.getTipoviSoba().get(this.tipSobe.getNaziv()).getCena() : 0;
			}
			ukupnaCena += najvecaCenaSoba;
			
			for(DodatneUsluge du : this.usluge) {
				double najvecaCenaUsluge = CenovnikManager.cenovnici.get(0).getDodatneUsluge().containsKey(du.getNaziv()) ? CenovnikManager.cenovnici.get(0).getDodatneUsluge().get(du.getNaziv()).getCena():0;
				
				for (Cenovnik c:CenovnikManager.cenovnici){
					if(c.getDodatneUsluge().get(du.getNaziv()).getCena() > najvecaCenaSoba && dan.isAfter(c.getVaziOd()) && dan.isBefore(c.getVaziDo()) && c.getAktivan())
						najvecaCenaUsluge = c.getDodatneUsluge().containsKey(du.getNaziv()) ? c.getDodatneUsluge().get(du.getNaziv()).getCena():0;
				}
				ukupnaCena+=najvecaCenaUsluge;
			}
			
		}
		
		return (float)ukupnaCena;
	}
	
	@Override
	public String toString() {
		String usluge = "";
		
		for(int i=0;i<this.usluge.size();i++) {
			if(i==this.usluge.size()-1) {
				usluge+=this.usluge.get(i).getNaziv();
				break;
			}
			usluge+=this.usluge.get(i).getNaziv()+", ";
		}
		return "Email: "+this.emailKorisnika+" | Status: "+this.status+" | Tip sobe: "+this.tipSobe.getNaziv()+
				" | Datum dolaska: "+this.datumDolaska.toString()+" | Datum odlaska: "+this.datumOdlaska.toString()
				+ " | Broj osoba: "+this.brOsoba+" | Usluge: "+usluge + " | Cena: "+this.izracunajCenu();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if(!(o instanceof Rezervacija))
			return false;
		
		Rezervacija r = (Rezervacija) o;
		
		return r.getGost().equals(this.emailKorisnika) && r.getDatumDolaska().equals(this.datumDolaska) && r.getDatumOdlaska().equals(this.datumOdlaska);
	}
	
}
