package ufrgs.maslab.abstractsimulator.variables;

import java.util.HashMap;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.interfaces.Building;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;

public class FireStation extends Agent implements Building{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877720837521565204L;

	public FireStation(){
		super();
	}
	
	public FireStation(Integer id)
	{
		super(id);
	}
	
	public void act(int time){
		System.out.println("Ammount of received messages: "+this.getRadioMessage().size());
		System.out.println("Fire Station "+this.getId());
		for(Message m : this.getRadioMessage())
		{
			FireBuildingTaskMessage t = (FireBuildingTaskMessage)m;
			if(!this.tasks.containsKey(t.getTaskId()))
				this.tasks.put(t.getTaskId(), t);
		}
		System.out.println("size of tasks "+this.tasks.size());
		for(FireBuildingTaskMessage t : this.tasks.values())
		{
			System.out.println(t.toString());
		}
	}
	
	@Override
	public void sendRadioMessage(Message msg) throws SimulatorException {
		if(msg.getType() != MessageType.RADIO)
			throw new SimulatorException(Transmitter.getProperty("exception.properties", "exception.not.radio.message"));
		this.getVoice().add(msg);
	}
	
	/**
	 * custom fields and methods
	 */
	
	private HashMap<Integer, FireBuildingTaskMessage> tasks = new HashMap<Integer, FireBuildingTaskMessage>();
	
	

}
