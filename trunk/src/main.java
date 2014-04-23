import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import ufrgs.maslab.abstractsimulator.myagents.GSOMAgent;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.Task;
import ufrgs.maslab.gsom.learning.GSOMLearning;
import ufrgs.maslab.gsom.network.GrowingSelfOrganizingMap;
import ufrgs.maslab.gsom.norm.Template;
import ufrgs.maslab.gsom.norm.TemplateNormalizer;
import ufrgs.maslab.gsom.util.Position;
import ufrgs.maslab.gsom.util.visualization.GrowingSelfOrganizingMapGraph2D;
import ufrgs.maslab.gsom.util.visualization.SelfOrganizingMapGraph3D;


public class main {

	
	private static boolean randomAgent = false;
	
	private transient static ArrayList<Position> tmpList = new ArrayList<Position>();
	
	static Template template = new Template(new double[]{0,0,0,0,1,0,0,0},new double[]{4,1,1,1,5,1,99,99});
	
	private transient static Double clusterMeasure = 0.0;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		/*
		//Task[] tArray = new Task[sim.getEnvironment().getTasks().size()];
		//tArray = sim.getEnvironment().getTasks().toArray(tArray);
		//MergeSort.mergeSort(tArray,true);
		
		template.setAttributes(new String[]{"temperature","wood","steel","concrete","floors","areaGround","x","y"});
		String fileLog = "log_experiment_1";
		WriteFile.getInstance().openFile(fileLog);
		
        String header = "";
        header = "time;total tasks;generated tasks;solved tasks; unsolved tasks;agents";				
		WriteFile.getInstance().write(header,fileLog);
		DisasterSimulation<Task, GSOMAgent<Task>> sim = new DisasterSimulation(Task.class, GSOMAgent.class, 4);
		int startTime = sim.getTime();
		int endTime = sim.getTimesteps();
		GSOMLearning gsom = gsomProcessing(template);
		
		
		Map<Task,Double> map = new HashMap<Task,Double>();
		ValueComparator doubleComparator = new ValueComparator(map);
		SortedMap<Task,Double> sortedMap = new TreeMap<Task,Double>(doubleComparator);
		DataSet training = new DataSet(8,1);
		DataSet test = new DataSet(8,1);
		
		while(startTime <= endTime)
		{
			
			WriteFile.getInstance().openFile(fileLog);
			sim.printTasks(fileLog);
			String fileGsom = "gsom_info";
			WriteFile.getInstance().openFile(fileGsom);
			String header2 = "time;ID;Temperature;Matter;Floors;AreaGround;TotalArea;a_x;a_y;n_x;n_y";
			WriteFile.getInstance().write(header2,fileGsom);
			
			Map<Task, DataSetRow> m = mountDataSet(sim.getEnvironment().getTasks(),template);
			
			//treino online
			if(!randomAgent){
				if(startTime == 1){
					
					//os dados das tarefas foram recebidos atraves
					//do metodo mountDataSet
					//esses dados sao inseridos em um training DataSet
					for(DataSetRow row : m.values())
					{
						training.addRow(row);
					}
					// dados sao normalizados conforme template
					training.normalize(new TemplateNormalizer(template));
					//monta um conjunto de testes/validacao
					//com tarefas aleatorias
					for(int k = 0; k < 100; k++)
					{
						test.addRow(randomTask());
					}
					//normaliza conforme template
					test.normalize(new TemplateNormalizer(template));
					
					gsom.getNeuralNetwork().getStructure().setInput(test);
					gsom.getNeuralNetwork().getStructure().setTest(training);
					//growing phase
					gsom.learn();
					//smoothing phase
					gsom.testingMode();
					//limpa a rede
					//retira nós dummy
					
					gsom.getNeuralNetwork().getStructure().cleanNetwork();
			        //monta data skeleton model
					gsom.getNeuralNetwork().buildSkeleton();
			        gsom.getNeuralNetwork().creatingLabels();
			        show2DMap(gsom.getNeuralNetwork());
					show3DMap(gsom.getNeuralNetwork());
					
					for(Task t : m.keySet())
					{
						Double est = estimation(m.get(t),gsom);
						clusterMeasure = 0.0;
						map.put(t,est);
					}
					sortedMap.putAll(map);
					
					Map<Double,ArrayList<Task>> clusterOfTasks = new HashMap<Double,ArrayList<Task>>();
					
					for(Task t : sortedMap.keySet())
					{
						if(!clusterOfTasks.containsKey(map.get(t)))
						{
							clusterOfTasks.put(map.get(t), new ArrayList<Task>());
						}
						clusterOfTasks.get(map.get(t)).add(t);
						
					}
					
					//System.out.println("size clusters "+clusterOfTasks.size());
					
					//for(Double d : clusterOfTasks.keySet())
					//{
					//	System.out.println("double "+d+" tasks "+clusterOfTasks.get(d).size());
					//}

					int ct = 0;
					int idC = 0;
					int totalAg = sim.getEnvironment().getAgents().size();
					ArrayList<GSOMAgent<Task>> coleagues = new ArrayList<GSOMAgent<Task>>();

					ArrayList<Double> clusterPriority = new ArrayList<Double>();
					Double sum = 0.0;
					for(Double d : clusterOfTasks.keySet())
					{
						clusterPriority.add(d);
						sum += d;
					}
					Queue<GSOMAgent<Task>> tmpList = new LinkedList<GSOMAgent<Task>>();
					tmpList.addAll(sim.getEnvironment().getAgents());
					for(Double d : clusterOfTasks.keySet())
					{
						int totalAgents = 0;
						//defining domain of agents
						while(totalAgents < (int)Math.abs(totalAg * (d/sum)))
						{
							GSOMAgent<Task> agent = tmpList.poll();
							if(agent!=null){
								coleagues.add(agent);
								agent.setDomain(clusterOfTasks.get(d));
								if(totalAgents == 0)
								{
									agent.leader = true;
								}
								totalAgents++;
							}else{
								break;
							}
						}
						for(GSOMAgent<Task> a : coleagues)
						{
							a.agents.addAll(coleagues);
						}
						coleagues.clear();
					}
					
					//Double rVal;
					//for(GSOMAgent a : sim.getEnvironment().getAgents())
					//{
					//	rVal = clusterPriority.get(idC)/sum;
					//	Double agentRatio = Math.abs(rVal * totalAg);
					//	if(ct < agentRatio)
					//	{
					//		coleagues.add(a);
					//		a.setDomain(clusterOfTasks.get(clusterPriority.get(idC)));
					//		ct++;
					//	}else
					//	{
					//		coleagues.add(a);
					//		a.setDomain(clusterOfTasks.get(clusterPriority.get(idC)));
					//		a.agents = coleagues;
					//		for(Object c : a.agents)
					//		{
					//			GSOMAgent tmpAgent = (GSOMAgent)c;
					//			//System.out.println("ratio "+agentRatio+" leader "+a.getId()+" tmpAgent "+tmpAgent.getId());
					//			sim.getEnvironment().getAgents().get(tmpAgent.getId()).agents = a.agents;
					//			
					//		}
					//		a.leader = true;
					//		coleagues.clear();
					//		ct = 0;
					//		idC++;
					//	}
					//}
					
					map.clear();
					sortedMap.clear();
					
				}
				
			}
			for(Task t : sim.getEnvironment().getTasks())
			{
				String task = startTime+";"+t.getId() + ";"+ t.getTemperature() + ";" + t.getMatter() + ";" + t.getFloors() + ";" + t.getAreaGround()
						+ ";" + t.getTotalArea() + ";" + t.getX() + ";" + t.getY() + ";" 
						+ gsom.getNeuralNetwork().getStructure().getWinner().getAxisPosition().get(0) + ";" 
						+ gsom.getNeuralNetwork().getStructure().getWinner().getAxisPosition().get(1);
				WriteFile.getInstance().write(task,fileGsom);
				
			}

			//System.out.println("simulation step - time "+startTime); 
			sim.simulationStep();
			startTime++;
			
			
		}
		//sim.simulationStart();
		//System.out.println(Math.log(20));
		WriteFile.getInstance().closeFile();
		System.out.println("Simulation Ended!");
		*/
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
	
	
	public static Double estimation(DataSetRow row, GSOMLearning gsom)
	{
		double[] extinguish = new double[row.getInput().length];
		gsom.calculate(row.getInput());
		double cluster = 0.0;
		getClusterSize(gsom.getNeuralNetwork().getStructure().getWinner(), gsom);
		tmpList.clear();
		return clusterMeasure;
		/*
		int ct = 0;;
		for(double d: row.getInput())
		{
			extinguish[ct] = d;
			ct++;
		}
		Position p1 = gsom.getNeuralNetwork().getStructure().getWinner();
		extinguish[0] = 0d;
		extinguish[1] = 0d;
		extinguish[2] = 0d;
		extinguish[3] = 0d;
		extinguish[4] = 0d;
		
		extinguish[14] = 0d;
		gsom.calculate(extinguish);
		Position p2 = gsom.getNeuralNetwork().getStructure().getWinner();
		return euclidianDistance(p1,p2);*/
	}
	
