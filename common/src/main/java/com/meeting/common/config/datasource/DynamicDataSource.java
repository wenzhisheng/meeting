package com.meeting.common.config.datasource;

import org.springframework.transaction.annotation.Propagation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DynamicDataSource {

    // 默认不启用事务
    boolean enableTransaction() default false;
    //事务传播级别
    Propagation propagation() default Propagation.REQUIRED;
    //顶级方法 在最后清空dataSource
    boolean topMethod() default false;

}
