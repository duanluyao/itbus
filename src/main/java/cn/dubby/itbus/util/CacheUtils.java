package cn.dubby.itbus.util;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
public class CacheUtils {

    /**
     * 用户访问次数
     */
    public static final String LOGIN_USER_COLLECTION = "login_user_collection";

    /**
     * 分类相关key的集合
     */
    public static final String BUS_LINE_COLLECTION_KEY = "bus_line_collection_key";

    /**
     * 文章相关key的集合
     */
    public static final String BUS_COLLECTION_KEY = "bus_collection_key";

    /**
     * 分类前缀
     */
    private static final String BUS_LINE_PREFIX = "bus_line_pre:";

    /**
     * 文章前缀
     */
    private static final String BUS_PREFIX = "bus_pre:";

    /**
     * 分类 TOP N
     * @param limit
     * @return
     */
    public static String getBusLineListCacheKey(int limit) {
        return BUS_LINE_PREFIX + "limit:" + limit;
    }

    /**
     * 文章 TOP N
     * @param limit
     * @return
     */
    public static String getBusListCacheKey(int limit) {
        return BUS_PREFIX + "limit:" + limit;
    }

    /**
     * 文章
     * @param busId
     * @return
     */
    public static String getBusCacheKey(int busId) {
        return BUS_PREFIX + "busId" + busId;
    }

}
