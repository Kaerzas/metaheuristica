package core;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

import metaheuristics.IAlgorithm;
import problems.ISolution;
import util.config.IConfiguration;

public class TempInicialEnfriamiento {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args){// Try open job file
		File jobFile = new File("configuration/RtLTSP.config");		
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
				
				double probabilidadInicial = 0.9;
				double probabilidadFinal = 0.0001;
				int n = 100000;
				double acc = 0.0;
				for(int i=0 ; i < n ; ++i){
					algorithm.execute();
					
					List<ISolution> solutions = algorithm.getBestSolutions();
					
					ISolution random = solutions.get(0);
					ISolution neighbour = solutions.get(1);
					
					double deltaE = Math.abs(neighbour.getFitness() - random.getFitness());
					acc -= deltaE;
				}
				
				double media = acc / ((double) n);
				double tempInicial = media / Math.log(probabilidadInicial);
				double tempFinal   = media / Math.log(probabilidadFinal);
				System.out.println("Media: " + media + ", T. inicial: " + tempInicial + ", T. final: " + tempFinal);
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
