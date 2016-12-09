package com.pcjinrong.pcjr.constant;

import com.pcjinrong.pcjr.bean.IndexFocusInfo;

public class Constant {

    public static boolean IS_LOGIN = false;
    public static boolean IS_GESTURE_LOGIN = false;

    public static int TYPE;
    public static String DEVICE_TOKEN;
    public static String REALNAME = "";
    public static IndexFocusInfo INDEX_FOCUS_INFO = null;

    public static final int REQUSET = 0;
    public static final int LOGOUT = 1;

    public static final int TYPE_ONGOING = 0;       //正在募集
    public static final int TYPE_SUCCESS = 1;       //募集成功
    public static final int TYPE_COMING = 2;        //即将开始
    public static final int TYPE_COUNTDOWN = 3;     //倒计时

    //手势密码点的状态
    public static final int POINT_STATE_NORMAL = 0;     // 正常状态
    public static final int POINT_STATE_SELECTED = 1;   // 按下状态
    public static final int POINT_STATE_WRONG = 2;      // 错误状态

    public static final String DEFAULT_TITLE = "活动";

    public static final String PRODUCT_NOTICE = "产品预告";
    public static final String PRODUCT_NOTICE_URL = "https://m.pcjr.com/notice";

    public static final String FORGET_PASSWORD = "忘记密码";
    public static final String FORGET_PASSWORD_URL = "https://m.pcjr.com/forgetpassword";

    public static final String USE_AGREEMENT = "使用协议";
    public static final String USE_AGREEMENT_URL = "https://m.pcjr.com/appdeal/use";

    public static final String PRIVACY_POLICY = "隐私条款";
    public static final String PRIVACY_POLICY_URL = "https://m.pcjr.com/appdeal/agreement";

    public static final String TIPS = "温馨提示";
    public static final String COUPONS_TIPS_URL = "https://m.pcjr.com/appdeal/coupons";
    public static final String REDPACKET_TIPS_URL = "https://m.pcjr.com/appdeal/redpacket";
    public static final String INTEREST_TIPS_URL = "https://m.pcjr.com/coupons/profittips";

    public static final String PLATFORM_ANNOUNCEMENT = "平台公告";
    public static final String PREPAYMENT_URL = "https://m.pcjr.com/platformnews/platformnewsdetail/index.html?id=87";
    public static final String PREPAYMENT_DEBX_URL = "https://m.pcjr.com/platformnews/platformnewsdetail?id=96";
    public static final String PLATFORM_NEWS_URL = "https://m.pcjr.com/platformnews";

    public static final String ABOUT_US = "关于我们";
    public static final String ABOUT_US_URL = "https://m.pcjr.com/aboutus";

    public static final String WITHDRAW_RULES = "提现细则";
    public static final String WITHDRAW_RULES_URL = "https://m.pcjr.com/appdeal/mention";

    public static final String SERVICE_AGREEMENT = "服务协议";
    public static final String SERVICE_AGREEMENT_URL = "https://m.pcjr.com/appdeal/rechargeag";

    public static final String CARD_EXPLAIN = "绑卡说明";
    public static final String CARD_EXPLAIN_URL = "https://m.pcjr.com/appdeal/bankintro";

    public static final String RISK_ASSESS = "风险评估";
    public static final String RISK_ASSESS_URL = "https://m.pcjr.com/riskassess";

    public static final String SHARE_URL = " https://m.pcjr.com";
    public static final String SHARE_LIST_URL = "https://m.pcjr.com/invest";
    public static final String SHARE_DETAIL_URL = "https://m.pcjr.com/productdetail/index?id=";
    public static final String SHARE_IMG_URL = "https://m.pcjr.com/lib/wap/images/tips/logo_256.png";
}
