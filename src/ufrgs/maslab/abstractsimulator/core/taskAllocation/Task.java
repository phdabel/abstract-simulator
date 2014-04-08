package ufrgs.maslab.abstractsimulator.core.taskAllocation;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.Value;

public class Task extends Value {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	private ArrayList<Human> performer = new ArrayList<Human>();
	
	private int x;
	
	private int y;
	
	public Task(){
		super();
	}
	
	public Task(Integer id)
	{
		super(id);
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

	public ArrayList<Human> getPerformer() {
		return performer;
	}

	public void setPerformer(ArrayList<Human> performer) {
		this.performer = performer;
	}
	
}