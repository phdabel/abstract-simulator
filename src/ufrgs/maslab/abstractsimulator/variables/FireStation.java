package ufrgs.maslab.abstractsimulator.variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import ufrgs.maslab.abstractsimulator.algorithms.Algorithm;
import ufrgs.maslab.abstractsimulator.algorithms.KMeans;
import ufrgs.maslab.abstractsimulator.algorithms.model.DataSet;
import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.abstractsimulator.constants.MessageType;
import ufrgs.maslab.abstractsimulator.core.interfaces.Building;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.log.FireBuildingTaskLogger;
import ufrgs.maslab.abstractsimulator.mailbox.message.FireBuildingTaskMessage;
import ufrgs.maslab.abstractsimulator.mailbox.message.Message;
import ufrgs.maslab.abstractsimulator.normalization.Template;
import ufrgs.maslab.abstractsimulator.normalization.TemplateNormalizer;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.values.Task;

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
		
		//template used to normalize the instances
		Template template = new Template(new double[]{0d,0d,5d}, new double[]{1000d, 1000d, 116d});
		template.setAttributes(new String[]{"x", "y", "difficulty"});
		this.templateFireTask = template;
		//
		
		//get radio messages from the other agents
		for(Message m : this.getRadioMessage())
		{
			FireBuildingTaskMessage t = (FireBuildingTaskMessage)m;
			if(!this.tasks.containsKey(t.getTaskId()))
				this.tasks.put(t.getTaskId(), t);
			
			//mount dataset (field) of tasks (points)
			this.taskToPoint(t, time);		
		}
		
		//normalization of the input data
		if(this.field.points.size() != 0)
			this.field.normalize(new TemplateNormalizer(this.templateFireTask));
		//
		
		/**
		 * in the first timestep fire station receives the input data by the messages
		 * from the fire fighters
		 * 
		 * then it mounts the dataset of the instances and from the second timestep it starts
		 * to find the clusters
		 */
		if(time > 1){
			this.clusteringMain(time);
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
		
	private final String FILEKMEANS = "kmeans_info";
	
	private Template templateFireTask = null;
	
	private DataSet field = new DataSet();
	
	Algorithm kmeans = new KMeans();
	
	/**
	 * main of the clustering phase
	 */
	public void clusteringMain(int time){
		
		WriteFile.getInstance().openFile(this.FILEKMEANS);
		
        if(time == 2){
        	String header = "";
            header = "x;y;difficulty";
        	WriteFile.getInstance().write(header,this.FILEKMEANS);
        	ArrayList<Double> weights = new ArrayList<Double>(Arrays.asList(0.15, 0.15, 0.7));
        	((KMeans)kmeans).weight = weights;
        	kmeans.setField(this.field);
			kmeans.run();
			

			//show2DMap(this.gsom.getNeuralNetwork());
			for(int p = 0; p < ((KMeans)this.kmeans).bestCentroids.size(); p++)
			{
				switch(p)
            	{
            		case 0:
            			System.out.println("Centroid Black");
            			break;
            		case 1:
            			System.out.println("Centroid Green");
            			break;
            		case 2:
            			System.out.println("Centroid Blue");
            			break;
            		case 3:
            			System.out.println("Centroid Red");
            			break;
            		case 4:
            			System.out.println("Centroid Yellow");
            			break;
            		case 5:
            			System.out.println("Centroid Gray");
            			break;
            		case 6:
            			System.out.println("Centroid White");
            			break;
            		case 7:
            			System.out.println("Centroid Magenta");
            			break;
            		default:
            			System.out.println("Centroid Cyan");
            			break;
            			
            	}
				for(int k = 0; k < this.templateFireTask.getMaxInput().length; k++)
				{
					System.out.println(this.templateFireTask.getAttributes()[k]+" --> "+(this.templateFireTask.getMaxInput()[k]*((KMeans)this.kmeans).bestCentroids.get(p).getAttributes().get(k)));
				}
				System.out.println();
			}
			//show3DMap(this.gsom.getNeuralNetwork());
        	
        }
		
	}
	
	/**
	 * insert point into the field
	 * @param p
	 * @param n
	 */
	public void taskToPoint(FireBuildingTaskMessage t, int time)
	{
		ArrayList<Double> attr = new ArrayList<Double>();
		attr.add(t.getTaskX().doubleValue());
		attr.add(t.getTaskY().doubleValue());
		attr.add(t.getTaskValue());
		Point p = new Point(t.getTaskId(), t.getTaskX().intValue(), t.getTaskY().intValue(), null, attr);
		this.field.addPoint(p);

	}
	
	/**
	 * transforms a FireBuildingTask into a double vector
	 * 
	 * @param t
	 * @return
	 */
	private double[] fireBuildingTask(FireBuildingTaskMessage t)
	{
		double d[] = new double[3];

		//int apartmentsPerFloor = t.getApartmentsPerFloor();
		//int buildingHP = t.getBuildingHP();
		//int floors = t.getFloors();
		//int groundArea = t.getGroundArea();
		//int matter = t.getMatter().getValue();
		double success = t.getTaskValue(); 
		double x = t.getTaskX();
		double y = t.getTaskY();
		//int temperature = t.getTemperature().getValue();
		
		d[0] = x;
		d[1] = y;
		d[2] = success;
		
		//d[0] = apartmentsPerFloor;
		//d[1] = buildingHP;
		//d[2] = floors;
		//d[3] = groundArea;
		//d[4] = matter;
		//d[5] = success;
		//d[6] = x;
		//d[7] = y;
		//d[8] = temperature;
		//String data = d[0]+";"+d[1]+";"+d[2]+";"+d[3]+";"+d[4]+";"+d[5]+";"+d[6]+";"+d[7]+";"+d[8];
		
		String data = d[0]+";"+d[1]+";"+d[2];
		WriteFile.getInstance().write(data,this.FILEKMEANS);
		return d;
		
	}
	/*
	public static void show2DMap(GrowingSelfOrganizingMap gsom)
	{
	    // GSOM 2D
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
		
	}*/
	
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
