package ufrgs.maslab.abstractsimulator.log;

import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;
import ufrgs.maslab.abstractsimulator.values.FireBuildingTask;

public class FireBuildingTaskLogger {
	
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
		String header = "id;temperature;matter;floors;damage;groundArea;apartmentsPerFloor;buildingHP;x;y";
		WriteFile.getInstance().write(header,logFile);
	}
	
	/**
	 * saves the current step of the environment in the log file
	 * @param time
	 */
	public static void logFireBuildingTask(FireBuildingTask t)
	{
		WriteFile.getInstance().openFile(logFile);
		String step = t.getId()+";"
				+t.getTemperature()+";"
				+t.getMatter()+";"
				+t.getFloors()+";"
				+t.getDamageType()+";"
				+t.getGroundArea()+";"
				+t.getApartmentsPerFloor()+";"
				+t.getBuildingHP()+";"
				+t.getX()+";"
				+t.getY()
				;
		WriteFile.getInstance().write(step, logFile);
	}

}
