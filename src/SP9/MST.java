/**
 * @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 * MST - SP9
 * Ver 1.0: 2021/04/30
 */

package SP9;

import SP9.Graph.*;
import SP9.BinaryHeap.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;

    //To store maximum distance of a node
    static final int INFINITY = Integer.MAX_VALUE;

    MST(Graph g) {
        super(g, new MSTVertex((Vertex) null));
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {

    	int index;
    	boolean seen;
    	MSTVertex parent; //to store the parent of final spanning tree nodes
    	int distance; //to store the minimum values of distance in nodes
    	Vertex vertex;

        MSTVertex(Vertex u) {
        }

        MSTVertex(MSTVertex u) {  // for prim2
            u.seen = false;
            u.parent = null;
            u.distance = INFINITY;
        }

        public MSTVertex make(Vertex u) {
            return new MSTVertex(u);
        }

        /**
         * To store the indices of heap
         */
        public void putIndex(int index) {
        	this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public int compareTo(MSTVertex other) {
            if(this.distance < other.distance){
                return -1;
            }else if(this.distance > other.distance){
                return 1;
            }else{
                return 0;
            }
        }
    }

    public long kruskal() {
        algorithm = "Kruskal";
        Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        return wmst;
    }

    public long boruvka() {
        algorithm = "Boruvka";

        wmst = 0;

        return wmst;
    }


    /**
     * Implementation 2 of Prim's
     */
    public long prim2(Vertex s) {
        algorithm = "indexed heaps";
        mst = new LinkedList<>();

        IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());

        //Initialize the vertices
        for(Vertex u : g){
            MSTVertex mstVertex = get(u);
            mstVertex.seen = false;
            mstVertex.parent = null;
            mstVertex.distance = INFINITY;
            mstVertex.vertex = u;
            mstVertex.index = u.getName()-1;
            if(u == s){
                mstVertex.distance = 0;
                mstVertex.seen = true;
            }

            //add in indexed heap
        	q.add(mstVertex);
		}

        wmst = 0;


        //update values of minimum spanning tree list
        while(!q.isEmpty()){
            //remove minimum distance node from the heap
        	MSTVertex u = q.remove();
        	u.seen = true;

        	//update the minimum tree distance
        	wmst += u.distance;
        	if(u.parent != null){
                for(Edge e: g.incident(u.vertex)){
                    if(e.otherEnd(u.vertex).equals(u.parent.vertex)){
                        mst.add(e);
                    }
                }
            }


        	//loop through all edges of the node to find minimum distances
        	for(Edge e: g.incident(u.vertex)){
        	    Vertex v = e.otherEnd(u.vertex);
        	    MSTVertex vMstVertex = get(v);
        	    if(!vMstVertex.seen && e.weight < vMstVertex.distance){
        	        vMstVertex.distance = e.weight;
        	        vMstVertex.parent = u;

        	        //percolate up
        	        q.decreaseKey(vMstVertex);
                }
			}
		}
        return wmst;
    }

    public long prim1(Vertex s) {
        algorithm = "PriorityQueue<Edge>";
        mst = new LinkedList<>();
        wmst = 0;
        PriorityQueue<Edge> q = new PriorityQueue<>();
        return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
        MST m = new MST(g);
        switch (choice) {
            case 0:
                m.boruvka();
                break;
            case 1:
                m.prim1(s);
                break;
            case 2:
                m.prim2(s);
                break;
            case 3:
                m.kruskal();
                break;
            default:

                break;
        }
        return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
//        String string = "7 12   1 2 4   1 3 26   1 4 14   2 5 18   2 4 12   3 4 30   3 6 16   4 5 2   4 6 3   5 6 10   5 7 8   6 7 5";
        Scanner in;
        int choice = 2;  // prim2
        if (args.length == 0 || args[0].equals("-")) {
            in = new Scanner(System.in);
        } else {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        }

        if (args.length > 1) {
            choice = Integer.parseInt(args[1]);
        }

//        in = new Scanner(string);

        Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

        Timer timer = new Timer();
        MST m = mst(g, s, choice);
        System.out.println(m.algorithm + "\n" + m.wmst);
//        System.out.println("MST : "+m.mst.toString());
        System.out.println(timer.end());
    }
}
