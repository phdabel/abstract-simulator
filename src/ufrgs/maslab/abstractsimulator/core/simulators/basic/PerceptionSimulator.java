package ufrgs.maslab.abstractsimulator.core.simulators.basic;

import java.util.ArrayList;
import java.util.Arrays;

import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.core.simulators.DefaultSimulation;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.Utilities;

public class PerceptionSimulator extends DefaultSimulation {

	private String configFile = "config.properties";
	
	@SuppressWarnings("unchecked")
	@Override
	public void simulate(Entity env) throws SimulatorException {
		if(env instanceof Environment)
			this.sense((Environment<Entity>)env);
		
		
	}
	
	/**
	 * function to analyse the environment and to define the domain of each agent
	 * based on the radius parameter.
	 * @param env
	 * @throws SimulatorException
	 */
	private void sense(Environment<Entity> env) throws SimulatorException{
		for(Variable var : env.getVariables())
		{
			ArrayList<Double> e1 = new ArrayList<Double>(
				    Arrays.asList(var.getX(), var.getY()));
			
			ArrayList<Integer> domain = new ArrayList<Integer>();
			Double radius = Transmitter.getDoubleConfigParameter(this.configFile, "agent.radius");
			for(Value val : env.getValues())
			{
				ArrayList<Double> e2 = new ArrayList<Double>(
					    Arrays.asList(val.getX(), val.getY()));
				
				if(Utilities.euclideanDistance(e1, e2) <= radius)
					domain.add(val.getId());
				
			}
			env.findVariableByID(var.getId(), var.getClass()).getDomain().clear();
			env.findVariableByID(var.getId(), var.getClass()).setDomain(domain);
		}
		
	}
	

}