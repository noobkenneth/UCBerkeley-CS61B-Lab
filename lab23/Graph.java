import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.HashSet;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        if (isAdjacent(v1, v2)) {
            for (int i = 0; i < adjLists[v1].size(); i++) {
                if (adjLists[v1].get(i).to == v2) {
                    adjLists[v1].get(i).weight = weight;
                }
            }
        } else {
            adjLists[v1].add(new Edge(v1, v2, weight));
        }
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        if (isAdjacent(v1, v2) && isAdjacent(v2, v1)) {
            for (int i = 0; i < adjLists[v1].size(); i++) {
                if (adjLists[v1].get(i).to == v2) {
                    adjLists[v1].get(i).weight = weight;
                }
            }
            for (int j = 0; j < adjLists[v2].size(); j++) {
                if (adjLists[v2].get(j).to == v1) {
                    adjLists[v2].get(j).weight = weight;
                }
            }

        } else {
            adjLists[v1].add(new Edge(v1, v2, weight));
            adjLists[v2].add(new Edge(v2, v1, weight));
        }
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for (int i = 0; i < adjLists[from].size(); i++) {
            if (adjLists[from].get(i).to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        if (adjLists[v].size() == 0) {
            return null;
        } else {
            ArrayList<Integer> verticesList = new ArrayList<>();
            for (int i = 0; i < adjLists[v].size(); i++) {
                verticesList.add((adjLists[v].get(i).to));
            }
            return verticesList;
        }

    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int count = 0;
        for (int i = 0; i < adjLists.length; i++) {
            for (int j = 0; j < adjLists[i].size(); j++) {
                if (adjLists[i].get(j).to == v) {
                    count++;
                }
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /* A class that iterates through the vertices of this graph, starting with
       vertex START. If the iteration from START has no path from START to some
       vertex v, then the iteration will not include v. */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        DFSIterator(int start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
            visited.add(start);
        }

        public boolean hasNext() {
            return !fringe.empty();
        }

        public Integer next() {
            Integer next = fringe.pop();
            visited.add(next);
            for (int i = 0; i < adjLists[next].size(); i++) {
                Integer toAdd = adjLists[next].get(i).to;
                if (!visited.contains(toAdd) && !fringe.contains(toAdd)) {
                    fringe.push(toAdd);
                }
            }
            return next;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        if (start == stop) {
            return true;
        } else {
            List<Integer> x = dfs(start);
            return (x.contains(stop));

        }
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        ArrayList<Integer> pathList = new ArrayList<>();
        if (!pathExists(start, stop)) {
            return pathList;
        } else if (start == stop) {
            pathList.add(start);
            return pathList;
        } else {
            pathList.add(start);
            int x = start;
            int y = 0;
            List<Integer> neighborStart = neighbors(x);
            while (x != stop) {
                for (int i = 0; i < neighborStart.size(); i++) {
                    if (pathExists(neighborStart.get(i), stop) && neighborStart.get(i) != x) {
                        pathList.add(neighborStart.get(i));
                        y = neighborStart.get(i);
                        break;
                    }
                }
                x = y;


            }

            return pathList;
        }
    }

//    public List<Integer> path(int start, int stop) {
//        ArrayList<Integer> pathList = new ArrayList<>();
//        if (!pathExists(start,stop)) {
//            return pathList;
//        } else if (start == stop) {
//            pathList.add(start);
//            return pathList;
//        } else {
//            Iterator<Integer> iter = new DFSIterator(start);
//            ArrayList<Integer> temp = new ArrayList<>();
//            while (iter.hasNext()) {
//                if (iter.next() != stop) {
//                    temp.add(iter.next());
//                } else {
//                    break;
//                }
//
//            }
//        }
//        return null;
//    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;



        TopologicalIterator() {
            fringe = new Stack<Integer>();

        }

        public boolean hasNext() {

            return false;
        }

        public Integer next() {

            return 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        //addEdge(0, 2);
        //addEdge(0, 4);
        addEdge(1, 2);
        //addEdge(2, 0);
        //addEdge(2, 3);
        //addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 1);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        //System.out.println(g1.pathExists(1, 3));
        //System.out.println(g1.path(0, 2));
        //g1.printDFS(0);
        //g1.printDFS(2);
        //g1.printDFS(3);
        //g1.printDFS(4);


        //g1.printPath(0, 3);
        //g1.printPath(0, 4);
        //g1.printPath(1, 3);
        //g1.printPath(1, 4);
        //g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        //System.out.println(g1.pathExists(0,2));

        Graph g3 = new Graph(10);
        g3.generateG3();
        //System.out.println(g3.pathExists(0,1));
        //g2.printTopologicalSort();
    }
}
