package com.oneaccount;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toSet;

public class WebCrawler {

    private HashSet<Document> documents;

    private Predicate<Element> isValidLink = link -> link.hasAttr("href") &&
            (link.attr("href").startsWith("http") ||
                    link.attr("href").startsWith("https") ||
                    link.attr("href").startsWith("www")) && link.attr("href").endsWith("/");

    public WebCrawler() {
        this.documents = new HashSet<>();
    }

    public Set<Document> crawl(final String url) {
        CompletableFuture<Void> allDoneFuture =
                supplyAsync(() -> {
                    Optional<Document> pageContent = getPageContent().apply(url);
                    pageContent.ifPresent(document -> documents.add(document));
                    return pageContent;
                })
                        .thenApplyAsync(getMainPageLinks())
                        .thenApplyAsync(doForEach())
                        .thenApplyAsync(futures -> futures.toArray(CompletableFuture[]::new))
                        .thenComposeAsync(CompletableFuture::allOf);

        allDoneFuture.join();

        return documents;
    }

    private Function<Set<String>, Stream<CompletableFuture<Optional<Document>>>> doForEach() {
        return urls -> urls
                .parallelStream()
                .map(url -> supplyAsync(() -> getPageContent().apply(url)));
    }

    private Function<String, Optional<Document>> getPageContent() {
        return url -> {
            try {
                if (url.startsWith("http") || url.startsWith("https") || url.startsWith("www")) {
                    Optional<Document> document = of(Jsoup.connect(url).get());
                    documents.add(document.get());
                    return document;
                } else {
                    System.out.println("Invalid url: " + url);
                    return empty();
                }
            } catch (IOException e) {
                System.out.println("Unreachable url: " + url);
                return empty();
            }
        };
    }

    private Function<Optional<Document>, Set<String>> getMainPageLinks() {
        return doc -> doc.map(document -> document.select("main").select("a")
                .stream()
                .filter(isValidLink)
                .map(link -> link.attr("href"))
                .peek(System.out::println)
                .collect(toSet())).orElse(Collections.emptySet());
    }

}
