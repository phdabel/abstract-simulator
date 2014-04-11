package ufrgs.maslab.abstractsimulator.util;

import java.util.ArrayList;

import ufrgs.maslab.abstractsimulator.exception.SimulatorException;

public class Utilities {
	
	private static String exceptionFile = "exception.properties";
	
	public static Double euclideanDistance(ArrayList<Double> e1, ArrayList<Double> e2) throws SimulatorException{
		Double dist = 0d;
		if(e1.size() != e2.size())
			throw new SimulatorException(Transmitter.getProperty(exceptionFile, "exception.array.different.size"));
		
		for(int k = 0; k < e1.size(); k++)
		{
			dist += Math.pow((e1.get(k) - e2.get(k)),2);
		}
		return Math.sqrt(dist);
	}

}
