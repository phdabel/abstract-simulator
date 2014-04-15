package ufrgs.maslab.abstractsimulator.mailbox.message;

import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.constants.Temperature;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class FireBuildingTaskMessage extends TaskMessage {
	
	private Temperature temperature;
	
	private Matter matter;
	
	private int floors;
	
	private int groundArea;
	
	private int apartmentsPerFloor;
	
	private int buildingHP;
	
	/**
	 * basic constructor 
	 * 
	 * @param t
	 */
	public void configureContent(FireBuildingTask t){
		this.setApartmentsPerFloor(t.getApartmentsPerFloor());
		this.setBuildingHP(t.getBuildingHP());
		this.setFloors(t.getFloors());
		this.setTemperature(t.getTemperature());
		this.setTaskId(t.getId());
		this.setMatter(t.getMatter());
		this.setGroundArea(t.getGroundArea());
		this.setTaskX(t.getX());
		this.setTaskY(t.getY());
	}
	
	/**
	 * basic constructor to broadcast messages
	 * 
	 * @param t
	 * @param sender
	 */
	public FireBuildingTaskMessage(Integer tid, Class<? extends Entity> taskClass, Agent sender, MessageType type)
	{
		this.setContent(tid);
		this.setContentClass(taskClass);
		this.setTaskId(tid);
		this.setFromAgent(sender.getId());
		this.setFromClass(sender.getClass());
		this.setType(type);
		this.setBroadCast(true);
		
	}
	
	public FireBuildingTaskMessage(Integer t, Class<? extends Entity> taskClass, Agent sender, Integer receiver, MessageType type)
	{
		this(t, taskClass, sender, type);
		this.setBroadCast(false);
		this.setToAgent(receiver);
		//this.setToClass(receiver.getClass());
	}

	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}

	public Matter getMatter() {
		return matter;
	}

	public void setMatter(Matter matter) {
		this.matter = matter;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public int getGroundArea() {
		return groundArea;
	}

	public void setGroundArea(int groundArea) {
		this.groundArea = groundArea;
	}

	public int getApartmentsPerFloor() {
		return apartmentsPerFloor;
	}

	public void setApartmentsPerFloor(int apartmentsPerFloor) {
		this.apartmentsPerFloor = apartmentsPerFloor;
	}

	public int getBuildingHP() {
		return buildingHP;
	}

	public void setBuildingHP(int buildingHP) {
		this.buildingHP = buildingHP;
	}
	

}
