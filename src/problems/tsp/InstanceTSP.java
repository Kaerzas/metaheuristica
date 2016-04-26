package problems.tsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.AbstractInstance;
import problems.ISolution;

public class InstanceTSP extends AbstractInstance 
{
	//////////////////////////////////////////////
	// -------------------------------- Variables
	/////////////////////////////////////////////
	
	/** The graph */
	
	public TSPGraph graph;
	
	/** The number of nodes */
	
	private int nNodes;

	//////////////////////////////////////////////
	// ----------------------------- Constructors
	/////////////////////////////////////////////
	
	/**
	 * Constructor that set if it is an max/min problem
	 */
	
	public InstanceTSP() 
	{
		// It is a minimization problem
		maximize = false;
	}
	
	//////////////////////////////////////////////
	// ---------------------------------- Methods
	/////////////////////////////////////////////
	
	@Override
	public void evaluate(ISolution solution) 
	{
		// Get the order of the nodes
		List<Integer> order = ((SolutionTSP)solution).getOrder();
		double total = 0.0;
		
		// Get the distance between the nodes of the permutation
		for(int i=0 ; i < (order.size()-1) ; ++i){
			total += graph.distance(order.get(i), order.get(i+1));
		}
		total += graph.distance(order.get(order.size()-1), 0);
		
		// Set the fitness to the individual
		solution.setFitness(total);
	}

	@Override
	public void loadInstance() 
	{
		// The list of nodes
		List<TSPNode> nodes = null;
		
		try{
			BufferedReader file = bufferedReader;
		
			// Needed variables to read the file
			String line, key, value;
			
			// Read file until the end condition
			while(true){
				
				line = file.readLine();
				
				//Stop condition, this starts the node section
				if(line.equals("NODE_COORD_SECTION"))
					break;
				
				key = line.split(":")[0];
				value = line.split(":")[1];
				
				if(key.startsWith("DIMENSION"))
					this.nNodes = Integer.parseInt(value.trim());
			}
			
			nodes = new ArrayList<TSPNode>(nNodes);
			
			//Read the nodes
			String[] nodeLine;
			TSPNode auxNode;
			double x, y;
			
			for(int i=0 ; i < nNodes ; ++i){
				nodeLine = file.readLine().split(" ");
				
				x = Double.parseDouble(nodeLine[1]);
				y = Double.parseDouble(nodeLine[2]);
				
				auxNode = new TSPNode(x,y);
				nodes.add(auxNode);
			}
		}
		catch(IOException e){
			System.err.println("Error in TSP file read");
		}
		
		// 	Initialize the graph
		graph = new TSPGraph(nodes);
	}
	
	/**
	 * Get the number of nodes
	 * @return the number of nodes
	 */
	public int getLength(){
		return this.nNodes;
	}
	
	public int hamming(ISolution sol1, ISolution sol2){
		List<Integer> order1 = ((SolutionTSP)sol1).getOrder();
		List<Integer> order2 = ((SolutionTSP)sol2).getOrder();
		int distance=0;
		for(int i=0; i<this.getLength(); ++i)
			if(order1.get(i) != order2.get(i))
				distance++;
		return distance;
	}
}