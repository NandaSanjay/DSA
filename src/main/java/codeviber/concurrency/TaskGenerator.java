package codeviber.concurrency;

import java.util.concurrent.ExecutionException;

public class TaskGenerator {
    static final Object lock = new Object();
    static boolean evenFlag = true;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] evenNumbers = {1,3,5,7,9};
        int[] oddNumbers = {2,4,6,8,10};

        TaskExecutor.getTaskExecutor()
                .submit(() ->
                {
                    for (int i = 0; i < evenNumbers.length; i++) {
                        synchronized (lock) {
                            while (!evenFlag) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            System.out.println(Thread.currentThread().getName() + ": " + evenNumbers[i]);
                            evenFlag = false;
                            lock.notifyAll();

                        }
                    }

                });

        TaskExecutor.getTaskExecutor()
                .submit(() ->
                {
                    for (int i = 0; i < oddNumbers.length; i++) {
                        synchronized (lock) {
                            while (evenFlag) {
                                try {
                                    lock.wait();
                                }  catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            System.out.println(Thread.currentThread().getName() + ": " + oddNumbers[i]);
                            evenFlag = true;
                            lock.notifyAll();
                        }
                    }
                });



    }
}
