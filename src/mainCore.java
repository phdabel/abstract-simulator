
import ufrgs.maslab.abstractsimulator.core.BlackBox;
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.Value;
import ufrgs.maslab.abstractsimulator.core.Variable;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;
import ufrgs.maslab.abstractsimulator.disaster.DisasterEnvironment;


public class mainCore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		BlackBox<DisasterEnvironment<Task,Agent>, Task, Agent> core;
		core = new BlackBox(DisasterEnvironment.class, Agent.class, Task.class);
		
		
		System.out.println("task x "+core.getEnvironment().findValueByID(13).getX());
		System.out.println("agent y "+env.findVariableByID(3).getY());
		
		
		
	}

}
