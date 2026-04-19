package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class MissingElementInRange {
    private static int[] data = new int[] {10, 12, 11, 15};

    static void main(String[] args) {

        int low = 10; int high = 15;
        HashSet<Integer> set = new HashSet<>();
        ArrayList<Integer> ans = new ArrayList<>();

        for(int d : data) set.add(d);

       IntStream.rangeClosed(low, high).forEach(i -> {
           if (!set.contains(i)) {
               ans.add(i);
           }
       });
       System.out.println(ans);


    }
}
