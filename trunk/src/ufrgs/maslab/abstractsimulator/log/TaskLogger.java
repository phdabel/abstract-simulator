package ufrgs.maslab.abstractsimulator.log;

import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.Task;

public class TaskLogger {
	
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
		logFile = Transmitter.getProperty("files.properties", "task");
		WriteFile.getInstance().openFile(logFile);
		String header = "id;x;y";
		WriteFile.getInstance().write(header,logFile);
	}
	
	/**
	 * saves the current step of the environment in the log file
	 * @param time
	 */
	public static void logTask(Task t)
	{
		WriteFile.getInstance().openFile(logFile);
		String step = t.getId()+";"
				+t.getX()+";"
				+t.getY()
				;
		WriteFile.getInstance().write(step, logFile);
	}

}
