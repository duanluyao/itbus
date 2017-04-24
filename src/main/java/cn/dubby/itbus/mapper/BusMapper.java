package cn.dubby.itbus.mapper;

import cn.dubby.itbus.bean.Bus;
import org.apache.ibatis.annotations.Param;

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

    List<Bus> selectByLine(@Param("lineId") int lineId, @Param("offset") int offset, @Param("limit") int limit);
}