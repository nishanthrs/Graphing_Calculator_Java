//Importing libraries to utilize data scructures (arrays, array lists) and calculate values and points
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Function_Calculation {
	//Window of the graph 
	public double minValue = -20.0;
	public double maxValue = 20.0;
	//Distance between points calculated
	public double gap = 0.05;
	//Number of points calculated in graph (determined by gap)
	private int steps = (int)((maxValue - minValue)/gap);
	
	//Function string to be parsed
    public String function = "(x*x*x)";
    //Array lists (double) that store the values at each point (xValues, yValues, derivative values)
    private List<Double> xArray = new ArrayList<Double>();
	private List<Double> yFunctionArray = new ArrayList<Double>();
    private List<Double> yDerivativesArray = new ArrayList<Double>();
    private List<Double> ySecondDerivativesArray = new ArrayList<Double>();
    //Array lists (point) to store the values as points to be used in graph
    private List<Point2D.Double> DerivativeZeroPoints = new ArrayList<Point2D.Double>();
    private List<Point2D.Double> secondDerivativeZeroPoints = new ArrayList<Point2D.Double>(); 
    private List<Point2D.Double> secondDerivativePositivePoints = new ArrayList<Point2D.Double>(); 
    private List<Point2D.Double> secondDerivativeNegativePoints = new ArrayList<Point2D.Double>();
    private List<Double> asymptotePoints = new ArrayList<Double>();
    private List<Point2D.Double> holePoints = new ArrayList<Point2D.Double>();
    
    //Public constructor (used for initialization of object in Graphing_View)
    public Function_Calculation()
    {
    	asymptotePoints.clear();
    	holePoints.clear();
    	setValues();
    	DerivativeZeroes();
    	SecondDerivativeZeroes();
    	SecondDerivativePositivePoints();
    	SecondDerivativeNegativePoints();
    	identifyAsymptote();
    }
    
    //Reset function data
    public void reComputeData()
    {
    	asymptotePoints.clear();
    	holePoints.clear();
    	setValues();
    	DerivativeZeroes();
    	SecondDerivativeZeroes();
    	SecondDerivativePositivePoints();
    	SecondDerivativeNegativePoints();  
    	identifyAsymptote();
    	steps = (int)((maxValue - minValue)/gap);
    }
   
    //Compute values (yValues) at each xPoint (separated by gap)
    public double valueAt(double x){
    	//Initialization of Java library (ScriptEngine) as objects to be used later in code
    	//Change computation engine later (to include all functions, trigonometric and logarithmic)
    	ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		Object eval = null;
		double functionasdouble = 0;
	 
		String newfunction = function.replaceAll("x", Double.toString(x));
		try
	 	{
			//Evaluates the function by plugging in real x values and computing the result using library
	        eval = engine.eval(newfunction);
	        //System.out.println(eval);
		} 
	    catch (ScriptException error) 
	 	{
	    	//Print info of the error
	    	error.printStackTrace();
		}
		try
		{
			functionasdouble = new BigDecimal(eval.toString()).doubleValue();
		}
		catch (NumberFormatException error)
		{
			if (!(function.contains("log"))) {
				//Distinguish between holes and asymptotes
				System.out.println("DIFFERENCE IN EXCEPTION: " + Math.abs(valueAt(x+gap) - valueAt(x-gap)));
				System.out.println("X POINT OF ASYMPTOTE: " + x);
				double y = valueAt(x+gap);//new BigDecimal(eval.toString()).doubleValue();
				if (Math.abs(valueAt(x+gap) - valueAt(x-gap)) < 1) {
					System.out.println("HOLE DETECTED AT X: " + x);
					holePoints.add(new Point2D.Double((x-minValue)/gap, y));
				}
				else {
					asymptotePoints.add((x-minValue)/gap);
				}
			}
			else {
				asymptotePoints.add((x-minValue)/gap);
			}
		}
		 
		return functionasdouble;		 
   } 
   
    public void DerivativeZeroes()
    {
    	DerivativeZeroPoints.clear();
    	for (int i = 0; i < steps-1; i++)
    	{
    		
    		if ((yDerivativesArray.get(i))*(yDerivativesArray.get(i+1)) < 0 && Math.abs((yDerivativesArray.get(i))*(yDerivativesArray.get(i+1))) < 0.5 && Math.abs((yDerivativesArray.get(i))*(yDerivativesArray.get(i+1))) > 10E-20)
    		{
    			
    			double y = yFunctionArray.get(i);
    			DerivativeZeroPoints.add(new Point2D.Double((double)i, y));
    			//adds derivative zeroes
    		}
    	}
    }

    public void SecondDerivativeZeroes()
    {
    	secondDerivativeZeroPoints.clear();
    	//System.out.println("second derivative zeros");
    	for (int i = 0; i < steps; i++)
    	{
    		if ((ySecondDerivativesArray.get(i))*(ySecondDerivativesArray.get(i+1)) < 0 && Math.abs((ySecondDerivativesArray.get(i))*(ySecondDerivativesArray.get(i+1))) < 0.5 && Math.abs((ySecondDerivativesArray.get(i))*(ySecondDerivativesArray.get(i+1))) > 10E-20)
    		{
    			//System.out.println(ySecondDerivativesArray.get(i));
    			//System.out.println(ySecondDerivativesArray.get(i+1));
    			double y = yFunctionArray.get(i);
    			secondDerivativeZeroPoints.add(new Point2D.Double((double)i, y));
    		}
    	}
    }
    
    public void SecondDerivativePositivePoints()
    {
    	secondDerivativePositivePoints.clear();
    	for (int i = 0; i < steps + 1; i++)
    	{
    		if (ySecondDerivativesArray.get(i) >= 0)
    		{
    			 double y = yFunctionArray.get(i);
    			 secondDerivativePositivePoints.add(new Point2D.Double((double)i, y));
    		}
    	}
    }
    
    public void SecondDerivativeNegativePoints()
    {
    	secondDerivativeNegativePoints.clear();
    	for (int i = 0; i < steps + 1; i++)
    	{
    		if (ySecondDerivativesArray.get(i) <= 0)
    		{
    			 double y = yFunctionArray.get(i);
    			 secondDerivativeNegativePoints.add(new Point2D.Double((double)i, y));
    		}
    	}
    }
    
    //Make sure to distinguish between holes and asymptotes
    public List<Double> identifyAsymptote()
    {
    	for (int i = 0; i < steps; i++)
    	{
    		if (((yDerivativesArray.get(i+1) * yDerivativesArray.get(i)) < -15000 && (yFunctionArray.get(i+1) * yFunctionArray.get(i)) > 15000) 
    				|| ((yDerivativesArray.get(i+1) * yDerivativesArray.get(i)) > 15000 && (yFunctionArray.get(i+1) * yFunctionArray.get(i)) < -15000))
    		{	
    			asymptotePoints.add((double) i);
    		}
    	}
    	return asymptotePoints;
    }
    
    //Computing methods to identify critical and inflection points and getter methods of the class to retrieve all the points and use in the front end 
    public List<Double> getXArray()
    {
    	return xArray;	
    }
    
    public List<Double> getYFunctionArray()
    {
    	return yFunctionArray;	
    }
    
    public List<Double> getYDerivativesArray()
    {
    	return yDerivativesArray;	
    }
    
    public List<Double> getYSecondDerivativesArray()
    {
    	return ySecondDerivativesArray;	
    }
    
    public List<Point2D.Double> getDerivativeZeroes()
    {
    	return DerivativeZeroPoints;	
    }
    
    public List<Point2D.Double> getSecondDerivativeZeroes()
    {
    	return secondDerivativeZeroPoints;	
    }
    
    public List<Point2D.Double> getSecondDerivativePositives()
    {
    	return secondDerivativePositivePoints;	
    }
    
    public List<Point2D.Double> getSecondDerivativeNegatives()
    {
    	return secondDerivativeNegativePoints;	
    }
    
    public List<Double> getAsymptotePoints()
    {
    	return asymptotePoints;	
    }
    
    public List<Point2D.Double> getHolePoints()
    {
    	return holePoints;
    }
    
    
    public void getSecondDerivativePositivePoints()
    {
    	for (int i = 0; i < steps + 1; i++)
    	{
    		if ((ySecondDerivativesArray.get(i))>=0.01)
    		{
    			secondDerivativePositivePoints.add(new Point2D.Double(minValue + i * gap, yFunctionArray.get(i)));
    		}
    	}
    }
    
    public void getSecondDerivativeNegativePoints()
    {
    	for (int i = 0; i < steps + 1; i++)
    	{
    		if ((ySecondDerivativesArray.get(i))<=-0.01)
    		{
    			secondDerivativeNegativePoints.add(new Point2D.Double(minValue + i * gap, yFunctionArray.get(i)));
    		}
    	}
    }
    
    //Reset points to be added to graph
    public void setValues()
    {
    	xArray.clear();
    	yFunctionArray.clear();
        for (int i = 0; i<steps+1; i++)
        {
       		xArray.add((double) (minValue + i * gap));
       		//defines yFunctionArray
       		yFunctionArray.add((double)(valueAt(xArray.get(i))));
       	}
       	
        yDerivativesArray.clear();
       	double yDMin = Double.MAX_VALUE;
       	for (int i=0; i< steps; i++)
       	{
       		//Calculation of derivative using slope method
       		yDerivativesArray.add((double)(yFunctionArray.get(i+1) - yFunctionArray.get(i))/gap);
       		yDMin = Math.min(yDMin, (double)(yFunctionArray.get(i+1) - yFunctionArray.get(i))/gap);
       		//if(yDMin < -10)
       			//System.out.println(i);
       			
       		//functionDerivativesArray[i] = valueAt(derivativesArray[i]);
       	}
       	//add boundary conditions
    	yDerivativesArray.add((double)((valueAt(minValue + (steps+1)*gap) - yFunctionArray.get(steps))/gap));	
        ySecondDerivativesArray.clear();
    	for (int i=0; i<steps-1; i++)
    	{
    		//Calculation of second derivative using similar slope method
    		ySecondDerivativesArray.add((yDerivativesArray.get(i+1) - yDerivativesArray.get(i))/gap);
    	}
    	double derivative_steps_n1 = (valueAt(minValue + steps*gap)-valueAt(minValue + (steps-1)*gap))/gap;
    	double derivative_steps = (valueAt(minValue + (steps+1)*gap)-valueAt(minValue + steps*gap))/gap;
    	double derivative_steps_p1 = (valueAt(minValue + (steps+2)*gap)-valueAt(minValue + (steps+1)*gap))/gap;
    	ySecondDerivativesArray.add(((derivative_steps - derivative_steps_n1)/gap));
    	ySecondDerivativesArray.add(((derivative_steps_p1 - derivative_steps)/gap));
    }
}
    
    
    
    
