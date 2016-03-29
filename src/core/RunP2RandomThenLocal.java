package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import metaheuristics.IAlgorithm;
import problems.ISolution;
import util.config.IConfiguration;
import util.config.Stopwatch;

/**
 * Main class used for execute an experiment
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class RunP2RandomThenLocal
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	static IAlgorithm loadAlgorithm(String configFile){
		IAlgorithm algorithm = null;
		
		// Try open job file
		File jobFile = new File(configFile);
		try {
			Configuration jobConf = new XMLConfiguration(jobFile);
			String algName = jobConf.getString("algorithm[@name]");
			
			// Instantiate the algorithm class used in the experiment
			Class<? extends IAlgorithm> algClass = 
					(Class<? extends IAlgorithm>) Class.forName(algName);
			algorithm = algClass.newInstance();
			
			// Configure the algorithm
			if(algorithm instanceof IConfiguration) {
				((IConfiguration) algorithm).configure(jobConf.subset("algorithm"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return algorithm;
	}
	
	static void logResults(String header, Stopwatch stp, List<ISolution> solutions, String filename){
		try{
			ISolution firstSol = solutions.get(0);
			Long firstTime = stp.lapTime(0);
			
			//Open results file
			File fOut = new File(filename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fOut));

			header += "# Time | Max fitness achieved\n";
			writer.write(header);
			
			writer.write(firstTime.toString() + " " + Double.toString(firstSol.getFitness()) + "\n");
			
			Double lastFitness = firstSol.getFitness();
			for(int j=1 ; j < solutions.size(); ++j){
				ISolution sol = solutions.get(j);
				Double fitness = sol.getFitness();
				Long lapTime = stp.lapTime(j);
				
				writer.write(lapTime.toString() + " " + lastFitness.toString() + "\n");
				writer.write(lapTime.toString() + " " + fitness.toString() + "\n");
				
				lastFitness = fitness;
				
				//sol.printSolution();
				//System.out.println(sol.getFitness());
				//System.out.println(stp.lapTime(j));
			}
			//Last point
			writer.write(Long.toString(stp.elapsed()) + " " + Double.toString(solutions.get(solutions.size()-1).getFitness()) + "\n");
			
			
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Main method for the execution
	 * 
	 * @param args command line arguments
	 */
	
	public static void main(String[] args) 
	{		
			IAlgorithm algorithm = loadAlgorithm("configuration/p2/random_local.config");
	
			// Execute and time the algorithm
			algorithm.execute();
			System.out.println("Finished! Logging to file...");
			
			Stopwatch stp = algorithm.getStopwatch();
			List<ISolution> bestSolutions = algorithm.getBestSolutions();
			
			//Open results file
			String header = "";
			header += "# KP knapPI_1_10000_1000000_1\n";
			header += "# Random (10.000 iterations) then Local FIS (200 iterations), BitSwap operator\n";
			
			logResults(header, stp, bestSolutions, "results/p2/random_local_mejorado200.txt");
			
			System.out.println("Done!");
	}
}