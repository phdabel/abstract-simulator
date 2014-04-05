package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;
import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.exception.SimulatorException;


public abstract class Variable<V extends Value> extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	protected V value = null;
	
	public int time = 0;
	
	protected ArrayList<V> domain = new ArrayList<V>();

	public V getValue() {
		return this.value;
	}

	public void assign(V value) throws SimulatorException {
		this.value = value;
		/*
		 * will be included after
		if(this.domain.contains(value))
		{
			this.value = value;
		}else{
			throw new SimulatorException("Variable does not has in domain.");
		}*/
		
	}
	
	
	public void act(int time)
	{
		
	}
	
	public void sense(int time){
		
	}

	public ArrayList<V> getDomain() {
		return this.domain;
	}

	public void setDomain(ArrayList<V> domain) {
		this.domain = domain;
	}
	
		

}
