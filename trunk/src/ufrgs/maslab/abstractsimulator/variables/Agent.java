package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.log.AgentLogger;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;

public class Agent extends Variable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Message> radio = new ArrayList<Message>();
	
	private ArrayList<Message> voice = new ArrayList<Message>();
	
	public Agent(){
		super();
	}
	
	public Agent(Integer id)
	{
		super(id);
		this.setX(this.rollDices());
		this.setY(this.rollDices());
	}
	
	public Agent(double x, double y){
		super();
		this.setY(y);
		this.setX(x);
	}

	@Override
	public void act(int time) {
		// TODO Auto-generated method stub
		
	}
	
	public void logger(){
		AgentLogger.logAgent(this);
	}
	
	public void header(){
		AgentLogger.saveHeader();
	}

	public ArrayList<Message> getRadioMessage() {
		return radio;
	}

	public void setRadio(ArrayList<Message> radio) {
		this.radio = radio;
	}

	public ArrayList<Message> getVoice() {
		return voice;
	}

	public void setVoice(ArrayList<Message> voice) {
		this.voice = voice;
	}
	
	

}
