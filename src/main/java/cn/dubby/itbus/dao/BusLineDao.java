package cn.dubby.itbus.dao;

import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.mapper.BusLineMapper;
import cn.dubby.itbus.util.CacheUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
@Repository
public class BusLineDao {

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private BusLineMapper busLineMapper;


    public List<BusLine> selectTopN(int limit) {
        // 先查缓存
        String cacheData = template.opsForValue().get(CacheUtils.getBusLineListCacheKey(limit));
        if (!StringUtils.isEmpty(cacheData)) {
            List<BusLine> busLineList = JSON.parseArray(cacheData, BusLine.class);
            return busLineList;
        }

        // 查库
        List<BusLine> result = busLineMapper.selectTopN(limit);
        cacheData = JSON.toJSONString(result);

        // 塞缓存
        template.opsForValue().set(CacheUtils.getBusLineListCacheKey(limit), cacheData);
        // 塞缓存key
        template.opsForSet().add(CacheUtils.BUS_LINE_COLLECTION_KEY, CacheUtils.getBusLineListCacheKey(limit));

        return result;
    }

}
