import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class project {

    private static Graph<String> readGraph(){

        Graph<String> inputGraph = new Graph<>();

        String lineFromFile;

        try{
            FileInputStream inFile = new FileInputStream("resources/graph_input.txt");
            Scanner fileScan = new Scanner(inFile);
            String firstLine = fileScan.nextLine();

            String[] vertexNames = firstLine.split("\\s+");     // 1st row, names of nodes
            int[][] adjArray = new int[vertexNames.length - 1][];   // Columns == # of vertex names

            HashMap<Integer, String> vertexKey = new HashMap<>();   // HashMap for column and row index/name
            for (int i=1; i<vertexNames.length; i++){
                vertexKey.put(i-1, vertexNames[i]);
            }


            int i = 0;  // index of row in adjArray
            while (fileScan.hasNextLine()){     // iterate through each row
                //int[] row = new int[21];
                lineFromFile = fileScan.nextLine();
                String[] splitLine = lineFromFile.split("\\s+");

                int[] row = new int[splitLine.length];        // int[] used to store edge values

                for (int j=1; j < splitLine.length; j++){       // skip first index (letter)
                    row[j-1] = Integer.valueOf(splitLine[j]);     // for each column, store edge value in row[]
                }
                adjArray[i] = row;  // adds edge values (in form of row[]) to each row in adjArray
                i++;                // iterate i to move into next row
            }
            inFile.close();

            HashMap<String, Integer> directDistanceMap = readDirectDistance();      // read in DD HashMap


            // create vertices
            for (int row=0; row < adjArray.length; row++){

                //test
                System.out.println("Test vertex node (" + row + "): " + vertexKey.get(row));
                //end test

                String vertexName = vertexKey.get(row);
                Integer vertexDD = directDistanceMap.get(vertexName);
                inputGraph.addVertex(vertexName, vertexDD);     // Creates Graph Vertex w/ name and direct distance
            }

            // add edge/weight entries into edgeMap for each Vertex
            for (int row=0; row < adjArray.length; row++){
                for (int column=0; column < adjArray[row].length; column++){
                    Graph.Vertex<String> targetVertex = inputGraph.findVertex(vertexKey.get(row));
                    Graph.Vertex<String> edgeVertex = inputGraph.findVertex(vertexKey.get(column));
                    if (adjArray[row][column] != 0 && (targetVertex != null && edgeVertex != null)){
                        inputGraph.addVertexEdge(targetVertex, edgeVertex, adjArray[row][column]);
                    }
                }
            }
            return inputGraph;
        }

        catch (FileNotFoundException ex){
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            ex.printStackTrace();
            System.exit(0);
        }

        catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private static String promptStartNode(Graph<String> g){
        Scanner input = new Scanner(System.in);
        boolean done = false;
        String startNode = null;

        while(!done){
            System.out.println("Please enter the name of the Start Node (Case Sensitive): " );

            if (input.hasNext()){
                startNode = input.next();
            }
            else{
                System.out.println("Invalid input. Please try again. ");
                continue;
            }

            if (g.findVertex(startNode) != null){
                done = true;
            }
            else if (g.findVertex(startNode) == null){
                System.out.println("Invalid Node Name. Please try again.");
            }
        }
        return startNode;
    }

    private static HashMap<String, Integer> readDirectDistance(){

        try{
            FileInputStream inFile = new FileInputStream("resources/direct_distance.txt");
            HashMap<String, Integer> distanceMap = new HashMap<>();

            Scanner scan = new Scanner(inFile);
            String lineFromFile;
            while (scan.hasNextLine()){
                lineFromFile = scan.nextLine();
                String[] splitLine = lineFromFile.split("\\s+");

                // Adds key/value pair to distanceMap. Assumes 1 key/1 value per line in .txt file
                distanceMap.put(splitLine[0], Integer.valueOf(splitLine[1]));
            }
            inFile.close();
            return distanceMap;
        }

        catch (FileNotFoundException ex){
            System.err.println(ex.getMessage() + ": File not found. Exiting.");
            ex.printStackTrace();
            System.exit(0);
        }

        catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    public static void main(String[] args){
        Graph<String> projectGraph = readGraph();
        String startNodeName = promptStartNode(projectGraph);
        projectGraph.setStartVertex(projectGraph.findVertex(startNodeName));
        projectGraph.setDestination(projectGraph.findVertex("Z"));

        //for (Graph.Vertex<String> v in projectGraph)

        /*
        // test
        Graph.Vertex<String> testVertex = projectGraph.findVertex("P");
        System.out.println("Test Vertex Name: " + testVertex.getName());
        System.out.println("Test Vertex Direct Distance: " + testVertex.getDirectDistance());
        Map.Entry<Integer, Graph.Vertex<String>> edge = projectGraph.getShortEdge(testVertex);
        System.out.println("Short edge weight: " + edge.getKey() + ", name: " + edge.getValue().getName());
        Map.Entry<Integer, Graph.Vertex<String>> edgeDD = projectGraph.getShortDD(testVertex);
        System.out.println("Short edge by DD: " + edgeDD.getValue().getDirectDistance() + ", name: " + edgeDD.getValue().getName());
         */

        // test algorithm1
        projectGraph.algorithm1();

        System.out.println();
        projectGraph.algorithm2();

    }
}
