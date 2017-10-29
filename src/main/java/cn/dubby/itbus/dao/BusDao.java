package cn.dubby.itbus.dao;

import cn.dubby.itbus.aspect.cache.CacheEvict;
import cn.dubby.itbus.aspect.cache.SingleCache;
import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.mapper.BusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
@Repository
public class BusDao {

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private BusMapper busMapper;

    @SingleCache(cacheKey = "'busTop:'.concat(#limit)", timeout = 1, unit = TimeUnit.HOURS)
    public List<Bus> selectTopN(int limit) {
        return busMapper.selectTopN(limit);
    }

    public List<Bus> selectByLine(int lineId, int offset, int limit) {
        return busMapper.selectByLine(lineId, offset, limit);
    }

    public Bus selectNext(int lineId, int busId) {
        return busMapper.selectNext(lineId, busId);
    }

    public Bus selectPrev(int lineId, int busId) {
        return busMapper.selectPrev(lineId, busId);
    }

    public List<Bus> selectByUserId(int userId) {
        return busMapper.selectByUserId(userId);
    }

    @SingleCache(cacheKey = "'bus:id:'.concat(#id)")
    public Bus selectByPrimaryKey(Integer id) {
        return busMapper.selectByPrimaryKey(id);
    }

    public int insertSelective(Bus record) {
        int result = busMapper.insertSelective(record);
        clearBusLineList();
        return result;
    }

    @CacheEvict(cacheKey = "'bus:id:'.concat(#record.id)")
    public int updateByPrimaryKeySelective(Bus record) {
        return busMapper.updateByPrimaryKeySelective(record);
    }

    public int countByLine(int lineId) {
        return busMapper.countByLine(lineId);
    }

    @CacheEvict(cacheKey = "'bus:id:'.concat(#busId)")
    public void up(Integer busId) {
        if (busId == null)
            return;

        busMapper.up(busId);
        clearBusLineList();
    }

    @CacheEvict(cacheKey = "'bus:id:'.concat(#busId)")
    public void down(Integer busId) {
        if (busId == null)
            return;

        busMapper.down(busId);
        clearBusLineList();
    }

    private void clearBusLineList() {
        template.delete("busLineTop:1000");
        template.delete("busLineTop:20");
    }
}
