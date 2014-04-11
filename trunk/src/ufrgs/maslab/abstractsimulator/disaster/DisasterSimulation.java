package ufrgs.maslab.abstractsimulator.disaster;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.Temperature;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.abstractsimulator.variables.Agent;

public class DisasterSimulation<T extends Task, A extends Agent<T>>{
	
	private DisasterEnvironment<T,A> env = new DisasterEnvironment<T, A>();
	
	private int initialAgents = 100;
	
	private int initialTasks = 100;
	
	private int timesteps = 10;
	
	private int generatedTasks = 0;
	
	private int time = 1;
	
	//probabilistic division of clusters are based on threshold
	private double clusterThreshold;

	/**
	 * k is the number of cluster of tasks according to x,y position
	 * @param t
	 * @param class1
	 * @param k
	 */
	public DisasterSimulation(Class<T> t, Class<A> a, int k)
	{
		this.clusterThreshold = k / this.initialTasks;
		try {
			this.configure(t,a, k);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public DisasterSimulation(Class<T> t, Class<A> a)
	{
		try {
			this.configure(t,a, 0);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
		
	private void configure(Class<T> t, Class<A> a, int k) throws InstantiationException, IllegalAccessException{
		Random r = new Random();
		for(int x = 0; x < this.initialAgents; x++)
		{
			A ag = a.newInstance();
			ag.setY(r.nextDouble());
			ag.setX(r.nextDouble());
			this.env.getAgents().add(ag);
		}
		
		if( k == 0){
			for(int y = 0; y < this.initialTasks; y++)
			{
				this.env.getTasks().add(t.newInstance());
			}
		}else{
			int ktmp;
			ktmp = 0;
			int ct = 0;
			Random dices = new Random();
			T taskTmp = null;
			for(int y = 0; y < this.initialTasks; y++)
			{
				if(dices.nextDouble() < this.clusterThreshold){
					ktmp++;
					ct = 0;
					taskTmp = t.newInstance();
					this.env.getTasks().add(taskTmp);
				}
				else if(ktmp == k || (ct < this.initialTasks/k && y != 0))
				{
					try {
						this.env.getTasks().add(t.getConstructor(taskTmp.getClass()).newInstance(taskTmp));
						ct++;
						
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(ktmp < k && ct == this.initialTasks/k || dices.nextDouble() < this.clusterThreshold){
					ktmp++;
					ct = 0;
					taskTmp = t.newInstance();
					this.env.getTasks().add(taskTmp);
				}else if(y == 0){
					ktmp++;
					ct = 0;
					taskTmp = t.newInstance();
					this.env.getTasks().add(taskTmp);
				}
			}
		}
		
	}
	
	public DisasterEnvironment<T,A> getEnvironment()
	{
		return this.env;
	}
	
	public void simulationStart(){
		while(this.time <= this.timesteps)
		{
			//this.printTasks();
			this.simulationStep();
		}
		
	}
	
	public void printTasks(String filename){
		String string = "";
		
		string = this.getTime()+";"+this.getEnvironment().getTasks().size()+";"+this.generatedTasks+";"+
		this.getEnvironment().getSolvedTasks().size()+";"+this.getEnvironment().getRemovedTasks().size()
		+";"+this.getEnvironment().getAgents().size();
		
		
		WriteFile.getInstance().write(string,filename);
		
		this.getEnvironment().getSolvedTasks().clear();
		this.getEnvironment().getRemovedTasks().clear();
		/*
		System.out.println("Time: "+this.getTime());
		System.out.println("Tarefas: "+this.getEnvironment().getTasks().size());
		System.out.println("Tarefas concluídas: "+this.getEnvironment().getSolvedTasks().size());
		System.out.println("Tarefas não resolvidas: "+this.getEnvironment().getRemovedTasks().size());
		System.out.println();*/
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void simulationStep(){
		/**
		 * execute actions of all agents
		 */
		this.generatedTasks = 0;
		//clear agents from tasks
		for(T task : this.env.getTasks())
		{
			task.getVariables().clear();
		}
		
		
		for(A agent : this.env.getAgents())
		{
			//act command
			agent.act(this.time);
			
			//show current time to agent
			agent.setTime(this.time);
			
			//assign agent's new task
			int taskId = -1;
			if(agent.getValue() != null){
				//System.out.println("time "+time+" agent "+agent.getId()+" task "+agent.getValue().getId());
				if(this.env.getTasks().contains(agent.getValue())){
						taskId = this.env.getTasks().indexOf(agent.getValue());
						if(taskId != -1 && !this.env.getTasks().get(taskId).getVariables().contains(agent))
							this.env.getTasks().get(taskId).getVariables().add((Variable)agent);
				}else{
					agent.assign(null);
				}
			}
		}
		
		/**
		 *  simulator's behavior
		 */
		for(int t = 0; t < this.env.getTasks().size(); t++)
		{
			T task = this.env.getTasks().get(t);
			
			int taskAgents = task.getVariables().size();
			
			if(taskAgents == 0)
			{
				task.decreaseTemperature = 0;
			}else if(task.getTemperature() < 4 || (task.getTemperature() == 4 && taskAgents >= 2)){
				task.decreaseTemperature++;
			}
			
			if(task.getTemperature() == task.decreaseTemperature)
			{
				int newTemp = task.getTemperature();
				newTemp--;
				if(newTemp <= 0){
					this.env.getTasks().get(t).setIsBurning(false);
					this.env.getSolvedTasks().add(this.env.getTasks().get(t));
					this.env.getTasks().remove(task);
				}else{
					switch(newTemp)
					{
						case 3:
							task.increaseTemperature = 6;
							break;
						case 2:
							task.increaseTemperature = 4;
							break;
						case 1:
							task.increaseTemperature = 2;
							break;
					}
					task.setTemperature(Temperature.values()[newTemp]);
					task.decreaseTemperature = 0;
				}
			}else if(task.getTemperature() >= 4 && taskAgents < 2)
			{
				this.propagate(task);
			}else
			{
				this.increaseFire(task);
			}
			
		}
		
		
		/**
		 * overall system state
		 */
		/*
		for(A agent : this.env.getAgents())
		{
			agent.sense(this.time);
		}*/
		this.time();
	}
	
	private boolean verifyDeadline(T t){
		if(t.getTemperature() == 4 
				&& (t.getIncreaseTemperature() - t.getDecreaseTemperature()) >= ((t.getModifier() * t.getTemperature())+5) )
		{
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	private void propagate(T t) {
		double alpha = 0.0;
		alpha = Matter.getProbability(t.getMatter());
		if(t.rollDices() <= alpha)
		{
			int taskId = this.env.getTasks().indexOf(t);
			this.env.getTasks().get(taskId).increaseTemperature();
			this.env.getTasks().add((T) new Task(t));
			this.generatedTasks++;
			if(this.verifyDeadline(t))
			{
				if(this.env.getTasks().contains(t))
				{
					this.env.getRemovedTasks().add((T) t);
					this.env.getTasks().remove(t);
				}
			}
		}
	}
	
	private void increaseFire(T t)
	{
		double alpha = 0.0;
		alpha = Matter.getProbability(t.getMatter());
		if(t.rollDices() <= alpha)
		{
			int taskId = this.env.getTasks().indexOf(t);
			this.env.getTasks().get(taskId).increaseTemperature();
			/**
			 * deadline
			 * remove task from task list
			 * inserts task into the removed task list
			 */
			T currentTask = this.env.getTasks().get(taskId);
			
			if(this.verifyDeadline(currentTask))
			{
				if(this.env.getTasks().contains(currentTask))
				{
					this.env.getRemovedTasks().add((T) currentTask);
					this.env.getTasks().remove(currentTask);
				}
			}
		}
	}

	public int getInitialAgents() {
		return initialAgents;
	}


	public void setInitialAgents(int initialAgents) throws SimulatorException {
		if(this.time == 1){
			this.initialAgents = initialAgents;
		}else{
			throw new SimulatorException("Impossible to define initial number of agents in runtime.");
		}
	}


	public int getInitialTasks() {
		return initialTasks;
	}


	public void setInitialTasks(int initialTasks) throws SimulatorException {
		if(this.time == 1)
		{
			this.initialTasks = initialTasks;
		}else{
			throw new SimulatorException("Impossible to define initial number of tasks in runtime.");
		}
	}

	public int getTimesteps() {
		return timesteps;
	}


	public void setTimesteps(int timesteps) throws SimulatorException {
		if(this.time == 1){
			this.timesteps = timesteps;
		}else{
			throw new SimulatorException("Total timesteps must be defined before simulation start.");
		}
	}

	public int getTime() {
		return time;
	}

	protected void time() {
		this.time++;
	}

	public int getGeneratedTasks() {
		return generatedTasks;
	}

	public void setGeneratedTasks(int generatedTasks) {
		this.generatedTasks = generatedTasks;
	}
	

}
