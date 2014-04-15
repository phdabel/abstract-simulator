package ufrgs.maslab.abstractsimulator.mailbox.message;

import ufrgs.maslab.abstractsimulator.core.Entity;

public class AgentMessage extends Message {
	
	private Integer agentId;
	
	private Double x;
	
	private Double y;

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	@Override
	public void configureContent(Entity content) {
		// TODO Auto-generated method stub
		
	}

}
