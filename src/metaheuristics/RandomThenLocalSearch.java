package metaheuristics;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.INeighExplorator;
import problems.ISolution;
import util.config.IConfiguration;

public class RandomThenLocalSearch extends AbstractAlgorithm 
{
	INeighExplorator explorator;
	private int randomTries;
	private int localTries;
	
	@Override
	public void search() {
		ISolution bestSolution = null;
		ISolution newSolution;
		
		//Random search
		for(int i=0; i<randomTries; i++) {
			// Create new solution and store
			newSolution = generator.generate();
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				//Calculate time since last solution/beginning
				stopwatch.lap();
				
				bestSolutions.add(newSolution);
				bestSolution = newSolution;
				// Show results
//				System.out.println("A best solution has been found in the iteration " + i + " after " + stopwatch.lapTime(i) + " ns:");
//				bestSolution.printSolution();
//				System.out.println();
			}
		}
		
		//Start local search from Random Search best solution
		newSolution = bestSolution;
		System.out.println("Starting local search at " + stopwatch.currentElapsed() + " ns");
		
		for(int i=0; i<localTries; i++) {
			// Generate the neighbor
			ISolution neighbour = explorator.generateBestNeighbour(newSolution);
			
			//System.out.println("Iteration " + i);
			
			if(neighbour != null) {
				stopwatch.lap();
				bestSolutions.add(newSolution);
				/*System.out.println("The individual is:");
				newSolution.printSolution();
				System.out.println("The neighbor is:");
				neighbour.printSolution();
				System.out.println();*/
				newSolution = neighbour;
				//System.out.println("Better solution found at iteration " + i + ": "+newSolution.getFitness());
			}
			else {
				System.out.println("No better solution found, iteration " + i);
				break;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Configuration configuration)
	{
		//Standard configuration
		super.configure(configuration);
		
		// Number of iterations
		this.randomTries = configuration.getInt("randomTries");
		this.localTries = configuration.getInt("localTries");
		
		try {
			// Get the name of the explorer class
			String instanceName = configuration.getString("explorator[@name]");
			
			// Instance class
			Class<? extends INeighExplorator> instanceClass = 
					(Class<? extends INeighExplorator>) Class.forName(instanceName);
			
			explorator = instanceClass.newInstance();
			explorator.setInstance(instance);
			
			if(explorator instanceof IConfiguration)
				((IConfiguration) explorator).configure(configuration.subset("explorator"));
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}	
}