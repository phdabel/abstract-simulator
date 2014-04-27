package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import ufrgs.maslab.abstractsimulator.algorithms.model.Point;


/**
 * Handy calculations.
 */
public class Calculations
{

    public final static int DISTANCE_EUCLIDIAN = 2;
    public final static int DISTANCE_MANHATTAN = 1;
    public final static int DISTANCE_EUCLIDIAN_SQ = -1;

    /**
     * @param p: Point from which we want the distance to another point
     * @param number: The number'th closest point to p.
     *
     * @pre: minDistances are calculated
     * @return: returns the point with the number'th minimal distance to p
     */
    Point minDistancePoint(Point p,int number)
    {
        return null;
    }

    /**
     * @param minPoints: Minimal amount of points that should be accounted for in the densityDistance
     * @param sourcePoint: Source point
     * @param destPoint: Destination point
     * @param m: 2 = Euclidean, 1 = Manhatten
     *
     * @pre minDistances are calculated
     * @return Returns the density distance between point sourcePoint and point endPoint
     */
    double densityDistance(int minPoints, Point sourcePoint, Point endPoint,int m)
    {
        return(Math.max(distance(sourcePoint,minDistancePoint(sourcePoint,minPoints),m),distance(sourcePoint,endPoint,m)));
    }
    
    public static double distance(ArrayList<Double> source, ArrayList<Double> dest, int m)
    {
    	double res = 0d;
    	switch (m) {
	        case DISTANCE_EUCLIDIAN_SQ:
	        	for(int k = 0; k < source.size(); k++)
	        	{
	        		res += Math.pow((source.get(k) - dest.get(k)),2);
	        	}
	        	return res;
	            //System.out.println("dx: " + dx + " dy: " + dy + " dist: " + (dx*dx + dy*dy));
	            //return (double) (dx*dx + dy*dy);
	        case DISTANCE_MANHATTAN:
	        	for(int k = 0; k < source.size(); k++)
	        	{
	        		res += (source.get(k) - dest.get(k));
	        	}
	        	return res;
	            //return (double) (dx + dy);
	        default:
	        	for(int k = 0; k < source.size(); k++)
	        	{
	        		res += Math.pow((source.get(k) - dest.get(k)), m);
	        	}
	        	return Math.pow(res, m); 
	            //return Math.pow(Math.pow(dx, m) + Math.pow(dy, m), 1.0/m);
    	}
    	
    }

    /**
     * Determine the distance between sourcePoint and destPoint.
     *
     * @param sourcePoint Source point
     * @param destPoint Destination point
     * @param m 2 = Euclidean, 1 = Manhatten
     * @return Distance from sourcePoint to destPoint
     */
    public static double distance(Point sourcePoint, Point destPoint, int m)
    {
    	return distance(sourcePoint.getAttributes(), destPoint.getAttributes(), m);
    }
    
    public static ArrayList<Double> addArray(ArrayList<Double> array1, ArrayList<Double> array2)
    {
    	ArrayList<Double> result = new ArrayList<Double>();
    	for(int k = 0; k < array1.size(); k++)
    	{
    		result.add(array1.get(k) + array2.get(k));
    	}
    	
    	return result;
    }
    
    public static ArrayList<Double> divideArray(ArrayList<Double> array1, Integer scalar)
    {
    	ArrayList<Double> result = new ArrayList<Double>();
    	for(int k = 0; k < array1.size(); k++)
    	{
    		result.add(array1.get(k) / scalar);
    	}
    	return result;
    }
    
    /**
     * db index measurement 
     * 
     * @param cluster
     * @return
     */
    public static Double dbIndex(HashMap<Point, ArrayList<ArrayList<Double>>> cluster)
    {
    	if(cluster.size() == 1)
    		return 0d;
    	
    	ArrayList<Point> c = new ArrayList<Point>(cluster.keySet());
    	Double sum = 0d;
    	Double maxValue = 0d;
    	for(int i = 0; i < c.size(); i++)
    	{
    		for(int j = i+1; j< c.size(); j++)
    		{
    			Double tmp = 0d;
    			tmp = intraClusterVariance(c.get(i), cluster.get(c.get(i))) + intraClusterVariance(c.get(j), cluster.get(c.get(j)));
    			tmp /= distance(c.get(i), c.get(j),2);
    			if(tmp > maxValue)
    				maxValue = tmp;
    		}
    		sum += maxValue;
    	}
    	
    	return sum / cluster.size();
    }
    
    /**
     * instracluster variance
     * @param c
     * @param instances
     * @return
     */
    public static Double intraClusterVariance(Point c, ArrayList<ArrayList<Double>> instances)
    {
    	Double var = 0d;
    	for(ArrayList<Double> i : instances)
    	{
    		var += Math.pow(distance(i, c.getAttributes(), 2),2);
    	}
    	return (var / instances.size());    	
    }
    
}
