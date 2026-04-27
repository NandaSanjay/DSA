package org.codeviber;

public class LongestSubString {

    public static void main(String[] args) {
        String s = "bangalore";
        //Output: 3 (The answer is "abc")
        String subStr = null;
        int l = 0, r = 0;
        int[] data = new int[256];
        int max = 0;
        int localStr = 0;
        int bestStart = 0;
        for (int i = 0; i< s.length(); i++) {

            l = Math.max(l, data[s.charAt(i)]);
            localStr = Math.max(localStr, r-l+1);

            data[s.charAt(i)] = i+1;
            r++;
            if (localStr > max) {
                max = localStr;
                bestStart = l;
            }

        }
        System.out.println(max);
        System.out.println(s.substring(bestStart, bestStart+max));
    }
}
