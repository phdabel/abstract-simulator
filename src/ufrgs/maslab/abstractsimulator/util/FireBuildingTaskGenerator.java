package ufrgs.maslab.abstractsimulator.util;

import java.util.Arrays;
import java.util.Random;

import ufrgs.maslab.abstractsimulator.constants.Matter;
import ufrgs.maslab.abstractsimulator.constants.Temperature;

public class FireBuildingTaskGenerator {
	
	public static double[] randomTask()
	{
		return randomTask(3, -1); 
	}
	
	public static double[] randomTask(int temperatureLevel)
	{
		return randomTask(temperatureLevel, -1);
	}
	
	public static double[] randomTask(int temperatureLevel, int matterLevel){
		double[] randomTask = new double[9];
		Arrays.fill(randomTask, 0d);
		Random r = new Random();
		
		int apartmentsPerFloor = 0;
		int buildingHP = 0;
		int floors = 0;
		int groundArea = 0;
		double success = 0;
		
		double x = r.nextDouble();
		double y = r.nextDouble();
		int matter = getMatter(matterLevel);
		
		int temperature = getTemperature(temperatureLevel);
		
		floors = getFloors(matter);
		
		groundArea = getGroundArea(matter);
		
		apartmentsPerFloor = getApartmentsPerFloor(groundArea);
		
		success = getSuccess(temperature, groundArea, apartmentsPerFloor);
				
		buildingHP = getBuildingHP(floors, apartmentsPerFloor, matter);
		
		randomTask[0] = apartmentsPerFloor;
		randomTask[1] = buildingHP;
		randomTask[2] = floors;
		randomTask[3] = groundArea;
		randomTask[4] = matter;
		randomTask[5] = success;
		randomTask[6] = x;
		randomTask[7] = y;
		randomTask[8] = temperature;
		
		return randomTask;
	}
	
	
	/**
	 * return matter 
	 * 
	 * @param matter
	 * @return
	 */
	private static int getMatter(int matter)
	{
		if(matter < 0)
			return Matter.randomMatter().getValue();
		return matter;
	}
	
	/**
	 * return random temperature 
	 * 
	 * @param level
	 * @return
	 */
	private static int getTemperature(int level)
	{
		Random r = new Random();
		switch(level)
		{
			case 1:
				return (r.nextInt(2) + 1);
			case 2:
				return (r.nextInt(2) + 3);
			default:
				return Temperature.randomTemperature().getValue();
		}
		
	}
	
	/**
	 * return number of floors of the building
	 * 
	 * @param matter
	 * @return
	 */
	private static int getFloors(int matter)
	{
		Random r = new Random();
		switch(matter)
		{
			case 0:
				return (1 + r.nextInt(2));
			case 1:
				return (1 + r.nextInt(5));
			default:
				return (1 + r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.floors")));
		}
	}
	
	/**
	 * return ground area of the building
	 * 
	 * @param matter
	 * @return
	 */
	private static int getGroundArea(int matter)
	{
		Random r = new Random();
		switch(matter)
		{
			case 0:
				return 50;
			default:
				return (50 + r.nextInt(Transmitter.getIntConfigParameter("config.properties", "maximum.groundArea")));
		}
	}
	
	/**
	 * return the number of apartments per floor
	 * 
	 * @param groundArea
	 * @return
	 */
	private static int getApartmentsPerFloor(int groundArea)
	{
		return (int)groundArea/50;
	}
	
	/**
	 * return hp of the building
	 * 
	 * @param floors
	 * @param apartmentsPerFloor
	 * @param matter
	 * @return
	 */
	private static int getBuildingHP(int floors, int apartmentsPerFloor, int matter)
	{
		return floors * apartmentsPerFloor + (10 ^ matter);
	}
	
	/**
	 * return difficulty of extinguish fire from building 
	 * 
	 * @param temperature
	 * @param groundArea
	 * @param apartmentsPerFloor
	 * @return
	 */
	private static int getSuccess(int temperature, int groundArea, int apartmentsPerFloor)
	{
		switch(temperature)
		{
		
			case 1:
				return 5;
			case 2:
				return ((int)(groundArea / 10));
			case 3:
				return (apartmentsPerFloor * (int)(groundArea / 10));
			case 4:
				return (24+(apartmentsPerFloor * (int)(groundArea / 10)));
			default:
				return 0;
			
		}
	}

}
