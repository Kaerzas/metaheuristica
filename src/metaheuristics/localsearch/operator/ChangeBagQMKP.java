package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;

public class ChangeBagQMKP extends INeighOperator 
{
	/** The instance */
	
	private InstanceQMKP instance;
	
	/** Solution to optimize */
	
	private SolutionQMKP original;
	
	/** The number of modifications */
	
	private int modified;
	
	/** Index used in modifications */
	
	private int idxModified;
	
	/** The size of the neighborhood */
	
	private int neighborhoodSize;
	
	/** Array that stores the index to the object inserted */
	
	private int [] objIndex;
	
	/** Object inserted in the initial solution */
	
	private int inserted;
	
	/** Step used changing the knapsack */
	
	private int step;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original)
	{
		this.inserted = 0;
		this.instance = (InstanceQMKP) instance;
		this.original = (SolutionQMKP) original;
		
		List <Integer> aux = new ArrayList<>();
		
		int [] objects = this.original.getObjects();
		
		for(int i=0; i<objects.length; i++)
			if(objects[i] != -1) {
				inserted++;
				aux.add(i);
			}
		
		objIndex = new int [aux.size()];
		for(int i =0; i<aux.size();i++)
			objIndex[i] = aux.get(i);
			
		
		//this.neighborhoodSize = (int) Math.pow(inserted, this.instance.getNKnapsacks()-1);
		this.neighborhoodSize = inserted * (this.instance.getNKnapsacks()-1);
		this.modified = 0;
		this.idxModified = 0;
		this.step = 1;
		
	}
	
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param param Index of the element to invert
	 * 
	 * @return a neighbour for the individual
	 */
	
	private ISolution generateNeighbour(int idx) 
	{
		// Get the objects
		int nObjects = this.original.getObjects().length;
		int [] newObjects = new int[nObjects];
		System.arraycopy(this.original.getObjects(), 0, newObjects, 0, nObjects);
		
		SolutionQMKP newInd = new SolutionQMKP(newObjects);
		int [] weight = original.getTotalWeight();		
		
		// The bag where the item was stored
		int oldBag = newInd.getObjects()[objIndex[idxModified]];
		// The bag where the item is going to be stores
		int newBag = (oldBag + step)%instance.getNKnapsacks();

		// Change the bag
		newInd.getObjects()[objIndex[idxModified]] = newBag;

		// Change the weights in the bags
		weight[oldBag] = weight[oldBag] - instance.getObjects().get(objIndex[idxModified]).getWeight();
		weight[newBag] = weight[newBag] + instance.getObjects().get(objIndex[idxModified]).getWeight();
		newInd.setTotalWeight(weight);
		
		
		boolean invalid = false;
		
		for(int i=0; i<weight.length;i++) {
			if(weight[i]>instance.getKnapsackSize())
				invalid = true;
		}
		
		// Reevaluate (solution invalid)
		if(invalid) {
			int exceded = 0;
			for(int i=0; i<weight.length; i++)
				if(weight[i] > instance.getKnapsackSize())
					exceded += (instance.getKnapsackSize() - weight[i]);
			newInd.setFitness(exceded);
		}
		// The solution is valid 
		else {
			// The previous was invalid
			if(original.getFitness()<0) {
				instance.evaluate(newInd);
			}
			// The previous was valid
			else {
				double fitness = original.getFitness();
				//System.out.println(fitness);
				
				int [] oldPartners = newInd.getObjectsInBag(oldBag);
				int [] newPartners = newInd.getObjectsInBag(newBag);
				
				// Disassociate with the objects of the old bag
				for(int i=0; i<oldPartners.length; i++) {
					fitness -= instance.getProfits()[objIndex[idxModified]][oldPartners[i]];
				}
				
				// Associate with the objects the new bag
				for(int i=0; i<newPartners.length; i++) {
					fitness += instance.getProfits()[objIndex[idxModified]][newPartners[i]];
				}
				
				// Assign the fitness
				newInd.setFitness(fitness);
			}
		}
		
		return newInd;
	}

	@Override
	public boolean hasNext() 
	{
		return modified < neighborhoodSize;
	}

	@Override
	public ISolution next() 
	{
		ISolution sol = generateNeighbour(modified);
		modified++;
		idxModified = modified % inserted;
		if(idxModified == 0)
			step++;
		
		return sol;
	}

	@Override
	public ISolution randomNeighbour() 
	{
		Random random = instance.getRandom();
		return generateNeighbour(random.nextInt(this.instance.getNObjects()));
	}
}