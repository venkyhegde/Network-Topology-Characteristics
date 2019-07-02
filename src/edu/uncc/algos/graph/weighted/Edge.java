package edu.uncc.algos.graph.weighted;

import java.util.Objects;

/**
 * @author venky
 */
public class Edge<E> {
    private E dest;
    private double w;

    /**
     * Instantiates a new Edge.
     *
     * @param dest
     *         the dest
     * @param w
     *         the w
     */
    public Edge(E dest, double w) {
        this.dest = dest;
        this.w = w;
    }

    /**
     * Gets dest.
     *
     * @return the dest
     */
    public E getDest() {
        return dest;
    }

    /**
     * Sets dest.
     *
     * @param dest
     *         the dest
     */
    public void setDest(E dest) {
        this.dest = dest;
    }

    /**
     * Gets w.
     *
     * @return the w
     */
    public double getW() {
        return w;
    }

    /**
     * Sets w.
     *
     * @param w
     *         the w
     */
    public void setW(double w) {
        this.w = w;
    }

    @Override public String toString() {
        return "[" + dest + "|(" + w + ")]";
    }


    @Override public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( !(o instanceof Edge) ) return false;
        Edge<?> edge = ( Edge<?> ) o;
        return getDest ().equals (edge.getDest ());
    }

    @Override public int hashCode() {
        return Objects.hash (getDest ());
    }
}
