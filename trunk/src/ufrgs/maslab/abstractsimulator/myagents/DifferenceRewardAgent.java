package ufrgs.maslab.abstractsimulator.myagents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class DifferenceRewardAgent<T extends Task> extends Agent{
	
	
	/**
	 *  ANT Reward Agent piece of code
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 12L;

	/**
	 *  V_k
	 */
	public Map<T, Double> actionUtility = new HashMap<T, Double>();
	
	/**
	 *  P_k
	 */
	public Map<T, Double> actionProbability = new HashMap<T, Double>();
	
	public Double tau = 0.5;
	
	public Double alpha = 0.9;
	
	public Double neededResources = 4.0;
	
	/**
	 *  similar to think of RCR
	 */
	public void act(int time)
	{
		if(time == 1){
			Random r = new Random();
			if(!this.getDomain().isEmpty()){
				for(T t : this.getDomain())
				{
					computeUtility(t, 0.0);
					computeProbability(t);
				}
				this.assign(this.getDomain().get(r.nextInt(this.getDomain().size())));
			}
		}else{
			if(this.getDomain().size() > 0){
				T t = this.getAction();
				//System.out.println("ag "+this.getId()+" chosen task "+t.getId()+" temperature "+t.getTemperature()+" matter "+t.getMatter()+" floors "+t.getFloors()+" ground "+t.getAreaGround()+" inc "+t.increaseTemperature+" dec "+t.decreaseTemperature+" mod "+t.modifier);
				this.assign(t);
			}
		}
	}
	
	/**
	 *  sense environment modification after actions of all agents
	 */
	@Override
	public void sense(int time){
		
		Double r = getDifferenceReward();
		computeUtility(getValue(), r);
			
	}
	
	/**
	 * returns utility of an action
	 * 
	 * @param Task t
	 * @return Double utility
	 */
	public Double actionUtility(T t)
	{
		Double v = 0.0;
		if(this.getDomain().contains(t)){
			if(this.actionUtility.containsKey(t))
			{
				return this.actionUtility.get(t);
			}else{
				this.actionUtility.put(t, v);
			}
		}else{
			if(this.actionUtility.containsKey(t))
			{
				this.actionUtility.remove(t);
			}
		}
		return v;
	}
	
	/**
	 * computes utility of a given task
	 * @param Task t task assigned
	 * @param Double r reward received by assign task t
	 */
	public void computeUtility(T t, Double r)
	{
		Double vk = 0.0;
		if(this.actionUtility.containsKey(t))
		{
			vk = (1 - this.alpha) * this.actionUtility.get(t) + this.alpha * r;
			this.actionUtility.put(t, vk);
		}else{
			this.actionUtility.put(t, r);
		}
		
	}
	
	public Double computeProbability(T task)
	{
		Double p = 0.0;
		Double n = Math.exp(this.actionUtility.get(task) / this.tau);
		Double d = 0.0;
		for(T a : this.actionUtility.keySet())
		{
			if(task != null && a != null && !a.equals(task))
			{
				d += Math.exp(this.actionUtility.get(a) / this.tau);
			}
		}
		if(d == 0.0)
		{
			d = 1.0;
		}
		p = n/d;
		return p;
	}
	
	/**
	 * updates probabilities and returns best action
	 * @return Task
	 */
	public T getAction()
	{
		T chosenTask = null;
		/**
		 *  empty set of probabilities
		 */
		if(this.actionProbability.isEmpty())
		{
			for(T t : this.getDomain())
			{	
				this.actionUtility(t);
				this.actionProbability.put(t, this.computeProbability(t));
			}
		}
		/**
		 *  environment tasks set different of action probability set
		 */
		else if( (this.getDomain().size() != this.actionProbability.size()) && !this.actionProbability.isEmpty())
		{
			ArrayList<T> removeList = new ArrayList<T>();
			
			for(T t : this.actionProbability.keySet())
			{
				if(this.getDomain().contains(t))
				{
					this.actionProbability.put(t, this.computeProbability(t));
				}else{
					removeList.add(t);
				}
			}
			for(T t : removeList)
			{
				this.actionUtility.remove(t);
				this.actionProbability.remove(t);
			}
		}
		/**
		 * return max probability action
		 */
		Double maxValue = Double.MIN_VALUE;
		for(T ct : this.actionProbability.keySet())
		{
			/*System.out.print("ag "+this.getId());
			System.out.print(" task "+ct.getId());
			System.out.print(" utility "+this.actionUtility.get(ct));
			System.out.println(" prob "+this.actionProbability.get(ct));*/
			if(this.actionProbability.get(ct) >= maxValue)
			{
				maxValue = this.actionProbability.get(ct);
				chosenTask = ct;
			}
		}
		return chosenTask;
		
	}
	
	
	/**
	 *  G(z)
	 *  @return Double globalReward
	 */
	public Double getGlobalReward()
	{
		Double reward = 0.0;
		if(this.neededResources == 0)
		{
			this.neededResources = 1.0;
		}
		if(getValue() != null){
			//reward = getValue().getAgents().size() * Math.exp( (- getValue().getAgents().size()) / this.neededResources);
		}
		return reward;
	}
	
	/**
	 * G(z - i)
	 * @return Double globalReward without agent
	 */
	public Double getGlobalRewardWithoutMe()
	{
		Double reward = 0.0;
		if(this.neededResources == 0)
		{
			this.neededResources = 1.0;
		}
		if(getValue() != null){
			//reward = (getValue().getAgents().size() - 1) * Math.exp( (-(getValue().getAgents().size()-1))/ this.neededResources);
		}		
		return reward;
	}
	
	/**
	 *  D(z) = G(z) - G(z - i)
	 *  
	 * @return Double differenceReward
	 */
	public Double getDifferenceReward()
	{
		return this.getGlobalReward() - this.getGlobalRewardWithoutMe();
	}


}
