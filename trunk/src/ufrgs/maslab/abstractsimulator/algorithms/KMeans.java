package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import ufrgs.maslab.abstractsimulator.algorithms.model.Point;

public class KMeans extends Algorithm {

	int clusters = 2;
	int maxClusters = 0;
	
	double lastDistance = 0; 
	
	ArrayList<Point> centroids = new ArrayList<Point>();
	HashMap<Point, ArrayList<ArrayList<Double>>> cluster = new HashMap<Point, ArrayList<ArrayList<Double>>>();
	
	
	
	@Override
	public void run() {
		this.maxClusters = (int) Math.sqrt(this.field.getAllPoints().size());
		for(int k = this.clusters; k < this.maxClusters; k++)
		{
			this.centroids.clear();
			this.cluster.clear();
			this.initializeCentroids(k);
			int i = 0;
			while(i != 500){
				this.clusterInstances();
				this.updateCluster();
				i++;
			}
			for(Point c : this.cluster.keySet())
			{
				System.out.println(c.getAttributes().toString());
			}
			System.out.println(k+" clusters index: "+Calculations.dbIndex(this.cluster));
		
		}
		
	}
	
	private void initializeCentroids(int cl){
		for(int k = 0; k < cl; k++)
		{
			Point p = this.field.getRandomPoint();
			if(!this.cluster.keySet().contains(p))
			{
				ArrayList<ArrayList<Double>> instances = new ArrayList<ArrayList<Double>>();
				instances.add(p.getAttributes());
				this.cluster.put(p, instances);
				this.centroids.add(p);
			}
		}
	}
	
	private void clusterInstances(){
		for(Point p : this.field.getAllPoints())
		{
			double minDist = Double.MAX_VALUE;
			Point bestP = null;
			for(Point c : this.cluster.keySet())
			{
				if(Calculations.distance(p, c, 2) < minDist)
				{
					minDist = Calculations.distance(p, c, 2);
					bestP = c;
				}
			}
			this.cluster.get(bestP).add(p.getAttributes());
			p.setCluster(this.centroids.indexOf(bestP));
		}
			
	}
	
	private void updateCluster(){
		for(Point p : this.cluster.keySet())
		{
			for(ArrayList<Double> i : this.cluster.get(p))
			{
				p.setAttributes(Calculations.addArray(i, p.getAttributes()));
			}
			p.setAttributes(Calculations.divideArray(p.getAttributes(), this.cluster.get(p).size()));
		}
	}

}
