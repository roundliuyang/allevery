package com.yly.alioss.demo.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by bluexiii on 24/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OssComponentTest {
    private Logger logger = LoggerFactory.getLogger(OssComponentTest.class);
    private String localPath;
    @Autowired
    OssComponent ossComponent;

    public OssComponentTest() {
        localPath = System.getProperty("user.dir") + "/" ;
    }

    @Test
    public void test1() throws Exception {
        System.out.println(  System.getProperty("user.dir")+"---------------");
    }


    @Test
    public void urlFromFileKey() throws Exception {
        String bucket = "liuyang-oss";
        String fileKey = "sonic.jpg";
        String url = ossComponent.urlFromFileKey(bucket, fileKey);

        logger.debug(url+"-----------------------------");
        System.out.println(url+"-----------------------------");
        assertNotNull(url);
    }

    @Test
    public void putObject() throws Exception {
        String bucket = "liuyang-oss";
        String filePath = localPath + "src/main/resources/xpic935.jpg";
        System.out.println(filePath+"----------------------------------------");

        String fileKey = ossComponent.putObject(bucket, filePath, null);
        System.out.println(fileKey+"--------------------------");
        logger.debug(fileKey);
        assertNotNull(fileKey);

        fileKey = ossComponent.putObject(bucket, filePath, "test/sonic.jpg");
        System.out.println(fileKey+"--------------------------");
        logger.debug(fileKey);
        assertNotNull(fileKey);
    }

    @Test
    public void getObject() throws Exception {
        String bucket = "liuyang-oss";
        String fileKey = "sonic.jpg";
        String localPath = this.localPath;

        String localFileKey = ossComponent.getObject(bucket, fileKey, localPath);

        logger.debug(localFileKey);
        System.out.println();
        assertNotNull(localFileKey);
    }

    @Test
    public void checkObject() throws Exception {
        String bucket = "liuyang-oss";
        String fileKey = "sonic.jpg";

        boolean flag = ossComponent.checkObject(bucket, fileKey);

        assertTrue(flag);
    }

    @Test
    public void listObjects() throws Exception {
        String bucket = "liuyang-oss";
        String keyPrifix = "";

        List<String> list = ossComponent.listObjects(bucket, keyPrifix);
        System.out.println(list.toString()+"-----------------");
        logger.debug(list.toString());
        assertTrue(list.size() > 0);
    }

    @Test
    public void deleteObject() throws Exception {
        String bucket = "liuyang-oss";
        String fileKey = "sonic.jpg";

        ossComponent.deleteObject(bucket, fileKey);
    }
}