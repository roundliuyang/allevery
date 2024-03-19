package com.yly.springdatamongodb.repository;


import com.yly.springdatamongodb.dataobject.UserDO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDO, Integer> {
}
