package com.oneaccount;

import com.github.tomakehurst.wiremock.WireMockServer;
import fixtures.Fixtures;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class WebCrawlerTest {

    private WireMockServer wireMockServer;
    private WebCrawler webCrawler;

    @BeforeEach
    public void setup() throws IOException {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        Fixtures.setupStub(wireMockServer);
        webCrawler = new WebCrawler();
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void crawlerCrawlsOnlyThroughValidLinksWithHrefAttributeOnMainPage() {
        //when:
        Set<Document> documents = webCrawler.crawl("http://localhost:8090/noLinkIndex");

        //then:
        assertThat(documents.size()).isEqualTo(1);
    }

    @Test
    public void crawlerCrawlsThroughAllReachableLinksInMainPage() {
        //when:
        Set<Document> documents = webCrawler.crawl("http://localhost:8090/index");

        //then:
        assertThat(documents.size()).isEqualTo(11);
    }

    @Test
    public void crawlerDoesntCrawlThroughInvalidLinksInMainPage() {
        //when:
        Set<Document> documents = webCrawler.crawl("http://localhost:8090/invalidLinksIndex");

        //then:
        assertThat(documents.size()).isEqualTo(1);
    }

    @Test
    public void crawlerIgnoresUnreachableLinksOnMainPage() {
        //when:
        Set<Document> documents = webCrawler.crawl("http://localhost:8090/unreachableLinksIndex");

        //then:
        assertThat(documents.size()).isEqualTo(1);
    }

    @Test
    public void crawlerDoesntCrawlWhenThereIsNoMainTagsOnMainPage() {
        //when:
        Set<Document> documents = webCrawler.crawl("http://localhost:8090/noMainTagIndex");

        //then:
        assertThat(documents.size()).isEqualTo(1);
    }
}