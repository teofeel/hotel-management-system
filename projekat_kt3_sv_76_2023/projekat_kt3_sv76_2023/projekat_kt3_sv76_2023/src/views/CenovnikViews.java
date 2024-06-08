package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;

import entity.DodatneUsluge;
import manager.AdminManager;
import manager.CenovnikManager;

import java.awt.*;
import java.util.*;

import com.toedter.calendar.JDateChooser;

import manager.*;
import entity.*;
import enumi.*;

public class CenovnikViews extends JFrame{
	private JPanel contentPanel;
	private CardLayout cardLayout;
	private JToolBar navbar;
	
	private JButton cenovniciButton;
	private JButton definisiCenovikButton;
	private JButton uslugeButton;
	
	public CenovnikViews() {
		setTitle("Cenovnici");
        setSize(1200, 750);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        navbar = new JToolBar();
        navbar.setFloatable(false);
        
        cenovniciButton = new JButton("Cenovnici");
        cenovniciButton.addActionListener(new NavbarButtonListener("Cenovnici"));
        
        definisiCenovikButton = new JButton("Definisi novi cenovnik");
        definisiCenovikButton.addActionListener(new NavbarButtonListener("DefinisanjeCenovnika"));
        
        uslugeButton = new JButton("Usluge");
        uslugeButton.addActionListener(new NavbarButtonListener("Usluge"));
        
        
        navbar.add(cenovniciButton);
        navbar.add(definisiCenovikButton);
        navbar.add(uslugeButton);
        
        cardLayout = new CardLayout();
		contentPanel = new JPanel(cardLayout);
		
		contentPanel.add(this.cenovniciPanel(), "Cenovnici");
		contentPanel.add(this.definisiCenovnikPanel(), "DefinisanjeCenovnika");
		contentPanel.add(this.uslugePanel(), "Usluge");
		
        add(navbar,BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        setVisible(true);
	}
	
	private JPanel cenovniciPanel() {
		JPanel cenovniciPanel = new JPanel(new BorderLayout());
        
        cenovniciPanel.add(new JLabel("Cenovnici"), BorderLayout.NORTH);
        
        if (CenovnikManager.cenovnici.size() == 0) {
            return cenovniciPanel;
        }
        
        JPanel cenovniciListaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int y = 0;
        
        for (Cenovnik c : CenovnikManager.cenovnici) {
            JLabel vazenjeOdLabel = new JLabel(c.getVaziOd().toString());
            JLabel vazenjeDoLabel = new JLabel(c.getVaziDo().toString());
            JLabel aktivanLabel = new JLabel(c.getAktivan() ? "Aktivan" : "Neaktivan");
            
            JButton izmenaButton = new JButton("Izmeni");
            izmenaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new CenovnikFrame(c);
                }
            });
            
