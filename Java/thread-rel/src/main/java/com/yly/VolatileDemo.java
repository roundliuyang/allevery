package com.yly;

public class VolatileDemo {
    int a = 0;
    volatile boolean flag = false;

    public void writer() {
        a = 1;  // step 1
        flag = true;   // step 2
    }

    public void reader() {
        if (flag) {          // step 3
            System.out.println(a);  // step 4
        }
    }
}

