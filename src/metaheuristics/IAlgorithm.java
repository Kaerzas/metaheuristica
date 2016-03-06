package metaheuristics;

import java.util.List;

import problems.ISolGenerator;
import problems.ISolution;
import util.config.IConfiguration;

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
	 * Execution method
	 */
	
	public void execute();
	
	public ISolGenerator getGenerator();
	public Stopwatch getStopwatch();
	public List<ISolution> getBestSolutions();
}
