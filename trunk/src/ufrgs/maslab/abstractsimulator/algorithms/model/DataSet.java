package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.*;

import ufrgs.maslab.abstractsimulator.normalization.Normalizer;

public class DataSet
{

    double min_x;
    double max_x;

    double min_y;
    double max_y;

    double min_z;
    double max_z;
    
    public TreeMap<Integer,Point> points;

    public DataSet()
    {
        min_x = Double.MAX_VALUE;
        max_x = 0;
        min_y = Double.MAX_VALUE;
        max_y = 0;
        min_z = Double.MAX_VALUE;
        max_z = 0;
        points = new TreeMap<Integer,Point>();
    }
    
    public Point getRandomPoint(){
    	Random random = new Random();
    	
    	List<Integer> keys = new ArrayList<Integer>(this.points.keySet());
    	Integer randomKey = keys.get(random.nextInt(keys.size()));
    	
    	return this.points.get(randomKey);
    }

    public void addPoint(Point p)
    {
    	//System.out.println(p.getId()+" - "+p.getX());
	        if (this.points.containsKey(p.getId())) {
	            return;
	        }
	        points.put(p.getId(), p);
	
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
    }
    
    public void normalize(Normalizer normalizer) {
    	ArrayList<Point> pointList = new ArrayList<Point>();
    	pointList.addAll(getAllPoints());
    	normalizer.normalize(pointList);
    }
    
    public int size()
    {
        return points.size();
    }

    public Collection<Point> getAllPoints()
    {
        return points.values();
    }

    public TreeMap<Integer,Point> getPointsMap()
    {
        return points;
    }

    public Point getPoint(int id)
    {
        return points.get(id);
    }

    /**
     * Get the width of the field.
     */
    public double getWidth()
    {
        return (max_x - min_x);
    }

    /**
     * Get the height of the field.
     */
    public double getHeight()
    {
        return (max_y - min_y);
    }

    public double getMaxX()
    {
        return max_x;
    }

    public double getMinX()
    {
        return min_x;
    }

    public double getMaxY()
    {
        return max_y;
    }

    public double getMinY()
    {
        return min_y;
    }
}
