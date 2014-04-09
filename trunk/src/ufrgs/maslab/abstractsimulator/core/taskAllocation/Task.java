package ufrgs.maslab.abstractsimulator.core.taskAllocation;

import java.io.Serializable;
import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Value;

public class Task extends Entity implements Value, Serializable, Comparable<Task> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
	private ArrayList<Human> performer = new ArrayList<Human>();
	
	private int x;
	
	private int y;
	
	/**
	 * <ul>
	 * <li>double value used to optimization</li>
	 * </ul>
	 */
	private Double value;
	
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
	
	public String toString(){
		return "Task: "+this.getId()+" - Value: "+this.getValue().toString();
	}

	public ArrayList<Human> getPerformer() {
		return performer;
	}

	public void setPerformer(ArrayList<Human> performer) {
		this.performer = performer;
	}

	/**
	 * <ul>
	 * <li>returns the numeric value</li>
	 * </ul>
	 * @return
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * <ul>
	 * <li>assigns the value</li>
	 * </ul>
	 * @param value
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	
	/**
	 * <ul>
	 * <li>compare two values</li>
	 * </ul>
	 */
	public int compareTo(Task other)
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