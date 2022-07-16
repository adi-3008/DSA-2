package com.DynamicProgramming;

import java.util.Arrays;

public class SubSetSum {
    public static boolean subSetSum(int[] set, int s){
        // n is the length of the set.
        int n = set.length;

        // 's' indicate sum  of the subset require, and we are at i'th item
        // and deciding to include ith item in set or not so that it will complete sum s.
        // T[i][j] represent true/false if sum s can be form using item i or not respectively
        boolean[][] T = new boolean[n+1][s+1];

        int[][] S = new int[n+1][s+1];

        for(int[] arr : S)
            Arrays.fill(arr, Integer.MAX_VALUE);


        for (int i = 0; i < T.length; i++) {
            for (int j = 0; j < T[0].length; j++) {
                // if sum require is zero then and no of items are i then we will include none
                // of the item and get sum zero.
                T[i][0] = true;
                S[i][0] = 1;

                // if sum require is j and no of item are zero this is not possible.
                T[0][j] = false;
                S[0][j] = 0;
            }
        }
        T[0][0] = true;
        S[0][0] = 1;

        int[][] temp = Arrays.copyOf(S, S.length);

        boolean flag = topDownSubSetSum(T, set);
        System.out.println(bottomUpSubSetSum(S,set,n,s));
        constructSolution(set,n,s,"}");
        return flag;
    }

    public static boolean topDownSubSetSum(boolean[][] T, int[] set){
        for (int i = 1; i < T.length; i++) {
            for (int j = 1; j < T[i].length; j++) {
                if (set[i-1] <= j)
                    T[i][j] = T[i-1][j-set[i-1]] || T[i-1][j];
                else
                    T[i][j] = T[i-1][j];
            }
        }
        return T[T.length-1][T[0].length-1];
    }

    public static int bottomUpSubSetSum(int[][] S, int[] set, int i, int w){
        if (w == 0)
            return 1;
        else if (i == 0)
            return 0;
        else if (S[i][w] != Integer.MAX_VALUE)
            return S[i][w];
        else{
            if (set[i-1] <= w)
                S[i][w] = Math.max(bottomUpSubSetSum(S, set, i-1,w-set[i-1]), bottomUpSubSetSum(S, set, i-1, w));
            else
                S[i][w] = bottomUpSubSetSum(S, set, i-1, w);
        }
        return S[i][w];
    }

    public static void constructSolution(int[] set, int i, int s, String p){
        if (i == 0)
            return;
        if (set[i-1] == s)
            System.out.println("{ " + (i-1) + " " + p );
        else if (set[i-1] < s)
            constructSolution(set,i-1,s-set[i-1],(i-1)+ " " + p);
        constructSolution(set, i - 1, s,p);
    }

    public static void main(String[] args) {
        int[] set = {2, 3, 6, 7, 4, 3, 2, 5};
        int w = 5;
        System.out.println(subSetSum(set, w));
    }
}
