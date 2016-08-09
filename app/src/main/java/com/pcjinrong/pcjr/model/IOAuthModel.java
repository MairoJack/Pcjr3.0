package com.pcjinrong.pcjr.model;

import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.bean.TradeRecords;

import java.util.List;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public interface IOAuthModel {
    Observable<MemberIndex> getMemberIndex();

    Observable<FinanceRecords> getMemberFinanceData();

    Observable<BaseBean<List<InvestRecords>>> getInvestRecords(int type, int page, int page_size);

    Observable<BaseBean<List<TradeRecords>>> getTradeRecords(int type, int page, int page_size);

    Observable<BaseBean<List<PaymentPlan>>> getPaymentPlan(int year, int month);

    Observable<BaseBean<MobileInfo>> getMobileInfo();

    Observable<BaseBean<IdentityInfo>> getIdentityInfo();

    Observable<BaseBean> bindMobileVerify(String mobile);

    Observable<BaseBean> unBindMobileVerify();

    Observable<BaseBean> bindMobile(String mobile, String verify);

    Observable<BaseBean> unBindMobile(String verify);

    Observable<BaseBean> verifyIdentity(String real_name,String identity);

    Observable<BaseBean> changePassword(String old_password,String new_password);

    Observable<BaseBean<List<BankCard>>> getMemberBankCardInfo();

    Observable<BaseBean> addBankCard(String bank_id,String card_no,String real_name);

    Observable<BaseBean> delBankCard(String bank_id);

    Observable<BaseBean> revoke_access_token();
}
