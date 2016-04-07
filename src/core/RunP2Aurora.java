package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import metaheuristics.IAlgorithm;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import problems.ISolution;
import util.config.IConfiguration;
import util.config.Stopwatch;

public class RunP2Aurora {
	
	private static List<String> instances = Arrays.asList("examples/KPInstances/kp1.csv", "examples/KPInstances/kp2.csv");
	private static List<String> explorators = Arrays.asList("metaheuristics.localsearch.BISNeighExplorator", "metaheuristics.localsearch.FISNeighExplorator");
	private static List<String> operators = Arrays.asList("metaheuristics.localsearch.operator.BitInversionKP", "metaheuristics.localsearch.operator.BitSwapKP");
	private static List<String> outputs = Arrays.asList("BIS_inversion", "BIS_swap", "FIS_inversion", "FIS_swap");
	private static boolean setSeed = false;
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	static IAlgorithm loadAlgorithm(int idxInst, int idxExp, int idxOp){
		IAlgorithm algorithm = null;
		
		// Try open job file
		String configFile="configuration/LocalKP.config";
		File jobFile = new File(configFile);
		
		try {
			Configuration jobConf = new XMLConfiguration(jobFile);

/*			// Shows Configuration arguments
 * 			Iterator<String> l = jobConf.getKeys();
 * 			while(l.hasNext())
 * 				System.out.println(l.next());
*/			
			// Update configuration in run time
			jobConf.setProperty("algorithm.instance.data", instances.get(idxInst));
			jobConf.setProperty("algorithm.explorator[@name]", explorators.get(idxExp));
			jobConf.setProperty("algorithm.explorator.operator", operators.get(idxOp));
			if(setSeed)
				jobConf.setProperty("algorithm.seed", Integer.toString(new Random().nextInt()));
			
			String algName = jobConf.getString("algorithm[@name]");
						
			// Instantiate the algorithm class used in the experiment
			Class<? extends IAlgorithm> algClass = 
			(Class<? extends IAlgorithm>) Class.forName(algName);
			algorithm = algClass.newInstance();
			
			// Configure the algorithm
			if(algorithm instanceof IConfiguration) 
				((IConfiguration) algorithm).configure(jobConf.subset("algorithm"));
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
//		int i=0, j=0, k=1; // kp1 - Testing BIS - Swap
		for(int i=0; i < instances.size(); ++i){
			for(int j=0; j < explorators.size(); ++j){
				for(int k=0; k < operators.size(); ++k){
					// Seed
//					for(int l=0; l<10; ++l){
						IAlgorithm algorithm = loadAlgorithm(i,j,k);
						
						// Execute and time the algorithm
						algorithm.execute();
						System.out.println("Finished! Logging to file...");
						
						Stopwatch stp = algorithm.getStopwatch();
						List<ISolution> bestSolutions = algorithm.getBestSolutions();
						
						//Open results file
						String header = "";
						String result = "results/";
						if(i==0){
							header += "# knapPI_12_500_1000_2\n";
							result += "kp1_";
						}
						else if(i==1){
							header += "# knapPI_12_500_1000_3\n";
							result += "kp2_";
						}
						
						//Get name output using j and k indices as a binary number
						String idx = String.format("%d%d", j,k);
						int index = Integer.parseInt(idx,2);
						result += outputs.get(index);
						//if(setSeed)
						//	result += l;
						result += ".txt";
						
						logResults(header, stp, bestSolutions, result);
						System.out.println("Done!");
					}
//				}
			}
		}
	}
}
