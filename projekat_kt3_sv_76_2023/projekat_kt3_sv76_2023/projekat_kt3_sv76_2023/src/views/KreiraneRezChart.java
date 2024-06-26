package views;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import entity.*;
import manager.*;

public class KreiraneRezChart extends JFrame{
	public KreiraneRezChart(){
		setSize(500,500);
		setTitle("Kreirane Rezervacije");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JFreeChart chart = createChart(createDataset());
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(500, 500));
        setContentPane(chartPanel);
        
        setVisible(true);
	}
	
	  private static JFreeChart createChart(DefaultPieDataset dataset) {
	        JFreeChart chart = ChartFactory.createPieChart(
	                "Kreirane Rezervacije",   
	                dataset,             
	                true,                
	                true,
	                false);

	        PiePlot plot = (PiePlot) chart.getPlot();
	        
	        Random rand = new Random();
	        
	        plot.setSectionPaint("NA_CEKANJU", new Color(123, 104, 238));
	        plot.setSectionPaint("POTVRDJENE", new Color(255, 165, 0));
	        plot.setSectionPaint("OTKAZANE", new Color(60, 179, 113));
	        plot.setSectionPaint("ODBIJENE", new Color(255, 20, 147));

	        return chart;
	    }
	
	private static DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        HashMap<String, Integer> opterecenost = new HashMap<String, Integer>();
        
        opterecenost = IzvestajiManager.getInstance().statusRezervacijaPrethodniMesec();
        
        for(Map.Entry<String, Integer> entry:opterecenost.entrySet()) {
        	 dataset.setValue(entry.getKey(), entry.getValue());
        }
        
     
        return dataset;
    }
}
