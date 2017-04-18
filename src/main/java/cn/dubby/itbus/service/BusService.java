package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.mapper.BusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

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

    public Bus save(int busLineId, String busName, String busContent) {
        if (busLineId < 0 || StringUtils.isEmpty(busContent) || StringUtils.isEmpty(busName)) {
            return null;
        }

        Bus bus = new Bus();
        bus.setBusContent(busContent);
        bus.setBusName(busName);
        bus.setBusLineId(busLineId);
        bus.setBusTicket(UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString());

        busMapper.insertSelective(bus);

        return busMapper.selectByPrimaryKey(bus.getId());
    }

}
