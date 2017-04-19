package com.pcjinrong.pcjr.model.impl;

import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.api.RetrofitManager;
import com.pcjinrong.pcjr.bean.AvailableInterest;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.InvestProductDetail;
import com.pcjinrong.pcjr.bean.InvestProductRepaymentInfo;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.bean.ListBean;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.bean.PaymentRecords;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.bean.RiskAssessmentScore;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.model.IOAuthModel;

import java.util.List;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public class OAuthModel implements IOAuthModel {

    private static final OAuthModel mInstance = new OAuthModel();


    public static OAuthModel getInstance() {
        return mInstance;
    }


    private OAuthModel() {
    }


    @Override
    public Observable<MemberIndex> getMemberIndex() {
        return RetrofitManager.getInstance().getAuthService().getMemberIndex();
    }

    @Override
    public Observable<FinanceRecords> getMemberFinanceData() {
        return RetrofitManager.getInstance().getAuthService().getMemberFinanceData();
    }

    @Override
    public Observable<BaseBean<List<InvestRecords>>> getInvestRecords(int type, int page, int page_size) {
        return RetrofitManager.getInstance().getAuthService().getMemberInvestData(type, page, page_size);
    }

    @Override
    public Observable<BaseBean<List<TradeRecords>>> getTradeRecords(int type, int page, int page_size) {
        return RetrofitManager.getInstance().getAuthService().getMemberLogData(type, page, page_size);
    }

    @Override
    public Observable<BaseBean<List<PaymentPlan>>> getPaymentPlan(int year, int month) {
        return RetrofitManager.getInstance().getAuthService().getMemberRepaymentData(year, month);
    }

    @Override
    public Observable<BaseBean<MobileInfo>> getMobileInfo() {
        return RetrofitManager.getInstance().getAuthService().getMobileInfo();
    }

    @Override
    public Observable<BaseBean<IdentityInfo>> getIdentityInfo() {
        return RetrofitManager.getInstance().getAuthService().getMemberIdentityInfo();
    }

    @Override
    public Observable<BaseBean> bindMobileVerify(String mobile) {
        return RetrofitManager.getInstance().getAuthService().bindMobileVerify(mobile);
    }

    @Override
    public Observable<BaseBean> unBindMobileVerify() {
        return RetrofitManager.getInstance().getAuthService().unBindMobileVerify();
    }

    @Override
    public Observable<BaseBean> bindMobile(String mobile, String verify) {
        return RetrofitManager.getInstance().getAuthService().bindMobile(mobile, verify);
    }

    @Override
    public Observable<BaseBean> unBindMobile(String verify) {
        return RetrofitManager.getInstance().getAuthService().unBindMobile(verify);
    }

    @Override
    public Observable<BaseBean> verifyIdentity(String rela_name, String identity) {
        return RetrofitManager.getInstance().getAuthService().verifyIdentity(rela_name, identity);
    }

    @Override
    public Observable<BaseBean> changePassword(String old_password, String new_password) {
        return RetrofitManager.getInstance().getAuthService().changePassword(old_password, new_password);
    }

    @Override
    public Observable<BaseBean<List<BankCard>>> getMemberBankCardInfo() {
        return RetrofitManager.getInstance().getAuthService().getMemberBankCardInfo();
    }

    @Override
    public Observable<BaseBean<RechargeInfo>> getRechargeInfo() {
        return RetrofitManager.getInstance().getAuthService().getRechargeInfo(ApiConstant.ACCEPT);
    }

    @Override
    public Observable<BaseBean> addBankCard(String bank_id, String card_no, String real_name) {
        return RetrofitManager.getInstance().getAuthService().addBankCard(bank_id, card_no, real_name);
    }

    @Override
    public Observable<BaseBean> delBankCard(String bank_id) {
        return RetrofitManager.getInstance().getAuthService().delBankCard(bank_id);
    }

    @Override
    public Observable<BaseBean<List<Letter>>> getLetterList(int page, int page_size) {
        return RetrofitManager.getInstance().getAuthService().getLetterList(page, page_size);
    }

    @Override
    public Observable<BaseBean<Letter>> getLetterDetail(String id) {
        return RetrofitManager.getInstance().getAuthService().getLetterDetail(id);
    }

    @Override
    public Observable<BaseBean<Withdraw>> getWithdrawInvestInfo() {
        return RetrofitManager.getInstance().getAuthService().getWithdrawInvestInfo();
    }

    @Override
    public Observable<BaseBean> withdrawVerify() {
        return RetrofitManager.getInstance().getAuthService().withdrawVerify();
    }

    @Override
    public Observable<Coupon> getUnusedCouponsNum() {
        return RetrofitManager.getInstance().getAuthService().getUnusedCouponsNum();
    }

    @Override
    public Observable<BaseBean<List<InvestTicket>>> getInvestTicketList(int type, int page, int page_size) {
        return RetrofitManager.getInstance().getAuthService().getInvestTicketList(type,page,page_size);
    }

    @Override
    public Observable<BaseBean<InvestTicket>> getInvestTicketDetail(String id) {
        return RetrofitManager.getInstance().getAuthService().getInvestTicketDetail(id);
    }

    @Override
    public Observable<BaseBean<List<RedPacket>>> getRedPacketList(int type, int page, int page_size) {
        return RetrofitManager.getInstance().getAuthService().getRedPacketList(type,page,page_size);
    }

    @Override
    public Observable<BaseBean> getRedPacketReward(String id) {
        return RetrofitManager.getInstance().getAuthService().getRedPacketReward(id);
    }

    @Override
    public Observable<BaseBean> withdraw(String amount, String bank_id, String verify) {
        return RetrofitManager.getInstance().getAuthService().withdraw(amount, bank_id, verify);
    }

    @Override
    public Observable<BaseBean> investProduct(String amount, String id, String password,String interestTicketId) {
        return RetrofitManager.getInstance().getAuthService().investProduct(amount,id,password,interestTicketId);
    }

    @Override
    public Observable<BaseBean> revoke_access_token() {
        return RetrofitManager.getInstance().getAuthService().revoke_access_token();
    }

    @Override
    public Observable<BaseBean> refreshDeviceToken(String device_token) {
        return RetrofitManager.getInstance().getAuthService().refreshDeviceToken(device_token);
    }

    @Override
    public Observable<BaseBean<List<InterestTicket>>> getAvailableInterestTicketList() {
        return RetrofitManager.getInstance().getAuthService().getAvailableInterestTicketList();
    }

    @Override
    public Observable<BaseBean<List<InterestTicket>>> getInterestTicketList(int type, int page, int page_size) {
        return RetrofitManager.getInstance().getAuthService().getInterestTicketList(type,page,page_size);
    }

    @Override
    public Observable<BaseBean<InterestTicket>> getInterestTicketDetail(String id) {
        return RetrofitManager.getInstance().getAuthService().getInterestTicketDetail(id);
    }

    @Override
    public Observable<RiskAssessmentScore> getRiskAssessmentScore() {
        return RetrofitManager.getInstance().getAuthService().getRiskAssessmentScore();
    }

    @Override
    public Observable<BaseBean<InvestProductDetail>> getInvestProductDetail(String id) {
        return RetrofitManager.getInstance().getAuthService().getInvestProductDetail(id);
    }

    @Override
    public Observable<BaseBean<List<InvestProductRepaymentInfo>>> getInvestProductRepaymentInfo(String id) {
        return RetrofitManager.getInstance().getAuthService().getInvestProductRepaymentInfo(id);
    }


}
