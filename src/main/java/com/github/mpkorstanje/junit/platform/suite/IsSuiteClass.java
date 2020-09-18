package com.github.mpkorstanje.junit.platform.suite;

import org.junit.platform.commons.support.AnnotationSupport;

import java.util.function.Predicate;

class IsSuiteClass implements Predicate<Class<?>> {

    private static final IsPotentialTestContainer isPotentialTestContainer = new IsPotentialTestContainer();

    @Override
    public boolean test(Class<?> testClass) {
        return isPotentialTestContainer.test(testClass) && hasSuiteAnnotation(testClass);
    }

    private boolean hasSuiteAnnotation(Class<?> testClass) {
        return AnnotationSupport.isAnnotated(testClass, Suite.class);
    }

}
