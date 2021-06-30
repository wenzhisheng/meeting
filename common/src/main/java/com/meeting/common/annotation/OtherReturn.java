package com.meeting.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: dameizi
 * @description: 自定义返回结果集结构
 * @dateTime 2019-03-29 14:17
 * @className com.weilaizhe.common.exception.OtherReturn
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
public @interface OtherReturn {
}
