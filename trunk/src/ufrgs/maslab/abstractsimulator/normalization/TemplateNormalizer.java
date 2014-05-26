package ufrgs.maslab.abstractsimulator.normalization;

import java.util.ArrayList;
import java.util.List;

import ufrgs.maslab.abstractsimulator.algorithms.model.Point;
import ufrgs.maslab.abstractsimulator.exception.SimulatorException;

public class TemplateNormalizer implements Normalizer {
	
	private double lowLimit=0, highLimit=1;
    double[] maxIn, maxOut; // contains max values for in and out columns
    double[] minIn, minOut; // contains min values for in and out columns     

    public TemplateNormalizer(Template template) {
        this.maxIn = template.maxInputValue;
        this.minIn = template.minInputValue;
        this.maxOut = template.maxOutputValue;
        this.minOut = template.minOutputValue;
    }  
    
    public void normalize(List<Point> dataSet) {
    	
    	if(dataSet.isEmpty())
    		throw new SimulatorException("DataSet is empty!");
    	if(dataSet.get(0).getAttributes().size() != maxIn.length || dataSet.get(0).getAttributes().size() != minIn.length)
    		throw new SimulatorException("Input size different of the Template size.");
    	
        for (Point row : dataSet) {
            ArrayList<Double> normalizedInput = normalizeToRange(row.getAttributes(), minIn, maxIn);
            row.setAttributes(normalizedInput);
            //if (dataSet.isSupervised()) {
            //    double[] normalizedOutput = normalizeToRange(row.getDesiredOutput(), minOut, maxOut);
            //    row.setDesiredOutput(normalizedOutput);
            //}
        }
    }
    
    private ArrayList<Double> normalizeToRange(ArrayList<Double> vector, double[] min, double[] max) {
    	ArrayList<Double> normalizedVector = new ArrayList<Double>();

    	for(int i = 0; i < vector.size(); i++)
    	{
    		Double e = ((vector.get(i) - min[i]) / (max[i] - min[i])) * (highLimit - lowLimit) + lowLimit ;
    		normalizedVector.add(e);
    	}

        return normalizedVector;             
    }	

}
