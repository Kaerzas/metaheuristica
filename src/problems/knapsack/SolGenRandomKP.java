package problems.knapsack;

import org.apache.commons.configuration.Configuration;

import problems.AbstractSolGenerator;
import problems.ISolution;
import util.config.IConfiguration;

/**
 * Generator class for the knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class SolGenRandomKP extends AbstractSolGenerator implements IConfiguration
{	
	///////////////////////////////////////////
	// ----------------------------- Variables
	//////////////////////////////////////////
	
	/**
	 * Probability of randomly adding an element to the knapsack
	 */
	private double addProbability;
	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/** Empty constructor */
	
	public SolGenRandomKP() 
	{

	}
		
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public ISolution generate() 
	{
		int nObjects = ((InstanceKnapsack)instance).getLength();
		
		byte [] knapsack = new byte[nObjects];
		int totalWeight = 0;
		
		for(int i=0; i<nObjects; i++) {
			if(randGenerator.nextDouble() < addProbability){
				knapsack[i] = 1;
				totalWeight += ((InstanceKnapsack)instance).getObjects().get(i).getWeight();
			}
			else
				knapsack[i] = 0;
		}
		
		SolutionKnapsack sol = new SolutionKnapsack(knapsack);
		sol.setTotalWeight(totalWeight);
		instance.evaluate(sol);
		return sol;
	}

	@Override
	public void configure(Configuration configuration) {
		// Set probability of adding an element
		this.addProbability = configuration.getDouble("probability");
	}
	
	public void setAddProbability(double addProbability){
		this.addProbability = addProbability;
	}
	
	public double getAddProbability(){
		return this.addProbability;
	}
}