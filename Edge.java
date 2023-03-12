public class Edge {
    private String m_start;
    private String m_end;
    private int m_cost;

    public Edge(String start, String end, int cost) {
        this.m_start = start;
        this.m_end  = end;
        this.m_cost = cost;
    }

    public String getStart() {
        return m_start;
    }

    public String getEnd() {
        return m_end;
    }

    public int getCost() {
        return m_cost;
    }

    @Override
    public String toString() {
        return "[" + "'" + getStart() + "'" + ", " + "'" + getEnd() + "'" + ", " + getCost() + "]";
    }
}
