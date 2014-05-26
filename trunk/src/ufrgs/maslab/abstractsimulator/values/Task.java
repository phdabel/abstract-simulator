package ufrgs.maslab.abstractsimulator.values;

import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.log.TaskLogger;

public class Task extends Value  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	
		
	public Task(){
		super();
	}
	
	public Task(Integer id)
	{
		super(id);
	}

	@Override
	public void logger() {
		TaskLogger.logTask(this);		
	}

	@Override
	public void header() {
		TaskLogger.saveHeader();
	}
	
	public String toString()
	{
		return ""+this.getId();
	}
	
	
	
		
}