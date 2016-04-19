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
		if(percentCapacity != -1)
			this.startCapacity = ((InstanceKnapsack)instance).getKnapsackSize()*percentCapacity;
		else
			this.startCapacity = Math.pow(((InstanceKnapsack)instance).getKnapsackSize(),3);
		//System.out.println("Candidates: "+percentCandidates+" Capacity: "+percentCapacity);
		
		// Initialize the knapsack and the weight
		byte [] knapsack = new byte[nObjects];		
		int totalWeight = 0;
		
		// Create an array with the best elements sorted by ratio
		List<KPObject> aux = new ArrayList<KPObject>(((InstanceKnapsack)instance).getObjects());		
		
		Collections.shuffle(aux, instance.getRandom());			
		aux = aux.subList(0, nObjectsSelection);
		Collections.sort(aux, new ComparatorRatioKP());
		
		
		for(int i=0; i<aux.size(); i++){
			if(totalWeight+aux.get(i).getWeight() > startCapacity){
				System.out.println("Start capacity achieved");
				break;
			}
			int index = ((InstanceKnapsack)instance).getObjects().indexOf(aux.get(i));
			totalWeight += aux.get(i).getWeight();
			knapsack[index] = 1;
		}
		//System.out.println("Start: "+ totalWeight +" Tope: "+ ((InstanceKnapsack)instance).getKnapsackSize() + " LC: "+nObjectsSelection);
		SolutionKnapsack sol = new SolutionKnapsack(knapsack);
		sol.setTotalWeight(totalWeight);
		instance.evaluate(sol);
		return sol;
	}
	
	@Override
	public void configure(Configuration configuration) {
		// Super class configure method		
		super.configure(configuration);
		this.percentCandidates = configuration.getFloat("nCandidates");
		this.percentCapacity = configuration.getFloat("startCapacity");
	}	
}