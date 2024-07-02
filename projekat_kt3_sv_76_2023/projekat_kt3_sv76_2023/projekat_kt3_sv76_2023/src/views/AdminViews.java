package views;

import java.util.*;

import javax.swing.*;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;

import entity.*;
import enumi.StrucnaSprema;
import enumi.TipSobeEnum;
import manager.*;
import controller.*;
public class AdminViews extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar;
	
	private JButton dodajZaposlenogButton;
	private JButton zaposleniButton;
	private JButton dodajGostaButton;
	private JButton gostiButton;
	private JButton sobeButton;
	private JButton dodajSobuButton;
	
	private JButton uslugeButton;
	
	//private JButton definisanjeCenovnikaButton;
	private JButton cenovniciButton;
	private JButton izvestajiButton;
	
	private JButton logout;
	
	private String korisnickoIme;
	
	public AdminViews(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
		
		setTitle("Admin Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
        dodajZaposlenogButton = new JButton("Dodaj zaposlenog");
        dodajZaposlenogButton.addActionListener(new NavbarButtonListener("DodajZaposlenog"));
        
        zaposleniButton = new JButton("Zaposleni");
        zaposleniButton.addActionListener(new NavbarButtonListener("Zaposleni"));
        
        dodajGostaButton = new JButton("Dodaj gosta");
        dodajGostaButton.addActionListener(new NavbarButtonListener("DodajGosta"));
        
        gostiButton = new JButton("Gosti");
        gostiButton.addActionListener(new NavbarButtonListener("Gosti"));
        
        
        dodajSobuButton = new JButton("Dodaj Sobu");
        dodajSobuButton.addActionListener(new NavbarButtonListener("DodajSobu"));
        
        sobeButton = new JButton("Sobe");
        sobeButton.addActionListener(new NavbarButtonListener("Sobe"));
        
        cenovniciButton = new JButton("Cenovnici");
        cenovniciButton.addActionListener(new ActionListener() {
        	@Override 
			public void actionPerformed(ActionEvent e) {
				new CenovnikViews();
			}
        });
        
        izvestajiButton = new JButton("Izvestaji");
        /*izvestajiButton.addActionListener(new ActionListener() {
        	@Override 
			public void actionPerformed(ActionEvent e) {
				new IzvestajiViews();
			}
        });   */   
        
        izvestajiButton.addActionListener(new NavbarButtonListener("Izvestaji"));
        
        logout = new JButton("Logout");
        logout.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e) {
				FileManager.getInstance().upisiPodatke();
				LoginView loginWindow = new LoginView();
				dispose();
			}
		});
        
        navbar.add(dodajZaposlenogButton);
        navbar.add(zaposleniButton);
        navbar.add(dodajGostaButton);
        navbar.add(gostiButton);
        navbar.add(dodajSobuButton);
        navbar.add(dodajSobuButton);
        navbar.add(sobeButton);
        navbar.add(cenovniciButton);
        navbar.add(izvestajiButton);
        navbar.add(logout);
        
        cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		
		contentPanel.add(this.dodajZaposlenogPanel(), "DodajZaposlenog");
		contentPanel.add(this.zaposleniPanel(), "Zaposleni");
		contentPanel.add(this.dodajGostaPanel(), "DodajGosta");
		contentPanel.add(this.gostiPanel(), "Gosti");
		contentPanel.add(this.dodajSobuPanel(), "DodajSobu");
		contentPanel.add(this.sobePanel(), "Sobe");
		contentPanel.add(new IzvestajiPanel(), "Izvestaji");

		add(navbar,BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
	}
	
	
	
	private JPanel dodajZaposlenogPanel() {
		JPanel dodajZaposlenogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;
        dodajZaposlenogPanel.add(new JLabel("Novi zaposleni:"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;
        dodajZaposlenogPanel.add(new JLabel(""), gbc); 

        y++;
        
        String[] tipovi = {"Administrator", "Recepcioner", "Sobarica"};
        JComboBox<String> tipBox = new JComboBox<>(tipovi);
        addComponent(dodajZaposlenogPanel, new JLabel("Tip posla:"), tipBox, gbc, y++);

        JTextField korisnickoImeField = new JTextField(12);
        addComponent(dodajZaposlenogPanel, new JLabel("Korisničko ime:"), korisnickoImeField, gbc, y++);

        JTextField imeField = new JTextField(12);
        addComponent(dodajZaposlenogPanel, new JLabel("Ime:"), imeField, gbc, y++);

        JTextField prezimeField = new JTextField(12);
        addComponent(dodajZaposlenogPanel, new JLabel("Prezime:"), prezimeField, gbc, y++);

        JComboBox<String> polBox = new JComboBox<>(new String[]{"Musko", "Zensko"});
        addComponent(dodajZaposlenogPanel, new JLabel("Pol:"), polBox, gbc, y++);

        JDateChooser datumPicker = new JDateChooser();
        datumPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        addComponent(dodajZaposlenogPanel, new JLabel("Datum rođenja:"), datumPicker, gbc, y++);

        JTextField telefonField = new JTextField(12);
        addComponent(dodajZaposlenogPanel, new JLabel("Telefon:"), telefonField, gbc, y++);

        JTextField adresaField = new JTextField(12);
        addComponent(dodajZaposlenogPanel, new JLabel("Adresa:"), adresaField, gbc, y++);

        JPasswordField lozinkaField = new JPasswordField(12);
        addComponent(dodajZaposlenogPanel, new JLabel("Lozinka:"), lozinkaField, gbc, y++);

        JComboBox<StrucnaSprema> spremaBox = new JComboBox<>();
        spremaBox.addItem(StrucnaSprema.OSNOVNA);
        spremaBox.addItem(StrucnaSprema.SREDNJA);
        spremaBox.addItem(StrucnaSprema.VISOKA);
        addComponent(dodajZaposlenogPanel, new JLabel("Stručna sprema:"), spremaBox, gbc, y++);

        JTextField stazField = new JTextField(2);
        addComponent(dodajZaposlenogPanel, new JLabel("Staž:"), stazField, gbc, y++);

        JButton dodajButton = new JButton("Dodaj zaposlenog");
        gbc.gridx = 1;
        gbc.gridy = y++;
        gbc.anchor = GridBagConstraints.CENTER;
        dodajZaposlenogPanel.add(dodajButton, gbc);

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tip = (String) tipBox.getSelectedItem();
                String korisnickoIme = korisnickoImeField.getText();
                String ime = imeField.getText();
                String prezime = prezimeField.getText();
                String pol = (String) polBox.getSelectedItem();
                String datum = LocalDate.ofInstant(datumPicker.getDate().toInstant(), ZoneId.systemDefault()).toString();
                String telefon = telefonField.getText();
                String adresa = adresaField.getText();
                String lozinka = new String(lozinkaField.getPassword());
                StrucnaSprema sprema = (StrucnaSprema) spremaBox.getSelectedItem();
                String staz = stazField.getText();

                String poruka = AdminManager.getInstance().dodajZaposlenog(tip, ime, prezime, pol,
                        datum, telefon, adresa, korisnickoIme, lozinka, sprema, staz);

                JOptionPane.showMessageDialog(dodajZaposlenogPanel, poruka);
            }
        });

        return dodajZaposlenogPanel;
	}
	private void addComponent(JPanel panel, JLabel label, JComponent component, GridBagConstraints gbc, int y) {
	        gbc.gridx = 0;
	        gbc.gridy = y;
	        gbc.anchor = GridBagConstraints.EAST;
	        panel.add(label, gbc);

	        gbc.gridx = 1;
	        gbc.anchor = GridBagConstraints.WEST;
	        panel.add(component, gbc);
	}
	
	 
	private void izmeniZaposlenog(Zaposleni zaposleni, JTable zaposleniTable) {
		JFrame izmenaFrame = new JFrame();
		
		izmenaFrame.addWindowListener(new WindowAdapter() {
			@Override
	        public void windowClosed(WindowEvent e) {
				zaposleniTable.updateUI();
	        }
			
		});
		
        izmenaFrame.setTitle("Izmena podataka");
        izmenaFrame.setSize(300, 300);
        izmenaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel podaciPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        JTextField korisnickoImeField = new JTextField(zaposleni.getKorisnickoIme(), 10);
        addComponent(podaciPanel, new JLabel("Korisničko ime:"), korisnickoImeField, gbc, y++);

        JTextField imeField = new JTextField(zaposleni.getIme(), 10);
        addComponent(podaciPanel, new JLabel("Ime:"), imeField, gbc, y++);

        JTextField prezimeField = new JTextField(zaposleni.getPrezime(), 10);
        addComponent(podaciPanel, new JLabel("Prezime:"), prezimeField, gbc, y++);

        JComboBox<String> polBox = new JComboBox<>();
        polBox.addItem("Muško");
        polBox.addItem("Žensko");
        polBox.setSelectedItem(zaposleni.getPol());
        addComponent(podaciPanel, new JLabel("Pol:"), polBox, gbc, y++);

        JDateChooser datumRodjenjaField = new JDateChooser();
        datumRodjenjaField.setDate(Date.from(zaposleni.getDatumRodjenja().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        addComponent(podaciPanel, new JLabel("Datum rođenja:"), datumRodjenjaField, gbc, y++);

        JTextField telefonField = new JTextField(zaposleni.getTelefon(), 10);
        addComponent(podaciPanel, new JLabel("Telefon:"), telefonField, gbc, y++);

        JTextField adresaField = new JTextField(zaposleni.getAdresa(), 10);
        addComponent(podaciPanel, new JLabel("Adresa:"), adresaField, gbc, y++);

        JPasswordField lozinkaField = new JPasswordField(zaposleni.getLozinka(), 10);
        addComponent(podaciPanel, new JLabel("Lozinka:"), lozinkaField, gbc, y++);

        JLabel tipLabel = new JLabel(zaposleni.getTip());
        addComponent(podaciPanel, new JLabel("Tip:"), tipLabel, gbc, y++);

        JComboBox<String> strucnaSpremaBox = new JComboBox<>();
        strucnaSpremaBox.addItem("VISOKA");
        strucnaSpremaBox.addItem("SREDNJA");
        strucnaSpremaBox.addItem("OSNOVNA");
        strucnaSpremaBox.setSelectedItem(zaposleni.getStrucnaSprema().toString());
        addComponent(podaciPanel, new JLabel("Stručna sprema:"), strucnaSpremaBox, gbc, y++);

        JTextField stazField = new JTextField(Integer.toString(zaposleni.getStaz()), 10);
        addComponent(podaciPanel, new JLabel("Staž:"), stazField, gbc, y++);

        JButton updateButton = new JButton("Update");
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        podaciPanel.add(updateButton, gbc);

        JScrollPane scrollPane = new JScrollPane(podaciPanel);
        izmenaFrame.add(scrollPane);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String korisnickoIme = korisnickoImeField.getText();
                String ime = imeField.getText();
                String prezime = prezimeField.getText();
                String pol = polBox.getSelectedItem().toString();
                String datumRodjenja = LocalDate.ofInstant(datumRodjenjaField.getDate().toInstant(), ZoneId.systemDefault()).toString();
                String telefon = telefonField.getText();
                String adresa = adresaField.getText();
                String lozinka = new String(lozinkaField.getPassword());

                String tip = tipLabel.getText();
                String strucnaSprema = strucnaSpremaBox.getSelectedItem().toString();
                String staz = stazField.getText();

                String poruka = AdminManager.getInstance().izmeniPodatkeZaposlenog(zaposleni.getKorisnickoIme(), korisnickoIme, ime,
                        prezime, pol, datumRodjenja, telefon, adresa, lozinka, strucnaSprema, staz, tip);

                JOptionPane.showMessageDialog(podaciPanel, poruka);
            }
        });

        izmenaFrame.setVisible(true);
	}
	private JPanel zaposleniPanel() {
		JPanel zaposleniPanel = new JPanel(new BorderLayout());
		
		ZaposleniTableModel model = new ZaposleniTableModel();
		JTable zaposleniTable = new JTable(model);
		TableRowSorter<ZaposleniTableModel> sorter = new TableRowSorter<>(model);
		zaposleniTable.setRowSorter(sorter);
		
		JPanel buttonPanel = new JPanel();
		JButton otpustiButton = new JButton("Otpusti");
        JButton izmeniButton = new JButton("Izmeni");
        JTextField imeField = new JTextField(10);
        JComboBox<String> tipBox = new JComboBox<>();
        tipBox.addItem("Default");
        tipBox.addItem("Administrator");
        tipBox.addItem("Recepcioner");
        tipBox.addItem("Sobarica");
        JButton filterButton = new JButton("Filter");
        
        buttonPanel.add(new JLabel("Korisnicko ime: "));
        buttonPanel.add(imeField);
        buttonPanel.add(tipBox);
        buttonPanel.add(filterButton);
        buttonPanel.add(izmeniButton);
        buttonPanel.add(otpustiButton);
        
        
        otpustiButton.addActionListener(new ActionListener() {
        	@Override 
			public void actionPerformed(ActionEvent e) {
        		try {
        			int modelRowIndex = zaposleniTable.convertRowIndexToModel(zaposleniTable.getSelectedRow());
        			Zaposleni zap = model.getZaposleni(modelRowIndex);
        			
        			String poruka = AdminManager.getInstance().otpustiZaposlenog(zap.getKorisnickoIme());
        			JOptionPane.showMessageDialog(buttonPanel, poruka);
        			
        			model.removeZaposleni(modelRowIndex);
        			model.fireTableDataChanged();
        			zaposleniTable.updateUI();
        			
        			
        		}catch(Exception err) {
        			JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan ni jedan red tabele");
        		}
			}
        });
        izmeniButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				try {
        			int modelRowIndex = zaposleniTable.convertRowIndexToModel(zaposleniTable.getSelectedRow());
        			Zaposleni zap = model.getZaposleni(modelRowIndex);
        			
        			izmeniZaposlenog(zap, zaposleniTable);
        			
        		}catch(Exception err) {
        			JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan ni jedan red tabele");
        		}
			}
		});
        filterButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>(3);

                String imeText = imeField.getText();
                if (imeText.length() > 0) {
                    filters.add(RowFilter.regexFilter("(?i)" + imeText, 0));
                }

                
                String tip = tipBox.getSelectedItem().toString();
                if (!tip.equals("Default")) {
                    filters.add(RowFilter.regexFilter("(?i)" + tip, 3));
                }
               

                RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
                sorter.setRowFilter(combinedFilter);
			}
	    });
        
		
		JScrollPane scrollPane = new JScrollPane(zaposleniTable);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		zaposleniPanel.add(buttonPanel, BorderLayout.PAGE_START);
		zaposleniPanel.add(scrollPane);
		
		return zaposleniPanel;
	}
	
	
	private JPanel dodajGostaPanel() {
		JPanel dodajGostaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int y = 0;
        
        JTextField imeField = new JTextField(12);
        JTextField prezimeField = new JTextField(12);
        addComponent(dodajGostaPanel, new JLabel("Ime:"), imeField, gbc, y++);
        addComponent(dodajGostaPanel, new JLabel("Prezime:"), prezimeField, gbc, y++);
        
        JComboBox<String> polBox = new JComboBox<>();
        polBox.addItem("Musko");
        polBox.addItem("Zensko");
        addComponent(dodajGostaPanel, new JLabel("Pol:"), polBox, gbc, y++);

        JDateChooser datumPicker = new JDateChooser();
        datumPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        addComponent(dodajGostaPanel, new JLabel("Datum rođenja:"), datumPicker, gbc, y++);
        
        JTextField telefonField = new JTextField(12);
        JTextField adresaField = new JTextField(12);
        JTextField brojPasosaField = new JTextField(12);
        JTextField emailField = new JTextField(12);
        addComponent(dodajGostaPanel, new JLabel("Telefon:"), telefonField, gbc, y++);
        addComponent(dodajGostaPanel, new JLabel("Adresa:"), adresaField, gbc, y++);
        addComponent(dodajGostaPanel, new JLabel("Broj pasosa:"), brojPasosaField, gbc, y++);
        addComponent(dodajGostaPanel, new JLabel("Email:"), emailField, gbc, y++);

        JButton dodajGostaButton = new JButton("Dodaj");
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dodajGostaPanel.add(dodajGostaButton, gbc);

        dodajGostaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String ime = imeField.getText();
               String prezime = prezimeField.getText();
               String pol = (String) polBox.getSelectedItem();
               String datum =  LocalDate.ofInstant(datumPicker.getDate().toInstant(), ZoneId.systemDefault()).toString();
               String telefon = telefonField.getText();
               String adresa = adresaField.getText();
               String brojPasosa = adresaField.getText();
               String email = emailField.getText();
               
               String poruka = AdminManager.getInstance().dodajGosta(ime, prezime, pol, datum, telefon, adresa, brojPasosa, email);
               
               JOptionPane.showMessageDialog(dodajGostaPanel, poruka);
            }
        });

        JScrollPane scrollPane = new JScrollPane(dodajGostaPanel);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
	}
	
	private void izmeniGosta(Gost gost, JTable gostTable) {
		  JFrame izmenaFrame = new JFrame();
	        izmenaFrame.setTitle("Izmena podataka");
	        izmenaFrame.setSize(300, 300);
	        izmenaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        
	        
	        izmenaFrame.addWindowListener(new WindowAdapter() {
				@Override
		        public void windowClosed(WindowEvent e) {
					gostTable.updateUI();
		        }
				
			});
	        
	        JPanel podaciPanel = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 5, 5, 5);
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        int y = 0;

	        JTextField korisnickoImeField = new JTextField(gost.getKorisnickoIme());
	        korisnickoImeField.setColumns(10);
	        addComponent(podaciPanel, new JLabel("Korisničko ime:"), korisnickoImeField, gbc, y++);

	        JTextField imeField = new JTextField(10);
	        imeField.setText(gost.getIme());
	        addComponent(podaciPanel, new JLabel("Ime:"), imeField, gbc, y++);

	        JTextField prezimeField = new JTextField(10);
	        prezimeField.setText(gost.getPrezime());
	        addComponent(podaciPanel, new JLabel("Prezime:"), prezimeField, gbc, y++);

	        JComboBox<String> polBox = new JComboBox<>();
	        polBox.addItem("Musko");
	        polBox.addItem("Zensko");
	        polBox.setSelectedItem(gost.getPol());
	        addComponent(podaciPanel, new JLabel("Pol:"), polBox, gbc, y++);

	        JDateChooser datumRodjenjaField = new JDateChooser();
	        datumRodjenjaField.setDate(Date.from(gost.getDatumRodjenja().atStartOfDay(ZoneId.systemDefault()).toInstant()));
	        addComponent(podaciPanel, new JLabel("Datum rođenja:"), datumRodjenjaField, gbc, y++);

	        JTextField telefonField = new JTextField(10);
	        telefonField.setText(gost.getTelefon());
	        addComponent(podaciPanel, new JLabel("Telefon:"), telefonField, gbc, y++);

	        JTextField adresaField = new JTextField(10);
	        adresaField.setText(gost.getAdresa());
	        addComponent(podaciPanel, new JLabel("Adresa:"), adresaField, gbc, y++);

	        JPasswordField lozinkaField = new JPasswordField(10);
	        lozinkaField.setText(gost.getLozinka());
	        addComponent(podaciPanel, new JLabel("Lozinka:"), lozinkaField, gbc, y++);

	        JButton updateButton = new JButton("Update");
	        gbc.gridx = 0;
	        gbc.gridy = y++;
	        gbc.gridwidth = 2;
	        gbc.anchor = GridBagConstraints.CENTER;
	        podaciPanel.add(updateButton, gbc);

	        JScrollPane scrollPane = new JScrollPane(podaciPanel);
	        izmenaFrame.add(scrollPane);

	        updateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String korisnickoIme = korisnickoImeField.getText();
	                String ime = imeField.getText();
	                String prezime = prezimeField.getText();
	                String pol = polBox.getSelectedItem().toString();
	                String telefon = telefonField.getText();
	                String datumRodjenja = LocalDate.ofInstant(datumRodjenjaField.getDate().toInstant(), ZoneId.systemDefault()).toString();
	                String adresa = adresaField.getText();
	                String lozinka = new String(lozinkaField.getPassword());

	                String poruka = AdminManager.getInstance().izmeniPodatkeGosta(gost.getKorisnickoIme(), korisnickoIme, ime,
	                        prezime, pol, datumRodjenja, telefon, adresa, lozinka);

	                JOptionPane.showMessageDialog(podaciPanel, poruka);
	            }
	        });

	        izmenaFrame.setVisible(true);
	}
	
	public JPanel gostiPanel() {
		JPanel gostiPanel = new JPanel(new BorderLayout());
		
		GostTableModel model = new GostTableModel();
		JTable gostTable = new JTable(model);
		TableRowSorter<GostTableModel> sorter = new TableRowSorter<>(model);
		gostTable.setRowSorter(sorter);
		
		JScrollPane scrollPanel = new JScrollPane(gostTable);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel buttonPanel = new JPanel();
		JButton obrisiButton = new JButton("Obrisi");
        JButton izmeniButton = new JButton("Izmeni");
        JTextField imeField = new JTextField(15);
        JButton filterButton = new JButton("Filter");
        
        buttonPanel.add(new JLabel("Email gosta: "));
        buttonPanel.add(imeField);
        buttonPanel.add(filterButton);
        buttonPanel.add(izmeniButton);
        buttonPanel.add(obrisiButton);
        
        obrisiButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
        			int modelRowIndex = gostTable.convertRowIndexToModel(gostTable.getSelectedRow());
					Gost gost = model.getGost(modelRowIndex);
        			
        			String poruka = AdminManager.getInstance().obrisiGosta(gost.getKorisnickoIme());
					
					JOptionPane.showMessageDialog(buttonPanel, poruka);
					
					model.removeGost(modelRowIndex);
					model.fireTableDataChanged();
					gostTable.updateUI();
        		}catch(Exception err) {
        			JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan ni jedan red tabele");
        		}
					
			}
        });
        
        izmeniButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
        			int modelRowIndex = gostTable.convertRowIndexToModel(gostTable.getSelectedRow());
					Gost gost = model.getGost(modelRowIndex);
        			
					izmeniGosta(gost, gostTable);
        		
					gostTable.updateUI();
        		}catch(Exception err) {
        			JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan ni jedan red tabele");
        		}
			}
        	
        });
        filterButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		String imeText = imeField.getText();
                if (imeText.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + imeText, 0));
                }
			}
	    });
		
        gostiPanel.add(buttonPanel, BorderLayout.PAGE_START);
		gostiPanel.add(scrollPanel);
		return gostiPanel;
    }

    private void redKorisnika(JPanel panel, Korisnik korisnik) {
        JLabel korisnickoImeLabel = new JLabel(korisnik.getKorisnickoIme());

        JTextField imeField = new JTextField(10);
        imeField.setText(korisnik.getIme());

        JTextField prezimeField = new JTextField(10);
        prezimeField.setText(korisnik.getPrezime());

        JComboBox<String> polBox = new JComboBox<>();
        polBox.addItem("Musko");
        polBox.addItem("Zensko");
        polBox.setSelectedItem(korisnik.getPol());

        JDateChooser datumRodjenja = new JDateChooser();
        datumRodjenja.setDate(Date.from(korisnik.getDatumRodjenja().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        JTextField telefonField = new JTextField(10);
        telefonField.setText(korisnik.getTelefon());

        JTextField adresaField = new JTextField(10);
        adresaField.setText(korisnik.getAdresa());

        JPasswordField lozinkaField = new JPasswordField(10);
        lozinkaField.setText(korisnik.getLozinka());

        panel.add(korisnickoImeLabel);
        panel.add(imeField);
        panel.add(prezimeField);
        panel.add(polBox);
        panel.add(datumRodjenja);
        panel.add(telefonField);
        panel.add(adresaField);
        panel.add(lozinkaField);
    }
    
    private JPanel dodajSobuPanel() {
    	JPanel dodajSobuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

       
        gbc.gridx = 0;
        gbc.gridy = 0;
        dodajSobuPanel.add(new JLabel("Sifra Sobe:"), gbc);

        gbc.gridx = 1;
        JTextField sifraSobeField = new JTextField(5);
        dodajSobuPanel.add(sifraSobeField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        dodajSobuPanel.add(new JLabel("Tip Sobe:"), gbc);

        gbc.gridx = 1;
        JComboBox<TipSobeEnum> tipSobeBox = new JComboBox<>();
        tipSobeBox.addItem(TipSobeEnum.JEDNOKREVETNA);
        tipSobeBox.addItem(TipSobeEnum.DVOKREVETNA);
        tipSobeBox.addItem(TipSobeEnum.ODVOKREVETNA_DVA);
        tipSobeBox.addItem(TipSobeEnum.TROKREVETNA);
        dodajSobuPanel.add(tipSobeBox, gbc);

       
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton dodajButton = new JButton("Dodaj");
        dodajSobuPanel.add(dodajButton, gbc);

        
        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sifraSobe = sifraSobeField.getText();
                TipSobeEnum tipSobe = (TipSobeEnum) tipSobeBox.getSelectedItem();
                String poruka = AdminManager.getInstance().dodajSobu(sifraSobe, tipSobe);
                JOptionPane.showMessageDialog(dodajSobuPanel, poruka);
            }
        });

        return dodajSobuPanel;
    }
	
    private JPanel sobePanel() {
    	JPanel sobePanel = new JPanel(new BorderLayout());
    	
    	SobaTableModel model = new SobaTableModel();
		JTable sobeTable = new JTable(model);
		TableRowSorter<SobaTableModel> sorter = new TableRowSorter<>(model);
		sobeTable.setRowSorter(sorter);
		
		JScrollPane scrollPanel = new JScrollPane(sobeTable);
		
		JPanel buttonPanel = new JPanel();
		JButton izmeniSobu = new JButton("Izmeni Sobu");
		JButton obrisiSobu = new JButton("Obrisi Sobu");
		JTextField sifraField = new JTextField(5);
		JComboBox<String> tipBox= new JComboBox<String>();
		tipBox.addItem("Default");
		tipBox.addItem("Jednokrevetna (1)");
		tipBox.addItem("Dvokrevetna (2)");
		tipBox.addItem("Dvokrevetna (1+1)");
		tipBox.addItem("Trokrevetna (2+1)");
        JButton filterButton = new JButton("Filter");
        
        buttonPanel.add(new JLabel("Sifra sobe: "));
        buttonPanel.add(sifraField);
        buttonPanel.add(tipBox);
        buttonPanel.add(filterButton);
		buttonPanel.add(izmeniSobu);
		buttonPanel.add(obrisiSobu);
		
		obrisiSobu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int modelRowIndex = sobeTable.convertRowIndexToModel(sobeTable.getSelectedRow());
					Soba s = model.getSobe(modelRowIndex);
					
					String poruka = AdminManager.getInstance().izbrisiSobu(Integer.toString(s.getSifra()));
					JOptionPane.showMessageDialog(buttonPanel, poruka);
					
					model.removeSoba(modelRowIndex);
        			model.fireTableDataChanged();
					sobeTable.updateUI();
				}catch(Exception err) {
					JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan ni jedan red tabele");
				}
				
			}
		});
		
		izmeniSobu.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				try {
					int modelRowIndex = sobeTable.convertRowIndexToModel(sobeTable.getSelectedRow());
					Soba s = model.getSobe(modelRowIndex);
					
					izmeniSobuFrame(s);
					
					sobeTable.updateUI();
				}catch(Exception err) {
					JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan ni jedan red tabele");
				}
				
			}
		});
		
		filterButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>(3);

                String sifraText = sifraField.getText();
                if (sifraText.length() > 0) {
                    filters.add(RowFilter.regexFilter("(?i)" + sifraText, 0));
                }

                
                String tipSobe = tipBox.getSelectedItem().toString();
                if (!tipSobe.equals("Default")) {
                	filters.add(new RowFilter<Object, Object>() {
                        @Override
                        public boolean include(Entry<? extends Object, ? extends Object> entry) {
                            String tip = (String) entry.getValue(1);
                            return tip.equals(tipSobe);
                        }
                    });
                }

                RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
                sorter.setRowFilter(combinedFilter);
			}
	    });
		
		sobePanel.add(buttonPanel, BorderLayout.PAGE_START);
		sobePanel.add(scrollPanel);
    	return sobePanel;
    }
    
    private void izmeniSobuFrame(Soba s) {
    	JFrame izmeniSobuFrame = new JFrame();
        
        izmeniSobuFrame.setSize(300, 300);
        izmeniSobuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        izmeniSobuFrame.setTitle("Izmena sobe");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        JTextField sifraField = new JTextField(Integer.toString(s.getSifra()));
        sifraField.setColumns(5);
        addComponent(panel, new JLabel("Šifra:"), sifraField, gbc, y++);

        JComboBox<String> tipSobeBox = new JComboBox<>();
        tipSobeBox.addItem(TipSobeEnum.JEDNOKREVETNA.toString());
        tipSobeBox.addItem(TipSobeEnum.DVOKREVETNA.toString());
        tipSobeBox.addItem(TipSobeEnum.ODVOKREVETNA_DVA.toString());
        tipSobeBox.addItem(TipSobeEnum.TROKREVETNA.toString());
        tipSobeBox.setSelectedItem(s.getNazivSobe());
        addComponent(panel, new JLabel("Tip sobe:"), tipSobeBox, gbc, y++);

        JButton updateButton = new JButton("Update");
        gbc.gridx = 1;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        panel.add(updateButton, gbc);

        JTextField noviDodatakField = new JTextField(10);
        addComponent(panel, new JLabel("Novi dodatak:"), noviDodatakField, gbc, y++);

        JButton dodajDodatno = new JButton("Dodaj Dodatno");
        gbc.gridx = 1;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        panel.add(dodajDodatno, gbc);

        for (String dodatno : s.getAmenities()) {
            JLabel dodatnoLabel = new JLabel(dodatno);
            JButton obrisi = new JButton("Obrisi");

            gbc.gridx = 0;
            gbc.gridy = y;
            gbc.gridwidth = 1;
            panel.add(dodatnoLabel, gbc);

            gbc.gridx = 1;
            panel.add(obrisi, gbc);

            y++;

            obrisi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String poruka = AdminManager.getInstance().izbaciDodatnoSoba(s, dodatnoLabel.getText());
                    JOptionPane.showMessageDialog(panel, poruka);
                }
            });
        }

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String poruka = AdminManager.getInstance().izmeniSobu(Integer.toString(s.getSifra()), sifraField.getText(), tipSobeBox.getSelectedItem().toString());
                JOptionPane.showMessageDialog(panel, poruka);
            }
        });

        dodajDodatno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String poruka = AdminManager.getInstance().dodajDodatnoSoba(s, noviDodatakField.getText());
                JOptionPane.showMessageDialog(panel, poruka);
            }
        });

        JScrollPane scrollPane = new JScrollPane(panel);
        izmeniSobuFrame.add(scrollPane);

        izmeniSobuFrame.setVisible(true);
    }
	
	private JPanel izvestajiPanel() {
		JPanel izvestajiPanel = new JPanel();
		
		izvestajiPanel.add(new JLabel("Izvestaji"));
		
		return izvestajiPanel;
	}
	
	
	
	private class NavbarButtonListener implements ActionListener {
		private String viewName;
		public NavbarButtonListener(String viewName) {
			this.viewName = viewName;
		}
		@Override 
		public void actionPerformed(ActionEvent e) {
			if(viewName.equals("Gosti")) {
				contentPanel.remove(gostiPanel());
				contentPanel.add(gostiPanel(), viewName);
			}else if(viewName.equals("Zaposleni")) {
				contentPanel.remove(zaposleniPanel());
				contentPanel.add(zaposleniPanel(), viewName);
			}else if(viewName.equals("Sobe")) {
				contentPanel.remove(sobePanel());
				contentPanel.add(sobePanel(), viewName);
			}
			cardLayout.show(contentPanel, viewName);
		}
		
	}
}
