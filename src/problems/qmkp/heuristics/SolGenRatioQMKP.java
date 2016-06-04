package problems.qmkp.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.Configuration;

import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolGenRandQMKP;
import problems.qmkp.SolutionQMKP;

public class SolGenRatioQMKP extends SolGenRandQMKP {

	private double percentCandidates, percentCapacity, startCapacity;
	private int nObjectsSelection;	
	
	public ISolution generate()
	{	
		// Get the information
		int nObjects = ((InstanceQMKP)instance).getNObjects();
		int nKnapsacks = ((InstanceQMKP)instance).getNKnapsacks();
		
		// Set of added elements
		Set <Integer> added = new HashSet<Integer>();
		
		// Create the objects and the solution
		int [] objects = new int [nObjects];
		// Initialize with -1 (no objects)
		for(int i=0; i<nObjects; i++)
			objects[i] = -1;
		SolutionQMKP sol = new SolutionQMKP(objects);

		// Create the weight array and the fitness
		int [] weights = new int [nKnapsacks];
		double fitness = 0;
		
		// Get the number of candidates
		if(percentCandidates > 0.0 && percentCandidates < 1.0)
			this.nObjectsSelection = (int) (nObjects*percentCandidates);
		else{
			System.err.println("Incorrect percent CL");
			System.exit(0);
		}
		// Check if the number of candidates is correct
		if(nObjectsSelection < nKnapsacks) {
			System.err.println("You should set more candidates");
			System.exit(0);
		}
		
		if(percentCapacity > 0)
			this.startCapacity = ((InstanceQMKP)instance).getKnapsackSize()*percentCapacity;
		else{
			System.err.println("Knapsack capacity to fill must be greater than 0");
			System.exit(0);
		}
		
		// First object in each knapsack is random
		List<Integer> beginning = new ArrayList<Integer>(nObjects);
		for(int i=0; i<nObjects; i++)
			beginning.add(i);
		Collections.shuffle(beginning, instance.getRandom());
		for(int i=0; i<nKnapsacks; ++i){
			int idxObj = beginning.get(i);
			// Insert rand object in the knapsack 'i'
			sol.getObjects()[idxObj] = i;
			// Sum the weight
			weights[i] = ((InstanceQMKP)instance).getObjects().get(idxObj).getWeight();
			// Sum the fitness
			fitness += ((InstanceQMKP)instance).getObjects().get(idxObj).getValue();
			// Remove the last object
			added.add(idxObj);
		}
		
		boolean finished = false;
		
		// Insert the rest of objects
		while(!finished) {
			// Update the candidate list
			List<Integer> candidates = new ArrayList<Integer>(nObjects);
			for(int i=0; i<nObjects; i++)
				candidates.add(i);
			Collections.shuffle(candidates, instance.getRandom());
			candidates.removeAll(added);
			if(candidates.size()>nObjectsSelection)
				candidates = candidates.subList(0, nObjectsSelection);
			
			// Best object index to add to the best knapsack
			int bestObj = -1;
			int bestKP = -1;
			double bestValue = 0;
			
			// Select best object to add to best knapsack
			for(int i=0; i<candidates.size(); i++) {
				for(int j=0; j<nKnapsacks; j++) {
					int objWeight = ((InstanceQMKP)instance).getObjects().get(candidates.get(i)).getWeight();
					if(objWeight + weights[j] <= this.startCapacity){
						// Calculate the ratio = Total knapsack value (with current object) / weight of current object
						double currValue = ((InstanceQMKP)instance).getIncrememtalValue(sol, candidates.get(i), j)/((InstanceQMKP)instance).getObjects().get(candidates.get(i)).getWeight();
						if(currValue > bestValue) {
							bestObj = candidates.get(i);
							bestKP = j;
							bestValue = currValue;
						}
					}
				}
			}
			if(bestObj != -1) {
				// Insert the best object in the best knapsack
				sol.getObjects()[bestObj] = bestKP;
				weights[bestKP] += ((InstanceQMKP)instance).getObjects().get(bestObj).getWeight();
				fitness += bestValue * ((InstanceQMKP)instance).getObjects().get(bestObj).getWeight();
				added.add(bestObj);
			}
			else
				finished = true;
		}
		
		// Set the weight and the fitness in the solution
		sol.setTotalWeight(weights);
		sol.setFitness(fitness);
		
		return sol;
	}
	
	public void configure(Configuration configuration) 
	{
		// Super class configure method		
		super.configure(configuration);
		this.percentCandidates = configuration.getDouble("percentCandidates");
		this.percentCapacity = configuration.getDouble("percentStartCapacity");
	}
}
