package com.yly.utils;

/**
 * 当前线程业务id工具类
 * @author xuzhipeng
 * @date 2021/2/9
 */
public final class BusinessUtil {
    //业务id
    private static ThreadLocal<Long> businessIdThreadLocal = new ThreadLocal<>();

    private BusinessUtil(){}



    public static void setBusinessId(Long businessId){
        businessIdThreadLocal.set(businessId);
    }

    public static Long getBusinessId(){
        return businessIdThreadLocal.get();
    }

    public static void removeBusinessId(){
        businessIdThreadLocal.remove();
    }
}
