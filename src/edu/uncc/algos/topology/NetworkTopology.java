package edu.uncc.algos.topology;

import edu.uncc.algos.graph.weighted.Edge;
import edu.uncc.algos.graph.weighted.UndirectedGraph;
import edu.uncc.algos.util.TopologyUtil;

import java.util.*;

/**
 * @author venky
 */
public class NetworkTopology {

    /**
     * Property 1. Degree of a node and its distribution.
     */

    public void degreeDistribution(UndirectedGraph graph) {
        double sum = 0;

        // get the vertices of graph
        List<Integer> verticesList = graph.vertices ();
        HashMap<Integer, Integer> degreeList = new HashMap<> ();
        // getting degree of for each vertices.
        for ( int i : verticesList ) {
            sum = sum + graph.degree (i);
            degreeList.put (i, graph.degree (i));
        }
        System.out.println ("Degree of Nodes");
        System.out.println ("node\tdegree");
        System.out.println ("-------------------");
        degreeList.forEach ((k, v) -> {
            System.out.println (k + "\t\t" + v);
        });

        /**
         * Degree Distribution
         */
        HashMap<Integer, Double> degreeDist = new HashMap<> ();
        double numOfNodes = verticesList.size ();

        degreeNodeCount (degreeList).forEach ((k, v) -> {
            degreeDist.put (k, TopologyUtil.formatDouble ((v / numOfNodes)));
        });
        System.out.println ("Degree Distribution of Nodes");
        System.out.println ("Degree\tDegree Dist");
        System.out.println ("-------------------");
        degreeDist.forEach ((k, v) -> {
            System.out.println (k + "\t\t" + v);
        });

        /**
         * Average degree of network
         * Sum of degree of all nodes / number of nodes
         */

        double avgDegree = sum / verticesList.size ();
        avgDegree = TopologyUtil.formatDouble (avgDegree);
        System.out.println ("Average Degree of this Network - " + avgDegree);

    }

    /**
     * Property 2. Strength and Strength distribution
     */
    public void strengthDistribution(UndirectedGraph graph) {
        double totalSt = 0;
        // get the vertices of graph
        List<Integer> verticesList = graph.vertices ();
        HashMap<Integer, Double> strengthList = new HashMap<> ();

        // getting strength for each node.
        for ( int i : verticesList ) {
            totalSt = totalSt + graph.strength (i);
            strengthList.put (i, graph.strength (i));
        }
        System.out.println ("Strength of Nodes");
        System.out.println ("node\tstrength");
        System.out.println ("-------------------");
        strengthList.forEach ((k, v) -> {
            System.out.println (k + "\t\t" + v);
        });


        /**
         * Strength Distribution
         */
        HashMap<Double, Double> strengthDist = new HashMap<> ();
        double numOfNodes = verticesList.size ();

        strengthNodeCount (strengthList).forEach ((k, v) -> {
            strengthDist.put (TopologyUtil.formatDouble (k), TopologyUtil.formatDouble ((v / numOfNodes)));
        });
        System.out.println ("Strength Distribution of Nodes");
        System.out.println ("Strength\tStrength Dist");
        System.out.println ("-------------------");
        strengthDist.forEach ((k, v) -> {
            System.out.println (k + "\t\t" + v);
        });

        /**
         * Average strength of network
         * Sum of strength of all nodes / number of nodes
         */

        double averageStrength = totalSt / verticesList.size ();
        averageStrength = TopologyUtil.formatDouble (averageStrength);
        System.out.println ("Average Degree of this Network - " + averageStrength);
    }

    /**
     * Property 3 - Clustering Coefficient of Network
     */
    public void clusteringCoefficient(UndirectedGraph graph) {
        double totalCc = 0;

        // get the vertices of graph
        List<Integer> verticesList = graph.vertices ();
        HashMap<Integer, Double> ccList = new HashMap<> ();

        // getting  Clustering Coefficient for each node.
        for ( int i : verticesList ) {
            totalCc = totalCc + graph.cCoefficient (i);

            ccList.put (i, graph.cCoefficient (i));
        }

        System.out.println ("Clustering Coefficient for Nodes");
        System.out.println ("node\tClustering Coefficient");
        System.out.println ("------------------------------------");
        ccList.forEach ((k, v) -> {
            System.out.println (k + "\t\t" + v);
        });


        double avarageCC = totalCc / verticesList.size ();
        avarageCC = TopologyUtil.formatDouble (avarageCC);
        System.out.println ("Average Degree of this Network - " + avarageCC);
    }

    /**
     * Property 4 - Scalefree or random network.
     */

