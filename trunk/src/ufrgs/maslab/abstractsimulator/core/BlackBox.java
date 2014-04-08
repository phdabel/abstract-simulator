package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.simulators.DefaultSimulation;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;

public class BlackBox<Val extends Value ,Variable> {
	
	/**
	 *  <ul>
	 *  <li>configuration file</li>
	 *  <li>contains all configuration parameters of the simulation</li>
	 *  </ul>
	 *  
	 */
	private String configFileName = "config.properties";
	
	private String messageFileName = "message.properties";
	
	private String exceptionFileName = "exception.properties";
	/**
	 * environment variable
	 */
	private Environment<Val, Variable> env;
	
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
	 * current time
	 */
	private int time = 0; 
	
	/**
	 * constructor of the simulator without clusters of values
	 * @param values
	 * @param variables
	 */
	public BlackBox(Class<Variable> var, Class<Val> val)
	{
		try {
			this.configure(var, val);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * specific constructor
	 */
	public BlackBox()
	{
		
	}
	
	/**
	 * new environment
	 */
	public void newEnvironment(){
		if(this.env == null){
			this.env = new Environment<Val, Variable>();
		}else
			Transmitter.message(this.messageFileName, "message.environment");
	}
	
	public void addAgent(Class<Variable> agentClass) throws InstantiationException, IllegalAccessException{
		Variable ag = agentClass.newInstance();
		this.env.getVariables().add(ag);
	}

	/**
	 * private function to create new variables and values to the simulation
	 * 
	 * @param t
	 * @param a
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void configure(Class<Variable> var, Class<Val> val) throws InstantiationException, IllegalAccessException{
		
		this.newEnvironment();
		for(int x = 0; x < this.initialAgents; x++)
		{
			Variable ag = var.newInstance();
			this.env.getVariables().add(ag);
		}
		
		for(int y = 0; y < this.initialTasks; y++)
		{
			this.env.getValues().add(val.newInstance());
		}		
	
	}
	
	/**
	 * starts the simulation until the total timesteps
	 * 
	 * @throws SimulatorException
	 */
	public void simulationStart() throws SimulatorException{
		if(this.getSimulation().isEmpty())
			throw new SimulatorException(Transmitter.getProperty(this.exceptionFileName, "exception.no.simulator"));
		while(this.time <= this.timesteps)
			this.simulationStep();

		WriteFile.getInstance().closeFile();
		Transmitter.message(this.configFileName, "final.message");
	}
	
	/**
	 * perform one simulation step
	 */
	private void simulationStep(){
		
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
	public Environment<Val, Variable> getEnvironment() {
		return env;
	}

	

}
