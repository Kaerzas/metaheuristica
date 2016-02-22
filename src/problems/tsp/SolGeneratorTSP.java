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
	
	public SolGeneratorTSP(int nNodes){
		this.nNodes = nNodes;
	}
	
	@Override
	public ISolution generate() 
	{
		Random randGenerator = new Random(seed);
		
		List<Integer> randSol = new ArrayList<Integer>(nNodes);
		for(int i=0 ; i < nNodes ; ++i){
			randSol.set(i, i+1);
		}
		Collections.shuffle(randSol, randGenerator);
		
		return new SolutionTSP(randSol);
	}
}
