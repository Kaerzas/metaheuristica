package metaheuristics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

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
	public void execute() 
	{
		// Create, evaluate and show the solutions
		List <ISolution> solutions = new ArrayList<ISolution>();
		ISolution bestSolution = null;
		
		// Create 1000 individuals
		for(int i=0; i<tries; i++) {
			
			// Create new solution and store
			ISolution newSolution = generator.generate();
			solutions.add(newSolution);
				
			// Evaluate the new solution
			instance.evaluate(newSolution);
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				bestSolution = newSolution;
				// Show results
				System.out.println("A best solution has been found in the iteration " + i + ":");
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
}