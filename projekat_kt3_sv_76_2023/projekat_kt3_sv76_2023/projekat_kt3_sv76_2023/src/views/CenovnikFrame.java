package views;
import java.awt.event.*;
import java.time.*;

import javax.swing.*;

import java.awt.*;
import java.util.*;

import com.toedter.calendar.JDateChooser;

import manager.*;

import entity.*;
import enumi.*;

public class CenovnikFrame extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar;
	
	private JButton sobeButton;
	private JButton uslugeButton;
	
	private Cenovnik cenovnik;
	
	public CenovnikFrame(Cenovnik cenovnik) {
		this.cenovnik = cenovnik;
		
		setTitle("Cenovnika");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
        sobeButton = new JButton("Sobe");
        sobeButton.addActionListener(new NavbarButtonListener("Sobe"));
        
        uslugeButton = new JButton("Usluge");
        uslugeButton.addActionListener(new NavbarButtonListener("Usluge"));
        
        navbar.add(sobeButton);
        navbar.add(uslugeButton);
        
        cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		
		contentPanel.add(this.sobePanel(), "Sobe");
		contentPanel.add(this.uslugePanel(), "Usluge");
		
		add(navbar,BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        setVisible(true);
	}
	
	private JPanel sobePanel() {
		JPanel sobePanel = new JPanel();
		
		if(cenovnik.getTipoviSoba().size()==0) {
			return sobePanel;
		}
		
		JPanel sobeListaPanel = new JPanel();
		
		for(TipSobe ts:cenovnik.getTipoviSoba().values()) {
			JLabel nazivSobe = new JLabel(ts.getNaziv());
			
			JTextField cenaSobe = new JTextField(5);
			cenaSobe.setText(Double.toString(ts.getCena()));
			cenaSobe.setName(ts.getNaziv()+"cenaField");
			
			JButton izmeniCenuButton = new JButton("Izmeni cenu");
			izmeniCenuButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String novaCena = cenaSobe.getText();
					String poruka = CenovnikManager.getInstance().izmeniCenuSobe(cenovnik, nazivSobe.getText(), novaCena);
					
					JOptionPane.showMessageDialog(sobeListaPanel, poruka);
				}
			});
			
			sobeListaPanel.add(nazivSobe);
			sobeListaPanel.add(cenaSobe);
			sobeListaPanel.add(izmeniCenuButton);
		}
		
		sobePanel.add(sobeListaPanel);
		return sobePanel;
	}
	
	private JPanel uslugePanel() {
		JPanel uslugePanel = new JPanel();
		
		if(cenovnik.getDodatneUsluge().size()==0) {
			return uslugePanel;
		}
		
		JPanel uslugeListaPanel = new JPanel();
		
		for(DodatneUsluge du:cenovnik.getDodatneUsluge().values()) {
			JLabel nazivUsluge = new JLabel(du.getNaziv());
			
			JTextField cenaUsluge = new JTextField(5);
			cenaUsluge.setText(Double.toString(du.getCena()));
			cenaUsluge.setName(du.getNaziv()+"cenaField");
			
			JButton izmeniCenuButton = new JButton("Izmeni cenu");
			izmeniCenuButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String novaCena = cenaUsluge.getText();
					String poruka = CenovnikManager.getInstance().izmeniCenuUsluge(cenovnik, nazivUsluge.getText(), novaCena);
					
					JOptionPane.showMessageDialog(uslugeListaPanel, poruka);
				}
			});
			
			uslugeListaPanel.add(nazivUsluge);
			uslugeListaPanel.add(cenaUsluge);
			uslugeListaPanel.add(izmeniCenuButton);
		}
		
		uslugePanel.add(uslugeListaPanel);
		return uslugePanel;
	}
	
	private class NavbarButtonListener implements ActionListener {
		private String viewName;
		public NavbarButtonListener(String viewName) {
			this.viewName = viewName;
		}
		@Override 
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(contentPanel, viewName);
		}
		
	}
}
