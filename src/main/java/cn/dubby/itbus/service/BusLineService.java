package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.BusLine;
import cn.dubby.itbus.dao.BusLineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangzheng03 on 2017/4/15.
 */
@Service
public class BusLineService {

    @Autowired
    private BusLineDao busLineDao;

    private static final int MAX_NUM = 1000;
    private static final int MIN_NUM = 0;

    public List<BusLine> selectTopN(int limit) {
        if (limit < MIN_NUM || limit > MAX_NUM) {
            limit = MAX_NUM;
        }
        return busLineDao.selectTopN(limit);
    }

    public List<BusLine> selectAll() {
        return busLineDao.selectTopN(MAX_NUM);
    }

}
