package ufrgs.maslab.abstractsimulator.core.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.values.Task;

public class TaskObject extends JPanel  implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4397072570513170778L;
	
	private int r = 0;
	private int g = 0;
	private int b = 0;
	
	private int x = 0;
	private int y = 0;
	private Task task = null;
	
	public TaskObject(Task t)
	{
		this.task = t;
		this.x = t.getX();
		this.y = t.getY();
		
	}
	
	public void paintComponent(Graphics object)
	{
		super.paintComponent(object);
		
		object.setColor(new Color(this.r,this.g,this.b));
		if(this.task instanceof FireBuildingTask){
			FireBuildingTask b = (FireBuildingTask)this.task;
			int area = (int) Math.abs(Math.sqrt(b.getGroundArea()));
			object.drawRect(this.x, this.y, area, area);
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(this.task.getId());
		
	}

}
