package com.yly.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {

    private final Engine engine;
    private final Transmission transmission;

    /**
         Implicit Constructor Injection

         As of Spring 4.3, classes with a single constructor can omit the @Autowired annotation.
         This is a nice little bit of convenience and boilerplate removal.
         On top of that, also starting with 4.3, we can leverage the constructor-based injection in @Configuration annotated classes.
         In addition, if such a class has only one constructor, we can omit the @Autowired annotation as well.
     */
    @Autowired
    public Car(Engine engine, Transmission transmission) {
        this.engine = engine;
        this.transmission = transmission;
    }

    @Override
    public String toString() {
        return String.format("Engine: %s Transmission: %s", engine, transmission);
    }
}
