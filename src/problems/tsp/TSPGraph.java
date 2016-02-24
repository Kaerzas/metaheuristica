package problems.tsp;

import java.util.List;

/**
 * Class representing the graph for the TSP problem
 * 
 * @author Rafael Barbudo Lunar
 *
 */

public class TSPGraph 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** The graph */
	
	private double graph[][];
	
	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/**
	 * Create the graph given the nodes
	 * 
	 * @param nodes the nodes of the graph
	 */
	
	public TSPGraph(List <TSPNode> nodes)
	{
		// Initialize the graph
		this.graph = new double[nodes.size()][nodes.size()];
		
		// Fill the distances
		for(int i = 0; i < nodes.size(); ++i){
			for(int j = 0; j < nodes.size(); ++j){			
				graph[i][j] = nodes.get(i).distance(nodes.get(j));
			}
		}	
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	/**
	 * Return the distance between two nodes
	 * 
	 * @param origin the origin node 
	 * @param destiny the destiny node
	 * 
	 * @return the distance
	 */
	
	public double distance(int origin, int destiny) 
	{
		return this.graph[origin][destiny];
	}

}