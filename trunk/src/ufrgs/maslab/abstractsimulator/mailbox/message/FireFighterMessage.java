package ufrgs.maslab.abstractsimulator.mailbox.message;

public class FireFighterMessage extends HumanMessage {
	
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
	
	

}
