package cn.dubby.itbus.controller;

import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.service.BusLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<BusLine> busLineList = busLineService.selectTopN(TOP_NUM);

        return ResponseEntity.ok(busLineList);
    }

    @RequestMapping(value = "all")
    public Object all() {
        List<BusLine> busLineList = busLineService.selectAll();

        return ResponseEntity.ok(busLineList);
    }

}
