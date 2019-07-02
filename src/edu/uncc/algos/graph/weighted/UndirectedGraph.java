package edu.uncc.algos.graph.weighted;

import java.util.*;

/**
 * A Undirected Weighted Graph ADT, backed by Hash table and LinkedHashSet.
 * Hashtable to store vertices and adjacency list as key, value pair. LinkedHasSet is to store adjacent vertices for
 * every vertex. This representation of graph follows Adjacent-list graph representation. This implementation is not
 * thread safe.
 * Hashtable provides better optimization over array/ array list. Hashtable ensures there won't be a null vertex. It
 * provides constant-time performance for most of it's operations such as add a new vertex into graph, add an edge,
 * finding degree, and to get adjacent vertices for a given vertex. This class provides two constructors to create a
 * graph.
 * The class also provides instance methods for many graph operation
 * Instance Methods -
 * 1. public void addVertex(E v) - Adds new vertex to graph.
 * 2. public void addEdge(E src, E dest) - Adds an edge for set of vertices.This method throw an
 * IndexOutOfBoundsException if source or destination vertex is not in this graph.
 * 3. public int edgesCount() - Returns number of edges in this graph.
 * 4. public int verticesCount() - Returns number of vertices in this graph.
 * 5. public int degree() - Returns the degree of a vertex v.
 * 6. public double strength() - Returns the strength for a vertex v.
 * 7. public double clusteringCoefficient() - Returns the Clustering coefficient of a vertex v.
 *
 * @param <E>
 *         the type parameter
 *
 * @author venky
 */
public class UndirectedGraph<E> implements Graph<E> {
    /**
     * to store number of vertices in graph.
     */
    private int numberOfVertices = 0;
    private int edges = 0;

    /**
     * A hash table to store vertices and it's adjacency list, as linked list
     */
    private Map<E, Set<Edge<E>>> graph;

    /**
     * Instantiates a new UndirectedGraph.
     */
    public UndirectedGraph() {
        // initializing the graph
        graph = new Hashtable<> ();

    }

    /**
     * Instantiates a new UndirectedGraph.
     * User can pass List of vertices to this constructor to create a graph with predefined vertices.
     *
     * @param vertices
     *         the list of vertices
     */
    public UndirectedGraph(List<E> vertices) {
        this.numberOfVertices = vertices.size ();
        graph = new Hashtable<> (this.numberOfVertices);
        for ( int i = 0 ; i < vertices.size () ; i++ ) {
            graph.put (vertices.get (i), new LinkedHashSet<Edge<E>> ());
        }
    }

    /**
     * Adds new vertex to this graph.
     *
     * @param v
     *         the v
     *         runtime O(1) - average case
     *         worst case O(log n)
     */
    @Override public void addVertex(E v) {
        if ( this.graph.containsKey (v) ) {
            throw new IllegalArgumentException ("Vertex exits in this graph");
        }
        this.graph.put (v, new LinkedHashSet<Edge<E>> ());
        this.numberOfVertices += 1;
    }

    /**
     * Adds an edge for set of vertices.This method throw an IndexOutOfBoundsException if source or destination
     * vertex is not in this graph.
     *
     * @param src
     *         the src vertex.
     * @param dest
     *         the destination vertex.
     * @param w
     *         weight of this edge.
     *         runtime O(1) - average case
     *         worst case O(log n)
     */
    @Override public void addEdge(E src, E dest, double w) {
        boolean edgeAdded = false;
        // check if source and destination are valid
        if ( !this.graph.containsKey (src) ) {
            throw new IndexOutOfBoundsException ("Invalid source or destination vertex");
        } else if ( !this.graph.containsKey (dest) ) {
            throw new IndexOutOfBoundsException ("Invalid source or destination vertex");
        }

        Edge e = new Edge (dest, w);
        edgeAdded = this.graph.get (src).add (e);

        e = new Edge (src, w);
        edgeAdded = this.graph.get (dest).add (e);

        if ( edgeAdded ) {
            this.edges += 1;
        }
    }


