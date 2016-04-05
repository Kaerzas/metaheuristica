package problems.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import problems.AbstractSolGenerator;
import problems.ISolution;

public class SolGenRandomTSP extends AbstractSolGenerator
{
	
	
	public SolGenRandomTSP(){
		
	}
	
	@Override
	public ISolution generate() 
	{		
		int nNodes = ((InstanceTSP)instance).getNNodes();
		
		List<Integer> randSol = new ArrayList<Integer>(nNodes);
		for(int i=0 ; i < nNodes ; ++i){
			randSol.add(i);
		}
		Collections.shuffle(randSol, randGenerator);
		
		ISolution sol = new SolutionTSP(randSol);
		instance.evaluate(sol);
		
		return sol;
	}
		
}
