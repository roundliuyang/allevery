package com.yly.dddhexagonalspring.domain.repository;



import com.yly.dddhexagonalspring.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> findById(UUID id);

    void save(Order order);
}
