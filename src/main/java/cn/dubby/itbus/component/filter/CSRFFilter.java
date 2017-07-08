package cn.dubby.itbus.component.filter;

import cn.dubby.itbus.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by teeyoung on 17/7/8.
 */
@WebFilter(
        urlPatterns = "/*"
)
public class CSRFFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger("csrf");

    private Set<String> HOST_SET = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        RedisTemplate<String, String> template = Application.applicationContext.getBean("stringRedisTemplate", RedisTemplate.class);
        String hostSet = template.opsForValue().get("host_set");
        String[] hostArray = hostSet.split("\\|");
        for (String str : hostArray) {
            HOST_SET.add(str.trim());
        }

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (checkRefer(httpServletRequest)) {
            //请求合法
            chain.doFilter(request, response);
        } else {
            logger.info(httpServletRequest.getHeader("Referer") + ":" + httpServletRequest.getRequestURI());
        }
    }

    private boolean checkRefer(HttpServletRequest httpServletRequest) {
        String refer = httpServletRequest.getHeader("Referer");
        try {
            URL url = new URL(refer);
            for (String host : HOST_SET) {
                if (url.getHost().endsWith(host)) {
                    return true;
                }
            }
        } catch (MalformedURLException e) {
            logger.error("csrf refer error", refer + ":" + httpServletRequest.getRequestURI());
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
