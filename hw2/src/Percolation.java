import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import net.sf.saxon.expr.PJConverter;

// TODO: Add any other necessary imports.

public class Percolation {
    boolean[] openTable; // 0 closed, 1 opened
    WeightedQuickUnionUF dis_set;
    WeightedQuickUnionUF dis_set_is_full;

    int numberOfOpenedSites;
    int size;

    public Percolation(int N) {
        if (N < 0)
            throw new java.lang.IllegalArgumentException("Size must be positive.");
        size = N;
        openTable = new boolean[N * N];
        dis_set = new WeightedQuickUnionUF(N * N + 1 + 1); // 1 for top and 1 for bottom virtual node
        dis_set_is_full = new WeightedQuickUnionUF(N * N + 1); // 1 for top virtual node
        numberOfOpenedSites = 0;

        // Connecting top virtual node to first row
        for (int i = 1; i < N + 1; i++) {
            dis_set.union(0, i);
            dis_set_is_full.union(0, i);
        }

        // Connecting bottom virtual node to last row
        for (int i = N * (N - 1) + 1; i < N * N; i++)
            dis_set.union(i, N * N + 1);

        // Setting table that tracks open spots to false
        for (int i = 0; i < N * N; i++) {
            openTable[i] = false;
        }
    }

    public void open(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
            throw new java.lang.IndexOutOfBoundsException("Row and Col must be within bounds");

        if (!isOpen(row, col)) {
            numberOfOpenedSites++;
            openTable[tableToLinear(row, col)] = true;
        }

        // Connecting when necessary
        if (row == 0) {
            if (col == 0) {
                if (openTable[tableToLinear(row, col + 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                }
            } else if (col == size - 1) {
                if (openTable[tableToLinear(row, col - 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                }
            } else {
                if (openTable[tableToLinear(row, col - 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                }
                if (openTable[tableToLinear(row, col + 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                }
            }
            if (openTable[tableToLinear(row + 1, col)]) {
                dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row + 1, col) + 1);
                dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row + 1, col) + 1);
            }
        } else if (row == size - 1) {
            if (col == 0) {
                  if (openTable[tableToLinear(row, col + 1)]) {
                      dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                      dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                  }
            } else if (col == size - 1) {
                if (openTable[tableToLinear(row, col - 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                }
            } else {
                if (openTable[tableToLinear(row, col - 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                }
                if (openTable[tableToLinear(row, col + 1)]) {
                    dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                    dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                }
            }
            if (openTable[tableToLinear(row - 1, col)]) {
                dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row - 1, col) + 1);
                dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row - 1, col) + 1);
            }
        } else { // all other cases
            if (openTable[tableToLinear(row - 1, col)]) {
                dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row - 1, col) + 1);
                dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row - 1, col) + 1);
            }
            if (openTable[tableToLinear(row + 1, col)]) {
                dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row + 1, col) + 1);
                dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row + 1, col) + 1);
            }
            if (openTable[tableToLinear(row, col - 1)]) {
                dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
                dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col - 1) + 1);
            }
            if (openTable[tableToLinear(row, col + 1)]) {
                dis_set.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
                dis_set_is_full.union(tableToLinear(row, col) + 1, tableToLinear(row, col + 1) + 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
            throw new java.lang.IndexOutOfBoundsException("Row and Col must be within bounds");
        return openTable[tableToLinear(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1)
            throw new java.lang.IndexOutOfBoundsException("Row and Col must be within bounds");
        return isOpen(row, col) && dis_set_is_full.connected(0, tableToLinear(row, col) + 1);
//        return isOpen(row, col) && dis_set.connected(0, tableToLinear(row, col) + 1);
    }

    public int numberOfOpenSites() { return numberOfOpenedSites; }

    public boolean percolates() { return dis_set.connected(0, size * size + 1); }

    // Auxiliary methods
    public int tableToLinear(int row, int col) {
        // The one at the end is a correction factor to allow me to use a virtual node at the beginning
        if (row > 0)
            return (row) * size + col;
        else
            return col;
    }


}
