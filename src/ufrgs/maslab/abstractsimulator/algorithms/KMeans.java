package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.gsom.neuron.Neuron;
import ufrgs.maslab.gsom.util.Position;

public class KMeans extends Algorithm {

	int clusters = 2;
	int maxClusters = 0;
	Double betterCluster = Double.MAX_VALUE;
	double lastDistance = 0; 
	
	//centroid points
	//ArrayList<Position> centroids = new ArrayList<Position>();
	ArrayList<Point> centroids = new ArrayList<Point>();
	
	//centroid points with
	HashMap<Point, ArrayList<Neuron>> cluster = new HashMap<Point, ArrayList<Neuron>>();
	//HashMap<Point, ArrayList<ArrayList<Double>>> cluster = new HashMap<Point, ArrayList<ArrayList<Double>>>();
	
	
	
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
			Double clusterMeasure = Calculations.dbIndex(this.cluster);
			System.out.println(k+" clusters index: "+clusterMeasure);
			if(clusterMeasure < this.betterCluster)
			{
				this.betterCluster = clusterMeasure;
				int ktmp = 0;
				for(Point ptmp : this.cluster.keySet())
				{
					for(Neuron n : this.cluster.get(ptmp)){
						n.setCluster(ktmp);
					}
					ktmp++;
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
			Neuron n = null;
			while(n == null)
				n = this.field.getRandomPoint();
			//Point p = this.field.getRandomPoint();
			//System.out.println("k "+k+" keyset size "+this.cluster.keySet().size()+" neuron "+n);
			Point p = new Point(n.getPosition().getAxisPosition().get(0), n.getPosition().getAxisPosition().get(1),0);
			if(n.getPosition().getDimension() > 2)
				p.setZ(n.getPosition().getAxisPosition().get(2));
			if(!this.cluster.keySet().contains(p))
			{
				ArrayList<Neuron> instances = new ArrayList<Neuron>();
				instances.add(n);
				Point c = new Point(n.getPosition().getAxisPosition().get(0), n.getPosition().getAxisPosition().get(1),0);
				ArrayList<Double> cWeights = new ArrayList<Double>(Collections.nCopies(n.getWeights().length, 0.0));
				c.setAttributes(cWeights);
				this.cluster.put(c, instances);
				this.centroids.add(c);
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
		for(Neuron n : this.field.getAllPoints())
		{
			ArrayList<Double> weights = new ArrayList<Double>();
			for(double d : n.getWeights())
			{
				weights.add(d);
			}
			double minDist = Double.MAX_VALUE;
			Point bestP = null;
			for(Point c : this.cluster.keySet())
			{
				if(Calculations.distance(weights, c.getAttributes(), 2) < minDist)
				{
					minDist = Calculations.distance(weights, c.getAttributes(), 2);
					bestP = c;
				}
			}
			this.cluster.get(bestP).add(n);
			
		}
		/*for(Point p : this.field.getAllPoints())
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
		}*/
			
	}
	
	private void updateCluster(){
		for(Point p : this.cluster.keySet())
		{
			for(Neuron i : this.cluster.get(p))
			{ 
				ArrayList<Double> weights = new ArrayList<Double>();
				for(double d : i.getWeights())
				{
					weights.add(d);
				}
				p.setAttributes(Calculations.addArray(weights, p.getAttributes()));
			}
			p.setAttributes(Calculations.divideArray(p.getAttributes(), this.cluster.get(p).size()));
			/*for(ArrayList<Double> i : this.cluster.get(p))
			{
				p.setAttributes(Calculations.addArray(i, p.getAttributes()));
			}
			p.setAttributes(Calculations.divideArray(p.getAttributes(), this.cluster.get(p).size()));*/
		}
	}

}
