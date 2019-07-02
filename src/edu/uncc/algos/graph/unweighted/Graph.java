package edu.uncc.algos.graph.unweighted;

import java.util.List;

/**
 * @author venky
 */
public interface Graph<E> {
    void addVertex(E v);

    void addEdge(E src, E dest);


    List<E> getNeighbours(E v);

    void removeVertex(E v);

    void removeEdge(E src, E dest);

    int degree(E v);

//    int edges();

    List<E> vertices();

    List<Integer> size();
}
