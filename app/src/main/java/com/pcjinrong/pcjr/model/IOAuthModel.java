package com.pcjinrong.pcjr.model;

import com.nostra13.universalimageloader.utils.L;
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
import com.pcjinrong.pcjr.bean.PaymentRecords;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.bean.RiskAssessmentScore;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.bean.Withdraw;

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

    Observable<BaseBean<RechargeInfo>> getRechargeInfo();

    Observable<BaseBean> addBankCard(String bank_id,String card_no,String real_name);

    Observable<BaseBean> delBankCard(String bank_id);

    Observable<BaseBean<List<Letter>>> getLetterList(int page, int page_size);

    Observable<BaseBean<Letter>> getLetterDetail(String id);

    Observable<BaseBean<Withdraw>> getWithdrawInvestInfo();

    Observable<BaseBean> withdrawVerify();

    Observable<Coupon> getUnusedCouponsNum();

    Observable<BaseBean<List<InvestTicket>>> getInvestTicketList(int type, int page, int page_size);

    Observable<BaseBean<InvestTicket>> getInvestTicketDetail(String id);

    Observable<BaseBean<List<RedPacket>>> getRedPacketList(int type, int page, int page_size);

    Observable<BaseBean> getRedPacketReward(String id);

    Observable<BaseBean> withdraw(String amount, String bank_id,String verify);

    Observable<BaseBean> investProduct(String amount, String id,String password,String interestTicketId);

    Observable<BaseBean> revoke_access_token();

    Observable<BaseBean> refreshDeviceToken(String device_token);

    Observable<BaseBean<List<InterestTicket>>> getAvailableInterestTicketList();

    Observable<BaseBean<List<InterestTicket>>> getInterestTicketList(int type, int page, int page_size);

    Observable<BaseBean<InterestTicket>> getInterestTicketDetail(String id);

    Observable<RiskAssessmentScore> getRiskAssessmentScore();

    Observable<BaseBean<List<PaymentRecords>>> getPaymentRecords(String id);
}
