package com.ulpay.testplatform.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: zhangsy
 * @Date: 2019/9/18 21:17
 * @Description: 用于扫描自动化用例
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoTest {

    public String description() default "";

    public boolean enabled() default true;
}
