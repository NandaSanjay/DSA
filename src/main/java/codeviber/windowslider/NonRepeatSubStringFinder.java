package codeviber.windowslider;

import java.util.Arrays;

public class NonRepeatSubStringFinder {
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
    
    public static int findNonRepeatedSubStringLength(String str) {
        int[] arr = new int[128];
        int maxLength = 0;
        int r = 0; int l = 0;
        while (r < str.length()) {
            l = Math.max(l, arr[str.charAt(r)] );
            arr[str.charAt(r)] = r+1;

            maxLength = Math.max(maxLength, r-l +1);
            r++;

        }
        return maxLength;
    }
//    Solotion -2
//    public int lengthOfLongestSubstring(String str) {
//        Map<Character, Integer> map = new HashMap<Character, Integer>();
//        int maxLength = 0; int localmaxLength = 0;
//        int r = 0; int l = 0;
//        while (r < str.length()) {
//
//            if (map.containsKey(str.charAt(r))) {
//                int idx =  map.get(str.charAt(r));
//                if (l <= idx) l = idx + 1;
//                localmaxLength = r-l +1;
//                if (localmaxLength > maxLength) {
//                    maxLength = localmaxLength;
//                }
//                map.put(str.charAt(r), r);
//                r++;
//            } else {
//                map.put(str.charAt(r), r);
//                localmaxLength++;
//                if (localmaxLength > maxLength) {
//                    maxLength = localmaxLength;
//                }
//                r = r+1;
//
//            }
//        }
//        return maxLength;
//    }

    public static String findNonRepeatedSubString(String str) {
        int[] arr = new int[128]; int strLastIdx = 0;
        int maxLength = 0;
        int r = 0; int l = 0;
        while (r < str.length()) {
            l = Math.max(l, arr[str.charAt(r)] );
            arr[str.charAt(r)] = r+1;

            if (maxLength <= r-l +1) {
                maxLength = r-l +1;
                strLastIdx = r;
            }
            r++;

        }
        return str.substring(strLastIdx-maxLength+1, strLastIdx+1);
    }

    private static int getLongestUniquesSubString(String s) {
        int[] bitStore = new int[128];
        int l=0, r=0, longest = 0;
        while(r<s.length()) {
            l = Math.max(l, bitStore[s.charAt(r)]);
            bitStore[s.charAt(r)] = r+1;
            r++;
            longest = Math.max(longest, r-l);
        }
        return longest;
    }


    public static void main(String[] args) {
        String str1 = "bangalore";
        String str2 = "abba";
        System.out.println(str1);
        System.out.println(findNonRepeatedSubString(str1));


        System.out.println(str2);
        System.out.println(findNonRepeatedSubString(str2));
    }

    public static boolean checkStringPermutation(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
    }
}


