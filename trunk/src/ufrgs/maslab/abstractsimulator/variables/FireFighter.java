package ufrgs.maslab.abstractsimulator.variables;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.interfaces.Platoon;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;


public class FireFighter extends Human implements Platoon {

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
	
	public void act(int time){
		//if(time < 3)
		//{
			for(Integer t : this.getDomain().keySet())
			{
				try {
					this.sendRadioMessage(new FireBuildingTaskMessage(t, this.getDomain().get(t), this, MessageType.RADIO));
				} catch (SimulatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		//}
		/*
		System.out.println("I'm fire fighter "+this.getId()+" at time "+time);
		for(Integer v : this.getDomain())
		{
			System.out.println("Task "+v);
		}*/
	}

	@Override
	public void sendRadioMessage(Message msg) throws SimulatorException {
		if(msg.getType() != MessageType.RADIO)
			throw new SimulatorException(Transmitter.getProperty("exception.properties", "exception.not.radio.message"));
		this.getVoice().add(msg);
		
	}

}
