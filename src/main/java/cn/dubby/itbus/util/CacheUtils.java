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
     * 文章
     * @param busId
     * @return
     */
    public static String getBusCacheKey(int busId) {
        return BUS_PREFIX + "id" + busId;
    }

}
