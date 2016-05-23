
package problems.qmkp;

import java.util.ArrayList;
import java.util.List;

import problems.AbstractSolution;

public class SolutionQMKP extends AbstractSolution 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////	
	
	/** Array representing the objects in the different knapsacks */
	
	int [] objects;
	
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
	 */
	
	public SolutionQMKP(int [] objects) 
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
	
	public int [] getObjects() 
	{
		return objects;
	}
	
	/**
	 * Set the object in the knapsack 
	 * 
	 * @param objects the objects
	 */
	
	public void setObjects(int[] objects) 
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
		System.out.print("[");
		for(int i=0; i<objects.length; i++) {
			if(objects[i]!=-1)
				System.out.print(objects[i]+",");
			else
				System.out.print("_,");
		}
		System.out.println("]");
		System.out.println("Fitness:" + getFitness());
	}

	public List<Integer> getObjectsInBag(int bag) {
		List <Integer> obj = new ArrayList<>();
		
		for(int i=0; i<objects.length; i++) {
			if(objects[i] == bag)
				obj.add(i);
		}
		
		return obj;
		//return ArrayUtils.toPrimitive(obj.toArray(new Integer[obj.size()]));
	}
}