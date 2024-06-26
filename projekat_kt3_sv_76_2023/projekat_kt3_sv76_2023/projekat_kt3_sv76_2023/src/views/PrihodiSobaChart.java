package views;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.time.LocalDate;
import java.time.format.TextStyle;

import java.util.*;
import manager.*;

public class PrihodiSobaChart extends JFrame{
	public PrihodiSobaChart(){
		setSize(600,600);
		setTitle("Chart");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JFreeChart chart = createChart(createDataset());
		
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 600));
        setContentPane(chartPanel);
        
        setVisible(true);
	}
	
	private DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		getPrihod(dataset, "Jednokrevetna (1)");
		getPrihod(dataset, "Dvokrevetna (2)");
		getPrihod(dataset, "Dvokrevetna (1+1)");
		getPrihod(dataset, "Trokrevetna (2+1)");
		getPrihod(dataset, "Ukupno");
		
		return dataset;
	}
	
	private void getPrihod(DefaultCategoryDataset dataset, String tip) {
		
		ArrayList<Float> prihodi = new ArrayList<Float>();
		prihodi = IzvestajiManager.getInstance().prihodiZadnjaGodina(tip);
		Collections.reverse(prihodi);

		for (int i = 0; i < prihodi.size(); i++) {
            Float prihod = prihodi.get(i);
            String mesec = getMesec(prihodi.size() - 1 - i);
            dataset.addValue(prihod, tip, mesec);
        }
		
	}
	
	private JFreeChart createChart(DefaultCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createLineChart(
                "Prihod po tipu sobe",
                "Mesec",
                "Prihod",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();

    
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesPaint(3, Color.ORANGE);
        renderer.setSeriesPaint(4, Color.BLACK);
        
        plot.setRenderer(renderer);

        return chart;
	}
	
	private String getMesec(int num) {
		LocalDate sada = LocalDate.now().minusMonths(num);
        return sada.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	}
}
