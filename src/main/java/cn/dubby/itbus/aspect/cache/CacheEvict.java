package cn.dubby.itbus.aspect.cache;

import java.lang.annotation.*;

/**
 * Created by teeyoung on 17/5/5.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheEvict {
    String cacheKey();
}
