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

	private int nObjectsSelecion;
	
	public ISolution generate() 
	{		
		// Get the object number for the problem
		int nObjects = ((InstanceKnapsack)instance).getNObjects();
		// Initialize the knapsack and the weight
		byte [] knapsack = new byte[nObjects];		
		int totalWeight = 0;
		
		// Create an array with the best elements sorted by ratio
		List<KPObject> aux = new ArrayList<KPObject>(((InstanceKnapsack)instance).getObjects());

		//System.out.println("reference aux:"+aux);
		//System.out.println("reference org:"+((InstanceKnapsack)instance).getObjects());
		
		
		aux = aux.subList(0, nObjectsSelecion);
		Collections.shuffle(aux);	
		Collections.sort(aux, new ComparatorRatioKP());
		
		/*
		System.out.println("weight:"+aux.get(0).getWeight());
		System.out.println("fitness:"+aux.get(0).getValue());
		
		System.out.println("weight:"+aux.get(1).getWeight());
		System.out.println("fitness:"+aux.get(1).getValue());
		
		System.out.println("weight:"+aux.get(2).getWeight());
		System.out.println("fitness:"+aux.get(2).getValue());
	
		System.out.println("weight:"+aux.get(9).getWeight());
		System.out.println("fitness:"+aux.get(9).getValue());
		*/
		
		//System.out.println("reference aux (sorted and delimitied):"+aux);
	
		for(int i=0; i<aux.size(); i++) {
			int index = ((InstanceKnapsack)instance).getObjects().indexOf(aux.get(i));
			totalWeight += aux.get(i).getWeight();
			knapsack[index] = 1;
		}
		
		SolutionKnapsack sol = new SolutionKnapsack(knapsack);
		sol.setTotalWeight(totalWeight);
		instance.evaluate(sol);
		return sol;
	}
	
	@Override
	public void configure(Configuration configuration) {
		// Super class configure method		
		super.configure(configuration);
		this.nObjectsSelecion = configuration.getInt("objSelecion");
	}	
}