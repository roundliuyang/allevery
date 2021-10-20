package com.yly.jackson.objectmapper.dto;

import java.util.Date;


// 让我们在Request类中用datePurchased属性来包装我们到目前为止使用的Car实例
public class Request {
    Car car;
    Date datePurchased;

    public Car getCar() {
        return car;
    }

    public void setCar(final Car car) {
        this.car = car;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(final Date datePurchased) {
        this.datePurchased = datePurchased;
    }
}