package metaheuristics.localsearch.operator;

import problems.ISolution;
import problems.knapsack.SolutionKnapsack;

public class BitInversionKP implements INeighOperator 
{
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour (must be an int)
	 * 
	 * @return a neighbour for the individual
	 */
	
	public ISolution generateNeighbour(ISolution individual, Object param) 
	{
		
		if(individual instanceof SolutionKnapsack) {

			int nObjects = ((SolutionKnapsack) individual).getObjects().length;
			byte [] newObjects = new byte[nObjects];
			System.arraycopy(((SolutionKnapsack) individual).getObjects(), 0, newObjects, 0, nObjects);
			
			SolutionKnapsack newInd = new SolutionKnapsack(newObjects);
			
			if(newInd.getObjects()[(int) param] == 0)
				newInd.getObjects()[(int) param] = 1;
			else
				newInd.getObjects()[(int) param] = 0;
			
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