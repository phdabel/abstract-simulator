package ufrgs.maslab.abstractsimulator.variables;


public class FireFighter extends Human {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608281129356235256L;
	
	/**
	 * <ul>
	 * <li>Default constructor of the fire fighter human</li>
	 * <li>Attributes</li>
	 * <li>Strength</li>
	 * <li>Dexterity</li>
	 * <li>Stamina</li>
	 * <li>Charisma</li>
	 * <li>Appearance</li>
	 * <li>Leadership</li>
	 * <li>Intelligence</li>
	 * <li>Reasoning</li>
	 * <li>Perception</li>
	 * <li>Will</li>
	 * <li>HP</li>
	 * </ul>
	 * <br/>
	 * <ul>
	 * <li>Skills</li>
	 * <li>Fire Fighting</li>
	 * <li>Advantages</li>
	 * </ul>
	 * 
	 */
	public FireFighter()
	{
		super();
		this.configureFireFighterSkills();
	}
	
	/**
	 * ability to extinguish fire from buildings
	 */
	private int fireFighting;
	
	public void configureFireFighterSkills(){
		this.setFireFighting();
	}
	
	/**
	 * constructor to search for fire fighter
	 * @param id
	 */
	public FireFighter(Integer id)
	{
		super(id);
	}

	public int getFireFighting() {
		return fireFighting;
	}

	private void setFireFighting() {
		this.fireFighting = 1 + this.rollDices(5);
	}

}
