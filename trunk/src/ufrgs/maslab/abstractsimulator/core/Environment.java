package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Environment<val extends Value, var extends Variable<val>> extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;
	
	private ArrayList<var> variables = new ArrayList<var>();
	
	private ArrayList<val> values = new ArrayList<val>();
	
	private Map<val, ArrayList<var>> allocation = new HashMap<val, ArrayList<var>>();
	
	private ArrayList<val> solvedValues = new ArrayList<val>();
	
	private ArrayList<val> removedValues = new ArrayList<val>();
	
	protected ArrayList<var> getVariables(){
		return this.variables;
	}
	
	protected ArrayList<val> getValues(){
		return this.values;
	}
	
	protected ArrayList<val> getSolvedValues(){
		return this.solvedValues;
	}
	
	protected ArrayList<val> getRemovedValues(){
		return this.removedValues;
	}

	public Map<val, ArrayList<var>> getAllocation() {
		return allocation;
	}

	public void setAllocation(Map<val, ArrayList<var>> allocation) {
		this.allocation = allocation;
	}


}
