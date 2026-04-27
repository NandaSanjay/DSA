package codeviber.ds;

import java.util.Arrays;

public class MoveZeroToEnd {

    public static void main(String args[]) {
        int[] data = new int[]{2,5,0,2,7,0};
        int[] temp = new int[data.length];
        Arrays.sort(data);
        int j = 0;

        for (int i = 0; i < data.length; i++) {
            if (data[i] != 0) {
                temp[j] = data[i];
                j++;
            }
        }
        System.out.println(Arrays.toString(temp));


    }
}
