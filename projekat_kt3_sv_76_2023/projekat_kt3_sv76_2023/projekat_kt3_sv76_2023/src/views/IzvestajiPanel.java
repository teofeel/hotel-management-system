package views;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.*;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import entity.*;
import enumi.StrucnaSprema;
import enumi.TipSobeEnum;
import manager.*;
import controller.*;

public class IzvestajiPanel extends JPanel{
	private JPanel mainPanel;
	private JDateChooser pocetakDatumPicker;
	private JDateChooser krajDatumPicker;
	private JButton getIzvestajiButton;
	private JButton prihodPoSobiButton;
	private JButton opterecenostSobaricaButton;
	private JButton kreiraneRezButton;
	
	private ArrayList<Float> prihodiRashodi;
	private HashMap<String, Integer> sobariceSredjeneSobe;
	private int brojPotvrdjenihRez;	
	private ArrayList<Integer> obradjeneRezervacije;
	
	private JLabel prihodiLabel;
	private JLabel rashodiLabel;
	
	private JTable sobaricaSpremljeneTable;
	private JTable sobeTable;
	
	private JLabel potvrdjeneRezLabel;
	private JLabel odbijeneRezLabel;
	private JLabel otkazaneRezLabel;
	
	public IzvestajiPanel() { 
		setLayout(new BorderLayout());

        
        add(this.fieldPanel(), BorderLayout.PAGE_START);
        add(this.izvestajiPanel());
        //add(this.drugiIzvestajiPanel());
	}
	
