package ufrgs.maslab.abstractsimulator.values;

import ufrgs.maslab.abstractsimulator.constants.Damage;
import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.Temperature;
import ufrgs.maslab.abstractsimulator.log.FireBuildingTaskLogger;
import ufrgs.maslab.abstractsimulator.util.Transmitter;

public class FireBuildingTask extends Task {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6064463956442943641L;
	
	/**
	 *  <ul>
	 *  <li>configuration file</li>
	 *  <li>contains all configuration parameters of the simulation</li>
	 *  </ul>
	 *  
	 */
	private String configFileName = "config.properties";
	
	/**
	 * levels of burning
	 * 0 - not burning
	 * 1 - room or kitchen
	 * 2 - apartment
	 * 3 - one floor
	 * 4 - more than one floor
	 * 
	 * 
	 */
	private Temperature temperature;
	
	/**
	 *  each type of matter of the buildings influences how
	 *  fire will propagate
	 *  0 = 0,6
	 *  1 = 0,3
	 *  2 = 0,15
	 */
	private Matter matter;
	
	/**
	 *  number of floors of the building
	 */
	private int floors = 1;
	/**
	 *  if groundArea
	 */
	private int groundArea;
	
	/**
	 * damage type of fire building task
	 */
	private Damage damageType = Damage.SEVERE;
	
	/**
	 * total number of apartments per floor
	 */
	private int apartmentsPerFloor;

	/**
	 * total building hit points
	 * <br/>
	 * when hp is 0 building fire can not be extinguished
	 */
	private int buildingHP;
	
	private int success;

	/**
	 * <ul>
	 * <li>default constructor</li>
	 * <li>Attributes</li>
	 * <li>Temperature - {0 - 4}</li>
	 * <li>Matter - {0 -2}</li>
	 * <li>Floors - {1 - maximum floors}</li>
	 * <li>Damage - Severe (2)</li>
	 * <li>groundArea - maximum groundArea</li>
	 * <li>apartmentsPerFloor</li>
	 * <li>buildingHP</li>
	 * </ul>
	 */
	public FireBuildingTask(){
		super();
		this.setTemperature(Temperature.randomTemperature());
		this.setMatter(Matter.randomMatter());
		if(this.getMatter() == Matter.WOODEN_HOUSE)
		{
			this.setFloors(1+this.rollDices(2));
			this.setGroundArea(50);
		}else if(this.getMatter() == Matter.REINFORCED_CONCRETE){
			this.setFloors(1+this.rollDices(5));
			this.setGroundArea(50 + this.rollDices(Transmitter.getIntConfigParameter(this.configFileName, "maximum.groundArea")));
		}else{
			this.setFloors(1+this.rollDices(Transmitter.getIntConfigParameter(this.configFileName, "maximum.floors")));
			this.setGroundArea(50 + this.rollDices(Transmitter.getIntConfigParameter(this.configFileName, "maximum.groundArea")));
		}
		this.setY(this.rollDices(/*Transmitter.getIntConfigParameter(this.configFileName, "maximum.xpos")*/));
		this.setX(this.rollDices(/*Transmitter.getIntConfigParameter(this.configFileName, "maximum.ypos")*/));
		this.configureTask();
	}
	
	/**
	 * used to searching for a task
	 * @param id
	 */
	public FireBuildingTask(Integer id)
	{
		super(id);
	}
	
	private void configureTask(){
		int aptRate = (int)(this.getGroundArea() / 50);
		this.setApartmentsPerFloor(aptRate);
		
		switch(this.getTemperature())
		{
		
			case WARM:
				this.setSuccess(5);
				break;
			case HOT:
				this.setSuccess((int)(this.groundArea / 10));
				break;
			case ON_FIRE:
				this.setSuccess(this.getApartmentsPerFloor() * (int)(this.groundArea / 10));
				break;
			case INFERNO:
				this.setSuccess(24+(this.getApartmentsPerFloor() * (int)(this.groundArea / 10)));
				break;
			default:
				this.setSuccess(0);
				break;
			
		}
		int hp = this.getFloors() * this.getApartmentsPerFloor() + (10 ^ this.getMatter().getValue());
		this.setBuildingHP(hp);
		
	}
	
	public Temperature getTemperature() {
		return temperature;
	}

	public void setTemperature(Temperature temperature) {
		this.temperature = temperature;
	}

	public Matter getMatter() {
		return this.matter;
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

	public Damage getDamageType() {
		return damageType;
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

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}
	
	public void logger(){
		FireBuildingTaskLogger.logFireBuildingTask(this);
	}
	
	public void header(){
		FireBuildingTaskLogger.saveHeader();
	}
	
	
}
