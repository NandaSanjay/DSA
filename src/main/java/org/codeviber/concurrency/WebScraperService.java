package codeviber.concurrency;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class WebScraperService {

    public CompletableFuture<Integer> getTotalWordCount(List<String> urls) {
        // 1. Map each URL to a CompletableFuture task
        List<CompletableFuture<Integer>> futures = urls.stream()
                .map(url -> CompletableFuture.supplyAsync(() -> fetchAndCountWords(url),
                                Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                        // 2. Exception handling: Fallback to 0 if a site is down
                        .exceptionally(ex -> {
                            System.err.println("Error fetching " + url + ": " + ex.getMessage());
                            return 0;
                        }))
                .toList();

        // 3. allOf() waits for all independent tasks to finish
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    // 4. Sum up all results once they are ready
                    return futures.stream()
                            .map(CompletableFuture::join)
                            .reduce(0, Integer::sum);
                });
    }

    private int fetchAndCountWords(String url) {
        // Simulate network call
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        return url.length(); // Simplified logic for example
    }
}