    /**
     * Returns number of edges in this graph.
     *
     * @return number of edges.
     *         runtime O(1)
     */
    @Override public int edgesCount() {
        return this.edges;
    }


    /**
     * Returns number of vertices in this graph.
     *
     * @return the int number of vertices.
     *         runtime O(1)
     */
    @Override public int verticesCount() {
        return this.numberOfVertices;
    }

    /**
     * Vertices list
     *
     * @return the set of vertices  in this graph.
     */
    @Override public List<E> vertices(){
        List<E> keyList = new ArrayList<> (this.graph.keySet ().size ());
        keyList.addAll (this.graph.keySet ());
        return keyList;
    }
    /**
     * Returns the degree of a vertex.
     * This method throws IllegalArgumentException if vertex is not part of this graph.
     *
     * @param v
     *         vertex
     *
     * @return degree of v.
     *         Runtime O(1) - average case
     *         worst case - O(log n)
     */
    @Override public int degree(E v) {
        // check if vertex is part of this graph
        if ( !this.graph.containsKey (v) ) {
            throw new IllegalArgumentException ("Vertex " + v + " is doesn't exists");
        }
        return this.graph.get (v).size ();
    }

    /**
     * Returns the strength of a vertex in this graph, throws IllegalArgumentException if vertex doesn't exists in
     * this graph.
     *
     * @param v
     *         vertex
     *
     * @return strength of v
     *         Runtime - O(e)
     */
    @Override public double strength(E v) {
        double st = 0;

        // check if vertex is defined in graph.
        if ( !this.graph.containsKey (v) ) {
            throw new IllegalArgumentException ("Vertex " + v + " is doesn't exists");
        }
        Set<Edge<E>> edges = this.graph.get (v);
        Iterator<Edge<E>> it = edges.iterator ();
        while ( it.hasNext () ) {
            Edge<E> e = it.next ();
            st = st + e.getW ();
        }
        return st;
    }

    /**
     * Clustering coefficient of a vertex v.
     * Clustering coefficient is defined as CC(v) = 2Nv / Kv (Kv -1)
     * where
     * v is a vertex
     * Kv is Degree of vertex v
     * Nv is # of links between neighbours of v.
     *
     * @param v
     *         the vertex.
     *
     * @return the double clustering coefficient of v.
     *         Run time
     *         O(num of neighbours(v))^2
     */
    @Deprecated
    @Override public double clusteringCoefficient(E v) {
        // check if vertex is defined in graph.
        if ( !this.graph.containsKey (v) ) {
            throw new IllegalArgumentException ("Vertex " + v + " is doesn't exists");
        }

        int kv = degree (v); // degree of v
        int nv = 0;
        double cc = 0.0;
        // find neighbours of v
        if ( this.graph.get (v).size () > 1 ) { // find cc only if a node has more than 1 edges.

            Set<Edge<E>> neighboursOfV = this.graph.get (v); // get neighbours of v
            List<Edge<E>> neighboursNodes = new ArrayList<> (neighboursOfV);

            // loop through each neighbour and find out if neighbours are connected.
            int j = neighboursNodes.size () - 1;
            for ( int i = 0 ; i < neighboursNodes.size () ; i++ ) {
                Set<Edge<E>> ne = this.graph.get (neighboursNodes.get (i).getDest ());
                while ( i < j ) {
                    // check if j is neighbour of i
                    Edge<E> e = neighboursNodes.get (j);
                    Iterator<Edge<E>> it = ne.iterator ();
                    while ( it.hasNext () ) {
                        if ( it.next ().getDest () == e.getDest () ) {
                            nv += 1;
                        }
                    }
                    j--;
                }
            }
        } else {
            nv = 0;
        }

        if ( nv > 0 ) {
            double dnominator = kv * (kv - 1);
            cc = (2 * nv) / dnominator;
        }

        return cc;
    }

