package ufrgs.maslab.abstractsimulator.core.simulators.basic;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.core.simulators.DefaultSimulation;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.variables.Agent;
import ufrgs.maslab.abstractsimulator.variables.Human;


public class CommunicationSimulation extends DefaultSimulation {

	private String exceptionFile = "exception.properties";
	
	@Override
	public void simulate(Entity entity) throws SimulatorException {
		if(!(entity instanceof Environment))
			throw new SimulatorException(Transmitter.getProperty(this.exceptionFile, "exception.not.environment"));
		
		@SuppressWarnings("unchecked")
		Environment<Entity> env = (Environment<Entity>)entity;
		for(Variable v : env.getVariables())
		{
			for(Message m : ((Agent)v).getVoice())
			{
				if(m.getBroadCast())
				{
					this.broadcastMessage(v, env, m);
				}else{
					Entity r = env.findVariableByID(m.getToAgent(), m.getToClass());
					this.sendDirectedVoiceMessage(v, r, m);
				}
			}
		}
			
		
	}

	@Override
	public void simulate(Entity... entity) throws SimulatorException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * send directed message from one human to other human
	 * 
	 * @param sender
	 * @param receiver
	 * @param msg
	 * @throws SimulatorException
	 */
	public void sendDirectedVoiceMessage(Entity sender, Entity receiver, Message msg) throws SimulatorException
	{
		if(!(sender instanceof Human) && !(receiver instanceof Human))
			throw new SimulatorException(Transmitter.getProperty(this.exceptionFile, "exception.not.human"));

		//send message to the receiver
		((Human)receiver).getEar().add(msg);

		//remove message from sender
		((Human)sender).getVoice().remove(msg);
		
	}
	
	/**
	 * broadcasts message from one sender to all agents in the environment
	 * 
	 * @param sender
	 * @param env
	 * @param msg
	 * @throws SimulatorException
	 */
	public void broadcastMessage(Entity sender, Environment<Entity> env, Message msg) throws SimulatorException
	{
		if(!(sender instanceof Agent))
			throw new SimulatorException(Transmitter.getProperty(this.exceptionFile, "exception.not.agent"));
		
		if(msg.getType() == MessageType.RADIO)
		{
			/**
			 * sends radio messages to all agents in the environment except sender
			 */
			this.brodcastRadioMessage(env, sender, msg);
			
		}else if(msg.getType() == MessageType.VOICE)
		{
			/**
			 * sends voice message to all humans in the environment except sender
			 */
			this.broadcastVoiceMessage(env, sender, msg);
		}
		
	}
	
	/**
	 * broadcasts message by voice
	 * 
	 * @param env
	 * @param sender
	 * @param msg
	 */
	private void broadcastVoiceMessage(Environment<Entity> env, Entity sender, Message  msg)
	{
		for(Variable v : env.getVariables())
		{
			if(v instanceof Human)
			{
				if(v.getId() != sender.getId())
					((Human)v).getEar().add(msg);
			}
		}
		((Agent)sender).getVoice().remove(msg);		
	}
	
	/**
	 * broadcasts message by radio
	 * 
	 * @param env
	 * @param sender
	 * @param msg
	 */
	private void brodcastRadioMessage(Environment<Entity> env, Entity sender, Message msg){
		for(Variable v : env.getVariables())
		{
			if(v instanceof Agent)
			{
				if(v.getId() != sender.getId())
					((Agent)v).getRadioMessage().add(msg);
			}
		}
		((Agent)sender).getVoice().remove(msg);
	}
	
}