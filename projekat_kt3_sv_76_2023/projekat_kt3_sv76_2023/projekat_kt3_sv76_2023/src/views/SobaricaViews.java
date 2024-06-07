package views;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.*;
import manager.*;
import enumi.*;

import javax.swing.*;

import controller.*;

import java.awt.*;
import java.awt.event.*;

public class SobaricaViews extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar;
	private JButton pregledSobaButton;
	private JButton logout;
	private SobaricaController sobaricaController;
	private String korisnickoIme;
	
	public SobaricaViews(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
		//sobaricaController = new SobaricaController(korisnickoIme);
		
		setTitle("Sobarica Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
        pregledSobaButton = new JButton("Pregled dodeljenih soba");
        pregledSobaButton.addActionListener(new NavbarButtonListener("PregledSobaPanel"));
        
        
        logout = new JButton("Logout");
        logout.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e) {
				FileManager.getInstance().upisiPodatke();
				LoginView loginWindow = new LoginView();
				dispose();
			}
		});
        
        navbar.add(pregledSobaButton);
		navbar.add(logout);
		
		cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);

		
		contentPanel.add(this.pregledSobaPanel(),"PregledSobaPanel");
		
		add(navbar,BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
        
	}
	
	private JPanel pregledSobaPanel() {
		JPanel pregledSobaPanel = new JPanel();
		pregledSobaPanel.add(new JLabel("Pregled soba"));
		
		HashMap<Integer, Soba> sobe = SobaricaManager.sobarice.get(this.korisnickoIme).getDodeljeneSobe();
		
		
		JPanel sobePanel = new JPanel(new GridLayout(sobe.values().size(),2));
		
		for(Soba soba:sobe.values()) {
			JLabel sifraSobe = new JLabel(Integer.toString(soba.getSifra()));
			JButton srediSobu = new JButton("Sredjena soba");
			
			sobePanel.add(sifraSobe);
			sobePanel.add(srediSobu);
			
			srediSobu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String poruka = SobaricaManager.getInstance().sredjenaSoba(soba, SobaricaManager.sobarice.get(korisnickoIme));
					
					JOptionPane.showMessageDialog(pregledSobaPanel, poruka);
					
					sobePanel.revalidate(); 
					sobePanel.repaint();
				}
			});
		}
		
		pregledSobaPanel.add(sobePanel);
		return pregledSobaPanel;
	}
	
	private class NavbarButtonListener implements ActionListener {
		private String viewName;
		public NavbarButtonListener(String viewName) {
			this.viewName = viewName;
		}
		@Override 
		public void actionPerformed(ActionEvent e) {
			if(viewName.equals("PregledSobaPanel")) {
				contentPanel.remove(pregledSobaPanel());
				contentPanel.add(pregledSobaPanel(),"PregledSobaPanel");	
			}
			cardLayout.show(contentPanel, viewName);
		}
		
	}
}
