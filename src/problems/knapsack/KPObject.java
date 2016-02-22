package problems.knapsack;

/**
 * Class representing an object for the Knapsack problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class KPObject 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Weight of the object */
	
	private double weight; 
	
	/** Value of the object */
	
	private double value;

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	/**
	 * Get the object's weight 
	 * 
	 * @return the weight
	 */
	
	public double getWeight() 
	{
		return weight;
	}

	/**
	 * Set the object's weight
	 * 
	 * @param weight the weight to set
	 */
	
	public void setWeight(double weight) 
	{
		this.weight = weight;
	}

	/**
	 * Get the object's value 
	 * 
	 * @return the value
	 */
	
	public double getValue() 
	{
		return value;
	}

	/**
	 * Set the object's value
	 * 
	 * @param value the value to set
	 */
	
	public void setValue(double value) 
	{
		this.value = value;
	}	
}