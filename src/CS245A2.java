import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class CS245A2 {
    Graph graph = new Graph();

    /**
     * Main
     * @param args This is the path of the source file
     */
    public static void main(String[] args) throws Exception {
        CS245A2 test = new CS245A2();
        String filePath = args[0];
        test.readFile(filePath);
        test.readInput();
    }

    /**
     * This method is to read the source file and import the names to the graph
     * @param filePath This is the path of the source file
     */
    public void readFile(String filePath) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            br.readLine();
            String movie;
            while((movie = br.readLine()) != null){
                movie = formatDoubleQuotes(castSplit(movie));
                if(movie == null){
                    continue;
                }

                //JSON parser
                JSONArray jarray = (JSONArray) new JSONParser().parse(movie);
                ArrayList<String> names = new ArrayList<String>();
                for(Object obj : jarray){
                    JSONObject jo = (JSONObject) obj;
                    String nameStr = (String) jo.get("name");
                    names.add(nameStr.toLowerCase());
                }

                String[] array = names.toArray(new String[names.size()]);
                graph.addEdges(array);
            }
        }
    }

    /**
     * This method is to split the PSON format String from source file
     * @param movie This is the whole line of one movie information
     * @return This returns the string after split or <>null</> if there is not cast information
     */
    public String castSplit(String movie) {
        String[] cast = movie.split("\\[\\{\"\"c");
        if(cast[0].contains("[]")){
            return null;
        }

        if(cast[1].charAt(0) == 'a'){
            String afterSplit;
            if(cast[1].contains("[]")){
                afterSplit = "[{\"\"c" + cast[1].substring(0, cast[1].length() - 4 );
            }else {
                afterSplit = "[{\"\"c" + cast[1].substring(0, cast[1].length() - 3);
            }
            return afterSplit;
        }
        return null;
    }

    /**
     * This method is to reformat the Pson format String
     * @param input This is the unreadable Pson format String
     * @return This returns the readable Pson format String
     */
    public String formatDoubleQuotes(String input){
        StringBuilder sb = new StringBuilder();
        if(input == null){
            return null;
        }

        for(int i = 0; i< input.length(); i++){
            char curr = input.charAt(i);
//            if(i == 0 || i == input.length() -1){
//                continue;
//            }

            if(curr == '\"' && input.charAt(i + 1) == '\"'){
                if (input.charAt(i + 2) == '\"' && input.charAt(i + 3) == '\"'){
                    sb.append(curr);
                }
                continue;
            }
            sb.append(curr);
        }
        return sb.toString();
    }

    /**
     * This method is to read user input from console
     */
    private void readInput() {
        boolean done = false;

        while(!done){
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
                System.out.println("Actor 1 name: ");
                String actor1 = br.readLine().toLowerCase();
                while(!graph.in_Graph(actor1)) {
                    System.out.println("No such actor.");
                    System.out.print("Actor 1 name: ");
                    actor1 = br.readLine().toLowerCase();
                }

                System.out.println("Actor 2 name: ");
                String actor2 = br.readLine().toLowerCase();
                while(!graph.in_Graph(actor2)) {
                    System.out.println("No such actor.");
                    System.out.print("Actor 2 name: ");
                    actor2 = br.readLine().toLowerCase();
                }

                LinkedList<String> result = graph.findShortestPath(actor2, actor1);
                if(result != null){
                    System.out.printf("Path between %s and %s: ", formatName(actor1), formatName(actor2));
                    print(result);
                }else{
                    System.out.println("There is not path from " + formatName(actor1) + " to " + formatName(actor2) + ".");
                }

                System.out.println("Continue? [Y/N]\n");
                String finished = br.readLine();
                if(finished.toLowerCase().charAt(0) == 'y'){
                    done = false;
                    readInput();
                }else{
                    done = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is to reformat the actors' names
     * @param name This is the actor's name
     * @return This returns the actor's name
     */
    public String formatName(String name) {
        String[] names = name.split(" ");
        String result = "";
        for(String part: names)
            result += " "+part.substring(0,1).toUpperCase()+part.substring(1);
        return result.substring(1);
    }

    /**
     * This method is to print the path
     * @param result This is the list of actors in the shortest path
     */
    public void print(LinkedList<String> result) {

        for(int i = 0; i < result.size() - 1; i++){
            System.out.printf("%s --> ", formatName(result.get(i)));
        }
        System.out.println(formatName(result.get(result.size() - 1)));
    }
}
