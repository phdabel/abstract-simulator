import ufrgs.maslab.abstractsimulator.core.BlackBox;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Human;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;
import ufrgs.maslab.abstractsimulator.disaster.FireFighter;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;


public class mainCore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//BlackBox<Task, Human> core = new BlackBox<Task,Human>(Human.class, Task.class);
		
		BlackBox<Task, Human> core = new BlackBox<Task,Human>();
		core.newEnvironment();
		
		try {
			core.addAgent(FireFighter.class);
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(Human h : core.getEnvironment().getVariables())
		{
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
			System.out.println();
		}
		
		try {
			core.simulationStart();
		} catch (SimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
