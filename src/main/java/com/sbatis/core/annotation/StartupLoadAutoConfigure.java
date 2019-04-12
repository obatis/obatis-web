package com.sbatis.core.annotation;

import com.sbatis.core.annotation.config.ImortStartupLoadAutoConfigure;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
@SpringBootApplication
@Import(value = ImortStartupLoadAutoConfigure.class)
@ComponentScan(value = "com.sbatis",
        useDefaultFilters = true
)
public @interface StartupLoadAutoConfigure {

}