    /**
     * Clustering coefficient of a vertex v.
     * Clustering coefficient is defined as CC(v) = 2Nv / Kv (Kv -1)
     * where
     * v is a vertex
     * Kv is Degree of vertex v
     * Nv is # of links between neighbours of v.
     *
     * @param v
     *         the vertex.
     *
     * @return the double clustering coefficient of v.
     *         Run time
     *         O(num of neighbours(v))^2
     */
    public double cCoefficient(E v) {

        // check if vertex is defined in graph.
        if ( !this.graph.containsKey (v) ) {
            throw new IllegalArgumentException ("Vertex " + v + " is doesn't exists");
        }

        int kv = degree (v); // degree of v
        int nv = 0;
        double cc = 0.0;
        // find neighbours of v
        if ( this.graph.get (v).size () > 1 ) { // find cc only if a node has more than 1 edges.
            List<Edge<E>> neighboursNodes = new ArrayList<> (graph.get (v));
           // looping through every neighbour
            for ( int i =0; i< neighboursNodes.size (); i++ ){
                // get neighbours of i
                Set<Edge<E>> neighboursOfI = this.graph.get (neighboursNodes.get (i).getDest ());
                int j = neighboursNodes.size () - 1;
                while ( i < j ){
                    if ( neighboursOfI.contains (neighboursNodes.get (j)) ){
                        nv += 1;

                    }
                    j--;
                }
            }
        } else {
            nv = 0;
        }

        if ( nv > 0 ) {
            double dnominator = kv * (kv - 1);
            cc = (2 * nv) / dnominator;
        }
        return cc;


    }

    /**
     * Gets list of neighbour vertices.
     * Throws IllegalArgumentException if vertex doesn't exists in this graph.
     * Runtime - constant
     *
     * @param v
     *         the vertex
     *
     * @return the list
     */
    public List<Edge<E>> getNeighbours(E v) {
        // check if vertex is valid or not
        if ( !this.graph.containsKey (v) ) {
            throw new IllegalArgumentException ("Invalid vertex - "+v);
        }
        List<Edge<E>> neighbours = new ArrayList<> (this.graph.get (v).size ());
        neighbours.addAll (this.graph.get (v));
        return neighbours;
    }



    /**
     * toString method is overridden to represent graph.
     *
     * @return Adjacency list representation of this graph.
     *         runtime - O(v)
     */
    @Override public String toString() {
        StringBuilder stringBuilder = new StringBuilder ();

        this.graph.forEach ((k, v) -> {
            stringBuilder.append (k + " -> ");
            stringBuilder.append (v);
            stringBuilder.append ("\n");
        });
        return stringBuilder.toString ();
    }

    public Comparator<Edge<E>> weightComparator = new Comparator<Edge<E>> () {
        @Override public int compare(Edge<E> o1, Edge<E> o2) {
            return ( int ) (o1.getW () - o2.getW ());
        }
    };
    /**
     * Private class represents a weighted edge in this graph
     *
     * @param <E>
     */
//    private class Edge<E> {
//        private E dest;
//        private double w;
//
//        /**
//         * Instantiates a new Edge.
//         *
//         * @param dest
//         *         the dest
//         * @param w
//         *         the w
//         */
//        public Edge(E dest, double w) {
//            this.dest = dest;
//            this.w = w;
//        }
//
//        /**
//         * Gets dest.
//         *
//         * @return the dest
//         */
//        public E getDest() {
//            return dest;
//        }
//
//        /**
//         * Sets dest.
//         *
//         * @param dest
//         *         the dest
//         */
//        public void setDest(E dest) {
//            this.dest = dest;
//        }
//
//        /**
//         * Gets w.
//         *
//         * @return the w
//         */
//        public double getW() {
//            return w;
//        }
//
//        /**
//         * Sets w.
//         *
//         * @param w
//         *         the w
//         */
//        public void setW(double w) {
//            this.w = w;
//        }
//
//        @Override public String toString() {
//            return "[" + dest + "|(" + w + ")]";
//        }
//
//
//        @Override public boolean equals(Object o) {
//            if ( this == o ) return true;
//            if ( !(o instanceof Edge) ) return false;
//            Edge<?> edge = ( Edge<?> ) o;
//            return getDest ().equals (edge.getDest ());
//        }
//
//        @Override public int hashCode() {
//            return Objects.hash (getDest ());
//        }
//    }
}

