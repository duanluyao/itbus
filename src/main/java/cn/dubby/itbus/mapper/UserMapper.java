package cn.dubby.itbus.mapper;

import cn.dubby.itbus.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByEmail(String email);

    User selectByInvitationCode(String code);

    int modifyPassword(@Param("password") String password, @Param("userId") int userId);
}