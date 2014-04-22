package ufrgs.maslab.abstractsimulator.algorithms;

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
        //long dx = (long) Math.abs(destPoint.getX() - sourcePoint.getX());
        //long dy = (long) Math.abs(destPoint.getY() - sourcePoint.getY());
        double res = 0d;
        switch (m) {
            case DISTANCE_EUCLIDIAN_SQ:
            	for(int k = 0; k < sourcePoint.getAttributes().size(); k++)
            	{
            		res += Math.pow((sourcePoint.getAttributes().get(k) - destPoint.getAttributes().get(k)),2);
            	}
            	return res;
                //System.out.println("dx: " + dx + " dy: " + dy + " dist: " + (dx*dx + dy*dy));
                //return (double) (dx*dx + dy*dy);
            case DISTANCE_MANHATTAN:
            	for(int k = 0; k < sourcePoint.getAttributes().size(); k++)
            	{
            		res += (sourcePoint.getAttributes().get(k) - destPoint.getAttributes().get(k));
            	}
            	return res;
                //return (double) (dx + dy);
            default:
            	for(int k = 0; k < sourcePoint.getAttributes().size(); k++)
            	{
            		res += Math.pow((sourcePoint.getAttributes().get(k) - destPoint.getAttributes().get(k)), m);
            	}
            	return Math.pow(res, m); 
                //return Math.pow(Math.pow(dx, m) + Math.pow(dy, m), 1.0/m);
        }
    }
}
