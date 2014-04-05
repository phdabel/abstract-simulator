package ufrgs.maslab.abstractsimulator.myagents;


import java.util.LinkedList;
import java.util.Queue;

import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;

public class GSOMAgent<T extends Task> extends Agent<T>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2001863310226679311L;
	
	public boolean leader = false;
	
	public Queue<GSOMAgent<T>> agents = new LinkedList<GSOMAgent<T>>();
		
	public T tmpVariable = null;
	
	public void act(int time)
	{
				
	}

}
