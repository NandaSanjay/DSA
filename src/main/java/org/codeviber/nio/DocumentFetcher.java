package org.codeviber.nio;

import org.apache.commons.lang3.time.StopWatch;
import org.codeviber.concurrency.TaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentFetcher {

    private static final Logger log = LoggerFactory.getLogger(DocumentFetcher.class);

    public static HttpClient client;
    private static final AtomicInteger successCounter = new AtomicInteger(0);
    private static final AtomicInteger failureCounter = new AtomicInteger(0);
    private static final AtomicInteger totalCounter = new AtomicInteger(0);
    private static final List<String> gitLinks = new ArrayList<>();
    private static final List<String> oneNote = new ArrayList<>();


    private static HttpClient getHTTPClient() {
        if (client == null) {
            client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
        }
        return client;

    }

    public static void fetchDocuments(String path) throws IOException, ExecutionException, InterruptedException {

        Pattern urlPattern = Pattern.compile("https?://\\S+");

        Path filePath = Path.of(path);
        List<String> linkLines = null;

        List<CompletableFuture<String>> futureList = new ArrayList<>();
        String currentFolder = "default";

        StopWatch watch = new StopWatch("Doc Fetcher");
        watch.start();

        try (Stream<String> lines = Files.lines(filePath)) {

            for(String linkLine : lines.toList()) {

                if(linkLine == null || linkLine.trim().isEmpty()) {
                    continue;
                }
                // 🔹 Detect folder
                if (linkLine.contains("@@")) {
                    currentFolder = extractFolderName(linkLine);
                    continue;
                }
                if (linkLine.contains("[GIT LAB]")) {
                    gitLinks.add(linkLine);
                    continue;
                }
                if (linkLine.contains("[ONENOTE]")) {
                    oneNote.add(linkLine);
                    continue;
                }
                Matcher matcher = urlPattern.matcher(linkLine);

                if (matcher.find()) {

                    String url = matcher.group();
                    String fileName = extractFileName(linkLine);
                    String folder = currentFolder;

                    futureList.add(
                            CompletableFuture.supplyAsync(() ->
                                            loadFiles(url, folder, fileName),
                                    TaskExecutor.getExecutor()
                            )
                    );
                } else {
                    log.info("Missing URL : {}", linkLine);

                }
            }

        }

        CompletableFuture<Void> joinedFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
        CompletableFuture<Void> responseFuture = joinedFuture.thenAccept(v->{
            Map<String, Long> responses = futureList.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.groupingBy(s->s, Collectors.counting()));

            log.info("Result : {}", responses);
            log.info("Total : {} | Success : {} | Failed : {} ",totalCounter, successCounter, failureCounter);

        }) ;

        responseFuture.get();
        watch.stop();
        log.info("Run Duration : {}", watch.getTime(TimeUnit.MINUTES));

        log.info("GIT Links : ");
        log.info(String.join(" \n\t",gitLinks));

        log.info("ONENOTE Links : ");
        log.info(String.join(" \n\t",oneNote));

    }

    private static String loadFiles(String url, String folder, String fileName) {
        int fileCount = totalCounter.incrementAndGet();
        String directUrl = convertToDirectDownload(url);

        // 1. Define the target path (src/main/resources/downloads)
        // NOTE: This path strategy only works in a development environment (IDE)
        Path basePath = Paths.get("src", "main", "resources", "downloads");
        Path resourcesPath = basePath.resolve(folder);
        Path targetFile = resourcesPath.resolve(fileName +".pdf");

        try {
            // Ensure the directory exists
            Files.createDirectories(resourcesPath);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(directUrl))
                    .header("User-Agent", "Mozilla/5.0")
                    .GET()
                    .build();

            log.info("Downloading file {} | URL : {}", fileCount, url);

            // 3. Send Request and Save directly to File
            // BodyHandlers.ofFile handles the file creation and writing efficiently
            HttpResponse<Path> response = getHTTPClient().send(request,
                    HttpResponse.BodyHandlers.ofFile(targetFile,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.WRITE,
                            StandardOpenOption.TRUNCATE_EXISTING));

            String contentType = response.headers().firstValue("Content-Type").orElse("");

            successCounter.incrementAndGet();
            return "SUCCESS";

        } catch (IOException | InterruptedException e) {
            log.error("Failed to fetch : {}", e.getMessage());
            failureCounter.incrementAndGet();
            return "FAILED";
        }
    }

    private static String sanitize(String input) {
        return input
                .replaceAll("[^a-zA-Z0-9_\\s]", "") // remove special chars
                .trim()
                .replaceAll("\\s+", "_"); // spaces -> _
    }

    private static String extractFileName(String line) {
        String beforeUrl = line.split("https")[0];

        // remove trailing colon
        beforeUrl = beforeUrl.replace(":", "").trim();

        return sanitize(beforeUrl);
    }

    private static String extractFolderName(String line) {
        return sanitize(line.replaceAll("@@", "").trim());
    }

    private static String convertToDirectDownload(String url) {
        if (url.contains("/file/d/")) {
            try {
                String fileId = url.split("/d/")[1].split("/")[0];
                return "https://drive.google.com/uc?export=download&id=" + fileId;
            } catch (Exception e) {
                return url;
            }
        }
        return url;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        fetchDocuments("/Users/sanjaynanda/workspace/DSA/src/main/resources/Unlock_LLD_HLD_TEXT.txt");
    }
}
