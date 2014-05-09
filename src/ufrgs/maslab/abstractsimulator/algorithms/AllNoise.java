package ufrgs.maslab.abstractsimulator.algorithms;

import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.gsom.neuron.Neuron;


/**
 * Stub algorithm.
 */
public class AllNoise extends Algorithm
{
    /**
     * Constructor.
     */
    public AllNoise()
    {
        super();
    }

    /**
     * Find the parameters
     *
     * @param ci Minimum number of clusters
     * @param cj Maximum number of clusters
     * @param n Number of points
     */
    public void findParameters(int ci, int cj, int n, long width, long height, int startx, int starty)
    {
        super.findParameters(ci, cj, n, width, height, startx, starty);
    }

    public void run()
    {
        for (Neuron p : field.getAllPoints()) {
            p.setCluster(0);
        }
    }
}