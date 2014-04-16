package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;


public class BranchAndBound<Var extends Variable, Val extends Value> {
	
	//TODO review the algorithm
	
	/**
	 * Map<Variable, Value>
	 */
	public Map<Integer, Integer> partialAssignment = new HashMap<Integer, Integer>();

	/**
	 * map to associate classes of variables and values
	 */
	private HashMap<Integer, Class<? extends Entity>> classAssociation = new HashMap<Integer, Class<? extends Entity>>();
	
	public Double upperBound = Double.MAX_VALUE;
	
	public Double lowerBound = 0d;
	
	public Double alpha = 0d;
	
	private Environment<Entity> env = null;
	
	/**
	 * list of variables
	 */
	public Queue<Integer> x = new LinkedList<Integer>();
	
	/**
	 * list of values
	 */
	public ArrayList<Integer> d = new ArrayList<Integer>();
	
	/**
	 * constraints map
	 * Map<Constraint<Variable, Value>,Double>
	 * 
	 */
	public Map<Constraint, Double> c = new HashMap<Constraint, Double>();
	
	public BranchAndBound(Queue<Integer> x, ArrayList<Integer> d, Map<Constraint, Double> constraints){
		this.x = x;
		this.d = d;
		this.c = constraints;
		
	}
	
	public void getAssignment(Double alpha)
	{
		try {
			this.branchAndBound(alpha);
		} catch (SimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getAssignment()
	{
		try {
			this.branchAndBound();
		} catch (SimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void branchAndBound(Double alpha) throws SimulatorException
	{
		this.alpha = alpha;
		this.branchAndBound(this.partialAssignment, this.lowerBound, this.upperBound, this.x, this.d, this.c);
	}
	
	public void branchAndBound() throws SimulatorException
	{
		this.upperBound = this.branchAndBound(this.partialAssignment, this.lowerBound, this.upperBound, this.x, this.d, this.c);	
	}
	
	@SuppressWarnings("unchecked")
	public Double branchAndBound(Map<Integer, Integer>pa, Double lb, Double ub, Queue<Integer> x, ArrayList<Integer> d, Map<Constraint, Double> c) throws SimulatorException
	{
		if(x.isEmpty()){
			this.partialAssignment.putAll(pa);
			return lb;
		}
		//this.lowerBound = lb;

		
		//variables
		Integer tmpX = x.poll();
		Var i = (Var) env.findVariableByID(tmpX, (Class<? extends Variable>) this.classAssociation.get(tmpX));
		
		for(Integer tmpD: i.getDomain().keySet())
		{
			
			ArrayList<Integer> dd = (ArrayList<Integer>) i.getDomain().keySet();
			Map<Constraint,Double> cc = c;
			
			this.partialAssignment.putAll(pa);
			this.partialAssignment.put(i.getId(), tmpD);
						
			//Constraint<Var,Val> ci = new Constraint<Var,Val>(i,v);
			
			Val v = (Val) env.findValueByID(tmpD, (Class<? extends Value>) this.classAssociation.get(tmpD));
			Double nLb = this.lowerBound + v.getValue();
			
			this.lookAhead(i.getId(), tmpD);
			
			/*if(this.alpha != 0d){
				nLb = nLb - (this.notAssignedTasks(this.partialAssignment));
			}*/
			if(this.localConsist(nLb, ub, x, dd, cc))
			{
				this.upperBound = this.branchAndBound(this.partialAssignment, nLb, ub, x, dd, cc);
			}
			
		}

		return this.upperBound;
	}
	
	@SuppressWarnings("unchecked")
	public void lookAhead(Integer i, Integer a){
		
		//verify binary constraints
		ArrayList<Integer> iColeagues = new ArrayList<Integer>();
		for(Integer j : iColeagues)
		{
			if(!j.equals(i))
			{
				Var varJ = (Var) env.findVariableByID(j, (Class<? extends Variable>) this.classAssociation.get(j));
				for(Integer b : varJ.getDomain().keySet())
				{
					/**
					 * TODO
					 * do the look ahead function
					 * 
					 */
					
					//b.getId();
					/*
					if(resourceConstraint(this.partialAssignment,b))
					{
						Constraint<Var,Val> cj = new Constraint<Var,Val>((Var)j,b);
						if(c.containsKey(cj))
						{
							Double cost = Double.MAX_VALUE;
							c.put(cj, cost);
							
						}
					}else{
						Constraint<Var,Val> cj = new Constraint<Var,Val>((Var)j,b);
						Double cb = a.getValue() - b.getValue();
						Double cost = c.get(cj) + ((1 - this.alpha) * cb);
						c.put(cj, cost);
						
					}*/
				}
				
			}
		}
		
		
	}
	
	public boolean localConsist(Double lb, Double ub, Queue<Integer> x, ArrayList<Integer> d, Map<Constraint, Double> c)
	{
		return (ub > lb);
	}
	
	
	public Environment<Entity> getEnv() {
		return env;
	}

	public void setEnv(Environment<Entity> env) {
		this.env = env;
	}


	public HashMap<Integer, Class<? extends Entity>> getClassAssociation() {
		return classAssociation;
	}

	public void setClassAssociation(HashMap<Integer, Class<? extends Entity>> classAssociation) {
		this.classAssociation = classAssociation;
	}


	/**
	 *  internal class - constraints
	 *  
	 */
	public static class Constraint {
		
		
		//variables
		private Integer i;
		private Integer j;
		//values
		private Integer a;
		private Integer b;
		private boolean binary = false;
		private Object[] keys = new Object[4];
		
		
		public Constraint(Integer i, Integer a)
		{
			this.i = i;
			this.a = a;
			keys[0] = i;
			keys[1] = a;
			this.binary = false;
		}
		
		public Constraint(Integer i, Integer a, Integer j, Integer b)
		{
			this.i = i;
			this.a = a;
			this.j = j;
			this.b = b;
			this.binary = true;
			keys[0] = i;
			keys[1] = a;
			keys[2] = j;
			keys[3] = b;

		}

		public Integer getI() {
			return i;
		}

		public void setI(Integer i) {
			this.i = i;
		}

		public Integer getJ() {
			return j;
		}

		public void setJ(Integer j) {
			this.j = j;
		}

		public Integer getA() {
			return a;
		}

		public void setA(Integer a) {
			this.a = a;
		}

		public Integer getB() {
			return b;
		}

		public void setB(Integer b) {
			this.b = b;
		}
		
		public int hashCode(){
			
			int hash = 31;
			
			return hash * Arrays.hashCode(this.keys);
			
		}
		
		public boolean isBinaryConstraint(){
			return this.binary;
		}
		
		
		public boolean equals(Constraint obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			
			final Constraint other = (Constraint)obj;
			return Arrays.equals(this.keys, other.keys);
			
		}
		
		
	}
	
	
	

}
