package cn.dubby.itbus.mapper;

import cn.dubby.itbus.bean.BusLine;

import java.util.List;

public interface BusLineMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusLine record);

    int insertSelective(BusLine record);

    BusLine selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusLine record);

    int updateByPrimaryKeyWithBLOBs(BusLine record);

    int updateByPrimaryKey(BusLine record);

    List<BusLine> selectTopN(int limit);
}