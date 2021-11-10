package com.utility.http;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestBuilder {
    public static final String APPLICATION_JSON = "application/json";

    public HttpRequest getHttpRequest(String payload, String uri, String contentType) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
    }

    public HttpRequest getHttpRequest(String payload, String uri) {
        return getHttpRequest(payload, uri, APPLICATION_JSON );
    }

    public HttpRequest getHttpGetRequest(String uri, String query, String contentType) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri + query))
                .header("Content-Type", contentType)
                .GET()
                .build();
    }

    public HttpRequest getHttpGetRequest(String uri, String query){
        return getHttpGetRequest(uri, query, APPLICATION_JSON);
    }
}