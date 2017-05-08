package cn.dubby.itbus.service;

import cn.dubby.itbus.bean.User;
import cn.dubby.itbus.constant.CookieConstant;
import cn.dubby.itbus.dao.UserDao;
import cn.dubby.itbus.mapper.EmailMapper;
import cn.dubby.itbus.service.dto.RegisterDto;
import cn.dubby.itbus.service.dto.ResetPasswordDto;
import cn.dubby.itbus.util.CacheUtils;
import cn.dubby.itbus.util.CookieUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by teeyoung on 17/4/26.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, String> template;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailMapper emailMapper;

    public User login(String email, String password, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        User user = userDao.selectByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            Cookie cookie = new Cookie(CookieConstant.LOGIN_EMAIL, user.getEmail());
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            httpServletResponse.addCookie(cookie);

            String visitId = CookieUtils.getCookie(httpServletRequest, CookieConstant.VISIT_ID);
            user.setPassword(null);
            template.opsForHash().put(CacheUtils.LOGIN_USER_COLLECTION, visitId, JSON.toJSONString(user));
            return user;
        }

        return null;
    }

    public void logout(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Cookie cookie = new Cookie(CookieConstant.LOGIN_EMAIL, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        httpServletResponse.addCookie(cookie);

        String visitId = CookieUtils.getCookie(httpServletRequest, CookieConstant.VISIT_ID);

        template.opsForHash().put(CacheUtils.LOGIN_USER_COLLECTION, visitId, CacheUtils.UN_LOGIN_USER);
    }

    public RegisterDto register(String email, String password, String invitationCode) {
        User user = userDao.selectByEmail(email);
        if (user != null) {
            return RegisterDto.EMAIL_EXIST;
        }

        if (!checkInvitationCode(invitationCode)) {
            return RegisterDto.INVITATION_CODE_ERROR;
        }

        User registerUser = new User();
        registerUser.setNickName(email);
        registerUser.setEmail(email);
        registerUser.setLoginName(email);
        registerUser.setPassword(password);

        registerUser.setInvitationCode(RandomStringUtils.randomAlphanumeric(6));

        int row = userDao.insertSelective(registerUser);
        if (row <= 0) {
            return RegisterDto.DB_ERROR;
        }

        emailService.sendRegisterThanksEmail(email);

        return new RegisterDto(1, registerUser.getId());
    }

    public ResetPasswordDto resetPassword(String email) {
        User user = userDao.selectByEmail(email);
        if (user == null) {
            return ResetPasswordDto.EMAIL_NOT_EXIST;
        }

        String password = RandomStringUtils.randomAlphanumeric(8);
        user.setPassword(password);

        int row = userDao.updateByPrimaryKeySelective(user);

        if (row <= 0) {
            return ResetPasswordDto.DB_ERROR;
        }

        emailService.sendResetPasswordEmail(email, password);

        return new ResetPasswordDto(1, user.getId());
    }

    public boolean checkInvitationCode(String code) {
        User user = userDao.selectByInvitationCode(code);
        if (user != null) {
            return true;
        }

        return false;
    }

}
