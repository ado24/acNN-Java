package com.function.getScore;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.model.ScoreContainer;
import com.utility.streams.StreamHelper;

import java.util.List;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetScore {
    private final StreamHelper streamHelper = new StreamHelper();

    /**
     * This function listens at endpoint "/api/getAvg". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/getScore
     * 2. curl {your host}/api/getScore?value=HTTP%20Query&weight=HTTP%20Query
     */
    @FunctionName("getScore")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.FUNCTION)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("'getScore' trigger processed a request.");

        List<Double> value = streamHelper.getValue(request, "value");

        //Get return values
        Double weight = streamHelper.getWeight(request, "weight");
        Double avg = streamHelper.getAverage(value);

        ScoreContainer retObj = new ScoreContainer(value, weight);
        Double score = streamHelper.getScore(retObj.getWeight(), retObj.getValue().get(0));
        retObj.setScore(score);
        String returnStr = String.format("{'value': %f, 'weight': %f }",
                avg,
                weight);

        if (value == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(retObj.getScore()).build();
            //return request.createResponseBuilder(HttpStatus.OK).body((new Gson()).toJson(retObj)).build();
        }
    }

}
