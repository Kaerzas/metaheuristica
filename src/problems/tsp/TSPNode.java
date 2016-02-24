package problems.tsp;

/**
 * Class representing a node for the TSP problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class TSPNode 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** Coordinate X */
	
	private double x;
	
	/** Coordinate Y */
	
	private double y;
	
	//////////////////////////////////////////////
	// ------------------------------ Constructor
	/////////////////////////////////////////////	
	
	/**
	 * Constructor that sets the coordinates
	 * 
	 * @param x coordinate X
	 * @param y coordinate Y
	 */
	
	public TSPNode(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////	
	
	/**
	 * Get the coordinate Y
	 * 
	 * @return the coordinate Y
	 */
	
	public double getY() 
	{
		return y;
	}

	/**
	 * Set the coordinate Y
	 * 
	 * @param y the coordinate Y
	 */
	
	public void setY(double y) 
	{
		this.y = y;
	}

	/**
	 * Get the coordinate X
	 * 
	 * @return the coordinate X
	 */
	
	public double getX() 
	{
		return x;
	}

	/**
	 * Set the coordinate X
	 * 
	 * @param x the coordinate X
	 */
	
	public void setX(double x) 
	{
		this.x = x;
	}
	
	/**
	 * Calculate the distance to other node
	 * 
	 * @param oNode the other node
	 * 
	 * @return distance between nodes
	 */
	
	public double distance(TSPNode oNode)
	{
		return Math.sqrt(Math.pow(this.x - oNode.getX(), 2) + Math.pow((this.y - oNode.getY()), 2));
	}
}