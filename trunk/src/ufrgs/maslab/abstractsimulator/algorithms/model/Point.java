package ufrgs.maslab.abstractsimulator.algorithms.model;

import ufrgs.maslab.gsom.util.Position;

public class Point
{

    int x;
    int y;
    int c; // cluster number
    
    public Point(Position point, int c)
    {
    	if(point.getAxisPosition().size() == 2)
    	{
    		this.x = point.getAxisPosition().get(0);
    		this.y = point.getAxisPosition().get(1);
    		this.c = c;
    	}
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
}
