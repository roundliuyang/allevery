package com.yly.server.oss.sdk.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.yly.server.oss.sdk.config.OssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * 阿里oss工具类
 *
 * @author xuzhipeng
 * @date 2021/2/3
 */
@Slf4j
@ConditionalOnProperty(prefix = OssProperties.OSS_CONFIG, name = "type", havingValue = "aliyun")
public class AliOssClientUtil implements OssClientUtil {

    private OssProperties ossProperties;

    public void setOssProperties(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    /**
     * 上传营业执照等敏感文件
     *
     * @param file
     * @return
     */
    @Override
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String url = null;
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        try (InputStream inputStream = file.getInputStream();) {
            String name = DigestUtils.md5DigestAsHex(file.getBytes()) + extension;
            url = upload(inputStream, name, ossProperties.getFileRootDir() + ossProperties.getLicenceDir());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;

    }

    @Override
    public String upload(MultipartFile file, String key) throws IOException {
        String url = null;
        try (InputStream inputStream = file.getInputStream();) {
            url = upload(inputStream, key, ossProperties.getFileRootDir() + ossProperties.getLicenceDir());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }

    /**
     * 上传并返回文件路径(比如/pmp/licence/a4949dfb500001ca5df769a62c4c28b6.jpg)
     *
     * @param instream
     * @param fileName
     * @param dir
     * @return
     */
    @Override
    public String upload(InputStream instream, String fileName, String dir) {
        try {
            OSS ossClient = getOssClient();
            PutObjectResult putResult = ossClient.putObject(ossProperties.getBucket(), dir + fileName,
                    instream);
            putResult.getETag();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dir + fileName;
    }

    @Override
    public String upload(String filePath) throws IOException {
        String url = null;
        File file = new File(filePath);
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        try (InputStream inputStream = new FileInputStream(file)) {
            String name = DigestUtils.md5DigestAsHex(toByteArray(file)) + extension;
            url = upload(inputStream, name, ossProperties.getFileRootDir() + ossProperties.getLicenceDir());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }

    @Override
    public String upload(String filePath, String key) throws IOException {
        String url = null;
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file)) {
            url = upload(inputStream, key, ossProperties.getFileRootDir() + ossProperties.getLicenceDir());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return url;
    }

    public String getOssUrl(String objectName) {
        // 设置URL过期时间为100年 3600L* 1000*24*365*100
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 100);
        return getOssUrl(objectName, expiration);
    }

    @Override
    public String getOssOutUrl(String objectName) {
        return getOssOutUrl(objectName, new Date(System.currentTimeMillis() + ossProperties.getExpiration() * 1000L));
    }

    /**
     * 获取外网可以访问的地址
     *
     * @param objectName
     * @return
     */
    @Override
    public String getOssOutUrl(String objectName, Date expiration) {
        String ossOutUrl = null;
        try {
            OSS ossClient = getOssClient(ossProperties.getEndpointOut());
            ossOutUrl = getOssUrl(ossClient, objectName, expiration);
        } catch (Exception e) {
            log.error("获取外网可以访问的地址的异常：{}", e);
        }
        return ossOutUrl;
    }

    private String getOssUrl(String objectName, Date expiration) {
        // 生成URL
        OSS ossClient = getOssClient();
        return getOssUrl(ossClient, objectName, expiration);
    }

    private String getOssUrl(OSS ossClient, String objectName, Date expiration) {
        URL url = ossClient.generatePresignedUrl(ossProperties.getBucket(), objectName, expiration);
        return url != null ? url.toString() : null;
    }

    private OSS getOssClient() {
        return getOssClient(ossProperties.getEndpoint());
    }

    private OSS getOssClient(String endpoint) {
        return new OSSClientBuilder().build(endpoint, ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

    private static volatile AliOssClientUtil aliOssClientUtil;

    private AliOssClientUtil() {
    }

    public static AliOssClientUtil getInstance() {
        if (aliOssClientUtil == null) {
            synchronized (AliOssClientUtil.class) {
                if (aliOssClientUtil == null) {
                    aliOssClientUtil = new AliOssClientUtil();
                }
            }
        }
        return aliOssClientUtil;
    }

    /**
     * 读取文件的字节数组
     *
     * @param file
     * @return
     * @throws IOException
     */
    private byte[] toByteArray(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("file not exists");
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length())) {
            BufferedInputStream in = null;
            in = new BufferedInputStream(new FileInputStream(file));
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;

        }
    }
}
