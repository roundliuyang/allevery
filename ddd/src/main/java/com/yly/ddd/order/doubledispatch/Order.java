package com.yly.ddd.order.doubledispatch;


import com.yly.ddd.order.OrderLine;
import com.yly.ddd.order.doubledispatch.visitor.OrderVisitor;
import com.yly.ddd.order.doubledispatch.visitor.Visitable;
import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.List;

public class Order extends com.yly.ddd.order.Order implements Visitable<OrderVisitor> {
    public Order(List<OrderLine> orderLines) {
        super(orderLines);
    }

    public Money totalCost(SpecialDiscountPolicy discountPolicy) {
        return totalCost().multipliedBy(1 - applyDiscountPolicy(discountPolicy), RoundingMode.HALF_UP);
    }

    protected double applyDiscountPolicy(SpecialDiscountPolicy discountPolicy) {
        return discountPolicy.discount(this);
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }
}
