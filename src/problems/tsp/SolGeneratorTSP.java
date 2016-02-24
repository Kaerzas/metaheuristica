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
	
	public SolGeneratorTSP(){
		
	}
	
	@Override
	public ISolution generate() 
	{
		Random randGenerator = new Random(seed);
		
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
}
