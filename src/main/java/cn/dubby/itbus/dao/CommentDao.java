package cn.dubby.itbus.dao;

import cn.dubby.itbus.bean.Comment;
import cn.dubby.itbus.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by teeyoung on 17/5/13.
 */
@Repository
public class CommentDao{

    @Autowired
    private CommentMapper commentMapper;


    public int deleteByPrimaryKey(Integer id) {
        return commentMapper.deleteByPrimaryKey(id);
    }

    public int insert(Comment record) {
        return commentMapper.insert(record);
    }

    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    public Comment selectByPrimaryKey(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(Comment record) {
        return commentMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

    public List<Comment> selectByBusId(Integer busId) {
        return commentMapper.selectByBusId(busId);
    }
}
