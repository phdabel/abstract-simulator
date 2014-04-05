package ufrgs.maslab.abstractsimulator.core;

import java.io.Serializable;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.util.IdGenerator;

public abstract class Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	public Entity(){
		this.setId(IdGenerator.getInstance().getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private Random dices = new Random();
	
	protected double rollDices()
	{
		return this.dices.nextDouble();
	}
	
	protected int rollDices(int maxValue)
	{
		return this.dices.nextInt(maxValue);
	}
	
	public int hashCode()
	{
		return this.id;
	}
	
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
