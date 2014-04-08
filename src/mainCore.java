import ufrgs.maslab.abstractsimulator.core.BlackBox;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Human;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;


public class mainCore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BlackBox<Task, Human> core = new BlackBox<Task,Human>(Human.class, Task.class);
		
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
		System.out.println("core task x "+core.getEnvironment().findValueByID(103).getClass());
		System.out.println("core agent y "+core.getEnvironment().findVariableByID(3).getClass());
		System.out.println("human priority "+core.getEnvironment().findVariableByID(3).getPriority().toString());
		System.out.println(core.getSimulation());
		
		try {
			core.simulationStart();
		} catch (SimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
