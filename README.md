Web Crawler
===========

1. Read a string (search term) from standard input
2. Get a Bing result page for the search term
3. Extract main result links from the page
4. Download the respective pages and extract the names of javascript libraries used in them
5. Print top 5 most used libraries to standard output

I've used 
    * jsoup library for parsing HTML pages.
    * completableFuture class for handling tasks that can run concurrently in a non-blocking way
    * wiremock to stub http calls in tests
    * junit5 for unit tests
    
To start Main Application:

```
mvn clean install

java -jar target/webcrawler-1.0.jar

```



