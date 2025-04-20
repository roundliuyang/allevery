package com.yly.lambda;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;

/*
首选标准功能接口
函数式接口收集在java.util.function包中，可满足大多数开发人员为 lambda 表达式和方法引用提供目标类型的需求。
这些接口中的每一个都是通用的和抽象的，这使得它们很容易适应几乎所有的 lambda 表达式。开发人员应该在创建新的功能接口之前探索这个包。
 */
public class LambdaTest {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        Integer reduce = integers.stream().reduce(Integer.MIN_VALUE, (a, b) -> Integer.max(a, b));
    }

}
