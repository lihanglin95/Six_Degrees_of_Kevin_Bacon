import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class CS245A2 {
    Graph graph = new Graph();

    public static void main(String[] args) throws Exception {
        CS245A2 test = new CS245A2();
        String filePath = args[0];
        test.readFile(filePath);
        test.readInput();
    }

    public void readFile(String filePath) throws Exception {
        try(BufferedReader br = new BufferedReader(new FileReader("src/" + filePath))){
            br.readLine();
            String movie;
            while((movie = br.readLine()) != null){
                movie = formatDoubleQuotes(split(movie));
                JSONArray jarray = (JSONArray) new JSONParser().parse(movie);
                ArrayList<String> names = new ArrayList<String>();
                for(Object obj : jarray){
                    JSONObject jo = (JSONObject) obj;
                    names.add((String) jo.get("name"));
                }

                Object[] obj = names.toArray();
                for(Object a : obj){
                    System.out.print(a);
                }
                System.out.println();
            }

        }
    }

    public String split(String movie) {
        String[] cast = movie.split("\\[\\{\"\"c");
        if(cast[0].contains("[]")){
            return null;
        }
        if(cast[1].charAt(0) == 'a'){
            String afterSplit = "[{\"\"c" + cast[1].substring(0, cast[1].length() - 3);
            return afterSplit;
        }
        return null;
    }

    public String formatDoubleQuotes(String input){
        StringBuilder sb = new StringBuilder();
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

    private void readInput() {
        boolean done = false;

        while(!done){
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
                System.out.println("Actor 1 name: ");
                String actor1 = br.readLine().toLowerCase();
                if(!graph.in_Graph(actor1)){
                    System.out.println("\nNo such actor.\n");
                    break;
                }

                System.out.println("Actor 2 name: ");
                String actor2 = br.readLine().toLowerCase();
                if(!graph.in_Graph(actor2)){
                    System.out.println("\nNo such actor.\n");
                    break;
                }

                LinkedList<String> result = graph.findShortestPath(actor2, actor1);
                if(result != null){
                    System.out.printf("Path between %s and %s: ", actor1, actor2);
                    print(result);
                }else{
                    System.out.println("There is not path from " + actor1 + " to " + actor2 + ".");
                }

                System.out.println("Continue? [Y/N]\n");
                String finished = br.readLine();
                if(finished.toLowerCase().charAt(0) == 'y'){
                    done = false;
                }else{
                    done = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void print(LinkedList<String> result) {

        for(int i = 0; i < result.size() - 1; i++){
            System.out.printf("%s --> ", result.get(i));
        }
        System.out.println(result.get(result.size() - 1));
    }
}
