package ufrgs.maslab.abstractsimulator.core.taskAllocation;

import ufrgs.maslab.abstractsimulator.core.Value;

public class Task extends Value {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	private int x;
	
	private int y;
	
	
	/**
	 *  creates a default burning building
	 *  with at most 5 floors
	 */
	public Task(){
		super();
	}
	
	public Task(Task t)
	{
		super();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int compareTo(Task other) {
	
		Task t = (Task)other;
		if(this.getValue() < t.getValue())
		{
			return -1;
		}
		if(this.getValue() > t.getValue())
		{
			return 1;
		}
		return 0;
	}
	
	public String toString(){
		return "Task: "+this.getId()+" - Value: "+this.getValue().toString();
	}

	
	
	
	
	

}
