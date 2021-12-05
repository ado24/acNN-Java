package com.function.getAvg;

import com.microsoft.azure.functions.*;

import com.utility.http.HttpRequestBuilder;
import com.utility.http.HttpResponseMessageMock;
import org.junit.jupiter.api.*;

import java.util.*;
import java.net.http.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.invocation.*;
import org.mockito.stubbing.Answer;





class GetAvgTest {
    private final HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder();
    String defaultUri = "http://localhost:7071/api/getAvg?";
    String testJson =  "{'value': [80, 145, 190]}";
    String queryStr = "value=[80,145,190]";
    String queryList = "[80,145,190]";
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

/*    @AfterAll
    static void killServer() {
        try {
            ProcessBuilder servProc = new ProcessBuilder();
            servProc.command("C:\\Windows\\System32\\taskkill.exe /IM func.exe /F").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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
            Assertions.assertEquals(response.statusCode(), HttpStatus.OK.value());

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
    public void testHttpTriggerJava() throws Exception {
        // Setup
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Azure");
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
        Assertions.assertEquals(ret.getStatus(), HttpStatus.OK);

        com.google.gson.Gson parser = new com.google.gson.Gson();
        com.model.ScoreContainer test = parser.fromJson(ret.getBody().toString(), com.model.ScoreContainer.class);

        Assertions.assertEquals(test.getValue().get(0), expectedValue, 0.001);
    }
}