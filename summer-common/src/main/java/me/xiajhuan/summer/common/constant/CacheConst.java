package me.xiajhuan.summer.common.constant;

/**
 * 缓存常量
 *
 * @author xiajhuan
 * @date 2022/11/22
 */
public class CacheConst {

    //*******************缓存类型********************

    /**
     * 缓存类型：堆内缓存
     */
    public static final String HEAP = "HEAP";

    /**
     * 缓存类型：Redis
     */
    public static final String REDIS = "REDIS";

    //*******************缓存值类型********************

    /**
     * 缓存值类型：String
     */
    public static final String VALUE_STRING = "STRING";

    /**
     * 缓存值类型：Auto
     */
    public static final String VALUE_AUTO = "AUTO";

    /**
     * 缓存值类型：Hash
     */
    public static final String VALUE_HASH = "HASH";

    /**
     * 缓存值类型：List
     */
    public static final String VALUE_LIST = "LIST";

    //*******************缓存Key********************

    /**
     * Excel数据 Key
     */
    public static final String EXCEL_DATA = "EXCEL_DATA_{}";

    /**
     * 验证码 Key
     */
    public static final String CAPTCHA_CODE = "CAPTCHA_CODE_{}";

}
