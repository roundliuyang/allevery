package concurrent;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();
        map.put("name","lisi");
        int  i = 6;
        long l = (long) i << 2;
        System.out.println(l);
        
    }
}
