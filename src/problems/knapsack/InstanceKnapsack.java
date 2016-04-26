package problems.knapsack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.AbstractInstance;
import problems.ISolution;

/**
 * Instance class for the knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class InstanceKnapsack extends AbstractInstance 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Number of objects for the problem */
	
	protected int nObjects;
	
	/** Size of the knapsack */
	
	protected int knapsackSize;
	
	/** List objects */
	
	protected List <KPObject> objects;
	
	protected double totalValue;
	
	//////////////////////////////////////////////
	// ------------------------------ Constructor
	/////////////////////////////////////////////
	
	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	/**
	 * Constructor that set if it is an max/min problem
	 */
	
	public InstanceKnapsack() 
	{
		// It is a maximization problem
		maximize = true;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	@Override
	public void evaluate(ISolution solution) 
	{
		int value = 0;		
		int weight = 0;
		
		// Check if the solution is an instance of the Knapsack problem
		if(solution instanceof SolutionKnapsack) {
			// Get the objects in the knapsack
			byte knapsack [] = ((SolutionKnapsack)solution).getObjects();
			// Iterate through the knapsack
			for(int i=0; i<knapsack.length; i++) {
				if(knapsack[i] == 1){
					value += this.objects.get(i).getValue();
					weight += this.objects.get(i).getWeight();
				}
			}
			
			((SolutionKnapsack)solution).setTotalWeight(weight);
			// Check if the solution is valid
			if(weight > knapsackSize)
				solution.setFitness(knapsackSize - weight);
			else
				solution.setFitness(value);	
		}
		else {
			System.err.println("Solution must be a SolutionKnapsack instance");
			System.exit(0);			
		}
	}

	@Override
	public void loadInstance() 
	{	
		String line = "";	
		BufferedReader br = bufferedReader;
		
		double totalValue = 0;
		
		try {
			while (!br.readLine().startsWith("knapPI"));
			// Read the number of objects
			line = br.readLine();
			nObjects = Integer.parseInt(line.split(" ")[1]);
			objects = new ArrayList <KPObject>(nObjects);
			// Read the knapsack size
			line = br.readLine();
			knapsackSize = Integer.parseInt(line.split(" ")[1]);
			
			// Ignore two next lines
			br.readLine();
			br.readLine();
			// Read N objects
			for(int i=0; i<nObjects; i++) {
				KPObject obj = new KPObject();
				line = br.readLine();
				String[] objectInformation = line.split(",");
				obj.setValue(Integer.parseInt(objectInformation[1]));
				obj.setWeight(Integer.parseInt(objectInformation[2]));
				objects.add(obj);
				totalValue += obj.getValue();
			}
			
			this.totalValue = totalValue;
		
		} catch (IOException e) {
			System.out.println("Problem while reading the CSV file");
			e.printStackTrace();
		}
		
		//Compute total value of all objects
		totalValue = 0.0;
		for(KPObject obj : objects){
			totalValue += obj.getValue();
		}
	}
	
	/**
	 * Get the number of objects 
	 * 
	 * @return the number of objects
	 */
	
	public int getNObjects() 
	{
		return nObjects;
	}

	/**
	 * Set the number of objects 
	 * 
	 * @param the number of objects
	 */
	
	public void setNObjects(int nObjects) 
	{
		this.nObjects = nObjects;
	}
	
	public int getLength(){
		return this.knapsackSize;
	}
	
	public List <KPObject> getObjects(){
		return this.objects;
	}
	
	public int hamming(ISolution sol1, ISolution sol2){
		byte knapsack1 [] = ((SolutionKnapsack)sol1).getObjects();
		byte knapsack2 [] = ((SolutionKnapsack)sol2).getObjects();
		int distance=0;
		for(int i=0; i<this.getLength(); ++i)
			if(knapsack1[i] != knapsack2[i])
				distance++;
		return distance;
	}
}