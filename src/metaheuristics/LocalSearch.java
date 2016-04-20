package metaheuristics;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.INeighExplorator;
import problems.ISolution;
import util.config.IConfiguration;

public class LocalSearch extends AbstractAlgorithm 
{
	INeighExplorator explorer;
	
	@Override
	public void search() {
		// Starting solution
		ISolution newSolution = generator.generate();
		ISolution neighbour = newSolution;
		
//		System.out.println("Solucion inicial:");
//		newSolution.printSolution();

		for(int i=0; i<tries; i++) {
			// Generate the neighbor
			neighbour = explorer.generateBestNeighbour(newSolution);
		
			if(neighbour != null) {
				stopwatch.lap();
				bestSolutions.add(neighbour);
//				System.out.println("Solucion mejorada en la iteracion "+ i + ":");
//				System.out.println("The individual is:");
//				newSolution.printSolution();
//				System.out.println("The neighbor is:");
//				neighbour.printSolution();
//				System.out.println();
				newSolution = neighbour;
			}
			else {
//				System.out.println("Not better solution found\n");
				break;
			}
			
			if(limitedTime && stopwatch.currentElapsed() >= maxTime){
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
		this.tries = configuration.getInt("tries");
		
		try {
			// Get the name of the explorer class
			String instanceName = configuration.getString("explorer[@name]");
			//System.out.println(instanceName);
			// Instance class
			Class<? extends INeighExplorator> instanceClass = 
					(Class<? extends INeighExplorator>) Class.forName(instanceName);
			
			explorer = instanceClass.newInstance();
			explorer.setInstance(instance);
			
			if(explorer instanceof IConfiguration)
				((IConfiguration) explorer).configure(configuration.subset("explorer"));
		}
		catch(Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}	
}
