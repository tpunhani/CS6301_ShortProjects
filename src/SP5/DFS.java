/**
 * @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 * Depth First Search Applications - SP5
 * Ver 1.0: 2021/03/12
 */

package SP5;


import SP5.Graph.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    int START = 0; //to check if the dfs is started
    int INPROGRESS = 1; //to check if the dfs is in progress
    int END = 2; //to check if dfs is finished

    public static class DFSVertex implements Factory {
        int cno;

        public DFSVertex(Vertex u) {
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    public static DFS depthFirstSearch(Graph g) {
        return null;
    }

    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {

        int[] status = new int[g.n];
        LinkedList<Vertex> result = new LinkedList<Vertex>();
        boolean hasCycle = false;
        for (int id = 0; id < g.n; id += 1) {

            if (status[id] == START) {
                hasCycle = hasCycle |(!dfs(id, status, result));
            }
        }
        if (hasCycle) {
            return null;
        }
        return result;
    }

    //returns false if Cycle Detected , returns True if successfull.
    public boolean dfs(int id, int[] status, LinkedList<Vertex> result) {

        status[id] = INPROGRESS;
        AdjList adj = g.adj(id);
        for (Edge e : adj.outEdges) {

            if (status[e.to.getIndex()] == INPROGRESS) {
                return false;
            } else if (status[e.to.getIndex()] == START) {
                if (!dfs(e.to.getIndex(), status, result)){
                    return false;
                }
            }
        }
        result.addFirst(g.getVertex(id + 1));
        status[id] = END;
        return true;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the connected components algorithm, the component no of each
    // vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g
    // is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        //String string = "7 7   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 6 7   6 1 1";
        String string = "4 3   1 2 2   2 3 3   3 4 5 0";

        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph(in, true);
        g.printGraph(false);

        DFS df = new DFS(g);
        List<Vertex> finishList = df.topologicalOrder1() ;
        if(finishList == null){
            System.out.println("Cycle Detected!");
        }
        else{
            System.out.println("Topological Order: "+finishList);
        }

    }
}