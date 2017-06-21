package com.io.toui.model;

import java.util.Map;

@FunctionalInterface
public interface ICacheOperator {

    void operate(final Map<Integer, ToiParameter<?>> valueCache);

}
