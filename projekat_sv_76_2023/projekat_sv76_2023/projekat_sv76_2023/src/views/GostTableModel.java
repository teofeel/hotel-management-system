package views;
import javax.swing.table.AbstractTableModel;

import manager.*;
import entity.*;
import java.util.*;
public class GostTableModel extends AbstractTableModel{
	private String[] columnNames = {"Korisnicko Ime", "Ime", "Prezime", "Pol", "Datum Rodjenja","Telefon"};
	private ArrayList<Gost> gosti;
	
	public GostTableModel() {
		this.gosti = new ArrayList<Gost>(GostManager.gosti.values());
	}
	
	public Gost getGost(int index) {
		return this.gosti.get(index);
	}
	public void removeGost(int index) {
		this.gosti.remove(index);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return GostManager.gosti == null ? 0:GostManager.gosti.size();
	}
	
	@Override 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Gost gost = this.gosti.get(rowIndex);
		
		switch(columnIndex) {
		case 0 :
			return gost.getKorisnickoIme();
		case 1:
			return gost.getIme();
		case 2:
			return gost.getPrezime();
		case 3:
			return gost.getPol();
		case 4:
			return gost.getDatumRodjenja();
		case 5:
			return gost.getTelefon();
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
