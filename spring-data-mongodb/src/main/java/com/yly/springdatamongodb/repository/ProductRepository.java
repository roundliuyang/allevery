package com.yly.springdatamongodb.repository;


import com.yly.springdatamongodb.dataobject.ProductDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductDO, Integer> {
}
