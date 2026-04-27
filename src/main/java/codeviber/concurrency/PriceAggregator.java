package codeviber.concurrency;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PriceAggregator {

    static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {

        List<String> venders = List.of("Vendor-1", "Vendor-2", "Vendor-3", "Vendor-4", "Vendor-5");
        Double price = getAggregatedPrice(venders);
        System.out.println("Aggregated price: " + price);

    }

    public static Double getAggregatedPrice(List<String> venders) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<CompletableFuture<Double>> futures = venders.stream()
                .map(vender ->
                        CompletableFuture.supplyAsync(() -> {
                            return fetchPrice(vender);
                        }, executor))
                .toList();
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<Double> valFuture = allFutures.thenApply(v -> {
            return futures.stream().mapToDouble(CompletableFuture::join)
                    .average()
                    .orElse(0.0);
        });

        Double val = 0.0;
        try {
            val = valFuture.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Timed out waiting for price aggregation");
        }
        return val;
    }

    // Mock method representing a remote API call
    private static Double fetchPrice(String name) {
        // In a real scenario, this would involve a network call
        System.out.println(Thread.currentThread().getName() + " : Fetching price for " + name);
        return switch (name) {
            case "Vendor-1" -> 100.0;
            case "Vend0r-2" -> 120.0;
            case "Vendor-3" -> 110.0;
            case "Vendor-4" -> 130.0;
            default -> 140.0;
        };
    }


}
