package com.function.getScore;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.utility.http.HttpRequestBuilder;
import com.utility.http.HttpResponseMessageMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GetScoreTest {
    private final HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
    String defaultUri = "http://localhost:7071/api/getScore?";
    String queryStr = "value=[45,80,58]&weight=0.695";
    String queryList = "[45,80,58]",
            queryWeight = "0.695";
    String testJson =  "{'value': [58,45,80], 'weight': 0.695}";
    String outputStr = "42.395";
    double expectedValue = 42.395;

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
            Assertions.assertEquals(response.statusCode(), HttpStatus.OK.value());
            Assertions.assertEquals(Double.parseDouble(response.body()), expectedValue, 0.001);
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
            Assertions.assertEquals(response.statusCode(), HttpStatus.OK.value());
            Assertions.assertEquals(Double.parseDouble(response.body()), expectedValue, 0.01);
            Assertions.assertTrue(String.format("%.3f", Double.parseDouble(response.body())).equalsIgnoreCase(outputStr));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testHttpTriggerJava() {
        // Setup
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("weight", queryWeight);
        queryParams.put("value", queryList);
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Invoke
        final HttpResponseMessage ret = testObj.run(req, context);

        // Verify
        try {
            Assertions.assertEquals(ret.getStatus(), HttpStatus.OK);
            Assertions.assertEquals(Double.parseDouble(ret.getBody().toString()), expectedValue, 0.01);
            Assertions.assertTrue(String.format("%.3f", Double.parseDouble(ret.getBody().toString())).equalsIgnoreCase(outputStr));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}