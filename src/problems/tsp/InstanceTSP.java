package problems.tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.IInstance;
import problems.ISolution;

public class InstanceTSP implements IInstance 
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
		System.err.println(order.size());
		total += graph.distance(order.get(order.size()-1), 0);
		
		solution.setFitness(total);
	}

	@Override
	public IInstance loadInstance(FileReader dataFileReader) 
	{
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
		return null;
	}
	
	public int getNNodes(){
		return this.nNodes;
	}
	
	public static void main(String[] args){
		File f = new File("examples/TSPInstances/berlin52.tsp");
		
		try{
			FileReader fr = new FileReader(f);
			IInstance inst = new InstanceTSP();
			
			inst.loadInstance(fr);
		} catch(FileNotFoundException e){
			System.out.println("File does not exist");
		}
	}
}