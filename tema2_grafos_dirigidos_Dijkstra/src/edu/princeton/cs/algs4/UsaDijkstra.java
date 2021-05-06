package edu.princeton.cs.algs4;

public class UsaDijkstra {
	public static void main(String[]args) {
		EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(6);
		
		ewd.addEdge(new DirectedEdge(0,1,1));
		ewd.addEdge(new DirectedEdge(0,2,3));
		ewd.addEdge(new DirectedEdge(1,2,3));
		ewd.addEdge(new DirectedEdge(1,3,10));
		ewd.addEdge(new DirectedEdge(2,1,1));
		ewd.addEdge(new DirectedEdge(2,3,2));
		ewd.addEdge(new DirectedEdge(2,4,10));
		ewd.addEdge(new DirectedEdge(3,4,1));
		ewd.addEdge(new DirectedEdge(3,5,5));
		ewd.addEdge(new DirectedEdge(4,3,2));
		ewd.addEdge(new DirectedEdge(4,5,2));
		
		DijkstraSP d = new DijkstraSP(ewd,0);
		System.out.println(d.pathTo(5));
		System.out.println(d.distTo(5));
	}
}
