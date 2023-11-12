package com.yly.server.oss.sdk.app;

import com.yly.server.oss.sdk.util.OssClientUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertNotNull;


/**
 * Created by bluexiii on 24/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OssComponentTest {
    private Logger log = LoggerFactory.getLogger(OssComponentTest.class);

    private String localPath;

    @Autowired
    OssClientUtil ossClientUtil;

    public OssComponentTest() {
        localPath = System.getProperty("user.dir") + "/" ;
    }

    @Test
    public void test1() throws Exception {
        System.out.println(  System.getProperty("user.dir")+"---------------");
    }

    @Test
    public void getObject() throws Exception {
        String ossOutUrl = ossClientUtil.getOssOutUrl("test/sonic.jpg");
        System.out.println(ossOutUrl);
    }


    @Test
    public void putObject() throws Exception {
        String ossOutUrl = ossClientUtil.upload("D:\\test\\sonic.jpg","test/sonic2.jpg");
        System.out.println(ossOutUrl);
    }

}