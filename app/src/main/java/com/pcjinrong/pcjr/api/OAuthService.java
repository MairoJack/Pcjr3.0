package com.pcjinrong.pcjr.api;

import com.google.gson.JsonObject;
import com.pcjinrong.pcjr.bean.AvailableInterest;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.bean.ListBean;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.bean.RiskAssessmentScore;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.bean.Withdraw;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 需要auth认证 api
 * Created by Mario on 2016/7/25.
 */
public interface OAuthService {
    /**
     * 用户登出
     *
     * @return
     */
    @POST("oauth/revoke_access_token")
    Observable<BaseBean> revoke_access_token();

    /**
     * 获取用户中心首页数据
     *
     * @return
     */
    @GET("member/index_data")
    Observable<MemberIndex> getMemberIndex();


    /**
     * 获取用户交易记录
     *
     * @return
     */
    @GET("member/log_data")
    Observable<BaseBean<List<TradeRecords>>> getMemberLogData(@Query("type") int type, @Query("page") int page, @Query("page_size") int page_size);

    /**
     * 获取用户投资记录
     *
     * @return
     */
    @GET("member/invest_data")
    Observable<BaseBean<List<InvestRecords>>> getMemberInvestData(@Query("type") int type, @Query("page") int page, @Query("page_size") int page_size);

    /**
     * 获取用户资金记录
     *
     * @return
     */
    @GET("member/finance_data")
    Observable<FinanceRecords> getMemberFinanceData();

    /**
     * 获取用户回款计划
     *
     * @param year         年
     * @param month        月
     * @return
     */
    @GET("member/current_month_repayment_data")
    Observable<BaseBean<List<PaymentPlan>>> getMemberRepaymentData(@Query("year") int year, @Query("month") int month);

    /**
     * 实名认证
     *
     * @return
     */
    @FormUrlEncoded
    @POST("member/verify_identity")
    Observable<BaseBean> verifyIdentity(@Field("real_name") String real_name, @Field("identity") String identity);

    /**
     * 获取实名认证信息
     *
     * @return
     */
    @GET("member/identity_info")
    Observable<BaseBean<IdentityInfo>> getMemberIdentityInfo();

    /**
     * 获取用户银行卡信息v1
     *
     */
    @GET("member/bank_card_info")
    Observable<BaseBean<List<BankCard>>> getMemberBankCardInfo();

    /**
     * 获取用户银行卡信息v2
     *
     */
    @GET("member/bank_card_info")
    Observable<BaseBean<RechargeInfo>> getRechargeInfo(@Header("Accept") String header);

    /**
     * 添加银行卡
     *
     * @param bank_id   银行ID
     * @param card_no   银行卡号
     * @param real_name 真实姓名
     * @return
     */
    @FormUrlEncoded
    @POST("member/add_bank_card")
    Observable<BaseBean> addBankCard(@Field("bank_id") String bank_id, @Field("card_no") String card_no, @Field("real_name") String real_name);

    /**
     * 删除银行卡
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("member/del_bank_card")
    Observable<BaseBean> delBankCard(@Field("id") String id);

    /**
     * 发送绑定验证码
     *
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("api/bind_mobile_verify")
    Observable<BaseBean> bindMobileVerify( @Field("mobile") String mobile);

    /**
     * 发送解绑验证码
     *
     * @return
     */
    @POST("api/unbind_mobile_verify")
    Observable<BaseBean> unBindMobileVerify();

    /**
     * 绑定手机
     *
     * @param mobile
     * @param verify
     * @return
     */
    @FormUrlEncoded
    @POST("member/bind_mobile")
    Observable<BaseBean> bindMobile(@Field("mobile") String mobile, @Field("verify") String verify);

    /**
     * 解绑手机
     *
     * @param verify
     * @return
     */
    @FormUrlEncoded
    @POST("member/unbind_mobile")
    Observable<BaseBean> unBindMobile(@Field("verify") String verify);

    /**
     * 获取用户手机信息
     *
     * @return
     */
    @GET("member/mobile_info")
    Observable<BaseBean<MobileInfo>> getMobileInfo();

