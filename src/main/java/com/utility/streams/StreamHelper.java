package com.utility.streams;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.model.ScoreContainer;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

public class StreamHelper {

    public String getName(HttpRequestMessage<Optional<String>> request, String key) {
        // Parse query parameter
        String query = request.getQueryParameters().get(key);
        return request.getBody().orElse(query);
    }

    public List<Double> getValue(HttpRequestMessage<Optional<String>> request, String key) {
        String query = request.getQueryParameters().get(key);
        String body = request.getBody().orElse(query);

        //body = getJsonFormatString(key, body);

        Gson parser = new Gson();
        //ScoreContainer test  = JsonParser.parseString(body).getAsJsonObject();
        ScoreContainer test = parser.fromJson(body, ScoreContainer.class);

        return new ArrayList<>(test.getValue());
    }

    public String getJsonFormatString(String key, String body) {
        if (!body.contains(key))
            body = String.format("{'%s' : %s}", key, body);
        return body;
    }

    public Double getWeight(HttpRequestMessage<Optional<String>> request, String key) {
        String query = request.getQueryParameters().get(key);
        String body = request.getBody().orElse(query);

        body = getJsonFormatString(key, body);

        Gson parser = new Gson();
        ScoreContainer test = parser.fromJson(body, ScoreContainer.class);

        if (test.getWeight() == null)
            test.setWeight(query.isEmpty() ? 0 : Double.parseDouble(query));

        return test.getWeight();
    }

    public Double getScore(Double weight, Double value) { return weight * value; }

    public Long getCount(List<Double> list) {
        return getStatistics(list).getCount();
    }

    public Double getAverage(List<Double> list) {
        return getStatistics(list).getAverage();
    }

    public DoubleSummaryStatistics getStatistics(List<Double> list) {
        return getDoubleStream(list)
                .summaryStatistics();
    }

    public DoubleStream getDoubleStream(List<Double> list) {
        return list.stream()
                .mapToDouble(Double::doubleValue);
    }
}