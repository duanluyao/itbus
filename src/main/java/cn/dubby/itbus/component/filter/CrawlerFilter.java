package cn.dubby.itbus.component.filter;

import cn.dubby.itbus.Application;
import cn.dubby.itbus.constant.CookieConstant;
import cn.dubby.itbus.util.CacheUtils;
import cn.dubby.itbus.util.CookieUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
@WebFilter(
        urlPatterns = "/*"
)
public class CrawlerFilter implements Filter {

    private RedisTemplate<String, String> template;

    /** 限制每 VISIT_EXPIRE_MINUTES 分钟，最大访问次数 */
    private static final int MAX_VISIT_NUM = 20 * 10;

    /** 缓存有效期 */
    private static final int VISIT_EXPIRE_MINUTES = 10;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        template = Application.applicationContext.getBean("stringRedisTemplate", RedisTemplate.class);
        template.expire(CacheUtils.LOGIN_USER_COLLECTION, VISIT_EXPIRE_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        checkRedisTemplate();

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isNewGuest(httpServletRequest)) {
            addCookie(httpServletResponse);
            String userAgent = httpServletRequest.getHeader("User-Agent");
            if (StringUtils.isEmpty(userAgent) || userAgent.length() < 10) {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        } else {
            String visitId = CookieUtils.getCookie(httpServletRequest, CookieConstant.VISIT_ID);
            if (!checkVisitId(visitId)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean checkVisitId(String visitId) {
        Long result = template.opsForHash().increment(CacheUtils.LOGIN_USER_COLLECTION, visitId, 1L);

        if (result == null) {
            return true;
        }

        if (result != null && result <= MAX_VISIT_NUM) {
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
        cookie.setMaxAge(60 * 60 * 24 * 10);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        template.opsForHash().increment(CacheUtils.LOGIN_USER_COLLECTION, visitId, 1L);
    }

    private boolean isNewGuest(HttpServletRequest request) {
        if (StringUtils.isEmpty(CookieUtils.getCookie(request, CookieConstant.VISIT_ID))) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
