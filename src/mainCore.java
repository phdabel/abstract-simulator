import ufrgs.maslab.abstractsimulator.core.BlackBox;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;
import ufrgs.maslab.abstractsimulator.variables.FireFighter;
import ufrgs.maslab.abstractsimulator.variables.FireStation;
import ufrgs.maslab.abstractsimulator.variables.Human;

public class mainCore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//BlackBox<Task, Human> core = new BlackBox<Task,Human>(Human.class, Task.class);
		
		BlackBox core = new BlackBox();
		core.newEnvironment();
		
		try {
			/**
			 * insert fire fighter agents
			 * insert fire building tasks
			 * insert fire station agents
			 */
			core.addAgent(FireFighter.class, 5);
			core.addTask(FireBuildingTask.class, 50);
			core.addAgent(FireStation.class, 2);
			
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			core.simulationStart();
		} catch (SimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		for(Variable var : core.getEnvironment().getVariables())
		{
			System.out.println(var.getClass());
			if(var instanceof Human){
				Human h = (Human)var;
				System.out.println("Attributes of Human "+h.getId());
				System.out.println("Strength "+h.getStrength());
				System.out.println("Dexterity "+h.getDexterity());
				System.out.println("Stamina "+h.getStamina());
				System.out.println("Charisma "+h.getCharisma());
				System.out.println("Appearance "+h.getAppearance());
				System.out.println("Leadership "+h.getLeadership());
				System.out.println("Intelligence "+h.getIntelligence());
				System.out.println("Reasoning "+h.getReasoning());
				System.out.println("Perception "+h.getPerception());
				System.out.println("ph "+((h.getStrength()-1)+(h.getDexterity()-1)+(h.getStamina()-1)));
				System.out.println("sc "+((h.getCharisma()-1)+(h.getAppearance()-1)+(h.getLeadership()-1)));
				System.out.println("mn "+((h.getIntelligence()-1)+(h.getReasoning()-1)+(h.getPerception()-1)));
				System.out.println("will "+h.getWill());
				System.out.println("x "+h.getX());
				System.out.println("y "+h.getY());
				System.out.println("domain "+h.getDomain().size());
				System.out.println();
			}else if(var instanceof FireStation){
				FireStation h = (FireStation)var;
				System.out.println("Attributes of the Fire Station "+h.getId());
				System.out.println("x "+h.getX());
				System.out.println("y "+h.getY());
				System.out.println("domain "+h.getDomain().size());
				System.out.println();
			}
		}
		for(Value val : core.getEnvironment().getValues())
		{
			System.out.println(val.getClass());
			FireBuildingTask t = (FireBuildingTask)val;
			System.out.println("Attributes of building "+t.getId());
			System.out.println("Temperature "+t.getTemperature());
			System.out.println("Floors "+t.getFloors());
			System.out.println("Ground Area "+t.getGroundArea());
			System.out.println("Success "+t.getSuccess());
			System.out.println("HP "+t.getBuildingHP());
			System.out.println("x "+t.getX());
			System.out.println("y "+t.getY());
		}*/
		
		
		
		
	}

}
