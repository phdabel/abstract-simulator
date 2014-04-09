package ufrgs.maslab.abstractsimulator.core.taskAllocation;

import ufrgs.maslab.abstractsimulator.core.Variable;

public class Agent extends Variable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public Agent(){
		super();
	}
	
	public Agent(Integer id)
	{
		super(id);
	}
	
	public Agent(double x, double y){
		super();
		this.setY(y);
		this.setX(x);
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
