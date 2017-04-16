package cn.dubby.itbus.controller;

import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.service.BusLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/15.
 */
@RestController
@RequestMapping(value = "line")
public class BusLineController {

    private final Logger logger = LoggerFactory.getLogger(BusLineController.class);

    @Autowired
    private BusLineService busLineService;

    private static final int TOP_NUM = 20;

    @RequestMapping(value = "index")
    public Object indexList() {
        List<BusLine> busLineList = null;
        try {
            busLineList = busLineService.selectTopN(TOP_NUM);
        } catch (Exception e) {
            logger.error("indexList error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(busLineList);
    }

}
