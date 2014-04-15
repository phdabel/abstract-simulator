package ufrgs.maslab.abstractsimulator.mailbox.message;

import ufrgs.maslab.abstractsimulator.core.Entity;


public class TaskMessage extends Message {
	
	private Integer taskId;
	
	private Double taskX;
	
	private Double taskY;
	
	private Double taskValue;
	
	public Integer getTaskId() {
		return taskId;
	}
	
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Double getTaskX() {
		return taskX;
	}

	public void setTaskX(Double taskX) {
		this.taskX = taskX;
	}

	public Double getTaskY() {
		return taskY;
	}

	public void setTaskY(Double taskY) {
		this.taskY = taskY;
	}

	public Double getTaskValue() {
		return taskValue;
	}

	public void setTaskValue(Double taskValue) {
		this.taskValue = taskValue;
	}

	@Override
	public void configureContent(Entity content) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
