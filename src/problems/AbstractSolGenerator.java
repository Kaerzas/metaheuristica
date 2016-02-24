package problems;

import java.util.Random;

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

	/** Random generator */
	
	protected Random randGenerator;
	
	/** Instance */
	
	protected IInstance instance;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void setRandom(Random random)
	{
		this.randGenerator = random;
	}

	@Override
	public void setInstance(IInstance instance)
	{
		this.instance = instance;
	}
}