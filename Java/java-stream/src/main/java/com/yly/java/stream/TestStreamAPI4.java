package com.yly.java.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: yly
 * Date: 2021/12/7 16:34
 */
public class TestStreamAPI4 {
    public static void main(String[] args) {

        //字符串list：
        List<String> list1 = new ArrayList();
        list1.add("1111");
        list1.add("2222");
        list1.add("3333");

        List<String> list2 = new ArrayList();
        list2.add("3333");
        list2.add("4444");
        list2.add("5555");

        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
        //  上面写法等同于  List<String> intersection = list1.stream().filter(list2::contains).collect(Collectors.toList());             注意

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());

        // 并集
        List<String> listAll = list1.parallelStream().collect(Collectors.toList());
        List<String> listAll2 = list2.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(Collectors.toList());
    }


    /*
        对象List:

        //所有学生
        List<Student> allList;
        //男学生
        List<Student> boyList;

        //取女学生
        List<Student> girlList;

        girlList=allList.stream.filter(new Predicate<Student>(){
            @Override
            public boolean test(Student student) {
                for(Student boy: boyList){
                    if(student.getSex()==boy.getSex()){
                        return false;
                    }
                }
                return true;
            }
        }).collect(Collectors.toList());
     */
}
