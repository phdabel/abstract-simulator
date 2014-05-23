package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.ArrayList;

public class Point
{
	private int id;
    private double x;
    private double y;
    private Double z = null;
    private Centroid cluster; // cluster number
    private ArrayList<Double> attributes = new ArrayList<Double>();

    public Point(int id, double x, double y, Centroid cluster, ArrayList<Double> attributes)
    {
    	this.id = id;
    	this.setAttributes(attributes);
    	this.x = x;
    	this.y = y;
    	this.cluster = cluster;
    }
    
    public void setZ(double z)
    {
    	this.z = z;
    }
    
    public Point(int id, double x, double y, Centroid c)
    {
    	this.id = id;
        this.x = x;
        this.y = y;
        this.cluster = c;
    }

	public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }
    
    public double getZ()
    {
    	return this.z;
    }

    public Centroid getCluster()
    {
        return this.cluster;
    }

    public void setCluster(Centroid c)
    {
        this.cluster = c;
    }

	public ArrayList<Double> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Double> attributes) {
		this.attributes = attributes;
	}
	
	public int hashCode()
	{
        return this.getId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
