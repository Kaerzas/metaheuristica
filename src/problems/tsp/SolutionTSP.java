package problems.tsp;

import java.util.List;

import problems.AbstractSolution;

/**
 * Class representing the solution for the knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class SolutionTSP extends AbstractSolution 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Order we visit the nodes */
	
	private List<Integer> order;

	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/**
	 * Empty constructor
	 */
	
	public SolutionTSP(){
		
	}
		
	/**
	 * Constructor that sets the order
	 * 
	 * @param order the node permutation
	 */
	
	public SolutionTSP(List<Integer> order)
	{
		this.order = order;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public void printSolution() 
	{
		System.out.println(order);
		System.out.println("Fitness:" + getFitness());
	}
	
	/**
	 * Get the order we visit the nodes
	 * 
	 * @return permutation representing the order
	 */
	
	public List<Integer> getOrder()
	{
		return order;
	}
}