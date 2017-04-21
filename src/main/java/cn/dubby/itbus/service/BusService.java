package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.mapper.BusMapper;
import cn.dubby.itbus.service.dto.ModifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(BusService.class);

    @Autowired
    private BusMapper busMapper;

    private static final int MAX_NUM = 100;
    private static final int MIN_NUM = 0;

    public List<Bus> selectTopN(int limit) {
        if (limit < MIN_NUM || limit > MAX_NUM) {
            limit = MAX_NUM;
        }
        return busMapper.selectTopN(limit);
    }

    public List<Bus> listByLine(int lineId) {
        return busMapper.selectByLine(lineId);
    }

    public Bus detail(int busId) {
        Bus bus = busMapper.selectByPrimaryKey(busId);
        if (bus == null) {
            logger.error("detail not exist,id:" + busId);
            return null;
        }

        bus.setBusTicket(null);
        return bus;
    }

    public ModifyResult<Bus> save(int busLineId, String busName, String busContent) {
        if (busLineId < 0 || StringUtils.isEmpty(busContent) || StringUtils.isEmpty(busName)) {
            return ModifyResult.PARAMS_ERROR;
        }

        Bus bus = new Bus();
        bus.setBusContent(busContent);
        bus.setBusName(busName);
        bus.setBusLineId(busLineId);
        bus.setBusTicket(UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString());

        try {
            busMapper.insertSelective(bus);
            bus = busMapper.selectByPrimaryKey(bus.getId());
        } catch (Exception e) {
            logger.error("save", e);
            return ModifyResult.SYSTEM_EXCEPTION;
        }

        return new ModifyResult<>(bus);
    }

    public ModifyResult<Bus> update(int busId, String ticket, int busLineId, String busName, String busContent) {
        if (busId < 0 || StringUtils.isEmpty(ticket) || busLineId < 0 || StringUtils.isEmpty(busContent) || StringUtils.isEmpty(busName)) {
            return ModifyResult.PARAMS_ERROR;
        }

        Bus bus = busMapper.selectByPrimaryKey(busId);
        if (bus == null) {
            logger.error("update not exist,id:" + busId);
            return save(busLineId, busName, busContent);
        }

        if (!ticket.equals(bus.getBusTicket())) {
            logger.error("ticket error,id:" + busId + ",ticket:" + ticket);
            return ModifyResult.TICKET_ERROR;
        }

        bus.setCreateTime(null);
        bus.setUpdateTime(null);
        bus.setBusContent(busContent);
        bus.setBusName(busName);
        bus.setBusLineId(busLineId);

        try {
            busMapper.updateByPrimaryKeySelective(bus);

            bus = busMapper.selectByPrimaryKey(busId);
        } catch (Exception e) {
            logger.error("save", e);
            return ModifyResult.SYSTEM_EXCEPTION;
        }

        return new ModifyResult<>(bus);
    }

}
