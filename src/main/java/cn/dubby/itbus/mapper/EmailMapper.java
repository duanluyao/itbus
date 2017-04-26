package cn.dubby.itbus.mapper;

import cn.dubby.itbus.bean.Email;
import cn.dubby.itbus.bean.EmailWithBLOBs;

public interface EmailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmailWithBLOBs record);

    int insertSelective(EmailWithBLOBs record);

    EmailWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmailWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(EmailWithBLOBs record);

    int updateByPrimaryKey(Email record);
}