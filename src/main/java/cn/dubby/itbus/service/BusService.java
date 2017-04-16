package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.mapper.BusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/15.
 */
@Service
public class BusService {

    @Autowired
    private BusMapper busMapper;

    public List<Bus> selectTopN(int limit) {
        if (limit < 0 || limit > 20) {
            limit = 10;
        }
        return busMapper.selectTopN(limit);
    }

    public Bus detail(int busId) {
        return busMapper.selectByPrimaryKey(busId);
    }

}
