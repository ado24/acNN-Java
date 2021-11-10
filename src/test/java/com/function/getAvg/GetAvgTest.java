package com.function.getAvg;

import com.utility.http.HttpRequestBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.*;
import java.net.http.HttpRequest;

import static org.junit.jupiter.api.Assertions.fail;

class GetAvgTest {
    private final HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
    String defaultUri = "http://localhost:7071/api/getAvg?";
    String testJson =  "{'value': [80, 145, 190]}";
    String queryStr = "value=[80,145,190]";
    String output = "{\"value\":[138.33333333333334],\"count\":3}";
    GetAvg testObj;
    double expectedValue = 138.33333333333334;

    @BeforeEach
    void setUp() {
        testObj = new GetAvg();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void run() {
        HttpResponse<String> response;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = httpRequestBuilder.getHttpRequest(testJson, this.defaultUri, "application/json");

/*        String resp = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(f -> new String("Test"))
                .join();*/
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Assertions.assertEquals(response.statusCode(), 200);

            com.google.gson.Gson parser = new com.google.gson.Gson();
            com.model.ScoreContainer test = parser.fromJson(response.body(), com.model.ScoreContainer.class);

            Assertions.assertEquals(test.getValue().get(0), expectedValue, 0.001);

            Assertions.assertTrue(response.body().equalsIgnoreCase(output));
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

            com.google.gson.Gson parser = new com.google.gson.Gson();
            com.model.ScoreContainer test = parser.fromJson(response.body(), com.model.ScoreContainer.class);

            Assertions.assertEquals(test.getValue().get(0), expectedValue, 0.001);
            Assertions.assertTrue(response.body().equalsIgnoreCase(output));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}