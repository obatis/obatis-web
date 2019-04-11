package com.sbatis.core.annotation;

import com.sbatis.core.annotation.config.ImortLoadResourceAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
@Import(value = ImortLoadResourceAutoConfig.class)
public @interface LoadResourceAutoConfig {
}