            JButton deaktivirajButton = new JButton(c.getAktivan() ? "Deaktiviraj" : "Aktiviraj");
            deaktivirajButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    c.setAktivan(!c.getAktivan());
                    aktivanLabel.setText(c.getAktivan() ? "Aktivan" : "Neaktivan");
                    deaktivirajButton.setText(c.getAktivan() ? "Deaktiviraj" : "Aktiviraj");
                    JOptionPane.showMessageDialog(cenovniciListaPanel, "Izmenjena aktivnost cenovnika");
                }
            });
            
            gbc.gridx = 0;
            gbc.gridy = y;
            cenovniciListaPanel.add(vazenjeOdLabel, gbc);
            
            gbc.gridx = 1;
            cenovniciListaPanel.add(vazenjeDoLabel, gbc);
            
            gbc.gridx = 2;
            cenovniciListaPanel.add(aktivanLabel, gbc);
            
            gbc.gridx = 3;
            cenovniciListaPanel.add(izmenaButton, gbc);
            
            gbc.gridx = 4;
            cenovniciListaPanel.add(deaktivirajButton, gbc);
            
            y++;
        }
        
        JScrollPane scrollPane = new JScrollPane(cenovniciListaPanel);
        cenovniciPanel.add(scrollPane, BorderLayout.CENTER);
        
        return cenovniciPanel;
	}
	
	
	
	private JPanel definisiCenovnikPanel() {
		 	JPanel definisiCenovnikPanel = new JPanel();
		    definisiCenovnikPanel.setLayout(new GridBagLayout());
		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.insets = new Insets(5, 5, 5, 5);
		    gbc.anchor = GridBagConstraints.WEST;

		    JLabel titleLabel = new JLabel("Definisi");
		    gbc.gridx = 0;
		    gbc.gridy = 0;
		    gbc.gridwidth = 2;
		    definisiCenovnikPanel.add(titleLabel, gbc);
		    gbc.gridwidth = 1;

		    gbc.gridy++;
		    gbc.gridx = 0;
		    definisiCenovnikPanel.add(new JLabel("Važi od:"), gbc);

		    JDateChooser vaziOdPicker = new JDateChooser();
		    vaziOdPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		    gbc.gridx = 1;
		    definisiCenovnikPanel.add(vaziOdPicker, gbc);

		    gbc.gridy++;
		    gbc.gridx = 0;
		    definisiCenovnikPanel.add(new JLabel("Važi do:"), gbc);

		    JDateChooser vaziDoPicker = new JDateChooser();
		    vaziDoPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		    gbc.gridx = 1;
		    definisiCenovnikPanel.add(vaziDoPicker, gbc);

		    gbc.gridy++;
		    gbc.gridx = 0;
		    definisiCenovnikPanel.add(new JLabel("Status:"), gbc);

		    JComboBox<String> aktivanBox = new JComboBox<>();
		    aktivanBox.addItem("Aktivan");
		    aktivanBox.addItem("Neaktivan");
		    gbc.gridx = 1;
		    definisiCenovnikPanel.add(aktivanBox, gbc);

		    gbc.gridy++;
		    gbc.gridx = 0;
		    gbc.gridwidth = 2;
		    gbc.anchor = GridBagConstraints.CENTER;

		    JButton dodajButton = new JButton("Dodaj");
		    dodajButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            LocalDate vaziOd = LocalDate.ofInstant(vaziOdPicker.getDate().toInstant(), ZoneId.systemDefault());
		            LocalDate vaziDo = LocalDate.ofInstant(vaziDoPicker.getDate().toInstant(), ZoneId.systemDefault());

		            if (vaziDo.isBefore(vaziOd)) {
		                JOptionPane.showMessageDialog(definisiCenovnikPanel, "Datum 'Važi do' mora biti nakon datuma 'Važi od'.", "Greška", JOptionPane.ERROR_MESSAGE);
		                return;
		            }

		            String aktivanStr = (String) aktivanBox.getSelectedItem();
		            boolean aktivan = aktivanStr.equals("Aktivan");

		            String poruka = AdminManager.getInstance().definisanjeCenovnika(vaziOd.toString(), vaziDo.toString(), aktivan);
		            JOptionPane.showMessageDialog(definisiCenovnikPanel, poruka);
		        }
		    });

		    definisiCenovnikPanel.add(dodajButton, gbc);

		    return definisiCenovnikPanel;
		/*JPanel definisiCenovnikPanel = new JPanel();
		
		definisiCenovnikPanel.add(new JLabel("Definisi"));
		
		JDateChooser vaziOdPicker = new JDateChooser();
		vaziOdPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		JDateChooser vaziDoPicker = new JDateChooser();
		vaziDoPicker.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		JComboBox<String> aktivanBox = new JComboBox<String>();
		aktivanBox.addItem("Aktivan");
		aktivanBox.addItem("Neaktivan");
		
		JButton dodajButton = new JButton("Dodaj");
		
		dodajButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String vaziOd = LocalDate.ofInstant(vaziOdPicker.getDate().toInstant(), ZoneId.systemDefault()).toString();
				String vaziDo= LocalDate.ofInstant(vaziDoPicker.getDate().toInstant(), ZoneId.systemDefault()).toString();
				
				String aktivanStr = (String) aktivanBox.getSelectedItem();
				boolean aktivan = false;
				
				if(aktivanStr.equals("Aktivan")) {
					aktivan = true;
				}
				
				String poruka = AdminManager.getInstance().definisanjeCenovnika(vaziOd, vaziDo, aktivan);
				
				JOptionPane.showMessageDialog(definisiCenovnikPanel, poruka);
			}
		});
		
		definisiCenovnikPanel.add(vaziOdPicker);
		definisiCenovnikPanel.add(vaziDoPicker);
		definisiCenovnikPanel.add(aktivanBox);
		definisiCenovnikPanel.add(dodajButton);
		
		return definisiCenovnikPanel;*/
	}
	
	private JPanel uslugePanel() {
		JPanel uslugePanel = new JPanel(new BorderLayout());

        JPanel dodajUsluguPanel = new JPanel();

        JTextField nazivUslugeField = new JTextField(12);
        JTextField cenaField = new JTextField(5);
        JButton dodajUsluguButton = new JButton("Dodaj uslugu");

        dodajUsluguButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String poruka = CenovnikManager.getInstance().dodajNovuUslugu(nazivUslugeField.getText(), cenaField.getText());
                JOptionPane.showMessageDialog(dodajUsluguPanel, poruka);
               
                uslugePanel.removeAll();
                uslugePanel.add(uslugePanel());
                uslugePanel.revalidate();
                uslugePanel.repaint();
            }
        });

        dodajUsluguPanel.add(new JLabel("Naziv usluge:"));
        dodajUsluguPanel.add(nazivUslugeField);
        dodajUsluguPanel.add(new JLabel("Cena:"));
        dodajUsluguPanel.add(cenaField);
        dodajUsluguPanel.add(dodajUsluguButton);

        uslugePanel.add(dodajUsluguPanel, BorderLayout.NORTH);
		
		if(CenovnikManager.cenovnici.size()==0 || CenovnikManager.cenovnici.get(0).getDodatneUsluge().size()==0)
			return uslugePanel;
		
		JPanel listaUslugaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int y = 0;

        if (CenovnikManager.cenovnici.size() != 0 && CenovnikManager.cenovnici.get(0).getDodatneUsluge().size() != 0) {
            for (Map.Entry<String, DodatneUsluge> entry : CenovnikManager.cenovnici.get(0).getDodatneUsluge().entrySet()) {
                DodatneUsluge du = entry.getValue();
                JLabel nazivUslugeLabel = new JLabel(du.getNaziv());
                JLabel cenaUslugeLabel = new JLabel(Double.toString(du.getCena()));
                JButton izbrisiUsluguButton = new JButton("Izbrisi");

                izbrisiUsluguButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        uslugePanel.removeAll();
                        uslugePanel.add(uslugePanel());
                        uslugePanel.revalidate();
                        uslugePanel.repaint();
                    }
                });

                gbc.gridx = 0;
                gbc.gridy = y;
                listaUslugaPanel.add(nazivUslugeLabel, gbc);

                gbc.gridx = 1;
                listaUslugaPanel.add(cenaUslugeLabel, gbc);

                gbc.gridx = 2;
                listaUslugaPanel.add(izbrisiUsluguButton, gbc);

                y++;
            }
        }

        JScrollPane scrollPane = new JScrollPane(listaUslugaPanel);
        uslugePanel.add(scrollPane, BorderLayout.CENTER);

        return uslugePanel;
	}
	
	
	private class NavbarButtonListener implements ActionListener {
		private String viewName;
		public NavbarButtonListener(String viewName) {
			this.viewName = viewName;
		}
		@Override 
		public void actionPerformed(ActionEvent e) {
			if(viewName.equals("Cenovnici")) {
				contentPanel.remove(cenovniciPanel());
				contentPanel.add(cenovniciPanel(), viewName);
			}
			cardLayout.show(contentPanel, viewName);
		}
		
	}
}
