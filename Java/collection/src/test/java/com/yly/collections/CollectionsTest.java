package com.yly.collections;

/*
    Collections是JDK提供的工具类，同样位于java.util包中。它提供了一系列静态方法，能更方便地操作各种集合。
    注意Collections结尾多了一个s，不是Collection！
 */
public class CollectionsTest {

    /*
        我们一般看方法名和参数就可以确认Collections提供的该方法的功能。例如，对于以下静态方法：
        public static boolean addAll(Collection<? super T> c, T... elements) { ... }
        addAll()方法可以给一个Collection类型的集合添加若干元素。因为方法签名是Collection，所以我们可以传入List，Set等各种集合类型。
     */


    /*
        创建空集合
        Collections提供了一系列方法来创建空集合：
        创建空List：List<T> emptyList()
        创建空Map：Map<K, V> emptyMap()
        创建空Set：Set<T> emptySet()
        要注意到返回的空集合是不可变集合，无法向其中添加或删除元素。
        此外，也可以用各个集合接口提供的of(T...)方法创建空集合。例如，以下创建空List的两个方法是等价的：
        List<String> list1 = List.of();
        List<String> list2 = Collections.emptyList();
     */


}
