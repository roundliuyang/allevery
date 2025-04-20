package com.yly.jvm.classloader.customerloader;

public class LoaderTest {
    public static void main(String[] args) throws Exception {
        CustomClassLoader loader = new CustomClassLoader();

        Object myObj = loader.loadClassAndInstantiate(Loader.class.getName());

        System.out.println("自定义：" + myObj.getClass());

        System.out.println("--------------------自定义------------------------");

        System.out.print("instanceof: ");
        System.out.println(myObj instanceof Loader);

        System.out.println("--------------------默认-------------------------");

        Object obj1 = new Loader();
        System.out.print("instanceof: ");
        System.out.println(obj1 instanceof Loader);
    }
}