package org.example;

import java.util.HashSet;

public class ToSumProb {
    private static int[] sum = new int[] {1, 2, 3, 4,6, 8, 9};

    static void main(String[] args) {

        int n = 9;
        HashSet<Integer> set = new HashSet<>();

        for (int i : sum) {
            int rem = n -i;
            if (set.contains(rem)) {
                System.out.println("present");
                return;
            }
            set.add(i);
        }


    }
}
