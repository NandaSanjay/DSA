package codeviber.backtracking;

import java.util.HashSet;
import java.util.Set;

public class SubsetSumProblem {


    public static int getMatchingSubsetCount(int[] data, int target) {

        Set<Integer> set = new HashSet<>();
        int matchCount = 0;

        for (int i = 0; i < data.length; i++) {
            int complement = data[i] - target;
            if (set.contains(complement)) {
                matchCount++;
            }
            set.add(complement);
        }
        return matchCount;

    }

    public  static void main(String args[]) {

        int[]  data = {10, 20, 14, 5, 1};
        System.out.println(getMatchingSubsetCount(data, 15));


    }
}
