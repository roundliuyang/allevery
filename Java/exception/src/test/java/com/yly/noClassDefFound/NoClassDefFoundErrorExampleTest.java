package com.yly.noClassDefFound;

import org.junit.Test;

public class NoClassDefFoundErrorExampleTest {
    @Test()
    public void givenInitErrorInClass_whenloadClass_thenNoClassDefFoundError() {

        NoClassDefFoundErrorExample sample = new NoClassDefFoundErrorExample();
        sample.getClassWithInitErrors();
    }
}
