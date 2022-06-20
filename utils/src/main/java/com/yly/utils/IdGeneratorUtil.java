package com.yly.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * @author xuzhipeng
 * @date 2021/01/31
 */
@Component
public class IdGeneratorUtil {

    private static final Logger log = LoggerFactory.getLogger(IdGeneratorUtil.class);
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RedisLuaUtil redisLuaUtil;

    public static final int TOTAL_BITS_LENGTH = 63;
    public static final int TIME_BITS_LENGTH = 41;
    public static final int DEAL_BITS_LENGTH = 10;
    private static final int COUNT_BITS_LENGTH = 12;

    private static final long TIME_BITS_MASK = (1L << TIME_BITS_LENGTH) - 1L;
    private static final int TIME_BITS_SHIFT_SIZE = TOTAL_BITS_LENGTH - TIME_BITS_LENGTH;
    private static final int DEAL_BITS_MASK = (1 << DEAL_BITS_LENGTH) - 1;

    private static final int MAX_COUNTER = 1 << COUNT_BITS_LENGTH;
    private long lastMillisecond;

    private AtomicInteger counter;
    public static IdGeneratorUtil instance;

    @PostConstruct // 初始化
    public void init(){
        instance = this;
        instance.redisUtil = this.redisUtil;
        instance.redisLuaUtil = this.redisLuaUtil;
    }

    /**
     * 获取一个全局唯一的ID号，64位长整型
     * code 支付方式标识
     * @return
     */
    public static long get() {
//        int code = IDGENERATOR_CODE;
        int code = "IDGENERATOR_CODE".hashCode();
        long id = 0;
        //正常获取
        try {
            id = instance.nextTicket(code);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //再试一次
        if (id == 0) {
            try {
                Thread.sleep(3);//等待3ms
                id = instance.nextTicket(code);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        if (id == 0) {
            //应急措施：返回当前时间戳 + 随机数
            return System.currentTimeMillis() + (int) Math.random() * 10000;
        } else {
            return id;
        }
    }

    /**
     * 计算下一个ID
     *
     * @return
     */
    private synchronized long nextTicket(int code) {
        List<String> keyList = new ArrayList();
        keyList.add(String.valueOf(code));
        // 时钟校验
        long currentMillisecond = System.currentTimeMillis();
        if (currentMillisecond < lastMillisecond) {
            throw new RuntimeException("time is out of sync by " + (lastMillisecond - currentMillisecond) + "ms");
        }
        long ts = currentMillisecond & TIME_BITS_MASK;

        // 时间戳移位到前面41位的地方
        ts = ts << TIME_BITS_SHIFT_SIZE;

        int res = Integer.parseInt(redisLuaUtil.runLuaScript(LuaFileEnum.ID,keyList));

        if (currentMillisecond == lastMillisecond) {

            if (res >= MAX_COUNTER) {
                throw new RuntimeException("too much requests cause counter overflow");
            }
        }
        //如果计数器达到上限
        if (res >= MAX_COUNTER) {
            if (currentMillisecond == lastMillisecond) {
                //同一毫秒内，直接抛异常，由调用方处理
                throw new RuntimeException("too much requests cause counter overflow");
            } else {
                //只要时间戳变了，可以重新计数
                redisUtil.set("id_key_"+code,0);
            }
        }

        //交易代号信息移位到指定位置
        int dealCode = (code & DEAL_BITS_MASK) << COUNT_BITS_LENGTH;

        lastMillisecond = currentMillisecond;
        return ts + dealCode + res;
    }

}

