package util.config;

import org.apache.commons.configuration.XMLConfiguration;

/**
 * Class used to load the configuration
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class ConfigLoader 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Configuration object */
	
	protected XMLConfiguration configuration;
	
	//////////////////////////////////////////////
	// ------------------------------ Constructor
	/////////////////////////////////////////////
	
	/**
	 * Constructor that sets the configuration file
	 * 
	 * @param configFile
	 */
	
	public ConfigLoader(XMLConfiguration configuration)
	{
		this.configuration = configuration;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	/**
	 * Get the instance used from the configuration file
	 * 
	 * @return the instance
	 */
	
	public String getInstance()
	{
		return configuration.getString("instance");
	}
	
	/**
	 * Get the solution used from the configuration file
	 * 
	 * @return the instance
	 */
	
	public String getSolution()
	{
		return configuration.getString("solution");
	}
	
	/**
	 * Get the generator used from the configuration file
	 * 
	 * @return the instance
	 */
	
	public String getSolGenerator()
	{
		return configuration.getString("solGenerator");
	}
	
	/**
	 * Get the data file where the instances are
	 * from the configuration file
	 * 
	 * @return the data file
	 */
	
	public String getData()
	{
		return configuration.getString("data");
	}

	/**
	 * Get the seed for a pseudo-random process
	 * 
	 * @return the seed
	 */
	
	public int getSeed() 
	{
		return Integer.parseInt(configuration.getString("seed"));
	}
}
