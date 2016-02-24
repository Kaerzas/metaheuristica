package core;

import java.io.File;

import org.apache.commons.configuration.XMLConfiguration;

import metaheuristics.IAlgorithm;
import util.config.IConfiguration;

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
				XMLConfiguration jobConf = new XMLConfiguration(jobFile);
				String algName = jobConf.getString("algorithm[@name]");
				
				// Instantiate the algorithm class used in the experiment
				Class<? extends IAlgorithm> algClass = 
						(Class<? extends IAlgorithm>) Class.forName(algName);
				IAlgorithm algorithm = algClass.newInstance();
				
				// Configure the algorithm
				if(algorithm instanceof IConfiguration) {
					((IConfiguration) algorithm).configure(jobConf.subset("algorithm"));
				}
				
				// Execute the algorithm
				algorithm.execute();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}