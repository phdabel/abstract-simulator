package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.mailbox.MailBox;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.values.Task;

public abstract class Variable extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8643465426657959565L;
			
	/**
	 * <ul>
	 * <li>current value assigned to this variable</li>
	 * </ul>
	 */
	private Integer value = null;
	
	/**
	 * <ul>
	 * <li>if this variable is allocated to some value or not</li>
	 * </ul>
	 */
	private boolean allocated = false;
	
	/**
	 * <ul>
	 * <li>lifetime of the variable</li>
	 * </ul>
	 */
	private int time = 0;

	/**
	 * <ul>
	 * <li>domain of the variable</li>
	 * <li>set of values which this variable can assign</li>
	 * </ul>
	 */
	private ArrayList<Integer> domain = new ArrayList<Integer>();

	private double x;
	
	private double y;
	
	private MailBox mail = new MailBox();
	
	public Variable(Integer id) {
		super(id);
	}

	public Variable() {
		super();
		this.setX(this.rollDices());
		this.setY(this.rollDices());
	}

	/**
	 * <ul>
	 * <li>returns the value assigned to this variable</li>
	 * </ul>
	 * @return
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * <ul>
	 * <li>assigns the value id to this variable</li>
	 * </ul>
	 * @param value
	 */
	public void assign(Task value) {
		this.value = value.getId();
		/*
		 * will be included after
		if(this.domain.contains(value))
		{
			this.value = value;
		}else{
			throw new SimulatorException("Variable does not has in domain.");
		}*/
		
	}
	
	/**
	 * <ul>
	 * <li>abstract function act</li>
	 * <li>all actions of the variable must be performed here</li>
	 * <li>act will be executed by simulator engine</li>
	 * </ul>
	 * @param time
	 */
	public abstract void act(int time);
		
	/**
	 * <ul>
	 * <li>returns the domain of the variable</li>
	 * </ul>
	 * @return
	 */
	public ArrayList<Integer> getDomain() {
		return this.domain;
	}

	/**
	 * <ul>
	 * <li>set the domain of the variable</li>
	 * </ul>
	 * @param domain
	 */
	public void setDomain(ArrayList<Integer> domain) {
		this.domain = domain;
	}
	
	/**
	 * <ul>
	 * <li>insert one item (value) to domain of the agent</li>
	 * </ul>
	 * @param value
	 */
	public void insertDomain(Integer value)
	{
		if(!this.getDomain().contains(value))
			this.getDomain().add(value);
	}

	/**
	 * <ul>
	 * <li>returns if the variable is allocated to some value</li>
	 * </ul>
	 * @return
	 */
	public boolean isAllocated() {
		return this.allocated;
	}

	/**
	 * <ul>
	 * <li>sets the allocation of the variable</li>
	 * </ul>
	 * @param allocated
	 */
	public void setAllocated(boolean allocated) {
		this.allocated = allocated;
	}

	/**
	 * <ul>
	 * <li>returns the age, in turns, of this variable</li>
	 * </ul>
	 * @return
	 */
	public int getTime() {
		return time;
	}

	/**
	 * <ul>
	 * <li>sets the time of this variable</li>
	 * </ul>
	 * @param time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setX(double x){
		this.x = x;
	}

	/**
	 * return all new messages
	 * @return
	 */
	public ArrayList<Message> getNewMail() {
		return mail.getInBox();
	}
	
	
	
	

	

}
