package views;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import manager.*;
import entity.*;
import java.util.*;
public class ZaposleniTableModel extends AbstractTableModel{
	private String[] columnNames = {"Korisnicko Ime", "Ime", "Prezime", "Tip", "Pol","Datum Rodjenja", "Strucna Sprema", "Staz", "Primanja"};
	private ArrayList<Zaposleni> zaposleni;
	public ZaposleniTableModel() {
		HashMap<String, Zaposleni> zaposleni = new HashMap<>();
		zaposleni.putAll(AdminManager.admini);
		zaposleni.putAll(RecepcionerManager.recepcioneri);
		zaposleni.putAll(SobaricaManager.sobarice);
		
		this.zaposleni = new ArrayList<Zaposleni>(zaposleni.values());
	}
	public Zaposleni getZaposleni(int index) {
		return zaposleni.get(index);
	}
	public void removeZaposleni(int index) {
		zaposleni.remove(index);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	@Override
	public int getRowCount() {
		int brojAdmina = AdminManager.admini.size();
		int brojRecepcionera = RecepcionerManager.recepcioneri.size();
		int brojSobarica = SobaricaManager.sobarice.size();
		return brojAdmina+brojRecepcionera+brojSobarica;
	}
	
	@Override 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Zaposleni zap = this.zaposleni.get(rowIndex);
		
		
		switch(columnIndex) {
		case 0 :
			return zap.getKorisnickoIme();
		case 1:
			return zap.getIme();
		case 2:
			return zap.getPrezime();
		case 3:
			return zap.getTip();
		case 4:
			return zap.getPol();
		case 5:
			return zap.getDatumRodjenja();
		case 6:
			return zap.getStrucnaSprema();
		case 7:
			return zap.getStaz();
		case 8:
			return zap.primanja();
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
