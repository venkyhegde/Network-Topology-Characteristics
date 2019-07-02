package edu.uncc.algos.util;

import edu.uncc.algos.graph.weighted.Graph;
import edu.uncc.algos.graph.weighted.UndirectedGraph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author venky
 */
public class TopologyUtil {

    public static double formatDouble(double d) {
        DecimalFormat df = new DecimalFormat ("###.##");
        double result = Double.valueOf (df.format (d));
        return result;
    }

    public static void scaleFreeRandomGraph(HashMap<Integer, Integer> degreeNodes, String title, String xLabel,
                                       String yLabel){
        SwingUtilities.invokeLater(() -> {
            LineChart lineChart = new LineChart(degreeNodes, title, xLabel, yLabel);
            lineChart.setVisible(true);
        });

    }

    public static UndirectedGraph<Integer> loadGraph(int graphId) throws FileNotFoundException{
        UndirectedGraph<Integer> graph = new UndirectedGraph<> ();

        // construct file url
        //String fileUrl = "resources/graph"+1+".txt";

        //	1 --> demo example one
        //  2 --> ScaleFree network
        //  3 --> Random network
        //	4 --> Random
        String fileUrlNode = "resources/GraphNodes"+graphId+".txt";
        String fileUrlEdge = "resources/GraphEdges"+graphId+".txt";


        File graphFile = new File (fileUrlNode);
        String filePath = graphFile.getPath ();
        Scanner sc = null;
        try {
            sc= new Scanner (graphFile);
        }catch ( FileNotFoundException e ){
            throw new FileNotFoundException ("Invalid file path");
        }

        File graphFile1 = new File (fileUrlEdge);
        String filePath1 = graphFile.getPath ();
        Scanner sc1 = null;
        try {
            sc1= new Scanner (graphFile1);
        }catch ( FileNotFoundException e ){
            throw new FileNotFoundException ("Invalid file path");
        }

        while ( sc.hasNextLine () )
        {
            String line = sc.nextLine ();
            graph.addVertex (Integer.parseInt (line.trim ()));
        }

        while ( sc1.hasNextLine () )
        {
            String line = sc1.nextLine ();
            String[] edge = line.trim ().split (" ");
            int w = edge.length > 2 ? Integer.parseInt (edge[2].trim ()) : 0;
            graph.addEdge (Integer.parseInt (edge[0].trim ()),Integer.parseInt (edge[1].trim ()),
                    w);
            // graph.addEdge (Integer.parseInt (line.trim ()));
        }



//        File graphFile = new File (fileUrl);
//        String filePath = graphFile.getPath ();
//        Scanner sc = null;
//        try {
//            sc= new Scanner (graphFile);
//        }catch ( FileNotFoundException e ){
//            throw new FileNotFoundException ("Invalid file path");
//        }
//
//

//        while ( sc.hasNextLine () ){
//            String line = sc.nextLine ();
//            if ( !line.trim ().startsWith ("e") ){
//                graph.addVertex (Integer.parseInt (line.trim ()));
//            } else {
//                String[] edge = line.trim ().split (",");
//                graph.addEdge (Integer.parseInt (edge[1].trim ()),Integer.parseInt (edge[2].trim ()),
//                        Integer.parseInt (edge[3].trim ()));
//            }
//        }

        return graph;

    }

}



class LineChart extends JFrame {

    public LineChart(HashMap<Integer, Integer> degreeNodes, String title, String xLabel, String yLabel) {
        initFrame (degreeNodes, title, xLabel, yLabel);
    }

    private void initFrame(HashMap<Integer, Integer> degreeNodes, String title, String xLabel, String yLabel) {

        XYDataset dataset = createDataset (degreeNodes);
        JFreeChart chart = createChart (dataset, title, xLabel, yLabel);
        ChartPanel chartPanel = new ChartPanel (chart);
        chartPanel.setBorder (BorderFactory.createEmptyBorder (15, 15, 15, 15));
        chartPanel.setBackground (Color.white);
        add (chartPanel);

        pack ();
        setTitle (title);
        setLocationRelativeTo (null);
        setDefaultCloseOperation (JFrame.HIDE_ON_CLOSE);

    }

    private XYDataset createDataset(HashMap<Integer, Integer> degreeNodes) {
        XYSeries series = new XYSeries ("Node");
        degreeNodes.forEach ((k, v) -> {
            series.add (k, v);
        });

        XYSeriesCollection dataset = new XYSeriesCollection ();
        dataset.addSeries (series);
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, String title, String xLabel, String yLabel) {

        JFreeChart chart = ChartFactory.createXYLineChart (title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL,
                true, true, false);
        XYPlot plot = chart.getXYPlot ();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer ();
        renderer.setSeriesPaint (0, Color.RED);
        renderer.setSeriesStroke (0, new BasicStroke (2.0f));

        plot.setRenderer (renderer);
        plot.setBackgroundPaint (Color.white);

        plot.setRangeGridlinesVisible (true);
        plot.setRangeGridlinePaint (Color.BLACK);

        plot.setDomainGridlinesVisible (true);
        plot.setDomainGridlinePaint (Color.BLACK);


        chart.setTitle (new TextTitle (title, new Font ("Serif", java.awt.Font.BOLD, 18)));
        return chart;

    }
}
