package codeviber.concurrency;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.*;

public class FinancialReportAggregator {

    private static ExecutorService executor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static String fetchFinancialReport(String emailAddress) {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return getUserId(emailAddress);
        }, executor).exceptionally(ex -> {
            System.out.println("Error while fetching user context for " + emailAddress);
            return null;
        });
        CompletableFuture<String> reportFuture = future.thenCompose(id -> {
            CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
                return fetchCreditScore(id);
            }, executor).exceptionally(ex -> {
                System.out.println("Error while fetching user context for " + emailAddress);
                return "Credit Score | DATA UNAVAILABLE";
            });

            CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
                return getTxnHisoty(id);
            }, executor).exceptionally(ex -> {
                System.out.println("Error while fetching user context for " + emailAddress);
                return "TXN History | DATA UNAVAILABLE";
            });

             return f1.thenCombine(f2, (creditScore, txnHisotry) -> {
                return creditScore + "\n" + txnHisotry;
            });

        });

        try {
            return reportFuture.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Error while fetching financial Report " + emailAddress);
            return null;
        } finally {
            executor.shutdown();
        }
    }

    private static String getTxnHisoty(String id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return """
                TXN HISTORY |
                    Date: 2026-04-20 | Amount: $500.00 | Type: Deposit
                    Date: 2026-04-22 | Amount: $120.50 | Type: Withdrawal
                    Date: 2026-04-25 | Amount: $45.00  | Type: Merchant
                """;
    }

    private static String fetchCreditScore(String id)  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return """
                CREDIT SCORE |
                    750/900
                """;
    }

    private static String getUserId(String emailAddress) {
        return emailAddress.substring(0, emailAddress.lastIndexOf("@"));
    }

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        System.out.println(fetchFinancialReport("sanjay.s.nanda@orcle.com"));
        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));

    }
}
