package cn.dubby.itbus.test.cache;

import cn.dubby.itbus.test.BaseTest;
import cn.dubby.itbus.util.CacheUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class RedisTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> template;

    @Test
    public void testHash() {
        String visitId = UUID.randomUUID().toString();
        Long result = template.opsForHash().increment(CacheUtils.LOGIN_USER_COLLECTION, visitId, 1L);
        System.out.println(result);

        template.opsForZSet().incrementScore("TEST_ZSET", visitId, 1);


    }

}
