package com.xzheng.coursera.algorithm.one;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


/**
 * Created by xzheng on 15/10/11.
 */
public class PercolationStats {

    private double thresholds[];
    private double mean;
    private double stddev;
    private int t;

    public PercolationStats(int N, int T) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T should be greater than 0");
        }
        t = T;

        thresholds = new double[T];
        int sites = N * N;
        for (int k = 0 ; k < thresholds.length ; k++) {
            Percolation percolation = new Percolation(N);
            int openSites = 0;
            while(openSites < sites) {
                //open a random blocked site
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if (!percolation.isOpen(i,j)) {
                    percolation.open(i,j);
                    openSites++;

                    //if percolates, compute the the threshold and record.
                    if (percolation.percolates()) {
                        thresholds[k] = (double)openSites / (double)sites;
                        break;
                    }
                }
            }
        }

        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - (1.96 * stddev ) / Math.sqrt(t);
    }

    public double confidenceHi() {
        return mean + (1.96 * stddev ) / Math.sqrt(t);
    }

    public static void main(String[] args) {

        if(args.length != 2) {
            System.out.println("usage: java PercolationStats N T");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        System.out.printf("%-24s = %.17g\n", "mean", stats.mean());
        System.out.printf("%-24s = %.17g\n", "stddev", stats.stddev());
        System.out.printf("%-24s = %.17g, %.17g\n", "95% confidence interval",
                stats.confidenceLo() , stats.confidenceHi());
    }
}