	private JPanel fieldPanel() {
		JPanel fieldPanel = new JPanel();
        
		this.pocetakDatumPicker = new JDateChooser();
		this.pocetakDatumPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		this.krajDatumPicker = new JDateChooser();
		this.krajDatumPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		this.getIzvestajiButton = new JButton("Izvestaji");
		this.prihodPoSobiButton = new JButton("Prihodi po sobi");
		this.opterecenostSobaricaButton = new JButton("Opterecenost sobarica");
		this.kreiraneRezButton = new JButton("Kreirane rezervacije");
		
		getIzvestajiButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				String pocetak = LocalDate.ofInstant(pocetakDatumPicker.getDate().toInstant(), ZoneId.systemDefault()).toString();
				String kraj = LocalDate.ofInstant(krajDatumPicker.getDate().toInstant(), ZoneId.systemDefault()).toString();
				
				setIzvestaji(pocetak, kraj);
			}
		});
        
		prihodPoSobiButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				new PrihodiSobaChart();
			}
		});
		
		opterecenostSobaricaButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				new OpterecenostSobaricaChart();
			}
		});
		
		kreiraneRezButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
        fieldPanel.add(this.pocetakDatumPicker);
        fieldPanel.add(this.krajDatumPicker);
        fieldPanel.add(this.getIzvestajiButton);
        fieldPanel.add(this.prihodPoSobiButton);
        fieldPanel.add(this.opterecenostSobaricaButton);
        fieldPanel.add(this.kreiraneRezButton);
        
        return fieldPanel;
	}
	
	private void setIzvestaji(String pocetak, String kraj) {
		prihodiRashodi = IzvestajiManager.getInstance().prihodiRashodi(pocetak, kraj);
		brojPotvrdjenihRez = IzvestajiManager.getInstance().potvrdjeneRezervacije(pocetak, kraj);
		obradjeneRezervacije = IzvestajiManager.getInstance().obradjeneRezervacije(pocetak, kraj);
		
		prihodiLabel.setText("Prihodi: " + Float.toString(prihodiRashodi.get(0)));
		rashodiLabel.setText("Rashodi: " + Float.toString(prihodiRashodi.get(1)));
		potvrdjeneRezLabel.setText("Potvrdjene: "+Integer.toString(obradjeneRezervacije.get(0)));
		this.odbijeneRezLabel.setText("Odbijene: "+Integer.toString(obradjeneRezervacije.get(2)));
		this.otkazaneRezLabel.setText("Otkazane: "+Integer.toString(obradjeneRezervacije.get(1)));
		
		setSredjeneTable(pocetak,kraj);
		setSobeTable(pocetak, kraj);
	}
	private void setSredjeneTable(String pocetak, String kraj) {
		sobariceSredjeneSobe = IzvestajiManager.getInstance().sredjeneSobe(pocetak, kraj);
		
		String[] columnNamesSpremljene = {"Sobarica", "Broj sredjenih"};
		Object[][] dataSpremljene = new Object[sobariceSredjeneSobe.size()][2];
		int index=0;
		for (Map.Entry<String, Integer> entry : sobariceSredjeneSobe.entrySet()) {
			dataSpremljene[index][0] = entry.getKey();
			dataSpremljene[index][1] = entry.getValue();
            index++;
        }
		sobaricaSpremljeneTable.setModel(new DefaultTableModel(dataSpremljene, columnNamesSpremljene));
	}
	private void setSobeTable(String pocetak, String kraj) {
		HashMap<Integer, Float> sobePrihodi = new HashMap<Integer, Float>();
		sobePrihodi = IzvestajiManager.getInstance().sobaPrihodi(pocetak, kraj);
		
		HashMap<Integer, Integer> sobeNocenja = new HashMap<Integer, Integer>();
		sobeNocenja = IzvestajiManager.getInstance().sobaNocenja(pocetak, kraj);
		
		System.out.println(sobeNocenja);
		
		String[] columnNamesSobe = {"Sifra Sobe", "Nocenja", "Prihodi"};
		Object[][] data = new Object[sobeNocenja.size()][3];
		
		int index=0;
		for(Map.Entry<Integer, Float> entry:sobePrihodi.entrySet()) {
			Integer sifraSobe = entry.getKey();
            Float prihodi = entry.getValue();
            Integer nocenja = sobeNocenja.get(sifraSobe);

            data[index][0] = sifraSobe;
            data[index][1] = nocenja != null ? nocenja : 0;
            data[index][2] = prihodi != null ? prihodi : 0.0f;
            index++;
		}
		
		this.sobeTable.setModel(new DefaultTableModel(data, columnNamesSobe));
	}
	
	
	private JPanel izvestajiPanel() {
		JPanel izvestajiPanel = new JPanel(new BorderLayout());

        this.prihodiLabel = new JLabel("Prihodi: 0");
        this.rashodiLabel = new JLabel("Rashodi: 0");
        this.potvrdjeneRezLabel = new JLabel("Potvrdjene: 0");
        this.odbijeneRezLabel = new JLabel("Odbijene: 0");
        this.otkazaneRezLabel = new JLabel("Otkazane: 0");

        this.sobaricaSpremljeneTable = new JTable();
        JScrollPane scrollPanel1 = new JScrollPane(sobaricaSpremljeneTable);

        this.sobeTable = new JTable();
        JScrollPane scrollPanel2 = new JScrollPane(sobeTable);

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelPanel.add(this.prihodiLabel);
        labelPanel.add(this.rashodiLabel);
        labelPanel.add(this.potvrdjeneRezLabel);
        labelPanel.add(this.odbijeneRezLabel);
        labelPanel.add(this.otkazaneRezLabel);

        JPanel tableContainerPanel = new JPanel();
        tableContainerPanel.setLayout(new BoxLayout(tableContainerPanel, BoxLayout.Y_AXIS));
        tableContainerPanel.add(scrollPanel1);
        tableContainerPanel.add(Box.createVerticalStrut(10)); 
        tableContainerPanel.add(scrollPanel2);

        izvestajiPanel.add(labelPanel, BorderLayout.PAGE_START);
        izvestajiPanel.add(tableContainerPanel, BorderLayout.CENTER);

        return izvestajiPanel;
	}
}
