package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.Entity;
import ufrgs.maslab.abstractsimulator.core.interfaces.Building;
import ufrgs.maslab.abstractsimulator.disaster.DisasterSimulation;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.gsom.learning.GSOMLearning;
import ufrgs.maslab.gsom.network.GrowingSelfOrganizingMap;
import ufrgs.maslab.gsom.norm.Template;
import ufrgs.maslab.gsom.norm.TemplateNormalizer;
import ufrgs.maslab.gsom.util.visualization.GrowingSelfOrganizingMapGraph2D;
import ufrgs.maslab.gsom.util.visualization.SelfOrganizingMapGraph3D;

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
		this.clusteringMain();
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
	
	private final String FILELOG = "log_experiment_1";
	
	private final String FILEGSOM = "gsom_info";
	
	private GSOMLearning gsom = null;
	
	private Template templateFireTask = null;
	
	/**
	 * main of the clustering phase
	 */
	public void clusteringMain(){
		WriteFile.getInstance().openFile(this.FILELOG);
		
		String header = "";
        header = "time;total tasks;generated tasks;solved tasks; unsolved tasks;agents";				
		WriteFile.getInstance().write(header,this.FILELOG);
		//DisasterSimulation<Task, GSOMAgent<Task>> sim = new DisasterSimulation(Task.class, GSOMAgent.class, 4);
		//int startTime = sim.getTime();
		//int endTime = sim.getTimesteps();
		//GSOMLearning gsom = gsomProcessing();
		
		Template template = new Template(new double[]{1d,2d,1d,50d,0d,5d,0d,0d,1d}, new double[]{4d, 220d, 30d, 230d, 2d, 116d, 1d, 1d, 4d});
		template.setAttributes(new String[]{"apartments per floor", "hp", "floors", "ground area", "matter", "success", "x", "y", "temperature"});
		this.templateFireTask = template;
		
		Map<Task,Double> map = new HashMap<Task,Double>();
		ValueComparator doubleComparator = new ValueComparator(map);
		SortedMap<Task,Double> sortedMap = new TreeMap<Task,Double>(doubleComparator);
		DataSet training = new DataSet(9,1);
		DataSet test = new DataSet(9,1);
		if(this.getTime() == 1){
			this.gsom = this.gsomTimestep(training, test);
		}
	}
	
	/**
	 * mounts data set and return the gsom
	 * @param training
	 * @param test
	 */
	public GSOMLearning gsomTimestep(DataSet training, DataSet test){
		//WriteFile.getInstance().openFile(this.FILELOG);
		//sim.printTasks(fileLog);
		//WriteFile.getInstance().openFile(this.FILEGSOM);
		//String header2 = "time;ID;Temperature;Matter;Floors;AreaGround;TotalArea;a_x;a_y;n_x;n_y";
		//WriteFile.getInstance().write(header2,this.FILEGSOM);
		
		Map<Task, DataSetRow> m = this.mountDataSet(this.getDomain());
		GSOMLearning gsom = null;
		if(this.getTime() == 1)
		{
			gsom = this.trainGSOM(m, training, test);
			
		}
		
		/*for(Task t : sim.getEnvironment().getTasks())
		{
			String task = startTime+";"+t.getId() + ";"+ t.getTemperature() + ";" + t.getMatter() + ";" + t.getFloors() + ";" + t.getAreaGround()
					+ ";" + t.getTotalArea() + ";" + t.getX() + ";" + t.getY() + ";" 
					+ gsom.getNeuralNetwork().getStructure().getWinner().getAxisPosition().get(0) + ";" 
					+ gsom.getNeuralNetwork().getStructure().getWinner().getAxisPosition().get(1);
			WriteFile.getInstance().write(task,fileGsom);
			
		}*/
		return gsom;
		
	}
	
	/**
	 * mounts initialization, training and testing datasets
	 * generates the GSOM
	 * 
	 * @param map
	 * @param training
	 * @param test
	 * @return
	 */
	private GSOMLearning trainGSOM(Map<Task, DataSetRow> map, DataSet training, DataSet test)
	{
		
		DataSet initSet = new DataSet(9,1);
		for(int i = 0; i < 10; i++)
		{
			DataSetRow d = new DataSetRow(FireBuildingTask.randomTask(), new double[]{0});
			initSet.addRow(d);
		}
		
		initSet.normalize(new TemplateNormalizer(this.templateFireTask));
	    
		ArrayList<Integer> dim = new ArrayList<Integer>();
		dim.add(2);
        dim.add(2);
        GrowingSelfOrganizingMap gsom = new GrowingSelfOrganizingMap(initSet,dim);
        GSOMLearning learning = new GSOMLearning(gsom);
        
        learning.getNeuralNetwork().getStructure().setInitializationDataSet(initSet);
        learning.initialize();
		
        /**
		 * monta um conjunto de treino
		 * com tarefas aleatorias
		 */
        for(int k = 0; k < 50; k++)
		{
			DataSetRow d = new DataSetRow(FireBuildingTask.randomTask(), new double[]{0});
			training.addRow(d);
		}
		training.normalize(new TemplateNormalizer(this.templateFireTask));
		
		/**
		 *  os dados das tarefas foram recebidos atraves
		 *  do metodo mountDataSet
		 *  esses dados sao inseridos em um test DataSet
		 */
		for(DataSetRow row : map.values())
		{
			test.addRow(row);
		}
		test.normalize(new TemplateNormalizer(this.templateFireTask));
		
		learning.getNeuralNetwork().getStructure().setInput(training);
		learning.getNeuralNetwork().getStructure().setTest(test);
		//growing phase
		learning.learn();
		//smoothing phase
		learning.testingMode();
		/**
		 * limpa a rede
		 * retira nós dummy
		 */
		//learning.getNeuralNetwork().getStructure().cleanNetwork();
		
		//learning.getNeuralNetwork().buildSkeleton();
		
        learning.getNeuralNetwork().creatingLabels(this.templateFireTask);
        //show2DMap(learning.getNeuralNetwork());
		show3DMap(learning.getNeuralNetwork());
		
		
		return learning;
	}
	
	/**
	 * receives a array list of tasks and returns an assembled dataset
	 * 
	 * @param tasks
	 * @return
	 */
	private Map<Task, DataSetRow> mountDataSet(ArrayList<Entity> tasks)
	{
		Map<Task, DataSetRow> m = new HashMap<Task, DataSetRow>();
		DataSet ds = new DataSet(9,1);
		for(Entity e: tasks)
		{
			if(e instanceof Task)
			{
				Task t = (Task)e;
				DataSetRow d = this.task(t);
				ds.addRow(d);
			}
		}
		ds.normalize(new TemplateNormalizer(this.templateFireTask));
		for(int i = 0; i < tasks.size(); i++)
		{
			if(tasks.get(i) instanceof Task)
				m.put((Task)tasks.get(i), ds.getRowAt(i));
		}
		
		return m;
		
	}
	
	/**
	 * receives a task and returns a datasetrow to the neuralnetwork
	 * 
	 * @param t Task which was received
	 * @return
	 */
	private DataSetRow task(Task t)
	{
		double[] d = new double[9];
		Arrays.fill(d,0.0);
		DataSetRow task = null;
		if(t instanceof FireBuildingTask)
		{
			d = this.fireBuildingTask((FireBuildingTask)t);
			task = new DataSetRow(d, new double[]{0});

		}
		
		return task;
	}
	
	/**
	 * transforms a FireBuildingTask into a double vector
	 * 
	 * @param t
	 * @return
	 */
	private double[] fireBuildingTask(FireBuildingTask t)
	{
		double d[] = new double[9];

		int apartmentsPerFloor = t.getApartmentsPerFloor();
		int buildingHP = t.getBuildingHP();
		int floors = t.getFloors();
		int groundArea = t.getGroundArea();
		int matter = t.getMatter().getValue();
		double success = t.getValue();
		double x = t.getX();
		double y = t.getY();
		int temperature = t.getTemperature().getValue();
		
		d[0] = apartmentsPerFloor;
		d[1] = buildingHP;
		d[2] = floors;
		d[3] = groundArea;
		d[4] = matter;
		d[5] = success;
		d[6] = x;
		d[7] = y;
		d[8] = temperature;
		
		return d;
		
	}
	
	public static void show2DMap(GrowingSelfOrganizingMap gsom)
	{
	    /** GSOM 2D */
		GrowingSelfOrganizingMapGraph2D and = new GrowingSelfOrganizingMapGraph2D(gsom.getStructure());
		        
		        
        JFrame frame = new JFrame();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().add(and);
    	
    	and.init();
    	and.start();
    	frame.pack();
    	frame.setVisible(true);
		    
	}
	
	public static void show3DMap(GrowingSelfOrganizingMap gsom)
	{
		SelfOrganizingMapGraph3D gsomMap = new SelfOrganizingMapGraph3D(gsom.getStructure());
        JFrame f = new JFrame();
		f.add(gsomMap);
		f.setSize(1024,768);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
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
