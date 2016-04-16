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
		
		byte knapsacks [][] = ((SolutionQMKP)solution).getObjects();
		int fitness = 0;
		
		// Get the individual values
		for(int i=0; i<nObjects; i++)
			for(int j=0; j<nKnapsacks; j++)
				if(knapsacks[i][j] == 1)
					fitness += this.objects.get(j).getValue();
		
		// Get the pairs values
		for(int i=0; i<nObjects-1; i++) 
			for(int j=i+1; j<nObjects; j++) 
				for(int k=0; k<nKnapsacks; k++)
					if((knapsacks[i][k]==1) && (knapsacks[j][k]==1)) {
						fitness += profits[i][j];
						// Quitar cuando se vea que no hay errores
						if(i == j) {
							System.out.println("Error, los indices no pueden ser iguales porque estariamos calculando un valor individual");
							System.exit(0);
						}
					}

		
		// TODO Habría que mirar si se usa esto o se cambia
		int [] totalWeight = ((SolutionQMKP)solution).getTotalWeight();
		int sumWeights = 0;
		boolean exceded = false;
		
		// Sum the weight o
		for(int i=0; i<totalWeight.length; i++) {
			sumWeights += totalWeight[i];
			if(totalWeight[i] > knapsackSize)
				exceded = true;
		}
		
		if(exceded)
			solution.setFitness(knapsackSize*nKnapsacks-sumWeights);
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

			// Read the values individual values
			line = br.readLine();
			String [] iValues = line.trim().split("\\s+");

			for(int i=0; i< nObjects; i++)
				objects.get(i).setValue(Integer.parseInt(iValues[i]));

			// Read the pairs values
			for(int i=0; i<nObjects-1; i++) {
				line = br.readLine();
				String [] values = line.trim().split("\\s+");
				for(int j=0; j<values.length; j++) {
					if (i!=j) {
						profits[i][j] = Integer.parseInt(values[j]);
						profits[j][i] = Integer.parseInt(values[j]);
					}
				}
			}
						
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

}
