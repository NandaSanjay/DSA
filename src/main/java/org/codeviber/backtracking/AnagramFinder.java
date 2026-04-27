package org.codeviber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnagramFinder {

    public static void main(String[] args) {

        System.out.println(findAnagrams("cbaebabacd", "abc")); // Output: [0, 6]
        System.out.println(findAnagrams("abab", "ab"));
    }

    public static List<Integer> findAnagrams(String s, String p) {
        int[] pCount =new int[26];
        int[] sCount = new int[26];

        for(int i = 0; i< p.length(); i++) {
            pCount[p.charAt(i)-'a']++;
        }

        List<Integer> list = new ArrayList<>();

        for(int i = 0; i< s.length(); i++) {
            sCount[s.charAt(i)-'a']++;

            if(i >= p.length()) {
                sCount[s.charAt(i-p.length()) - 'a']--;
            }
            if(Arrays.equals(pCount, sCount)) {
                list.add(i-p.length()+1);
            }
        }

        return list;

    }
}
