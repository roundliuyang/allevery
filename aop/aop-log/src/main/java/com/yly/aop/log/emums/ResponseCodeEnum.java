package com.yly.aop.log.emums;

/**
 * 响应码枚举类
 */
public enum ResponseCodeEnum {
    SUCCESS(200, "SUCCESS"),
    SYSTEM_ERROR(1000, "系统异常"),
    EMPTY_POST(1001, "POST参数为空"),
    SIGN_ERROR(1002, "签名异常"),
    PARAM_ERROR(1003, "参数异常"),
    LOGIN_ERROR(1004, "账号不存在或密码有误！"),
    //-------------------业务异常
    REMIT_ERROR(2001, "打款异常"),
    REMIT_ERROR_PAYPANEL(2017, "收款账号未添加白名单！"),
    REMIT_SIZE_ERROR(2014, "打款条数异常"),
    PULL_BANK_FLOW_ERROR(2002, "拉取当日流水异常"),
    PULL_HIS_BANK_FLOW_ERROR(2003, "拉取历史流水异常"),
    PULL_BACK_ORDER_ERROR(2004, "拉取当日回单信息异常"),
    PULL_HIS_BACK_ORDER_ERROR(2005, "拉取历史回单信息异常"),
    PULL_BACK_ORDER_IMG_ERROR(2006, "拉取回单图片异常"),
    NULL_RESULT_ERROR(2007, "CBS返回值为空"),
    RESULT_ERROR(2008,"定时查询打款状态接口异常"),
    OUT_TIME_ERROR(2009,"查询流水时间范围不能超过一天!"),

    CMB_NULL_RESULT_ERROR(2010, "CMB返回值为空"),
    CMB_PULL_BACK_ORDER_ERROR(2011, "CMB拉取当日回单异常"),
    CMB_PULL_HIS_BACK_ORDER_ERROR(2012, "CMB拉取历史回单异常"),
    CMB_PULL_BACK_ORDER_IMG_ERROR(2013, "CMB拉取历史回单异常"),
    EXCEL_IS_NULL(2016, "excel文件不能为空"),
    SPDB_PULL_BACK_ORDER_ERROR(2101, "SPDB拉取当日回单异常"),
    SPDB_PULL_BACK_ORDER_FILE_ERROR(2101, "SPDB下载回单文件失败"),
    QUERY_PAY_ERROR(2015, "查询打款信息出错"),
    COMMUNICATION_ERROR(9999,"未知异常");



    private int code;
    private String desc;

    ResponseCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
