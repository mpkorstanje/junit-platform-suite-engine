package com.github.mpkorstanje.junit.platform.suite;

import org.apiguardian.api.API;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@API(status = EXPERIMENTAL, since = "1.7")
public @interface Configurations {
	Configuration[] value();
}
