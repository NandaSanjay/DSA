package codeviber.concurrency;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiExecutor {

    ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    List<Runnable> tasks;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        tasks.forEach(service::execute);
    }
}