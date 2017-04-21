package cn.dubby.itbus.controller;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.service.BusService;
import cn.dubby.itbus.service.dto.ModifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/16.
 */
@RestController
@RequestMapping(value = "bus")
public class BusController {

    private final Logger logger = LoggerFactory.getLogger(BusController.class);

    private static final int TOP_NUM = 100;

    @Autowired
    private BusService busService;

    @RequestMapping(value = "index")
    public Object indexList() {
        List<Bus> busList = null;
        try {
            busList = busService.selectTopN(TOP_NUM);
        } catch (Exception e) {
            logger.error("indexList error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(busList);
    }

    @RequestMapping(value = "detail")
    public Object detail(int id) {
        Bus bus = null;
        try {
            bus = busService.detail(id);
        } catch (Exception e) {
            logger.error("detail error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(bus);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(int busLineId, String busName, String busContent) {
        ModifyResult<Bus> bus = null;
        try {
            bus = busService.save(busLineId, busName, busContent);
        } catch (Exception e) {
            logger.error("save error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (bus.isSuccess())
            return ResponseEntity.ok(bus.getResult());

        return ResponseEntity.badRequest();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(int busId, String ticket, int busLineId, String busName, String busContent) {
        ModifyResult<Bus> bus = null;
        try {
            bus = busService.update(busId, ticket, busLineId, busName, busContent);
        } catch (Exception e) {
            logger.error("save error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (bus.isSuccess())
            return ResponseEntity.ok(bus.getResult());

        return ResponseEntity.badRequest();
    }

}
