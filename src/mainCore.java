
import ufrgs.maslab.abstractsimulator.core.Environment;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Agent;
import ufrgs.maslab.abstractsimulator.core.taskAllocation.Task;


public class mainCore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Environment<Task,Agent<Task>> env = new Environment<Task,Agent<Task>>();
		
		for(int i = 0; i < 10; i++)
		{
			env.getVariables().add(new Agent<Task>(1d,1d));
		}
		
		for(int j = 0; j < 10; j++)
		{
			env.getValues().add(new Task());
			
		}
		
		for(Agent<Task> a : env.getVariables())
		{
			System.out.println("Agent "+a.getId());
		}
		
		for(Task t : env.getValues())
		{
			if(t.getId() == 13)
				t.setX(10);
			System.out.println("Task "+t.getId());
		}
		
		System.out.println("task x "+env.findValueByID(13).getX());
		System.out.println("agent y "+env.findVariableByID(3).getY());
		
		
		
	}

}
