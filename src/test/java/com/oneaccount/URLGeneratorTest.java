package com.oneaccount;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class URLGeneratorTest {

    @Test
    void spacesInSearchItemAreReplacedWithPlusBeforeAddingAsQueryParamToURL() {
        //given:
        String searchItem = "Mickey Mouse clubhouse";
        String expectedUrl = "https://www.bing.com/search?q=mickey+mouse+clubhouse";

        //when:
        String normalizedUrl = new URLGenerator(searchItem).generate();

        //then:
        assertThat(normalizedUrl).isEqualTo(expectedUrl);
    }

    @Test
    void searchItemCanIncludeSymbolsAndNumbers() {
        //given:
        String searchItem = "%Mickey Mouse 123";
        String expectedUrl = "https://www.bing.com/search?q=%mickey+mouse+123";

        //when:
        String normalizedUrl = new URLGenerator(searchItem).generate();

        //then:
        assertThat(normalizedUrl).isEqualTo(expectedUrl);
    }
}