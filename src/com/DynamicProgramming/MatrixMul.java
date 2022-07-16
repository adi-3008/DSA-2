package com.DynamicProgramming;

import java.util.Arrays;

public class MatrixMul {
    public static void main(String[] args) {
        int[] p = {1,2,1,4,1};
        System.out.print(matrixMul(p));
        System.out.println(memoized_matrix_chain(p));

    }

    // bottom up evaluation.
    public static int matrixMul(int[] p) {
        int n = p.length-1;

        // cell m[i][j] represent no of scalar multiplication require to multiply i'th matrix with j'th matrix.
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];

        // initialise no or multiplication of single matrices as 0
        for (int i = 0; i < n; i++) {
            m[i][i] = 0;
        }

        for (int l = 1; l < n; l++) {
            for (int i = 0; i < n-l; i++) {
                int j = i + l;
                m[i][j] = Integer.MAX_VALUE;
                int q = 0;
                for (int k = i; k <= j-1; k++) {
                    q = m[i][k] + m[k+1][j] + p[i]*p[k+1]*p[j+1];
                    if(q < m[i][j]){
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }

        PRINT_OPTIMAL_PARENS(s,0,n-1);

        return m[0][n-1];
    }

    // top-down approach
    public static int memoized_matrix_chain(int[] p){
        int n = p.length-1;
        int[][] m = new int[n][n];

        for (int[] ints : m) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }

        return lookUp_Chain(m,p,0,n-1);
    }

    private static int lookUp_Chain(int[][] m, int[] p, int i, int j) {
        if (m[i][j] < Integer.MAX_VALUE)
            return m[i][j];
        else if (i == j)
            m[i][j] = 0;
        else {
            for (int k = i; k <= j-1; k++) {
                m[i][j] = Math.min(m[i][j], lookUp_Chain(m,p,i,k) + lookUp_Chain(m,p,k+1,j) + p[i]*p[k+1]*p[j+1]);
            }
        }
        return m[i][j];
    }

    public static void PRINT_OPTIMAL_PARENS(int[][] s, int i, int j){
        if (i == j)
            System.out.print("A" + i);
        else {
            System.out.print("(");
            PRINT_OPTIMAL_PARENS(s,i,s[i][j]);
            PRINT_OPTIMAL_PARENS(s,s[i][j] + 1,j);
            System.out.print(")");
        }
    }
}
