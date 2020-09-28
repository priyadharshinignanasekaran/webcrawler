package fixtures;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.IOUtils.toByteArray;

public class Fixtures {

    private Fixtures() {
    }

    public static void setupStub(final WireMockServer wireMockServer) throws IOException {
        final String noLinkMainPage = new String(toByteArray(getResource("nolink_main_page.html")), UTF_8);
        final String invalidLinksMainPage = new String(toByteArray(getResource("main_page_with_invalid_links.html")), UTF_8);
        final String unreachableLinksMainPage = new String(toByteArray(getResource("main_page_with_unreachable_links.html")), UTF_8);
        final String noMainTagMainPage = new String(toByteArray(getResource("main_page_without_main_tag.html")), UTF_8);
        final String mainPage = new String(toByteArray(getResource("main_page.html")), UTF_8);
        final String page1 = new String(toByteArray(getResource("page1.html")), UTF_8);
        final String page2 = new String(toByteArray(getResource("page2.html")), UTF_8);
        final String page3 = new String(toByteArray(getResource("page3.html")), UTF_8);
        final String page4 = new String(toByteArray(getResource("page4.html")), UTF_8);
        final String page5 = new String(toByteArray(getResource("page5.html")), UTF_8);
        final String page6 = new String(toByteArray(getResource("page6.html")), UTF_8);
        final String page7 = new String(toByteArray(getResource("page7.html")), UTF_8);
        final String page8 = new String(toByteArray(getResource("page8.html")), UTF_8);
        final String page9 = new String(toByteArray(getResource("page9.html")), UTF_8);
        final String page10 = new String(toByteArray(getResource("page9.html")), UTF_8);
        final String noScriptPage = new String(toByteArray(getResource("no_script_page.html")), UTF_8);


        wireMockServer.stubFor(get(urlEqualTo("/index"))
                .willReturn(aResponse()
                        .withBody(mainPage)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/noLinkIndex"))
                .willReturn(aResponse()
                        .withBody(noLinkMainPage)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/invalidLinksIndex"))
                .willReturn(aResponse()
                        .withBody(invalidLinksMainPage)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/unreachableLinksIndex"))
                .willReturn(aResponse()
                        .withBody(unreachableLinksMainPage)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );


        wireMockServer.stubFor(get(urlEqualTo("/noMainTagIndex"))
                .willReturn(aResponse()
                        .withBody(noMainTagMainPage)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );


        wireMockServer.stubFor(get(urlEqualTo("/page1.html/"))
                .willReturn(aResponse()
                        .withBody(page1)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page2.html/"))
                .willReturn(aResponse()
                        .withBody(page2)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page3.html/"))
                .willReturn(aResponse()
                        .withBody(page3)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page4.html/"))
                .willReturn(aResponse()
                        .withBody(page4)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page5.html/"))
                .willReturn(aResponse()
                        .withBody(page5)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page6.html/"))
                .willReturn(aResponse()
                        .withBody(page6)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page7.html/"))
                .willReturn(aResponse()
                        .withBody(page7)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page8.html/"))
                .willReturn(aResponse()
                        .withBody(page8)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page9.html/"))
                .willReturn(aResponse()
                        .withBody(page9)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/page10.html/"))
                .willReturn(aResponse()
                        .withBody(page10)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );

        wireMockServer.stubFor(get(urlEqualTo("/no_script_page.html/"))
                .willReturn(aResponse()
                        .withBody(noScriptPage)
                        .withHeader("Content-Type", "text/html; charset=UTF-8")
                )
        );
    }

    private static InputStream getResource(String fileName) {
        return Fixtures.class.getClassLoader().getResourceAsStream(fileName);
    }
}
