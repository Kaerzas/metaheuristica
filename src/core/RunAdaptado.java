package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import metaheuristics.IAlgorithm;
import metaheuristics.VND;
import problems.ISolution;
import util.config.IConfiguration;
import util.config.Stopwatch;

/**
 * Main class used for execute an experiment
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class RunAdaptado
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
	
	static void logResults(ISolution solution, int resets, String filename){
		try{
	
			//Open results file
			File fOut = new File(filename);

			BufferedWriter writer = new BufferedWriter(new FileWriter(fOut, true));
		
			writer.write(resets + "\t" + solution.getFitness() + "\n");
				
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
			IAlgorithm algorithm = loadAlgorithm("configuration/VNDQMKP.config");
	
			int nExec = 10;
			
			for(int i=0 ; i < nExec ; ++i){
				// Execute and time the algorithm
				algorithm.execute();
				System.out.println("Finished! Logging to file...");
								
				// aqui poner la metaheuristica con multiarranque
				int resets = 0;
				if(algorithm instanceof VND) {
					resets = ((VND)algorithm).getResets();
				}
				
				//Open results file
				String header = "";
				header += "# VND, jeu_100_75_5, 1 min\n";
				header += "Resets | Best Fitness";
				logResults(algorithm.getBestSolution(), resets, "/home/i22balur/Escritorio/pruebas/jeu_300_50_5.txt");
				
				algorithm.getBestSolutions().clear();
				algorithm.removeBestSolution();
			}
			
			System.out.println("Done!");
	}
}