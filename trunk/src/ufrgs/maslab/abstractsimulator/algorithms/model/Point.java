package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.ArrayList;


public class Point
{
	
    int x;
    int y;
    int c; // cluster number
    private ArrayList<Double> attributes = new ArrayList<Double>();

    public Point(int x, int y, int c, ArrayList<Double> attributes)
    {
    	this.setAttributes(attributes);
    	this.x = x;
    	this.y = y;
    	this.c = c;
    }
    
    public Point(int x, int y, int c)
    {
        this.x = x;
        this.y = y;
        this.c = c;
    }

	public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getCluster()
    {
        return c;
    }

    public void setCluster(int c)
    {
        this.c = c;
    }

	public ArrayList<Double> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Double> attributes) {
		this.attributes = attributes;
	}
}
