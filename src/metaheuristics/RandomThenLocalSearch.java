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
		for(int i=0; (i<randomTries) && (!maxTimeReached()); i++) {
			// Create new solution and store
			newSolution = generator.generate();
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				//Calculate time since last solution/beginning
				logSolution(newSolution);
				bestSolution = newSolution;
				// Show results
//				System.out.println("A best solution has been found in the iteration " + i + " after " + stopwatch.lapTime(i) + " ns:");
//				bestSolution.printSolution();
//				System.out.println();
			}
		}
		
		//Start local search from Random Search best solution
		newSolution = bestSolution;
		//System.out.println("Starting local search at " + stopwatch.currentElapsed() + " ns");
		
		for(int i=0; (i<localTries) && (!maxTimeReached()); i++) {
			// Generate the neighbor
			ISolution neighbour = explorator.generateBestNeighbour(newSolution);
			
			//System.out.println("Iteration " + i);
			
			if(neighbour != null) {
				logSolution(neighbour);
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
			String instanceName = configuration.getString("explorer[@name]");
			
			// Instance class
			Class<? extends INeighExplorator> instanceClass = 
					(Class<? extends INeighExplorator>) Class.forName(instanceName);
			
			explorator = instanceClass.newInstance();
			explorator.setInstance(instance);
			
			if(explorator instanceof IConfiguration)
				((IConfiguration) explorator).configure(configuration.subset("explorer"));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//Get algorithm params to header
		String algorithm = getClass().getName().split("\\.")[1];
		String instance  = (configuration.subset("instance").getString("data")).split("/")[2];
		String explorer  = (configuration.getString("explorer[@name]")).split("\\.")[2];
		String operator  = (configuration.subset("explorer").getString("operator")).split("\\.")[3];

		this.header = "# " + instance;
		this.header += "\n# Algorithm: " + algorithm;
		this.header += "\n# Local tries: " + this.randomTries + "\tRandom tries: " + this.localTries;
		this.header += "\n# Explorer: " + explorer + "\tOperator: " + operator;
		
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
}