    /**
     * 修改密码
     *
     * @param old_password
     * @param new_password
     * @return
     */
    @FormUrlEncoded
    @POST("member/change_password")
    Observable<BaseBean> changePassword(@Field("old_password") String old_password, @Field("new_password") String new_password);

    /**
     * 获取站内信列表
     *
     * @param page
     * @return
     */
    @GET("member/letter_list")
    Observable<BaseBean<List<Letter>>> getLetterList(@Query("page") int page, @Query("page_size") int page_size);

    /**
     * 获取站内信详情
     *
     * @param id
     * @return
     */
    @GET("member/letter_detail")
    Observable<BaseBean<Letter>> getLetterDetail(@Query("id") String id);

    /**
     * 获取用户未使用的优惠券数量
     *
     * @return
     */
    @GET("member/unused_coupons_num")
    Observable<Coupon> getUnusedCouponsNum();

    /**
     * 获取用户投资券列表
     *
     * @param type         0 未使用; 1 已使用; 2 已过期;
     * @param page
     * @return
     */
    @GET("member/invest_ticket_list")
    Observable<BaseBean<List<InvestTicket>>> getInvestTicketList(@Query("type") int type, @Query("page") int page, @Query("page_size") int pageSize);

    /**
     * 获取用户投资券详情
     *
     * @param id
     * @return
     */
    @GET("member/invest_ticket_detail")
    Observable<BaseBean<InvestTicket>> getInvestTicketDetail(@Query("id") String id);

    /**
     * 获取红包信息
     * @param type     0 未领取; 1 已领取;
     * @param page
     * @return
     */
    @GET("member/red_packet_list")
    Observable<BaseBean<List<RedPacket>>> getRedPacketList(@Query("type") int type, @Query("page") int page, @Query("page_size") int pageSize);

    /**
     * 获取用户提现/投资信息
     *
     * @return
     */
    @GET("member/withdraw_invest_info")
    Observable<BaseBean<Withdraw>> getWithdrawInvestInfo();

    /**
     * 用户提现
     *
     * @param amount  提现金额
     * @param bank_id 银行ID
     * @param verify  验证码
     * @return
     */
    @FormUrlEncoded
    @POST("member/withdraw")
    Observable<BaseBean> withdraw(@Field("amount") String amount, @Field("bank_id") String bank_id, @Field("verify") String verify);

    /**
     * 发送提现验证码
     *
     * @return
     */
    @POST("api/withdraw_verify")
    Observable<BaseBean> withdrawVerify();

    /**
     * 投资
     *
     * @param amount       投资金额
     * @param id           产品ID
     * @param password     密码
     * @param interestTicketId     加息券ID
     * @return
     */
    @FormUrlEncoded
    @POST("member/invest_product")
    Observable<BaseBean> investProduct(@Field("amount") String amount, @Field("id") String id, @Field("password") String password, @Field("interest_ticket_id") String interestTicketId);

    /**
     * 领取红包
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("member/get_red_packet_reward")
    Observable<BaseBean> getRedPacketReward(@Field("id")  String id);

    /**
     * 绑定设备
     *
     * @param device_token 设备号
     * @return
     */
    @FormUrlEncoded
    @POST("member/refresh_device_token")
    Observable<BaseBean> refreshDeviceToken(@Field("device_token") String device_token);

    /**
     * 获取当前可用加息券列表
     *
     * @return
     */
    @GET("member/available_interest_ticket_list")
    Observable<BaseBean<List<InterestTicket>>> getAvailableInterestTicketList();


    /**
     * 获取用户加息券列表
     *
     * @param type         0 未使用; 1 已使用; 2 已过期;
     * @param page
     * @return
     */
    @GET("member/interest_ticket_list")
    Observable<BaseBean<List<InterestTicket>>> getInterestTicketList(@Query("type") int type, @Query("page") int page, @Query("page_size") int pageSize);

    /**
     * 获取用户加息券详情
     *
     * @param id
     * @return
     */
    @GET("member/interest_ticket_detail")
    Observable<BaseBean<InterestTicket>> getInterestTicketDetail(@Query("id") String id);

    /**
     * 获取测评分数
     *
     * @return
     */
    @GET("member/risk_assessment_score")
    Observable<RiskAssessmentScore> getRiskAssessmentScore();
}
