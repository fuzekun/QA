package com.fuzekun.demo1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)                 // 注解到方法上
@Retention(RetentionPolicy.RUNTIME)         // 运行的时候有效
public @interface LoginRequired {



}
