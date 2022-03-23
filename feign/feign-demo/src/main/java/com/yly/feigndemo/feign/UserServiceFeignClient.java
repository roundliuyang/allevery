package com.yly.feigndemo.feign;


import com.yly.feigndemo.request.UserAddRequest;
import com.yly.feigndemo.response.UserResponse;
import feign.*;

import java.util.List;
import java.util.Map;

/**
 * 基于 Contract.Default 默认契约
 */
public interface UserServiceFeignClient {

    /**
     * @RequestLine 注解，添加在方法上，设置请求方法和请求地址，按照 请求方法 请求地址 格式，例如说 GET /user/get。同时，可以通过 {param} 表达式声明占位参数，搭配 @Param 注解一起使用。
     * @QueryMap 注解，请求 Query 参数集，无需像 @Param 在 @RequestLine 定义 {param} 表达式进行占位。
     * @Param 注解，添加在方法参数上，设置占位参数。不过要注意，@Param 是必传参数，因此传入 null 会报错。
     * 获得用户详情
     */
    @RequestLine("GET /user/get?id={id}")
    UserResponse get(@Param("id") Integer id);

    @RequestLine("GET /user/list?name={name}&gender={gender}")
    List<UserResponse> list(@Param("name") String name,
                            @Param("gender") Integer gender);

    @RequestLine("GET /user/list")
    List<UserResponse> list(@QueryMap Map<String, Object> queryMap);

    /**
     *@Headers 注解，添加在方法上，设置请求头。
     */
    @RequestLine("POST /user/add")
    @Headers("Content-Type: application/json")
    Integer add(UserAddRequest request);

}
