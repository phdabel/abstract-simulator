package ufrgs.maslab.abstractsimulator.mailbox.message;

public class HumanMessage extends AgentMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6957318464283032931L;
	/**
	 * physical attributes
	 */
	private int strength = 1;
	private int dexterity = 1;
	private int stamina = 1;
	
	/**
	 * social attributes
	 */
	private int charisma = 1;
	private int appearance = 1;
	private int leadership = 1;
	
	/**
	 * mental attributes
	 */
	private int intelligence = 1;
	private int reasoning = 1;
	private int perception = 1;
	
	
	/**
	 * other attributes
	 */
	private int will = 5;
	private int hp = 7;
	public int getStrength() {
		return strength;
	}
	public void setStrength(int strength) {
		this.strength = strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public int getCharisma() {
		return charisma;
	}
	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}
	public int getAppearance() {
		return appearance;
	}
	public void setAppearance(int appearance) {
		this.appearance = appearance;
	}
	public int getLeadership() {
		return leadership;
	}
	public void setLeadership(int leadership) {
		this.leadership = leadership;
	}
	public int getIntelligence() {
		return intelligence;
	}
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	public int getReasoning() {
		return reasoning;
	}
	public void setReasoning(int reasoning) {
		this.reasoning = reasoning;
	}
	public int getPerception() {
		return perception;
	}
	public void setPerception(int perception) {
		this.perception = perception;
	}
	public int getWill() {
		return will;
	}
	public void setWill(int will) {
		this.will = will;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public String toString(){
		String s = super.toString();
		s += "Strength: "+this.getStrength()+" \n "
	      + "Dexterity: "+this.getDexterity()+" \n"
	      + "Stamina: "+this.getStamina()+" \n "
	      + "Charisma: "+this.getCharisma()+" \n "
	      + "Appearance: "+this.getAppearance()+" \n "
	      + "Leadership: "+this.getLeadership()+" \n "
	      + "Intelligence: "+this.getIntelligence()+" \n "
	      + "Reasoning: "+this.getReasoning()+" \n "
	      + "Perception: "+this.getPerception()+" \n "
	      + "Will: "+this.getWill()+" \n "
	      + "HP: "+this.getHp()+" \n ";
	      return s;
	}

}
