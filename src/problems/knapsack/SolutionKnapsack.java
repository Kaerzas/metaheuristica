package problems.knapsack;

import problems.AbstractSolution;

/**
 * Solution class for the knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class SolutionKnapsack extends AbstractSolution
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////	
	
	/** Array representing the objects in the knapsack */
	
	byte [] objects;
	int totalWeight;
	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////

	/** Empty constructor */
	
	public SolutionKnapsack() 
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Create a knapsack solution given the objects in
	 * the knapsack
	 * 
	 * @param objects objects in the knapsack
	 * 
	 */
	
	public SolutionKnapsack(byte [] objects) 
	{
		this.objects = objects;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Get the object in the knapsack 
	 * 
	 * @return the objects
	 */
	
	public byte[] getObjects() 
	{
		return objects;
	}

	/**
	 * Set the object in the knapsack 
	 * 
	 * @param objects the objects
	 */
	
	public void setObjects(byte[] objects) 
	{
		this.objects = objects;
	}
	
	public int getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}

	@Override
	public void printSolution() 
	{
		System.out.print("[");
		for (int i=0; i< objects.length; i++) {
			System.out.print(objects[i]+",");
		}
		System.out.println("]");
		System.out.println("Fitness:" + getFitness());
	}
}
