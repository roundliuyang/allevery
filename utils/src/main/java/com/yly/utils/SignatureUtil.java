package com.yly.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 请求签名类
 *
 * MD5的生成方式: 请求参数中去掉值为null的参数, 加上appId加上毫秒级时间戳, 加上六位随机数,
 * 将参数key排序后，用key=value&key=value...的方式拼接，最后再拼上&secret=秘钥，最后根据拼接的内容生成MD5
 *
 * @author xuzhipeng
 * @date 2021/01/31
 */
public class SignatureUtil {

    private static final Logger log = LoggerFactory.getLogger(SignatureUtil.class);

    //签名格式(appId_MD5_毫秒级时间戳_随机数)
    private static final String SIGN_FORMAT = "%s_%s_%s_%s";
    //md5格式(请求参数字符串_appId_secret_时间戳_随机数)
    private static final String MD5_FORMAT = "%s_%s_%s_%s_%s";

//    /**
//     * 生成签名
//     * @param secret
//     * @param paramsString
//     * @param appId
//     * @return
//     */
//    public static String generate(String secret, String paramsString, String appId){
//        String timestamp = String.valueOf(System.currentTimeMillis());
//        String randomNum = String.valueOf(RandomUtil.generateRandomInt6());
//        return getSign(appId,secret,timestamp,randomNum,paramsString);
//    }

    public static String generate(String secret, Map<String, String> params, String appId){
        String timestamp = String.valueOf(System.currentTimeMillis());
//        String randomNum = String.valueOf(RandomUtil.generateRandomInt6());
//        String md5Content = generateMD5Content(params,secret,timestamp,randomNum);
//        return getSign(appId,md5Content,timestamp,randomNum);
        return null;
    }

    private static String generateMD5Content(Map<String, String> params, String secret, String timestamp, String randomNum) {
        params.put("api_random", randomNum);
        params.put("api_time", timestamp);
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(params.get(key));
            if (value == null || "".equals(value) || "sign".equals(key)) {
                //签名字段和空值字段，过滤掉
                continue;
            }

            if (i == keys.size() - 1) {
                //拼接时，不包括最后一个&字符
                sb.append(key).append("=").append(value);
            } else {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        sb.append("&secret=").append(secret);

        return sb.toString();
    }

//    /**
//     * 签名校验
//     * @param secret
//     * @param paramsString
//     * @param sign
//     * @return
//     */
//    public static boolean check(String secret, String paramsString, String sign){
//        String[] signArray = sign.split(Constants.SYMBOL_UNDERLINE);
//        String appId = signArray[0];
//        String timestamp = signArray[2];
//        String randomNum = signArray[3];
//        String newSign = getSign(appId,secret,timestamp,randomNum,paramsString);
//        return sign.equals(newSign);
//    }

    public static boolean check(String secret, Map<String, String> params, String sign){
//        String[] signArray = sign.split(Constants.SYMBOL_UNDERLINE);
        String[] signArray = sign.split("Constants.SYMBOL_UNDERLINE");
        String appId = signArray[0];
        String timestamp = signArray[2];
        String randomNum = signArray[3];
        String md5Content = generateMD5Content(params,secret,timestamp,randomNum);
        log.info("预签名字符串：" + md5Content);
        String newSign = getSign(appId,md5Content,timestamp,randomNum);
        log.info("参数签名：{}，生成签名：{}。", sign, newSign);
        return sign.equals(newSign);
    }

    /**
     * 拼接生成签名中MD5的原始内容
     * @param appId
     * @param secret
     * @param timestamp
     * @param randomNum
     * @param paramsString
     * @return
     */
    private static String getMD5OriginalContent(String appId, String secret, String timestamp, String randomNum, String paramsString){
        return String.format(MD5_FORMAT, paramsString == null ? StringUtils.EMPTY : paramsString, appId, secret, timestamp, randomNum);
    }

//    /**
//     * 获取签名
//     * @param appId
//     * @param secret
//     * @param timestamp
//     * @param randomNum
//     * @param paramsString
//     * @return
//     */
//    private static String getSign(String appId, String secret, String timestamp, String randomNum, String paramsString){
//        String md5Content = getMD5OriginalContent(appId,secret,timestamp,randomNum,paramsString);
//        return String.format(SIGN_FORMAT,appId,DigestUtils.md5DigestAsHex(md5Content.getBytes()),timestamp,randomNum);
//    }

    private static String getSign(String appId, String md5Content, String timestamp, String randomNum){
        return String.format(SIGN_FORMAT,appId,DigestUtils.md5DigestAsHex(md5Content.getBytes()),timestamp,randomNum);
    }
}