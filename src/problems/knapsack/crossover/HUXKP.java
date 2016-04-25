package problems.knapsack.crossover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.configuration.Configuration;

import metaheuristics.evolutive.AbstractCrossover;
import problems.IInstance;
import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.SolutionKnapsack;

public class HUXKP extends AbstractCrossover {

	InstanceKnapsack instance;
	
	@Override
	public void initialize(IInstance instance) {
		this.instance = (InstanceKnapsack) instance;
	}
	
	@Override
	public ISolution cross(ISolution parentA, ISolution parentB) {
		SolutionKnapsack kpA = (SolutionKnapsack) parentA;
		SolutionKnapsack kpB = (SolutionKnapsack) parentB;
		
		byte[] genA = kpA.getObjects();
		byte[] genB = kpB.getObjects();
		
		byte[] genNew = new byte[genA.length];

		// Assign values that are equal in both parents
		// Log values that are different
		List<Integer> different = new ArrayList<Integer>();
		for(int i=0 ; i < genA.length ; ++i){
			if(genA[i] == genB[i])
				genNew[i] = genA[i];
			else{
				genNew[i] = -1;
				different.add(i);
			}
		}
		
		//Suffle different values
		java.util.Collections.shuffle(different, instance.getRandom());
		
		// Even different values will be inherited from parentA
		// Odd different values will be inherited from parentB
		int curr;
		for(int i=0 ; i < different.size() ; ++i){
			curr = different.get(i);
			genNew[curr] = (i%2 == 0) ? genA[curr] : genB[curr];
		}
		
		SolutionKnapsack sk = new SolutionKnapsack(genNew);
		//instance.evaluate(sk);
		return sk;
	}

	@Override
	public void configure(Configuration configuration) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args){
		Random r = new Random();
		InstanceKnapsack ik = new InstanceKnapsack();
		ik.setRandom(r);
		
		HUXKP hkp = new HUXKP();
		hkp.initialize(ik);
		
		int size = 1000;
		byte[] a1 = new byte[size];
		for(int i=0 ; i < a1.length ; ++i){
			if(r.nextBoolean())
				a1[i] = 1;
		}
		SolutionKnapsack s1 = new SolutionKnapsack(a1);
		
		byte[] a2 = new byte[size];
		for(int i=0 ; i < a2.length ; ++i){
			if(r.nextBoolean())
				a2[i] = 1;
		}
		SolutionKnapsack s2 = new SolutionKnapsack(a2);
		
		s1.printSolution();
		s2.printSolution();
		SolutionKnapsack ns = (SolutionKnapsack) hkp.cross(s1, s2);
		ns.printSolution();
		
		int difA=0, difB=0;
		
		for(int i=0 ; i < s1.getObjects().length ; ++i){
			if(ns.getObjects()[i] != s1.getObjects()[i])
				difA++;
			if(ns.getObjects()[i] != s2.getObjects()[i])
				difB++;
		}
		
		System.out.println("Distance to parent A: " + difA);
		System.out.println("Distance to parent B: " + difB);
		
		if(Math.abs(difA - difB) <= 1)
			System.out.println("Distances differ on 1 or less bytes, that's good");
		else
			System.out.println("There's more than 1 difference, you fucked it up!");
	}
}
