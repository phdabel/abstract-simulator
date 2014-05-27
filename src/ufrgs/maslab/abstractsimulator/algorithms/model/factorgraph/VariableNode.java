package ufrgs.maslab.abstractsimulator.algorithms.model.factorgraph;

import java.util.Set;

public class VariableNode implements Node {
	
	private int id;
	
	public VariableNode(int id)
	{
		this.id = id;
	}
	
	public boolean equals(Object n) {
        return (n instanceof VariableNode)
                && (this.getId() == ((VariableNode) n).getId());
    }

    public int getId() {
        return this.id;
    }
	
	public int hashCode() {
        return ("Variable_" + this.id).hashCode();
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
