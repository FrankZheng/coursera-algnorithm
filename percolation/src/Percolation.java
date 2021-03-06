import com.sun.tools.javac.util.Assert;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by xzheng on 15/10/10.
 */
public class Percolation {

    private static final int OPEN = 1;
    private static final int BLOCKED = 0;

    private int[][] sites;
    private WeightedQuickUnionUF unionFinder;
    private int count;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        validate(n);
        count = n;
        //initialize all sites to be blocked
        sites = new int[n][n];

        //initialize the union finder with n * n sites, with a virtual top site and a virtual bottom site.
        int num = n * n + 2;
        unionFinder = new WeightedQuickUnionUF(num);

        virtualTop = 0;
        virtualBottom = n*n+1;

        //connect the virtual top site with the sites at first row.
        //connect the virtual bottom site with the sites at bottom row.
        /*
        for (int i = 1 ; i <= n ; i++) {
            unionFinder.union(virtualTop, i);
            unionFinder.union(virtualBottom - i, virtualBottom);
        }
        */
    }

    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
    }

    private void validate(int i, int j) {
        if (i < 1 || i > count) {
            throw new IndexOutOfBoundsException("i should between 1 and " + count);
        }
        if (j < 1 || j > count) {
            throw new IndexOutOfBoundsException("j should between 1 and " + count);
        }
    }

    private int getSiteUFIndex(int i, int j) {
        return j + count * i + 1;
    }

    public void open(int i, int j) {
        validate(i, j);

        int ii = i-1;
        int jj = j-1;
        sites[jj][ii] = OPEN;

        //connect the site's left, right, up, down sites.
        int indexUF = getSiteUFIndex(ii, jj);
        if (jj > 0 && sites[jj-1][ii] == OPEN) {
            //connect left
            unionFinder.union(indexUF, getSiteUFIndex(ii, jj-1));

        }
        if (jj < count-1 && sites[jj+1][ii] == OPEN) {
            //connect right
            unionFinder.union(indexUF, getSiteUFIndex(ii, jj+1));
        }
        if (ii > 0 && sites[jj][ii-1] == OPEN) {
            //connect up
            unionFinder.union(indexUF, getSiteUFIndex(ii-1, jj));
        }
        if (ii < count-1 && sites[jj][ii+1] == OPEN) {
            //connect down
            unionFinder.union(indexUF, getSiteUFIndex(ii+1, jj));
        }
        if (ii == 0) {
            //connect virtual top
            unionFinder.union(indexUF, virtualTop);
        }
        if (ii == count - 1) {
            //connect virtual bottom
            unionFinder.union(indexUF, virtualBottom);
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return sites[j-1][i-1] == OPEN;
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return unionFinder.connected(virtualTop, getSiteUFIndex(i-1, j-1));
    }

    public boolean percolates() {
        return unionFinder.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        /*
        int N = StdIn.readInt();
        Percolation percolation = new Percolation(N);
        while (!StdIn.isEmpty()) {
            int i = StdIn.readInt();
            int j = StdIn.readInt();
            if (!percolation.isOpen(i, j)) {
                percolation.open(i, j);
            }
        }
        String result = "percolates";
        if (!percolation.percolates()) {
            result = "does NOT percolates";
        }
        System.out.printf("Percolation %s\n", result);
        */
        Percolation percolation = new Percolation(3);
        percolation.open(1,3);
        Assert.check(percolation.isFull(1,3));
        percolation.open(2,3);
        Assert.check(percolation.isFull(2,3));
        percolation.open(3,3);
        Assert.check(percolation.isFull(3,3));
        percolation.open(3,1);
        Assert.check(!percolation.isFull(3,1));

        percolation.open(2,1);
        percolation.open(1,1);

        Assert.check(percolation.percolates());

    }


}
