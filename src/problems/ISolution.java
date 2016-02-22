package problems;

/**
 * Interface which has the methods needed to work 
 * with the representation of a solution
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public interface ISolution 
{	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Shows in console a given solution
	 */
	
	public void printSolution();
	
	/**
	 * Get the fitness of the solution
	 * 
	 * @return the fitness
	 */
	
	public double getFitness();
	
	/**
	 * Set the fitness of a solution
	 * 
	 * @param fitness to set
	 */
	
	public void setFitness(double fitness);
}
