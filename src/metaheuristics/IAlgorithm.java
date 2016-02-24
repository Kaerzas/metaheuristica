package metaheuristics;

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
}
