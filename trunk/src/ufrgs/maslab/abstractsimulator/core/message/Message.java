package ufrgs.maslab.abstractsimulator.core.message;

import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;

public class Message {
	
	private int fromAgent;
	
	private int toAgent;
	
	private Class<Agent> fromClass;
	
	private Class<Agent> toClass;
	
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

	public Class<Agent> getFromClass() {
		return fromClass;
	}

	public void setFromClass(Class<Agent> fromClass) {
		this.fromClass = fromClass;
	}

	public Class<Agent> getToClass() {
		return toClass;
	}

	public void setToClass(Class<Agent> toClass) {
		this.toClass = toClass;
	}

	public Boolean getBroadCast() {
		return broadCast;
	}

	public void setBroadCast(Boolean broadCast) {
		this.broadCast = broadCast;
	}
	
	

}
