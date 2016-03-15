package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.List;

import problems.ISolution;
import problems.knapsack.SolutionKnapsack;
import problems.tsp.SolutionTSP;

public class NodeSwapTSP implements INeighOperator{
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour (must be an integer arraylist with two elements)
	 * 
	 * @return a neighbour for the individual
	 */
	public ISolution generateNeighbour(ISolution individual, Object param){
		if(individual instanceof SolutionTSP) {
			int nNodes = ((SolutionTSP) individual).getOrder().size();
			List<Integer> newNodes = new ArrayList<Integer> (nNodes);
			System.arraycopy(((SolutionTSP) individual).getOrder(), 0, newNodes, 0, nNodes);
			int pos1 = ((int[]) param)[0]; 
			int pos2 = ((int[]) param)[1];
			SolutionTSP newInd = new SolutionTSP(newNodes);
			
			((SolutionTSP) newInd).getOrder().set(pos1, ((SolutionTSP) individual).getOrder().get(pos2));
			((SolutionTSP) newInd).getOrder().set(pos2, ((SolutionTSP) individual).getOrder().get(pos1));
			
			return newInd;
		}
		else {
			System.out.println("The individual must be a SolutionTSP");
			System.exit(0);
		}
		// This point should never e reached
		return null;
	}
}
