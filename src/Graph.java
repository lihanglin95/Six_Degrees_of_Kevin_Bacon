import java.util.*;

public class Graph {
    HashMap<String, LinkedList<String>> adjGraph;

    public Graph(){
        adjGraph = new HashMap<String, LinkedList<String>>();
    }

    public void addEdges(String[] actors){
        for(String actor : actors){
            actor = actor.toLowerCase();
            if(adjGraph.containsKey(actor)){
                for(String subActor : actors){
                    subActor = subActor.toLowerCase();
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
            }

            Iterator<String> it = adjGraph.get(current).listIterator();
            while(it.hasNext()){
                String visitingActor = it.next();
                if(!visited.containsKey(visitingActor)){
                    visited.put(visitingActor, current);
                    queue.add(visitingActor);
                }
            }
        }
        return null;
    }

    public boolean in_Graph(String actor){
        return adjGraph.containsKey(actor);
    }
}
