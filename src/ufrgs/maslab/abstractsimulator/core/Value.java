package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;


public abstract class Value extends Entity implements Serializable, Comparable<Value> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	/**
	 * <ul>
	 * <li>double value used to optimization</li>
	 * </ul>
	 */
	private Double value;
	
	/**
	 * overrides entity constructor
	 */
	public Value(){
		super();
	}
	
	/**
	 * overrides entity constructor
	 * @param id
	 */
	public Value(Integer id)
	{
		super(id);
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

}
