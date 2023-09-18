package com.yly.join.service;


import com.github.yulichang.base.MPJBaseServiceImpl;
import com.yly.join.entity.UserDO;
import com.yly.join.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends MPJBaseServiceImpl<UserMapper, UserDO> implements UserService {
}

