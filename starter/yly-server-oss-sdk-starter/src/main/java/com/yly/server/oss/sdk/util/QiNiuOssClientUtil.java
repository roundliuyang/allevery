package com.yly.server.oss.sdk.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.yly.server.oss.sdk.config.OssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @author yangtao
 * @date : 2021-07-08 15:08
 */
@Slf4j
@ConditionalOnProperty(prefix = OssProperties.OSS_CONFIG, name = "type", havingValue = "qiniuyun")
public class QiNiuOssClientUtil implements OssClientUtil {

    private OssProperties ossProperties;

    public void setOssProperties(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        return upload(file, null);
    }

    @Override
    public String upload(String filePath) throws IOException {
        return upload(filePath, null);
    }

    @Override
    public String upload(String filePath, String key) throws IOException {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(RegionEnum.getRegion(ossProperties.getRegion()));
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = getUploadCredential();
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("QiNiuOssClientUtil upload putRet key:{}，hash:{}", putRet.key, putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("QiNiuOssClientUtil upload error r.toString:{}", r.toString());
            try {
                log.error("QiNiuOssClientUtil upload error r.bodyString:{}", r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    @Override
    public String upload(MultipartFile file, String key) throws IOException {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(RegionEnum.getRegion(ossProperties.getRegion()));
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = getUploadCredential();
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Response response = uploadManager.put(file.getBytes(), key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("QiNiuOssClientUtil upload putRet key:{}，hash:{}", putRet.key, putRet.hash);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("QiNiuOssClientUtil upload error r.toString:{}", r.toString());
            try {
                log.error("QiNiuOssClientUtil upload error r.bodyString:{}", r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    @Override
    public String getOssOutUrl(String objectName) {
        return getOssOutUrl(objectName, new Date(System.currentTimeMillis() + ossProperties.getExpiration() * 1000L));
    }

    @Override
    public String getOssOutUrl(String objectName, Date expiration) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(objectName, "utf-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String publicUrl = String.format("%s/%s", ossProperties.getEndpoint(), encodedFileName);
        Auth auth = Auth.create(ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        long expireInSeconds = (expiration.getTime() - System.currentTimeMillis()) / 1000L;
        log.info("QiNiuOssClientUtil getOssOutUrl expireInSeconds：{}", expireInSeconds);
        return auth.privateDownloadUrl(publicUrl, expireInSeconds);
    }

    @Override
    public String upload(InputStream inputStream, String fileName, String dir) {
        Configuration cfg = new Configuration(RegionEnum.getRegion(ossProperties.getRegion()));
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = getUploadCredential();
        try {
            Response response = uploadManager.put(inputStream, fileName, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("QiNiuOssClientUtil upload putRet key:{}，hash:{}", putRet.key, putRet.hash);
            return ossProperties.getEndpoint() + putRet.hash;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("QiNiuOssClientUtil upload error r.toString:{}", r.toString());
            try {
                log.error("QiNiuOssClientUtil upload error r.bodyString:{}", r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    /**
     * 获取上传凭证
     */
    private String getUploadCredential() {
        Auth auth = Auth.create(ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
        String upToken = auth.uploadToken(ossProperties.getBucket());
        log.info("QiNiuOssClientUtil getUploadCredential upToken:{}", upToken);
        return upToken;
    }

    private static volatile QiNiuOssClientUtil qiNiuOssClientUtil;

    private QiNiuOssClientUtil() {
    }

    public static QiNiuOssClientUtil getInstance() {
        if (qiNiuOssClientUtil == null) {
            synchronized (QiNiuOssClientUtil.class) {
                if (qiNiuOssClientUtil == null) {
                    qiNiuOssClientUtil = new QiNiuOssClientUtil();
                }
            }
        }
        return qiNiuOssClientUtil;
    }
}
