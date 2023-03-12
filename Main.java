import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Edge[] edges1 = {new Edge("a", "b", 2), new Edge("a", "c", 4), new Edge("b", "c", 5), new Edge("b", "d", 4),
                        new Edge("b", "e", 9), new Edge("c", "e", 1), new Edge("d", "e", 2), new Edge("c", "g", 2),
                        new Edge("c", "h", 7), new Edge("g", "h", 3), new Edge("g", "f", 1), new Edge("h", "j", 5),
                        new Edge("g", "j", 8), new Edge("f", "i", 2), new Edge("i", "j", 6), new Edge("g", "i", 6)};

        Graph graph1 = new Graph(edges1, true);

        System.out.println(Arrays.asList(graph1.dijkstra("a", "j").toArray()));
        System.out.println(Arrays.asList(graph1.bellmanFord("a", "j").toArray()));
        System.out.println(Arrays.asList(graph1.kruskal().toArray()));
    }
}
