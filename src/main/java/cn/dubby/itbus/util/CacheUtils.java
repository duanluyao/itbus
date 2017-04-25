package cn.dubby.itbus.util;

/**
 * Created by yangzheng03 on 2017/4/23.
 */
public class CacheUtils {

    public static final String LOGIN_USER_COLLECTION = "login_user_collection";

    public static final String BUS_LINE_COLLECTION_KEY = "bus_line_collection_key";

    public static final String BUS_COLLECTION_KEY = "bus_collection_key";

    private static final String BUS_LINE_PREFIX = "bus_line_pre:";

    private static final String BUS_PREFIX = "bus_pre:";

    public static String getBusLineListCacheKey(int limit) {
        return BUS_LINE_PREFIX + "limit:" + limit;
    }

    public static String getBusListCacheKey(int limit) {
        return BUS_PREFIX + "limit:" + limit;
    }

    public static String getBusListByLineCacheKey(int lineId, int offset, int limit) {
        return BUS_PREFIX + "lineId:" + lineId + ":offset:" + offset + ":limit:" + limit;
    }

    public static String getBusCacheKey(int busId) {
        return BUS_PREFIX + "busId" + busId;
    }

}
