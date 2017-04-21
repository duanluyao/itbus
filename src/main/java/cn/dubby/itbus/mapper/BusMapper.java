package cn.dubby.itbus.mapper;

import cn.dubby.itbus.bean.Bus;

import java.util.List;

public interface BusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bus record);

    int insertSelective(Bus record);

    Bus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bus record);

    int updateByPrimaryKeyWithBLOBs(Bus record);

    int updateByPrimaryKey(Bus record);

    List<Bus> selectTopN(int limit);

    List<Bus> selectByLine(int lineId);
}