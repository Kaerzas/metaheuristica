package problems;

/**
 * Abstract class for all the generators
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public abstract class AbstractSolGenerator implements ISolGenerator 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Seed */
	
	protected int seed;

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public int getSeed() 
	{
		return seed;
	}

	@Override
	public void setSeed(int seed) 
	{
		this.seed = seed;
	}
}