    public void scaleFreeNetwork(UndirectedGraph graph) {
        // get the vertices of graph
        List<Integer> verticesList = graph.vertices ();
        HashMap<Integer, Integer> degreeMap = new HashMap<> ();
        // getting degree of for each vertices.
        for ( int i : verticesList ) {
            degreeMap.put (i, graph.degree (i));
        }
        HashMap<Integer, Integer> degreeNodesCount = degreeNodeCount (degreeMap);
        System.out.println (degreeNodesCount);
        TopologyUtil.scaleFreeRandomGraph (degreeNodesCount, "Scale Free vs Random", "Degree", "Nodes");
    }


    public void characteristicPathLength(UndirectedGraph graph){
        List<Integer> vertexList = graph.vertices ();
       Collections.sort (vertexList);
        double totalPathLen = 0;

        for ( int i:vertexList ){
//            UndirectedGraph<Integer> temp = graph;
            double[] distance = shortestPath (graph, i);
            for ( int j = i; j<distance.length; j++ ){
                totalPathLen = totalPathLen + distance[j];
            }
        }
        double cpl = totalPathLen / (vertexList.size () * ( vertexList.size () - 1));
        System.out.println ("Characteristic Path Length of this Graph is - "+TopologyUtil.formatDouble (cpl));
    }
    private double[] shortestPath(UndirectedGraph graph, int source){
        Queue<Edge<Integer>> pQueue = new PriorityQueue<> (graph.weightComparator);

        // initialize distance array
        double[] distance = new double[graph.verticesCount ()+1 ];
        int[] path = new int[graph.verticesCount ()+1 ];
        for ( int i=1;i<distance.length;i++ ){
            distance[i] = -1;
        }
        path[0] = -1;
        distance[source] = 0;

        // get neighbours of source and add to priority queue
        List<Edge<Integer>> edges = graph.getNeighbours (source);
        for ( Edge<Integer> e: edges ){
            distance[e.getDest ()] = e.getW ();
            path[e.getDest ()] = source;
            pQueue.offer (e);
        }

        while ( !pQueue.isEmpty () ){
            Edge<Integer> e = pQueue.poll ();
            int nextNode = e.getDest ();
            List<Edge<Integer>> adjE = graph.getNeighbours (nextNode);
            for ( int i=0; i<adjE.size (); i++ ){
                double cW = adjE.get (i).getW ();
                double pW = distance[nextNode];
                double newDistance = pW + cW;
                if ( distance[adjE.get (i).getDest ()] == -1 ){
                    distance[adjE.get (i).getDest ()] = newDistance;
                    pQueue.offer (adjE.get (i));
                    path[adjE.get (i).getDest ()] = e.getDest ();
                } else if ( distance[adjE.get (i).getDest ()] > newDistance ){
                    distance[adjE.get (i).getDest ()] = newDistance;
//                    Edge<Integer> thisEdge = adjE.get (i);
                    Edge<Integer> thisEdge2 = new Edge<> (adjE.get (i).getDest (), adjE.get(i).getW ());
                    thisEdge2.setW (newDistance);
                    pQueue.offer (thisEdge2);

                    path[adjE.get (i).getDest ()] = e.getDest ();
                }
            }
        }

        return distance;
    }

    /**
     * @param degreeMap
     *
     * @return
     */
    private HashMap<Integer, Integer> degreeNodeCount(HashMap<Integer, Integer> degreeMap) {
        Set<Integer> degreeSet = new TreeSet<> ();
        degreeMap.forEach ((k, v) -> {
            degreeSet.add (v);
        });

        Set<Map.Entry<Integer, Integer>> degreeEntry = degreeMap.entrySet ();
        HashMap<Integer, Integer> degreeNodesCount = new HashMap<> ();
        Iterator<Integer> iterator = degreeSet.iterator ();


        while ( iterator.hasNext () ) {
            int degree = iterator.next ();
            int nodeCount = 0;
            Iterator<Map.Entry<Integer, Integer>> it = degreeEntry.iterator ();
            while ( it.hasNext () ) {
                if ( it.next ().getValue () == degree ) {
                    nodeCount += 1;
                }
            }
            degreeNodesCount.put (degree, nodeCount);
        }

        return degreeNodesCount;
    }

    private HashMap<Double, Integer> strengthNodeCount(HashMap<Integer, Double> strengthMap) {
        Set<Double> strengthSet = new TreeSet<> ();
        strengthMap.forEach ((k, v) -> {
            strengthSet.add (v);
        });

        Set<Map.Entry<Integer, Double>> strengthEntry = strengthMap.entrySet ();
        HashMap<Double, Integer> stNodesCount = new HashMap<> ();
        Iterator<Double> iterator = strengthSet.iterator ();


        while ( iterator.hasNext () ) {
            double st = iterator.next ();
            int nodeCount = 0;
            Iterator<Map.Entry<Integer, Double>> it = strengthEntry.iterator ();
            while ( it.hasNext () ) {
                if ( it.next ().getValue () == st ) {
                    nodeCount += 1;
                }
            }
            stNodesCount.put (st, nodeCount);
        }

        return stNodesCount;
    }

}
