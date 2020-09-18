package com.github.mpkorstanje.junit.platform.suite;

import java.util.function.Predicate;

import static org.junit.platform.commons.util.ReflectionUtils.isAbstract;
import static org.junit.platform.commons.util.ReflectionUtils.isInnerClass;
import static org.junit.platform.commons.util.ReflectionUtils.isPrivate;

class IsPotentialTestContainer implements Predicate<Class<?>> {

    @Override
    public boolean test(Class<?> candidate) {
        // Please do not collapse the following into a single statement.
        if (isPrivate(candidate)) {
            return false;
        }
        if (isAbstract(candidate)) {
            return false;
        }
        if (candidate.isLocalClass()) {
            return false;
        }
        if (candidate.isAnonymousClass()) {
            return false;
        }
        return !isInnerClass(candidate);
    }

}
