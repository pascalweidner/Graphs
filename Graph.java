import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.*;


public class Graph {
    private final ArrayList<Edge> m_edges = new ArrayList<>();
    private final boolean directed;

    public Graph(Edge[] edges, boolean directed) {
        this.m_edges.addAll(Arrays.asList(edges));
        this.directed = directed;
    }

    private ArrayList<String> vertices() {
        Set<String> v = new HashSet<>();
        for(Edge e : m_edges) {
            v.add(e.getStart());
            v.add(e.getEnd());
        }

        String[] cache = new String[v.size()];
        return new ArrayList<>(Arrays.asList(v.toArray(cache)));
    }


    private ArrayList<Tuple<String, Integer>> getAdjacent(String v) {
        ArrayList<Tuple<String, Integer>> neighbors = new ArrayList<>();
        for(Edge e: m_edges) {
            if(Objects.equals(e.getStart(), v)) {
                neighbors.add(new Tuple<>(e.getEnd(), e.getCost()));
            }
        }
        return neighbors;
    }

    private String getMin(ArrayList<String> vertices, Map<String, Integer> distances) {
        String smallestV = vertices.get(0);
        int smallestVD = distances.get(vertices.get(0));
        for(String v : vertices) {
            if(distances.get(v) < smallestVD) {
                smallestV = v;
                smallestVD = distances.get(v);
            }
        }

        return smallestV;
    }

    private ArrayList<String> getPath(String start, String destination, Map<String, String> prevV) {
        ArrayList<String> path = new ArrayList<>();
        String currV = destination;
        while(!Objects.equals(currV, start)) {
            path.add(0, currV);
            currV = prevV.get(currV);
        }
        path.add(0, currV);

        return path;
    }

    private HashMap<String, Integer> getDistances() {
        HashMap<String, Integer> distancesV = new HashMap<>();
        for(String v : vertices()) {
            distancesV.put(v, (int) Double.POSITIVE_INFINITY);
        }

        return  distancesV;
    }

    private HashMap<String, String> getPrev() {
        HashMap<String, String> prevV = new HashMap<>();
        for(String v: vertices()) {
            prevV.put(v, null);
        }

        return prevV;
    }

    public ArrayList<String> dijkstra(String source, String destination) {
        Map<String, Integer> distancesV = getDistances();
        Map<String, String> prevV = getPrev();

        distancesV.put(source, 0);

        ArrayList<String> vertices = new ArrayList<>(vertices());
        while(vertices.size() > 0) {
            String v = getMin(vertices, distancesV);
            vertices.remove(v);
            if (distancesV.get(v) == (int) Double.POSITIVE_INFINITY) {
                break;
            }

            for(Tuple<String, Integer> adjacent : getAdjacent(v)) {
                int pathCost = distancesV.get(v) + adjacent.y;

                if(distancesV.get(adjacent.x) > pathCost) {
                    distancesV.put(adjacent.x, pathCost);
                    prevV.put(adjacent.x, v);
                }
            }

        }

        return getPath(source, destination, prevV);
    }

    public ArrayList<String> bellmanFord (String source, String destination) {
        if(!directed) {
            return null;
        }

        Map<String, Integer> distancesV = getDistances();
        Map<String, String> prevV = getPrev();

        distancesV.put(source, 0);

        for(int i = 0; i < vertices().size() - 1; i++) {
            for(Edge edge: m_edges) {
                if(distancesV.get(edge.getStart()) != (int) Double.POSITIVE_INFINITY && distancesV.get(edge.getStart()) + edge.getCost() < distancesV.get(edge.getEnd())) {
                    distancesV.put(edge.getEnd(), distancesV.get(edge.getStart()) + edge.getCost());
                    prevV.put(edge.getEnd(), edge.getStart());
                }
            }
        }
        for(Edge edge: m_edges) {
            if(distancesV.get(edge.getStart()) != (int) Double.POSITIVE_INFINITY && distancesV.get(edge.getStart()) + edge.getCost() < distancesV.get(edge.getEnd())) {
                return null;
            }
        }

        return getPath(source, destination, prevV);
    }

    private String root(HashMap<String, String> parents, String v) {
        if(Objects.equals(parents.get(v), v)) {
            return v;
        }
        return root(parents, parents.get(v));
    }

    private void union(HashMap<String, String> parents, String u, String v) {
        String u_root = root(parents, u);
        String v_root = root(parents, v);

        parents.put(u_root, v_root);
    }

    public ArrayList<Edge> kruskal() {
        ArrayList<Edge> mst = new ArrayList<>();

        ArrayList<Edge> edges = new ArrayList<>(m_edges);
        edges.sort(Comparator.comparingInt(Edge::getCost));

        HashMap<String, String> prevV = new HashMap<>();
        for(String v: vertices()) {
            prevV.put(v, v);
        }

        int amountEdges = 0;

        int i = 0;
        while(amountEdges < vertices().size() - 1) {
            Edge edge = edges.get(i);
            i++;
            String parentU = root(prevV, edge.getStart());
            String parentV = root(prevV, edge.getEnd());

            if (!Objects.equals(parentU, parentV)) {
                amountEdges++;
                mst.add(new Edge(edge.getStart(), edge.getEnd(), edge.getCost()));
                union(prevV, edge.getStart(), edge.getEnd());
            }
        }

        return mst;
    }
}
