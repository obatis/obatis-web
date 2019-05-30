package com.obatis.core.annotation;

import com.obatis.core.annotation.config.ImortStartupLoadAutoConfigure;
import com.obatis.config.SystemConstant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启动配置类，主要实现包扫描。
 * 如果引用项目的启动类位于com目录下或者com.obatis目录下，可以直接使用 @SpringBootApplication 注解即可，无需使用 @StartupLoadAutoConfigure，使用也没有其他影响
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
@SpringBootApplication
@ComponentScan(value = SystemConstant.CORE_BASE_DIR)
@Import(value = ImortStartupLoadAutoConfigure.class)
public @interface StartupLoadAutoConfigure {

}
