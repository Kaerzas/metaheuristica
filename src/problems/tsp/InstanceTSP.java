package problems.tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.AbstractInstance;
import problems.ISolution;

public class InstanceTSP extends AbstractInstance 
{
	private TSPGraph graph;
	private int nNodes;
	
	
	@Override
	public void evaluate(ISolution solution) 
	{
		List<Integer> order = ((SolutionTSP)solution).getOrder();
		double total = 0.0;
		
		for(int i=0 ; i < (order.size()-1) ; ++i){
			total += graph.distance(order.get(i), order.get(i+1));
		}
		total += graph.distance(order.get(order.size()-1), 0);
		
		solution.setFitness(total);
	}

	@Override
	public void loadInstance(FileReader dataFileReader) 
	{
		maximize = false;
		
		List<TSPNode> nodes = null;
		
		try{
			BufferedReader file = new BufferedReader(dataFileReader);
			
			String line;
			String[] pair;
			String key, value;
			while(true){
				line = file.readLine();
				
				//Stop condition, this starts the node section
				if(line.equals("NODE_COORD_SECTION")){
					break;
				}
				
				pair = line.split(":");
				key = pair[0];
				value = pair[1];
				
				if(key.startsWith("DIMENSION"))
					this.nNodes = Integer.parseInt(value.trim());
			}
			
			nodes = new ArrayList<TSPNode>(nNodes);
			//Read nodes
			String[] nodeLine;
			TSPNode auxNode;
			double x,y;
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
		
		graph = new TSPGraph(nodes);
	}
	
	public int getNNodes()
	{
		return this.nNodes;
	}
}