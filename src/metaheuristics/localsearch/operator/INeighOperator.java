package metaheuristics.localsearch.operator;

import java.util.Iterator;
import java.util.Random;

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
	 * Generates a random neighbour for the specified neighbourhood
	 * @return A random neighbour in the neighbourhood
	 */
	public ISolution randomNeighbour(Random random){
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
