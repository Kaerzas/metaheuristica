package metaheuristics.localsearch.operator;

import problems.ISolution;

/**
 * @author i22balur
 *
 */
public interface INeighOperator 
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour
	 * 
	 * @return a neighbour for the individual
	 */
	
	public ISolution generateNeighbour (ISolution individual, Object param);
	
}
