package cn.dubby.itbus.test.cache;

import cn.dubby.itbus.test.BaseTest;
import cn.dubby.itbus.util.CacheUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class RedisTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String, String> template;

    @Test
    public void testHash() {



    }


    public static void main(String[] args) {

    }

}
