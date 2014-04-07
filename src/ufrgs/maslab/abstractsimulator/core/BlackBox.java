package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;
import ufrgs.maslab.abstractsimulator.disaster.DisasterEnvironment;
import ufrgs.maslab.abstractsimulator.util.Transmitter;

public class BlackBox<Env extends Environment<Val, Var>, Val extends Value, Var extends Variable> {
	
	/**
	 *  <ul>
	 *  <li>configuration file</li>
	 *  <li>contains all configuration parameters of the simulation</li>
	 *  </ul>
	 *  
	 */
	private String configFileName = "config.properties";
	
	/**
	 * environment variable
	 */
	private Env env;
	
	/**
	 *  list of simulations
	 */
	private ArrayList<DefaultSimulation> simulation = new ArrayList<DefaultSimulation>();

	/**
	 *  initial variables (agents)
	 */
	private int initialAgents = Transmitter.getIntConfigParameter(this.configFileName, "config.variables");
	
	/**
	 * initial values (tasks)
	 */
	private int initialTasks = Transmitter.getIntConfigParameter(this.configFileName, "config.values");
	
	/**
	 *  total timesteps
	 */
	private int timesteps = Transmitter.getIntConfigParameter(this.configFileName, "config.timesteps");
	
	
	/**
	 * constructor of the simulator without clusters of values
	 * @param values
	 * @param variables
	 */
	public BlackBox(Class<Env> env, Class<Var> var, Class<Val> val)
	{
		try {
			this.configure(env, var, val);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
		

	/**
	 * private function to create new variables and values to the simulation
	 * 
	 * @param t
	 * @param a
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void configure(Class<Env> env, Class<Var> var, Class<Val> val) throws InstantiationException, IllegalAccessException{
		
		this.env = env.newInstance();
		
		for(int x = 0; x < this.initialAgents; x++)
		{
			Var ag = var.newInstance();
			this.env.getVariables().add(ag);
		}
		
		for(int y = 0; y < this.initialTasks; y++)
		{
			this.env.getValues().add(val.newInstance());
		}		
	
	}

	/**
	 * return list of simulators
	 * @return
	 */
	public ArrayList<DefaultSimulation> getSimulation() {
		return simulation;
	}

	/**
	 * add simulation to the list
	 * @param simulation
	 */
	public void addSimulation(DefaultSimulation simulation) {
		this.getSimulation().add(simulation);
	}
	
	/**
	 * return the environment
	 * @return
	 */
	public Env getEnvironment() {
		return env;
	}

	

}
