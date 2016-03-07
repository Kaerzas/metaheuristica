package metaheuristics;

import java.util.ArrayList;

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
	public void execute() 
	{
		// Create, evaluate and show the solutions
		ISolution bestSolution = null;
		bestSolutions = new ArrayList<ISolution>();
		
		// Try some individuals
		stopwatch.start();

		for(int i=0; i<tries; i++) {
			
			// Create new solution and store
			ISolution newSolution = generator.generate();
				
			// Evaluate the new solution
			instance.evaluate(newSolution);
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				//Calculate time since last solution/beginning
				stopwatch.lap();
				
				bestSolutions.add(newSolution);
				bestSolution = newSolution;
				// Show results
				//System.out.println("A best solution has been found in the iteration " + i + " after " + stopwatch.lapTime(lastLap) + " ns:");
				//bestSolution.printSolution();
				//System.out.println();
			}
		}
		stopwatch.stop();
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