package com.yly.ddd.order.doubledispatch.visitor;

public interface Visitable<V> {
    void accept(V visitor);
}
