package ufrgs.maslab.abstractsimulator.disaster;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class DisasterEnvironment<T extends Task, A extends Agent<T>> extends Environment<T,A> {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9L;
	
	public ArrayList<T> getTasks(){
		return this.getValues();
	}
	
	public ArrayList<A> getAgents(){
		return this.getVariables();
	}
	
	public ArrayList<T> getSolvedTasks()
	{
		return this.getSolvedValues();
	}
	
	public ArrayList<T> getRemovedTasks()
	{
		return this.getRemovedValues();
	}

}
