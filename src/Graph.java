import java.util.*;

public class Graph {
    HashMap<String, LinkedList<String>> adjGraph;

    /*
    Constructor
     */
    public Graph(){
        adjGraph = new HashMap<String, LinkedList<String>>();
    }

    /**
     * This method is to add edges between the actors in the same movie
     * @param actors This is the array of actors in the same movie
     */
    public void addEdges(String[] actors){
        for(String actor : actors){
            if(adjGraph.containsKey(actor)){
                for(String subActor : actors){
                    if(!subActor.equals(actor) || !adjGraph.get(actor).contains(subActor)){
                        adjGraph.get(actor).add(subActor);
                    }
                }
            }else{
                adjGraph.put(actor, new LinkedList<String>(Arrays.asList(actors)));
                adjGraph.get(actor).remove(actor);
            }
        }
    }

    /**
     * This method is to find the shortest path between two target actors
     * @param from This is actor2
     * @param to This is actor1
     * @return This returns the list of actors in the shortest path
     */
    public LinkedList<String> findShortestPath(String from, String to){
        HashMap<String, String> visited = new HashMap<String, String>(adjGraph.size());
        LinkedList<String> queue = new LinkedList<String>();
        LinkedList<String> result = new LinkedList<String>();
        visited.put(from, from);
        queue.add(from);

        while(queue.size() != 0){
            String current = queue.poll();

            if(current.equals(to)){
                while(!current.equals(from)){
                    result.add(current);
                    current = visited.get(current);
                }
                result.add(current);
                return result;
            }else{
                ListIterator<String> it = adjGraph.get(current).listIterator();
                while(it.hasNext()){
                    String visitingActor = it.next();
                    if(!visited.containsKey(visitingActor)){
                        visited.put(visitingActor, current);
                        queue.add(visitingActor);
                    }
                }

//                for(String name : adjGraph.get(current)){
//                    if(!visited.containsKey(name)){
//                        visited.put(name, current);
//                        queue.add(name);
//                    }
//                }
            }


        }
        return result;
    }

    /**
     * This method is to check if the actor name is in the graph
     * @param actor This is the actor's name
     * @return This returns <>true</> if it contains or <>false</> otherwise
     */
    public boolean in_Graph(String actor){
        return adjGraph.containsKey(actor);
    }
}
