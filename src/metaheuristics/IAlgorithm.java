package metaheuristics;

import java.util.List;

import problems.IInstance;
import problems.ISolGenerator;
import problems.ISolution;
import util.config.IConfiguration;
import util.config.Stopwatch;

/**
 * General interface for all the metaheuristics
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public interface IAlgorithm extends IConfiguration
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Timed execution
	 */
	
	public void execute();
	
	/**
	 * Get the generator 
	 * 
	 * @return The generator
	 */
	
	public ISolGenerator getGenerator();
	
	/**
	 * Get the instance 
	 * 
	 * @return The instance
	 */
	
	public IInstance getInstance();
	
	/**
	 * Get the stop watch 
	 * 
	 * @return The stop watch
	 */
	
	public Stopwatch getStopwatch();
	
	/**
	 * Get the best solution 
	 * 
	 * @return The best solution
	 */
	
	public List<ISolution> getBestSolutions();
}
