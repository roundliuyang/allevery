package com.yly.dddhexagonalspring.domain.service;



import com.yly.dddhexagonalspring.domain.Order;
import com.yly.dddhexagonalspring.domain.Product;
import com.yly.dddhexagonalspring.domain.repository.OrderRepository;

import java.util.UUID;

/**
 * define a Domain Service, which usually contains logic that can't be a part of our root
 */
public class DomainOrderService implements OrderService {

    private final OrderRepository orderRepository;

    public DomainOrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public UUID createOrder(final Product product) {
        final Order order = new Order(UUID.randomUUID(), product);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public void addProduct(final UUID id, final Product product) {
        final Order order = getOrder(id);
        order.addOrder(product);

        orderRepository.save(order);
    }

    @Override
    public void completeOrder(final UUID id) {
        final Order order = getOrder(id);
        order.complete();

        orderRepository.save(order);
    }

    @Override
    public void deleteProduct(final UUID id, final UUID productId) {
        final Order order = getOrder(id);
        order.removeOrder(productId);

        orderRepository.save(order);
    }

    private Order getOrder(UUID id) {
        return orderRepository
          .findById(id)
          .orElseThrow(() -> new RuntimeException("Order with given id doesn't exist"));
    }
}
