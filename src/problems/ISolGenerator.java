package problems;

import java.util.Random;

/**
 * Interface which has the methods needed to 
 * create solutions for a given problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public interface ISolGenerator
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Create a new solution
	 * 
	 * @return A new solution
	 */
	
	public ISolution generate();
	

	/**
	 * Set the random generator
	 * 
	 * @param random the random generator
	 */
	
	void setRandom(Random random);

	/**
	 * Set the instance for the generator
	 * 
	 * @param instance the instance of the problem
	 */
	
	public void setInstance(IInstance instance);

}