package ufrgs.maslab.abstractsimulator.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

//import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
//import ufrgs.maslab.abstractsimulator.disaster.Task;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
//import ufrgs.maslab.abstractsimulator.myagents.GSOMAgent;

//public class BranchAndBound<Var extends Variable<Val,?>, Val extends Value<Var,?>> {

public class BranchAndBound<Var extends Variable<Val>, Val extends Value> {
	
	public Map<Var, Val> partialAssignment = new HashMap<Var, Val>();
	
	public Double upperBound = Double.MAX_VALUE;
	
	public Double lowerBound = 0d;
	
	public Double alpha = 0d;
	
	public Queue<Var> x = new LinkedList<Var>();
	
	public ArrayList<Val> d = new ArrayList<Val>();
	
	public Map<Constraint<Var,Val>, Double> c = new HashMap<Constraint<Var,Val>, Double>();
	
	public BranchAndBound(Queue<Var> x, ArrayList<Val> d, Map<Constraint<Var, Val>, Double> constraints){
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
	
	public Double branchAndBound(Map<Var, Val>pa, Double lb, Double ub, Queue<Var> x, ArrayList<Val> d, Map<Constraint<Var,Val>, Double> c) throws SimulatorException
	{
		if(x.isEmpty()){
			this.partialAssignment.putAll(pa);
			return lb;
		}
		//this.lowerBound = lb;
		Var i = x.poll();
		for(Val v: ((ArrayList<Val>)i.getDomain()))
		{
			
			ArrayList<Val> dd = i.getDomain();
			Map<Constraint<Var,Val>,Double> cc = c;
			
			this.partialAssignment.putAll(pa);
			this.partialAssignment.put(i, v);
						
			//Constraint<Var,Val> ci = new Constraint<Var,Val>(i,v);
			
			Double nLb = this.lowerBound + v.getValue();
			
			this.lookAhead(i, v);
			
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
	
	public void lookAhead(Var i, Val a){
		
		//verify binary constraints
		ArrayList<Var> iColeagues = new ArrayList<Var>();
		for(Var j : iColeagues)
		{
			if(!j.equals(i))
			{
				for(Val b : ((ArrayList<Val>)j.getDomain()))
				{
					b.getId();
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
	
	public boolean localConsist(Double lb, Double ub, Queue<Var> x, ArrayList<Val> d, Map<Constraint<Var,Val>, Double> c)
	{
		return (ub > lb);
	}
	
	
	/**
	 *  internal class - constraints
	 *  
	 */
	public static class Constraint<Var, Val> {
		
		
		//variables
		private Var i;
		private Var j;
		//values
		private Val a;
		private Val b;
		private boolean binary = false;
		private Object[] keys = new Object[4];
		
		
		public Constraint(Var i, Val a)
		{
			this.i = i;
			this.a = a;
			keys[0] = i;
			keys[1] = a;
			this.binary = false;
		}
		
		public Constraint(Var i, Val a, Var j, Val b)
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

		public Var getI() {
			return i;
		}

		public void setI(Var i) {
			this.i = i;
		}

		public Var getJ() {
			return j;
		}

		public void setJ(Var j) {
			this.j = j;
		}

		public Val getA() {
			return a;
		}

		public void setA(Val a) {
			this.a = a;
		}

		public Val getB() {
			return b;
		}

		public void setB(Val b) {
			this.b = b;
		}
		
		public int hashCode(){
			
			int hash = 31;
			
			return hash * Arrays.hashCode(this.keys);
			
		}
		
		public boolean isBinaryConstraint(){
			return this.binary;
		}
		
		
		public boolean equals(Constraint<Var,Val> obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			
			final Constraint<Var,Val> other = (Constraint<Var,Val>)obj;
			return Arrays.equals(this.keys, other.keys);
			
		}
		
		
	}
	
	
	

}
