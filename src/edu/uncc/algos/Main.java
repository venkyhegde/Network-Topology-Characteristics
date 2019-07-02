package edu.uncc.algos;

import edu.uncc.algos.graph.weighted.Graph;
import edu.uncc.algos.graph.weighted.UndirectedGraph;
import edu.uncc.algos.topology.NetworkTopology;
import edu.uncc.algos.util.TopologyUtil;


import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean showOptions = false;
        UndirectedGraph<Integer> graph = null;
        System.out.println ("Network Topology Properties" +
                ".\n=====================================================\nPlease follow instructions and select " +
                "appropriate options" +
                ".");
        Scanner sc = new Scanner (System.in);
        System.out.println ("I. Please select a graph");
        System.out.println ("Enter 1 for Graph 1");
        System.out.println ("Enter 2 for Graph 2");
        System.out.println ("Enter 3 for Graph 3");
        System.out.println ("Enter 4 for Graph 4");

        int graphId = sc.nextInt ();
        try {
            graph = TopologyUtil.loadGraph (graphId);
            showOptions = true;
        }
        catch ( FileNotFoundException e ) {
            e.printStackTrace ();
        }
        System.out.println ("\nAdjacency list representation of graph " + graphId);
        System.out.println (graph);

        NetworkTopology nt = new NetworkTopology ();

        while ( showOptions ) {
            System.out.println ("Select any Topology property");
            System.out.println ("1. Degree of nodes and Distribution");
            System.out.println ("2. Strength of nodes and Distribution");
            System.out.println ("3. Clustering coefficient of network");
            System.out.println ("4. Characteristic path length");
            System.out.println ("5. Random or Scalefree network");
            System.out.println ("6. All");
            System.out.println ("7. Exit");

            int option = sc.nextInt ();

            switch ( option ) {
                case 1:
                    System.out.println ("Property 1 - Degree of nodes and Distribution");
                    nt.degreeDistribution (graph);
                    System.out.println ("======================================================");
                    break;
                case 2:
                    System.out.println ("Property 2 - Strength of nodes and Distribution");
                    nt.strengthDistribution (graph);
                    System.out.println ("======================================================");
                    break;
                case 3:
                    System.out.println ("Property 3 - Clustering Coefficient of Network");
                    nt.clusteringCoefficient (graph);
                    System.out.println ("======================================================");
                    break;
                case 4:
                    System.out.println ("Property 4 - Characteristic path length");
                    nt.characteristicPathLength(graph);
                    System.out.println ("======================================================");
                    break;
                case 5:
                    System.out.println ("5. Random or Scalefree network");
                    nt.scaleFreeNetwork (graph);
                    System.out.println ("======================================================");
                    break;
                case 6:
                    System.out.println ("Property 1 - Degree of nodes and Distribution");
                    nt.degreeDistribution (graph);
                    System.out.println ("======================================================");
                    System.out.println ("Property 2 - Strength of nodes and Distribution");
                    nt.strengthDistribution (graph);
                    System.out.println ("======================================================");
                    System.out.println ("Property 3 - Clustering Coefficient of Network");
                    nt.clusteringCoefficient (graph);
                    System.out.println ("======================================================");
                    System.out.println ("Property 4 - Characteristic path length");
                    nt.characteristicPathLength(graph);
                    System.out.println ("======================================================");
                    System.out.println ("5. Random or Scalefree network");
                    nt.scaleFreeNetwork (graph);
                    System.out.println ("======================================================");
                    break;
                case 7:
                    showOptions = false;
                    break;
                default:
                    System.out.println ("Invalid Input");
                    break;
            }

        }

    }
}
