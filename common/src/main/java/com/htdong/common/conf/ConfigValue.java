package com.htdong.common.conf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Import(ConfigRegister.class)
public @interface ConfigValue {

    static final String DEFAULT_NULL = "@null";

    String key();

    String defaultValue() default DEFAULT_NULL;
}
