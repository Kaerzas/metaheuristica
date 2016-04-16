package metaheuristics;

import org.apache.commons.configuration.Configuration;

import problems.IInstance;
import problems.ISolution;

/**
 * Class representing the random search algorithm
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class RandomSearch extends AbstractAlgorithm 
{
	////////////////////////////////////////////////
	// ---------------------------------- Variables
	///////////////////////////////////////////////
	
	/**
	 * Number of tries for the Random Search
	 */
	private int tries;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public void search() 
	{
		// Create, evaluate and show the solutions
		ISolution bestSolution = null;
		
		// Try some individuals
		for(int i=0; i<tries; i++) {
			
			// Create new solution and store
			ISolution newSolution = generator.generate();
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				//Calculate time since last solution/beginning
				stopwatch.lap();
				
				bestSolutions.add(newSolution);
				bestSolution = newSolution;
				// Show results
				System.out.println("A best solution has been found in the iteration " + i + " ");
				bestSolution.printSolution();
				System.out.println();
			}
		}
	}
	
	@Override
	public void configure(Configuration configuration)
	{
		//Standard configuration
		super.configure(configuration);
		
		//Metaheuristic specific configuration
		this.tries = configuration.getInt("tries");
	}

	@Override
	public IInstance getInstance() {
		return instance;
	}
}