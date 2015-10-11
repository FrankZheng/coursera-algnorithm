package com.xzheng.coursera.algorithm.one;


import com.sun.tools.javac.util.Assert;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by xzheng on 15/10/10.
 */
public class Percolation {

    private static final int OPEN = 1;
    private static final int BLOCKED = 0;

    private int[][] sites;
    private WeightedQuickUnionUF unionFinder;
    int count;
    int virtualTop;
    int virtualBottom;

    public Percolation(int n) {
        validate(n);
        count = n;
        //initialize all sites to be blocked
        sites = new int[n][n];

        //initialize the union finder with n * n sites, with a virtual top site and a virtual bottom site.
        int num = n * n + 2;
        unionFinder = new WeightedQuickUnionUF(num);

        //connect the virtual top site with the sites at first row.
        //connect the virtual bottom site with the sites at bottom row.
        virtualTop = 0;
        virtualBottom = n*n+1;
        for (int i = 1 ; i <= n ; i++) {
            unionFinder.union(virtualTop, i);
            unionFinder.union(virtualBottom - i, virtualBottom);
        }
    }

    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
    }

    private void validate(int i, int j ) {
        if(i < 1 || i > count ) {
            throw new IndexOutOfBoundsException("i should between 1 and " + count);
        }
        if(j < 1 || j > count) {
            throw new IndexOutOfBoundsException("j should between 1 and " + count);
        }
    }

    private int getSiteUFIndex(int i, int j) {
        return i + count * j + 1;
    }

    public void open(int i, int j) {
        validate(i, j);

        int ii = i-1;
        int jj = j-1;
        sites[ii][jj] = OPEN;

        //connect the site's left, right, up, down sites.
        int indexUF = getSiteUFIndex(ii, jj);
        if (ii > 0 && sites[ii-1][jj] == OPEN) {
            //connect left
            unionFinder.union(indexUF, getSiteUFIndex(ii-1,jj));

        }
        if (ii < count-1 && sites[ii+1][jj] == OPEN) {
            //connect right
            unionFinder.union(indexUF, getSiteUFIndex(ii+1,jj));
        }
        if (jj > 0 && sites[ii][jj-1] == OPEN ) {
            //connect up
            unionFinder.union(indexUF, getSiteUFIndex(ii,jj-1));
        }
        if (jj < count-1 && sites[ii][jj+1] == OPEN ) {
            //connect down
            unionFinder.union(indexUF, getSiteUFIndex(ii,jj+1));
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return sites[i-1][j-1] == OPEN;
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return unionFinder.connected(virtualTop, getSiteUFIndex(i-1,j-1));
    }

    public boolean percolates() {
        return unionFinder.connected(virtualTop, virtualBottom);
    }

    public static void main(String[] args) {
        int n = 4;
        Percolation percolation = new Percolation(n);
        Assert.check(!percolation.percolates());
        Assert.check(!percolation.isOpen(1,1));
        Assert.check(!percolation.isOpen(n,n));

        percolation.open(1,1);
        Assert.check(percolation.isOpen(1,1));
        Assert.check(percolation.isFull(1,1));
        Assert.check(!percolation.percolates());

        percolation.open(1,2);
        percolation.open(1,3);
        percolation.open(1,4);
        Assert.check(percolation.percolates());


        percolation = new Percolation(n);
        percolation.open(1,1);
        percolation.open(2,2);
        percolation.open(3,3);
        percolation.open(4,4);
        Assert.check(!percolation.percolates());

        percolation.open(2,1);
        percolation.open(2,3);
        percolation.open(3,4);
        Assert.check(percolation.percolates());

    }


}
