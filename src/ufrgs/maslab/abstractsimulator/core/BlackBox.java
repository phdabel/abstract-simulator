package ufrgs.maslab.abstractsimulator.core;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.core.simulators.DefaultSimulation;
import ufrgs.maslab.abstractsimulator.core.simulators.basic.CommunicationSimulation;
import ufrgs.maslab.abstractsimulator.core.simulators.basic.PerceptionSimulator;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;

public class BlackBox {
	
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
	private Environment<? extends Entity> env;
	
	/**
	 *  list of simulations
	 */
	private ArrayList<DefaultSimulation> simulation = new ArrayList<DefaultSimulation>();

	/**
	 *  initial variables (agents)
	 */
	//private int initialAgents = Transmitter.getIntConfigParameter(this.configFileName, "config.variables");
	
	/**
	 * initial values (tasks)
	 */
	//private int initialTasks = Transmitter.getIntConfigParameter(this.configFileName, "config.values");
	
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
	/*
	public BlackBox(Class<? extends Variable> var, Class<? extends Value> val)
	{
		try {
			this.configure(var, val);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * specific constructor
	 */
	public BlackBox()
	{
		this.newEnvironment();
		/**
		 * insert the perception simulation (1st simulator)
		 */
		this.addSimulation(PerceptionSimulator.class);
		/**
		 * insert the communication simulation (2nd simulator)
		 */
		this.addSimulation(CommunicationSimulation.class);
		
	}
	
	/**
	 * new environment
	 */
	public void newEnvironment(){
		if(this.env == null){
			this.env = new Environment<Entity>();
		}else
			Transmitter.message(this.messageFileName, "message.environment");
	}
	
	/**
	 * <ul>
	 *  <li>function to define the ammount of variables of the environment</li>
	 * </ul>
	 * 
	 * @param agentClass Class of the variable
	 * @param ammount Ammount of the variable
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public void addAgent(Class<? extends Variable> agentClass, Integer ammount) throws InstantiationException, IllegalAccessException{
		this.getEnvironment().registerVariable(agentClass, ammount);
		for(int i = 0; i < ammount; i++){
			Variable var = agentClass.newInstance();
			if(i == 0)
				var.header();
			var.logger();
			//HumanLogger.logHuman((Human)var);
			this.env.getVariables().add(var);
			
		}
	}
	
	public void addTask(Class<? extends Value> taskClass, Integer ammount) throws InstantiationException, IllegalAccessException{
		this.getEnvironment().registerValue(taskClass, ammount);
		for(int i = 0; i < ammount; i++)
		{
			Value val = taskClass.newInstance();
			if(i == 0)
				val.header();
			val.logger();
			//FireBuildingTaskLogger.logFireBuildingTask((FireBuildingTask)val);
			this.env.getValues().add(val);
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
	/*
	private void configure(Class<? extends Variable> var, Class<? extends Value> val) throws InstantiationException, IllegalAccessException{
		
		this.newEnvironment();
		for(int x = 0; x < this.initialAgents; x++)
		{
			
			ArrayList<Variable> arrVar = new ArrayList<Variable>();
			arrVar.add(var.newInstance());
			this.env.getVariables().add(arrVar.get(0));
		}
		
		for(int y = 0; y < this.initialTasks; y++)
		{
			this.env.getValues().add(val.newInstance());
		}		
	
	}*/
	
	/**
	 * starts the simulation until the total timesteps
	 * 
	 * @throws SimulatorException
	 */
	public void simulationStart() throws SimulatorException{
		if(this.getSimulation().isEmpty())
			throw new SimulatorException(Transmitter.getProperty(this.exceptionFileName, "exception.no.simulator"));
		while(this.time <= this.timesteps){
			this.time++;
			this.simulationStep();
		}

		WriteFile.getInstance().closeFile();
		Transmitter.message(this.configFileName, "final.message");
	}
	
	/**
	 * perform one simulation step
	 * @throws SimulatorException 
	 */
	private void simulationStep() throws SimulatorException{
		/**
		 * updates the agent view and runs commands for all agents
		 */
		for(Variable var : this.env.getVariables())
		{
			//runs the perception simulator to update the current agent view
			if(this.getSimulation().get(0) instanceof PerceptionSimulator)
				this.getSimulation().get(0).simulate(var, this.getEnvironment());
			
			//update age of the agent
			var.setTime(this.time);
			//runs the act command for each agent
			var.act(this.time);
		}
		
		if(this.getSimulation().get(1) instanceof CommunicationSimulation)
			this.getSimulation().get(1).simulate(this.env);
		
		/**
		 * perform the simulations except perception simulator
		 */
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
	public void addSimulation(Class<? extends DefaultSimulation> simulation) {
		try {
			this.getSimulation().add(simulation.newInstance());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * return the environment
	 * @return
	 */
	public Environment<? extends Entity> getEnvironment() {
		return env;
	}

	

}
