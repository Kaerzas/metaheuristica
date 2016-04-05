package problems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import org.apache.commons.configuration.Configuration;

/**
 * Abstract class for all the instances
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public abstract class AbstractInstance implements IInstance 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** File with the instances */
	
	protected BufferedReader bufferedReader;
	
	/** Indicates whether a max/min problem */
		
	protected boolean maximize;
	
	private Random random;

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public boolean betterThan(ISolution sol1, ISolution sol2) 
	{
		if((sol1.getFitness() > sol2.getFitness()) && maximize)
			return true;
		if((sol1.getFitness() < sol2.getFitness()) && !maximize)
			return true;

		return false;
	}

	@Override
	public void configure(Configuration configuration) 
	{
		// Get the descriptor for the data file if it doesnt exist
		if(bufferedReader == null){
			String fileName = configuration.getString("data");
			try {
				FileReader dataFileReader = new FileReader(new File(fileName));
				bufferedReader = new BufferedReader(dataFileReader);
				loadInstance();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Random getRandom() {
		return random;
	}

	@Override
	public void setRandom(Random random) {
		this.random = random;
	}
}