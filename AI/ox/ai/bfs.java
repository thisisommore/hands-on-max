import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class BFS {
    private HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();

    void insertEdge(int v, int w) {
        int defaultValue[] = {};
        List<Integer> adj = map.getOrDefault(v, defaultValue);
        map.put(v, adj);
        String l = map.get(v).toString();
        System.out.println(l);
    }

    void BFSm(int source, int dest) {
        LinkedList<Integer> ll = map.get(source);
        int smallest = -1000;
        ll.forEach((t) -> {
            if (t < smallest) {
                smallest = t;
            }
        });
        System.out.println(smallest);
        if (smallest == dest) {
            System.exit(0);
        }
        BFSm(smallest, dest);
    }

    public static void main(String args[]) {
        BFS graph = new BFS();
        graph.insertEdge(2, 2);
        graph.insertEdge(2, 3);

        graph.insertEdge(3, 2);
        graph.insertEdge(3, 4);

        graph.insertEdge(1, 2);
        graph.insertEdge(1, 4);
        graph.insertEdge(1, 25);

        graph.insertEdge(4, 3);
        graph.insertEdge(4, 1);
        graph.insertEdge(4, 5);

        graph.insertEdge(25, 1);
        graph.insertEdge(25, 5);

        graph.insertEdge(5, 4);
        graph.insertEdge(5, 25);

        System.out.println("Breadth First Traversal for the graph is:");
        graph.BFSm(1, 5);
    }

}