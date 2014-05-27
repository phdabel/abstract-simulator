package ufrgs.maslab.abstractsimulator.algorithms.model.factorgraph;

import java.util.Set;


public interface Node {
	
	public void addNeighbour(Node n);

    public Set<? extends Node> getNeighbour();

    public String stringOfNeighbour();

    @Override
    public boolean equals(Object o);

    public int getId();

    @Override
    public int hashCode();

}
