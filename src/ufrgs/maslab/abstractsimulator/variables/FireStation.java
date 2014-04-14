package ufrgs.maslab.abstractsimulator.variables;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.interfaces.Building;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
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
		System.out.println("Messages received: ");
	}
	
	@Override
	public void sendRadioMessage(Message msg) throws SimulatorException {
		if(msg.getType() != MessageType.RADIO)
			throw new SimulatorException(Transmitter.getProperty("exception.properties", "exception.not.radio.message"));
		this.getVoice().add(msg);
	}
	

}
