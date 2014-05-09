package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.*;

import ufrgs.maslab.gsom.neuron.Neuron;
import ufrgs.maslab.gsom.util.Position;

public class Field
{

    int min_x;
    int max_x;

    int min_y;
    int max_y;

    int min_z;
    int max_z;
    
    public TreeMap<Position, Neuron> points;
    //public TreeMap<Long,Point> points;

    public Field()
    {
        min_x = Integer.MAX_VALUE;
        max_x = 0;
        min_y = Integer.MAX_VALUE;
        max_y = 0;
        min_z = Integer.MAX_VALUE;
        max_z = 0;
        points = new TreeMap<Position, Neuron>();
        //points = new TreeMap<Long,Point>();
    }
    
    public Neuron getRandomPoint(){
    	Random random = new Random();
    	List<Position> keys = new ArrayList<Position>(this.points.keySet());
    	
    	Position randomKey = keys.get(random.nextInt(keys.size()));
    	//System.out.println("position "+randomKey.getAxisPosition());
    	return this.points.get(randomKey);
    }
    /*public Point getRandomPoint(){
    	Random random = new Random();
    	
    	List<Long> keys = new ArrayList<Long>(this.points.keySet());
    	Long randomKey = keys.get(random.nextInt(keys.size()));
    	
    	return this.points.get(randomKey);
    }*/

    /*public void addPoint(Point p)
    {
	        if (hasPoint(p.getX(), p.getY())) {
	            return;
	        }
	        points.put(getKey(p.getX(), p.getY()), p);
	
	        if (p.getX() < min_x) {
	            min_x = p.getY();
	        }
	        if (p.getX() > max_x) {
	            max_x = p.getX();
	        }
	
	        if (p.getY() < min_y) {
	            min_y = p.getY();
	        }
	        if (p.getY() > max_y) {
	            max_y = p.getY();
	        }	
    }*/
    public void addPoint(Neuron n)
    {
    	if(hasPoint(n.getPosition()))
    		return;
    	this.points.put(n.getPosition(), n);
    	if(n.getPosition().getDimension() > 2)
    	{
    		if(n.getPosition().getAxisPosition().get(2) < min_z)
    		{
    			min_z = n.getPosition().getAxisPosition().get(2);
    		}
    		
    		if(n.getPosition().getAxisPosition().get(2) > max_z)
    		{
    			max_z = n.getPosition().getAxisPosition().get(2);
    		}
    			
    	}
    	
    	if(n.getPosition().getAxisPosition().get(0) < min_x)
		{
			min_x = n.getPosition().getAxisPosition().get(0);
		}
		
		if(n.getPosition().getAxisPosition().get(0) > max_x)
		{
			max_x = n.getPosition().getAxisPosition().get(0);
		}
		
		if(n.getPosition().getAxisPosition().get(1) < min_y)
		{
			min_y = n.getPosition().getAxisPosition().get(1);
		}
		
		if(n.getPosition().getAxisPosition().get(1) > max_y)
		{
			max_y = n.getPosition().getAxisPosition().get(1);
		}
		
    }

    public int size()
    {
        return points.size();
    }

    public Collection<Neuron> getAllPoints()
    {
    	return points.values();
    }
    
    public TreeMap<Position, Neuron> getPointsMap()
    {
    	return points;
    }
    
    /*
    public Collection<Point> getAllPoints()
    {
        return points.values();
    }

    public TreeMap<Long,Point> getPointsMap()
    {
        return points;
    }*/

    public Neuron getPoints(Position p)
    {
    	return points.get(p);
    }
    /*
    public Point getPoint(int x, int y)
    {
        return points.get(getKey(x, y));
    }*/

    public boolean hasPoint(Position p)
    {
    	return points.containsKey(p);
    }
    /*
    public boolean hasPoint(int x, int y)
    {
        return points.containsKey(getKey(x, y));
    }

    public long getKey(int x, int y)
    {
        long key = x;
        key = key << 32;
        key += y;
        return key;
    }*/

    /**
     * Get the width of the field.
     */
    public long getWidth()
    {
        return (long) (max_x - min_x);
    }

    /**
     * Get the height of the field.
     */
    public long getHeight()
    {
        return (long) (max_y - min_y);
    }

    public int getMaxX()
    {
        return max_x;
    }

    public int getMinX()
    {
        return min_x;
    }

    public int getMaxY()
    {
        return max_y;
    }

    public int getMinY()
    {
        return min_y;
    }
}
