package com.DynamicProgramming;

import java.util.Arrays;

public class KnapSackZeroOne {

    public static int ks(int[] weight, int[] profit, int c) {

        // let denote no of item by n.
        int n = profit.length;

        // T[i][j] represent whether to include i'th item in knapsack if the current
        // capacity of the knapsack is j.
        int[][] T = new int[n + 1][c + 1];

        // if either items are finished and still knapsack is full then we can't able to add more item in it
        // so profit gain is zero.
        // also if knapsack capacity is zero, so we can't able to add more item in it so profit gain is zero.
        for (int i = 0; i < T.length; i++) {
            T[i][0] = 0;
            T[0][i] = 0;
        }

        // this is only for top-down method.
        // mark all cell -1 so that overlapping sub-problem will be identified
        for (int[] ints : T) {
            Arrays.fill(ints, -1);
        }

//        int maxProfit1 = bottomUpKS(T, weight, profit);
        int maxProfit = topDownKS(T,n, c,weight,profit);
        constructSolution(T,n,c,weight,profit);
        return maxProfit;
    }


    private static int bottomUpKS(int[][] T, int[] weight, int[] profit) {
        // i represent the current item to be included.
        for (int i = 1; i < T.length; i++) {
            // c represent current capacity of the knapsack.
            for (int c = 1; c < T[i].length; c++) {

                // if weight of the current item is less than capacity as well as including it will cause more
                // profit than not including it then we will include it in tha knapsack.
                if (c >= weight[i - 1] && profit[i - 1] + T[i - 1][c - weight[i - 1]] > T[i-1][c]){
                    T[i][c] = profit[i - 1] + T[i - 1][c - weight[i - 1]];
                }
                // otherwise we will do not include it.
                else {
                    T[i][c] = T[i-1][c];
                }
            }
        }

        return T[T.length - 1][T[0].length - 1];
    }

    private static int topDownKS(int[][] T, int i, int c, int[] weight, int[] profit){
        if (i == 0 || c == 0)
            return 0;
        else if (T[i][c] != -1)
            return T[i][c];
        else{
            if (weight[i-1] <= c && T[i-1][c-weight[i-1]] + profit[i-1] > T[i-1][c])
                T[i][c] = profit[i-1] + topDownKS(T, i-1,c-weight[i-1], weight, profit);
            else
                T[i][c] = topDownKS(T,i-1,c,weight,profit);
        }
        return T[i][c];
    }

    private static void constructSolution(int[][] T, int i, int c, int[] weight, int[] profit) {
        if (i == 0 || c == 0)
            return;
        if (weight[i - 1] <= c && profit[i - 1] + T[i - 1][c - weight[i - 1]] > T[i - 1][c]){
            System.out.print(i + " ");
            constructSolution(T,i-1,c-weight[i-1],weight,profit);
        }else{
            System.out.println(i-1 + " ");
            constructSolution(T,i-1,c,weight,profit);
        }
    }

    public static void main(String[] args) {
        int[] profit = {10, 12, 28};
        int[] weight = {1, 2, 4};
        int capacity = 6;
        System.out.println(ks(weight, profit, capacity));

        // formulating a problem.
        // T[i][j] represent profit we will get when we add i th item in the knapsack with current
        // capacity of the knapsack to be j.

        // to add an item i in the knapsack we the current capacity of the knapsack should be greater than
        // weight of that item being added in the knapsack.
        // as well as it will cause more profit than considering we will not add it.
        // means
        // weight of the current item to be added <= currentCapacity of the knapsack
        // weight[i-1] <= c
        // T[i][j] = max{ profit[i-1] + T[i-1][c-weight[i-1]], T[i-1][c] }

    }
}
