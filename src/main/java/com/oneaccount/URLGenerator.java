package com.oneaccount;

public class URLGenerator {
    private String searchItem;

    public URLGenerator(final String searchItem) {
        this.searchItem = searchItem;
    }

    public String generate() {
        return String.format("https://www.bing.com/search?q=%s", normalizeSearchItem(searchItem));
    }

    private String normalizeSearchItem(String param) {
        return param.toLowerCase().replaceAll(" ", "+");
    }
}
