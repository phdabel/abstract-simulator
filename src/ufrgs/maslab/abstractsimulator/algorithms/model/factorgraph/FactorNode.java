package ufrgs.maslab.abstractsimulator.algorithms.model.factorgraph;

import java.util.Set;

public class FactorNode implements Node {
	
	private int id;
	
	public FactorNode(int id)
	{
		this.id = id;
	}
	
	public boolean equals(Object n) {
        return (n instanceof FactorNode)
                && (this.getId() == ((FactorNode) n).getId());
    }
	
	public int getId() {
		return this.id;
	}

	public int hashCode() {
        return ("Factor_" + this.id).hashCode();
    }

	@Override
	public void addNeighbour(Node n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<? extends Node> getNeighbour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String stringOfNeighbour() {
		// TODO Auto-generated method stub
		return null;
	}


}
