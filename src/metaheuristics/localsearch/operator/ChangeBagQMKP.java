package metaheuristics.localsearch.operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import problems.IInstance;
import problems.ISolution;
import problems.qmkp.InstanceQMKP;
import problems.qmkp.SolutionQMKP;

public class ChangeBagQMKP extends INeighOperator 
{
	class Neighbour{
		public int idx;
		public int newBag; 
		
		public Neighbour(int idx, int newBag){
			this.idx = idx;
			this.newBag = newBag;
		}
	}
	
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** The instance */
	
	private InstanceQMKP instance;
	
	/** Solution to optimize */
	
	private SolutionQMKP original;
	
	private Stack<Neighbour> neighbourhood;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////

	@Override
	public void initialize(IInstance instance, ISolution original)
	{
		// Load the instance and the individual
		this.instance = (InstanceQMKP) instance;
		this.original = (SolutionQMKP) original;
		neighbourhood = null;
	}
	
	/**
	 * Generate a neighbour for a given individual
	 * 
	 * @param param Index of the element to invert
	 * 
	 * @return a neighbour for the individual
	 */
	
	private ISolution generateNeighbour(int idx, int newBag) 
	{
		// Get the objects of the original solution
		int nObjects = this.original.getObjects().length;
		int [] newObjects = new int[nObjects];
		System.arraycopy(this.original.getObjects(), 0, newObjects, 0, nObjects);
		// Get the weight of the original solution
		int nKnapsacks = this.instance.getNKnapsacks();
		int [] weight = new int [nKnapsacks];
		System.arraycopy(this.original.getTotalWeight(), 0, weight, 0, nKnapsacks);	
		
		// Generate a new individual (the neighbor)
		SolutionQMKP newInd = new SolutionQMKP(newObjects);		
		
		// The bag where the item was stored
		int oldBag = newInd.getObjects()[idx];

		// Change the bag
		newInd.getObjects()[idx] = newBag;

		// Change the weights in the bags
		weight[oldBag] = weight[oldBag] - instance.getObjects().get(idx).getWeight();
		weight[newBag] = weight[newBag] + instance.getObjects().get(idx).getWeight();
		newInd.setTotalWeight(weight);
		
		// Flag used to see if the solution is invalid based on the weights
		boolean invalid = false;
		// See if any knapsack is overflow
		for(int i=0; i<weight.length;i++) {
			if(weight[i]>instance.getKnapsackSize())
				invalid = true;
		}
		
		// Reevaluate the solution
		// The solution is invalid
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
				// It is needed to evaluate from the begining
				instance.evaluate(newInd);
			}
			// The previous was valid
			else {
				// Get the previous fitness
				double fitness = original.getFitness();				
				// Get the old and the new partners element from the object that has been moved
				List<Integer> oldPartners = newInd.getObjectsInBag(oldBag);
				List<Integer> newPartners = newInd.getObjectsInBag(newBag);
				
				// Disassociate with the objects of the old bag
				for(Integer o : oldPartners)
					fitness -= instance.getProfits()[idx][o];
				// Associate with the objects the new bag
				for(Integer n : newPartners)
					fitness += instance.getProfits()[idx][n];
				
				// Assign the fitness
				newInd.setFitness(fitness);
			}
		}
		
		return newInd;
	}
	
	private void resetNeighbourhood(){
		int [] objects = this.original.getObjects();
		int nBags = this.instance.getNKnapsacks();
		
		int bag;
		neighbourhood = new Stack<>();
		for(int i=0 ; i < objects.length ; ++i){
			bag = objects[i];
			if(bag < 0) //Skip objects that are not inserted in any bag
				continue;
			
			for(int b=0 ; b < nBags ; ++b){
				if(b == bag) //The same bag doesn't count
					continue;
				
				neighbourhood.push(new Neighbour(i, b));
			}
		}
		Collections.shuffle(neighbourhood, instance.getRandom());
	}

	@Override
	public boolean hasNext() 
	{
		if(neighbourhood == null)
			resetNeighbourhood();
		
		return neighbourhood.size() > 0;
	}

	@Override
	public ISolution next() 
	{
		Neighbour n = neighbourhood.pop();
		return generateNeighbour(n.idx, n.newBag);
	}
	
	@Override
	public ISolution randomNeighbour() 
	{
		List<Integer> inserted = new ArrayList<>(instance.getNObjects());
		int[] objects = original.getObjects();
		
		// List of inserted elements
		for(int i=0 ; i < objects.length ;  ++i){
			if(objects[i] >= 0){
				inserted.add(i);
			}
		}
		
		if(inserted.size() < 2)
			throw new RuntimeException("ChangeBagQMKP can only generate neighbours for solutions with 2 or more objects inserted.");
		
		int idx = inserted.get(instance.getRandom().nextInt(inserted.size()));
		int currentBag = objects[idx];
		int newBag = instance.getRandom().nextInt(instance.getNKnapsacks()-1); // [0, nBags-1)
		if(newBag >= currentBag)
			newBag = newBag + 1; //[0, currentBag), (currentBag, nBags)
		
		return generateNeighbour(idx, newBag);
	}
	
	public static void main(String[] args){
		//Create instance
		InstanceQMKP instance = new InstanceQMKP();
		Configuration c = new BaseConfiguration();
		c.setProperty("data", "examples/QMKPInstances/jeu_100_100_1.txt");
		instance.configure(c);
		instance.setRandom(new Random());
		
		//Create solution
		int[] objects = new int[instance.getNObjects()];
		for(int i=0 ; i < objects.length ; ++i){
			objects[i] = -1;
		}
		objects[0] = 0;
		objects[1] = 1;
		objects[2] = 2;
		ISolution solution = new SolutionQMKP(objects);
		instance.evaluate(solution);
		
		//Create operator
		ChangeBagQMKP operator = new ChangeBagQMKP();
		operator.initialize(instance, solution);
		
		System.out.println("Original solution:");
		solution.printSolution();
		
		System.out.println("Neighbours:");
		while(operator.hasNext()){
			ISolution neigh = operator.next();
			neigh.printSolution();
			
			double quickFitness = neigh.getFitness();
			instance.evaluate(neigh);
			double correctFitness = neigh.getFitness();
			
			if(quickFitness != correctFitness){
				System.err.println("Fitness incorrectly computed");
				System.err.println("Computed: " + quickFitness + ", Correct: " + correctFitness);
			}
		}
		
		System.out.println("Random neighbours:");
		for(int i=0 ; i < 10 ; ++i){
			ISolution neigh = operator.randomNeighbour();
			neigh.printSolution();
			
			double quickFitness = neigh.getFitness();
			instance.evaluate(neigh);
			double correctFitness = neigh.getFitness();
			
			if(quickFitness != correctFitness){
				System.err.println("Fitness incorrectly computed");
				System.err.println("Computed: " + quickFitness + ", Correct: " + correctFitness);
			}
		}
	}
}