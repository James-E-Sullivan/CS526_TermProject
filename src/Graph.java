import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import net.datastructures.*;
import sun.reflect.generics.tree.Tree;

public class Graph<E> extends UnsortedTableMap<Graph.Vertex<E>, E> {

    //---------- nested vertex class ----------
    protected static class Vertex<E> {
        private E name;
        private Integer directDistance;
        //private HashMap<Vertex<E>, Integer> edgeMap;
        private TreeMap<Vertex<E>, Integer> edgeMap;
        //private Vertex<E> shortEdge;


        // default constructor
        public Vertex(E n){
            name = n;
        }

        /*
        // detailed constructor
        public Vertex(E n, Integer dd, TreeMap<Vertex<E>, Integer> edges){
            name = n;
            directDistance = dd;
            edgeMap = edges;
        }

         */



        // accessor methods
        public E getName() {return name;}
        public Integer getDirectDistance() {return directDistance;}
        public TreeMap<Vertex<E>, Integer> getEdges() {return edgeMap;}

        /**
         * Iterates through edgeMap edges to find shortest edge.
         * @return Map.Entry (Vertex, weight pair) with smallest weight:
         */
        public Map.Entry<Vertex<E>, Integer> getShortEdge() {
            /*
            if (edgeMap.isEmpty())
                return null;
            Map.Entry<Vertex<E>, Integer> shortEdge = null; // returns null
            Integer smallWeight = -1;
            // iterates through Entries (edges) in edgeMap
            for (Map.Entry<Vertex<E>, Integer> edge : edgeMap.entrySet()){
                Integer weight = edge.getValue();   // weight of edge

                // find smallest value
                if (smallWeight == -1 || edge.getValue() < smallWeight){
                    smallWeight = edge.getValue();
                    shortEdge = edge;
                }
            }
            return shortEdge;

             */

            return edgeMap.firstEntry();
        }

        // update methods
        public void setName(E n) {name = n;}
        public void setDirectDistance(Integer d) {directDistance = d;}
        public void setEdgeMap(TreeMap<Vertex<E>, Integer> e) {edgeMap = e;}
        public void addEdge(Vertex<E> v, Integer weight){

        }
    } //----------- end of nested vertex class -----------



    /*
    protected Vertex<E> addVertex(E name, Integer directDistance, TreeMap<Vertex<E>, Integer> edges){
        size++;
        return new Vertex<E>(name, directDistance, edges);
    }

     */

    protected Vertex<E> addVertex(E name){
        size++;
        return new Vertex<E>(name);
    }

    private HashMap<String, Integer> nodeKey;
    private int[][] adjArray;
    private Vertex<E> destination = null;
    private Vertex<E> startVertex = null;
    private int size = 0;

    // accessor methods
    public int size(){return size;}
    public Vertex<E> getDestination(){return destination;}
    public Vertex<E> getStartVertex(){return startVertex;}
    public Vertex<E> getVertex(String name){
        if (Graph.)

        return
    }

    // update methods
    public void setAdjArray(int[][] inputArray){adjArray = inputArray;}
    public void setNodeKey(HashMap<String, Integer> inputNK){nodeKey = inputNK;}

    /*
    public void addVertices(int[][] inputArray, HashMap<String, Integer> inputNodeKey){
        for (int i=0; i < inputArray.length; i++){
            addVertex(inputNodeKey.k)
            for (int j=0; j < inputArray.length; j++){
                if (inputArray[i][j] != 0){

                }
            }
        }
    }

     */


    public void printShortestPath(Vertex<E> start, Vertex<E> end){
        Vertex<E> currentNode = start;
        System.out.println("Current node = " + currentNode.getName());

        System.out.println("Adjacent Nodes: ");
        for (Vertex<E> adjNode : currentNode.getEdges().keySet()){
            System.out.print(adjNode.getName() + ", ");
        }

        for (Vertex<E> adjNode : currentNode.getEdges().keySet()){
            System.out.println(adjNode.getName() + ": dd(" +
                    adjNode.getName() + ") = " + adjNode.getDirectDistance());
        }
    }

    public void algorithm1(Vertex<E> start, Vertex<E> end){
        printShortestPath(start, end);
    }

    @Override
    public E put(Vertex<E> key, E value) {
        return super.put(key, value);
    }

    @Override
    public E remove(Vertex<E> key) {
        return super.remove(key);
    }
}
