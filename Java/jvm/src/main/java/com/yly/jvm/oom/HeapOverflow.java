package com.yly.jvm.oom;

import java.util.ArrayList;
import java.util.List;

public class HeapOverflow {
    public static void main(String[] args) {
        long counter = 0L;
        List<String> list = new ArrayList<String>();

        while (true) {
            list.add(new String("ddddddddddddd"));
            System.out.println("当前创建了第" + ++counter + "个对象");
        }
    }
}
 