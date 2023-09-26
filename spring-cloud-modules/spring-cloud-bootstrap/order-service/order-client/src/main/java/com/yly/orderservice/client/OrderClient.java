package com.yly.orderservice.client;

public interface OrderClient {

    OrderResponse order(OrderDTO orderDTO);
}
