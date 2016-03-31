package metaheuristics.localsearch.operator;

import java.util.Iterator;

import problems.IInstance;
import problems.ISolution;

/**
 * @author i22balur
 *
 */
public abstract class INeighOperator implements Iterator<ISolution>
{
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	public abstract void initialize(IInstance instance, ISolution original);
	
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param individual The individual
	 * @param param Parameter used to generate the neighbour
	 * 
	 * @return a neighbour for the individual
	 */
	
	//public ISolution generateNeighbour (ISolution individual, Object param);
	
}
