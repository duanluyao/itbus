package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.Comment;
import cn.dubby.itbus.dao.CommentDao;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by teeyoung on 17/5/13.
 */
@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentDao commentDao;

    public Comment comment(Integer busId, String content) {
        Comment comment = new Comment();
        comment.setBusId(busId);
        comment.setContent(StringEscapeUtils.escapeHtml4(content));

        commentDao.insertSelective(comment);
        return comment;
    }

    public List<Comment> listByBusId(Integer busId) {
        return commentDao.selectByBusId(busId);
    }

}
