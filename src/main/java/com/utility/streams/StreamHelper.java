package com.utility.streams;

import com.microsoft.azure.functions.HttpRequestMessage;

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

        com.google.gson.Gson parser = new com.google.gson.Gson();
        com.model.ScoreContainer test = parser.fromJson(body, com.model.ScoreContainer.class);

        return new ArrayList<>(test.getValue());
    }

    public Double getWeight(HttpRequestMessage<Optional<String>> request, String key) {
        String query = request.getQueryParameters().get(key);
        String body = request.getBody().orElse(query);

        com.google.gson.Gson parser = new com.google.gson.Gson();
        com.model.ScoreContainer test = parser.fromJson(body, com.model.ScoreContainer.class);

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