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
        
        // Title
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Nova rezervacija");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, gbc);

        // Date Choosers
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

        // Buttons
        gbc.gridy = 3;
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
        // Event Listeners
        listRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear previous data
            	panel.remove(roomTypeLabel);
            	panel.remove(tipoviSoba);
            	panel.remove(peopleCountLabel);
            	panel.remove(brojLjudi);
            	panel.remove(novaRezervacijaButton);
            	
                tipoviSoba.removeAllItems();

                // Get available room types (dummy data for example)
                ArrayList<String> slobodneSobe = pregledSlobodnihTipovaSoba(
                        LocalDate.ofInstant(datumDolaska.getDate().toInstant(), ZoneId.systemDefault()),
                        LocalDate.ofInstant(datumOdlaska.getDate().toInstant(), ZoneId.systemDefault())
                );

                // Populate JComboBox with available room types
                for (String roomType : slobodneSobe) {
                    tipoviSoba.addItem(roomType);
                }

                // ComboBoxes
                gbc.gridy = 4;
                gbc.gridx = 0;
                
                panel.add(roomTypeLabel, gbc);

                gbc.gridx = 1;
                
                panel.add(tipoviSoba, gbc);

                gbc.gridy = 5;
                gbc.gridx = 0;
                
                panel.add(peopleCountLabel, gbc);

                gbc.gridx = 1;
                
                panel.add(brojLjudi, gbc);

                // Submit Button
                gbc.gridy = 6;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                
                panel.add(novaRezervacijaButton, gbc);

                listRoomsButton.getModel().setPressed(false);
                listRoomsButton.getModel().setArmed(false);
                // Refresh the panel
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

                // Date Choosers
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

                // Buttons
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
            	String poruka = GostManager.getInstance().zahtevRezervacija(GostManager.gosti.get(korisnickoIme), 
						CenovnikManager.cenovnici.get(0).getTipoviSoba().get(tipoviSoba.getSelectedItem()), Integer.parseInt(brojLjudi.getSelectedItem().toString()),
						LocalDate.ofInstant(datumDolaska.getDate().toInstant(), ZoneId.systemDefault()).toString(), 
						LocalDate.ofInstant(datumOdlaska.getDate().toInstant(), ZoneId.systemDefault()).toString(), 
						new ArrayList<DodatneUsluge>());
            	
                JOptionPane.showMessageDialog(panel, poruka);
                
                novaRezervacijaButton.getModel().setPressed(false);
                novaRezervacijaButton.getModel().setArmed(false);
            }
        });
        
        return panel;
    }

	
	private JPanel rezervacijePanel() {
		JPanel rezervacijePanel = new JPanel();
		//rezervacijePanel.add(new JLabel("Rezervacije"));
		
		ArrayList<Rezervacija> rezervacije = this.pregledRezervacija(GostManager.gosti.get(this.korisnickoIme));
		System.out.println(rezervacije);
		JPanel listaRezervacija = new JPanel(new GridLayout(rezervacije.size(),8));
		
		for(Rezervacija rez:rezervacije) {
			JLabel email = new JLabel(rez.getGost());
			JLabel status = new JLabel(rez.getStatus().toString());
			JLabel tipSobe = new JLabel(rez.getTipSobe().getNaziv());
			JLabel datumDolaska = new JLabel(rez.getDatumDolaska().toString());
			JLabel datumOdklaska = new JLabel(rez.getDatumOdlaska().toString());
			JLabel brOsoba = new JLabel(Integer.toString(rez.getBrOsoba()));
			JLabel cena = new JLabel(Float.toString(rez.getCena()));
			
			JButton dodajUslugu = new JButton("Dodaj Uslugu");
			JButton otkazi = new JButton("Otkazi");
			
			listaRezervacija.add(email);
			listaRezervacija.add(status);
			listaRezervacija.add(tipSobe);
			listaRezervacija.add(datumDolaska);
			listaRezervacija.add(datumOdklaska);
			listaRezervacija.add(brOsoba);
			listaRezervacija.add(cena);
			listaRezervacija.add(dodajUslugu);
			listaRezervacija.add(otkazi);
			
			dodajUslugu.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        JFrame uslugeFrame = new JFrame("Usluge");
			        uslugeFrame.setSize(300, 300);
			        uslugeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			        JPanel uslugePanel = new JPanel(new GridLayout(0, 2)); // GridLayout with 0 rows for dynamic layout
			        updateUslugePanel(uslugePanel, uslugeFrame);

			        uslugeFrame.add(new JScrollPane(uslugePanel)); // Added JScrollPane for better usability
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
			                    updateUslugePanel(uslugePanel, uslugeFrame); // Update panel after action
			                }
			            });
			            uslugePanel.add(actionButton);
			        });
			        uslugePanel.revalidate(); // Refresh the panel to reflect changes
			        uslugePanel.repaint();
			    }
			});
			
			
			otkazi.addActionListener(new ActionListener() {
				@Override 
				public void actionPerformed(ActionEvent e) {
					String poruka = GostManager.getInstance().otkaziRezervaciju(GostManager.gosti.get(korisnickoIme), 
							rez.getDatumDolaska().toString(), rez.getDatumOdlaska().toString());
					
					JOptionPane.showMessageDialog(rezervacijePanel, poruka);
				}
			});
		}
		
		rezervacijePanel.add(listaRezervacija);
		
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

	public static ArrayList<Rezervacija> pregledRezervacija(Gost gost) {
		ArrayList<Rezervacija> rezervacije = new ArrayList<Rezervacija>();
		for(int i=0;i<DodatnoManager.rezervacije.size();i++) {
			if (DodatnoManager.rezervacije.get(i).getGost().equals(gost.getKorisnickoIme())) {
				rezervacije.add(DodatnoManager.rezervacije.get(i));
				System.out.println(DodatnoManager.rezervacije.get(i).toString());
			}
		}
		return rezervacije;
	}
	
	private ArrayList<String> pregledSlobodnihTipovaSoba(LocalDate pocetak, LocalDate kraj) {
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
		
		
		for (Soba soba : DodatnoManager.sobe.values()) {
			if(soba.getNazivSobe().equals(tipSobe) && soba.getStatus().equals(StatusSobe.SLOBODNA)) {
				sobePoTipu.add(soba);
			}
		}
		if (sobePoTipu.isEmpty()) {
			System.out.println("Nema slbodnih soba");
			return false;
		}
		
		for (Rezervacija rezervacija : DodatnoManager.rezervacije) {
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
	}
}
