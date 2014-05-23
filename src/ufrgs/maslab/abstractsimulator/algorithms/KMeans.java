package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import ufrgs.maslab.abstractsimulator.algorithms.model.Centroid;
import ufrgs.maslab.abstractsimulator.algorithms.model.Point;

public class KMeans extends Algorithm {

	int clusters = 2;
	int maxClusters = 0;
	Double betterCluster = Double.MAX_VALUE;
	double lastDistance = 0;
	public ArrayList<Double> weight = new ArrayList<Double>();
	
	//centroid points
	ArrayList<Centroid> centroids = new ArrayList<Centroid>();
	
	//centroid points with
	HashMap<Centroid, ArrayList<Point>> cluster = new HashMap<Centroid, ArrayList<Point>>();
	
	public ArrayList<Centroid> bestCentroids = new ArrayList<Centroid>();
	
	
	
	@Override
	public void run() {
		this.maxClusters = (int) Math.sqrt(this.field.getAllPoints().size());
		for(int k = this.clusters; k < this.maxClusters; k++)
		{
			this.centroids.clear();
			this.cluster.clear();
			this.initializeCentroids(k);
			int i = 0;
			while(i != 100){
				this.clusterInstances();
				this.updateCluster();
				i++;
			}
			for(Centroid c : this.cluster.keySet())
			{
				System.out.println(c.getAttributes().toString());
			}
			Double clusterMeasure = Calculations.dbIndex(this.cluster);
			System.out.println(k+" clusters index: "+clusterMeasure);
			if(clusterMeasure < this.betterCluster)
			{
				this.bestCentroids.clear();
				this.betterCluster = clusterMeasure;
				for(Centroid ptmp : this.cluster.keySet())
				{
					this.bestCentroids.add(ptmp);
					for(Point n : this.cluster.get(ptmp)){
						n.setCluster(ptmp);
					}
				}
			}
		
		}
		
	}
	
	/**
	 * initialize centroids for k-means algorithm
	 * 
	 * @param cl <code>int<code> number of centroids for k-means
	 */
	private void initializeCentroids(int cl){
		for(int k = 0; k < cl; k++)
		{
			Centroid n = null;
			while(n == null)
			{
				Point p = this.field.getRandomPoint();
				n = new Centroid();
				n.setId(k);
				n.setAttributes(p.getAttributes());
				n.setX(p.getX());
				n.setY(n.getY());
			}
			
			//Point p = new Point(n, n.getPosition().getAxisPosition().get(1),0);
			//if(n.getPosition().getDimension() > 2)
			//	p.setZ(n.getPosition().getAxisPosition().get(2));
			if(!this.cluster.keySet().contains(n))
			{
				ArrayList<Point> instances = new ArrayList<Point>();
				//instances.add(n);
				//Point c = new Point(n.getPosition().getAxisPosition().get(0), n.getPosition().getAxisPosition().get(1),0);
				//ArrayList<Double> cWeights = new ArrayList<Double>(Collections.nCopies(n.getWeights().length, 0.0));
				//c.setAttributes(cWeights);
				this.cluster.put(n, instances);
				this.centroids.add(n);
			}
			/*if(!this.cluster.keySet().contains(p))
			{
				ArrayList<ArrayList<Double>> instances = new ArrayList<ArrayList<Double>>();
				instances.add(p.getAttributes());
				this.cluster.put(p, instances);
				this.centroids.add(p);
			}*/
		}
	}
	
	private void clusterInstances(){
		for(Point n : this.field.getAllPoints())
		{
			ArrayList<Double> weights = new ArrayList<Double>();
			for(double d : n.getAttributes())
			{
				weights.add(d);
			}
			double minDist = Double.MAX_VALUE;
			Centroid bestP = null;
			for(Centroid c : this.cluster.keySet())
			{
				if(weight.isEmpty()){
					if(Calculations.distance(weights, c.getAttributes(), 2) < minDist)
					{
						minDist = Calculations.distance(weights, c.getAttributes(), 2);
						bestP = c;
					}
				}else{
					if(Calculations.distance(weights, c.getAttributes(), 3, this.weight) < minDist)
					{
						minDist = Calculations.distance(weights, c.getAttributes(), 3, this.weight);
						bestP = c;
					}
				}
			}
			this.cluster.get(bestP).add(n);
			
		}
			
	}
	
	private void updateCluster(){
		for(Centroid p : this.cluster.keySet())
		{
			for(Point i : this.cluster.get(p))
			{ 
				ArrayList<Double> weights = new ArrayList<Double>();
				for(double d : i.getAttributes())
				{
					weights.add(d);
				}
				p.setAttributes(Calculations.addArray(weights, p.getAttributes()));
			}
			p.setAttributes(Calculations.divideArray(p.getAttributes(), this.cluster.get(p).size()));
		}
	}

}
