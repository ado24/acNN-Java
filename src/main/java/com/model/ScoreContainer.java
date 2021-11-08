package com.model;

import com.microsoft.azure.functions.HttpRequestMessage;
import com.model.interfaces.IContainer;
import com.model.interfaces.IScore;
import com.model.interfaces.IWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScoreContainer implements IContainer, IWeight, IScore {
    Double weight;
    Double score;
    List<Double> value;
    Long count;

    public ScoreContainer(Double value, Long count, Double weight) {
        this(value, count);
        this.weight = weight;
    }

    public ScoreContainer(List<Double> value, Double weight) {
        this(value);
        this.weight = weight;
    }

    public ScoreContainer(Double value, Long count) {
        this();
        this.value.add(value);
        this.count = count;
    }

    public ScoreContainer(List<Double> value) {
        this.value = value;
    }

    public ScoreContainer(Double value) {
        this();
        this.getValue().add(value);
    }

    public ScoreContainer(String value) {
        this(Double.valueOf(value));
    }

    public ScoreContainer() {
        this(new ArrayList<>());
    }

    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public Double getScore() {
        return score;
    }

    @Override
    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public List<Double> getValue() {
        return value;
    }

    @Override
    public void setValue(List<Double> value) {
        this.value = value;
    }

    @Override
    public Long getCount() {
        return count;
    }

    @Override
    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("{'value': %s,'weight': %f}", value, weight);
    }

    public List<Double> getValue(HttpRequestMessage<Optional<String>> request, String key) {
        String query = request.getQueryParameters().get(key);
        String body = request.getBody().orElse(query);

        com.google.gson.Gson parser = new com.google.gson.Gson();
        ScoreContainer test = parser.fromJson(body, ScoreContainer.class);

        return new ArrayList<>(test.getValue());
    }

}
