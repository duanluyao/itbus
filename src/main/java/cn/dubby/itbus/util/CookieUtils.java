package cn.dubby.itbus.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangzheng03 on 2017/4/25.
 */
public class CookieUtils {
    public static String getCookie(HttpServletRequest httpServletRequest, String cookieName) {
        Cookie[] cookieList = httpServletRequest.getCookies();

        if (cookieList == null || cookieList.length <= 0) {
            return null;
        }

        for (Cookie cookie : cookieList) {
            if (cookieName.equals(cookie.getName())) {
                String cookieValue = cookie.getValue();
                return cookieValue;
            }
        }

        return null;
    }
}
