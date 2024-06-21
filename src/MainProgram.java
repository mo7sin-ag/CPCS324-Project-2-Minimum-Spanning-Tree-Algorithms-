// Abdulmohsin Ageel 2036815 aaageel@stu.kau.edu.sa
// Abdallatef Zeghib 2244531 azeghib@stu.kau.edu.sa
// Ahmed Bahabri 2047558 amohamedbahabri@stu.kau.edu.sa

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MainProgram {

    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) throws FileNotFoundException {
        File F1 = new File("input1.txt");
        File F2 = new File("input2.txt");
        Scanner input = new Scanner(System.in);
        Scanner InputF1;

        System.out.println("1. Finding minimum spanning tree using Prim’s algorithm.");
        System.out.println("2. Finding minimum spanning tree using Kruskal’s algorithm.");
        System.out.println("3. Finding shortest path using Dijkstra’s algorithm.");
        System.out.println("4. Quit.");

        System.out.println("Enter option number: ");
        int Option = input.nextInt();
        while (Option != 0) {
            switch (Option) {
                case 1:
                    InputF1 = new Scanner(F1);
                    while (InputF1.hasNextLine()) {
                        Graph G = new Graph(Integer.parseInt(InputF1.nextLine()));

                        int NumberOfEdges = Integer.parseInt(InputF1.nextLine());
                        for (int i = 0; i < NumberOfEdges; i++) {
                            String SplitedLine[] = InputF1.nextLine().split(" ");

                            int Source = Integer.parseInt(SplitedLine[0]);
                            int Destination = Integer.parseInt(SplitedLine[1]);
                            int Weight = Integer.parseInt(SplitedLine[2]);

                            G.AddEdge(Source, Destination, Weight);
                        }
                        int[][] Weights = G.getWeights();

                        MST_Prim(G, Weights, 0);

                        break;
                    }
                    break;
                case 2:

                    // Read input from file
                    Stack<Edge> weights = new Stack<>();
                    InputF1 = new Scanner(F1);

                    int V = InputF1.nextInt(); // Number of vertices
                    int E = InputF1.nextInt(); // Number of edges
//              List<Edge> edges = new ArrayList<>();

                    for (int i = 0; i < E; i++) {
                        int src = InputF1.nextInt();
                        int dest = InputF1.nextInt();
                        double weight = InputF1.nextDouble();
                        weights.push(new Edge(src, dest, weight));
//                edges.add(new Edge(src, dest, weight));
                    }
                    InputF1.close();

                    // Compute MST
                    Stack<Edge> mst = kruskalMST(weights, V);

                    break;

                case 3:
                    try {

                        Scanner scanner = new Scanner(F2);
                        int vertices = scanner.nextInt();
                        int edges = scanner.nextInt();
                        Map<Integer, List<Edge>> graph = new HashMap<>();

                        for (int i = 0; i < edges; i++) {
                            int source = scanner.nextInt();
                            int target = scanner.nextInt();
                            int weight = scanner.nextInt();
                            graph.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(target, weight));
                        }

                        scanner.close();

                        System.out.println("Weight Matrix:");
                        printWeightMatrix(graph, vertices);

                        Scanner userInputScanner = new Scanner(System.in);
                        System.out.print("\nEnter Source vertex: ");
                        int sourceVertex = userInputScanner.nextInt();

                        System.out.println("\nDijkstra using priority queue:");
                        long startTime = System.nanoTime();
                        dijkstra(graph, sourceVertex, vertices);
                        long endTime = System.nanoTime();

                        long elapsedTime = endTime - startTime;
                        System.out.println("\nRunning time of Dijkstra using priority queue is: " + elapsedTime + " nano seconds");
                        System.out.println("");

                    } catch (FileNotFoundException e) {
                        System.out.println("File not found.");
                    }
                    break;
                case 4:
                    System.exit(0);
            }
            System.out.print("Enter option number: ");
            Option = input.nextInt();
            System.out.println("");
        }
    }
