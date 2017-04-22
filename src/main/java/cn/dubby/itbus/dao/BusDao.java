package cn.dubby.itbus.dao;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.mapper.BusMapper;
import cn.dubby.itbus.util.CacheUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
@Repository
public class BusDao {

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private BusMapper busMapper;

    public List<Bus> selectTopN(int limit) {
        // 先查缓存
        String cacheData = template.opsForValue().get(CacheUtils.getBusListCacheKey(limit));
        if (!StringUtils.isEmpty(cacheData)) {
            List<Bus> busLineList = JSON.parseArray(cacheData, Bus.class);
            return busLineList;
        }

        // 查库
        List<Bus> result = busMapper.selectTopN(limit);
        cacheData = JSON.toJSONString(result);

        // 塞缓存
        template.opsForValue().set(CacheUtils.getBusListCacheKey(limit), cacheData);
        // 塞缓存key
        template.opsForSet().add(CacheUtils.BUS_COLLECTION_KEY, CacheUtils.getBusListCacheKey(limit));

        return result;
    }

    public List<Bus> selectByLine(int lineId) {
        // 先查缓存
        String cacheData = template.opsForValue().get(CacheUtils.getBusListByLineCacheKey(lineId));
        if (!StringUtils.isEmpty(cacheData)) {
            List<Bus> busLineList = JSON.parseArray(cacheData, Bus.class);
            return busLineList;
        }

        // 查库
        List<Bus> result = busMapper.selectByLine(lineId);
        cacheData = JSON.toJSONString(result);

        // 塞缓存
        template.opsForValue().set(CacheUtils.getBusListByLineCacheKey(lineId), cacheData);

        return result;
    }

    public Bus selectByPrimaryKey(Integer id) {
        // 先查缓存
        String cacheData = template.opsForValue().get(CacheUtils.getBusCacheKey(id));
        if (!StringUtils.isEmpty(cacheData)) {
            Bus bus = JSON.parseObject(cacheData, Bus.class);
            return bus;
        }

        // 查库
        Bus result = busMapper.selectByPrimaryKey(id);
        cacheData = JSON.toJSONString(result);

        // 塞缓存
        template.opsForValue().set(CacheUtils.getBusCacheKey(id), cacheData);

        return result;
    }

    public int insertSelective(Bus record) {
        //插库
        int resultRow = busMapper.insertSelective(record);

        //删 Top N 缓存
        Set<String> keySet = template.opsForSet().members(CacheUtils.BUS_COLLECTION_KEY);
        if (!CollectionUtils.isEmpty(keySet)) {
            for (String key : keySet) {
                if (!StringUtils.isEmpty(key)) {
                    template.delete(key);
                }
            }
        }
        //删 listByLineId 缓存
        template.delete(CacheUtils.getBusListByLineCacheKey(record.getBusLineId()));

        return resultRow;
    }

    public int updateByPrimaryKeySelective(Bus record) {
        Bus bus = selectByPrimaryKey(record.getId());

        //删 listByLineId 缓存
        template.delete(CacheUtils.getBusListByLineCacheKey(bus.getBusLineId()));

        int resultRow = busMapper.updateByPrimaryKeySelective(record);

        //删缓存
        template.delete(CacheUtils.getBusCacheKey(record.getId()));
        //删 listByLineId 缓存
        template.delete(CacheUtils.getBusListByLineCacheKey(record.getBusLineId()));

        return resultRow;
    }

}
