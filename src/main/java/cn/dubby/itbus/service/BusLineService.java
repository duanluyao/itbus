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

    private static final int MAX_NUM = 1000;
    private static final int MIN_NUM = 0;

    public List<BusLine> selectTopN(int limit) {
        if (limit < MIN_NUM || limit > MAX_NUM) {
            limit = MAX_NUM;
        }
        return busLineMapper.selectTopN(limit);
    }

    public List<BusLine> selectAll() {
        return busLineMapper.selectTopN(MAX_NUM);
    }

}
