import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.LinkedList;

import net.datastructures.*;
import sun.reflect.generics.tree.Tree;

public class Graph<V> extends LinkedList<V> {

    //---------- nested vertex class ----------
    protected static class Vertex<V> {
        private V name;
        private Integer directDistance;
        //private HashMap<Vertex<K>, Integer> edgeMap;
        private TreeMap<Vertex<V>, Integer> edgeMap;
        //private Vertex<E> shortEdge;


        // default constructor
        public Vertex(V n){
            name = n;
        }

        /*
        // detailed constructor
        public Vertex(K n, Integer dd, TreeMap<Vertex<K>, Integer> edges){
            name = n;
            directDistance = dd;
            edgeMap = edges;
        }

         */



        // accessor methods
        public V getName() {return name;}
        public Integer getDirectDistance() {return directDistance;}
        public TreeMap<Vertex<V>, Integer> getEdges() {return edgeMap;}

        /**
         * Iterates through edgeMap edges to find shortest edge.
         * @return Map.Entry (Vertex, weight pair) with smallest weight:
         */
        public Map.Entry<Vertex<V>, Integer> getShortEdge() {
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
        public void setName(V n) {name = n;}
        public void setDirectDistance(Integer d) {directDistance = d;}
        public void setEdgeMap(TreeMap<Vertex<V>, Integer> e) {edgeMap = e;}
        public void addEdge(Vertex<V> v, Integer weight){

        }
    } //----------- end of nested vertex class -----------


    protected LinkedList<Vertex<V>> graphContainer = new LinkedList<>();


    /*
    protected Vertex<E> addVertex(E name, Integer directDistance, TreeMap<Vertex<E>, Integer> edges){
        size++;
        return new Vertex<E>(name, directDistance, edges);
    }

     */

    protected void addVertex(V name){
        Vertex<V> newVertex = new Vertex<>(name);
        graphContainer.add(newVertex);
        size++;
    }

    protected Vertex<V> removeVertex(Vertex<V> v){
        size--;
        if (graphContainer.remove(v))
            return v;
        else
            return null;
    }

    private HashMap<String, Integer> nodeKey;
    private int[][] adjArray;
    private Vertex<V> destination = null;
    private Vertex<V> startVertex = null;
    private int size = 0;

    // accessor methods
    public int size(){return size;}
    public Vertex<V> getDestination(){return destination;}
    public Vertex<V> getStartVertex(){return startVertex;}
    public Vertex<V> getVertex(String name){

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


    public void printShortestPath(Vertex<V> start, Vertex<V> end){
        Vertex<V> currentNode = start;
        System.out.println("Current node = " + currentNode.getName());

        System.out.println("Adjacent Nodes: ");
        for (Vertex<V> adjNode : currentNode.getEdges().keySet()){
            System.out.print(adjNode.getName() + ", ");
        }

        for (Vertex<V> adjNode : currentNode.getEdges().keySet()){
            System.out.println(adjNode.getName() + ": dd(" +
                    adjNode.getName() + ") = " + adjNode.getDirectDistance());
        }
    }

    public void algorithm1(Vertex<V> start, Vertex<V> end){
        printShortestPath(start, end);
    }


}
