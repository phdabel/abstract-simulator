package ufrgs.maslab.abstractsimulator.mailbox.message;


import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public abstract class Message {
	
	private int fromAgent;
	
	private int toAgent;
	
	private Class<? extends Agent> fromClass;
	
	private Class<? extends Agent> toClass;
	
	private MessageType type;
	
	private Boolean broadCast = true;
	
	public int getFromAgent() {
		return fromAgent;
	}

	public void setFromAgent(int fromAgent) {
		this.fromAgent = fromAgent;
	}

	public int getToAgent() {
		return toAgent;
	}

	public void setToAgent(int toAgent) {
		this.toAgent = toAgent;
	}

	public Class<? extends Agent> getFromClass() {
		return fromClass;
	}

	public Class<? extends Agent> getToClass() {
		return toClass;
	}
	
	public void setFromClass(Class<? extends Agent> fromClass)
	{
		this.fromClass = fromClass;
	}

	public void setToClass(Class<? extends Agent> toClass) {
		this.toClass = toClass;
	}

	public Boolean getBroadCast() {
		return broadCast;
	}

	public void setBroadCast(Boolean broadCast) {
		this.broadCast = broadCast;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}
	
	

}
