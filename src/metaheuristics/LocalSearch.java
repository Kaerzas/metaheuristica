package metaheuristics;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.INeighExplorator;
import problems.ISolution;
import util.config.IConfiguration;

public class LocalSearch extends AbstractAlgorithm 
{
	
	/** Number of iterations */
	protected int tries;
	
	INeighExplorator explorer;
	
	@Override
	public void search() {
		// Starting solution
		ISolution newSolution = generator.generate();
		ISolution neighbour = newSolution;
		
		//System.out.println("Solucion inicial:");
		//newSolution.printSolution();
		//System.out.println();
		
		for(int i=0; (i<tries) && (!maxTimeReached()); i++) {
			// Generate the neighbor
			neighbour = explorer.generateBestNeighbour(newSolution);			
			
			if(neighbour != null) {
				logSolution(neighbour);
				/*System.out.println("Solucion mejorada en la iteracion "+ i + ":");
				System.out.println("The individual is:");
				newSolution.printSolution();
				System.out.println("The neighbor is:");
				neighbour.printSolution();
				System.out.println();*/
				newSolution = neighbour;
			}
			else {
				//System.out.println("Not better solution found\n");
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
		
		// Execution time
		if(configuration.containsKey("tries"))
			this.tries = configuration.getInt("tries");
		else
			this.tries = Integer.MAX_VALUE;
		
		try {
			// Get the name of the explorer class
			String instanceName = configuration.getString("explorer[@name]");
			// Instance class
			Class<? extends INeighExplorator> instanceClass = 
					(Class<? extends INeighExplorator>) Class.forName(instanceName);
			
			explorer = instanceClass.newInstance();
			explorer.setInstance(instance);
			

			
			if(explorer instanceof IConfiguration)
				((IConfiguration) explorer).configure(configuration.subset("explorer"));
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
		
		String generator = configuration.getString("solGenerator[@name]");
		String solGenerator = null;
		
		switch(generator.split("\\.")[2]){
		
			case "SolGenRandomKP":
				solGenerator = "\n# Generator: SolGenRandomKP\tprob: " + configuration.subset("solGenerator").getDouble("probability");
				break;
				
			case "SolGenRandQMKP":
				solGenerator = "\n# Generator: SolGenRandQMKP\tprob: " + configuration.subset("solGenerator").getDouble("probability");
				break;
				
			case "SolGenRandomTSP":
				solGenerator = "\n# Generator: SolGenRandomTSP";
				break;
			
			case "heuristics":
				
				switch(generator.split("\\.")[3]){
				
					case "SolGenGreedyRand":
						solGenerator = "\n# Generator: SolGenGreedyRand\tPercentCandidates: " + configuration.subset("solGenerator").getDouble("percentCandidates");
						algorithm = "GRASP";
						break;
					case "SolGenRatioKP":
						solGenerator = "\n# Generator: SolGenRatioKP\tprobability: " + configuration.subset("solGenerator").getDouble("probability");
						solGenerator += "\n# PercentCandidates: " + configuration.subset("solGenerator").getDouble("percentCandidates") + "\tPercentStartCapacity: " + configuration.subset("solGenerator").getDouble("percentStartCapacity");
						algorithm = "GRASP";
						break;
					case "SolGenRatioQMKP":
						solGenerator = "\n# Generator: SolGenRatioQMKP\tprobability: " + configuration.subset("solGenerator").getDouble("probability") + "\tPercentCandidates: " + configuration.subset("solGenerator").getDouble("percentCandidates");
						algorithm = "GRASP";
						break;
				}
				break;
		}
		
		this.header = "# " + instance;
		this.header += "\n# Algorithm: " + algorithm;
		this.header += "\n# Tries: " + this.tries;
		this.header += "\n# Explorer: " + explorer + "\tOperator: " + operator;
		this.header += solGenerator;
	}	
}
