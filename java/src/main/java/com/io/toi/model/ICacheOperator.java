package com.io.toi.model;

import java.util.Map;

@FunctionalInterface
public interface ICacheOperator {

    void operate(final Map<Integer, ToiParameter<?>> valueCache);

}
