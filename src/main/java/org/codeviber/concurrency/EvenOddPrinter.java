package codeviber.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class EvenOddPrinter {
    public static void main(String[] args) {

        int[] even = {2,4,6,8,10};
        int[] odd = {1,3,5,7,9};
        printEvenOdd(even,odd);

    }

    public static void printEvenOdd(int[] even,  int[] odd) {
        Object lock = new Object();
        AtomicBoolean isEven = new AtomicBoolean(false);
        try(ExecutorService executor = Executors.newFixedThreadPool(2)) {
            executor.submit(() -> {
                for (int i = 0; i < odd.length; i++) {
                    while(isEven.get()) {
                    }

                    synchronized (lock) {
                        System.out.println("Odd: " + odd[i]);
                        isEven.set(true);
                        lock.notify();
                    }

                }
            });
            executor.submit(() -> {
                for (int i = 0; i < even.length; i++) {
                    while(!isEven.get()) {
                    }

                    synchronized (lock) {
                        System.out.println("Even: " + even[i]);
                        isEven.set(false);
                        lock.notify();
                    }

                }
            });
        }


    }
}
