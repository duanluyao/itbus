package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.mapper.BusLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/15.
 */
@Service
public class BusLineService {

    @Autowired
    private BusLineMapper busLineMapper;

    public List<BusLine> selectTopN(int limit) {
        if (limit < 0 || limit > 20) {
            limit = 10;
        }
        return busLineMapper.selectTopN(limit);
    }

}
