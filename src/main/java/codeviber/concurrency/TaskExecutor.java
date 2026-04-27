package codeviber.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
    private static ExecutorService executor;
    public static ExecutorService getTaskExecutor() {
        if(executor == null) {
            executor = new ThreadPoolExecutor(5, Integer.MAX_VALUE,
                    60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());
        }
        return executor;
    }
}
