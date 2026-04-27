package codeviber.utils;

import java.util.*;
import java.util.stream.Collectors;

public class StringsUtil {

    public static void main(String[] args) {

        String s1 = "I am learning streams API in java";

//        findHighestWord(s1);
//
//        findNHighestLenthWord(s1);
//
//        getEachWordAlongWithLength(s1);
//
//        findWordWithSpecifiedNoOfVowels(s1);
//
//        findOccuranceOfChars();
//
//        findFirstNonRepeatedChar(s1);
//
//        getNumbersInStrings();
//
//        groupAnagrams();

//        String s = "bangalore";
//        System.out.println(getLongestUniquesSubString(s));

        List<String> list = Arrays.asList("apple", "orange", "banana", "papaya", "cherry", "avocado");
        Map<Character, Long> collected = list.stream()
                .collect(Collectors.groupingBy(x ->x.charAt(0), Collectors.counting()));
        System.out.println(collected);

        list.stream().sorted(Comparator.reverseOrder()).toList();
        System.out.println(list);


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

    private static void groupAnagrams() {
        /* Group Anagram words in a string
         * face - cafe
         */
        String[] anagrams = new String[] {"pat","tap","pan","nap","Team","meet","meat"};
        Collection<List<String>> values = Arrays.stream(anagrams)
                .collect(Collectors.groupingBy(
                        x -> Arrays.stream(x.toLowerCase().split(""))
                                .sorted()
                                .toList()))
                .values();
        System.out.println(values);
    }

    private static void findHighestWord(String s1) {
        String maxString = Arrays.stream(s1.split(" ")).max(Comparator.comparing(String::length))
                .get();
        //System.out.println(maxString);
    }

    private static void findNHighestLenthWord(String s1) {
        //Second height word in a sentence.
        Integer len = Arrays.stream(s1.split(" ")).map(s->s.length() )
                .sorted(Comparator.reverseOrder())
                .skip(1).findFirst().get();

        //System.out.println(len);
    }

    private static void getEachWordAlongWithLength(String s1) {
        //Get each words with its length
        Map<String, Long> collect = Arrays.stream(s1.split(" "))
                .collect(Collectors.groupingBy(x->x, Collectors.counting()));
        // System.out.println(collect);
    }

    private static void findWordWithSpecifiedNoOfVowels(String s1) {
        //Given a sentence find the words with specified number of vowels. v=2
        Arrays.stream(s1.split(" "))
                .filter(s -> s.replaceAll("[^aeiouAEIOU]", "").length() == 2)
                .forEach(System.out::println);
    }

    private static void findOccuranceOfChars() {
        //Geven a word find the occurance of each chars
        String word = "Mississippim";
        Map<String, Long> collect1 = Arrays.stream(word.split(""))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
        //System.out.println(collect1);
    }

    private static void findFirstNonRepeatedChar(String s1) {
        //Given a string find the 1st non repeated char
        String s = "Hello World";
        String s2 = Arrays.stream(s.split("")).filter(x -> s.indexOf(x) == s.lastIndexOf(x))
                .findFirst().get();
        //System.out.println(s1);
    }

    private static void getNumbersInStrings() {
        //s2.chars().distinct().mapToObj(x-> (char)x).forEach(System.out::print);

        String[] s3 = new String[]{"abc","123","456","xyz"};
        List<Integer> list = Arrays.stream(s3).filter(x -> x.matches("[0-9]+"))
                .map(Integer::parseInt)
                .toList();

        //System.out.println(list);
    }


}
