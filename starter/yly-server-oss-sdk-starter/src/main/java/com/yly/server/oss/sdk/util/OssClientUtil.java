package com.yly.server.oss.sdk.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author by yangTao
 * @Classname OssClientUtil
 * @Description TODO
 * @Date 2021/7/8 10:27
 */
public interface OssClientUtil {
    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param key  默认不指定key的情况下，以文件内容的hash值作为文件名
     * @return
     * @throws IOException
     */
    String upload(MultipartFile file, String key) throws IOException;

    /**
     * 上传并返回文件路径(比如/pmp/licence/a4949dfb500001ca5df769a62c4c28b6.jpg)
     *
     * @param inputStream
     * @param fileName
     * @param dir
     * @return
     */
    String upload(InputStream inputStream, String fileName, String dir);

    /**
     * 上传文件
     *
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    String upload(String filePath) throws IOException;

    /**
     * 上传文件
     *
     * @param filePath 文件路径
     * @param key      默认不指定key的情况下，以文件内容的hash值作为文件名
     * @return
     * @throws IOException
     */
    String upload(String filePath, String key) throws IOException;

    /**
     * 获取外网可以访问的地址
     *
     * @param objectName 服务器路径
     * @return
     */
    String getOssOutUrl(String objectName);

    /**
     * 获取外网可以访问的地址
     *
     * @param objectName 服务器路径
     * @param expiration 失效期
     * @return
     */
    String getOssOutUrl(String objectName, Date expiration);

}