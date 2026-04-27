package codeviber.ds.arrays;

import java.util.Arrays;

public class ArrayRotator {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        rotate(arr, -1);
        System.out.println(Arrays.toString(arr));

    }

    public static void rotate(int[] arr, int n) {
        if (n == 0)
            return;

        n = n % arr.length;
        if (n < 0)
            n = n + arr.length;

        reverse(arr, 0, n-1);
        reverse(arr, n, arr.length-1);
        reverse(arr,0, arr.length-1);
    }

    private static void reverse(int[] arr, int start, int end) {
        int temp = 0;
        while (start < end) {
            temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }

    }
}
