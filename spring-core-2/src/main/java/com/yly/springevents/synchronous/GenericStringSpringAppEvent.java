package com.yly.springevents.synchronous;

class GenericStringSpringAppEvent extends GenericSpringAppEvent<String> {

    GenericStringSpringAppEvent(final Object source, final String what) {
        super(source, what);
    }

}
