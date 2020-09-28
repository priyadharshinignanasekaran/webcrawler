package com.oneaccount;

import org.jsoup.nodes.Document;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class JSLibraryAnalyzer {

    private JSLibraryAnalyzer() {
    }

    public static Map<String, Long> top5JSLibraries(final Set<Document> documents) {
        Set<Entry<String, Long>> futureJSLibrary = supplyAsync(() -> getAllJSLibraryByCount(documents)).join();
        return futureJSLibrary.stream()
                .limit(5)
                .collect(toMap(Entry::getKey, Entry::getValue));
    }

    private static Set<Entry<String, Long>> getAllJSLibraryByCount(Set<Document> documents) {
        return documents.stream()
                .map(JSLibraryAnalyzer::getJavaScriptLibraries)
                .flatMap(Collection::stream)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet().stream()
                .sorted(comparingByValue(reverseOrder()))
                .collect(toCollection(LinkedHashSet::new));
    }

    private static List<String> getJavaScriptLibraries(Document document) {
        return document.select("script").stream()
                .filter(element -> element.hasAttr("src"))
                .map(element -> {
                    String[] splitPaths = element.attr("src").split("/");
                    return splitPaths[splitPaths.length - 1];
                })
                .filter(js -> js.endsWith(".js"))
                .collect(toList());
    }
}
