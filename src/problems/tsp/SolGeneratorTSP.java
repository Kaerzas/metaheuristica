package problems.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import problems.AbstractSolGenerator;
import problems.ISolution;

public class SolGeneratorTSP extends AbstractSolGenerator
{
	private int nNodes;
	
	private Random randGenerator;
	
	public SolGeneratorTSP(){
		
	}
	
	@Override
	public ISolution generate() 
	{		
		List<Integer> randSol = new ArrayList<Integer>(nNodes);
		for(int i=0 ; i < nNodes ; ++i){
			randSol.add(i);
		}
		Collections.shuffle(randSol, randGenerator);
		
		return new SolutionTSP(randSol);
	}
	
	public void setNNodes(int nNodes){
		this.nNodes = nNodes;
	}
	
	/**
	 * Set the seed and initialize the random generator
	 * 
	 * @param seed the seed to ser
	 */
	
	public void setSeed(int seed) 
	{
		this.seed = seed;
		randGenerator = new Random(seed);
	}
}
