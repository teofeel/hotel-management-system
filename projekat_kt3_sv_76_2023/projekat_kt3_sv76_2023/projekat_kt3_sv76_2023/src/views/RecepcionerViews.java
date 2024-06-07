package views;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import entity.*;

import enumi.StatusRezervacije;
import manager.*;
import enumi.*;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controller.*;

import java.awt.*;
import java.awt.event.*;


public class RecepcionerViews extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar; 
	private JButton checkInButton;
	private JButton checkOutButton;
	private JButton checkInRezervacijaButton;
	private JButton izmenaStatusaRezButton;
	private JButton sobeButton;
	private JButton logout;
	private RecepcionerController recepcionerController;
	
	private String korisnickoIme;
	
	public RecepcionerViews(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
		
		setTitle("Recepcioner Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
		checkInButton = new JButton("Check IN");
		checkInButton.addActionListener(new NavbarButtonListener("CheckINPanel"));
		
		checkInRezervacijaButton = new JButton("Check IN sa rezervacijom");
		checkInRezervacijaButton.addActionListener(new NavbarButtonListener("CheckINRezervacijaPanel"));
		
		checkOutButton = new JButton("Check OUT");
		checkOutButton.addActionListener(new NavbarButtonListener("CheckOutPanel"));
		
		izmenaStatusaRezButton = new JButton("Izmena statua rezervacije");
		izmenaStatusaRezButton.addActionListener(new NavbarButtonListener("IzmenaStatusaRezervacijePanel"));
		
		sobeButton = new JButton("Sobe");
		sobeButton.addActionListener(new NavbarButtonListener("SobePanel"));

		
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
		navbar.add(checkOutButton);
		navbar.add(izmenaStatusaRezButton);
		navbar.add(sobeButton);
		navbar.add(logout);
		
		cardLayout = new CardLayout(); 
		contentPanel = new JPanel(cardLayout);
		
		contentPanel.add(this.checkInPanel(), "CheckINPanel");
		contentPanel.add(this.checkInRezervacijaPanel(), "CheckINRezervacijaPanel");
		contentPanel.add(this.izmenaStatusaPanel(), "IzmenaStatusaRezervacijePanel");
		contentPanel.add(this.checkOutPanel(), "CheckOutPanel");
		contentPanel.add(this.sobePanel(), "SobePanel");
		
		add(navbar,BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);

	}
	
	private JPanel sobePanel() {
		HashMap<Integer, Soba> sobe = DodatnoManager.sobe;

		JPanel sobePanel = new JPanel();
		
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
        
        sobePanel.setMaximumSize(getMaximumSize());	
        
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
		JPanel checkInPanel = new JPanel();
		checkInPanel.add(new JLabel("Check IN rezervacija"));
		ArrayList<Rezervacija> rezervacije = DodatnoManager.getInstance().potvrdjeneRezervacije();
		for(Rezervacija rez:rezervacije) {
			JPanel rezervacijaPanel = new JPanel();
			
			JLabel emailGosta = new JLabel(rez.getGost());
			
			JLabel datumDolaska = new JLabel(rez.getDatumDolaska().toString());
			JLabel datumOdlaska = new JLabel(rez.getDatumOdlaska().toString());
			JLabel brojLjudi = new JLabel(Integer.toString(rez.getBrOsoba()));
			JLabel tipSobe = new JLabel(rez.getTipSobe().getNaziv());
			
			JButton zapocniCheckInButton = new JButton("Check In");
			JButton dodajUslugu = new JButton("Dodaj uslugu");
			
			rezervacijaPanel.add(emailGosta);
			rezervacijaPanel.add(datumDolaska);
			rezervacijaPanel.add(datumOdlaska);
			rezervacijaPanel.add(brojLjudi);
			rezervacijaPanel.add(tipSobe);
			rezervacijaPanel.add(dodajUslugu);
			rezervacijaPanel.add(zapocniCheckInButton);
			
			dodajUslugu.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        JFrame uslugeFrame = new JFrame("Usluge");
			        uslugeFrame.setSize(300, 300);
			        uslugeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			        JPanel uslugePanel = new JPanel(new GridLayout(0, 2)); 
			        updateUslugePanel(uslugePanel, uslugeFrame);

			        uslugeFrame.add(new JScrollPane(uslugePanel));
			        uslugeFrame.setVisible(true);
			        
			    }

			    private void updateUslugePanel(JPanel uslugePanel, JFrame uslugeFrame) {
			        uslugePanel.removeAll(); // Clear existing components
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
			                    updateUslugePanel(uslugePanel, uslugeFrame); 
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
					JFrame checkInFrame = checkInFrame(rez);
					checkInFrame.setVisible(true); 
				}
			});
			checkInPanel.add(rezervacijaPanel);
		}
		return checkInPanel;
	}
	
	private JScrollPane izmenaStatusaPanel() {
		JPanel izmenaStatusaPanel = new JPanel();
		izmenaStatusaPanel.add(new JLabel("Izmena statusa"));
		
		ArrayList<Rezervacija> rezervacije = DodatnoManager.getInstance().naCekanjuRezervacije();
		
		JPanel rezervacijaPanel = new JPanel(new GridLayout(rezervacije.size(),5));
		
		for(Rezervacija rez:rezervacije) {
			JLabel emailGosta = new JLabel(rez.getGost());
			
			JLabel datumDolaska = new JLabel(rez.getDatumDolaska().toString());
			JLabel datumOdlaska = new JLabel(rez.getDatumOdlaska().toString());
			JLabel brojLjudi = new JLabel(Integer.toString(rez.getBrOsoba()));
			JLabel tipSobe = new JLabel(rez.getTipSobe().getNaziv());
			JLabel cenaRezervacije = new JLabel(Float.toString(rez.getCena()));
			
			JButton potvrdiButton = new JButton("Potvrdi");
			JButton odbijButton = new JButton("Odbij");

			
			rezervacijaPanel.add(emailGosta);
			rezervacijaPanel.add(datumDolaska);
			rezervacijaPanel.add(datumOdlaska);
			rezervacijaPanel.add(brojLjudi);
			rezervacijaPanel.add(tipSobe);
			rezervacijaPanel.add(cenaRezervacije);
			rezervacijaPanel.add(potvrdiButton);
			rezervacijaPanel.add(odbijButton);
			
			potvrdiButton.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					String poruka = RecepcionerManager.getInstance().izmenaStatusaRezrvacije(rez.getGost(), 
							rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString(), StatusRezervacije.POTVRDJENA);
					JOptionPane.showMessageDialog(izmenaStatusaPanel, poruka);
				}
			});
			
			odbijButton.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					String poruka = RecepcionerManager.getInstance().izmenaStatusaRezrvacije(rez.getGost(), 
							rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString(), StatusRezervacije.ODBIJENA);
					JOptionPane.showMessageDialog(izmenaStatusaPanel, poruka);
				}
			});
		}
		JScrollPane scrollPane = new JScrollPane(rezervacijaPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //izmenaStatusaPanel.add(scrollPane, BorderLayout.CENTER);
        
        //izmenaStatusaPanel.setMaximumSize(getMaximumSize());	
        
		//izmenaStatusaPanel.add(rezervacijaPanel);
		return scrollPane;
	}
	
	private JPanel checkOutPanel() {
		JPanel checkOutPanel = new JPanel();
		
		ArrayList<Soba> sobe = DodatnoManager.getInstance().zauzeteSobe();
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
		
		ArrayList<Soba> sobe = DodatnoManager.getInstance().slobodneSobeCheckIn(rez.getTipSobe().getNaziv());
		
		for(Soba s:sobe) {
			sobeComboBox.addItem(s.getSifra());
		}
		
		JButton checkInujButton = new JButton("Check IN");
		checkInujButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String poruka = RecepcionerManager.getInstance().checkInProcesRezervacija(rez.getGost(), rez.getDatumDolaska(),
																		rez.getDatumOdlaska(), DodatnoManager.sobe.get(sobeComboBox.getSelectedItem()));
				JOptionPane.showMessageDialog(checkInPanel, poruka);
			}
		});
		
		checkInPanel.add(sobeComboBox);
		checkInPanel.add(checkInujButton);
		checkInFrame.add(checkInPanel);
		return checkInFrame;
	}
	
	
}
