package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import metaheuristics.IAlgorithm;
import problems.ISolution;
import problems.knapsack.SolGeneratorKnapsack;
import util.config.IConfiguration;

/**
 * Main class used for execute an experiment
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class RunP1KP
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
				
				int nPruebas = 10;
				
				//Open results file
				File fOut = new File("results/kp4.txt");
				BufferedWriter writer = new BufferedWriter(new FileWriter(fOut));
				writer.write("# Knapsack problem\n");
				writer.write("# Probability | Max fitness achieved\n");
				
				for(double prob = 0.5 ; prob > 0.0001 ; prob = prob/2){
					((SolGeneratorKnapsack) algorithm.getGenerator()).setAddProbability(prob);
					
					double media = 0.0;
					for(int i=0 ; i < nPruebas ; ++i){
						// Execute and time the algorithm
						algorithm.execute();
						
						List<ISolution> bestSolutions = algorithm.getBestSolutions();
						
						media += bestSolutions.get(bestSolutions.size()-1).getFitness();
						
					}
					
					media /= nPruebas;
					
					//Guardar resultados
					writer.write(Double.toString(prob) + " " + Double.toString(media)  + "\n");
				}
				writer.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}