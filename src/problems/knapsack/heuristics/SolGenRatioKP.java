package problems.knapsack.heuristics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.KPObject;
import problems.knapsack.SolGenRandomKP;
import problems.knapsack.SolutionKnapsack;

public class SolGenRatioKP extends SolGenRandomKP 
{

	/** Number of objects for doing the selection **/

	private int nObjectsSelection;
	private double startCapacity;
	float percentCandidates, percentCapacity;
	
	public ISolution generate(){		
		// Get the object number for the problem
		int nObjects = ((InstanceKnapsack)instance).getNObjects();
		if(percentCandidates > 0.0 || percentCandidates < 1.0)
			this.nObjectsSelection = (int) (nObjects*percentCandidates);
		else{
			System.err.println("Incorrect percent CL");
			System.exit(0);
		}
		if(percentCapacity > 0)
			this.startCapacity = ((InstanceKnapsack)instance).getKnapsackSize()*percentCapacity;
		else{
			System.out.println("Knapsack capacity to fill must be greater than 0");
			System.exit(-1);
		}
			
		
		// Initialize the knapsack and the weight
		byte [] knapsack = new byte[nObjects];		
		int totalWeight = 0;
		
		// Create an array with the best elements sorted by ratio
		List<KPObject> sorted = new ArrayList<KPObject>(((InstanceKnapsack)instance).getObjects());		
		Collections.sort(sorted, new ComparatorRatioKP());
		
		List<KPObject> aux = new ArrayList<KPObject>(sorted.subList(0, nObjectsSelection));
		sorted.removeAll(aux);
		
		//Fill until the knapsack is filled or there aren't more candidates
		while(totalWeight < startCapacity && aux.size() > 0){
			int candidateIndex = randGenerator.nextInt(aux.size());
			KPObject o = aux.get(candidateIndex);
			if(totalWeight+o.getWeight() > startCapacity) // Don't add if it overflows the knapsack
				break;
			
			int index = ((InstanceKnapsack)instance).getObjects().indexOf(o);
			knapsack[index] = 1;
			
			//Remove candidate and insert new one
			aux.remove(candidateIndex);
			if(sorted.size() > 0){
				aux.add(sorted.get(0));
				sorted.remove(0);
			}
			
			totalWeight += o.getWeight();
		}
		
		SolutionKnapsack sol = new SolutionKnapsack(knapsack);
		sol.setTotalWeight(totalWeight);
		instance.evaluate(sol);
		System.out.println(sol.getFitness());
		return sol;
	}
	
	@Override
	public void configure(Configuration configuration) {
		// Super class configure method		
		super.configure(configuration);
		this.percentCandidates = configuration.getFloat("percentCandidates");
		this.percentCapacity = configuration.getFloat("percentStartCapacity");
	}	
}
