package codeviber.strings;

import org.apache.commons.lang3.time.StopWatch;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FirstUniqueCharFinder {
    public static void main(String[] args) {
        String input = "abcdefabdefgkabdneidjeijdiejdiejdiejdfiejfdiejfdpejfd]pejf2ofj";

        //O(n2)
        StopWatch stopWatch1 = new StopWatch();
        stopWatch1.start();
        IntStream.range(0, input.length())
                .filter(index -> input.indexOf(input.charAt(index)) == input.lastIndexOf(input.charAt(index)))
                .mapToObj(i -> (char)input.charAt(i))
                .findFirst()
                .ifPresent(System.out::println);
        stopWatch1.stop();
        System.out.println(stopWatch1.getTime(TimeUnit.NANOSECONDS));

        //O(n)
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LinkedHashMap<Character, Long> map = input.chars()
                .mapToObj(x -> (char)x)
                .collect(Collectors.groupingBy(c->c, LinkedHashMap::new,  Collectors.counting()));

        map.entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst()
                .ifPresent(System.out::println);
        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.NANOSECONDS));
    }
}
