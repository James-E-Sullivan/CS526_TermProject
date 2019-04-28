import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class project {

    /**
     * Reads lines from a text file to create a Graph object.
     * Assumes that the first column and first row correspond to node (vertex) names.
     * Assumes that graph is made-up of positive integers, separated by spaces.
     * For each node name, a Graph.Vertex object is created, and the positive integers
     * are set as the edge weight between Vertex objects.
     * @return inputGraph
     */
    private static Graph<String> readGraph(){

        Graph<String> inputGraph = new Graph<>();       // new Graph object, Vertices to be added
        String lineFromFile;

        try{
            FileInputStream inFile = new FileInputStream("resources/graph_input.txt");  // graph_input file
            Scanner fileScan = new Scanner(inFile);
            String firstLine = fileScan.nextLine();

            String[] vertexNames = firstLine.split("\\s+");   // 1st row from txt file; names of nodes
            int[][] adjArray = new int[vertexNames.length - 1][];   // Columns == # of vertex names, 2D array

            HashMap<Integer, String> vertexKey = new HashMap<>();   // HashMap for column and row index/name

            // fills an array with the name of nodes
            for (int i=1; i<vertexNames.length; i++){
                vertexKey.put(i-1, vertexNames[i]);
            }

            int i = 0;  // index of row in adjArray
            while (fileScan.hasNextLine()){     // iterate through each row

                lineFromFile = fileScan.nextLine();
                String[] splitLine = lineFromFile.split("\\s+");    // split line by spaces, into array

                int[] row = new int[splitLine.length];          // int[] used to store edge values

                for (int j=1; j < splitLine.length; j++){       // skip first index (letter)
                    row[j-1] = Integer.valueOf(splitLine[j]);   // for each column, store edge value in row[]
                }
                adjArray[i] = row;  // adds edge values (in form of row[]) to each row in adjArray
                i++;                // iterate i to move into next row
            }
            inFile.close();

            // HashMap used to store node-name/direct-distance pairs
            HashMap<String, Integer> directDistanceMap = readDirectDistance();      // read in DD HashMap

            // create Graph vertices
            for (int row=0; row < adjArray.length; row++){
                String vertexName = vertexKey.get(row);         // get name from vertexKey, corresponding to row
                Integer vertexDD = directDistanceMap.get(vertexName);   // gets DD value from DD HashMap
                inputGraph.addVertex(vertexName, vertexDD);     // Creates Graph Vertex w/ name and direct distance
            }

            // add edge/weight entries into edgeMap for each Vertex
            for (int row=0; row < adjArray.length; row++){
                for (int column=0; column < adjArray[row].length; column++){

                    // finds targetVertex & edgeVertex values corresponding to position in adjArray
                    Graph.Vertex<String> targetVertex = inputGraph.findVertex(vertexKey.get(row));
                    Graph.Vertex<String> edgeVertex = inputGraph.findVertex(vertexKey.get(column));

                    // Adds edge Entry to Vertex object if position in adjArray is not 0
                    if (adjArray[row][column] != 0 && (targetVertex != null && edgeVertex != null)){
                        inputGraph.addVertexEdge(targetVertex, edgeVertex, adjArray[row][column]);
                    }
                }
            }
            return inputGraph;      // return Graph inputGraph (with associated Vertex objects)
        }

        // Catch FileNotFoundException errors
        catch (FileNotFoundException ex){
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            ex.printStackTrace();
            System.exit(0);
        }

        // Catch all other exceptions
        catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    /**
     * Prompts user to input a start node, checks validity.
     * @param g: The Graph for which a start node is being specified.
     * @return startNode: The String name to be used as the name of start node of g.
     */
    private static String promptStartNode(Graph<String> g){
        Scanner input = new Scanner(System.in);
        boolean done = false;
        String startNode = null;

        while(!done){
            System.out.println("Please enter the name of the Start Node (Case Sensitive): " );

            // set startNode to next user input String
            if (input.hasNext()){ startNode = input.next(); }
            else{
                System.out.println("Invalid input. Please try again. ");
                continue;
            }

            // check if Vertex with name == startNode String is in Graph g
            if (g.findVertex(startNode) != null){ done = true; }
            else if (g.findVertex(startNode) == null){
                System.out.println("Invalid Node Name. Please try again.");
            }
        }
        return startNode;
    }

    /**
     * Reads lines from a text file to create a HashMap
     * with Vertex names and directDistance values.
     * @return distanceMap: HashMap (name, DD)
     */
    private static HashMap<String, Integer> readDirectDistance(){

        try{
            FileInputStream inFile = new FileInputStream("resources/direct_distance.txt");
            HashMap<String, Integer> distanceMap = new HashMap<>();

            Scanner scan = new Scanner(inFile);
            String lineFromFile;
            while (scan.hasNextLine()){
                lineFromFile = scan.nextLine();

                // split each line by spaces, into array
                String[] splitLine = lineFromFile.split("\\s+");

                // Adds key/value pair to distanceMap. Assumes 1 key/1 value per line in .txt file
                distanceMap.put(splitLine[0], Integer.valueOf(splitLine[1]));
            }
            inFile.close();
            return distanceMap;
        }

        // Catch FileNotFoundException errors
        catch (FileNotFoundException ex){
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            ex.printStackTrace();
            System.exit(0);
        }

        // Catch all other errors
        catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static void main(String[] args){

        // create Graph object from text file using readGraph() method
        Graph<String> projectGraph = readGraph();

        // get name of start node from user
        String startNodeName = promptStartNode(projectGraph);
        System.out.println("User entered " + startNodeName + " as the start node");

        // set Vertex corresponding to startNodeName as projectGraph's start node
        projectGraph.setStartVertex(projectGraph.findVertex(startNodeName));

        // set the end node to Vertex with name of "Z"
        projectGraph.setDestination(projectGraph.findVertex("Z"));

        // test algorithm 1
        System.out.println("\nAlgorithm 1: \n");
        projectGraph.algorithm1();

        // test algorithm 2
        System.out.println("\nAlgorithm 2: \n");
        projectGraph.algorithm2();
    }
}
