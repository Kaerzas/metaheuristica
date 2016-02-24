package metaheuristics;

import java.util.ArrayList;
import java.util.List;

import problems.ISolution;

/**
 * Class representing the random search algorithm
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class RandomSearch extends AbstractAlgorithm 
{
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
		for(int i=0; i<1000; i++) {
			
			// Create new solution and store
			ISolution newSolution = generator.generate();
			solutions.add(newSolution);
				
			// Evaluate the new solution
			instance.evaluate(newSolution);
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				bestSolution = newSolution;
				// Show results
				System.out.println("A best solution has been found in the itration " + i + ":");
				bestSolution.printSolution();
				System.out.println();
			}
		}
	}
}