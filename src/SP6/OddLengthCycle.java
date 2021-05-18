/**
 * @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 * Find Odd Cycles using BFS - SP6
 * Ver 1.0: 2021/03/25
 */

package SP6;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class OddLengthCycle {

    Set<Graph.Vertex> components = new HashSet<>(); //to track vertices eligible for BFS.
    Set<Integer> scannedVertex = new HashSet<>(); //to track all the scanned Vertices.


    /**
     * @param g for original graph
     * @param src for source vertex
     * @return list of all the vertices which forms odd cycle otherwise NULL
     */
    public List<Graph.Vertex> oddCycle(Graph g, Graph.Vertex src){
        BFSOO bfs = new BFSOO(g);

        //run bfs algorithm on src Vertex.
        bfs.bfs(src);

        //map to find two vertices for which distance is same in BFS.
        Map<Graph.Vertex, Integer> vertexMap = new HashMap<>();

        for(Graph.Vertex u: g){
            int dis = bfs.getDistance(u);

            //find equal distance vertices from BFS list.
            if(dis != BFSOO.INFINITY && bfs.getSeen(u)){
                if(components.contains(u)){
                    components.remove(u);
                }
                scannedVertex.add(u.getName());
                if(vertexMap.containsValue(dis)){
                    List<Graph.Vertex> vList = vertexMap.entrySet()
                            .stream()
                            .filter(entry -> Objects.equals(entry.getValue(), dis))
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());
                    if(checkEdgeBetweenVertices(g, u, vList.get(0))){
                        return findAllVertices(bfs, u, vList.get(0));
                    }
                }else{
                    vertexMap.put(u, dis);
                }
            }else if(dis == BFSOO.INFINITY && !scannedVertex.contains(u.getName())){
                components.add(u);
            }
        }

        //run BFS on different set of components.
        for(Graph.Vertex u: components){
            return oddCycle(g, u);
        }

        //return null for Bipartite graph.
        return null;

    }

    /**
     * @param g for original graph
     * @param u for vertex1
     * @param v for vertex2
     * @return is there an edge between u and v?
     */
    private boolean checkEdgeBetweenVertices(Graph g, Graph.Vertex u, Graph.Vertex v){
        for(Graph.Edge e: g.incident(u)){
            if(e.otherEnd(u) == v){
                return true;
            }
        }
        return false;
    }


    /**
     * @param bfs for BFSOO object
     * @param u for vertex1
     * @param v for vertex2
     * @return list of all vertices which forms cycle from u to v.
     */
    private List<Graph.Vertex> findAllVertices(BFSOO bfs, Graph.Vertex u, Graph.Vertex v){
        LinkedList<Graph.Vertex> result = new LinkedList<>();
        result.add(u);
        Graph.Vertex first = u;
        Graph.Vertex second = v;
        while(bfs.getParent(first) != bfs.getParent(second)){
            result.add(bfs.getParent(first));
            first = bfs.getParent(first);
            second = bfs.getParent(second);
        }

        Graph.Vertex root = bfs.getParent(first);
        result.add(root);
        second = v;
        result.addFirst(second);


        while(bfs.getParent(second) != root){
            result.addFirst(bfs.getParent(second));
            second = bfs.getParent(second);
        }
        result.addFirst(root);

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {

        String string = "8 9   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   6 8 -1    7 8 1 1";
//        String string = "5 5   1 2 2   2 3 1   3 4 1   4 5 1   5 1 1 1";
//        String string = "8 10   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   7 6 -1   7 8 2   8 1 2 1";
//        String string = "5 5   1 2 2   1 3 3   2 4 5   3 5 4   4 5 1 1";
//        String string = "6 6   1 2 2   2 3 3   3 4 5   4 5 3   5 6 1   6 1 -7 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readGraph(in);

        int s = in.nextInt();

        OddLengthCycle oddLengthCycle = new OddLengthCycle();
        List<Graph.Vertex> oddCycleList = oddLengthCycle.oddCycle(g, g.getVertex(s));

        if(oddCycleList == null){
            System.out.println("Graph is Bipartite");
        }else{
            StringBuilder printString = new StringBuilder();
            for(Graph.Vertex u: oddCycleList){
                printString.append(u.getName()).append("->");
            }
            System.out.println(printString.substring(0, printString.length()-2));
        }
    }

}
