package com.oneaccount;

import com.github.tomakehurst.wiremock.WireMockServer;
import fixtures.Fixtures;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JSLibraryAnalyzerTest {

    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() throws IOException {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        Fixtures.setupStub(wireMockServer);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    void JSLibraryAnalyserCanGetTop5JSLibraries() throws IOException {
        //given:
        Set<Document> documents = new HashSet<>();
        documents.add(Jsoup.connect("http://localhost:8090/page1.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page2.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page3.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page4.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page5.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page6.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page7.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page8.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page9.html/").get());
        documents.add(Jsoup.connect("http://localhost:8090/page10.html/").get());

        //when:
        Map<String, Long> top5JSLibraries = JSLibraryAnalyzer.top5JSLibraries(documents);

        //then:
        assertThat(top5JSLibraries.keySet()).containsExactlyInAnyOrder("jquery.js", "disney.js", "calculate.js", "snhb-loader.min.js", "sncmp_stub.min.js");
    }

    @Test
    void JSLibraryAnalyserReturnEmptyLibrariesSetWhenThereAreNoScriptsOnTheWebPage() throws IOException {
        //given:
        Set<Document> documents = new HashSet<>();
        documents.add(Jsoup.connect("http://localhost:8090/no_script_page.html/").get());

        //when:
        Map<String, Long> top5JSLibraries = JSLibraryAnalyzer.top5JSLibraries(documents);

        //then:
        assertThat(top5JSLibraries.keySet()).isEmpty();
    }
}

