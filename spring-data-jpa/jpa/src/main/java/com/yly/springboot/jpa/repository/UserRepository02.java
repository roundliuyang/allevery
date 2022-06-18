package com.yly.springboot.jpa.repository;


import com.yly.springboot.jpa.dataobject.UserDO;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository02 extends PagingAndSortingRepository<UserDO, Integer> {

}
