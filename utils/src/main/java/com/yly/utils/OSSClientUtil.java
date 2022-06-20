package com.yly.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;

import com.yly.config.OSSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里oss工具类
 * @author xuzhipeng
 * @date 2021/2/3
 */

@Component
public class OSSClientUtil {

    protected static final Logger log = LoggerFactory.getLogger(OSSClientUtil.class);

    @Resource
    private OSSProperties ossProperties;



    public String upload(MultipartFile file, String prefix) {
        String originalFilename = file.getOriginalFilename();
        String url = null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        try (InputStream inputStream =  file.getInputStream();){
            String name = DigestUtils.md5DigestAsHex(file.getBytes()) + extension;
            url = upload(inputStream, name, ossProperties.getFileRootDir() + prefix);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }

    /**
     * 上传并返回文件路径(比如/pmp/licence/a4949dfb500001ca5df769a62c4c28b6.jpg)
     * @param instream
     * @param fileName
     * @param dir
     * @return
     */
    public String upload(InputStream instream, String fileName, String dir) {
        OSS ossClient = getOSSClient();
        try {
            PutObjectResult putResult = ossClient.putObject(ossProperties.getBucket(), dir + fileName,
                    instream);
            putResult.getETag();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }finally {
            ossClient.shutdown();
        }
        return dir + fileName;
    }

    public String getOssUrl(String objectName) {
        // 设置URL过期时间为100年 3600l* 1000*24*365*100
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 100);
        return getOssUrl(objectName,expiration);
    }



    public String getOssUrl(String objectName, Date expiration) {
        // 生成URL
        OSS ossClient = getOSSClient();
        return getOssUrl(ossClient,objectName,expiration);
    }

    public String getOssUrl(OSS ossClient, String objectName, Date expiration) {
        URL url = ossClient.generatePresignedUrl(ossProperties.getBucket(), objectName, expiration);
        ossClient.shutdown();
        return url != null ? url.toString() : null;
    }

    private OSS getOSSClient() {
        return getOSSClient(ossProperties.getEndpoint());
    }

    private OSS getOSSClient(String endpoint) {
        return new OSSClientBuilder().build(endpoint, ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

}
