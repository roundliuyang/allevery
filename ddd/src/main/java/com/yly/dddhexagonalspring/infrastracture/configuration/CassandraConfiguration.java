package com.yly.dddhexagonalspring.infrastracture.configuration;


import com.yly.dddhexagonalspring.infrastracture.repository.cassandra.SpringDataCassandraOrderRepository;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories(basePackageClasses = SpringDataCassandraOrderRepository.class)
public class CassandraConfiguration {

}
