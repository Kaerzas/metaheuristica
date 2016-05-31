package metaheuristics;

import java.util.List;
import java.util.Random;

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
	 * Get the solution list representing the algorithm execution
	 * 
	 * @return List of solutions in each iteration
	 */
	
	public List<ISolution> getBestSolutions();
	
	public void removeBestSolution();
	
	/**
	 * Get the best solution found
	 * 
	 * @return Best solution found
	 */
	public ISolution getBestSolution();
	
	public Random getRandom();
	
	public String getHeader();
}
