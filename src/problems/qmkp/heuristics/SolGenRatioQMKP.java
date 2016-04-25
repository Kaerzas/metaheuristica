package problems.qmkp.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolGenRandQMKP;
import problems.qmkp.SolutionQMKP;

public class SolGenRatioQMKP extends SolGenRandQMKP {

	private double percentCandidates;
	private int nObjectsSelection;
	
	
	public ISolution generate()
	{	
		// Get the information
		int nObjects = ((InstanceQMKP)instance).getNObjects();
		int nKnapsacks = ((InstanceQMKP)instance).getNKnapsacks();
				
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
		if(percentCandidates > 0.0 || percentCandidates < 1.0)
			this.nObjectsSelection = (int) (nObjects*percentCandidates);
		else{
			System.err.println("Incorrect percent CL");
			System.exit(0);
		}
		
		// Check if the number of candidates is correct
		if(nObjectsSelection < nKnapsacks) {
			System.out.println("You should set more candidates");
			System.exit(0);
		}
		
		// Get the random candidates
		List<Integer> listObj = new ArrayList<Integer>();
		for(int i=0; i<nObjects; i++)
			listObj.add(i);
		Collections.shuffle(listObj, instance.getRandom());
		listObj = listObj.subList(0, nObjectsSelection);
		
		// Put the first objects
		for(int i=0; i<nKnapsacks; i++) {
			// Get the index of the object
			int idxObj = listObj.get(listObj.size()-1);
			// Insert the last object in the knapsack 'i'
			sol.getObjects()[idxObj] = i;
			// Sum the weight
			weights[i] = ((InstanceQMKP)instance).getObjects().get(idxObj).getWeight();
			// Sum the fitness
			fitness += ((InstanceQMKP)instance).getObjects().get(idxObj).getValue();
			// Remove the last object
			listObj.remove(listObj.size()-1);
		}
		
		// Put the other objects
		while(!listObj.isEmpty()) {
			
			// Index to the best object to add to the best knapsack
			int bestObj = -1;
			int bestKP = -1;
			double bestValue = 0;
			double auxValue = 0;
			int removeIdx = 0;
			
			// See which object add
			for(int i=0; i<listObj.size(); i++) {
				for(int j=0; j<nKnapsacks; j++) {
					int auxWeight = ((InstanceQMKP)instance).getObjects().get(listObj.get(i)).getWeight();
					if(auxWeight + weights[j] <= ((InstanceQMKP)instance).getKnapsackSize()) {
						auxValue = ((InstanceQMKP)instance).getIncrememtalValue(sol, listObj.get(i), j)/((InstanceQMKP)instance).getObjects().get(listObj.get(i)).getWeight();
						if(auxValue > bestValue) {
							bestObj = listObj.get(i);
							bestKP = j;
							bestValue = auxValue;
							removeIdx = i;
						}
					}
				}
			}
			
			if(bestObj != -1) {
				// Insert the best object in the best knapsack
				sol.getObjects()[bestObj] = bestKP;
				weights[bestKP] += ((InstanceQMKP)instance).getObjects().get(bestObj).getWeight();
				fitness += bestValue * ((InstanceQMKP)instance).getObjects().get(bestObj).getWeight();
			}
			
			// Remove the object from candidate list
			listObj.remove(removeIdx);
		}
		
		// Set the weight and the fitness to the solution
		sol.setTotalWeight(weights);
		
		// Check if the knapsacks are exceded
		int exceded = 0;
		// Sum the weight o
		for(int i=0; i<sol.getTotalWeight().length; i++) {
			if(sol.getTotalWeight()[i] > ((InstanceQMKP)instance).getKnapsackSize())
				exceded += ((InstanceQMKP)instance).getKnapsackSize() - sol.getTotalWeight()[i] ;
		}
		
		// At least one bag is exceded
		if(exceded<0)
			sol.setFitness(exceded);
		// The solution is valid
		else
			sol.setFitness(fitness);
				
		return sol;
	}
	
	public void configure(Configuration configuration) 
	{
		// Super class configure method		
		super.configure(configuration);
		this.percentCandidates = configuration.getDouble("percentCandidates");
	}
}
