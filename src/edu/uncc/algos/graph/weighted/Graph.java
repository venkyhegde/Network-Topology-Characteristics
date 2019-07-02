package edu.uncc.algos.graph.weighted;

import java.util.List;

/**
 * @author venky
 */
public interface Graph<E> {
    void addVertex(E v);

    void addEdge(E src, E dest, double w);

    int edgesCount();

    int verticesCount();

    int degree(E v);
    List<E> vertices();

    double strength(E v);

    double clusteringCoefficient(E v);
    public double cCoefficient(E v);

    @Override String toString();
}
