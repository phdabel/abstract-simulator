package ufrgs.maslab.abstractsimulator.core.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class Viewer extends JApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7157083672751898254L;
	static final int width = 1000;
	static final int height = 750;
	static Environment<? extends Entity> env = null;
	static int time = 0;
	static JPanel area = null;
	
	public static void main(Environment<? extends Entity> e, int t)
	{

		env = e;
		time = t;
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());
		area = new JPanel();
		
		area.setBackground(Color.BLUE);
		content.add(area);
		mountObjects();
		JFrame window = new JFrame("Viewer");
		window.setContentPane(content);
		window.setSize(width, height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	
	public void init()
	{
		
	}
	
	private static void mountObjects()
	{
		for(Value v : env.getValues())
		{
			if(v instanceof Task)
				area.add(new TaskObject((Task)v));
		}
		for(Variable v : env.getVariables())
		{
			if(v instanceof Agent)
				area.add(new AgentObject((Agent)v));
		}
	}


}
