package problems.qmkp;

import problems.AbstractSolution;

public class SolutionQMKP extends AbstractSolution 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////	
	
	/** Array representing the objects in the different 
	 * knapsacks, where the columns represents the knapsacks 
	 * and the row the objects 
	 **/
	
	// TODO revisar que se inicializa a 0 (creo que si)
	byte [][] objects;
	
	/** Array representing the total weight in the knapsacks */
	
	int [] totalWeight;
	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////

	/** Empty constructor */
	
	public SolutionQMKP() 
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
	
	public SolutionQMKP(byte [][] objects) 
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
	
	public byte[][] getObjects() 
	{
		return objects;
	}
	
	/**
	 * Set the object in the knapsack 
	 * 
	 * @param objects the objects
	 */
	
	public void setObjects(byte[][] objects) 
	{
		this.objects = objects;
	}	

	/**
	 * Get the total weight in all the knapsack
	 * 
	 * @return The total weight
	 */
	
	public int [] getTotalWeight() 
	{
		return totalWeight;
	}

	/**
	 * Get the total weight in all the knapsack
	 * 
	 * @param totalWeight All knapsacks weight
	 */
	
	public void setTotalWeight(int [] totalWeight) 
	{
		this.totalWeight = totalWeight;
	}
	
	@Override
	public void printSolution() 
	{
		// TODO revisar porque puede que falle
		
		for(int i=0; i<objects[0].length; i++) {
			System.out.println("Knapsack " + i + ":");
			System.out.print("[");
			// Read the columns (objects)
			for (int j=0; j< objects.length; j++) {
				System.out.print(objects[j][i]+",");
			}
			System.out.println("]");
		}
		System.out.println("Fitness:" + getFitness());
	}
	
}