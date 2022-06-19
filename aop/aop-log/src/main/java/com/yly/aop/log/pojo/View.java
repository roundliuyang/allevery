package com.yly.aop.log.pojo;

/**
 * @JsonView 视图
 * 可以过滤pojo的属性，使Controller在返回json时候，pojo某些属性不返回
 *
 */
public class View {

    /**
     * BasicView 其他视图必须继承BasicView
     */
    public interface BasicView {}
    public interface SimpleView extends BasicView {}

    // bank info
    public interface BankBaseInfoView extends BasicView {}
    public interface BankBranchBaseInfoView extends BasicView {}

}
