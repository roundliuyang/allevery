package com.yly.ddd.order.mongo;


import com.yly.ddd.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderMongoRepository extends MongoRepository<Order, String> {

}
