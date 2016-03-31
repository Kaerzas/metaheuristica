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
	
	private int weight; 
	
	/** Value of the object */
	
	private int value;

	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	/**
	 * Get the object's weight 
	 * 
	 * @return the weight
	 */
	
	public int getWeight() 
	{
		return weight;
	}

	/**
	 * Set the object's weight
	 * 
	 * @param weight the weight to set
	 */
	
	public void setWeight(int weight) 
	{
		this.weight = weight;
	}

	/**
	 * Get the object's value 
	 * 
	 * @return the value
	 */
	
	public int getValue() 
	{
		return value;
	}

	/**
	 * Set the object's value
	 * 
	 * @param value the value to set
	 */
	
	public void setValue(int value) 
	{
		this.value = value;
	}	
}