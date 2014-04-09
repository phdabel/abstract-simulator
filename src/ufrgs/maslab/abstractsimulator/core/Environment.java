package ufrgs.maslab.abstractsimulator.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;

public class Environment<E extends Entity> extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8L;
	
	/**
	 * <ul>
	 * <li>environment log filename</li>
	 * </ul>
	 * 
	 */
	private String logFile = null;
	
	/**
	 * 
	 * <ul>
	 * <li>value class</li>
	 * </ul>
	 * 
	 */
	private Class<? extends Value> valClass = null;
	
	/**
	 * <ul>
	 * <li>variable class</li>
	 * </ul>
	 * 
	 */
	private Class<? extends Variable> varClass = null;
	
	/**
	 * <ul>
	 * <li>Set of variables of the environment</li>
	 * </ul>
	 * 
	 */
	private Collection<? extends Variable> variableSet = new ArrayList<>();
	
	/**
	 * <ul>
	 * <li>Set of values of the environment</li>
	 * </ul>
	 */
	private Collection<? extends Value> valueSet = new ArrayList<>();
	
	/**
	 *  <ul>
	 *  <li>Map of allocation of variables and values in a given turn<li>
	 *  <li>Map Structure <Value Id, ArrayList<Variable Id>></li>
	 *  <ul>
	 */
	private Map<Integer, ArrayList<Integer>> allocationSet = new HashMap<Integer, ArrayList<Integer>>();
	
	/**
	 * <ul>
	 * <li>Id of the solved values</li>
	 * </ul>
	 */
	private ArrayList<Integer> solvedValues = new ArrayList<Integer>();
	
	/**
	 * <ul>
	 * <li>Id of unsolved values</li>
	 * <li>Values are unsolved if they reach the deadline</li>
	 * </ul>
	 */
	private ArrayList<Integer> removedValues = new ArrayList<Integer>();
		
	/**
	 *  overrides entity constructor
	 */
	public Environment()
	{
		super();
		this.saveHeader();
		
	}
	
	/**
	 * overrides entity constructor
	 * @param id
	 */
	public Environment(Integer id)
	{
		super(id);
		this.saveHeader();
	}
	
	/**
	 * <ul>
	 * <li>returns the set of variables of the environment</li>
	 * </ul>
	 * 
	 * @return ArrayList<var extends Variable>
	 */
	public List<?> getVariables(){
		
		if(this.getVarClass() == null)
			if(!this.variableSet.isEmpty())
				this.setVarClass(this.variableSet.iterator().next().getClass());
		return Arrays.asList(this.variableSet.iterator());
	}
	
	/**
	 * <ul>
	 * <li>returns the set of values of the environment</li>
	 * </ul>
	 * 
	 * @return ArrayList<val extends Value>
	 */
	public List<?> getValues(){
		if(this.getValClass() == null)
			if(!this.valueSet.isEmpty())
				this.setValClass(this.valueSet.iterator().next().getClass());
		return Arrays.asList(this.valueSet.iterator());
	}
	
	/**
	 * <ul>
	 * <li>returns the list of ids of values that was solved by agents</li>
	 * </ul>
	 * 
	 * @return ArrayList<Integer>
	 */
	protected ArrayList<Integer> getSolvedValues(){
		return this.solvedValues;
	}
	
	/**
	 * <ul>
	 * <li>returns the list of ids of values that was unsolved</li>
	 * </ul>
	 * 
	 * @return ArrayList<Integer>
	 */
	protected ArrayList<Integer> getRemovedValues(){
		return this.removedValues;
	}

	/**
	 * <ul>
	 * <li>returns the map with ids of variables assigned to ids of values</li>
	 * <li>value id is keys</li>
	 * <li>list of variables id are the values of the map</li>
	 * </ul>
	 * 
	 * @return Map<Integer, Array<Integer>>
	 */
	public Map<Integer, ArrayList<Integer>> getAllocation() {
		return allocationSet;
	}

	/**
	 * <ul>
	 * <li>sets a map of values ids and variables ids</li>
	 * </ul>
	 * 
	 * @param allocation Map<Integer, ArrayList<Integer>>
	 */
	protected void setAllocation(Map<Integer, ArrayList<Integer>> allocation) {
		this.allocationSet = allocation;
	}
	
	/**
	 * creates header for environment log file
	 */
	public void saveHeader()
	{
		this.logFile = Transmitter.getProperty("files.properties", "environment");
		WriteFile.getInstance().openFile(this.logFile);
		String header = "time;task_id;agents_id";
		WriteFile.getInstance().write(header,this.logFile);
	}
	
	/**
	 * saves the current step of the environment in the log file
	 * @param time
	 */
	public void saveCurrentStep(Integer time)
	{
		WriteFile.getInstance().openFile(this.logFile);
		String step = null;
		if(!this.getAllocation().isEmpty()){
			for(Integer val : this.getAllocation().keySet())
			{
				step = time.toString()+";";
				step += val.toString()+";";
				for(Integer var : this.getAllocation().get(val))
				{
					step += var.toString()+";";
				}
				step += "total;"+this.getAllocation().get(val).size()+";";
				WriteFile.getInstance().write(step, this.logFile);
				WriteFile.getInstance().nLine(this.logFile);
			}
		}else{
			step = time.toString()+";";
			WriteFile.getInstance().write(step, this.logFile);
			WriteFile.getInstance().nLine(this.logFile);
		}
	}
	
	/**
	 * <ul>
	 * <li>saves current allocation to the log file</li>
	 * <li>clear current allocation</li>
	 * <li>assigns agents to the new allocation</li>
	 * <li>O(2 agents) complexity</li>
	 * </ul>
	 * @param time
	 */
	public void allocateVariables(int time){
		this.saveCurrentStep(time);
		this.getAllocation().clear();
		Iterator<?> var = this.getVariables().iterator();
		while(var.hasNext())
		{
			this.allocateVariable(((Agent)var).getValue(), ((Agent)var).getId());
		}
	}
	
	/**
	 * <ul>
	 * <li>assign one agent to one value</li>
	 * <li>if value wasn't mapped, creates the value mapping and assign variable</li>
	 * </ul>
	 * 
	 * @param value id which the agent will be assigned to
	 * @param variable id which will be assigned to some value
	 */
	private void allocateVariable(Integer value, Integer variable)
	{
		if(this.getAllocation().containsKey(value)){
			if(!this.getAllocation().get(value).contains(variable)){
				this.getAllocation().get(value).add(variable);
			}else{
				Transmitter.message("message.properties","message.already.assigned.variable");
			}
		}else{
			this.getAllocation().put(value, new ArrayList<Integer>());
			this.getAllocation().get(value).add(variable);
		}
	}
	
	/**
	 * <ul>
	 * <li>deallocate variable</li>
	 * </ul>
	 * @param value
	 * @param variable
	 */
	public void deallocateVariable(Integer value, Integer variable)
	{
		if(this.getAllocation().containsKey(value)){
			this.getAllocation().get(value).remove(variable);
		}else{
			Transmitter.message("message.properties", "message.non.assigned.variable");
		}
		
	}
	
	/**
	 * <ul>
	 * <li></li>
	 * </ul>
	 * @param variable
	 * @param value
	 * @return Boolean
	 */
	/**
	private Boolean verifyAllocationPersistence(Integer variable, Integer value){
		
		//id da variavel
		Integer idxVar = this.getVariables().indexOf(this.findVariableByID(variable));
		var agent = this.getVariables().get(idxVar);
		//old value of the variable
		Integer oldVal = null;
		if(agent.isAllocated())
		{
			oldVal = agent.getValue();
		}
		
		if(this.getAllocation().containsKey(value))
			if(this.getAllocation().get(value).contains(variable))
				return false;
		
		
		
		return true;
	}*/
	
	/**
	 * <ul>
	 * <li>returns the object Value specified by the parameter idx</li>
	 * </ul>
	 * @param idx
	 * @return
	 */
	public Value findValueByID(int idx){
		Value v = null;
		try {
			v = this.valClass.getConstructor(Integer.class).newInstance(idx);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		int idxVal = this.getValues().indexOf(v); 
		v = null;
		return (Value)this.getValues().get(idxVal);
	}
	
	/**
	 * <ul>
	 * <li>returns the object variable specified by the parameters idx</li>
	 * </ul>
	 * @param idx
	 * @return
	 */
	public Variable findVariableByID(int idx){
		
		Variable v = null;
		try {
			v = this.getVarClass().getConstructor(Integer.class).newInstance(idx);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		int idxVar = this.getVariables().indexOf(v);
		v = null;
		return (Variable)this.getVariables().get(idxVar);
	}
	
	
	
	protected Class<? extends Value> getValClass() {
		return valClass;
	}

	protected void setValClass(Class<? extends Value> valClass) {
		this.valClass = valClass;
	}

	protected Class<? extends Variable> getVarClass() {
		return varClass;
	}

	protected void setVarClass(Class<? extends Variable> varClass) {
		this.varClass = varClass;
	}


}
