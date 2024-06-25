package views;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import java.awt.event.*;

import java.awt.*;
import java.time.*;
import com.toedter.calendar.JDateChooser;
import entity.*;
import enumi.*;
import manager.*;

public class GostViews extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar;
	
	private JButton zahtevRezervacijeButton;
	private JButton rezervacijeButton; 	
	private JButton mojTrosakButton;
	private JButton logout;
	
	private String korisnickoIme;
	public GostViews(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
		
		setTitle("Gost Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
        zahtevRezervacijeButton = new JButton("Nova Rezervacija");
        zahtevRezervacijeButton.addActionListener(new NavbarButtonListener("NovaRezervacija"));
        
        rezervacijeButton = new JButton("Moje Rezervacije");
        rezervacijeButton.addActionListener(new NavbarButtonListener("Rezervacije"));

        logout = new JButton("Logout");
        logout.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e) {
				FileManager.getInstance().upisiPodatke();
				LoginView loginWindow = new LoginView();
				dispose();
			}
		});
        
        navbar.add(zahtevRezervacijeButton);
        navbar.add(rezervacijeButton);
        navbar.add(logout);
        
        cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		
		contentPanel.add(this.zahtevRezervacijePanel(), "NovaRezervacija");
		contentPanel.add(this.rezervacijePanel(), "Rezervacije");
		
		
		add(navbar,BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
	}
	
	private JPanel zahtevRezervacijePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Nova rezervacija");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        JLabel arrivalLabel = new JLabel("Datum Dolaska:");
        panel.add(arrivalLabel, gbc);

        gbc.gridx = 1;
        JDateChooser datumDolaska = new JDateChooser();
        datumDolaska.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        panel.add(datumDolaska, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel departureLabel = new JLabel("Datum Odlaska:");
        panel.add(departureLabel, gbc);

        gbc.gridx = 1;
        JDateChooser datumOdlaska = new JDateChooser();
        datumOdlaska.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        panel.add(datumOdlaska, gbc);
        
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel amenitiesLabel= new JLabel("Dodaci sobe:");
        panel.add(amenitiesLabel, gbc);
        
        gbc.gridx = 1;
        JTextField amenitiesField = new JTextField();
        panel.add(amenitiesField, gbc);

        
        gbc.gridy = 4;
        gbc.gridx = 0;
        JButton resetButton = new JButton("Reset");
        panel.add(resetButton, gbc);

        gbc.gridx = 1;
        JButton listRoomsButton = new JButton("Slobodne Sobe");
        panel.add(listRoomsButton, gbc);
        
        JComboBox<String> tipoviSoba = new JComboBox<>();
        JLabel roomTypeLabel = new JLabel("Tip Sobe:");
        JComboBox<String> brojLjudi = new JComboBox<>(new String[]{"1", "2", "3"});
        JLabel peopleCountLabel = new JLabel("Broj Ljudi:");
        JButton novaRezervacijaButton = new JButton("Posalji zahtev");
        
        
        listRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	panel.remove(roomTypeLabel);
            	panel.remove(tipoviSoba);
            	panel.remove(peopleCountLabel);
            	panel.remove(brojLjudi);
            	panel.remove(novaRezervacijaButton);
            	
                tipoviSoba.removeAllItems();

                ArrayList<String> slobodneSobe = SobaManager.getInstance().pregledSlobodnihTipovaSoba(
                        LocalDate.ofInstant(datumDolaska.getDate().toInstant(), ZoneId.systemDefault()),
                        LocalDate.ofInstant(datumOdlaska.getDate().toInstant(), ZoneId.systemDefault()),
                        amenitiesField.getText()
                );
                /*ArrayList<String> slobodneSobe = SobaManager.getInstance().pregledSlobodnihTipovaSoba(
                        LocalDate.ofInstant(datumDolaska.getDate().toInstant(), ZoneId.systemDefault()),
                        LocalDate.ofInstant(datumOdlaska.getDate().toInstant(), ZoneId.systemDefault())
                );*/

               
                for (String roomType : slobodneSobe) {
                    tipoviSoba.addItem(roomType);
                }

              
                gbc.gridy = 5;
                gbc.gridx = 0;
                
                panel.add(roomTypeLabel, gbc);

                gbc.gridx = 1;
                
                panel.add(tipoviSoba, gbc);

                gbc.gridy = 6;
                gbc.gridx = 0;
                
                panel.add(peopleCountLabel, gbc);

                gbc.gridx = 1;
                
                panel.add(brojLjudi, gbc);

               
                gbc.gridy = 7;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                
                panel.add(novaRezervacijaButton, gbc);

                listRoomsButton.getModel().setPressed(false);
                listRoomsButton.getModel().setArmed(false);
                
                panel.revalidate();
                panel.repaint();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDate now = LocalDate.now();
                datumDolaska.setDate(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                datumOdlaska.setDate(Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                tipoviSoba.removeAllItems();
                
                panel.removeAll();
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                
                gbc.gridwidth = 2;
                panel.add(titleLabel, gbc);

                
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                panel.add(arrivalLabel, gbc);

                gbc.gridx = 1;
                panel.add(datumDolaska, gbc);

                gbc.gridy = 2;
                gbc.gridx = 0;
                panel.add(departureLabel, gbc);

                gbc.gridx = 1;
                panel.add(datumOdlaska, gbc);

             
                gbc.gridy = 3;
                gbc.gridx = 0;
                panel.add(resetButton, gbc);

                gbc.gridx = 1;
                panel.add(listRoomsButton, gbc);

                resetButton.getModel().setPressed(false);
                resetButton.getModel().setArmed(false);
                
                panel.revalidate();
                panel.repaint();
            }
        });
        
        novaRezervacijaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		String tipSobe = tipoviSoba.getSelectedItem().toString();
            		if (tipSobe.equals("")) throw new Exception();
            		
            		String poruka = GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get(korisnickoIme), 
    						CenovnikManager.cenovnici.get(0).getTipoviSoba().get(tipoviSoba.getSelectedItem()), Integer.parseInt(brojLjudi.getSelectedItem().toString()),
    						LocalDate.ofInstant(datumDolaska.getDate().toInstant(), ZoneId.systemDefault()).toString(), 
    						LocalDate.ofInstant(datumOdlaska.getDate().toInstant(), ZoneId.systemDefault()).toString(), 
    						new ArrayList<DodatneUsluge>());
                	
                    JOptionPane.showMessageDialog(panel, poruka);
                    
                    novaRezervacijaButton.getModel().setPressed(false);
                    novaRezervacijaButton.getModel().setArmed(false);
            	}catch(Exception e1) {
            		JOptionPane.showMessageDialog(panel, "Nije moguce napraviti rez");
            	}
            	
            }
        });
        
        return panel;
    }

	
	private JPanel rezervacijePanel() {
		JPanel rezervacijePanel = new JPanel(new BorderLayout());
	    // rezervacijePanel.add(new JLabel("Rezervacije"), BorderLayout.NORTH);

	    ArrayList<Rezervacija> rezervacije = RezervacijaManager.getInstance().pregledRezervacija(GostManager.gosti.get(korisnickoIme));
	    System.out.println(rezervacije);
	    JPanel listaRezervacija = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    int row = 0;
	    for (Rezervacija rez : rezervacije) {
	        gbc.gridy = row++;
	        gbc.gridx = 0;
	        listaRezervacija.add(new JLabel(rez.getGost()), gbc);

	        gbc.gridx = 1;
	        listaRezervacija.add(new JLabel(rez.getStatus().toString()), gbc);

	        gbc.gridx = 2;
	        listaRezervacija.add(new JLabel(rez.getTipSobe().getNaziv()), gbc);

	        gbc.gridx = 3;
	        listaRezervacija.add(new JLabel(rez.getDatumDolaska().toString()), gbc);

	        gbc.gridx = 4;
	        listaRezervacija.add(new JLabel(rez.getDatumOdlaska().toString()), gbc);

	        gbc.gridx = 5;
	        listaRezervacija.add(new JLabel(Integer.toString(rez.getBrOsoba())), gbc);

	        gbc.gridx = 6;
	        listaRezervacija.add(new JLabel(rez.getStatus().equals(StatusRezervacije.ODBIJENA) ? "0" : Float.toString(rez.getCena())), gbc);

	        gbc.gridx = 7;
	        JButton dodajUsluguButton = new JButton("Dodaj Uslugu");
	        listaRezervacija.add(dodajUsluguButton, gbc);

	        gbc.gridx = 8;
	        JButton otkaziButton = new JButton("Otkazi");
	        listaRezervacija.add(otkaziButton, gbc);

	        dodajUsluguButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                JFrame uslugeFrame = new JFrame("Usluge");
	                uslugeFrame.setSize(300, 300);
	                uslugeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	                JPanel uslugePanel = new JPanel(new GridLayout(0, 2)); 
	                updateUslugePanel(uslugePanel, uslugeFrame, rez);

	                uslugeFrame.add(new JScrollPane(uslugePanel)); 
	                uslugeFrame.setVisible(true);
	            }

	            private void updateUslugePanel(JPanel uslugePanel, JFrame uslugeFrame, Rezervacija rez) {
	                uslugePanel.removeAll(); 
	                CenovnikManager.cenovnici.get(0).getDodatneUsluge().forEach((key, du) -> {
	                    uslugePanel.add(new JLabel(du.getNaziv()));
	                    JButton actionButton = new JButton(rez.getUsluge().contains(du) ? "Izbaci" : "Dodaj");
	                    actionButton.addActionListener(new ActionListener() {
	                        @Override
	                        public void actionPerformed(ActionEvent e) {
	                            if (rez.getUsluge().contains(du)) {
	                                rez.izbaciUslugu(du.getNaziv());
	                            } else {
	                                rez.dodajUslugu(du);
	                            }
	                            updateUslugePanel(uslugePanel, uslugeFrame, rez); 
	                        }
	                    });
	                    uslugePanel.add(actionButton);
	                });
	                uslugePanel.revalidate(); 
	                uslugePanel.repaint();
	            }
	        });

	        otkaziButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String poruka = GostManager.getInstance().otkaziRezervaciju(GostManager.gosti.get(korisnickoIme),
	                        rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString());

	                JOptionPane.showMessageDialog(rezervacijePanel, poruka);
	            }
	        });
	    }

	    JScrollPane scrollPane = new JScrollPane(listaRezervacija);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    JLabel ukupanTrosakLabel = new JLabel("Ukupan trosak: "+GostManager.getInstance().ukupanTrosak(korisnickoIme));
	    
	    rezervacijePanel.add(ukupanTrosakLabel, BorderLayout.PAGE_START);
	    rezervacijePanel.add(scrollPane, BorderLayout.CENTER);

	    return rezervacijePanel;
	}
	
	private class NavbarButtonListener implements ActionListener {
		private String viewName;
		public NavbarButtonListener(String viewName) {
			this.viewName = viewName;
		}
		@Override 
		public void actionPerformed(ActionEvent e) {
			if(viewName.equals("Rezervacije")) {
				contentPanel.add(rezervacijePanel(), "Rezervacije");
			}
			cardLayout.show(contentPanel, viewName);
		}
		
	}

	/*public static ArrayList<Rezervacija> pregledRezervacija(Gost gost) {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for(int i=0;i<RezervacijaManager.rezervacije.size();i++) {
			if (RezervacijaManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme())) {
				rezervacije.add(RezervacijaManager.rezervacije.get(i));
				System.out.println(RezervacijaManager.rezervacije.get(i).toString());
			}
		}
		return rezervacije;
	}*/
	
	/*private ArrayList<String> pregledSlobodnihTipovaSoba(LocalDate pocetak, LocalDate kraj) {
		ArrayList<String> tipoviSobe = new ArrayList<String>();
		
		if(pocetak.isBefore(LocalDate.now()) || kraj.isBefore(LocalDate.now()))
			return tipoviSobe;
		
		for(TipSobeEnum ts:TipSobeEnum.values()) {
			if(this.pregledSlobodnihSoba(pocetak, kraj, ts.toString()) && 
					!tipoviSobe.contains(ts.toString())) {
				tipoviSobe.add(ts.toString());
			}
		}
		
		return tipoviSobe;
	}
	
	public static boolean pregledSlobodnihSoba(LocalDate pocetak, LocalDate kraj, String tipSobe) {
		ArrayList<Soba> sobePoTipu = new ArrayList<Soba>();
		
		
		for (Soba soba : SobaManager.sobe.values()) {
			if(soba.getNazivSobe().equals(tipSobe) && soba.getStatus().equals(StatusSobe.SLOBODNA)) {
				sobePoTipu.add(soba);
			}
		}
		if (sobePoTipu.isEmpty()) {
			System.out.println("Nema slbodnih soba");
			return false;
		}
		
		for (Rezervacija rezervacija : RezervacijaManager.rezervacije) {
			if (rezervacija.getDatumOdlaska().minusDays(1).isBefore(pocetak) || rezervacija.getDatumDolaska().isAfter(kraj)) {
				continue;
			}
			
			if (sobePoTipu.isEmpty()) {
				System.out.println("Nema slobodnih soba");
				return false;
			}
			
			if (rezervacija.getTipSobe().getNaziv().equals(tipSobe) && rezervacija.getStatus().equals(StatusRezervacije.POTVRDJENA)){
				sobePoTipu.remove(0);
			}
		}
		if (sobePoTipu.isEmpty()) {
			System.out.println("Nema slobodnih soba");
			return false;
		}
		
		for(Soba s:sobePoTipu) {
			System.out.println(s);
		}
		return true;
	}*/
}
