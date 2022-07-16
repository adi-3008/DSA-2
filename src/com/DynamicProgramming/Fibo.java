package com.DynamicProgramming;

import java.util.Arrays;

public class Fibo {
    public static int fibo(int[] arr, int n){
        if (arr[n] < Integer.MAX_VALUE)
            return arr[n];
        else if (n < 2){
            arr[n] = n;
            return n;
        }else{
            int ans =  fibo(arr, n-1) + fibo(arr, n-2);
            arr[n] = ans;
            return ans;
        }
    }

    public static int fibo(int n){
        int[] arr = new int[n+1];
        Arrays.fill(arr, Integer.MAX_VALUE);
        return fibo(arr, n);
    }

    public static void main(String[] args) {
        System.out.println(fibo(50));
    }
}
