package com.tensquare.asset.validation;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckObj {
    String message() default "嵌套对象校验失败";

    boolean nullable() default false;

    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        CheckObj[] value();
    }
}
