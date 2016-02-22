package problems.knapsack;

import java.util.List;

import problems.IInstance;
import problems.ISolution;

public class InstanceKnapsack implements IInstance 
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
	
	//////////////////////////////////////////////
	// ------------------------------ Constructor
	/////////////////////////////////////////////
	
	/** Empty constructor */
	
	public InstanceKnapsack() 
	{
		// TODO Auto-generated constructor stub
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	@Override
	public void evaluate(ISolution solution) 
	{
		double value = 0;		
		double weight = 0;
		
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
			// Check if the solution is valid
			if(weight > knapsackSize)
				solution.setFitness(0);
			else
				solution.setFitness(value);	
		}
		else {
			System.err.println("Solution must be a SolutionKnapsack instance");
			System.exit(0);			
		}
	}

	@Override
	public IInstance loadInstance(String fileName) 
	{
		// TODO Auto-generated method stub
		return null;
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
	
}