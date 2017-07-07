package cn.dubby.itbus.dao;

import cn.dubby.itbus.bean.User;
import cn.dubby.itbus.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by teeyoung on 17/5/5.
 */
@Repository
public class UserDao {

    @Autowired
    private UserMapper userMapper;


    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    public User selectByInvitationCode(String code) {
        return userMapper.selectByInvitationCode(code);
    }

    public boolean modifyPassword(String password, int userId) {
        return userMapper.modifyPassword(password, userId) == 1;
    }
}
