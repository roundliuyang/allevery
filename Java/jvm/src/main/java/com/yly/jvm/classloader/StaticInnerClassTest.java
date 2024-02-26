package com.yly.jvm.classloader;

public class StaticInnerClassTest {

    private P p1 = new P("A--p1");
    static P p3 = new P("A--p3");

    public StaticInnerClassTest() {
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
        new StaticInnerClassTest();
    }
    
    
    

    public static class C {
        private P p1 = new P("C--p1");
        static P p3 = new P("C--p3");

        public C() {
            System.out.println("C()");
        }

        private P p2 = new P("C--p2");

        static {
            new P("C--static");
        }

        {
            new P("C{...}");
        }
    }
}
