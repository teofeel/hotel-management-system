package views;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import manager.RezervacijaManager;
import entity.*;
public class RezervacijaTableModel extends AbstractTableModel {
	private String[] columnNames = {"Gost", "Tip Sobe", "Soba", "Datum Dolaska", "Datum Odlaska", "Cena", "Status", "Dodatne Usluge"};
	
	public RezervacijaTableModel() {
		
	}
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return RezervacijaManager.rezervacije == null ? 0 : RezervacijaManager.rezervacije.size();
	}
	
	
	@Override 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Rezervacija rez = RezervacijaManager.rezervacije.get(rowIndex);
		
		switch(columnIndex) {
		case 0 :
			return rez.getGost();
		case 1:
			return rez.getTipSobe().getNaziv();
		case 2:
			return rez.getSoba() == null ? "":rez.getSoba().getSifra();
		case 3:
			return rez.getDatumDolaska();
		case 4:
			return rez.getDatumOdlaska();
		case 5:
			return rez.getCena();
		case 6:
			return rez.getStatus();
		case 7:
			String usl = "";
			for(DodatneUsluge du:rez.getUsluge()) {
				usl+=du.getNaziv()+" ";
			}
			return usl;
		default:
			return null;
		}
	}
	@Override
	public String getColumnName(int column) {
		return this.columnNames[column];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return this.getValueAt(0, columnIndex).getClass();
	}
	@Override
	public boolean isCellEditable(int row, int column) {
	   return false;
	}
}
