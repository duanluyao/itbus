package cn.dubby.itbus.aspect.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by teeyoung on 2016/12/30.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SingleCache {
    String cacheKey();

    long timeout() default 1;

    TimeUnit unit() default TimeUnit.HOURS;
}
