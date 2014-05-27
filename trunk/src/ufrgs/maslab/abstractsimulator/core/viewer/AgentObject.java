package ufrgs.maslab.abstractsimulator.core.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.Agent;
import ufrgs.maslab.abstractsimulator.variables.FireFighter;
import ufrgs.maslab.abstractsimulator.variables.FireStation;


public class AgentObject extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1824456882221783249L;

	private int r = 0;
	private int g = 0;
	private int b = 0;
	
	private int x = 0;
	private int y = 0;
	private Agent agent = null;
	
	public AgentObject(Agent a)
	{
		this.agent = a;
		this.x = a.getX();
		this.y = a.getY();
		
	}
	
	public void paintComponent(Graphics object)
	{
		super.paintComponent(object);
		
		object.setColor(new Color(this.r,this.g,this.b));
		if(this.agent instanceof FireFighter){
			object.drawOval(this.x, this.y, 20, 20);
		}
		
		if(this.agent instanceof FireStation)
		{
			object.drawRoundRect(this.x, this.y, 30, 30, 10, 10);
		}
		
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println(this.agent.getId());
		
	}

}
