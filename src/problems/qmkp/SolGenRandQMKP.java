package problems.qmkp;

import org.apache.commons.configuration.Configuration;

import problems.AbstractSolGenerator;
import problems.ISolution;
import util.config.IConfiguration;

public class SolGenRandQMKP extends AbstractSolGenerator implements IConfiguration 
{
	///////////////////////////////////////////
	// ----------------------------- Variables
	//////////////////////////////////////////
	
	/** Probability of randomly adding an element*/
	
	private double addProbability;
	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/** Empty constructor */
	
	public SolGenRandQMKP() 
	{
		// TODO Auto-generated constructor stub
	}

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public ISolution generate() 
	{
		int nObjects = ((InstanceQMKP)instance).getNObjects();
		int nKnapsacks = ((InstanceQMKP)instance).getNKnapsacks();
		int [] knapsack = new int[nObjects];
		
		// Initialize with -1 (no objects)
		for(int i=0; i<nObjects; i++)
			knapsack[i] = -1;
		
		int [] totalWeight = new int[nKnapsacks];
		int chosenKP;
		
		for(int i=0; i<nObjects; i++) {
			if(randGenerator.nextDouble() < addProbability){
				chosenKP = Math.abs(randGenerator.nextInt())%nKnapsacks;
				knapsack[i] = chosenKP;
				totalWeight[chosenKP] += ((InstanceQMKP)instance).getObjects().get(i).getWeight();
			}
		}
		
		SolutionQMKP sol = new SolutionQMKP(knapsack);
		sol.setTotalWeight(totalWeight);
		instance.evaluate(sol);
		return sol;
	}

	@Override
	public void configure(Configuration configuration) {
		// Set probability of adding an element
		this.addProbability = configuration.getDouble("probability");
	}
}