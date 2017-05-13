package cn.dubby.itbus.controller;

import cn.dubby.itbus.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by teeyoung on 17/5/13.
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object comment(Integer busId, String content) {
        return commentService.comment(busId, content);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Object list(Integer busId) {
        return commentService.listByBusId(busId);
    }

}
