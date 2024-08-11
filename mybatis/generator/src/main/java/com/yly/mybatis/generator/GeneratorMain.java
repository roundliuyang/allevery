package com.yly.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GeneratorMain {
    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //读取配置文件com.eloancn.operate.generator.GeneratorMain
        //	File configFile = new File("src/main/java/com/yy/mybatis/generatorConfig_eloan_db.xml");
        //	File configFile = new File("src/main/java/com/yy/mybatis/generatorConfig_eloancn_inte.xml");
        //	File configFile = new File("E:\\p2peloan_workspace\\eloan_act_bg_branch\\src\\main\\java\\com\\eloancn\\operate\\generator\\generatorConfig_act.xml");
        File configFile = new File("D:\\allevery\\allevery\\mybatis\\generator\\src\\main\\java\\com\\yly\\mybatis\\generator\\generatorConfig_act.xml");
        System.out.println(configFile.exists());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config;
        try {
            config = cp.parseConfiguration(configFile);


            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator;
            try {
                myBatisGenerator = new MyBatisGenerator(config, callback,
                        warnings);
                myBatisGenerator.generate(null);

                //打印结果
                for (String str : warnings) {
                    System.out.println(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("成功生成!!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLParserException e) {
            e.printStackTrace();
        }
    }
}
