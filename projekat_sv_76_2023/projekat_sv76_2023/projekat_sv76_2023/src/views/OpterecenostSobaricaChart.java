package views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.LocalDate;
import java.time.format.TextStyle;

import java.util.*;
import manager.*;
import entity.*;

public class OpterecenostSobaricaChart extends JFrame{
	public OpterecenostSobaricaChart(){
		setSize(500,500);
		setTitle("Opterecenost Sobarica");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JFreeChart chart = createChart(createDataset());
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 500));
        setContentPane(chartPanel);
        
        setVisible(true);
	}
	
	  private static JFreeChart createChart(DefaultPieDataset dataset) {
	        JFreeChart chart = ChartFactory.createPieChart(
	                "Opterecenost Sobarica",   
	                dataset,             
	                true,                
	                true,
	                false);

	        PiePlot plot = (PiePlot) chart.getPlot();
	        
	        Random rand = new Random();
	        for(Sobarica s:SobaricaManager.sobarice.values()) {
	        	 plot.setSectionPaint(s.getKorisnickoIme(), new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
	        }

	        return chart;
	    }
	
	private static DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<String, Integer> opterecenost = new HashMap<String, Integer>();
        
        opterecenost = IzvestajiManager.getInstance().opterecenostSobarica();
        
        for(Map.Entry<String, Integer> entry:opterecenost.entrySet()) {
        	 dataset.setValue(entry.getKey(), entry.getValue());
        }
        
     
        return dataset;
    }
}
