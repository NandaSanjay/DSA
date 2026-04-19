package org.example;

import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {

        int[] data = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        String s ="Java is a awesome programming language";
        List list  = Arrays.stream(data)
                .boxed()
                .filter(x -> ( x %2 == 0))
                .toList();
        List<Character> charList =  s.chars()
                .mapToObj(c -> (char) c)
                .filter(character ->
                    s.indexOf(character) == s.lastIndexOf(character))
                .toList();

       System.out.println(list);
       System.out.println(charList);

    }
}
