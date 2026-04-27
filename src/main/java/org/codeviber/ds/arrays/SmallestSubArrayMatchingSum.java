package codeviber.ds.arrays;

import java.util.ArrayList;
import java.util.List;

public class SmallestSubArrayMatchingSum {

    public static void main(String[] args) {
        int l=0, r=0;
        int[] nums = {2,3,1,2,4,3};
        int k = 7, localSus = 0;

        int result = Integer.MAX_VALUE;
        List<Integer> localList = new ArrayList<>();

        while (r < nums.length) {

            localSus = localSus+nums[r];

            while(localSus>k){
                result = Math.min(r-l,result);
                localSus -=nums[l];
                l++;
            }
            r++;
        }
        if (Integer.MAX_VALUE == result)
            result = 0;
        System.out.println(result);


    }

}
