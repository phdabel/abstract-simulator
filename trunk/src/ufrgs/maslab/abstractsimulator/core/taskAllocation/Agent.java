package ufrgs.maslab.abstractsimulator.core.taskAllocation;

import ufrgs.maslab.abstractsimulator.core.Variable;

public class Agent<T extends Task> extends Variable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double x;
	
	private double y;
	
	public Agent(){
		super();
	}
	
	public Agent(Integer id)
	{
		super(id);
	}
	
	public Agent(double x, double y){
		super();
		this.x = x;
		this.y = y;
	}

	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setX(double x){
		this.x = x;
	}

	@Override
	public void act(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sense(int time) {
		// TODO Auto-generated method stub
		
	}
	

}