	public static void getClusterSize(Position p, GSOMLearning gsom)
	{
		clusterMeasure += gsom.getNeuralNetwork().getStructure().getNeuronAt(p).hits;
		tmpList.add(p);
		for(Position pos : gsom.getNeuralNetwork().getStructure().getNeuronAt(p).getNeighbours())
		{
			if(!tmpList.contains(pos)){
				getClusterSize(pos, gsom);
			}
		}
	}
	
	public static GSOMLearning gsomProcessing(Template template)
	{		
		/*
		DataSet initSet = new DataSet(8,1);
		for(int i = 0; i < 50; i++)
		{
			initSet.addRow(randomTask());
		}
		
		initSet.normalize(new TemplateNormalizer(template));
	    
		ArrayList<Integer> dim = new ArrayList<Integer>();
        dim.add(2);
        dim.add(2);
        
        GrowingSelfOrganizingMap gsom = new GrowingSelfOrganizingMap(initSet,dim,template);
        
               
        GSOMLearning learning = new GSOMLearning(gsom);
        learning.getNeuralNetwork().getStructure().setInitializationDataSet(initSet);
        learning.initialize();
        
        //learning.weightFeature1 = featureImportance1;
        //learning.weightFeature2 = featureImportance2;
        
        
        //learning.learn();
        //gsom.getStructure().cleanNetwork();
        
        //gsom.creatingLabels();
    
    	
    	return learning;
		*/
		return null;
	}
	
