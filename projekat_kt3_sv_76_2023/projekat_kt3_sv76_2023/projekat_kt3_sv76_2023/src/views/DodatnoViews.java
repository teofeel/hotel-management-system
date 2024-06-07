package views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.*;
import enumi.*;
import views.*;
import manager.*;
public class DodatnoViews {
	public static void pregledSlobodnihTipovaSoba(String odDatum, String doDatum, Cenovnik cenovnik) {
		System.out.println("Slobodni datumi za sobe");
		for(TipSobe ts : cenovnik.getTipoviSoba().values()) {
			String slobodniDatumi = "Soba: "+ts.getNaziv()+" je slobodna: ";
			
			ArrayList<LocalDate> dani = new ArrayList<LocalDate>();
			LocalDate pocetak = LocalDate.parse(odDatum);
			LocalDate kraj = LocalDate.parse(doDatum);
			
			int d=0;
			while(pocetak.plusDays(d).isBefore(kraj) || pocetak.plusDays(d).isEqual(kraj)) {
				dani.add(pocetak.plusDays(d));
				d++;
			}
			int sviDaniSlobodni = dani.size();
			for(int i=0;i<DodatnoManager.rezervacije.size();i++) {
				if(!DodatnoManager.rezervacije.get(i).getTipSobe().getNaziv().equals(ts.getNaziv()) || !DodatnoManager.rezervacije.get(i).getStatus().equals(StatusRezervacije.POTVRDJENA)) {
					continue;
				}
				
				LocalDate pocetakRez = DodatnoManager.rezervacije.get(i).getDatumDolaska();
				LocalDate krajRez = DodatnoManager.rezervacije.get(i).getDatumOdlaska();
				
				
				int dd=0;
				while(pocetakRez.plusDays(dd).isBefore(krajRez.minusDays(1)) || pocetakRez.plusDays(dd).isEqual(krajRez.minusDays(1))) {
					dani.remove(pocetakRez.plusDays(dd));
					dd++;
				}
			}
			
			
			if(dani.size()==0) 
				System.out.println("Soba: "+ts.getNaziv()+" nije slobodna");
			else if(dani.size()==sviDaniSlobodni)
				System.out.println("Soba: "+ts.getNaziv()+" je slobodna u celom periodu od "+odDatum+" do "+doDatum);
			
			else {
				int i=0;
				while(i<dani.size()) {
					String pocetniDatum = dani.get(i).toString();
					String krajnjiDatum = dani.get(i).toString();
					
					while(i<dani.size()-1 && dani.get(i).plusDays(1).equals(dani.get(i+1))) {
						krajnjiDatum=dani.get(i+1).toString();
						i++;
					}
					
					String interval="";
					if(pocetniDatum.equals(krajnjiDatum)) 
						interval = pocetniDatum;
					else
						interval = pocetniDatum+" do "+krajnjiDatum;
					
					slobodniDatumi += interval+", ";
					i++;
				}
				System.out.println(slobodniDatumi.substring(0, slobodniDatumi.length()-2));
			}
		}
	}

}
