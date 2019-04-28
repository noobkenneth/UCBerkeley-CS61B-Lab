// import edu.princeton.cs.algs4.QuickFindUF;
// import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF grid;
    private boolean[] open;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF full;
    // TODO: Add instance variables here.

    /* Creates an N-by-N grid with all sites initially blocked. */
    public Percolation(int N) {
        this.N = N;
        if (N <= 0) {
            throw  new IllegalArgumentException("Invalid argument.");
        }
        grid = new WeightedQuickUnionUF(N * N + 2);
        open = new boolean[N * N];
        top = xyTo1D(N - 1, N - 1) + 1;
        bottom = xyTo1D(N - 1, N - 1) + 2;
        full = new WeightedQuickUnionUF((N * N + 1));



    }

    private void isWithinBounds(int row, int col) throws IndexOutOfBoundsException {
        if (!valid(row, col)) {
            throw new IndexOutOfBoundsException("Out of range.");
        }
    }

    /* Opens the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        isWithinBounds(row, col);
        if (isOpen(row, col)) {
            return;
        }
        open[xyTo1D(row, col)] = true;
        connect(row, col);
    }

    /* Returns true if the site at (row, col) is open. */
    public boolean isOpen(int row, int col) {
        isWithinBounds(row, col);
        return open[xyTo1D(row, col)] == true;


    }

    /* Returns true if the site (row, col) is full. */
    public boolean isFull(int row, int col) {
        isWithinBounds(row, col);
        return full.connected(xyTo1D(row, col), top);

    }

    /* Returns the number of open sites. */
    public int numberOfOpenSites() {
        int count = 0;
        for (int row = 0; row < N; row ++ ) {
            for (int col = 0; col < N; col ++) {
                if (isOpen(row, col) && valid(row, col)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /* Returns true if the system percolates. */
    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    /* Converts row and column coordinates into a number. This will be helpful
       when trying to tie in the disjoint sets into our NxN grid of sites. */
    private int xyTo1D(int row, int col) {
        isWithinBounds(row, col);
        return (N * (row) + col);

    }
    /* Returns true if (row, col) site exists in the NxN grid of sites.
       Otherwise, return false. */
    private boolean valid(int row, int col) {
        return (row >= 0 && col >= 0 && row < N && col < N);
    }

    private void connect(int row, int col) {
        int position = xyTo1D(row, col);
        if (row == 0) { //if its at the top
            grid.union(top, position);
            full.union(top, position);
        }

        if (row == N-1) { //if its at the bottom
            grid.union(bottom, position);
        }

        if (valid(row, col - 1) && isOpen(row, col - 1) ) { // checking if the left side is open
            grid.union(xyTo1D(row, col -1 ), position);
            full.union(xyTo1D(row, col -1 ), position);
        }

        if (valid(row, col + 1) && isOpen(row, col + 1)) { //check right side
            grid.union(xyTo1D(row, col + 1), position);
            full.union(xyTo1D(row, col + 1), position);
        }

        if (valid(row + 1, col) && isOpen(row + 1, col)) { //check top
            grid.union(xyTo1D(row + 1, col), position);
            full.union(xyTo1D(row + 1, col), position);
        }

        if (valid(row -1, col) && isOpen(row - 1, col)) { //check bottom
            grid.union(xyTo1D(row - 1, col), position);
            full.union(xyTo1D(row - 1, col), position);
        }
    }

    public static void main(String[] args) {
        Percolation x = new Percolation(5);
        System.out.println(x);
        x.open(1,1);
        System.out.println(x);
    }
}
