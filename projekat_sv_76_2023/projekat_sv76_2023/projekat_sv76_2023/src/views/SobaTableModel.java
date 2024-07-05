package views;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import manager.SobaManager;
import entity.*;

public class SobaTableModel extends AbstractTableModel{
	private String[] columnNames = {"Sifra", "Tip Sobe", "Status", "Amenities"};
	private ArrayList<Soba> sobe;
	public SobaTableModel() {
		sobe = new ArrayList<Soba>(SobaManager.sobe.values());
	}
	
	public Soba getSobe(int index) {
		return sobe.get(index);
	}
	public void removeSoba(int index) {
		sobe.remove(index);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		return SobaManager.sobe == null ? 0 : SobaManager.sobe.size();
	}
	
	@Override 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Soba soba = sobe.get(rowIndex);
		
		switch(columnIndex) {
		case 0 :
			return soba.getSifra();
		case 1:
			return soba.getNazivSobe();
		case 2:
			return soba.getStatus().toString();
		case 3:
			return soba.getAmenities();
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
