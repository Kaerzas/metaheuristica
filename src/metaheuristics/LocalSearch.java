package metaheuristics;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.INeighExplorator;
import problems.ISolution;
import util.config.IConfiguration;

public class LocalSearch extends AbstractAlgorithm 
{
	INeighExplorator explorator;
	
	@Override
	public void execute() {
		// Generate some random individuals
		stopwatch.start();

		for(int i=0; i<tries; i++) {
			// Create new solution and store
			ISolution newSolution = generator.generate();
			// Evaluate the new solution
			instance.evaluate(newSolution);
			// Generate the neighbour
			ISolution neighbour = explorator.generateBestNeighbour(newSolution);
			
			System.out.println("Iteration " + i);
			
			if(neighbour != null) {
				System.out.println("The individual is:");
				newSolution.printSolution();
				System.out.println("The neighbour is:");
				neighbour.printSolution();
				System.out.println();
			}
			else {
				System.out.println("Not better solution found\n");
			}
		}	
		stopwatch.stop();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Configuration configuration)
	{
		//Standard configuration
		super.configure(configuration);
		
		// Number of iterations
		this.tries = configuration.getInt("tries");
		
		try {
			// Get the name of the explorator class
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