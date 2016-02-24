package problems.knapsack;

import problems.AbstractSolGenerator;
import problems.ISolution;

/**
 * Generator class for the knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class SolGeneratorKnapsack extends AbstractSolGenerator 
{	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/** Empty constructor */
	
	public SolGeneratorKnapsack() 
	{

	}
		
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public ISolution generate() 
	{
		int nObjects = ((InstanceKnapsack)instance).getNObjects();
		
		byte [] knapsack = new byte[nObjects];
		
		for(int i=0; i<nObjects; i++) {
			if(randGenerator.nextDouble() < 0.5)
				knapsack[i] = 0;
			else
				knapsack[i] = 1;
		}
				
		return new SolutionKnapsack(knapsack);
	}
}