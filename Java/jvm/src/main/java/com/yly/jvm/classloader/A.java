package com.yly.jvm.classloader;


/**
 * 上面的结果很明显验证了我们的猜想，先进行类对象初始化，然后再进行类实例对象初始化
 * 在类对象进行初始化的时候，按顺序会初始化类中的静态成员变量，静态初始化块。
 * 在进行类实例初始化的时候，按顺序会初始化成员变量，初始化块，构造函数
 */
class A {
    private P p1 = new P("A--p1");
    static P p3 = new P("A--p3");

    public A() {
        System.out.println("A()");
    }

    private P p2 = new P("A--p2");

    static {
        new P("A--static");
    }

    {
        new P("A{...}");
    }

    public static void main(String[] args) {
        A a = new A();
    }

}

