package com.function.getScore;

import com.utility.http.HttpRequestBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class GetScoreTest {
    private final HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
    String defaultUri = "http://localhost:7071/api/getScore?";
    String queryStr = "value=[45,80,58]&weight=0.695";
    String testJson =  "{'value': [58,45,80], 'weight': 0.695}";
    String outputStr = "42.395";
    double output   = 42.395;

    GetScore testObj;

    @BeforeEach
    void setUp() {
        testObj = new GetScore();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void run() {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = httpRequestBuilder.getHttpRequest(testJson, defaultUri);

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(response.statusCode(), 200);
            Assertions.assertEquals(Double.parseDouble(response.body()), output, 0.001);
            Assertions.assertTrue(String.format("%.3f", Double.parseDouble(response.body())).equalsIgnoreCase(outputStr));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void queryRun() {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = httpRequestBuilder.getHttpGetRequest(defaultUri, queryStr);

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(response.statusCode(), 200);
            Assertions.assertEquals(Double.parseDouble(response.body()), output, 0.01);
            Assertions.assertTrue(String.format("%.3f", Double.parseDouble(response.body())).equalsIgnoreCase(outputStr));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}