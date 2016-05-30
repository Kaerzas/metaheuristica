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
		while(!maxTimeReached()){
			// Create, evaluate and show the solutions
			ISolution bestSolution = null;
			
			// Try some individuals
			for(int i=0; (i<tries) && (!maxTimeReached()); i++) {
				
				// Create new solution and store
				ISolution newSolution = generator.generate();
				
				// Check if the new solution is better
				if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
					//Calculate time since last solution/beginning
					logSolution(newSolution);
					bestSolution = newSolution;
					// Show results
	//				System.out.println("A best solution has been found in the iteration " + i + " ");
	//				bestSolution.printSolution();
	//				System.out.println();
				}
			}
		}
	}
	
	@Override
	public void configure(Configuration configuration)
	{
		//Standard configuration
		super.configure(configuration);
		
		//Metaheuristic specific configuration
		if(configuration.containsKey("tries"))
			this.tries = configuration.getInt("tries");
		else
			this.tries = Integer.MAX_VALUE;
		
		//Get algorithm params to header
		String algorithm = getClass().getName().split("\\.")[1];
		String instance  = (configuration.subset("instance").getString("data")).split("/")[2];
		
		this.header = "# " + instance;
		this.header += "\n# Algorithm: " + algorithm;
		this.header += "\n# Tries: " + this.tries;
		
		String generator = configuration.getString("solGenerator[@name]");

		switch(generator.split("\\.")[2]){
		
			case "SolGenRandomKP":
				this.header += "\n# Generator: SolGenRandomKP\tprob: " + configuration.subset("solGenerator").getDouble("probability");
				break;
				
			case "SolGenRandQMKP":
				this.header += "\n# Generator: SolGenRandQMKP\tprob: " + configuration.subset("solGenerator").getDouble("probability");
				break;
				
			case "SolGenRandomTSP":
				this.header += "\n# Generator: SolGenRandomTSP";
				break;
		}
	}

	@Override
	public IInstance getInstance() {
		return instance;
	}
}