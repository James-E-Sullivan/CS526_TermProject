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
        private TreeMap<Integer, Vertex<K>> edgeMap = new TreeMap<>();
        private LinkedList<Vertex<K>> edgeQueue = new LinkedList<>();
        private Vertex<K> prevNode;

        //default constructor
        public Vertex(){}

        // constructor with name and direct distance
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

    // Graph class variables
    private Vertex<K> destination = null;   // destination must be added with update method
    private Vertex<K> startVertex = null;   // start vertex must be added with update method
    private int size = 0;                   // size starts at 0

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
    // find vertex by name
    public Vertex<K> findVertex(K name){
        if (graphContainer.containsKey(name)){
            return graphContainer.get(name);
        }
        return null;
    }

    // update methods
    public void setDestination(Vertex<K> v){destination = v;}
    public void setStartVertex(Vertex<K> v){startVertex = v;}
    public void addVertexEdge(Vertex<K> targetVertex, Vertex<K> edgeVertex, Integer weight){
        targetVertex.addEdge(weight, edgeVertex);
    }
    protected Vertex<K> removeVertex(Vertex<K> v){
        size--;
        if (graphContainer.containsValue(v)){
            graphContainer.remove(v.getName());
            return v;
        }
        return null;
    }
    protected void addVertex(K name, Integer dd){
        Vertex<K> newVertex = new Vertex<>(name, dd);
        graphContainer.put(newVertex.getName(), newVertex);
        size++;
    }

    /**
     * Resets edgeQueue to use for pathfinding
     */
    public void resetEdgeQueues(){
        for (Vertex<K> v : graphContainer.values()){
            v.setEdgeQueue();
        }
    }


    public void shortestPathWeight(Vertex<K> start, Vertex<K> end){
        Vertex<K> trailerNode = start;
        LinkedList<Vertex<K>> fullSequence = new LinkedList<>();
        LinkedList<Vertex<K>> shortSequence = new LinkedList<>();
        Integer totalLength = 0;
        fullSequence.add(start);
        shortSequence.add(start);

        resetEdgeQueues();      // make sure edgeQueue values contain all edges

        Vertex<K> nextNode = smallestWeightQueue(trailerNode);        // nextNode chosen by shortestEdge
        trailerNode.edgeQueue.getFirst();                          // remove first value from Vertex edgeQueue

        while (nextNode != end && nextNode != null){
            fullSequence.add(nextNode);         // add node to full sequence in all cases
            reorderEdgeQueue(trailerNode, nextNode);

            if (nextNode.edgeQueue.size() == 0){
                shortSequence.add(nextNode);
                trailerNode = nextNode;
                nextNode = nextNode.prevNode;
            }

            else if (shortSequence.contains(nextNode)){
                totalLength -= trailerNode.edgeWeight(nextNode);    // remove length from last node to nextNode
                shortSequence.removeLast();
                trailerNode = nextNode;
                nextNode = smallestWeightQueue(nextNode);
                trailerNode.edgeQueue.removeFirst();
            }

            else if (nextNode.getEdgeQueue().size() != 0){
                totalLength += trailerNode.edgeWeight(nextNode);
                shortSequence.add(nextNode);
                trailerNode = nextNode;
                nextNode = smallestWeightQueue(nextNode);
                trailerNode.edgeQueue.removeFirst();
            }
        }// Path has reached the end
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

    public void shortestPathDD(Vertex<K> start, Vertex<K> end){
        Vertex<K> trailerNode = start;
        LinkedList<Vertex<K>> fullSequence = new LinkedList<>();
        LinkedList<Vertex<K>> shortSequence = new LinkedList<>();
        Integer totalLength = 0;
        fullSequence.add(start);
        shortSequence.add(start);

        resetEdgeQueues();      // make sure edgeQueue values contain all edges

        Vertex<K> nextNode = trailerNode.shortDD().getValue();        // nextNode chosen by shortest direct distance
        trailerNode.edgeQueue.getFirst();                          // remove first value from Vertex edgeQueue

        while (nextNode != end && nextNode != null){
            fullSequence.add(nextNode);         // add node to full sequence in all cases
            reorderEdgeQueue(trailerNode, nextNode);

            if (nextNode.edgeQueue.size() == 0){
                trailerNode = nextNode;
                shortSequence.add(nextNode);
                nextNode = nextNode.prevNode;
            }

            else if (shortSequence.contains(nextNode)){
                totalLength -= trailerNode.edgeWeight(nextNode);    // remove length from last node to nextNode
                shortSequence.removeLast();
                trailerNode = nextNode;
                nextNode = shortestDDQueue(nextNode);
                trailerNode.edgeQueue.removeFirst();
            }

            else if (nextNode.getEdgeQueue() != null){
                totalLength += trailerNode.edgeWeight(nextNode);
                shortSequence.add(nextNode);
                trailerNode = nextNode;
                nextNode = shortestDDQueue(nextNode);
                trailerNode.edgeQueue.removeFirst();
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

    private Vertex<K> smallestWeightQueue(Vertex<K> v){
        Vertex<K> smallestEdge = v.edgeQueue.getFirst();
        Integer smallWeight = smallestEdge.edgeWeight(v) + smallestEdge.getDirectDistance();
        for(Vertex<K> node : v.edgeQueue){
            if ((node.edgeWeight(v) + node.getDirectDistance()) < smallWeight){
                smallestEdge = node;
            }
        }
        return smallestEdge;
    }

    /**
     * Reorders edgeQueue of Vertex so its
     * trailing node is the last Vertex in its queue.
     * @param trailer: trailing node
     * @param next: next node
     */
    protected void reorderEdgeQueue(Vertex<K> trailer, Vertex<K> next){
        if (next.edgeQueue.contains(trailer)){
            int tempIndex = next.edgeQueue.indexOf(trailer);
            Vertex<K> temp = next.edgeQueue.remove(tempIndex);
            next.prevNode = temp;
        }
    }

    public void algorithm1(){
        shortestPathDD(startVertex, destination);
    }

    public void algorithm2(){
        shortestPathWeight(startVertex, destination);
    }
}
