package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.variables.Human;


public abstract class Value extends Entity implements Comparable<Value> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5767460036572644735L;
	
	private int x;
	
	private int y;
	
	private ArrayList<Class<Human>> performer = new ArrayList<Class<Human>>();
	
	/**
	 * <ul>
	 * <li>double value used to optimization</li>
	 * </ul>
	 */
	private Double value;
	
	public Value(Integer id) {
		super(id);
	}

	public ArrayList<Class<Human>> getPerformer() {
		return this.performer;
	}

	public void setPerformer(ArrayList<Class<Human>> performer) {
		this.performer = performer;
	}
	
	public Value() {
		super();
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
	
	public String toString(){
		return "Task: "+this.getId()+" - Value: "+this.getValue().toString();
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
	
	public abstract void logger();
	
	public abstract void header();


}
