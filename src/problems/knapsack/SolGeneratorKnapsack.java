package problems.knapsack;

import org.apache.commons.configuration.Configuration;

import problems.AbstractSolGenerator;
import problems.ISolution;
import util.config.IConfiguration;

/**
 * Generator class for the knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class SolGeneratorKnapsack extends AbstractSolGenerator implements IConfiguration
{	
	///////////////////////////////////////////
	// ----------------------------- Variables
	//////////////////////////////////////////
	
	/**
	 * Probability of randomly adding an element to the knapsack
	 */
	private double addProbability;
	
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
			if(randGenerator.nextDouble() < addProbability)
				knapsack[i] = 1;
			else
				knapsack[i] = 0;
		}
				
		return new SolutionKnapsack(knapsack);
	}

	@Override
	public void configure(Configuration configuration) {
		// Set probability of adding an element
		this.addProbability = configuration.getDouble("probability");
	}
}