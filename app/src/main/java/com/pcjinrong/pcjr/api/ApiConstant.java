package com.pcjinrong.pcjr.api;

/**
 * Created by Mario on 2016/7/8.
 */
public class ApiConstant {

    /**
     * api接口
     */
    public final static String BASE_URL = "http://api.pcjr.test/mapi/";
    public final static String CLIENT_ID = "1";
    public final static String CLIENT_SECRET = "123";
    public final static String BEARER = "Bearer";
    public final static String ACCEPT = "application/x.pcjr.v2+json";
    public final static String SUFFIX = "pcjrjsb";

    /**
     * 快捷支付接口
     */
    public final static String PAY_URL = "http://paytest.pcjr.com/yibao/quick/";

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_RETRY_COUNT = 2;
    public static final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";


}