//==================================================================================

    public static void MST_Prim(Graph G, int Weights[][], int r) {
        double Sum = 0;
        double[][] MST = new double[Weights.length][Weights.length];
        Queue Q = new Queue();

        for (int i = 0; i < G.getnVerts(); i++) {
            G.getVertex(i).setPi(null);
            G.getVertex(i).setKey(1000);
        }

        for (int i = 0; i < G.getnVerts(); i++) {
            Q.Insert(G.getVertex(i));
        }

        G.getVertex(r).setKey(0);

        long Start = System.nanoTime();
        while (!Q.IsEmpty()) {
            Vertex u = Q.FindMinimum().getV();
            Q.Delete(Q.FindMinimum());
            int uNumber = Integer.parseInt(u.getLabel());

            for (int i = 0; i < G.getnVerts(); i++) {
                Vertex v = G.getVertex(i);
                int vNumber = Integer.parseInt(v.getLabel());

                if (G.IsAdjacent(u, v)) {
                    if (Q.FindNode(v) && Weights[uNumber][vNumber] < v.getKey()) {
                        v.setKey(Weights[uNumber][vNumber]);
                        v.setPi(u);
                    }
                }
            }

            if (!Q.IsEmpty()) {
                Vertex v = Q.FindMinimum().getV();
                int vNumber = Integer.parseInt(v.getLabel());

                MST[Integer.parseInt(v.getPi().getLabel())][vNumber] = v.getKey();
                Sum += MST[Integer.parseInt(v.getPi().getLabel())][vNumber];
            }
        }
        long End = System.nanoTime();

        System.out.println("");
        System.out.println("Total weight of MST by Prim's algorithm (Using unordered Min-");
        System.out.println("Priority queue): " + Sum);
        System.out.println("The edges in the MST are:");

        for (int i = 0; i < MST.length; i++) {
            for (int j = 0; j < MST[i].length; j++) {
                if (MST[i][j] != 0) {
                    System.out.println("Edge from " + i + " to " + j + " has weight " + MST[i][j]);
                }
            }
        }

        System.out.println();
        System.out.println("Running time of Prim’s algorithm using unordered Min-Priority");
        System.out.println("Queue is " + (End - Start) + " Nano seconds");
        System.out.println("");
    }
//-----------------------------------------------------------------------------------
    // Function to find Minimum Spanning Tree using Kruskal's algorithm

    static Stack<Edge> kruskalMST(Stack<Edge> edges, int V) {
        // Sort edges by weight

//        Collections.sort(edges, Comparator.comparingDouble(a -> a.weight));
        long startTime = System.nanoTime();
        mergeSort(edges);

        UnionFind uf = new UnionFind(V);
        Stack<Edge> mst = new Stack<>();

        for (Edge edge : edges) {

            if (!uf.isSet(edge.src, edge.dest)) {
                uf.union(edge.src, edge.dest);
                mst.push(edge);
            }
        }
        long endTime = System.nanoTime();
        // Print MST edges and total weight
        double totalWeight = 0.0;
        System.out.println("The edges in the MST are:");
        for (Edge edge : mst) {
            System.out.println("Edge from " + edge.src + " to " + edge.dest + " has weight " + edge.weight);
            totalWeight += edge.weight;
        }
        System.out.println("\nTotal weight of MST by Kruskal's algorithm: " + totalWeight);

        long duration = (endTime - startTime);  // Time in nanoseconds
        System.out.println("\nRunning Time of Kruskal’s algorithm using Union-Find approach is " + duration + " Nano seconds.");
        System.out.println("");
        return mst;
    }
