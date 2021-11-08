package com.model.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IContainer extends Serializable {
    List<Double> getValue();

    void setValue(List<Double> value);

    Long getCount();

    void setCount(Long count);
}
