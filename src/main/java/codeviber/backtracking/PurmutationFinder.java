package codeviber.backtracking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PurmutationFinder {

//   BruteForce -------------------------------------------------------------
//    public static List<String> findPurmutations(String s) {
//        Set<String> set = new HashSet<>();
//        for (int i = 0; i < s.length(); i++) {
//            for (int j = 0; j < s.length(); j++) {
//                String swappedStr = swap(s, i, j);
//                set.add(swappedStr);
//                set.add(new StringBuilder(swappedStr).reverse().toString());
//            }
//        }
//        return new ArrayList<>(set);
//
//    }


    private static String swap(String s, int i, int j) {
        StringBuilder sb = new StringBuilder(s);
        char temp = s.charAt(i);
        sb.setCharAt(i, s.charAt(j));
        sb.setCharAt(j, temp);
        return sb.toString();
    }

    // with back tracking
    private static void getPermutations(char[] chars, int idx, Set<String> set) {
        if (idx == chars.length-1) {
            set.add(String.valueOf(chars));
            System.out.println(chars);
            return;
        }
        for (int i = idx; i < chars.length; i++) {
            swap(chars, i, idx);
            getPermutations(chars, idx+1, set);
            swap(chars, i, idx);
        }
    }

    private static void getPermutations(int[] chars, int idx, Set<List<Integer>> set) {
        if (idx == chars.length-1) {
            set.add((Arrays.stream(chars).boxed().toList()));
        }
        for (int i = idx; i < chars.length; i++) {
            swap(chars, i, idx);
            getPermutations(chars, idx+1, set);
            swap(chars, i, idx);
        }
    }

    private static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    private static void swap(char[] data, int i, int j) {
        char temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        String str1 = "abc";
        Set<String> set = new HashSet<>();

        getPermutations(str1.toCharArray(),0,  set);
        System.out.println(set);

    }
}
