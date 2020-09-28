package com.oneaccount;

import com.github.tomakehurst.wiremock.WireMockServer;
import fixtures.Fixtures;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class AppTest {

    private WireMockServer wireMockServer;

    @BeforeAll
    void setup() throws IOException {
        wireMockServer = new WireMockServer(8090);
        wireMockServer.start();
        Fixtures.setupStub(wireMockServer);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @Test
    void main() {
        //given:
        final String url = "http://localhost:8090/index";

        //when:
        Map<String, Long> topJSLibraries = App.start(url);

        //then:
        assertThat(topJSLibraries.keySet()).containsExactlyInAnyOrder("jquery.js", "disney.js", "calculate.js", "snhb-loader.min.js", "sncmp_stub.min.js");
    }
}