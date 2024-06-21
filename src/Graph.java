
class Graph {

// Attributes
    private Vertex VertexList[];
    private int AdjacentMatrix[][];
    private int NumberOfVertices = 0;

// Constructors
    public Graph(int Vertices) {
        VertexList = new Vertex[Vertices];

        for (int i = 0; i < Vertices; i++) {
            AddVertex(i + "");
        }

        AdjacentMatrix = new int[Vertices][Vertices];
    }

    // Getters
    public int getnVerts() {
        return NumberOfVertices;
    }

    public int[][] getWeights() {
        return AdjacentMatrix;
    }

    public Vertex getVertex(int i) {
        return VertexList[i];
    }

    // Methods
    public void AddVertex(String Label) {

        Vertex V = new Vertex(Label);
        VertexList[NumberOfVertices++] = V;
    }

    public void AddEdge(int start, int end, int Weight) {
        AdjacentMatrix[start][end] = Weight;
        AdjacentMatrix[end][start] = Weight;
    }

    public boolean IsAdjacent(Vertex Source, Vertex Destination) {
        return AdjacentMatrix[Integer.parseInt(Source.getLabel())][Integer.parseInt(Destination.getLabel())] != 0;
    }
}
