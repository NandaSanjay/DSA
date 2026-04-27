package codeviber.concurrency;

public class ThreadPractices {
    public static void main(String[] args) {
        Thread t = new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getPriority());
                throw new RuntimeException("Crashed");

        }, "Thread-1");
        t.setPriority(Thread.MIN_PRIORITY);
        t.setUncaughtExceptionHandler((thread, throwable) -> {
            System.out.println(thread.getName() + " crashed and caught " + throwable);
        });
        t.start();
    }
}
