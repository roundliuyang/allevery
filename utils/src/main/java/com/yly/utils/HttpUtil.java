package com.yly.utils;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * TODO
 *
 * @author jijiahe
 * @version 1.0
 * @date 2020/12/2 11:12
 */
public class HttpUtil {

    /**
     *
     *get请求
     * @param url
     * @param headers
     * @param param
     * @author jijiahe
     * @date 2020/7/15 12:10
     * @return java.lang.String
     */
    public static String getStringObject(String url, HttpHeaders headers, String param){
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(url,String.class,entity);
        return result.getBody();
    }
    /**
     *
     *post请求
     * @param url
     * @param headers
     * @param param
     * @author jijiahe
     * @date 2020/7/15 12:10
     * @return java.lang.String
     */
    public static String postString(String url, HttpHeaders headers, String param,String charset){
        //json格式的参数
        HttpEntity<String> requestEntity = new HttpEntity<String>(param,headers);
        RestTemplate restTemplate = new RestTemplate();
        if (null!=charset){
            restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName(charset)));
        }
        ResponseEntity<String> responseEntity =restTemplate.postForEntity(url,requestEntity, String.class);
        return responseEntity.getBody();
    }


    public static String postMap(String url, Map<String, String> headsMap, Map<String, String> contentMap) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> content = new ArrayList<NameValuePair>();
        Iterator iterator = contentMap.entrySet().iterator();           //将content生成entity
        while(iterator.hasNext()){
            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
            content.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
        }
        CloseableHttpResponse response = null;
        try {
           /* Iterator headerIterator = headerMap.entrySet().iterator();          //循环增加header
            while(headerIterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) headerIterator.next();
                post.addHeader(elem.getKey(),elem.getValue());
            }*/
            if (headsMap != null && !headsMap.isEmpty()) {
                headsMap.forEach((key, value) -> {
                    post.addHeader(key, value);
                });
            }
            if(content.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(content,"UTF-8");
                post.setEntity(entity);
            }
            response = httpClient.execute(post);            //发送请求并接收返回数据
            if(response != null && response.getStatusLine().getStatusCode() == 200)
            {
                org.apache.http.HttpEntity entity = response.getEntity();       //获取response的body部分
                result = EntityUtils.toString(entity);          //读取reponse的body部分并转化成字符串
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
                if(response != null)
                {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        return null;
    }
    /**get请求(用于key-value格式的参数,且带有header请求头)
     * @param url
     * @param map            请求的参数map<key,value>键值对
     * @param headerKey        headers Key
     * @param headerValue    headers Value
     * @return
     */
    public static String doGet(String url,Map<String, String> map,String headerKey,String headerValue) {
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            /*
             * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /** 第一种添加参数的形式 */
            /*uriBuilder.addParameter("name", "root");
            uriBuilder.addParameter("password", "123456");*/

            //String mapString = JSON.toJSONString(map);
            if(map!=null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
            }


            /** 第二种添加参数的形式 */
            /* List<NameValuePair> list = new LinkedList<>();
            BasicNameValuePair param1 = new BasicNameValuePair("startTime", "2019-1-1 00:00:00");
            BasicNameValuePair param2 = new BasicNameValuePair("endTime", "2019-1-9 00:00:00");
            list.add(param1);
            list.add(param2);
            uriBuilder.setParameters(list);*/

            // 根据带参数的URI对象构建GET请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            /*
             * 添加请求头信息
             */
            // 浏览器表示
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.6)");
            // 传输的类型
            httpGet.addHeader("Content-Type", "application/json");
            //header的<key,value>
            httpGet.setHeader(headerKey, headerValue);

            // 执行请求
            response = httpClient.execute(httpGet);
            // 获得响应的实体对象
            org.apache.http.HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("解析错误");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URI解析异常");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        return entityStr;
    }
}
