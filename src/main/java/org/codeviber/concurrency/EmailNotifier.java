package codeviber.concurrency;

import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EmailNotifier {
    public static List<String> processNotification(List<String> users, ExecutorService executor)
            throws ExecutionException, InterruptedException, TimeoutException {

        Map<String, String> userEmails = getUserEmails(users);
        List<CompletableFuture<String>> notifFutures = users.stream()
                .map(user -> {
                    return CompletableFuture.supplyAsync(() -> {
                        return sendEmail(userEmails.get(user));
                    }, executor).exceptionally(e -> {
                        System.out.println("failed to send Email for user " + user);
                        return null;
                    });
                })
                .toList();
        CompletableFuture<Void> joinedFutute =  CompletableFuture
                .allOf(notifFutures.toArray(new CompletableFuture[0]));

        return  joinedFutute.thenApply(v->
             notifFutures.stream()
                    .map(CompletableFuture::join)
                    .toList()
                ).get(1, TimeUnit.MINUTES);


    }

    private static String sendEmail(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Sending email to " + s);
        return "Email Send to :" + s;
    }

    private static Map<String, String> getUserEmails(List<String> users) {

        return IntStream.range(0, users.size())
                .boxed()
                .collect(Collectors.toMap(
                        users::get,
                        i -> "user." + users.get(i) + "@email.com"
                ));

    }

    public static void main(String[] args) {
        List<String> users = Stream.generate(() -> UUID.randomUUID().toString())
                .limit(100)
                .toList();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> notifData = null;
        try {
            notifData = processNotification(users, TaskExecutor.getTaskExecutor());
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        if (notifData == null) {
            System.out.println("No emails found");
        }
        String response = String.join("\n", notifData);
        System.out.println(response);
        stopWatch.stop();
        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));

    }
}
