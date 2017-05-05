package cn.dubby.itbus.util;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
public class CacheUtils {

    /**
     * 分类前缀
     */
    private static final String BUS_LINE_PREFIX = "bus_line:";

    /**
     * 文章前缀
     */
    private static final String BUS_PREFIX = "bus:";

    /**
     * 登录态
     */
    public static final String LOGIN_USER_COLLECTION = "LOGIN_USER_COLLECTION";

    public static final String UN_LOGIN_USER = "UN_LOGIN_USER";

    /**
     * 文章
     *
     * @param busId
     * @return
     */
    public static String getBusCacheKey(int busId) {
        return BUS_PREFIX + "id" + busId;
    }

}
