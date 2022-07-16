package com.greedy;

import java.util.Arrays;
import java.util.Comparator;

public class KnapSack {
    public static void main(String[] args) {
        float[] profit = {2, 28, 25, 18, 9};
        float[] weight = {1, 4, 5, 3, 3};
        int n = 5;
        int m = 15;
        float ans = knapSackPrb(profit, weight, n, m);
        System.out.printf("%.2f", ans);

    }

    // space complexity:- O(N)
    // time complexity:- O(NlogN)
    public static float knapSackPrb(float[] profit, float[] weight, int n, int m){
        Float[][] profitWeightRatio = new Float[n][2];

        // find profit by weight ratio.
        // O(n)
        for (int i = 0; i < n; i++) {
            profitWeightRatio[i][0] = (profit[i]/weight[i]);
            profitWeightRatio[i][1] = (float)i;
        }

        // sort profit by weight ratio in non-increasing order
        // O(NlogN)
        Arrays.sort(profitWeightRatio, new Comparator<Float[]>() {
            @Override
            public int compare(Float[] o1, Float[] o2) {
                return o2[0].compareTo(o1[0]);
            }
        });

        // lambda expression.
       // Arrays.sort(profitWeightRatio, (o1, o2) -> o2[0].compareTo(o1[0]));


        // add profit according to profit by weight ratio
        // O(n)
        float p = 0;
        int index = 0;
        for (int i = 0; i < n; i++) {
            index = profitWeightRatio[i][1].intValue();
            if (m > 0 && weight[index] <= m){
                p = p + profit[index];
                m = m - (int) weight[index];
            }else {
                break;
            }
        }

        if (m > 0){
            p = p + profit[index]* m/weight[index];
        }

        return p;
    }
}
