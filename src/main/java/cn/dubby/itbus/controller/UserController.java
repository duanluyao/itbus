package cn.dubby.itbus.controller;

import cn.dubby.itbus.bean.User;
import cn.dubby.itbus.constant.CookieConstant;
import cn.dubby.itbus.constant.HttpRequestConstant;
import cn.dubby.itbus.service.UserService;
import cn.dubby.itbus.service.dto.RegisterDto;
import cn.dubby.itbus.service.dto.ResetPasswordDto;
import cn.dubby.itbus.service.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by teeyoung on 17/5/5.
 */
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Object login(String email, String password, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        User user = userService.login(email, password, httpServletResponse, httpServletRequest);

        if (user == null) {
            return ResponseEntity.badRequest().body("账号或密码错误，请重试");
        }
        UserDto userDto = new UserDto();
        userDto.setNickName(user.getNickName());
        userDto.setUserId(user.getId());

        return userDto;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Object register(String email, String password, String invitationCode, HttpServletResponse httpServletResponse) {
        if (StringUtils.isEmpty(invitationCode)) {
            return ResponseEntity.badRequest().body("邀请码不能为空");
        }

        RegisterDto register = userService.register(email, password, invitationCode);

        switch (register.getErrorCode()) {
            case 1:
                return ResponseEntity.ok().body(register);
            case -1:
                return ResponseEntity.badRequest().body("邮箱已经被注册了");
            case -2:
                return ResponseEntity.badRequest().body("注册码错误");
        }
        return ResponseEntity.badRequest().body("注册失败");
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public Object logout(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        userService.logout(httpServletResponse, httpServletRequest);
        return ResponseEntity.ok().body("退出登录");
    }

    @RequestMapping(value = "resetpassword")
    public Object resetPassword(String email) {
        ResetPasswordDto resetPasswordDto = userService.resetPassword(email);

        switch (resetPasswordDto.getErrorCode()) {
            case 1:
                return ResponseEntity.ok().body(resetPasswordDto);
            case -1:
                return ResponseEntity.badRequest().body("没有账号");
        }
        return ResponseEntity.ok().body("重置失败");
    }

    @RequestMapping(value = "modify/password")
    public Object modifyPassword(String password, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Object user = httpServletRequest.getAttribute(HttpRequestConstant.LOGIN_USER);
        if (user == null) {
            // 未登录
            return ResponseEntity.ok().body("请先登录！");
        } else {
            // 登录
            User loginUser  = (User) user;
            boolean success = userService.modifyPassword(password, loginUser.getId());
            if (success) {
                return ResponseEntity.ok().body("修改成功！");
            }
        }
        return ResponseEntity.badRequest().body("修改失败");
    }
}
