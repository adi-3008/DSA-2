package com.DynamicProgramming;

public class LCS {
    public static void main(String[] args) {
        char[] x = {'A','A','B'};
        char[] y = {'A','C','A'};
        int m = x.length;
        int n = y.length; // BDAB BCAB BCBA
        int[][] c = memoized_lcs(x,y);
        System.out.println("Length of longest subsequence is " + c[m][n]);
        print_lcs(x,y,c,m,n,"");
    }

    public static int[][] LCS_LENGTH(char[] x, char[] y){
        int m = x.length;
        int n = y.length;

        int[][] c = new int[m+1][n+1];

        for (int i = 0; i <= m; i++) {
            c[i][0] = 0;
        }

        for (int i = 0; i <= n; i++) {
            c[0][i] = 0;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x[i-1] == y[j-1])
                    c[i][j] = 1 + c[i-1][j-1];
                else
                    c[i][j] = Math.max(c[i-1][j], c[i][j-1]);
            }
        }

        return c;
    }

    public static int[][] memoized_lcs(char[] x, char[] y){
        int m = x.length;
        int n = y.length;

        int[][] c = new int[m+1][n+1];

        for (int i = 0; i <= m; i++) {
            c[i][0] = 0;
        }

        for (int i = 0; i <= n; i++) {
            c[0][i] = 0;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                c[i][j] = Integer.MAX_VALUE;
            }
        }

        lcs(x,y,c,m,n);
        return c;
    }

    private static int lcs(char[] x, char[] y, int[][] c, int i, int j){
        if (i == 0 || j == 0)
            return 0;
        else if (c[i][j] < Integer.MAX_VALUE)
            return c[i][j];
        else if (x[i-1] == y[j-1])
            c[i][j] = 1 + lcs(x,y,c,i-1,j-1);
        else
            c[i][j] = Math.max(lcs(x,y,c,i-1,j),lcs(x,y,c,i,j-1));
        return c[i][j];
    }

    public static void print_lcs(char[] x, char[] y, int[][] c, int i, int j, String p){
        if (i == 0 || j == 0){
            System.out.println(p);
        }
        else if (x[i-1] == y[j-1])
            print_lcs(x, y, c, i - 1, j - 1,x[i-1] + p);
        else if (c[i-1][j] > c[i][j-1])
            print_lcs(x,y,c,i-1,j,p);
        else if (c[i-1][j] < c[i][j-1])
            print_lcs(x,y,c,i,j-1,p);
        else {
            print_lcs(x,y,c,i,j-1,p);
            print_lcs(x,y,c,i-1,j,p);
        }
    }
}
