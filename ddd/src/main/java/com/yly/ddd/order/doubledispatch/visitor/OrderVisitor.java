package com.yly.ddd.order.doubledispatch.visitor;

import com.yly.ddd.order.doubledispatch.Order;
import com.yly.ddd.order.doubledispatch.SpecialOrder;

public interface OrderVisitor {
    void visit(Order order);

    void visit(SpecialOrder order);
}
