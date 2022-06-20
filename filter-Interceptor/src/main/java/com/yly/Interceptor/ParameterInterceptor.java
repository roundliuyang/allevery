package com.yly.Interceptor;



import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 拦截器 校验签名
 *
 * @author xuzhipeng
 * @date 2021/01/31
 */
@Component
public class ParameterInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ParameterInterceptor.class);

    public static final String VERIFY_FAIL_MSG = "The request parameter signature verification failed!";

//    @Resource
//    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //从header中获取签名
      /*  String sign = request.getHeader(Constants.SIGNATURE);
        // 从url中获取签名(业务方js调用中台下载文件接口在请求头加签名和解决跨域比较麻烦，所以将签名放在url中)
        if(StringUtils.isBlank(sign)){
            sign = request.getParameter("sign");
        }
        if (StringUtils.isBlank(sign)) {
            log.error("签名为空");
            signErrorResponse(response);
            return false;
        }
        String[] signArray = sign.split(Constants.SYMBOL_UNDERLINE);
        if (signArray.length != 4) {
            log.error("签名长度不符合，[{}]", sign);
            signErrorResponse(response);
            return false;
        }
        String appId = signArray[0];
        Object secretValue = redisUtil.get(RedisKeyUtil.getKey(BANK_AGENCY_PLATFORM, Constants.SECRET, APPID, appId));
        if (secretValue == null) {
            log.error("无法根据签名中的appId从redis中获取secret，[{}]", sign);
            signErrorResponse(response);
            return false;
        }
        Object businessId = redisUtil.get(RedisKeyUtil.getKey(BANK_AGENCY_PLATFORM, Constants.BUSINESSID, APPID, appId.toString()));
        if (businessId == null) {
            log.error("无法根据签名中的appId从redis中获取businessId，[{}]", sign);
            signErrorResponse(response);
            return false;
        }
        //设置当前线程businessId
        BusinessUtil.setBusinessId(Long.parseLong(String.valueOf(businessId)));
        String secret = secretValue.toString();
        if (HttpMethod.GET.name().equals(request.getMethod())) {
//            String queryString = request.getQueryString();
//            if (null != queryString) {
//                queryString = URLDecoder.decode(queryString, Constants.CHARSET_UTF8);
//            }
//            log.info("[{}]请求参数params:[{}],签名sign:[{}]", request.getMethod(), queryString, sign);
//            boolean right = SignatureUtil.check(secret, queryString, sign);
            Enumeration<String> parameterNames = request.getParameterNames();
            Map<String, String> requestParamMap = new HashMap<>();
            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                requestParamMap.put(key, request.getParameter(key));
            }
            // 过滤空字段
            Map<String, String> params = new HashMap<>();
            for (String key : requestParamMap.keySet()) {
                if (null != requestParamMap.get(key) && !"".equals(requestParamMap.get(key))) {
                    params.put(key, requestParamMap.get(key));
                }
            }
            log.info("get传入参数requestParamString:[{}] 去空后:[{}]",
                    JSONObject.toJSONString(requestParamMap),
                    JSONObject.toJSONString(params));
            boolean right = SignatureUtil.check(secret, params, sign);
            if (right) {
                return true;
            }
            signErrorResponse(response);
            return false;
        }
        if (HttpMethod.POST.name().equals(request.getMethod())) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            String requestParamString = IOUtils.read(reader);
//            Map<String, Object> requestParamMap = JSONObject.parseObject(requestParamString, Map.class);
//            log.info("[{}]请求参数params:[{}],签名sign:[{}]", request.getMethod(), requestParamMap, sign);
//            boolean right = SignatureUtil.check(secret, requestParamString, sign);
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String requestParamString = IOUtils.read(reader);
            log.info("post传入参数requestParamString:[{}] sign:[{}]", requestParamString, sign);
            // 参数防止乱序
            JSONObject requestParamJSONObject = JSONObject.parseObject(requestParamString, Feature.OrderedField);
            Map<String, String> requestParamMap = JSONObject.toJavaObject(requestParamJSONObject, Map.class);
            // 过滤空字段
            Map<String, String> params = new HashMap<>();
            for (String key : requestParamMap.keySet()) {
                if (null != requestParamMap.get(key) && !"".equals(requestParamMap.get(key))) {
                    params.put(key, requestParamMap.get(key));
                }
            }
            log.info("预签名参数：" + params);
            boolean right = SignatureUtil.check(secret, params, sign);
            if (right) {
                return true;
            }
            signErrorResponse(response);
            return false;
        }*/
//        BusinessUtil.setBusinessId(6798785148691849255L);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {
        return;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
//        BusinessUtil.removeBusinessId();
        return;
    }

    private void signErrorResponse(HttpServletResponse response) {
        log.warn(VERIFY_FAIL_MSG);
        response.setStatus(HttpStatus.SC_FORBIDDEN);
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
//            writer.write(JSONObject.toJSONString(ResponseResult.error(ResponseCodeEnum.SIGN_ERROR.getCode(), ResponseCodeEnum.SIGN_ERROR.getDesc())));
            writer.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
