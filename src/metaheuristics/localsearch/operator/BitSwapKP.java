package metaheuristics.localsearch.operator;

import problems.ISolution;
import problems.knapsack.SolutionKnapsack;

public class BitSwapKP implements INeighOperator 
{
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour (must be an integer arraylist with two elements)
	 * 
	 * @return a neighbour for the individual
	 */
	
	public ISolution generateNeighbour(ISolution individual, Object param) 
	{
		if(individual instanceof SolutionKnapsack) {
			
			int nObjects = ((SolutionKnapsack) individual).getObjects().length;
			byte [] newObjects = new byte[nObjects];
			System.arraycopy(((SolutionKnapsack) individual).getObjects(), 0, newObjects, 0, nObjects);
			int pos1 = ((int[]) param)[0];
			int pos2 = ((int[]) param)[1];
			byte aux;
			
			SolutionKnapsack newInd = new SolutionKnapsack(newObjects);
							
			aux = ((SolutionKnapsack) newInd).getObjects()[pos1];
			((SolutionKnapsack) newInd).getObjects()[pos1] = ((SolutionKnapsack) newInd).getObjects()[pos2];
			((SolutionKnapsack) newInd).getObjects()[pos2] = aux;
			
			return newInd;
		}
		else {
			System.out.println("The individual must be a SolutionKnapsack");
			System.exit(0);
		}
		// This point should never e reached
		return null;
	}
}