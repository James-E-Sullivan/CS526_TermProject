import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;


public class project {

    private static HashMap<HashMap<String, Integer>, int[][]> readGraph(){

        //test
        Graph<String> inputGraph = new Graph<>();

        HashMap<HashMap<String, Integer>, int[][]> inputMap = new HashMap<>();

        String lineFromFile;

        try{
            FileInputStream inFile = new FileInputStream("resources/graph_input.txt");
            Scanner fileScan = new Scanner(inFile);
            String firstLine = fileScan.nextLine();

            String[] vertexNames = firstLine.split("\\s+");     // 1st row, names of nodes
            int[][] adjArray = new int[vertexNames.length - 1][];   // Columns == # of vertex names

            HashMap<String, Integer> nodeKey = new HashMap<>();        // HashMap for names
            for (int i=1; i<vertexNames.length; i++){   // skip index 0 (column 0 will be letters)
                nodeKey.put(vertexNames[i], i-1);       // 'A' will correspond to index 0 ,'B' to 1, etc.
            }

            //test - swap order of HashMap
            HashMap<Integer, String> vertexKey = new HashMap<>();
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
                    row[j] = Integer.valueOf(splitLine[j]);     // for each column, store edge value in row[]
                }
                adjArray[i] = row;  // adds edge values (in form of row[]) to each row in adjArray
                i++;                // iterate i to move into next row
            }

            inFile.close();

            //test this is better
            for (Map.Entry pair : nodeKey.entrySet()){
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }

            // create vertices
            for (int row=0; row < adjArray.length; row++){
                System.out.println("Test vertex node (" + row + "): " + vertexKey.get(row));
                inputGraph.addVertex(vertexKey.get(row));     // Creates Graph Vertex w/ name corresponding to row
            }

            // add edge/weight entries into edgeMap for each Vertex
            for (int row=0; row < adjArray.length; row++){
                for (int column=0; column < adjArray[row].length; column++){
                    if (adjArray[row][column] != 0){
                        Graph.Vertex<String> targetVertex = inputGraph.findVertex(vertexKey.get(row));
                        Graph.Vertex<String> edgeVertex = inputGraph.findVertex(vertexKey.get(column));
                        inputGraph.addVertexEdge(targetVertex, edgeVertex, adjArray[row][column]);
                    }
                }
            }




            inputMap.put(nodeKey, adjArray);
            return inputMap;        // returns HashMap with (nodeKey : adjArray)
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
        HashMap<HashMap<String, Integer>, int[][]> inMap = readGraph();

        //HashMap<String, Integer> nodeMap = inMap.


        //System.out.println(testArray1[1][2]);




        HashMap<String, Integer> hm01 = new HashMap<>(readDirectDistance());
        System.out.println(hm01.get("A"));

    }
}
