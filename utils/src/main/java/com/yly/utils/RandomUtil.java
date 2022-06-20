package com.yly.utils;

/**
 * 随机数工具类
 * @author xuzhipeng
 * @date 2021/01/31
 */
public final class RandomUtil {

    private RandomUtil(){}

    /**
     * 生成六位随机整数
     * @return
     */
    public static int generateRandomInt6(){
        return (int)((Math.random()*9+1)*100000);
    }
}
