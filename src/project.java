import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Dictionary;
import java.util.HashMap;


public class project {

    private static int[][] readGraph(){
        Graph<Graph.Vertex<String>, String> inputGraph = new Graph<>();
        int[][] column = new int[21][];

        String lineFromFile;

        try{
            FileInputStream inFile = new FileInputStream("resources/graph_input.txt");
            Scanner fileScan = new Scanner(inFile);
            String firstLine = fileScan.nextLine();

            String[] vertexNames = firstLine.split("\\s+");     // 1st row, names of nodes

            HashMap<String, Integer> nodeKey = new HashMap<>();        // hashmap for names
            for (int i=1; i<vertexNames.length; i++){
                nodeKey.put(vertexNames[i], i);
                inputGraph.addVertex(vertexNames[i]);
            }
            // test
            System.out.println(nodeKey.entrySet());


            int i = 0;
            while (fileScan.hasNextLine()){
                //int[] row = new int[21];
                lineFromFile = fileScan.nextLine();

                //inputGraph.addVertex(vertexNames[i])


                // test
                System.out.println(lineFromFile);



                String[] splitLine = lineFromFile.split("\\s+");
                inputGraph.addVertex(splitLine[0]);


                int[] row = new int[splitLine.length];

                for (int j=0; j < splitLine.length; j++){       // skip first letter
                    if (j==0)
                        //inputGraph.addVertex(splitLine[j], )

                    row[j] = Integer.valueOf(splitLine[j]);
                }

                column[i] = row;
                i++;
            }

            inFile.close();
            return column;
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
        int[][] testArray1 = readGraph();

        //System.out.println(testArray1[1][2]);




        for (int i=0; i < testArray1.length; i++){
            for (int j=0; j < testArray1.length; j++){
                System.out.println(testArray1[i][j]);
            }
        }


        HashMap<String, Integer> hm01 = new HashMap<>(readDirectDistance());
        System.out.println(hm01.get("A"));

    }
}
