package codeviber.windowslider;

/**
 *
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false otherwise.
 *
 * In other words, return true if one of s1's permutations is the substring of s2.
 *
 *
 *
 * Example 1:
 *
 * Input: s1 = "ab", s2 = "eidbaooo"
 * Output: true
 * Explanation: s2 contains one permutation of s1 ("ba").
 * Example 2:
 *
 * Input: s1 = "ab", s2 = "eidboaoo"
 * Output: false
 *
 */
public class PermutationInStringFinder {

    public static boolean checkInclusion(String s1, String s2) {

        if (s1.length() < s1.length()) {
            return false;
        }

        if (s2.length() == 1 && s1.contains(s2)) {
            return true;
        }

        int l=0; int r=0;
        char[] s2Chars = s2.toCharArray();
        while(r<s1.length()) {
            if ((r-l) == s2.length()) {
                return true;
            }
            if (s2.indexOf(s1.charAt(r)) != -1 ) {
                r++;
            } else {
                l=++r;
            }
        }
        if ((r-l) == s2.length()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String str1 = "ooolleoooleh";
        String str2 = "hello";
        System.out.println(str1 + ", " + str2);
        System.out.println(checkInclusion(str1, str2));

    }
}
