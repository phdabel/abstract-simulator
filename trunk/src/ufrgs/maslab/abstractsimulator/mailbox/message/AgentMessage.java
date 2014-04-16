package ufrgs.maslab.abstractsimulator.mailbox.message;

import ufrgs.maslab.abstractsimulator.core.Entity;

public class AgentMessage extends Message {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2946799500910338263L;

	private Integer agentId;
	
	private Double x;
	
	private Double y;

	public String toString(){
		String s = super.toString();
		s = "ID: "+this.getAgentId().toString()+" \n "
				+"X: "+this.getX().toString()+" \n "
				+"Y: "+this.getY().toString()+" \n ";
		return s;
		
	}
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
