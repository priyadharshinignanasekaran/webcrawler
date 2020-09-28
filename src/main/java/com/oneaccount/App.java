package com.oneaccount;

import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import static java.lang.System.currentTimeMillis;

public class App {

    public static void main(String[] args) throws Exception {
        System.out.print("Enter search string: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String searchItem = br.readLine();

        String url = new URLGenerator(searchItem).generate();

        final long startTime = currentTimeMillis();
        System.out.println(start(url));
        final long endTime = currentTimeMillis();

        System.out.println("Total time taken:" + (endTime - startTime) * 0.001 + "seconds");
    }

    public static Map<String, Long> start(final String url) {
        WebCrawler crawler = new WebCrawler();
        Set<Document> documents = crawler.crawl(url);
        return JSLibraryAnalyzer.top5JSLibraries(documents);
    }
}
