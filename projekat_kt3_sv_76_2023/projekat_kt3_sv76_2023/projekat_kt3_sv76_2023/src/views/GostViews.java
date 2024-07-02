package views;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

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
		RezervacijaTableModel model = new RezervacijaTableModel();
		JTable rezervacijeTable = new JTable(model);
		
		TableRowSorter<RezervacijaTableModel> sorter = new TableRowSorter<>(model);
		rezervacijeTable.setRowSorter(sorter);
		sorter.setRowFilter(RowFilter.regexFilter(korisnickoIme, 0));
		JButton otkaziButton = new JButton("Otkazi");
		JButton dodajUsluguButton = new JButton("Dodaj uslugu");
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(otkaziButton);
		buttonPanel.add(dodajUsluguButton);
		dodajUsluguButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		int row = rezervacijeTable.convertRowIndexToModel(rezervacijeTable.getSelectedRow());
            		Rezervacija rez = RezervacijaManager.rezervacije.get(row);
            		JFrame uslugeFrame = new JFrame("Usluge");
            		uslugeFrame.setSize(300, 300);
            		uslugeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            		JPanel uslugePanel = new JPanel(new GridLayout(0, 2)); 
            		updateUslugePanel(uslugePanel, uslugeFrame, rez);

            		uslugeFrame.add(new JScrollPane(uslugePanel)); 
            		uslugeFrame.setVisible(true);
            		uslugeFrame.addWindowListener(new WindowAdapter() {
            			@Override
            	        public void windowClosed(WindowEvent e) {
            				rezervacijeTable.updateUI();
            	        }
            		});
            	
            	}catch(Exception err) {
            		JOptionPane.showMessageDialog(rezervacijePanel, "Nije selektovan red tabele");
            	}
                
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
            	try {
            		int row = rezervacijeTable.convertRowIndexToModel(rezervacijeTable.getSelectedRow());
            		Rezervacija rez = RezervacijaManager.rezervacije.get(row);
            		
            		String poruka = GostManager.getInstance().otkaziRezervaciju(GostManager.gosti.get(korisnickoIme),
                            rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString());

                    JOptionPane.showMessageDialog(rezervacijePanel, poruka);
                    rezervacijeTable.updateUI();
            	}catch(Exception e1) {
            		 JOptionPane.showMessageDialog(rezervacijePanel, "Nije selektovan red tabele");
            	}
            }
        });
    	
		rezervacijePanel.add(buttonPanel, BorderLayout.PAGE_END);
		JScrollPane scrollPane = new JScrollPane(rezervacijeTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		rezervacijePanel.add(scrollPane);
	    
	    JLabel ukupanTrosakLabel = new JLabel("Ukupan trosak: "+GostManager.getInstance().ukupanTrosak(korisnickoIme));
	    
	    rezervacijePanel.add(ukupanTrosakLabel, BorderLayout.PAGE_START);

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
}
