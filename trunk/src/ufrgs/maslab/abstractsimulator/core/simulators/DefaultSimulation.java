package ufrgs.maslab.abstractsimulator.core.simulators;

import java.util.Random;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;

public abstract class DefaultSimulation {

	private Integer difficulty = 6;
	
	private Integer modifier;
	
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
	@SuppressWarnings("unused")
	private double rollDices()
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
	private int rollDices(int maxValue)
	{
		return this.dices.nextInt(maxValue) + 1;
	}
	
	public abstract void simulate(Entity entity) throws SimulatorException;
	
	public abstract void simulate(Entity... entity) throws SimulatorException;

	public Integer getDifficulty() {
		return difficulty;
	}

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		if((this.getDifficulty()+modifier)>10)
			modifier = 4;
		this.modifier = modifier;
	}
	
	private Integer testAttributesSet(Integer... attr){
		Integer result = 0;
		for(Integer i : attr)
		{
			for(int k = 0; k < i; k++)
			{
				int dice = this.rollDices(10);
				if(dice >= (this.getDifficulty()+this.getModifier()))
				{	
					result++;				
				}else if(dice == 1)
				{
					result--;
				}
				
			}
		}
		
		return result;
	}
	
	public Integer testAction(Integer... attr)
	{
		int test = this.testAttributesSet(attr);
		
		return test;
	}
			
	
	
	
	
}
