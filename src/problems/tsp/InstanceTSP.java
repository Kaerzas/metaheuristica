package problems.tsp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import problems.IInstance;
import problems.ISolution;

public class InstanceTSP implements IInstance 
{
	private TSPGraph graph;
	
	@Override
	public void evaluate(ISolution solution) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public IInstance loadInstance(FileReader dataFileReader) 
	{
		List<TSPNode> nodes = null;
		
		try{
			BufferedReader file = new BufferedReader(dataFileReader);
			
			int nNodes=0;
			
			String line;
			String[] pair;
			String key, value;
			while(true){
				line = file.readLine();
				
				//Stop condition, this starts the node section
				if(line == "NODE_COORD_SECTION"){
					break;
				}
				
				pair = line.split(" : ");
				key = pair[0];
				value = pair[1];
				
				if(key == "DIMENSION")
					nNodes = Integer.parseInt(value);
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
}