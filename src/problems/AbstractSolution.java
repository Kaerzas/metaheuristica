package problems;

/**
 * Abstract class for all the solution
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public abstract class AbstractSolution implements ISolution 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Solution fitness */
	
	double fitness = 0;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public double getFitness() 
	{
		return fitness;
	}

	@Override
	public void setFitness(double fitness) 
	{
		this.fitness = fitness;	
	}
}