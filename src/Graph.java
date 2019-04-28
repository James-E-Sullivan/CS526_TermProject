import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph<K> {

    //---------- nested vertex class ----------
    protected static class Vertex<K> {
        private K name;
        private Integer directDistance;
        //private HashMap<Vertex<K>, Integer> edgeMap;
        private TreeMap<Integer, Vertex<K>> edgeMap = new TreeMap<>();
        private LinkedList<Vertex<K>> edgeQueue = new LinkedList<>();
        //private Vertex<E> shortEdge;

        //default constructor
        public Vertex(){}

        public Vertex(K n, Integer dd){
            name = n;
            directDistance = dd;
        }


        // accessor methods
        public K getName() {return name;}
        public Integer getDirectDistance() {return directDistance;}
        public TreeMap<Integer, Vertex<K>> getEdges() {return edgeMap;}
        public LinkedList<Vertex<K>> getEdgeQueue(){return edgeQueue;}
        public Integer edgeWeight(Vertex<K> node){

            for (Map.Entry<Integer, Vertex<K>> edge: this.edgeMap.entrySet()) {  // iterate through edgeMap edges
                if (edge.getValue() == node)                                // if node == an edge node
                    return edge.getKey();                                   // return weight
            }
            return null;                                                    // otherwise return null
        }

        /**
         * finds shortest edge in edgeMap
         * @return Map.Entry (weight, Vertex pair) with smallest weight:
         */
        public Map.Entry<Integer, Vertex<K>> shortEdge() {
            return edgeMap.firstEntry();
        }

        /**
         * Finds edge with shortest directDistance
         * @return shortestDD: Map.Entry (directDistance, Vertex pair), or null
         */
        public Map.Entry<Integer, Vertex<K>> shortDD(){
            Map.Entry<Integer, Vertex<K>> shortestDD = edgeMap.firstEntry();     // initializes DD to first entry DD
            Iterator<Map.Entry<Integer, Vertex<K>>> edgeIterator = edgeMap.entrySet().iterator();

            // iterates through Map entries
            while(edgeIterator.hasNext()){
                Map.Entry<Integer, Vertex<K>> edge = (Map.Entry<Integer, Vertex<K>>)edgeIterator.next();
                if (edge.getValue().getDirectDistance() < shortestDD.getValue().getDirectDistance()){
                    shortestDD = edge;
                }
            }
            return shortestDD;
        }

        public Map.Entry<Integer, Vertex<K>> nextShortestDD(Integer i){
            Map.Entry<Integer, Vertex<K>> nextDD = edgeMap.higherEntry(this.shortDD().getKey());
            return nextDD;
        }

        // update methods
        public void setName(K n) {name = n;}
        public void setDirectDistance(Integer d) {directDistance = d;}
        public void setEdgeMap(TreeMap<Integer, Vertex<K>> e) {edgeMap = e;}
        public void setEdgeQueue(){
            for (Map.Entry<Integer, Vertex<K>> edge : this.edgeMap.entrySet()){
                edgeQueue.add(edge.getValue());
            }
        }
        public void addEdge(Integer weight, Vertex<K> v){
            edgeMap.put(weight, v);
        }
    } //----------- end of nested vertex class -----------

    // HashMap used as container for Name/Vertex pairs
    protected HashMap<K, Vertex<K>> graphContainer = new HashMap<>();


    /*
    protected Vertex<E> addVertex(E name, Integer directDistance, TreeMap<Vertex<E>, Integer> edges){
        size++;
        return new Vertex<E>(name, directDistance, edges);
    }

     */

    protected void addVertex(K name, Integer dd){
        Vertex<K> newVertex = new Vertex<>(name, dd);
        graphContainer.put(newVertex.getName(), newVertex);
        size++;
    }

    // find vertex by name
    public Vertex<K> findVertex(K name){
        if (graphContainer.containsKey(name)){
            return graphContainer.get(name);
        }
        return null;
    }

    protected Vertex<K> removeVertex(Vertex<K> v){
        size--;
        if (graphContainer.containsValue(v)){
            graphContainer.remove(v.getName());
            return v;
        }
        return null;
    }

    public void addVertexEdge(Vertex<K> targetVertex, Vertex<K> edgeVertex, Integer weight){
        targetVertex.addEdge(weight, edgeVertex);
    }



    private Vertex<K> destination = null;
    private Vertex<K> startVertex = null;
    private int size = 0;

    // accessor methods
    public int size(){return size;}
    public Vertex<K> getDestination(){return destination;}
    public Vertex<K> getStartVertex(){return startVertex;}

    public Map.Entry<Integer, Vertex<K>> getShortEdge(Vertex<K> v){
        return v.shortEdge();
    }

    public Map.Entry<Integer, Vertex<K>> getShortDD(Vertex<K> v){
        return v.shortDD();
    }

    public Integer getEdgeWeight(Vertex<K> a, Vertex<K> b){
        return a.edgeWeight(b);
    }

    // update methods
    public void setDestination(Vertex<K> v){destination = v;}
    public void setStartVertex(Vertex<K> v){startVertex = v;}

    /**
     * Resets edgeQueue to use for pathfinding
     */
    public void resetEdgeQueues(){
        for (Vertex<K> v : graphContainer.values()){
            v.setEdgeQueue();
        }
    }


    public void shortestPathWeight(Vertex<K> start, Vertex<K> end){
        System.out.println("Current node = " + start.getName());

        System.out.print("Adjacent nodes: ");
        for (Vertex<K> adjNode : start.getEdges().values()){
            System.out.print(adjNode.getName() + ", ");
        }

        for (Vertex<K> edgeNode : start.getEdges().values()){

            Integer ddValue = edgeNode.directDistance;
            Integer edgeWeight = getEdgeWeight(start, edgeNode);

            System.out.print("\n" + edgeNode.getName() + ": w(" +
                    start.getName() + ", " + edgeNode.getName() +
                    ") + " + "dd(" + edgeNode.getName() + ") = " +
                    edgeWeight + " + " + ddValue + " = " + (edgeWeight + ddValue));
        }
    }

    public void shortestPathDD(Vertex<K> start, Vertex<K> end){
        Vertex<K> trailerNode = start;
        LinkedList<Vertex<K>> fullSequence = new LinkedList<>();
        LinkedList<Vertex<K>> shortSequence = new LinkedList<>();
        Integer totalLength = 0;
        fullSequence.add(start);
        shortSequence.add(start);

        resetEdgeQueues();      // make sure edgeQueue values contain all edges

        Vertex<K> nextNode = trailerNode.shortDD().getValue();        // nextNode chosen by shortestEdge
        trailerNode.edgeQueue.removeFirst();                          // remove first value from Vertex edgeQueue

        while (nextNode != end && nextNode != null){
            fullSequence.add(nextNode);         // add node to full sequence in all cases



            //TEST
            System.out.println("\nTESTING>>>>>>>>>>>>>");
            System.out.println("Trailer Node: " + trailerNode.getName());
            System.out.print("Trailer Node Queue: ");
            for (Vertex<K> v : trailerNode.edgeQueue)
                System.out.print(v.getName() + ", ");
            System.out.println("\nNext Node: " + nextNode.getName());
            System.out.print("Next Node Queue: ");
            for (Vertex<K> v : nextNode.edgeQueue)
                System.out.print(v.getName() + ", ");
            System.out.print("\nFull Sequence: ");
            for (Vertex<K> v : fullSequence)
                System.out.print(v.getName() + "->");






            if (nextNode.edgeQueue.size() == 0){
                trailerNode.edgeQueue.removeFirst();
                trailerNode = nextNode;
                nextNode = shortSequence.getLast();
            }

            else if (shortSequence.contains(nextNode)){

                totalLength -= trailerNode.edgeWeight(nextNode);    // remove length from last node to nextNode

                Vertex<K> temp = shortestDDQueue(trailerNode);
                trailerNode = nextNode;
                nextNode = temp;
            }

            else if (nextNode.getEdgeQueue() != null){






                totalLength += trailerNode.edgeWeight(nextNode);
                shortSequence.add(nextNode);

                Vertex<K> temp = shortestDDQueue(trailerNode);
                trailerNode = nextNode;
                trailerNode.edgeQueue.removeFirst();
                nextNode = shortestDDQueue(temp);

            }

        }
        // Path has reached the end
        totalLength += shortSequence.getLast().edgeWeight(nextNode);
        fullSequence.add(end);
        shortSequence.add(end);

        System.out.print("Sequence of all nodes: ");
        for (Vertex<K> v : fullSequence){
            if (v.getName() == end.getName())
                System.out.println(v.getName());
            else
                System.out.print(v.getName() + " -> ");
        }

        System.out.print("Shortest path: ");
        for (Vertex<K> v : shortSequence){
            if (v.getName() == end.getName())
                System.out.println(v.getName());
            else
                System.out.print(v.getName() + " -> ");
        }

        System.out.println("Shortest path length: " + totalLength);

    }


    private Vertex<K> shortestDDQueue(Vertex<K> v){
        Vertex<K> shortestDD = v.edgeQueue.getFirst();
        for (Vertex<K> node : v.edgeQueue){
            if (node.getDirectDistance() < shortestDD.getDirectDistance())
                shortestDD = node;
        }
        return shortestDD;
    }


    public void algorithm1(){
        shortestPathDD(startVertex, destination);
    }

    public void algorithm2(){
        shortestPathWeight(startVertex, destination);
    }




}
