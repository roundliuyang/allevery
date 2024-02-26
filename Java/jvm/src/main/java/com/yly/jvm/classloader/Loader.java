package com.yly.jvm.classloader;

import java.io.InputStream;

public class Loader {
    public static void main(String[] args) throws Exception {
        ClassLoader loader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name)
                    throws ClassNotFoundException {
                try {
                    String className = name
                            .substring(name.lastIndexOf('.') + 1) + ".class";
                    InputStream iStream = getClass().getResourceAsStream(
                            className);
                    if (iStream == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[iStream.available()];
                    iStream.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (Exception e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = new Loader();

        System.out.println("默认：" + obj.getClass());

        Object myObj = loader.loadClass(Loader.class.getName()).newInstance();

        System.out.println("自定义：" + myObj.getClass());

        System.out.println("--------------------自定义------------------------");

        System.out.print("instanceof: ");
        System.out.println(myObj instanceof com.yly.jvm.classloader.Loader);

        System.out.println("--------------------默认-------------------------");

        Object obj1 = new Loader();
        System.out.print("instanceof: ");
        System.out.println(obj1 instanceof com.yly.jvm.classloader.Loader);

    }
}