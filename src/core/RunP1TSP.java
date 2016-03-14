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

public class RunP1TSP
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
				
				//Open results file
				File fOut = new File("results/tsp3_4.txt");
				BufferedWriter writer = new BufferedWriter(new FileWriter(fOut));
				writer.write("# Traveling Salesman Problem (d2103)\n");
				writer.write("# Time | Max fitness achieved\n");

				// Execute and time the algorithm
				algorithm.execute();
				
				Stopwatch stp = algorithm.getStopwatch();
				List<ISolution> bestSolutions = algorithm.getBestSolutions();
				
				//Guardar resultados
				ISolution firstSol = bestSolutions.get(0);
				Long firstTime = stp.lapTime(0);
				
				writer.write(firstTime.toString() + " " + Double.toString(firstSol.getFitness()) + "\n");
				
				Double lastFitness = firstSol.getFitness();
				for(int j=1 ; j < bestSolutions.size(); ++j){
					ISolution sol = bestSolutions.get(j);
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
				writer.write(Long.toString(stp.elapsed()) + " " + Double.toString(bestSolutions.get(bestSolutions.size()-1).getFitness()) + "\n");
				
				
				writer.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}