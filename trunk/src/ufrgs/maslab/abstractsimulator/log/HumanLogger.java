package ufrgs.maslab.abstractsimulator.log;

import ufrgs.maslab.abstractsimulator.core.taskAllocation.Human;
import ufrgs.maslab.abstractsimulator.util.Transmitter;
import ufrgs.maslab.abstractsimulator.util.WriteFile;

public class HumanLogger {
	
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
		logFile = Transmitter.getProperty("files.properties", "agent");
		WriteFile.getInstance().openFile(logFile);
		String header = "id;strength;dexterity;stamina;charisma;appearance;leadership;intelligence;reasoning;perception;will;hp;x;y";
		WriteFile.getInstance().write(header,logFile);
	}
	
	/**
	 * saves the current step of the environment in the log file
	 * @param time
	 */
	public static void logHuman(Human h)
	{
		WriteFile.getInstance().openFile(logFile);
		String step = h.getId()+";"
				+h.getStrength()+";"
				+h.getDexterity()+";"
				+h.getStamina()+";"
				+h.getCharisma()+";"
				+h.getAppearance()+";"
				+h.getLeadership()+";"
				+h.getIntelligence()+";"
				+h.getReasoning()+";"
				+h.getPerception()+";"
				+h.getWill()+";"
				+h.getHp()+";"
				+h.getX()+";"
				+h.getY()
				;
		WriteFile.getInstance().write(step, logFile);

	}

}
