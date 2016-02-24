package util.config;

import org.apache.commons.configuration.Configuration;

/**
 * Class used to indicate that a class can be configured
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public interface IConfiguration 
{
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Configuration method
	 * 
	 * @param configuration the configuration class
	 */
	
	public void configure(Configuration configuration);
}
