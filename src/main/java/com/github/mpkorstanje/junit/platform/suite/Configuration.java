package com.github.mpkorstanje.junit.platform.suite;

import org.apiguardian.api.API;
import org.apiguardian.api.API.Status;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @Configuration} passes a configuration value to the suite.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@API(status = Status.EXPERIMENTAL)
@Repeatable(Configurations.class)
public @interface Configuration {
    String key();

    String value() default "";

}
