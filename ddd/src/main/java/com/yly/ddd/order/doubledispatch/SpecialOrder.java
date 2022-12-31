package com.yly.ddd.order.doubledispatch;

import com.yly.ddd.order.OrderLine;
import com.yly.ddd.order.doubledispatch.visitor.OrderVisitor;

import java.util.List;

public class SpecialOrder extends Order {

    private boolean eligibleForExtraDiscount;

    public SpecialOrder(List<OrderLine> orderLines) {
        super(orderLines);
        this.eligibleForExtraDiscount = false;
    }

    public SpecialOrder(List<OrderLine> orderLines, boolean eligibleForSpecialDiscount) {
        super(orderLines);
        this.eligibleForExtraDiscount = eligibleForSpecialDiscount;
    }

    public boolean isEligibleForExtraDiscount() {
        return eligibleForExtraDiscount;
    }

    @Override
    protected double applyDiscountPolicy(SpecialDiscountPolicy discountPolicy) {
        return discountPolicy.discount(this);
    }

    @Override
    public void accept(OrderVisitor visitor) {
        visitor.visit(this);
    }

}
