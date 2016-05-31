package metaheuristics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import metaheuristics.localsearch.BISNeighExplorator;
import metaheuristics.localsearch.INeighExplorator;
import metaheuristics.localsearch.operator.ChangeObjectQMKP;
import metaheuristics.localsearch.operator.SwapObjQMKP;
import problems.ISolution;

public class VND extends AbstractAlgorithm 
{
	
	/** Number of iterations */
	protected int tries;
	
	INeighExplorator explorer;
	
	ChangeObjectQMKP opChangeObjectQMKP;
	
	SwapObjQMKP opSwapObjQMKP;
	
	int resets;
	
	public VND () {
		this.explorer = new BISNeighExplorator();
		this.explorer.setInstance(instance);
				
		opChangeObjectQMKP = new ChangeObjectQMKP();
		opSwapObjQMKP = new SwapObjQMKP();
		
	}
	
	@Override
	public void search() {
		
		int resetCounter = 0;

		
		while(!maxTimeReached()){
			
			int op = 1;

			
			// Starting solution			
			ISolution newSolution = generator.generate();
			ISolution neighbour = newSolution;
			
			//System.out.println("Solucion inicial:");
			//newSolution.printSolution();
			//System.out.println();
			
			while(!maxTimeReached() && op <=2) {
				// Generate the neighbor
				if(op == 1) {
					opChangeObjectQMKP.initialize(instance, newSolution);
					explorer.setOperator(opChangeObjectQMKP);
				}
				else if(op == 2) {
					opSwapObjQMKP.initialize(instance, newSolution);
					explorer.setOperator(opSwapObjQMKP);
				}
					
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
					op = 1;
				}
				else {
					op++;
					//System.out.println(op);
				}
			}
			resetCounter++;
		}
		resets = resetCounter;
	}

	@Override
	public void configure(Configuration configuration)
	{
		//Standard configuration
		super.configure(configuration);		
		
		explorer.setInstance(instance);
		
		//Get algorithm params to header
		String algorithm = getClass().getName().split("\\.")[1];
		String instance  = (configuration.subset("instance").getString("data")).split("/")[2];
		String explorer  = "FISNeighExplorator";
		String operator1  = "ChangeObjectQMKP";
		String operator2  = "SwapObjQMKP";
		
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
		this.header += "\n# Explorer: " + explorer + "\tOperators: " + operator1 + " and " + operator2;
		this.header += solGenerator;
	}

	public int getResets() {
		return resets;
	}	
}
