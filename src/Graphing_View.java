import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Graphing_View extends JPanel {

    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(255, 255, 255, 150);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color pointsOfInflectionColor = Color.BLUE;
    private Color maxMinColor = Color.MAGENTA;
    private Color concaveUpColor = Color.YELLOW;
    private Color concaveDownColor = Color.CYAN;
    private Color holeColor = Color.RED;
    private Color gridColor = new Color(200, 200, 200, 200);
    private Color asymptoteColor = Color.ORANGE;
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 2;
    private int specialPointWidth = 7;
    private int numberYDivisions = 10;
    //public int arraySize = 400; 
    public int intType;
    public List<Double> xArray; // = new ArrayList<>();
	public List<Double> yArray; // = new ArrayList<>();
	public List<Double> derivativeYArray;
	public List<Double> secondDerivativeYArray;
	public List<Point2D.Double> derivativeZeroPoints;
	public List<Point2D.Double> secondDerivativeZeroPoints;
	public List<Point2D.Double> secondDerivativePositivePoints;
	public List<Point2D.Double> secondDerivativeNegativePoints;
	public List<Double> asymptotePoints;
	public List<Point2D.Double> holePoints;
	//public List<Double> infinityPoints;

	public Graphing_View(int intInput, Function_Calculation functionData){
	   this.intType = intInput;
	   if (intType == 0)
	   {
		   //takes arrays from FunctionData
		   this.xArray = functionData.getXArray();
		   this.yArray = functionData.getYFunctionArray(); 
		   //Add in second and third x Array / y Array to graph derivative on same graph
		   this.derivativeYArray = functionData.getYDerivativesArray();
		   this.secondDerivativeYArray = functionData.getYSecondDerivativesArray();
		   this.derivativeZeroPoints = functionData.getDerivativeZeroes();
		   this.secondDerivativeZeroPoints = functionData.getSecondDerivativeZeroes();
		   this.secondDerivativePositivePoints = functionData.getSecondDerivativePositives();
		   this.secondDerivativeNegativePoints = functionData.getSecondDerivativeNegatives();
		   this.asymptotePoints = functionData.getAsymptotePoints();
		   this.holePoints = functionData.getHolePoints();
		   //this.infinityPoints = functionData.getInfinityPoints();
	   }
	   else if (intType == 1)
	   {
		   this.xArray = functionData.getXArray();
		   this.yArray = functionData.getYDerivativesArray();		   	   
	   }
	   else if (intType == 2)
	   {
		   this.xArray = functionData.getXArray();
		   this.yArray = functionData.getYSecondDerivativesArray(); 		   	   		   
	   }
      
   }
   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        System.out.println("DERIVATIVE POINTS: " + derivativeYArray);
        System.out.println("SECOND DERIVATIVE POINTS: " + secondDerivativeYArray);
        System.out.println("DERIVATIVE ZEROS: " + this.derivativeZeroPoints);
        System.out.println("INFLECTION POINTS: " + this.secondDerivativeZeroPoints);
        
        System.out.println("ASYMPTOTE POINTS: " + this.asymptotePoints);
        System.out.println("HOLES IN GRAPHING_VIEW: " + this.holePoints);

        //setValues();
        double maxValueY = getMaxArrayValue(yArray);
        double minValueY = getMinArrayValue(yArray);
//        double maxValueYDerivative = getMaxArrayValue(derivativeYArray);
//        double minValueYDerivative = getMinArrayValue(derivativeYArray);
//        double maxValueYSecondDerivative = getMaxArrayValue(secondDerivativeYArray);
//        double minValueYSecondDerivative = getMinArrayValue(secondDerivativeYArray);
//        System.out.println("MIN VALUE Y DERIVATIVE: " + minValueYDerivative);
//        System.out.println("MAX VALUE Y DERIVATIVE: " + maxValueYDerivative);
        double maxValueX = getMaxArrayValue(xArray);
        double minValueX = getMinArrayValue(xArray);
        
        //if (intType != 0)
        //{ 
        	maxValueY = maxValueY + 2;
        	minValueY = minValueY - 2;  
//        	maxValueYDerivative += 2;
//        	minValueYDerivative -= 2;
//        	maxValueYSecondDerivative += 2;
//        	minValueYSecondDerivative -= 2;
        //}
        
        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (xArray.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxValueY - minValueY);
//        double yScaleDerivative = ((double) getHeight() - 2 * padding - labelPadding) / (maxValueYDerivative - minValueYDerivative);
//        double yScaleSecondDerivative = ((double) getHeight() - 2 * padding - labelPadding) / (maxValueYSecondDerivative - minValueYSecondDerivative);
        
        List<Point> graphFunctionPoints = new ArrayList<Point>();
        List<Point> graphDerivativePoints = new ArrayList<Point>();
        List<Point> graphSecondDerivativePoints = new ArrayList<Point>();
        for (int i = 0; i < xArray.size(); i++) 
        {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((maxValueY - yArray.get(i)) * yScale + padding);
//            int y2 = (int) ((maxValueY - derivativeYArray.get(i)) * yScaleDerivative + padding);
//            int y3 = (int) ((maxValueY - secondDerivativeYArray.get(i)) * yScaleSecondDerivative + padding);
            graphFunctionPoints.add(new Point(x1, y1));
//            graphDerivativePoints.add(new Point(x1, y2));
//            graphSecondDerivativePoints.add(new Point(x1, y3));
        }
        
        System.out.println("GRAPH FUNCTION POINTS: " + graphFunctionPoints);
        System.out.println("GRAPH DERIVATIVE POINTS: " + graphDerivativePoints);
        System.out.println("GRAPH SECOND DERIVATIVE POINTS: " + graphSecondDerivativePoints);
        
        List<Point> graphDerivativeZeroes = new ArrayList<Point>();  
        List<Point> graphSecondDerivativeZeroes = new ArrayList<Point>();
        List<Point> graphSecondDerivativePositivePoints = new ArrayList<Point>();
        List<Point> graphSecondDerivativeNegativePoints = new ArrayList<Point>();
        List<Point> graphAsymptoteXPosition = new ArrayList<Point>();
        List<Point> graphHolePoints = new ArrayList<Point>();
        //List<Point> graphInfinityPoint = new ArrayList<Point>();
        
        if (intType == 0)
        {

	        for (int i = 0; i < derivativeZeroPoints.size(); i++) 
	        {
	            int x1 = (int) (derivativeZeroPoints.get(i).x * xScale + padding + labelPadding);
	            int y1 = (int) ((maxValueY - derivativeZeroPoints.get(i).y) * yScale + padding);
	            //graphs the max/min
	            graphDerivativeZeroes.add(new Point(x1, y1));
	        }
        }
        
       if (intType == 0)
        {

	        for (int i = 0; i < secondDerivativeZeroPoints.size(); i++) 
	        {
	            int x1 = (int) (secondDerivativeZeroPoints.get(i).x * xScale + padding + labelPadding);
	            int y1 = (int) ((maxValueY - secondDerivativeZeroPoints.get(i).y) * yScale + padding);
	            //graphs the points of inflection
	            graphSecondDerivativeZeroes.add(new Point(x1, y1));
	        }
        }
   
       if (intType == 0)
       {
    	   for (int i = 0; i < secondDerivativePositivePoints.size(); i++)
    	   {
    		   int x1 = (int) (secondDerivativePositivePoints.get(i).x * xScale + padding + labelPadding);
    		   int y1 = (int) ((maxValueY - secondDerivativePositivePoints.get(i).y) * yScale + padding);
    		   //graphs concave up
    		   graphSecondDerivativePositivePoints.add(new Point(x1, y1));
    	   }
       }
       
       if (intType == 0)
       {
    	   for (int i = 0; i < secondDerivativeNegativePoints.size(); i++)
    	   {
    		   int x1 = (int) (secondDerivativeNegativePoints.get(i).x * xScale + padding + labelPadding);
    		   int y1 = (int) ((maxValueY - secondDerivativeNegativePoints.get(i).y) * yScale + padding);
    		   //graphs concave down
    		   graphSecondDerivativeNegativePoints.add(new Point(x1, y1));
    	   }
    	   System.out.println("INFLECTION POINTS IN NEW POINT: " + graphSecondDerivativeNegativePoints);
       }
       
       if (intType == 0)
       {
    	   for (int i = 0; i < asymptotePoints.size(); i++)
    	   {
    		   System.out.println("ASYMPTOTE X VALUES: " + asymptotePoints.get(i));
    		   System.out.println("NO. OF ASYMPTOTES: " + asymptotePoints.size());
    		   int x1 = (int)(asymptotePoints.get(i) * xScale + padding + labelPadding);
    		   int y1 = (int) ((maxValueY - maxValueY ) * yScale + padding);
    		   int y2 = (int) ((maxValueY - minValueY ) * yScale + padding);
    		   graphAsymptoteXPosition.add(new Point(x1, y1));
    		   graphAsymptoteXPosition.add(new Point(x1, y2));
    	   }
       }
       
       if (intType == 0) {
    	   for (int i = 0; i < holePoints.size(); i++) {
    		   int x1 = (int) (holePoints.get(i).x * xScale + padding + labelPadding);
    		   int y1 = (int) ((maxValueY - holePoints.get(i).y) * yScale + padding);
    		   graphHolePoints.add(new Point(x1, y1));
    	   }
    	   System.out.println("HOLES IN NEW POINT: " + graphHolePoints);
       }
       
        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);
        
        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (xArray.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((minValueY + (maxValueY - minValueY) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
        
        // and for x axis      
       
        for (int i = 0; i < numberYDivisions + 1; i++) 
        {
            if (xArray.size() > 1) 
            {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / numberYDivisions + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = ((int) ((minValueX + (maxValueX - minValueX) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";

                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        Stroke oldFunctionStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        
        for (int i = 0; i < graphFunctionPoints.size() - 1; i++) 
        {
            int x1 = graphFunctionPoints.get(i).x;
            int y1 = graphFunctionPoints.get(i).y;
            int x2 = graphFunctionPoints.get(i + 1).x;
            int y2 = graphFunctionPoints.get(i + 1).y;
            
            boolean acrossAsymptotes = false;
            
            if (intType == 0)
            {          
	            for (int j = 0; j < asymptotePoints.size(); j++)
	            {
	            	//System.out.println(asymptotePoints.get(j) + " asymptotePoints.get(j) ");
	            	
	            	if (((double)i -asymptotePoints.get(j)) * ((double)(i+1) -asymptotePoints.get(j))  <= 0)
	            	{
	            		acrossAsymptotes = true;          		
	            	}
	            }
            }
            
            if (acrossAsymptotes == false)
            {
            	g2.drawLine(x1, y1, x2, y2);
            }
            else {
            	//g2.fillOval
            }
        }
        
        //Stroke oldDerivativeStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        
        g2.setStroke(oldFunctionStroke);
        
        //draws points
      
        g2.setColor(pointColor);
        for (int i = 0; i < graphFunctionPoints.size(); i++) 
        	{
            	int x = graphFunctionPoints.get(i).x - pointWidth / 2;
            	int y = graphFunctionPoints.get(i).y - pointWidth / 2;
//            	int y2 = graphDerivativePoints.get(i).y - pointWidth / 2;
//            	int y3 = graphSecondDerivativePoints.get(i).y - pointWidth / 2;
            	int ovalW = pointWidth;
            	int ovalH = pointWidth;
            	g2.fillOval(x, y, ovalW, ovalH);
//            	g2.fillOval(x, y2, ovalW, ovalH);
//            	g2.fillOval(x, y3, ovalW, ovalH);
        	}
        
        if (intType == 0)
        {
        	g2.setColor(maxMinColor);
        	for (int i = 0; i < graphDerivativeZeroes.size(); i++) 
        	{
        		int x = graphDerivativeZeroes.get(i).x - pointWidth / 2;
        		int y = graphDerivativeZeroes.get(i).y - pointWidth / 2;
        		int ovalW = specialPointWidth;
        		int ovalH = specialPointWidth;
        		g2.fillOval(x, y, ovalW, ovalH);
        	}
        }
        
        if (intType == 0)
        {
        	g2.setColor(pointsOfInflectionColor);
        	for (int i = 0; i < graphSecondDerivativeZeroes.size(); i++) 
        	{
        		int x = graphSecondDerivativeZeroes.get(i).x - pointWidth / 2;
        		int y = graphSecondDerivativeZeroes.get(i).y - pointWidth / 2;
        		int ovalW = specialPointWidth;
        		int ovalH = specialPointWidth;
        		g2.fillOval(x, y, ovalW, ovalH);
        		System.out.println("INFLECTION X POINT: " + x);
            	System.out.println("INFLECTION Y POINT: " + y);
        	}
        }
        
        if (intType == 0)
        {
        	g2.setColor(concaveUpColor);
        	for (int i = 0; i < graphSecondDerivativePositivePoints.size(); i++) 
        	{
        		int x = graphSecondDerivativePositivePoints.get(i).x - pointWidth / 2;
        		int y = graphSecondDerivativePositivePoints.get(i).y - pointWidth / 2;
        		int ovalW = pointWidth;
            	int ovalH = pointWidth;
            	g2.fillOval(x, y, ovalW, ovalH);
        	}
        }
        
        if (intType == 0)
        {
        	g2.setColor(concaveDownColor);
        	for (int i = 0; i < graphSecondDerivativeNegativePoints.size(); i++) 
        	{
        		int x = graphSecondDerivativeNegativePoints.get(i).x - pointWidth / 2;
        		int y = graphSecondDerivativeNegativePoints.get(i).y - pointWidth / 2;
        		int ovalW = pointWidth;
        		int ovalH = pointWidth;
        		g2.fillOval(x, y, ovalW, ovalH);
        	}
        }
        
        if (intType == 0) {
        	g2.setColor(holeColor);
        	for (int i = 0; i < holePoints.size(); i++) {
        		int x = graphHolePoints.get(i).x - pointWidth / 2;
        		int y = graphHolePoints.get(i).y - pointWidth / 2;
        		int ovalW = specialPointWidth;
        		int ovalH = specialPointWidth;
        		g2.fillOval(x, y, ovalW, ovalH);
        		System.out.println("HOLE X POINT: " + x);
            	System.out.println("HOLE Y POINT: " + y);
        	}
        }
        
        if (intType == 0)
        {
        	
        	g2.setColor(asymptoteColor);
        	for (int i = 0; i < graphAsymptoteXPosition.size(); i=i+2)
        	{
        		
        		int x1 = graphAsymptoteXPosition.get(i).x - pointWidth / 2;
        		int y1 = graphAsymptoteXPosition.get(i).y - pointWidth / 2;
        		int x2 = graphAsymptoteXPosition.get(i+1).x - pointWidth / 2;
        		int y2 = graphAsymptoteXPosition.get(i+1).y - pointWidth / 2;
        		g2.drawLine(x1, y1, x2, y2);
      	   	}
        	
        }
    }

//    @Override
   
    private double getMinArrayValue(List<Double> function) 
    	{
        double minValue = Double.MAX_VALUE;
        for (Double value : function) 
        {
        	minValue = Math.min(minValue, value);
        }
        return minValue;
    }

    private double getMaxArrayValue(List<Double> function) {
        double maxValue = Double.MIN_VALUE;
        for (Double value : function) {
            maxValue = Math.max(maxValue, value);
        }
        return maxValue;
    }

    public void setFunction(List<Double> yArray) {
        this.yArray = yArray;
        invalidate();
        this.repaint();
    }
   
    public List<Double> yArray() {
        return yArray;
    }

   public static JTextArea area = new JTextArea();
   public static JTextArea maxArea = new JTextArea();
   public static JTextArea minArea = new JTextArea();
   public static JTextArea scaleArea = new JTextArea();
   public static Function_Calculation functionData = new Function_Calculation();
   public static Graphing_View mainPanel = new Graphing_View(0, functionData); // draw original function
   public static Graphing_View mainPanel_1 = new Graphing_View(1, functionData); // draw derivative function
   public static Graphing_View mainPanel_2 = new Graphing_View(2, functionData); // draw second derivative function

   private static void createAndShowGui() {      
        mainPanel.setPreferredSize(new Dimension(800, 600));
        mainPanel_1.setPreferredSize(new Dimension(800, 600));
        mainPanel_2.setPreferredSize(new Dimension(800, 600));
        
        JFrame frame = new JFrame("Function");
        JFrame frame_1 = new JFrame("Derivative");
        JFrame frame_2 = new JFrame("Second Derivative");
        JButton button = new JButton();
        button.setText("Graph");
        area.setPreferredSize(new Dimension(200,20));
        area.setText("x*x*x");
        button.addActionListener(new Graphing_View(0, functionData).new listener());
        mainPanel.add(button);
        mainPanel.add(area);
        minArea.setPreferredSize(new Dimension(100,20));
        minArea.setText("-20");
        maxArea.setPreferredSize(new Dimension(100,20));
        maxArea.setText("20");
        scaleArea.setPreferredSize(new Dimension(100,20));
        scaleArea.setText("0.05");
        mainPanel.add(minArea);
        mainPanel.add(maxArea);
        mainPanel.add(scaleArea);
        
        button.addActionListener(new Graphing_View(0, functionData).new listener());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame_1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_1.getContentPane().add(mainPanel_1);
        frame_1.pack();
        frame_1.setLocationRelativeTo(null);
        frame_1.setVisible(true);
 
        frame_2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_2.getContentPane().add(mainPanel_2);
        frame_2.pack();
        frame_2.setLocationRelativeTo(null);
        frame_2.setVisible(true);

   }  
   class listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String f = area.getText();
			System.out.println("FUNCTION: " + f);
			//Replace Math.sin, Math.cos, etc. with sin and cos notation to make more user friendly
			f = f.replaceAll("sin", "Math.sin");
			f = f.replaceAll("cos", "Math.cos");
			f = f.replaceAll("tan", "Math.tan");
			f = f.replaceAll("arcsin", "Math.asin");
			f = f.replaceAll("arccos", "Math.acos");
			f = f.replaceAll("arctan", "Math.atan");
			f = f.replaceAll("log", "Math.log");
			f = f.replaceAll("ln", "Math.log");
			if (f.contains("|")) {
				String function = f.replace("|", "");
				f = "Math.abs(" + function;
				f = f + ")";
			}
			System.out.println("POST FUNCTION: " + f);
			String minValue = minArea.getText();
			String maxValue = maxArea.getText();
			String scaleValue = scaleArea.getText();
			//Transfer UI info to Function_Calculation instance variable properties 
			functionData.function = f;
			functionData.minValue = Double.parseDouble(minValue);
			functionData.maxValue = Double.parseDouble(maxValue);
			functionData.gap = Double.parseDouble(scaleValue);
			functionData.reComputeData();
		
			mainPanel.repaint();
			mainPanel_1.repaint();
			mainPanel_2.repaint();
		}
   	
   }
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGui();
         }
      });
   }
}