package views;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import entity.*;
import manager.*;
import enumi.*;

import javax.swing.*;
import javax.swing.RowFilter.Entry;
import javax.swing.table.TableRowSorter;

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
		
		SobaTableModel model = new SobaTableModel();
		JTable sobeTable = new JTable(model);
		TableRowSorter<SobaTableModel> sorter = new TableRowSorter<>(model);
		sobeTable.setRowSorter(sorter);
		
		RowFilter<SobaTableModel, Object> customFilter = new RowFilter<SobaTableModel, Object>() {
		    @Override
		    public boolean include(Entry<? extends SobaTableModel, ? extends Object> entry) {
		       for(Soba s:SobaricaManager.sobarice.get(korisnickoIme).getDodeljeneSobe().values()) {
		    	   if(s.getSifra() == (int) entry.getValue(0))
		    		   return true;
		       }
		       return false;
		    }
		};
		sorter.setRowFilter(customFilter);
		
		JScrollPane scrollPanel =  new JScrollPane(sobeTable);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pregledSobaPanel.add(scrollPanel);
		
		JPanel buttonPanel = new JPanel();
		JButton srediSobuButton = new JButton("Sredjena soba");
		buttonPanel.add(srediSobuButton);
		pregledSobaPanel.add(buttonPanel, BorderLayout.PAGE_START);
        srediSobuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		Soba soba = model.getSobe(sobeTable.convertRowIndexToModel(sobeTable.getSelectedRow()) );
            		
            		String poruka = SobaricaManager.getInstance().sredjenaSoba(soba, SobaricaManager.sobarice.get(korisnickoIme));
                    JOptionPane.showMessageDialog(pregledSobaPanel, poruka);
                    
                    sorter.setRowFilter(customFilter);
                    sobeTable.updateUI();
            	}catch(Exception err) {
            		JOptionPane.showMessageDialog(buttonPanel, "Nije selektovan red tabele");
            	}
                
                
            }
        });

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
