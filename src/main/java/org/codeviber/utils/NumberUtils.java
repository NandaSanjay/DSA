package codeviber.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberUtils {
    public static void main(String[] args) {

//        //Geven a list of integers, devide it into two list one having even nos and the other odds
//        int[] data = {1,2,3,4,5,6};
//         Collection<List<Integer>> collect = Arrays.stream(data)
//                .boxed()
//                .collect(Collectors.groupingBy(x -> x%2 == 0, Collectors.toList()))
//                .values();
//
//        Collection<List<Integer>> collect1 = Arrays.stream(data).boxed()
//                .collect(Collectors.partitioningBy(x -> x % 2 == 0, Collectors.toList()))
//                        .values();
//        //System.out.println(collect1);
//
//        // Given an int[], rearrange to find largest number
//        int[] data2 = {1,3,5,8,2,4,8,8,8,2,2,4};
//        //Arrays.stream(data2).boxed().sorted(Comparator.reverseOrder()).forEach(System.out::print);
//
//        //Given an array find the sum of unique elements
//        int sum = Arrays.stream(data2).distinct().sum();
//
//        System.out.println(sum);
//
//        int[] data3 = {4,6,12,13,24,5,36};
//        Map<Integer, List<Integer>> collect2 = Arrays.stream(data3).boxed()
//                .collect(Collectors.groupingBy(x -> x / 10 * 10, LinkedHashMap::new, Collectors.toList()));
//
//        System.out.println(collect2);
//
//        multiplyAlternatives();
//
//        int[] data4 = {4,5,1,7,2,9};
//        IntStream.range(0, data4.length/2).map(x-> data4[x] * data4[data4.length-x-1]).forEach(System.out::println);
//
//        int[] data5 = {0,2,5,7,0,9};
//        List<Integer> l1 = Arrays.stream(data5).boxed().filter(x -> x == 0).toList();
//        List<Integer> l2 = Arrays.stream(data5).boxed().filter(x -> x != 0).toList();
//        List<Integer> result = new ArrayList<>();
//        result.addAll(l1);
//        result.addAll(l2);
//
//        Collection<List<Integer>> values = Arrays.stream(data5)
//                .boxed()
//                .collect(Collectors.partitioningBy(x -> x == 0, Collectors.toList())).values();
//
//        List<Integer> list = values.stream().flatMap(Collection::stream).toList();
//        System.out.println(list);

//        int[] data = {1,2,3,4,5};
//        Arrays.stream(data).reduce((x,y)->x+y).ifPresent(System.out::println);

        int[] inData = {2,2,2,1,1,1,3,4}; int k = 2;
        Map<Integer, Long> collect = Arrays.stream(inData)
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<Integer> list = collect.entrySet().stream()
                .sorted(Map.Entry.<Integer,Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(k)
                .map(Map.Entry::getKey)
                .toList();

        System.out.println(list);


    }
    private static void multiplyAlternatives() {

        //Multiply alternative numbers in array
        int[] data = new int[]{4,5,1,7,2,9,2};
        Integer reduce = IntStream.range(0, data.length).filter(i -> i % 2 == 0).mapToObj(i -> data[i])
                .reduce(1, (a, b) -> a * b);
        System.out.println(reduce);
    }
}
