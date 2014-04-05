package ufrgs.maslab.abstractsimulator.disaster;

import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.Temperature;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;

public class BurningTask extends Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * levels of burning
	 * 0 - not burning
	 * 1 - room or kitchen
	 * 2 - apartment
	 * 3 - one floor
	 * 4 - more than one floor
	 * 
	 * each timestep whether the number of agents is lower than temperature
	 * then increaseTemperature increases 1
	 * 
	 * if increasesTemperature mod 2 == 0 then temperature increases 1
	 */
	private Temperature temperature;
	
	/**
	 *  support variable to increase temperature level
	 */
	//private int increaseTemperature = 0;
	public int increaseTemperature = 0;
	
	
	/**
	 * counts timesteps to decrease temperature
	 */
	//private int decreaseTemperature = 0;
	public int decreaseTemperature = 0;
	
	/**
	 *  each type of matter of the buildings influences how
	 *  fire will propagate
	 *  0 = 0,6
	 *  1 = 0,3
	 *  2 = 0,15
	 */
	private Matter matter;
	
	//private transient int requiredTime = 0;
		public transient int requiredTime = 0;
		/**
		 *  if temperature == 4 then modifier increases floors
		 */
		private int floors = 1;
		/**
		 *  if areaGround higher than 500 then modifier increases 1
		 */
		private int areaGround;
		
		/**
		 * totalArea is areaGround times floors
		 * represents total area of building
		 */
		private int totalArea;
		/**
		 *  modifier will adjust timesteps needed to extinguish fire
		 */
		//private int modifier = 1;
		public int modifier = 1;
		
		private boolean isBurning = true;
		
		public BurningTask(BurningTask task)
		{
			this.setTemperature(Temperature.randomTemperature());
			this.setMatter(Matter.randomMatter());
			if(this.getMatter() == 0)
			{
				this.setFloors(1+this.rollDices(2));
			}else{
				this.setFloors(1 + this.rollDices(5));
			}
			this.setAreaGround(100 + this.rollDices(900));
			this.setY(this.rollDices(100));
			this.setX(this.rollDices(100));
			this.configureTask();
		}
		
		private void configureTask() {
			
			if(this.getAreaGround() > 500)
			{
				this.setModifier(modifier + 1);
			}
			
			if(this.getTemperature() == 4)
			{
				this.setModifier(modifier + this.getFloors());
			}
			this.requiredTime = this.modifier * this.getTemperature();
		}

		/**
		 * 
		 *  get methods
		 *  
		 */
		
		public int getMatter(){
			return this.matter.getValue();
		}

		public int getTemperature() {
			return this.temperature.getValue();
		}
		
		public int getFloors() {
			return this.floors;
		}
		
		public int getAreaGround() {
			return this.areaGround;
		}
		
		public int getTotalArea()
		{
			return this.totalArea;
		}
		
		public boolean getIsBurning(){
			return this.isBurning;
		}
		
		protected int getModifier() {
			return modifier;
		}
		
		protected int getIncreaseTemperature() {
			return increaseTemperature;
		}
		
		protected int getRequiredTime(){
			return this.requiredTime;
		}
		
		/*
		public ArrayList<A> getAgents()
		{
			return (ArrayList<A>)this.getVariables();
		}*/
		
		public int getDecreaseTemperature()
		{
			return this.decreaseTemperature;
		}
		
		/**
		 *  set methods
		 * 
		 */
		
		protected void setMatter(Matter matter)
		{
			this.matter = matter;
		}

		protected void setTemperature(Temperature temperature) {
			
			this.temperature = temperature;
		}

		protected void setFloors(int floors) {
			this.floors = floors;
		}

		protected void setAreaGround(int areaGround) {
			this.areaGround = areaGround;
			this.totalArea = areaGround * this.getFloors();
		}
		
		protected void setIsBurning(boolean b)
		{
			this.isBurning = b;
		}

		protected void setModifier(int modifier) {
			this.modifier = modifier;
		}

		protected void increaseTemperature() {
			this.increaseTemperature++;
			if(this.increaseTemperature % 2 == 0)
			{
				int newT = (this.getTemperature() + 1);
				if(newT <= 4){
					this.setTemperature(Temperature.values()[newT]);
					if(newT == 4)
					{
						this.setModifier(this.getModifier() + this.getFloors());
					}
				}
			}
		}

}
