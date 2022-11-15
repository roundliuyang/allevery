package com.yly.ddd.order.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<JpaOrder, Long> {

}
