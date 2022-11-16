package com.yly.dddhexagonalspring.infrastracture.configuration;


import com.yly.dddhexagonalspring.infrastracture.repository.mongo.SpringDataMongoOrderRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
public class MongoDBConfiguration {
}
