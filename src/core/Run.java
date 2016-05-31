package core;

import java.io.File;
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

public class Run
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Main method for the execution
	 * 
	 * @param args command line arguments
	 */
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) 
	{		
		// Check the config file is received
		if(args.length == 0) {
			System.out.println("You must set the config file");
			System.exit(0);
		}
		
		// Try open job file
		File jobFile = new File(args[0]);		
		if (jobFile.exists()) {
			try {
				Configuration jobConf = new XMLConfiguration(jobFile);
				String algName = jobConf.getString("algorithm[@name]");
				
				// Instantiate the algorithm class used in the experiment
				Class<? extends IAlgorithm> algClass = 
						(Class<? extends IAlgorithm>) Class.forName(algName);
				IAlgorithm algorithm = algClass.newInstance();
				
				// Configure the algorithm
				if(algorithm instanceof IConfiguration) {
					((IConfiguration) algorithm).configure(jobConf.subset("algorithm"));
				}
				
				// Execute and time the algorithm
				Stopwatch stp = new Stopwatch();
				stp.start();
				algorithm.execute();
				stp.stop();
				
				List<ISolution> bestSolutions = algorithm.getBestSolutions();
				System.out.println("Time elapsed: " + stp.elapsed() + " ns");
				if(bestSolutions.size() > 0)
					System.out.println("Best fitness found: " + algorithm.getBestSolution().getFitness());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Configuration file doesn't exist");
		}
	}
}