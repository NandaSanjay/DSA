package codeviber.windowslider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KLengthNonRepeatSubStringFinder {
    public static boolean checkIfAllUnique(String str) {
//      Sol -1 
//        for(char ch : str.toCharArray()) {
//            if (str.lastIndexOf(ch) != str.indexOf(ch))
//                return false;
//        }
//      Sol -2
//        Set<Character> set = new HashSet<>();
//        for (int i = 0; i < str.length(); i++) {
//            if (!set.add(str.charAt(i))) {
//                return false;
//            }
//        }
//
//        return true;
//      Sol-3
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        for(int i = 0; i < chars.length-1; i++) {
            if (chars[i] == chars[i + 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static List<String> findNonRepeatedSubStringLength(String str, int k) {
        List<String> list = new ArrayList<>();
        int[] arr = new int[128];
        int maxLength = 0;
        int r = 0; int l = 0;
        while (r < str.length()) {
            l = Math.max(l, arr[str.charAt(r)] );
            arr[str.charAt(r)] = r+1;

            maxLength = Math.max(maxLength, r-l +1);
            if (r-l +1 == k) {
                list.add(str.substring(l, r+1));
                l++;
            }
            r++;

        }
        return list;
    }


    public static void main(String[] args) {
        String str1 = "geeksforgeeks";
        String str2 = "abba";
        System.out.println(str1);
        System.out.println(findNonRepeatedSubStringLength(str1, 5));

//        System.out.println(str2);
//        System.out.println(findNonRepeatedSubString(str2));
    }

    public static boolean checkStringPermutation(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }
}


