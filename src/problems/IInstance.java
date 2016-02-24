package problems;

import java.io.FileReader;

/**
 * Interface which has the methods needed to create 
 * an instance and evaluate a problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public interface IInstance 
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
	 * 
	 * @param dataFileReader reader of the data file
	 * 
	 * @return A instance for the problem
	 */
	
	public IInstance loadInstance(FileReader dataFileReader);	
}
