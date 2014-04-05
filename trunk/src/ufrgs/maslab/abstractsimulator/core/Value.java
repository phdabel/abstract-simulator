package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;
//import java.util.ArrayList;
import java.util.ArrayList;


public abstract class Value extends Entity implements Serializable, Comparable<Value> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	private transient ArrayList<Variable> variables = new ArrayList<Variable>();
	
	private Double value;
	
	public ArrayList<Variable> getVariables()
	{
		return this.variables;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public int compareTo(Value other)
	{
		if(this.value < other.getValue()){
			return -1;
		}
		if(this.value > other.getValue())
		{
			return 1;
		}
		return 0;
	}

}
