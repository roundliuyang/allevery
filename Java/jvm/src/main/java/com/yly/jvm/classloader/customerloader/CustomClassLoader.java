package com.yly.jvm.classloader.customerloader;

import java.io.InputStream;

class CustomClassLoader extends ClassLoader {
    public Object loadClassAndInstantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> clazz = loadClass(className);
        return clazz.newInstance();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String className = name.substring(name.lastIndexOf('.') + 1) + ".class";
            InputStream iStream = getClass().getResourceAsStream(className);
            if (iStream == null) {
                return super.findClass(name);
            }
            byte[] b = new byte[iStream.available()];
            iStream.read(b);
            return defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name);
        }
    }
}
