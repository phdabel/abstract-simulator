package ufrgs.maslab.abstractsimulator.mailbox.message;


import java.io.Serializable;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public abstract class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -142739020941263551L;

	private int fromAgent;
	
	private int toAgent;
	
	private int content;
	
	private Class<? extends Entity> contentClass;
	
	private Class<? extends Agent> fromClass;
	
	private Class<? extends Agent> toClass;
	
	private MessageType type;
	
	private Boolean broadCast = true;
	
	public abstract void configureContent(Entity content);
	
	public String toString()
	{
		String s = "From: "+this.getFromAgent()+" \n "
				+"To: "+this.getToAgent()+" \n "
				+"Type: "+this.getType()+" \n ";
				
		return s;
	}
	
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

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public Class<? extends Entity> getContentClass() {
		return contentClass;
	}

	public void setContentClass(Class<? extends Entity> contentClass) {
		this.contentClass = contentClass;
	}
	
	

}