	public static Double euclidianDistance(Position p1, Position p2)
	{
		double diff = 0.0;
		for(int k = 0; k < p1.getDimension(); k++)
		{
			diff += Math.pow((p1.getAxisPosition().get(k) - p2.getAxisPosition().get(k)),2);
		}
		return Math.sqrt(diff);
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
	
	public static DataSetRow task(Task t)
	{
		/*
		double[] d = new double[8];
		Arrays.fill(d,0.0);
		int temperature = t.getTemperature();
		d[0] = temperature;
		int matter = t.getMatter(); //matter - d[1] - d[3]
		d[matter+1] = 1;
		int floors = t.getFloors(); //floors - d[4]
		d[4] = floors;
		if(t.getAreaGround() > 500) //groundArea - d[5]
		{
			d[5] = 1;
		}else{
			d[5] = 0;
		}
		d[6] = t.getX();
		d[7] = t.getY();
		DataSetRow task = new DataSetRow(d, new double[]{0});
		return task;
		*/
		return null;
	}
	
	public static DataSetRow randomTask()
	{
		Random r = new Random();
		//Random r1 = new Random();
		double[] d = new double[8];
		Arrays.fill(d, 0);
		for(int i = 0; i < 6; i++)
		{
			switch(i)
			{
				//temperature
				case 0: //temperature - d[0]
					int temperature = r.nextInt(4);
					d[i] = 1+temperature;
					break;
				//matter
				case 1:
					int matter = r.nextInt(3); //matter - d[1] - d[3]
					d[1+matter] = 1;
					break;
				//floors
				case 2:
					if(d[1] == 0){
						int floors = (r.nextInt(5)); //floors - d[4]
						d[4] = 1+floors;
					}else{
						int floors = (r.nextInt(2)); //floors - d[4]
						d[4] = 1+floors;
					}
					break;
				//areaGround
				case 3:
					d[5] = r.nextInt(2); //d[5] areaGround 0 < 500; 1 >= 500
					break;
				//x coordinate
				case 4:
					d[6] = r.nextDouble(); //x coordinate - d[6]
					break;
				case 5:
					d[7] = r.nextDouble(); //y coordinate - d[7]
					break;
			}
		}
		DataSetRow task = new DataSetRow(d, new double[]{0});
		return task;
	}
	
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

