package problems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
	
	protected FileReader dataFileReader;
	
	/** Indicates whether a max/min problem */
		
	protected boolean maximize;

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public boolean betterThan(ISolution sol1, ISolution sol2) 
	{
		if((sol1.getFitness() > sol2.getFitness()) && maximize)
			return true;
		if((sol1.getFitness() <= sol2.getFitness()) && !maximize)
			return true;

		return false;
	}

	@Override
	public void configure(Configuration configuration) 
	{
		// Get the descriptor for the data file
		String fileName = configuration.getString("data");
		try {
			dataFileReader = new FileReader(new File(fileName));
			loadInstance();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}