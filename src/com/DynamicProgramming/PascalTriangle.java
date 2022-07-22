package com.DynamicProgramming;

public class PascalTriangle {
    public static int[][] pascalTriangleBottomUp(int n){
        int[][] T = new int[n][n];
        for (int i = 0; i < T.length; i++) {
            for (int j = 0; j < T[i].length; j++) {
                if (j > i)
                    break;
                else if (j == 0 || i == j)
                    T[i][j] = 1;
                else
                    T[i][j] = T[i-1][j] + T[i-1][j-1];
            }
        }
        return T;
    }

    public static int[][] pascalTriangleTopDown(int n){
        int[][] T = new int[n][n];
        for (int j = 0; j < T[n-1].length; j++) {
            helper(T, n-1, j);
        }
        return T;
    }

    // we know that we never encountered zero in pascal triangle, so we mark cells to
    // zero actually this is done automatically during initialisation of table.
    // identify we have not computed value for that cell.
    private static int helper(int[][] T, int i, int j){
        if (i < 0 || j < 0)
            return 0;
        else if (j == 0)
            T[i][j] = 1;
        else if (T[i][j] == 0)
            T[i][j] = helper(T, i-1, j) + helper(T, i-1,j-1);
        return T[i][j];
    }

    public static void printPascalTriangle(int[][] T){
        for (int i = 0; i < T.length; i++) {
            for (int j = 0; j < T[i].length; j++) {
                if (j > i){
                    System.out.println();
                    break;
                }
                else
                    System.out.print(T[i][j] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] ans = pascalTriangleBottomUp(5);
        printPascalTriangle(ans);

    }
}
