package ufrgs.maslab.abstractsimulator.core;

import ufrgs.maslab.abstractsimulator.util.Transmitter;


public class DefaultSimulation<Var extends Variable<Val>, Val extends Value> {
	
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
	private Environment<Val,Var> env = new Environment<Val, Var>();
	
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
	private int generatedTasks = 0;
	
	private int time = 1;
	
	//probabilistic division of clusters are based on threshold
	private double clusterThreshold;
	*/
	
	/**
	 * k is the number of cluster of tasks according to x,y position
	 * @param t
	 * @param class1
	 * @param k
	 */
	public Simulation(Class<T> t, Class<A> a, int k)
	{
		//this.clusterThreshold = k / this.initialTasks;
		try {
			this.configure(t,a, k);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	

}
