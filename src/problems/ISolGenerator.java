package problems;

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
	 * Get the seed
	 * 
	 * @return the seed
	 */
	
	public int getSeed();
	
	/**
	 * Set the seed 
	 * 
	 * @param seed to set
	 */
	
	public void setSeed(int seed);
}
