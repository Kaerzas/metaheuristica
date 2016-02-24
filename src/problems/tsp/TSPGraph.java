package problems.tsp;

import java.util.ArrayList;
import java.util.List;

public class TSPGraph {
	
	private double graph[][];
	
	public TSPGraph(List <TSPNode> nodes){
		
		this.graph = new double[nodes.size()][nodes.size()];
		
		for(int i = 0; i < nodes.size(); ++i){
			for(int j = 0; j < nodes.size(); ++j){			
				graph[i][j] = nodes.get(i).distance(nodes.get(j));
			}
		}	
	}
	
	public double distance(int a, int b) {
		return this.graph[a][b];
	}
	
	public static void main(String args[]) {

        // Vectores
        TSPNode n1 = new TSPNode(45, 76);
        TSPNode n2 = new TSPNode(32, 48);
        
        List <TSPNode> lista = new ArrayList <TSPNode>();
        
        lista.add(n1);
        lista.add(n2);
        
        TSPGraph grafo = new TSPGraph(lista);
        
        for(int i = 0; i < 2; i++){
        	for(int j = 0; j < 2; j++){
        		System.out.print(grafo.distance(i,j) + "\t");
        	}
        	System.out.println();
        }
        
	}
}


