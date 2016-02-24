package problems.knapsack;

import java.util.Random;

import problems.AbstractSolGenerator;
import problems.ISolution;

public class SolGeneratorKnapsack extends AbstractSolGenerator 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Number of objects for the problem */
	
	int nObjects;
	
	/** Random generator */
	
	Random randGenerator;
	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/** Empty constructor */
	
	public SolGeneratorKnapsack() 
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Create a knapsack solution generator given 
	 * the number of objects
	 * 
	 * @param nObject number of objects
	 * 
	 */
	
	public SolGeneratorKnapsack(int nObjects) 
	{
		this.nObjects = nObjects;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Set the seed and initialize the random generator
	 * 
	 * @param seed the seed to ser
	 */
	
	public void setSeed(int seed) 
	{
		this.seed = seed;
		randGenerator = new Random(seed);
	}
	
	@Override
	public ISolution generate() 
	{
		
		byte [] knapsack = new byte[nObjects];
		
		for(int i=0; i<nObjects; i++) {
			if(randGenerator.nextDouble() < 0.5)
				knapsack[i] = 0;
			else
				knapsack[i] = 1;
		}
				
		return new SolutionKnapsack(knapsack);
	}

	/**
	 * Get the number of objects
	 * 
	 * @return the nObjects
	 */
	
	public int getnObjects() 
	{
		return nObjects;
	}

	/**
	 * Set the number of objects
	 * 
	 * @param nObjects the nObjects to set
	 */
	
	public void setnObjects(int nObjects) 
	{
		this.nObjects = nObjects;
	}
	
}
