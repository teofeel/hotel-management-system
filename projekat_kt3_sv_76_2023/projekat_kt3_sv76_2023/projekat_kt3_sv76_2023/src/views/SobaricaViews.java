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
		JPanel pregledSobaPanel = new JPanel(new BorderLayout());
	    pregledSobaPanel.add(new JLabel("Pregled soba"), BorderLayout.NORTH);

	    HashMap<Integer, Soba> sobe = SobaricaManager.sobarice.get(korisnickoIme).getDodeljeneSobe();
	    
	    JPanel sobePanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    int row = 0;
	    for (Soba soba : sobe.values()) {
	        gbc.gridy = row++;
	        gbc.gridx = 0;
	        sobePanel.add(new JLabel(Integer.toString(soba.getSifra())), gbc);

	        gbc.gridx = 1;
	        JButton srediSobuButton = new JButton("Sredjena soba");
	        sobePanel.add(srediSobuButton, gbc);

	        srediSobuButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String poruka = SobaricaManager.getInstance().sredjenaSoba(soba, SobaricaManager.sobarice.get(korisnickoIme));
	                JOptionPane.showMessageDialog(pregledSobaPanel, poruka);
	                sobePanel.revalidate();
	                sobePanel.repaint();
	            }
	        });
	    }

	    JScrollPane scrollPane = new JScrollPane(sobePanel);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	    pregledSobaPanel.add(scrollPane, BorderLayout.CENTER);

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
