package problems.knapsack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.IInstance;
import problems.ISolution;

public class InstanceKnapsack implements IInstance 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Number of objects for the problem */
	
	protected int nObjects;
	
	/** Size of the knapsack */
	
	protected int knapsackSize;
	
	/** List objects */
	
	protected List <KPObject> objects;
	
	
	//////////////////////////////////////////////
	// ------------------------------ Constructor
	/////////////////////////////////////////////
	
	/** Empty constructor */
	
	public InstanceKnapsack() 
	{
		// TODO Auto-generated constructor stub
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	@Override
	public void evaluate(ISolution solution) 
	{
		double value = 0;		
		double weight = 0;
		
		// Check if the solution is an instance of the Knapsack problem
		if(solution instanceof SolutionKnapsack) {
			// Get the objects in the knapsack
			byte knapsack [] = ((SolutionKnapsack)solution).getObjects();
			// Iterate through the knapsack
			for(int i=0; i<knapsack.length; i++) {
				if(knapsack[i] == 1){
					value += this.objects.get(i).getValue();
					weight += this.objects.get(i).getWeight();
				}
			}
			//System.out.println("peso total:" + weight);
			// Check if the solution is valid
			// TODO cambiar por --> (value - value' (si todos se cogieran)
			if(weight > knapsackSize) {
				//System.out.println("knapsackSize:" + knapsackSize);
				//System.out.println("weight:" + weight);
				solution.setFitness(0);
			}
			else {
				//System.out.println("knapsackSize:" + knapsackSize);
				//System.out.println("weight:" + weight);
				solution.setFitness(value);	
			}
		}
		else {
			System.err.println("Solution must be a SolutionKnapsack instance");
			System.exit(0);			
		}
	}

	@Override
	public IInstance loadInstance(FileReader dataFileReader) 
	{
		String line = "";	
		BufferedReader br = new BufferedReader(dataFileReader);
		int pesoTotal = 0;
		
		
		try {
			while (!br.readLine().startsWith("knapPI"));
			// Read the number of objects
			line = br.readLine();
			nObjects = Integer.parseInt(line.split(" ")[1]);
			System.out.println("nObjects:" + nObjects);
			objects = new ArrayList <KPObject>(nObjects);
			// Read the knapsack size
			line = br.readLine();
			knapsackSize = Integer.parseInt(line.split(" ")[1]);
			System.out.println("knapsackSize:" + knapsackSize);

			// Ignore two next lines
			br.readLine();
			br.readLine();
			// Read N objects
			for(int i=0; i<nObjects; i++) {
				KPObject obj = new KPObject();
				line = br.readLine();
				String[] objectInformation = line.split(",");
				obj.setValue(Integer.parseInt(objectInformation[1]));
				obj.setWeight(Integer.parseInt(objectInformation[2]));
				System.out.println("nuevo peso:" +obj.getWeight());
				pesoTotal += obj.getWeight();
				objects.add(obj);
			}
		
		} catch (IOException e) {
			System.out.println("Problem while reading the CSV file");
			e.printStackTrace();
		}
		
		System.out.println("pesoTotal:" + pesoTotal);
		
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Get the number of objects 
	 * 
	 * @return the number of objects
	 */
	
	public int getNObjects() 
	{
		return nObjects;
	}

	/**
	 * Set the number of objects 
	 * 
	 * @param the number of objects
	 */
	
	public void setNObjects(int nObjects) 
	{
		this.nObjects = nObjects;
	}
	
}