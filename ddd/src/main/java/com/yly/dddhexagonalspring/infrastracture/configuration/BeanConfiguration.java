package com.yly.dddhexagonalspring.infrastracture.configuration;


import com.yly.dddhexagonalspring.DomainLayerApplication;
import com.yly.dddhexagonalspring.domain.repository.OrderRepository;
import com.yly.dddhexagonalspring.domain.service.DomainOrderService;
import com.yly.dddhexagonalspring.domain.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = DomainLayerApplication.class)
public class BeanConfiguration {

    @Bean
    OrderService orderService(final OrderRepository orderRepository) {
        return new DomainOrderService(orderRepository);
    }
}
