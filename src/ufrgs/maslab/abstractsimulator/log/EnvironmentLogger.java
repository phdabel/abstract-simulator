package ufrgs.maslab.abstractsimulator.log;

import java.util.ArrayList;
import java.util.Map;

import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;

public class EnvironmentLogger {
	
	/**
	 * <ul>
	 * <li>environment log filename</li>
	 * </ul>
	 * 
	 */
	private static String logFile = null;
	
	/**
	 * creates header for environment log file
	 */
	public static void saveHeader()
	{
		logFile = Transmitter.getProperty("files.properties", "environment");
		WriteFile.getInstance().openFile(logFile);
		String header = "time;task_id;agents_id";
		WriteFile.getInstance().write(header,logFile);
		WriteFile.getInstance().nLine(logFile);
	}
	
	/**
	 * saves the current step of the environment in the log file
	 * @param time
	 */
	public static void saveCurrentStep(Integer time, Map<Integer, ArrayList<Integer>> allocationSet)
	{
		WriteFile.getInstance().openFile(logFile);
		String step = null;
		if(!allocationSet.isEmpty()){
			for(Integer val : allocationSet.keySet())
			{
				step = time.toString()+";";
				step += val.toString()+";";
				for(Integer var : allocationSet.get(val))
				{
					step += var.toString()+";";
				}
				step += "total;"+allocationSet.get(val).size()+";";
				WriteFile.getInstance().write(step, logFile);
				WriteFile.getInstance().nLine(logFile);
			}
		}else{
			step = time.toString()+";";
			WriteFile.getInstance().write(step, logFile);
			WriteFile.getInstance().nLine(logFile);
		}
	}

}
