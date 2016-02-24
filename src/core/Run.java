package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import problems.IInstance;
import problems.ISolGenerator;
import problems.ISolution;
import problems.knapsack.InstanceKnapsack;
import problems.knapsack.SolGeneratorKnapsack;
import problems.tsp.InstanceTSP;
import problems.tsp.SolGeneratorTSP;
import util.config.ConfigLoader;

/**
 * Main class used for execute an experiment
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class Run
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Generator used in the creation */
	
	protected static ISolGenerator generator;
	
	/** Solution used in the representation */
	
	protected static ISolution solution;
	
	/** Instance of the problem */
	
	protected static IInstance instance;
	
	/** File with the instances */
	
	protected static String dataFile;
	
	/** Seed */
	
	protected static int randSeed;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Main method for the execution
	 * 
	 * @param args command line arguments
	 */
	
	public static void main(String[] args) 
	{		
		
		if(args.length == 0) {
			System.out.println("You must set the config file");
			System.exit(0);
		}
		else {
			// Try open job file
			File jobFile = new File(args[0]);		
			if (jobFile.exists()) {
				try {
					configure(jobFile);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// Load the general parameters for the instance and the generator
		try{
			FileReader fr = new FileReader(new File(dataFile));
			instance.loadInstance(fr);
		}
		catch(FileNotFoundException e){
			System.err.println(e.toString());
			System.exit(0);
		}
		generator.setSeed(randSeed);
		
		// Configure the Knapsack generator
		if(instance instanceof InstanceKnapsack) {
			int nObjects = ((InstanceKnapsack)instance).getNObjects();
			if(generator instanceof SolGeneratorKnapsack) {
				((SolGeneratorKnapsack) generator).setnObjects(nObjects);
			}
		}
		
		// Configure the Knapsack generator
		if(instance instanceof InstanceTSP) {
			int nNodes = ((InstanceTSP)instance).getNNodes();
			if(generator instanceof SolGeneratorTSP) {
				((SolGeneratorTSP) generator).setNNodes(nNodes);
			}
		}
		
		// Create, evaluate and show the solutions
		List <ISolution> solutions = new ArrayList<ISolution>();
		ISolution bestSolution = null;
		
		// Create 1000 individuals
		for(int i=0; i<1000; i++) {
			
			// Create new solution and store
			ISolution newSolution = generator.generate();
			solutions.add(newSolution);
				
			// Evaluate the new solution
			instance.evaluate(newSolution);
			
			// Check if the new solution is better
			if((bestSolution == null) || (instance.betterThan(newSolution, bestSolution))){
				bestSolution = newSolution;
				// Show results
				System.out.println("A best solution has been found in the itration " + i + ":");
				bestSolution.printSolution();
				System.out.println();
			}
		}
	}
	
	/**
	 * Configure the execution of the problem
	 * 
	 * @param jobFile configuration file
	 * 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException
	 *  
	 */
	
	@SuppressWarnings("unchecked")
	public static void configure(File jobFile) throws ClassNotFoundException, InstantiationException, IllegalAccessException 
	{
		XMLConfiguration jobConf;
		try {
			// Initialize the configuration process
			jobConf = new XMLConfiguration(jobFile);
			ConfigLoader loader = new ConfigLoader(jobConf);
			
			// Get the data from the XML file
			String instanceName = loader.getInstance();
			String solutionName = loader.getSolution();
			String generatorName = loader.getSolGenerator();
			String data = loader.getData();
			int seed = loader.getSeed();
			
			// Instantiate the class used in the experiment
			Class<? extends IInstance> instanceClass = 
					(Class<? extends IInstance>) Class.forName(instanceName);
			
			Class<? extends ISolution> solutionClass = 
					(Class<? extends ISolution>) Class.forName(solutionName);
			
			Class<? extends ISolGenerator> generatorClass = 
					(Class<? extends ISolGenerator>) Class.forName(generatorName);
			
			// Set the class used in the experiment
			instance = instanceClass.newInstance();
			solution = solutionClass.newInstance();
			generator = generatorClass.newInstance();
			
			// Set the data instances file and the seed
			dataFile = data;
			randSeed = seed;
		} 
		catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}