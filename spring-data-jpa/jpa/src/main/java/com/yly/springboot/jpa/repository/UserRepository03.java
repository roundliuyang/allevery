package com.yly.springboot.jpa.repository;


import com.yly.springboot.jpa.dataobject.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;

public interface UserRepository03 extends PagingAndSortingRepository<UserDO, Integer> {

    UserDO findByUsername(String username);
    // 对于分页操作，需要使用到 Pageable 参数，需要作为方法的最后一个参数。
    Page<UserDO> findByCreateTimeAfter(Date createTime, Pageable pageable);

}
