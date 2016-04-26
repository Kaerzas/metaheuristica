package problems.qmkp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.AbstractInstance;
import problems.ISolution;
import problems.knapsack.KPObject;

public class InstanceQMKP extends AbstractInstance 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Number of objects for the problem */
	
	protected int nObjects;
	
	/** Number of knapsacks */
	
	protected int nKnapsacks;
	
	/** Size of the knapsack */
	
	protected int knapsackSize;
	
	/** List objects */
	
	protected List <KPObject> objects;
	
	/** Profits of pair of objects */
	
	int [][] profits;
	
	//////////////////////////////////////////////
	// ------------------------------ Constructor
	/////////////////////////////////////////////	

	/**
	 * Constructor that set if it is an max/min problem
	 */
	
	public InstanceQMKP() 
	{
		// It is a maximization problem
		maximize = true;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	@Override
	public void evaluate(ISolution solution) 
	{
		// TODO El peso lo omito porque el generador ya se lo pone (habria que ver si no da problemas)
		
		int knapsacks [] = ((SolutionQMKP)solution).getObjects();
		int fitness = 0;
		
		// Get the individual values
		for(int i=0; i<nObjects; i++)
			if(knapsacks[i] != -1)
				fitness += this.objects.get(i).getValue();
		
		
		// Get the pairs values
		int position;
		for(int i=0; i<nObjects-1; i++) {
			position = knapsacks[i];
			if(position != -1) {
				for(int j=i+1; j<nObjects; j++) {
					if(knapsacks[j] == position) {
						fitness += profits[i][j];
					}
				}
			}
		}
		
		int [] totalWeight = ((SolutionQMKP)solution).getTotalWeight();
		int exceded = 0;
		
		// Sum the weight o
		for(int i=0; i<totalWeight.length; i++) {
			if(totalWeight[i] > knapsackSize)
				exceded += knapsackSize - totalWeight[i] ;
		}
		
		// At least one bag is exceded
		if(exceded<0)
			solution.setFitness(exceded);
		// The solution is valid
		else
			solution.setFitness(fitness);
		
	}

	@Override
	public void loadInstance() 
	{
		BufferedReader br = bufferedReader;
		String line;	
		
		try {
			// Read the instance name
			br.readLine();

			// Read the number of objects
			line = br.readLine();
			nObjects = Integer.parseInt(line);

			// Read the number of knapsacks
			line = br.readLine();
			nKnapsacks = Integer.parseInt(line);
			
			// Initialize the objects
			objects = new ArrayList <KPObject>(nObjects);
			for(int i=0; i<nObjects; i++)
				objects.add(new KPObject());

			// Initialize the profits array
			profits = new int [nObjects][nObjects];

			// Read the individual values
			line = br.readLine();
			String [] iValues = line.trim().split("\\s+");

			for(int i=0; i< nObjects; i++)
				objects.get(i).setValue(Integer.parseInt(iValues[i]));

			// Read the pairs values
			for(int i=0; i<nObjects-1; i++) {
				line = br.readLine();
				String [] values = line.trim().split("\\s+");
				for(int j=0; j<values.length; j++) {
					profits[i][i+1+j] = Integer.parseInt(values[j]);
					profits[i+1+j][i] = Integer.parseInt(values[j]);

				}
			}
			/*
			for(int i=0; i<nObjects;i++) {
				for(int j=0; j<nObjects;j++) {
					System.out.print(profits[i][j] + " ");
				}
				System.out.println();
			}
			*/
			
			// Read two blank lines
			line = br.readLine();
			line = br.readLine();
			
			// Read the knapsack size
			line = br.readLine();
			knapsackSize = Integer.parseInt(line);
			
			// Read the objects weights
			line = br.readLine();
			String [] sWeights = line.trim().split("\\s+");
			for(int i=0; i<sWeights.length; i++)
				objects.get(i).setWeight(Integer.parseInt(sWeights[i]));
			
		} catch (IOException e) {
			System.out.println("Problem while reading file");
			e.printStackTrace();
		}

	}

	public int getNKnapsacks() 
	{
		return nKnapsacks;
	}

	public int getNObjects() 
	{
		return nObjects;
	}

	public List <KPObject> getObjects()
	{
		return objects;
	}

	public int getKnapsackSize() {
		return knapsackSize;
	}

	public void setKnapsackSize(int knapsackSize) {
		this.knapsackSize = knapsackSize;
	}

	public int[][] getProfits() {
		return profits;
	}

	public void setProfits(int[][] profits) {
		this.profits = profits;
	}

	
	public double getIncrememtalValue(SolutionQMKP sol, int object, int knapsack) 
	{
		// Set the individual value
		double value = objects.get(object).getValue();
		
		// Get the partners
		int [] partners = sol.getObjectsInBag(knapsack);
		
		// Get the pair values
		for(int i=0; i<partners.length; i++)
			value += profits[object][partners[i]];
		
		// Return the values
		return value;
	}

	@Override
	public int hamming(ISolution sol1, ISolution sol2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}
}
