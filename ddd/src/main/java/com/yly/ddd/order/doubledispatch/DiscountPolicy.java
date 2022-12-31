package com.yly.ddd.order.doubledispatch;

public interface DiscountPolicy {
    double discount(Order order);
}
