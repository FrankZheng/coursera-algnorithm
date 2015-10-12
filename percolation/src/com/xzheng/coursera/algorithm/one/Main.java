package com.xzheng.coursera.algorithm.one;

import com.sun.tools.javac.util.Assert;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("hello world!\n");
        WeightedQuickUnionUF qu = new WeightedQuickUnionUF(10);
        qu.union(0,1);
        qu.union(1,2);
        qu.union(5,6);
        qu.union(7,9);

        Assert.check(qu.connected(0,2));

    }
}
