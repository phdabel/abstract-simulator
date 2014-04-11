package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.util.IdGenerator;

public abstract class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <ul>
	 * <li>id of the entity</li>
	 * </ul>
	 */
	private int id;
	
		
	/**
	 * <ul>
	 * <li>constructor generates a new id number to each entity</li>
	 * <li>id number follows an order</li>
	 * <li>there is no two entities with the same id</li>
	 * </ul>
	 */
	public Entity(){
		this.setId(IdGenerator.getInstance().getId());
	}
	
	/**
	 * <ul>
	 * <li>generates a new Entity with the specified id</li>
	 * <li>usually, this constructor is used to search objects in list</li>
	 * </ul>
	 * @param id
	 */
	public Entity(Integer id)
	{
		this.setId(id);
	}

	/**
	 * <ul>
	 * <li>returns the id of the entity</li>
	 * </ul>
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * <ul>
	 * <li>set the id of the entity</li>
	 * </ul>
	 * @param id
	 */
	private void setId(int id) {
		this.id = id;
	}
	
	/**
	 * <ul>
	 * <li>used to roll dices for actions and events</li>
	 * <li>actions are probabilistic</li>
	 * <li>each entity has dices</li>
	 * </ul>
	 */
	private Random dices = new Random();
	
	/**
	 * <ul>
	 * <li>d100 dice</li>
	 * <li>returns [0,1] probability</li>
	 * </ul>
	 * @return
	 */
	protected double rollDices()
	{
		return this.dices.nextDouble();
	}
	
	/**
	 * <ul>
	 * <li>d<maxValue> dice</li>
	 * <li>returns a random number given the parameter maxValue</li>
	 * <li>returns [0,maxValue)</li>
	 * </ul>
	 * @param maxValue
	 * @return
	 */
	protected int rollDices(int maxValue)
	{
		return this.dices.nextInt(maxValue);
	}
	
	
	/**
	 * <ul>
	 * <li>returns the hashCode of the entity</li>
	 * <li>the same id</li>
	 * </ul>
	 */
	public int hashCode()
	{
		return this.id;
	}
	
	/**
	 * <ul>
	 * <li>compares two entities</li>
	 * </ul>
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof Entity && ((Entity)obj).equals(this.id))
			return true;
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		Entity other = (Entity)obj;
		if(this.getId() != other.getId())
			return false;
		
		return true;
	}

	

}
