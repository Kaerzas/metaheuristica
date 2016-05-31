package metaheuristics.localsearch;

import metaheuristics.localsearch.operator.INeighOperator;
import problems.IInstance;
import problems.ISolution;
import util.config.IConfiguration;

/**
 * @author i22balur
 *
 */
public interface INeighExplorator extends IConfiguration
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Generate the best neighbour for a given search strategy
	 * 
	 * @param individual The individual
	 * 
	 * @return a neighbour for the individual
	 */
	
	public ISolution generateBestNeighbour (ISolution individual);
	
	
	public IInstance getInstance();
	
	public void setInstance(IInstance instance);


	public void setOperator(INeighOperator operator);
}
