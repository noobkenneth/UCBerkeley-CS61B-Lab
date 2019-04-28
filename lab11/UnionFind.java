public class UnionFind {

    private int[] parent;
    private int[] sz;
    private int size;


    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        if (N <= 0) throw new IllegalArgumentException("N must be more than 0");
        parent = new int[N];
        sz = new int[N];
        System.out.println(parent);
        this.size = N ;
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            sz[i] = -1;

        }


    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -sz[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return parent[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v > parent.length) {
            throw new IllegalArgumentException("No such element");
        }
        int root = v;
        while (root != parent[root]) {
            root = parent[root];
        }

        while (v != root) {
            int next = parent[v];
            parent[v] = root;
            v = next;
        }
        return root;
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by connecting V1's root to V2's root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        v1 = find(v1);
        v2 = find(v2);

        if (v1 == v2) {
            return;
        }

        if (sz[find(v1)] > sz[find(v2)]) {
            sz[v2] += sz[v1];
            parent[v1] = v2;
            sz[v1] = parent[v2];
        } else if (sz[find(v1)] < sz[find(v2)]){
            parent[v2] = v1;
            sz[v1] += sz[v2];
            sz[v2] = parent[v1];
        } else if (parent[find(v1)] > parent[find(v2)]){
            parent[v2] = v1;
            sz[v1] += sz[v2];
            sz[v2] = parent[v1];
        } else {
            sz[v2] += sz[v1];
            parent[v1] = v2;
            sz[v1] = parent[v2];
        }

    }

    public static void main(String[] args) {
        UnionFind x = new UnionFind(4);
        //System.out.println(x);

        x.union(3,0);
        x.union(2,0);
        x.union(1,0);
        //x.sizeOf(4);
        System.out.println(x.sizeOf(3));
        System.out.println(x.find(3));
        //System.out.println(x.parent(3));
        //System.out.println(x.connected(0,1));
        System.out.println("union 1 2");
        System.out.println(x);
    }

}
