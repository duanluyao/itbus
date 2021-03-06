package cn.dubby.itbus.component.filter;

import cn.dubby.itbus.Application;
import cn.dubby.itbus.bean.User;
import cn.dubby.itbus.constant.CookieConstant;
import cn.dubby.itbus.constant.HttpRequestConstant;
import cn.dubby.itbus.util.CacheUtils;
import cn.dubby.itbus.util.CookieUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by teeyoung on 17/5/5.
 */
@WebFilter(
        urlPatterns = "/*"
)
public class LoginFilter implements Filter {

    private RedisTemplate<String, String> template;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        template = Application.applicationContext.getBean("stringRedisTemplate", RedisTemplate.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        checkRedisTemplate();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isNewGuest(httpServletRequest)) {
            deleteEmailCookie(httpServletResponse);
            addCookie(httpServletResponse);
            String userAgent = httpServletRequest.getHeader("User-Agent");
            if (StringUtils.isEmpty(userAgent) || userAgent.length() < 10) {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            String visitId = CookieUtils.getCookie(httpServletRequest, CookieConstant.VISIT_ID);
            if (checkVisitId(visitId, httpServletRequest, httpServletResponse)) {
                chain.doFilter(request, response);
                return;
            } else {
                deleteCookie(httpServletResponse);
            }
        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }


    private boolean checkVisitId(String visitId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Object result = template.opsForHash().get(CacheUtils.LOGIN_USER_COLLECTION, visitId);
        if (result != null) {
            if (CacheUtils.UN_LOGIN_USER.equals(result)) {
                return true;
            }
            User user = JSON.parseObject(result.toString(), User.class);
            httpServletRequest.setAttribute(HttpRequestConstant.LOGIN_USER, user);

            Cookie cookie = new Cookie(CookieConstant.LOGIN_EMAIL, user.getEmail());
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            httpServletResponse.addCookie(cookie);
            return true;
        }
        return false;
    }


    private void checkRedisTemplate() {
        if (template == null) {
            template = Application.applicationContext.getBean("stringRedisTemplate", RedisTemplate.class);
        }
    }

    private void addCookie(HttpServletResponse response) {
        String visitId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(CookieConstant.VISIT_ID, visitId);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        template.opsForHash().put(CacheUtils.LOGIN_USER_COLLECTION, visitId, CacheUtils.UN_LOGIN_USER);
    }

    private void deleteCookie(HttpServletResponse response) {
        String visitId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie(CookieConstant.VISIT_ID, visitId);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        deleteEmailCookie(response);
    }

    private void deleteEmailCookie(HttpServletResponse response) {
        Cookie loginEmail = new Cookie(CookieConstant.LOGIN_EMAIL, "");
        loginEmail.setMaxAge(0);
        loginEmail.setPath("/");
        loginEmail.setHttpOnly(false);
        response.addCookie(loginEmail);
    }

    private boolean isNewGuest(HttpServletRequest request) {
        if (StringUtils.isEmpty(CookieUtils.getCookie(request, CookieConstant.VISIT_ID))) {
            return true;
        }

        return false;
    }

}
