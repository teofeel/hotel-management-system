package views;

import java.time.*;
import java.time.ZoneId;
import java.util.*;

import entity.*;

import enumi.StatusRezervacije;
import manager.*;
import enumi.*;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import com.toedter.calendar.JDateChooser;

import controller.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RecepcionerViews extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar; 
	private JButton checkInButton;
	private JButton checkOutButton;
	private JButton checkInRezervacijaButton;
	private JButton izmenaStatusaRezButton;
	private JButton sobeButton;
	private JButton sveRezervacijeButton;
	private JButton logout;
	private RecepcionerController recepcionerController;
	
	private String korisnickoIme;
	
	public RecepcionerViews(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
		
		setTitle("Recepcioner Dashboard");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
		checkInButton = new JButton("Check IN");
		checkInButton.addActionListener(new NavbarButtonListener("CheckINPanel"));
		
		checkInRezervacijaButton = new JButton("Check IN sa rezervacijom");
		checkInRezervacijaButton.addActionListener(new NavbarButtonListener("CheckINRezervacijaPanel"));
		
		/*checkOutButton = new JButton("Check OUT");
		checkOutButton.addActionListener(new NavbarButtonListener("CheckOutPanel"));*/
		
		/*izmenaStatusaRezButton = new JButton("Izmena statua rezervacije");
		izmenaStatusaRezButton.addActionListener(new NavbarButtonListener("IzmenaStatusaRezervacijePanel"));*/
		
		sobeButton = new JButton("Sobe");
		sobeButton.addActionListener(new NavbarButtonListener("SobePanel"));

		sveRezervacijeButton = new JButton("Rezervacije");
		sveRezervacijeButton.addActionListener(new NavbarButtonListener("SveRezPanel"));
		
		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e) {
				FileManager.getInstance().upisiPodatke();
				LoginView loginWindow = new LoginView();
				dispose();
			}
		});
		
		navbar.add(checkInButton);
		navbar.add(checkInRezervacijaButton);
		//navbar.add(checkOutButton);
		//navbar.add(izmenaStatusaRezButton);
		navbar.add(sobeButton);
		navbar.add(sveRezervacijeButton);
		navbar.add(logout);
		
		cardLayout = new CardLayout(); 
		contentPanel = new JPanel(cardLayout);
		
		contentPanel.add(this.checkInPanel(), "CheckINPanel");
		contentPanel.add(this.checkInRezervacijaPanel(), "CheckINRezervacijaPanel");
		contentPanel.add(this.izmenaStatusaPanel(), "IzmenaStatusaRezervacijePanel");
		contentPanel.add(this.checkOutPanel(), "CheckOutPanel");
		contentPanel.add(this.sobePanel(), "SobePanel");
		contentPanel.add(this.sveRezPanel(), "SveRezPanel");
		
		add(navbar,BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);

	}
	
	private JPanel sveRezPanel() {
		JPanel sveRezPanel = new JPanel(new BorderLayout());
		
		RezervacijaTableModel model = new RezervacijaTableModel();
		JTable rezervacijeTable = new JTable(model);
		
		TableRowSorter<RezervacijaTableModel> sorter = new TableRowSorter<>(model);
		rezervacijeTable.setRowSorter(sorter);

	    //sveRezPanel.add(filterPanel(sorter), BorderLayout.PAGE_END);
	    
	    /*JScrollPane scrollPaneTable = new JScrollPane(rezervacijeTable);
	    scrollPaneTable.add(rezervacijeTable);
	    
		sveRezPanel.add(rezervacijeTable);*/
		
		JButton potvrdiButton = new JButton("Potvrdi");
		potvrdiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int row = rezervacijeTable.getSelectedRow();
					
					String gostEmail = RezervacijaManager.rezervacije.get(row).getGost();
					String datumDolaska = RezervacijaManager.rezervacije.get(row).getDatumDolaska().toString();
					String datumOdlaska = RezervacijaManager.rezervacije.get(row).getDatumOdlaska().toString();
					String poruka = RecepcionerManager.getInstance().izmenaStatusaRezrvacije(gostEmail, datumDolaska, datumOdlaska, StatusRezervacije.POTVRDJENA);
					
					JOptionPane.showMessageDialog(sveRezPanel, poruka);
					rezervacijeTable.updateUI();
				}catch(Exception err) {
					JOptionPane.showMessageDialog(sveRezPanel, "Nijedan red tabele nije selektovan");
				}
				
			}
		});
		
		JButton odbijButton = new JButton("Odbij");
		odbijButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int row = rezervacijeTable.getSelectedRow();
					
					String gostEmail = RezervacijaManager.rezervacije.get(row).getGost();
					String datumDolaska = RezervacijaManager.rezervacije.get(row).getDatumDolaska().toString();
					String datumOdlaska = RezervacijaManager.rezervacije.get(row).getDatumOdlaska().toString();
					String poruka = RecepcionerManager.getInstance().izmenaStatusaRezrvacije(gostEmail, datumDolaska, datumOdlaska, StatusRezervacije.ODBIJENA);
					
					JOptionPane.showMessageDialog(sveRezPanel, poruka);
					rezervacijeTable.updateUI();
				}catch(Exception err) {
					JOptionPane.showMessageDialog(sveRezPanel, "Nijedan red tabele nije selektovan");
				}
				
			}
		});
		
		JButton dodajUsluguButton = new JButton("Dodaj uslugu");
		dodajUsluguButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		Rezervacija rez = RezervacijaManager.rezervacije.get(rezervacijeTable.getSelectedRow());
            		JFrame uslugeFrame = new JFrame("Usluge");
            		uslugeFrame.addWindowListener(new WindowAdapter() {
            			@Override
            	        public void windowClosed(WindowEvent e) {
            				rezervacijeTable.updateUI();
            	        }
            			
            		});
            		uslugeFrame.setSize(300, 300);
            		uslugeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            		JPanel uslugePanel = new JPanel(new GridLayout(0, 2));
            		updateUslugePanel(uslugePanel, uslugeFrame, rez);

            		uslugeFrame.add(new JScrollPane(uslugePanel));
            		uslugeFrame.setVisible(true);
            		
            	}catch(Exception e1) {
            		JOptionPane.showMessageDialog(sveRezPanel, "Nijedan red tabele nije selektovan");
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
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(potvrdiButton);
		buttonPanel.add(odbijButton);
		buttonPanel.add(dodajUsluguButton);
		
		sveRezPanel.add(buttonPanel, BorderLayout.PAGE_START);
		
		JScrollPane scrollPane = new JScrollPane(rezervacijeTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sveRezPanel.add(scrollPane);
        
        
        JScrollPane scrollPane1 = new JScrollPane(filterPanel(sorter));
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        sveRezPanel.add(filterPanel(sorter), BorderLayout.PAGE_END);
		/*sveRezPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		JScrollPane scrollPane = new JScrollPane(rezervacijeTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    sveRezPanel.add(scrollPane, BorderLayout.CENTER);*/
		
		
		return sveRezPanel;
	}
	
	private JPanel filterPanel(TableRowSorter<RezervacijaTableModel> sorter) {
		JPanel filterPanel = new JPanel(new GridBagLayout());

	    JTextField tipSobeFilterField = new JTextField(10);
	    JTextField sobaFilterField = new JTextField(10);
	    JTextField cenaPocetnaField = new JTextField(5);
	    JTextField cenaKrajnjaField = new JTextField(5);
	    JComboBox<String> statusRezBox = new JComboBox<>();
	    statusRezBox.addItem("Default");
	    statusRezBox.addItem("NA CEKANJU");
	    statusRezBox.addItem("POTVRDJENA");
	    statusRezBox.addItem("ODBIJENA");
	    statusRezBox.addItem("OTKAZANA");
	    JTextField dodatneUslugeField = new JTextField(20);
	   
	    
	    JButton filterButton = new JButton("Filter");
	    filterButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>(3);

                String tipSobeText = tipSobeFilterField.getText();
                if (tipSobeText.length() > 0) {
                    filters.add(RowFilter.regexFilter("(?i)" + tipSobeText, 1));
                }

                String sobaText = sobaFilterField.getText();
                if (sobaText.length() > 0) {
                    filters.add(RowFilter.regexFilter("(?i)" + sobaText, 2));
                }

                String minCenaText = cenaPocetnaField.getText();
                String maxCenaText = cenaKrajnjaField.getText();
                if (minCenaText.length() > 0 || maxCenaText.length() > 0) {
                    try {
                        int minCena = minCenaText.length() > 0 ? Integer.parseInt(minCenaText) : Integer.MIN_VALUE;
                        int maxCena = maxCenaText.length() > 0 ? Integer.parseInt(maxCenaText) : Integer.MAX_VALUE;
                        filters.add(new RowFilter<Object, Object>() {
                            @Override
                            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                                Float cena = (Float) entry.getValue(5);
                                return cena >= minCena && cena <= maxCena;
                            }
                        });
                    } catch (NumberFormatException e1) {

                    }
                }
                
                String statusRez = statusRezBox.getSelectedItem().toString();
                if (!statusRez.equals("Default")) {
                    filters.add(RowFilter.regexFilter("(?i)" + statusRez, 6));
                }
                
                if (dodatneUslugeField.getText().length() > 0) {
                	 String[] dodatneUsluge = dodatneUslugeField.getText().split(" ");
                	String regexPattern="";
                	for (String r:dodatneUsluge) {
                		regexPattern += "(?=.*\\"+r+"\\b)";
                	}
                    filters.add(RowFilter.regexFilter(regexPattern, 7));
                }

                RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
                sorter.setRowFilter(combinedFilter);
			}
	    });
	    
	    
	    JButton sortNajmanjeCena = new JButton("Sort po najmanjoj ceni");
	    sortNajmanjeCena.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
	    		sorter.setSortKeys(new ArrayList<>());
	    	    sortKeys.add(new RowSorter.SortKey(5, SortOrder.ASCENDING));
	    		sorter.setSortKeys(sortKeys);
			}
	    });
	    JButton sortNajvecaButton = new JButton("Sort po najvecoj ceni");
	    sortNajvecaButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
	    		sorter.setSortKeys(new ArrayList<>());
	    		
	    	    sortKeys.add(new RowSorter.SortKey(5, SortOrder.DESCENDING));
	    		sorter.setSortKeys(sortKeys);
			}
	    });
	    
	    JButton removeSortButton = new JButton("Unsorted");
	    removeSortButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
	    		sorter.setSortKeys(new ArrayList<>());
	    		
	    	    sortKeys.add(new RowSorter.SortKey(5, SortOrder.UNSORTED));
	    		sorter.setSortKeys(sortKeys);
			}
	    });
	    
	    GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(new JLabel("Filter by Tip Sobe:"), gbc);
        gbc.gridx = 1;
        filterPanel.add(tipSobeFilterField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Filter by Soba ID:"), gbc);
        gbc.gridx = 3;
        filterPanel.add(sobaFilterField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(new JLabel("Najmanja cena:"), gbc);
        gbc.gridx = 1;
        filterPanel.add(cenaPocetnaField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Najveca cena:"), gbc);
        gbc.gridx = 3;
        filterPanel.add(cenaKrajnjaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        filterPanel.add(new JLabel("Dodatne usluge:"), gbc);
        gbc.gridx = 1;
        filterPanel.add(dodatneUslugeField, gbc);

        gbc.gridx = 2;
        filterPanel.add(new JLabel("Status Rezervacije:"), gbc);
        
        gbc.gridx = 3;
	    filterPanel.add(statusRezBox, gbc);
	    
	    gbc.gridx = 0;
        gbc.gridy = 3;
	    filterPanel.add(filterButton, gbc);
	    gbc.gridx = 1;
	    filterPanel.add(removeSortButton, gbc);
	    
	    gbc.gridx = 2;
	    filterPanel.add(sortNajmanjeCena, gbc);
	    gbc.gridy = 3;
	    filterPanel.add(sortNajvecaButton, gbc);
	    
	    return filterPanel;
	}
	
	
	private JPanel sobePanel() {
		JPanel sobePanel = new JPanel(new BorderLayout());
		
		SobaTableModel model = new SobaTableModel();
		JTable sobeTable = new JTable(model);
		
		JScrollPane scrollPanel = new JScrollPane(sobeTable);
		sobePanel.add(scrollPanel, BorderLayout.CENTER);
		JButton checkOut = new JButton("Check Out");
		checkOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Soba s = model.getSobe(sobeTable.getSelectedRow());
					
					String poruka = RecepcionerManager.getInstance().checkOUTProces(s);
					JOptionPane.showMessageDialog(sobePanel, poruka);
					sobeTable.updateUI();
				}catch(Exception err) {
					JOptionPane.showMessageDialog(sobePanel, "Nije selektovan ni jedan red tabele");
				}
				
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(checkOut);
		sobePanel.add(buttonPanel, BorderLayout.PAGE_START);
		/*HashMap<Integer, Soba> sobe = SobaManager.sobe;

		
		
		JPanel sobeListaPanel = new JPanel(new GridLayout(sobe.size(),2));
		
		for(Soba s:sobe.values()) {
			JLabel sifraSobe = new JLabel(Integer.toString(s.getSifra()));
			JLabel tipSobe = new JLabel(s.getNazivSobe());
			JLabel statusSobe = new JLabel(s.getStatus().toString());
			
			JButton checkOut = new JButton("Check Out");
			JButton dodaciSobe = new JButton("Amenities");
			
			sobeListaPanel.add(sifraSobe);
			sobeListaPanel.add(tipSobe);
			sobeListaPanel.add(statusSobe);
			sobeListaPanel.add(dodaciSobe);
			sobeListaPanel.add(checkOut);
			
			
			dodaciSobe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JFrame dodaciFrame = new JFrame();
					dodaciFrame.setTitle("Dodaci");
					dodaciFrame.setSize(300,300);
					dodaciFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					
					JPanel dodaciPanel = new JPanel();
					for(String dodatak:s.getAmenities()) {
						JLabel dodatakLabel = new JLabel(dodatak);
						
						dodaciPanel.add(dodatakLabel);
					}
					
					dodaciFrame.add(dodaciPanel);
					dodaciFrame.setVisible(true);
					
				}
			});
			
			checkOut.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String poruka = RecepcionerManager.getInstance().checkOUTProces(s);
					JOptionPane.showMessageDialog(sobePanel, poruka);
				}
			});
		}
		
		JScrollPane scrollPane = new JScrollPane(sobeListaPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sobePanel.add(scrollPane, BorderLayout.CENTER);
        
        sobePanel.setMaximumSize(getMaximumSize());	*/
        
		return sobePanel;
	}
	
	private JPanel checkInPanel() {
		JPanel checkInPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        checkInPanel.add(new JLabel("Check IN"), gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        checkInPanel.add(new JLabel("Ime:"), gbc);
        gbc.gridx = 1;
        JTextField imeField = new JTextField(15);
        checkInPanel.add(imeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        checkInPanel.add(new JLabel("Prezime:"), gbc);
        gbc.gridx = 1;
        JTextField prezimeField = new JTextField(15);
        checkInPanel.add(prezimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        checkInPanel.add(new JLabel("Pol:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> polBox = new JComboBox<>();
        polBox.addItem("Musko");
        polBox.addItem("Zensko");
        checkInPanel.add(polBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        checkInPanel.add(new JLabel("Datum Rodjenja:"), gbc);
        gbc.gridx = 1;
        JDateChooser datumRodjenja = new JDateChooser();
        checkInPanel.add(datumRodjenja, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        checkInPanel.add(new JLabel("Telefon:"), gbc);
        gbc.gridx = 1;
        JTextField telefonField = new JTextField(15);
        checkInPanel.add(telefonField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        checkInPanel.add(new JLabel("Adresa:"), gbc);
        gbc.gridx = 1;
        JTextField adresaField = new JTextField(15);
        checkInPanel.add(adresaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        checkInPanel.add(new JLabel("Broj Pasosa:"), gbc);
        gbc.gridx = 1;
        JTextField brojPasosaField = new JTextField(15);
        checkInPanel.add(brojPasosaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        checkInPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(15);
        checkInPanel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton checkIn = new JButton("Check-In");
        checkInPanel.add(checkIn, gbc);
        
        checkIn.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		String ime = imeField.getText();
        		String prezime = prezimeField.getText();
        		String pol = polBox.getSelectedItem().toString();
        		String datum = LocalDate.ofInstant(datumRodjenja.getDate().toInstant(), ZoneId.systemDefault()).toString();
        		String telefon = telefonField.getText();
        		String adresa = adresaField.getText();
        		String brojPasosa = brojPasosaField.getText();
        		String korisnickoIme = emailField.getText();
        		
        		
        		
				String poruka = RecepcionerManager.getInstance().checkInProces(ime, prezime, pol, 
						datum, telefon, adresa, brojPasosa, korisnickoIme);
				
				JOptionPane.showMessageDialog(checkInPanel, poruka);
			}
        });

        return checkInPanel;
		
	}
	
	private JPanel checkInRezervacijaPanel() {
		JPanel checkInPanel = new JPanel(new BorderLayout());
	    checkInPanel.add(new JLabel("Check IN rezervacija"), BorderLayout.NORTH);
	    
	    RezervacijaTableModel model = new RezervacijaTableModel();
		JTable rezervacijeTable = new JTable(model);
		
		TableRowSorter<RezervacijaTableModel> sorter = new TableRowSorter<>(model);
		rezervacijeTable.setRowSorter(sorter);
		
		sorter.setRowFilter(RowFilter.regexFilter("POTVRDJENA", 6));
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Custom RowFilter to filter dates after the current day
		RowFilter<RezervacijaTableModel, Object> customFilter = new RowFilter<RezervacijaTableModel, Object>() {
		    @Override
		    public boolean include(Entry<? extends RezervacijaTableModel, ? extends Object> entry) {
		        // Get the date from the table in the specified column (e.g., column 3)
		       // String dateString = (String) entry.getValue(3);
		        LocalDate date = (LocalDate) entry.getValue(3);
		        StatusRezervacije sr = (StatusRezervacije) entry.getValue(6);
		        //Soba s = (Soba) entry.getValue(2);
		        // Compare the date with the current date
		        return date.isAfter(LocalDate.now().minusDays(1)) && sr.equals(StatusRezervacije.POTVRDJENA) && entry.getValue(2)=="";
		    }
		};
		
		sorter.setRowFilter(customFilter);
		
		checkInPanel.add(rezervacijeTable);
		
		JButton zapocniCheckInButton = new JButton("Check In");
        JButton dodajUsluguButton = new JButton("Dodaj uslugu");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(zapocniCheckInButton);
        buttonPanel.add(dodajUsluguButton);
        checkInPanel.add(buttonPanel, BorderLayout.PAGE_START);
        
        dodajUsluguButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		Rezervacija rez = RezervacijaManager.rezervacije.get(rezervacijeTable.getSelectedRow());
            		
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
            	}catch(Exception e1) {
            		JOptionPane.showMessageDialog(buttonPanel, "Nijedan red tabele nije selektovan");
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

        zapocniCheckInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		Rezervacija rez = RezervacijaManager.rezervacije.get(rezervacijeTable.getSelectedRow());
            		JFrame checkInFrame = checkInFrame(rez);
            		checkInFrame.setVisible(true);
            		
            		checkInFrame.addWindowListener(new WindowAdapter() {
            			@Override
            	        public void windowClosed(WindowEvent e) {
            				sorter.setRowFilter(customFilter);
            	        }
            		});
            	}catch(Exception err) {
            		JOptionPane.showMessageDialog(buttonPanel, "Nijedan red tabele nije selektovan");
            	}
                
               
            }
        });

	    return checkInPanel;
	}
	
	private JPanel izmenaStatusaPanel() {
		JPanel izmenaStatusaPanel = new JPanel(new BorderLayout());
	    izmenaStatusaPanel.add(new JLabel("Izmena statusa"), BorderLayout.NORTH);

	    ArrayList<Rezervacija> rezervacije = RezervacijaManager.getInstance().naCekanjuRezervacije();
	    
	    JPanel rezervacijaPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(5, 5, 5, 5);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    int row = 0;
	    for (Rezervacija rez : rezervacije) {
	        gbc.gridy = row;

	        gbc.gridx = 0;
	        rezervacijaPanel.add(new JLabel(rez.getGost()), gbc);
	        
	        gbc.gridx = 1;
	        rezervacijaPanel.add(new JLabel(rez.getDatumDolaska().toString()), gbc);
	        
	        gbc.gridx = 2;
	        rezervacijaPanel.add(new JLabel(rez.getDatumOdlaska().toString()), gbc);
	        
	        gbc.gridx = 3;
	        rezervacijaPanel.add(new JLabel(Integer.toString(rez.getBrOsoba())), gbc);
	        
	        gbc.gridx = 4;
	        rezervacijaPanel.add(new JLabel(rez.getTipSobe().getNaziv()), gbc);
	        
	        gbc.gridx = 5;
	        rezervacijaPanel.add(new JLabel(Float.toString(rez.getCena())), gbc);

	        gbc.gridx = 6;
	        JButton potvrdiButton = new JButton("Potvrdi");
	        potvrdiButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String poruka = RecepcionerManager.getInstance().izmenaStatusaRezrvacije(rez.getGost(), 
							rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString(), StatusRezervacije.POTVRDJENA);
					JOptionPane.showMessageDialog(izmenaStatusaPanel, poruka);
					
					izmenaStatusaPanel.removeAll();
					izmenaStatusaPanel.add(izmenaStatusaPanel());
					izmenaStatusaPanel.revalidate();
					izmenaStatusaPanel.repaint();
					
					
	            }
	        });
	        rezervacijaPanel.add(potvrdiButton, gbc);

	        gbc.gridx = 7;
	        JButton odbijButton = new JButton("Odbij");
	        odbijButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String poruka = RecepcionerManager.getInstance().izmenaStatusaRezrvacije(rez.getGost(), 
							rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString(), StatusRezervacije.ODBIJENA);
					JOptionPane.showMessageDialog(izmenaStatusaPanel, poruka);
					

					izmenaStatusaPanel.removeAll();
					izmenaStatusaPanel.add(izmenaStatusaPanel());
					izmenaStatusaPanel.revalidate();
					izmenaStatusaPanel.repaint();
	            }
	        });
	        rezervacijaPanel.add(odbijButton, gbc);

	        row++;
	    }

	    JScrollPane scrollPane = new JScrollPane(rezervacijaPanel);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    izmenaStatusaPanel.add(scrollPane, BorderLayout.CENTER);

	    return izmenaStatusaPanel;
	}
	
	private JPanel checkOutPanel() {
		JPanel checkOutPanel = new JPanel();
		
		ArrayList<Soba> sobe = SobaManager.getInstance().zauzeteSobe();
		System.out.println(sobe);
		JPanel sobePanel = new JPanel(new GridLayout(sobe.size(),2));
		
		for(Soba s:sobe) {
			JLabel sifraSobe = new JLabel(Integer.toString(s.getSifra()));
			JButton checkOut = new JButton("Check Out");
			
			sobePanel.add(sifraSobe);
			sobePanel.add(checkOut);
			
			checkOut.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String poruka = RecepcionerManager.getInstance().checkOUTProces(s);
					
					JOptionPane.showMessageDialog(checkOutPanel, poruka);
				}
			});
		}
		checkOutPanel.add(sobePanel);
		return checkOutPanel;
	}
	
	private class NavbarButtonListener implements ActionListener {
		private String viewName;
		public NavbarButtonListener(String viewName) {
			this.viewName = viewName;
		}
		@Override 
		public void actionPerformed(ActionEvent e) {
			if(viewName.equals("CheckINRezervacijaPanel")) {
				contentPanel.remove(checkInRezervacijaPanel());
				contentPanel.add(checkInRezervacijaPanel(), "CheckINRezervacijaPanel");
			}else if(viewName.equals("IzmenaStatusaRezervacijePanel")) {
				contentPanel.remove(izmenaStatusaPanel());
				contentPanel.add(izmenaStatusaPanel(), "IzmenaStatusaRezervacijePanel");
			}else if(viewName.equals("CheckOutPanel")) {
				contentPanel.remove(checkOutPanel());
				contentPanel.add(checkOutPanel(), "CheckOutPanel");
			}else if(viewName.equals("SobePanel")) {
				contentPanel.remove(sobePanel());
				contentPanel.add(sobePanel(), "SobePanel");
			}
			cardLayout.show(contentPanel, viewName);
		}
	}
	
	private JFrame checkInFrame(Rezervacija rez) {
		JFrame checkInFrame = new JFrame();
		JPanel checkInPanel = new JPanel();
		
		checkInFrame.setTitle("Check IN");
		checkInFrame.setSize(300,300);
		checkInFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JComboBox<Integer> sobeComboBox = new JComboBox<Integer>();
		
		ArrayList<Soba> sobe = SobaManager.getInstance().slobodneSobeCheckIn(rez.getTipSobe().getNaziv());
		
		for(Soba s:sobe) {
			sobeComboBox.addItem(s.getSifra());
		}
		
		JButton checkInujButton = new JButton("Check IN");
		checkInujButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String poruka = RecepcionerManager.getInstance().checkInProcesRezervacija(rez.getGost(), rez.getDatumDolaska(),
																		rez.getDatumOdlaska(), SobaManager.sobe.get(sobeComboBox.getSelectedItem()));
				JOptionPane.showMessageDialog(checkInPanel, poruka);
				
			}
		});
		
		checkInPanel.add(sobeComboBox);
		checkInPanel.add(checkInujButton);
		checkInFrame.add(checkInPanel);
		return checkInFrame;
	}
	
	
}
