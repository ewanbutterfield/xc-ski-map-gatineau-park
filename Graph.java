//https://algorithms.tutorialhorizon.com/weighted-graph-implementation-java/

import java.util.LinkedList;
public class Graph {
    private LinkedList<Edge>[] adjacencylist;
    public Graph(int numNodes) {
        adjacencylist = new LinkedList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjacencylist[i] = new LinkedList<>();
        }
    }
    public void addEgde(int source, int destination) {
        Edge edge = new Edge(source, destination);
        adjacencylist[source].addFirst(edge);
    }
    public int getDestinationNode(int source, int ConnectionNum) {
        return adjacencylist[source].get(ConnectionNum).getDestination();
    }
    public int getNumPossibleDestinations(int source) {
        return adjacencylist[source].size();
    }
}
