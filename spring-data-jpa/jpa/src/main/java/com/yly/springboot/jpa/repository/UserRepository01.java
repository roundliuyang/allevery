package com.yly.springboot.jpa.repository;


import com.yly.springboot.jpa.dataobject.UserDO;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository01 extends CrudRepository<UserDO, Integer> {

}
