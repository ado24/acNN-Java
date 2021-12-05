package com.function.getAvg;

import java.util.*;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import com.google.gson.Gson;
import com.model.Container;
import com.utility.streams.StreamHelper;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetAvg {
    private final StreamHelper streamHelper = new StreamHelper();

    /**
     * This function listens at endpoint "/api/getAvg". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/getAvg
     * 2. curl {your host}/api/getAvg?name=HTTP%20Query
     */
    @FunctionName("getAvg")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.FUNCTION)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        List<Double> value;

        context.getLogger().info("'getAvg' trigger processed a request.");

        try {
            String name = streamHelper.getName(request, "name");
            value = streamHelper.getValue(request, "value");
        } catch (Exception e) {
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a value (in square bracket notation) on the query string or in the request body").build();
        }

        //Get return values
        Long count = streamHelper.getCount(value); // value.size();
        Double avg = streamHelper.getAverage(value);

        Container retObj = new Container(avg, count);

        String returnStr = String.format("{'value': %f, 'count': %d }",
                avg,
                count);

        if (value == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a value (in square bracket notation) on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body((new Gson()).toJson(retObj)).build();
        }
    }

}
