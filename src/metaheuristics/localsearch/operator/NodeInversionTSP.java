package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.List;

import problems.ISolution;
import problems.tsp.SolutionTSP;

public class NodeInversionTSP implements INeighOperator 
{
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour (must be an ArrayList with two elements)
	 * 
	 * @return a neighbour for the individual
	 */
	
	public ISolution generateNeighbour(ISolution individual, Object param) 
	{
		
		if(individual instanceof SolutionTSP) {

			List<Integer> newObjects = new ArrayList<Integer>(((SolutionTSP) individual).getOrder());
			
			SolutionTSP newInd = new SolutionTSP(newObjects);
			
			int aux;
		    int start = ((int[])param)[0];
		    int end = ((int[])param)[1];
		    
		    for (int i = start; i <= ((end-start) / 2)+start; i++) {
		        aux = newObjects.get(i);
		        newObjects.set(i,newObjects.get(end-(i-start)));
		        newObjects.set(end-(i-start), aux);
		    }
			
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
