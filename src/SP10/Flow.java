/**
 * @author Tarun Punhani(txp190029) and Vishal Puri(vxp190034)
 *  Flow and Postman Tour - SP10
 * Ver 1.0: 2021/05/16
 */

package SP10;// Starter code for max flow

import SP10.Graph.*;

import java.util.*;

public class Flow {

    final int HASHCONSTANT = 25;

    Graph givenGraph; //store the given graph in local variable

    //node variable arrays
    int[] heightOfVertex;
    int[] excessOfVertex;

    HashMap<Long, Integer> c;
    HashMap<Long, Integer> f;
    HashMap<Edge, Integer> capacity;
    HashMap<Integer, HashSet<Long>> incident;

    Vertex s, t;


    public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
        this.givenGraph = g;
        this.capacity = capacity;
        this.s = s;
        this.t = t;
        c = new HashMap<>();
        f = new HashMap<>();
    }

    // Return max flow found. Use either FIFO or Priority queue.
    public int preflowPush() {
        // initialize visited vertices
        boolean[] visitedVertices = new boolean[givenGraph.size()];
        this.excessOfVertex = new int[givenGraph.size()];
        this.heightOfVertex = new int[givenGraph.size()];

        heightOfVertex[t.getIndex()] = 0;
        visitedVertices[t.getIndex()] = true;

        this.incident = new HashMap<>();

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(t.getIndex());
        while (!queue.isEmpty()) {
            int indexOfCurrentVertex = queue.poll();
            for (Edge e : givenGraph.inEdges(givenGraph.getVertex(indexOfCurrentVertex + 1))) {
                if (!visitedVertices[e.fromVertex().getIndex()]) {
                    visitedVertices[e.fromVertex().getIndex()] = true;
                    heightOfVertex[e.fromVertex().getIndex()] = heightOfVertex[indexOfCurrentVertex] + 1;
                    queue.add(e.fromVertex().getIndex());
                }
            }
        }
        heightOfVertex[s.getIndex()] = givenGraph.size();




        for (Edge e : givenGraph.getEdgeArray()) {
            long uniqueNumber = flowHashCalculation(e);
            c.put(uniqueNumber, capacity.get(e));
            f.put(uniqueNumber, 0);
        }
        for (Edge e : givenGraph.getEdgeArray()) {
            long uniqueNumber = revereHashCalculation(e);
            if (!c.containsKey(uniqueNumber))
                c.put(uniqueNumber, 0);
        }


        for (Edge e : givenGraph.getEdgeArray()) {
            int from = e.fromVertex().getIndex();
            int to = e.toVertex().getIndex();


            long forwardHash = flowHashCalculation(e);
            long hashOfReverse = revereHashCalculation(e);

            if (!incident.containsKey(from))
                incident.put(from, new HashSet<>());


            if (!incident.containsKey(to))
                incident.put(to, new HashSet<>());


            incident.get(from).add(forwardHash);
            incident.get(to).add(hashOfReverse);


            if (!f.containsKey(hashOfReverse)) {
                f.put(hashOfReverse, 0);
            }

        }

        for (long e : incident.get(s.getIndex())) {

            int to = (int) (e >> HASHCONSTANT);
            int from = (int) (e - ((e >> HASHCONSTANT) << HASHCONSTANT));

            excessOfVertex[from] -= c.get(e);
            excessOfVertex[to] += c.get(e);

            f.put(e, c.get(e));
        }

        Queue<Integer> excessQueue = new LinkedList<Integer>();
        boolean[] activeVertices = new boolean[givenGraph.size()];


        for (int i = 0; i < excessOfVertex.length; i++) {
            if (excessOfVertex[i] > 0 && s.getIndex() != i && t.getIndex() != i) {
                excessQueue.add(i);
                activeVertices[i] = true;
            }
        }


        while (!excessQueue.isEmpty()) {

            int currentVertexNumber = excessQueue.poll();
            activeVertices[currentVertexNumber] = false;

            for (long edge : incident.get(currentVertexNumber)) {
                int to = (int) (edge >> HASHCONSTANT);
                if (heightOfVertex[currentVertexNumber] == heightOfVertex[to] + 1) {
                    int minExcess = Math.min(excessOfVertex[currentVertexNumber], getCapacityOfEdge(edge));
                    f.put(edge, f.get(edge) + minExcess);
                    excessOfVertex[to] += minExcess;

                    if (excessOfVertex[to] > 0 && !activeVertices[to] && to != s.getIndex() && to != t.getIndex()) {
                        activeVertices[to] = true;
                        excessQueue.add(to);
                    }
                    excessOfVertex[currentVertexNumber] -= minExcess;
                }
            }

            if (excessOfVertex[currentVertexNumber] > 0) {
                activeVertices[currentVertexNumber] = true;
                excessQueue.add(currentVertexNumber);

                int minValue = Integer.MAX_VALUE;
                for (long edge : incident.get(currentVertexNumber)) {
                    if (getCapacityOfEdge(edge) != 0) {
                        minValue = Math.min(minValue, heightOfVertex[(int) (edge >> HASHCONSTANT)]);
                    }
                }
                heightOfVertex[currentVertexNumber] = minValue + 1;
            }
        }
        return excessOfVertex[t.getIndex()];
    }

    /**
     * find the capacity of an edge
     * @param e is the edge
     * @return the capacity of edge
     */
    public int getCapacityOfEdge(long e) {
        long to = e >> HASHCONSTANT;
        long from = e - ((e >> HASHCONSTANT) << HASHCONSTANT);
        long reverse = (from << HASHCONSTANT) + to;
        return c.get(e) - f.get(e) + f.get(reverse);
    }


    /**
     * Find unique hash number for an edge
     * @param e is the edge
     * @return unique number of edge
     */
    public long flowHashCalculation(Edge e) {
        return ((long) e.toVertex().getIndex() << HASHCONSTANT) + e.fromVertex().getIndex();
    }


    /**
     * Find unique hash number for an reverse edge
     * @param e is the edge
     * @return unique number of reverse edge
     */
    public long revereHashCalculation(Edge e) {
        return ((long) e.fromVertex().getIndex() << HASHCONSTANT) + e.toVertex().getIndex();
    }


    /**
     * Get all the vertices after cut
     * @param visited is the boolean status of vertex
     * @return set of vertices after cut
     */
    public Set<Vertex> getVerticesAftercut(boolean visited) {
        //define hashmap to store the result
        HashSet<Vertex> result = new HashSet<>();
        boolean[] visitedVertices = new boolean[givenGraph.size()];

        //initialize source visited state
        visitedVertices[s.getIndex()] = true;


        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s.getIndex());

        //run the loop till queue is empty
        while (!q.isEmpty()) {
            int currentVertex = q.poll();
            for (Edge e : givenGraph.outEdges(givenGraph.getVertex(currentVertex))) {
                long hash = flowHashCalculation(e);
                if (getCapacityOfEdge(hash) > 0) {
                    visitedVertices[e.toVertex().getIndex()] = true;
                    q.add(e.toVertex().getIndex());
                }
            }
        }
        for (int i = 0; i < visitedVertices.length; i++) {
            if (visitedVertices[i] == visited)
                result.add(givenGraph.getVertexArray()[i]);
        }
        return result;
    }

    // flow going through edge e
    public int flow(Edge e) {
        return f.get(flowHashCalculation(e));
    }

    // capacity of edge e
    public int capacity(Edge e) {
        return getCapacityOfEdge(flowHashCalculation(e));
    }

    /* After maxflow has been computed, this method can be called to
       get the "S"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutS() {
        return getVerticesAftercut(true);
    }

    /* After maxflow has been computed, this method can be called to
       get the "T"-side of the min-cut found by the algorithm
    */
    public Set<Vertex> minCutT() {
        return getVerticesAftercut(false);
    }
}
