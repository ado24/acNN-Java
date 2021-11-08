package com.model;

import com.model.interfaces.IContainer;

import java.util.ArrayList;
import java.util.List;

public class Container implements IContainer {
    List<Double> value;
    Long count;

    public Container(Double value, Long count) {
        this();
        this.value.add(value);
        this.count = count;
    }

    public Container(List<Double> value) {
        this.value = value;
    }

    public Container() {
        this(new ArrayList<>());
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
        return String.format("{'value': %s,'count': %d}", value, count);
    }
}
