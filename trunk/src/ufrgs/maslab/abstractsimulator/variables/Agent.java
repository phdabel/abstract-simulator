package ufrgs.maslab.abstractsimulator.variables;

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
		this.setX(this.rollDices());
		this.setY(this.rollDices());
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
	
	

}
