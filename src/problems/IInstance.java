package problems;

import java.util.Random;

import util.config.IConfiguration;

/**
 * Interface which has the methods needed to create 
 * an instance and evaluate a problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public interface IInstance extends IConfiguration
{	
	/**
	 * Evaluate the solution
	 * 
	 * @param solution solution to evaluate
	 * 
	 */
	
	public void evaluate(ISolution solution);	
	
	/**
	 * Load an instance for a given problem
	 */
	
	public void loadInstance();	
	
	/**
	 * Compare two solution
	 * 
	 * @param sol1 first solution to compare
	 * @param sol2 second solution to compare
	 * 
	 * @return true if the first solution is better, false anyway
	 */
	
	public boolean betterThan(ISolution sol1, ISolution sol2);

	Random getRandom();

	void setRandom(Random random);
}
