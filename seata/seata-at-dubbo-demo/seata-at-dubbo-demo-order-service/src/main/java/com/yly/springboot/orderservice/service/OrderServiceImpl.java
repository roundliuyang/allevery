package com.yly.springboot.orderservice.service;


import com.yly.springboot.accountservice.api.AccountService;
import com.yly.springboot.orderservice.api.OrderService;
import com.yly.springboot.orderservice.dao.OrderDao;
import com.yly.springboot.orderservice.entity.OrderDO;
import com.yly.springboot.productservice.api.ProductService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@org.apache.dubbo.config.annotation.Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderDao orderDao;

    @Reference
    private AccountService accountService;
    @Reference
    private ProductService productService;

    @Override
    @GlobalTransactional
    public Integer createOrder(Long userId, Long productId, Integer price) throws Exception {
        Integer amount = 1; // 购买数量，暂时设置为 1。

        logger.info("[createOrder] 当前 XID: {}", RootContext.getXID());

        // 扣减库存
        productService.reduceStock(productId, amount);

        /**
         * AccountServiceImpl$$FastClassBySpringCGLIB$$1de0240d.invoke(<generated>) 是一个由Spring框架生成的CGLIB代理类的方法调用。
         * 这通常出现在Spring AOP（面向切面编程）和代理技术的上下文中，用于在方法调用前后执行一些特定的逻辑，如事务管理、安全性检查、性能监控等。
         */
        // 扣减余额
        accountService.reduceBalance(userId, price);

        // 保存订单
        OrderDO order = new OrderDO().setUserId(userId).setProductId(productId).setPayAmount(amount * price);
        orderDao.saveOrder(order);
        logger.info("[createOrder] 保存订单: {}", order.getId());

        // 返回订单编号
        return order.getId();
    }

}

