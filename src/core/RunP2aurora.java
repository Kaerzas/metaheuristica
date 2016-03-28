package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

import metaheuristics.IAlgorithm;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import problems.ISolution;
import util.config.IConfiguration;
import util.config.Stopwatch;

public class RunP2aurora {
	
	private static List<String> instancias = Arrays.asList("examples/KPInstances/kp1.csv", "examples/KPInstances/kp2.csv");
	private static List<String> exploradores = Arrays.asList("metaheuristics.localsearch.BISNeighExplorator", "metaheuristics.localsearch.FISNeighExplorator");
	private static List<String> operadores = Arrays.asList("metaheuristics.localsearch.operator.BitInversionKP", "metaheuristics.localsearch.operator.BitSwapKP");
	private static List<String> salidas = Arrays.asList("BIS_inversion", "BIS_swap", "FIS_inversion", "FIS_swap");
	
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	static IAlgorithm loadAlgorithm(String instancia, String explorador, String operador){
		IAlgorithm algorithm = null;
		
		// Try open job file
		String configFile="configuration/p2/LocalKnapsack.config";
		File jobFile = new File(configFile);
		try {
			Configuration jobConf = new XMLConfiguration(jobFile);
			
			jobConf.clearProperty("instance[@name]");
			jobConf.clearProperty("explorator[@name]");
			jobConf.clearProperty("operator[@name]");
			
			jobConf.addProperty("instance[@name]", instancia);
			jobConf.addProperty("explorator[@name]", explorador);
			jobConf.addProperty("operator[@name]", operador);
			
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
		for(int i=0 ; i < instancias.size() ; ++i){
			for(int j=0; j < exploradores.size(); ++j){
				for(int k=0; k < operadores.size(); ++k){
					IAlgorithm algorithm = loadAlgorithm(instancias.get(i), exploradores.get(j), operadores.get(k));
					
					// Execute and time the algorithm
					algorithm.execute();
					System.out.println("Finished! Logging to file...");
					
					Stopwatch stp = algorithm.getStopwatch();
					List<ISolution> bestSolutions = algorithm.getBestSolutions();
					
					//Open results file
					String header = "";
					if(i==0){
						header += "# knapPI_12_500_1000_2\n";
						logResults(header, stp, bestSolutions, "results/p2/kp1_"+salidas.get(j+k)+".txt");
					}
					if(i==1){
						header += "# knapPI_12_500_1000_3\n";
						logResults(header, stp, bestSolutions, "results/p2/kp2_"+salidas.get(j+k)+".txt");
					}
					System.out.println("Done!");
				}
			}
		}
	}
}
