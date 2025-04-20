package com.yly.shardingdatasource.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yly.shardingdatasource.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

}
