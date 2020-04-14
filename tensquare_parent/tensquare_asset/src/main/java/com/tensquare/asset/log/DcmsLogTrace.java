package com.tensquare.asset.log;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DcmsLogTrace {
    boolean printArgs() default  true;

    boolean printReturn() default  true;

    boolean checkApiRequest() default  true;

    boolean checkSysId() default  true;

    boolean checkUserId() default  true;

    boolean checkToken() default  true;

    boolean checkBizParam() default  true;

    Class<?>[] groups() default {};
}