//-----------------------------------------------------------------------------------

    private static void dijkstra(Map<Integer, List<Edge>> graph, int source, int vertices) {
        double[] dist = new double[vertices];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(node -> node.distance));
        pq.offer(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.vertex;

            if (dist[u] < node.distance) {
                continue;
            }

            List<Edge> edges = graph.getOrDefault(u, Collections.emptyList());
            for (Edge edge : edges) {
                int v = edge.dest;
                double weight = edge.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }

        printShortestPaths(dist, source);
    }
// //==================================================================================

    public static Stack<Edge> mergeSort(Stack<Edge> stack) {
        if (stack.size() <= 1) {
            return stack;
        }

        Stack<Edge> left = new Stack<>();
        Stack<Edge> right = new Stack<>();
        int middle = stack.size() / 2;

        // Split original stack into two halves
        for (int i = 0; i < middle; i++) {
            left.push(stack.pop());
        }
        while (!stack.isEmpty()) {
            right.push(stack.pop());
        }

        // Recursively sort both halves
        left = mergeSort(left);
        right = mergeSort(right);

        // Merge the sorted halves
        Stack<Edge> tempStack = new Stack<>();
        while (!left.isEmpty() && !right.isEmpty()) {
            if (left.peek().weight >= right.peek().weight) {
                tempStack.push(left.pop());
            } else {
                tempStack.push(right.pop());
            }
        }

        // Add remaining elements from left and right stacks
        while (!left.isEmpty()) {
            tempStack.push(left.pop());
        }
        while (!right.isEmpty()) {
            tempStack.push(right.pop());
        }

        // Rebuild the original stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }

        return stack;
    }

//-----------------------------------------------------------------------------------
    private static void printWeightMatrix(Map<Integer, List<Edge>> graph, int vertices) {
        // Print column indices
        System.out.print("     ");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print row indices and weights
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + "    ");
            List<Edge> edges = graph.getOrDefault(i, Collections.emptyList());
            for (int j = 0; j < vertices; j++) {
                int weight = 0;
                for (Edge edge : edges) {
                    if (edge.dest == j) {
                        weight = edge.weightInt;
                        break;
                    }
                }
                System.out.print(weight + " ");
            }
            System.out.println();
        }
    }

//-----------------------------------------------------------------------------------
    private static void printShortestPaths(double[] dist, int source) {
        int n = dist.length;
        System.out.println("Shortest paths from vertex " + source + " are:");
        for (int i = 0; i < n; i++) {
            if (i != source) {
                System.out.printf("A path from %d to %d: ", source, i);
                if (dist[i] == INF) {
                    System.out.println("No path exists.");
                } else {
                    System.out.printf("(Length: %.1f)%n", dist[i]);
                }
            } else {
                System.out.printf("A path from %d to %d: %d (Length: %.1f)%n", source, i, source, dist[i]);
            }
        }
    }

    //==================================================================================   
    static class Node {

        int vertex;
        double distance;

        public Node(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
//-----------------------------------------------------------------------------------    
    // Edge class representing an edge between two vertices with a weight
//-----------------------------------------------------------------------------------

    static class Edge {

        int src, dest, weightInt;
        double weight;

        Edge(int dest, double weight) {
            this.dest = dest;
            this.weight = weight;
            this.weightInt = (int) weight;
        }

        Edge(int src, int dest, double weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }
//-----------------------------------------------------------------------------------    
    // UnionFind class representing the Union-Find data structure

    static class UnionFind {

        int[] id;
//size of the union

        UnionFind(int size) {
            id = new int[size];
            //evrey node is the parent of it self
            for (int i = 0; i < size; i++) {
                id[i] = i;
            }
        }

        boolean isSet(int p, int q) {
            return id[p] == id[q];
        }

        int find(int p) {
            if (p != id[p]) {
                return find(id[p]);
            }
            return id[p];
        }

        //join two groups
        void union(int p, int q) {
            int pid = id[p];
            for (int i = 0; i < id.length; i++) {
                if (id[i] == pid) {
                    id[i] = id[q];
                }
            }
        }

    }
}
