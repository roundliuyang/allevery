package com.yly.jedis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * Author: yly
 * Date: 2021/11/3 14:29
 * redistemplate下opsForHash的操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedistemplateOpsForHash {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test01(){
        // 1、put(H key, HK hashKey, HV value) -- 新增HashMap
        redisTemplate.opsForHash().put("hashValue","map1","map1-1");
        redisTemplate.opsForHash().put("hashValue","map2","map2-2");


        // 2、values(H key) --Get entry set (values) of hash at {@code key}.
        List<Object> hashValue = redisTemplate.opsForHash().values("hashValue");            // 结果  [map1-1, map2-2]


        // 3、entries(H key) -- Get entire hash stored at key.
        Map<Object, Object> hashValue1 = redisTemplate.opsForHash().entries("hashValue");    // 结果 {map2=map2-2, map1=map1-1}


        // 4、get(H key, Object hashKey)  -- Get value for given hashKey from hash at key.
        Object o = redisTemplate.opsForHash().get("hashValue", "map1");               // 结果 map1-1


        // 5、hasKey(H key, Object hashKey)     -- Determine if given hash hashKey exists.
        Boolean aBoolean = redisTemplate.opsForHash().hasKey("hashValue", "map3");     //结果  false


        // 6、keys(H key)                       -- Get key set (fields) of hash at key.
        Set<Object> hashValue2 = redisTemplate.opsForHash().keys("hashValue");                    //结果 [map1, map2]


        // 7、size(H key)                       -- Get size of hash at key.
        Long hashValue3 = redisTemplate.opsForHash().size("hashValue");                     // 结果 2


        // 8、increment(H key, HK hashKey, Long delta)   -- Increment value of a hash hashKey by the given delta.
        Long increment = redisTemplate.opsForHash().increment("hashInc", "map1", 3);


        // 9、increment(H key, HK hashKey, double delta)   -- Increment value of a hash hashKey by the given delta.
        Double increment1 = redisTemplate.opsForHash().increment("hashInc", "map1", 3.0);


        // 10、multiGet(H key, Collection<HK> hashKeys)    -- Get values for given hashKeys from hash at key.
        List<Object> list = new ArrayList<Object>();
        list.add("map1");
        list.add("map2");
        List mapValueList = redisTemplate.opsForHash().multiGet("hashValue",list);     // 结果 [map1-1, map2-2]


        // 11、putAll(H key, Map<? extends HK,? extends HV> m)     --Set multiple hash fields to multiple values using data provided in m.
        Map newMap = new HashMap();
        newMap.put("map3","map3-3");
        newMap.put("map5","map5-5");
        redisTemplate.opsForHash().putAll("hashValue",newMap);
        Map<Object, Object> hashValue4 = redisTemplate.opsForHash().entries("hashValue");


        // 12、putIfAbsent(H key, HK hashKey, HV value)     -- Set the value of a hash hashKey only if hashKey does not exist.
        redisTemplate.opsForHash().putIfAbsent("hashValue","map6","map6-6");
        Map<Object, Object> hashValue5 = redisTemplate.opsForHash().entries("hashValue");


        //13、scan(H key, ScanOptions options)          -- to do -- 匹配获取键值对，ScanOptions.NONE为获取全部键对，ScanOptions.scanOptions().match("map1").build()     匹配获取键位map1的键值对,不能模糊匹配。
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("hashValue", ScanOptions.scanOptions().match("map1").build());
        //Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("hashValue",ScanOptions.NONE);
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            System.out.println("通过scan(H key, ScanOptions options)方法获取匹配键值对:" + entry.getKey() + "---->" + entry.getValue());
        }

        //14、delete(H key, Object... hashKeys)    -- Delete given hash hashKeys.
        redisTemplate.opsForHash().delete("hashValue","map1","map2");
        Map<Object, Object> hashValue6 = redisTemplate.opsForHash().entries("hashValue");
    }

//     15、public List<Menu> selectMenus() throws Exception     缓存菜单的操作
//    public List<Menu> selectMenus() throws Exception {
//        Collection<String> menujsons = redisTemplate.opsForHash().entries("menuList").values();
//        //查看缓存是否存在菜单
//        if (menujsons != null && menujsons.size() != 0) {
//            List<Menu> menuList = new ArrayList<>();
//            menujsons.forEach(a -> menuList.add(JSONObject.parseObject(a, Menu.class)));
//            //缓存取出数据排序
//            Collections.sort(menuList, Comparator.comparing(BaseEntity::getId));
//            return menuList;
//        }
//        //不存在 数据库中查询并存入缓存 返回
//        List<Menu> allMenu = getMapper().selectMenus();
//        if(CollectionUtils.isNotEmpty(allMenu)){
//            allMenu.forEach(m -> redisTemplate.opsForHash().put("menuList", m.getId().toString(), JSONObject.toJSONString(m)));
//        }
//        return allMenu;
//    }

}
