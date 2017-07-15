package cn.dubby.itbus.controller;

import cn.dubby.itbus.bean.Bus;
import cn.dubby.itbus.bean.User;
import cn.dubby.itbus.constant.HttpRequestConstant;
import cn.dubby.itbus.service.BusService;
import cn.dubby.itbus.service.dto.CountDto;
import cn.dubby.itbus.service.dto.ModifyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/16.
 */
@RestController
@RequestMapping(value = "bus")
public class BusController {

    private final Logger logger = LoggerFactory.getLogger(BusController.class);

    private static final int TOP_NUM = 20;

    @Autowired
    private BusService busService;

    @RequestMapping(value = "index")
    public Object indexList() {
        List<Bus> busList = null;

        busList = busService.selectTopN(TOP_NUM);

        return ResponseEntity.ok(busList);
    }

    @RequestMapping(value = "list")
    public Object listByLine(int lineId, int pageId) {
        List<Bus> busList = null;

        busList = busService.listByLine(lineId, pageId);

        return ResponseEntity.ok(busList);
    }

    @RequestMapping(value = "mine")
    public Object mine(HttpServletRequest httpServletRequest) {
        List<Bus> busList = null;
        Object user = httpServletRequest.getAttribute(HttpRequestConstant.LOGIN_USER);
        if (user == null) {
            // 未登录
            busList = new ArrayList<>();
        } else {
            // 登录
            User loginUser  = (User) user;
            busList = busService.mine(loginUser.getId());
        }

        return ResponseEntity.ok(busList);
    }

    @RequestMapping(value = "count")
    public Object countByLine(int lineId, int pageId) {
        CountDto countDto = busService.countByLine(lineId, pageId);
        return ResponseEntity.ok(countDto);
    }

    @RequestMapping(value = "detail")
    public Object detail(int id) {
        Bus bus = null;

        bus = busService.detail(id);

        return ResponseEntity.ok(bus);
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Object save(HttpServletRequest httpServletRequest, int busLineId, String busName, String busContent, @RequestParam(name = "email", required = false) String email) {
        ModifyResult<Bus> bus = null;

        Object user = httpServletRequest.getAttribute(HttpRequestConstant.LOGIN_USER);
        if (user == null) {
            // 未登录
            bus = busService.save(busLineId, busName, busContent, email);
        } else {
            // 登录
            User loginUser  = (User) user;
            bus = busService.save(busLineId, busName, busContent, email, loginUser.getId());
        }

        if (bus.isSuccess())
            return ResponseEntity.ok(bus.getResult());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请求出错");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Object update(int busId, String ticket, int busLineId, String busName, String busContent) {
        ModifyResult<Bus> bus = null;

        bus = busService.update(busId, ticket, busLineId, busName, busContent);

        if (bus.isSuccess())
            return ResponseEntity.ok(bus.getResult());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请求出错");
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public Object modify(HttpServletRequest httpServletRequest, int busId, int busLineId, String busName, String busContent) {
        ModifyResult<Bus> bus = null;

        Object user = httpServletRequest.getAttribute(HttpRequestConstant.LOGIN_USER);
        if (user == null) {
            // 未登录
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请先登录");
        } else {
            // 登录
            bus = busService.modify(busId, busLineId, busName, busContent);
        }

        if (bus.isSuccess())
            return ResponseEntity.ok(bus.getResult());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请求出错");
    }

    @RequestMapping(value = "up", method = RequestMethod.POST)
    public Object up(Integer busId) {
        busService.up(busId);
        return ResponseEntity.ok().body(null);
    }

    @RequestMapping(value = "down", method = RequestMethod.POST)
    public Object down(Integer busId) {
        busService.down(busId);
        return ResponseEntity.ok().body(null);
    }

    @RequestMapping(value = "next")
    public Object next(int id) {
        Bus bus = null;

        bus = busService.next(id);

        return ResponseEntity.ok(bus);
    }

    @RequestMapping(value = "prev")
    public Object prev(int id) {
        Bus bus = null;

        bus = busService.prev(id);

        return ResponseEntity.ok(bus);
    }

}
