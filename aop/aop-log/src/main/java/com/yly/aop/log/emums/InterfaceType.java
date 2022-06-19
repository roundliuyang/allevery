package com.yly.aop.log.emums;

import lombok.Getter;

/**
 */
@Getter
public enum InterfaceType {
    REMIT("打款"),
    REMIT_STATR("查询打款是否成功"),
    PULL_BANK_FLOW("拉取当日流水"),
    PULL_HIS_BANK_FLOW("拉取历史流水"),
    PULL_BACK_ORDER("拉取当日回单"),
    PULL_BACK_ORDER_IMG("拉取回单图片"),
    PULL_HIS_BACK_ORDER("拉取历史回单");


    private String type;

    InterfaceType(String type) {
        this.type = type;
    }
}
