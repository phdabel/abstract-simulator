package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;
//import java.util.HashMap;


import java.util.Random;

import ufrgs.maslab.abstractsimulator.mailbox.MailBox;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
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
	 * <li>map of values</li>
	 * <li>value is the key and class is the value</li>
	 * </ul>
	 */
	//private HashMap<Integer,Class<? extends Entity>> domain = new HashMap<Integer,Class<? extends Entity>>();
	private ArrayList<Entity> domain = new ArrayList<Entity>();
	
	private Integer x;
	
	private Integer y;
	
	private MailBox mail = new MailBox();
	
	public Variable(Integer id) {
		super(id);
	}

	public Variable() {
		super();
		Random r = new Random();
		this.setX(r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.xpos")));
		this.setY(r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.ypos")));
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
	/*public HashMap<Integer,Class<? extends Entity>> getDomain() {
		return this.domain;
	}*/
	
	public ArrayList<Entity> getDomain(){
		return this.domain;
	}

	
	/**
	 * <ul>
	 * <li>insert one item (value) to domain of the agent</li>
	 * </ul>
	 * @param value
	 */
	/*public void insertDomain(Integer value, Class<? extends Entity>clazz)
	{
		if(!this.getDomain().containsKey(value))
			this.getDomain().put(value, clazz);
	}*/
	public void insertDomain(Entity value)
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

	
	public Integer getX(){
		return this.x;
	}
	
	public Integer getY(){
		return this.y;
	}
	
	public void setY(Integer y)
	{
		this.y = y;
	}
	
	public void setX(Integer x){
		this.x = x;
	}

	/**
	 * return all new messages
	 * @return
	 */
	public ArrayList<Message> getNewMail() {
		return mail.getInBox();
	}
	
	public abstract void logger();

	public abstract void header();

	/*public void setDomain(HashMap<Integer, Class<? extends Entity>> domain) {
		this.domain = domain;
	}*/
	public void setDomain(ArrayList<Entity> domain)
	{
		this.domain = domain;
	}
	

}
