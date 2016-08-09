package com.pcjinrong.pcjr.model.impl;

import com.pcjinrong.pcjr.api.RetrofitManager;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.bean.TradeRecords;
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
        return RetrofitManager.getInstance().getAuthService().bindMobile(mobile,verify);
    }

    @Override
    public Observable<BaseBean> unBindMobile(String verify) {
       return RetrofitManager.getInstance().getAuthService().unBindMobile(verify);
    }

    @Override
    public Observable<BaseBean> verifyIdentity(String rela_name,String identity) {
        return RetrofitManager.getInstance().getAuthService().verifyIdentity(rela_name,identity);
    }

    @Override
    public Observable<BaseBean> changePassword(String old_password,String new_password) {
        return RetrofitManager.getInstance().getAuthService().changePassword(old_password,new_password);
    }

    @Override
    public Observable<BaseBean<List<BankCard>>> getMemberBankCardInfo() {
        return RetrofitManager.getInstance().getAuthService().getMemberBankCardInfo();
    }

    @Override
    public Observable<BaseBean> addBankCard(String bank_id, String card_no, String real_name) {
        return RetrofitManager.getInstance().getAuthService().addBankCard(bank_id,card_no,real_name);
    }

    @Override
    public Observable<BaseBean> delBankCard(String bank_id) {
        return RetrofitManager.getInstance().getAuthService().delBankCard(bank_id);
    }

    @Override
    public Observable<BaseBean> revoke_access_token() {
        return RetrofitManager.getInstance().getAuthService().revoke_access_token();
    }



}
