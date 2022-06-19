package com.yly.springboot.jpa.repository;


import com.yly.springboot.jpa.dataobject.UserDO;
import org.springframework.data.repository.CrudRepository;

// 第一个泛型设置对应的实体是 UserDO ，第二个泛型设置对应的主键类型是 Integer 。
public interface UserRepository01 extends CrudRepository<UserDO, Integer> {

}
