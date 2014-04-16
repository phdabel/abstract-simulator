package ufrgs.maslab.abstractsimulator.mailbox.message;

public class FireFighterMessage extends HumanMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8977634813262565987L;
	/**
	 * ability to extinguish fire from buildings
	 */
	private int fireFighting;

	public int getFireFighting() {
		return fireFighting;
	}

	public void setFireFighting(int fireFighting) {
		this.fireFighting = fireFighting;
	}
	
	public String toString()
	{
		String s = super.toString();
		s += "Fire Fighting: "+this.getFireFighting()+" \n ";
		return s;
	}
	
	

}
