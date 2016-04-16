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
		byte [][] knapsack = new byte[nObjects][nKnapsacks];
		// TODO Revisar que se inicializa a 0
		int [] totalWeight = new int[nObjects];
		int chosenKP;
		
		for(int i=0; i<nObjects; i++) {
			if(randGenerator.nextDouble() < addProbability){
				chosenKP = Math.abs(randGenerator.nextInt())%nKnapsacks;
				knapsack[i][chosenKP] = 1;
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
