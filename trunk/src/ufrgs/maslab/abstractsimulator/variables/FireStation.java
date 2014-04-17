package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.interfaces.Building;
import ufrgs.maslab.abstractsimulator.disaster.DisasterSimulation;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.gsom.learning.GSOMLearning;
import ufrgs.maslab.gsom.network.GrowingSelfOrganizingMap;
import ufrgs.maslab.gsom.norm.LogTransformation;
import ufrgs.maslab.gsom.norm.Template;
import ufrgs.maslab.gsom.norm.TemplateNormalizer;

public class FireStation extends Agent implements Building{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877720837521565204L;

	public FireStation(){
		super();
	}
	
	public FireStation(Integer id)
	{
		super(id);
	}
	
	public void act(int time){
		System.out.println("Ammount of received messages: "+this.getRadioMessage().size());
		System.out.println("Fire Station "+this.getId());
		for(Message m : this.getRadioMessage())
		{
			FireBuildingTaskMessage t = (FireBuildingTaskMessage)m;
			if(!this.tasks.containsKey(t.getTaskId()))
				this.tasks.put(t.getTaskId(), t);
		}
		System.out.println("size of tasks "+this.tasks.size());
		for(FireBuildingTaskMessage t : this.tasks.values())
		{
			System.out.println("ID: "+t.getTaskId());
			System.out.println("Apartments/floor: "+t.getApartmentsPerFloor());
			System.out.println("HP: "+t.getBuildingHP());
			System.out.println("Floors: "+t.getFloors());
			System.out.println("Ground Area: "+t.getGroundArea());
			System.out.println("Matter: "+t.getMatter().getValue());
			System.out.println("Value: "+t.getTaskValue());
			System.out.println("X: "+t.getTaskX());
			System.out.println("Y: "+t.getTaskY());
			System.out.println("Temperature: "+t.getTemperature().getValue());
			System.out.println();
			//System.out.println(t.toString());
		}
	}
	
	@Override
	public void sendRadioMessage(Message msg) throws SimulatorException {
		if(msg.getType() != MessageType.RADIO)
			throw new SimulatorException(Transmitter.getProperty("exception.properties", "exception.not.radio.message"));
		this.getVoice().add(msg);
	}
	
	/**
	 * custom fields and methods
	 */
	
	private HashMap<Integer, FireBuildingTaskMessage> tasks = new HashMap<Integer, FireBuildingTaskMessage>();
	
	/**
	 * main of the clustering phase
	 */
	public void clusteringMain(){
		String fileLog = "log_experiment_1";
		WriteFile.getInstance().openFile(fileLog);
		
		String header = "";
        header = "time;total tasks;generated tasks;solved tasks; unsolved tasks;agents";				
		WriteFile.getInstance().write(header,fileLog);
		//DisasterSimulation<Task, GSOMAgent<Task>> sim = new DisasterSimulation(Task.class, GSOMAgent.class, 4);
		//int startTime = sim.getTime();
		//int endTime = sim.getTimesteps();
		//GSOMLearning gsom = gsomProcessing();
		
		
		Map<Task,Double> map = new HashMap<Task,Double>();
		ValueComparator doubleComparator = new ValueComparator(map);
		SortedMap<Task,Double> sortedMap = new TreeMap<Task,Double>(doubleComparator);
		DataSet training = new DataSet(9,1);
		DataSet test = new DataSet(9,1);
	}
	
	public void gsomTimestep(){
		WriteFile.getInstance().openFile(fileLog);
		//sim.printTasks(fileLog);
		String fileGsom = "gsom_info";
		WriteFile.getInstance().openFile(fileGsom);
		String header2 = "time;ID;Temperature;Matter;Floors;AreaGround;TotalArea;a_x;a_y;n_x;n_y";
		WriteFile.getInstance().write(header2,fileGsom);
		
		Map<Task, DataSetRow> m = mountDataSet(this.getDomain());
		
	}
	
	public static Map<Task, DataSetRow> mountDataSet(ArrayList<Task> tasks, Template template)
	{
		Map<Task, DataSetRow> m = new HashMap<Task, DataSetRow>();
		DataSet ds = new DataSet(8,1);
		for(Task t : tasks)
		{
			DataSetRow d = task(t);
			ds.addRow(d);
		}
		ds.normalize(new TemplateNormalizer(template));
		for(int i = 0; i < tasks.size(); i++)
		{
			m.put(tasks.get(i), ds.getRowAt(i));
		}
		
		return m;
		
	}
		
	/**
	 * internal class to comparate values and to provide ordering of tasks
	 * @author abel
	 *
	 */
	public static class ValueComparator implements Comparator<Task> {

	    Map<Task, Double> base;
	    public ValueComparator(Map<Task, Double> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with equals.    
	    public int compare(Task a, Task b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        } // returning 0 would merge keys
	    }
	}
	

}
