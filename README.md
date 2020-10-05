Web Crawler
===========

1. Read a string (search term) from standard input
2. Get a Bing result page for the search term
3. Extract main result links from the page
4. Download the respective pages and extract the names of javascript libraries used in them
5. Print top 5 most used libraries to standard output
    
To start Main Application:

```
mvn clean install

java -jar target/webcrawler-1.0.jar

```



