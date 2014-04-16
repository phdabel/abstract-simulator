package ufrgs.maslab.abstractsimulator.core.simulators.basic;


import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.core.simulators.DefaultSimulation;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.mailbox.message.TaskMessage;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.Agent;
import ufrgs.maslab.abstractsimulator.variables.Human;


public class CommunicationSimulation extends DefaultSimulation {

	private String exceptionFile = "exception.properties";
	
	@SuppressWarnings("unchecked")
	@Override
	public void simulate(Entity entity) throws SimulatorException {
		if(!(entity instanceof Environment))
			throw new SimulatorException(Transmitter.getProperty(this.exceptionFile, "exception.not.environment"));
		
		Environment<Entity> env = (Environment<Entity>)entity;
		
		//erase messages
		for(Variable v: env.getVariables())
		{
			if(v instanceof Human)
				((Human)v).getEar().clear();
			if(v instanceof Agent)
				((Agent)v).getRadioMessage().clear();
		}
		
		//send new messages
		for(Variable v : env.getVariables())
		{
			Agent a = (Agent)v;
			for(int k = 0; k < a.getVoice().size(); k++)
			{
				if(a.getVoice().get(k) instanceof TaskMessage){
					FireBuildingTaskMessage msg = (FireBuildingTaskMessage) a.getVoice().get(k);
					msg.configureContent(
							(FireBuildingTask)env.findValueByID(a.getVoice().get(k).getContent(),(Class<? extends Value>) a.getVoice().get(k).getContentClass())
							);
				}
				if(a.getVoice().get(k).getBroadCast())
				{
					this.broadcastMessage(v, env, a.getVoice().get(k));
				}else{
					Entity r = env.findVariableByID(a.getVoice().get(k).getToAgent(), a.getVoice().get(k).getToClass());
					this.sendDirectedVoiceMessage(v, r, a.getVoice().get(k));
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
		/**
		 * TODO
		 * voice messages must be sent to closest humans
		 */
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