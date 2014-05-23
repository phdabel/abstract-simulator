package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ufrgs.maslab.abstractsimulator.algorithms.model.*;

public abstract class Algorithm
{

    public final static double UNDEFINED = Double.POSITIVE_INFINITY;

    // DISTANCE_MANHATTAN, DISTANCE_EUCLIDIAN or DISTANCE_EUCLIDIAN_SQ
    // Euclidian Sq is the same as Euclidian, but does not take the square root,
    // but instead squares the epsilon parameter. Thus it is a lot faster.
    public final static int DISTANCE_METRIC = Calculations.DISTANCE_MANHATTAN;

    
    protected DataSet field;

    /**
     * Constructor.
     */
    public Algorithm()
    {
    }

    /**
     * Run the algorithm.
     */
    public abstract void run();

    // implemented methods

    /**
     * Set the field.
     *
     * @param field Field
     */
    public void setField(DataSet field)
    {
        this.field = field;
    }


    protected int count(ClusterNode c)
    {
        int num = 1;

        for (ClusterNode child : c.getChildren()) {
            num += count(child);
        }

        return num;
    }

    

    /**
     * Print the cluster tree.
     */
    public void printTree(ClusterNode node)
    {
        System.out.println("Ch: " + node.getPoints().size() + " {");

        for (ClusterNode child : node.getChildren()) {
            printTree(child);
        }

        System.out.println("}");
    }

    /**
     * Cluster tree.
     */
    class ClusterNode
    {
        /**
         * Children of this node.
         */
        List<ClusterNode> children = new ArrayList<ClusterNode>();

        /**
         * Points in this node.
         */
        List<Point> points = new ArrayList<Point>();

        /**
         * This node's parent.
         */
        ClusterNode parent;

        /**
         * The split point.
         */
        Point splitPoint;

        /**
         * Removed marker.
         */
        boolean removed = false;


        /**
         * Constructor.
         *
         * @param parent Parent of the created node
         */
        public ClusterNode(ClusterNode parent)
        {
            this.parent = parent;
        }

        /**
         * Get the children.
         *
         * @return children
         */
        public List<ClusterNode> getChildren()
        {
            return children;
        }

        /**
         * Get the points.
         *
         * @return points
         */
        public List<Point> getPoints()
        {
            return points;
        }

        /**
         * Get the parent.
         *
         * @return parent
         */
        public ClusterNode getParent()
        {
            return parent;
        }

        /**
         * Add a child.
         *
         * @param child Child of the node.
         */
        public void addChild(ClusterNode child)
        {
            children.add(child);
        }

        /**
         * Add a point.
         *
         * @param point Point of this node.
         */
        public void addPoint(Point point)
        {
            points.add(point);
        }

        /**
         * Add multiple points.
         *
         * @param points points to add
         */
        public void addPoints(Collection<Point> c)
        {
            points.addAll(c);
        }

        /**
         * Remove.
         */
        public void remove()
        {
            removed = true;
        }

        /**
         * Is removed.
         */
        public boolean isRemoved()
        {
            return removed;
        }
    }

}