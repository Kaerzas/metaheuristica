package problems.qmkp.crossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.configuration.Configuration;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;
import metaheuristics.evolutive.AbstractCrossover;

public class HUXQMPK extends AbstractCrossover 
{
	InstanceQMKP instance;
	
	@Override
	public void initialize(IInstance instance) 
	{
		this.instance = (InstanceQMKP) instance;
	}

	@Override
	public ISolution cross(ISolution parentA, ISolution parentB) 
	{
		// Get the parents
		SolutionQMKP kpA = (SolutionQMKP) parentA;
		SolutionQMKP kpB = (SolutionQMKP) parentB;
		
		// Get the objects of the parents
		int[] genA = kpA.getObjects();
		int[] genB = kpB.getObjects();
		
		// The genotype of the son
		int[] genNew = new int[genA.length];

		// Assign values that are equal in both parents and save the different ones
		List<Integer> different = new ArrayList<Integer>();
		for(int i=0 ; i < genA.length ; i++){
			if(genA[i] == genB[i])
				genNew[i] = genA[i];
			else{
				genNew[i] = -2;	// This is an invalid value (not neccesary)
				different.add(i);
			}
		}
		
		//Shuffle different values
		Collections.shuffle(different, instance.getRandom());
		
		// Get the half (quitar si Javi tiene razon)
		different = different.subList(0, different.size()/2);
		
		// Even different values will be inherited from parentA
		// Odd different values will be inherited from parentB
		int curr;
		for(int i=0 ; i < different.size() ; ++i){
			curr = different.get(i);
			genNew[curr] = (i%2 == 0) ? genA[curr] : genB[curr];
		}
		
		SolutionQMKP sk = new SolutionQMKP(genNew);
		instance.evaluate(sk);
		return sk;
	}

	@Override
	public void configure(Configuration configuration) 
	{
		// TODO Auto-generated method stub
	}
}