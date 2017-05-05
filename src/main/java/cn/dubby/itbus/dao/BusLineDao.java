package cn.dubby.itbus.dao;

import cn.dubby.itbus.aspect.cache.SingleCache;
import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.mapper.BusLineMapper;
import cn.dubby.itbus.util.CacheUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
@Repository
public class BusLineDao {

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private BusLineMapper busLineMapper;

    @SingleCache(cacheKey = "'busLineTop:'.concat(#limit)", timeout = 5, unit = TimeUnit.MINUTES)
    public List<BusLine> selectTopN(int limit) {
        return busLineMapper.selectTopN(limit);
    }

}
