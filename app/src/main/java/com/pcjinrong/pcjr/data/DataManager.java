package com.pcjinrong.pcjr.data;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.App;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.ProductTradingRecord;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.model.impl.ApiModel;
import com.pcjinrong.pcjr.model.impl.OAuthModel;
import com.pcjinrong.pcjr.model.impl.PayModel;
import com.pcjinrong.pcjr.utils.RxUtils;
import com.pcjinrong.pcjr.utils.SPUtils;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.functions.Func1;


/**
 * Created by Mario on 2016/7/8.
 */
public class DataManager {

    private static DataManager dataManager;

    private ApiModel apiModel;
    private OAuthModel oAuthModel;
    private PayModel payModel;

    public synchronized static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    /*
     * -------------------------- ApiModel Start------------------------------
     */
    public Observable<Token> getAccessToken(String username, String password) {
        return this.apiModel.getAccessToken(username, password)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> register(String name, String password, String recommend_person) {
        return this.apiModel.register(name, password, recommend_person)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<Token> refreshToken(String refresh_token) {
        return this.apiModel.refreshToken(refresh_token)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<Product>>> getIndexProductList() {
        return this.apiModel.getIndexProductList()
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<IndexFocusInfo> getIndexFocusInfo() {
        return this.apiModel.getIndexFocusInfo()
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<Product>>> getInvestProductList(int type, int page, int page_size) {
        return this.apiModel.getInvestProductList(type, page, page_size)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<Product>> getProductDetail(String id) {
        return this.apiModel.getProductDetail(id)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<ProductTradingRecord>>> getProductTradingRecordList(String id, int page, int page_size) {
        return this.apiModel.getProductTradingRecordList(id, page, page_size)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<BankCard>>> getBankCardList() {
        return this.apiModel.getBankCardList()
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }
    /*
     * -------------------------- ApiModel Over ------------------------------
     */

    /*
     * -------------------------- OAuthModel Start------------------------------
     */
    public Observable<MemberIndex> getMemberIndex() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getMemberIndex())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<FinanceRecords> getMemberFinanceData() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getMemberFinanceData())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<InvestRecords>>> getInvestRecords(int type, int page, int page_size) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getInvestRecords(type, page, page_size))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<TradeRecords>>> getTradeRecords(int type, int page, int page_size) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getTradeRecords(type, page, page_size))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<PaymentPlan>>> getPaymentPlan(int year, int month) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getPaymentPlan(year, month))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<MobileInfo>> getMobileInfo() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getMobileInfo())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<IdentityInfo>> getIdentityInfo() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getIdentityInfo())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> revoke_access_token() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.revoke_access_token())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> bindMobileVerify(String mobile) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.bindMobileVerify(mobile))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> bindMobile(String mobile, String verify) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.bindMobile(mobile, verify))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> unBindMobileVerify() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.unBindMobileVerify())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> unBindMobile(String verify) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.unBindMobile(verify))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> verifyIdentity(String rela_name, String identity) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.verifyIdentity(rela_name, identity))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<BankCard>>> getBankCardInfo() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getMemberBankCardInfo())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<RechargeInfo>> getRechargeInfo() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getRechargeInfo())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> addBankCard(String bank_id, String card_no, String real_name) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.addBankCard(bank_id, card_no, real_name))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> delBankCard(String bank_id) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.delBankCard(bank_id))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> changePassword(String old_password, String new_password) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.changePassword(old_password, new_password))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<Letter>>> getLetterList(int page, int page_size) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getLetterList(page, page_size))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<Letter>> getLetterDetail(String id) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getLetterDetail(id))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> withdraw(String amount, String bank_id, String verify) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.withdraw(amount, bank_id, verify))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<Withdraw>> getWithdrawInvestInfo() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getWithdrawInvestInfo())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<Coupon> getUnusedCouponsNum() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getUnusedCouponsNum())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<RedPacket>>> getRedPacketList(int type, int page, int page_size) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getRedPacketList(type, page, page_size))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> getRedPacketReward(String id) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getRedPacketReward(id))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<List<InvestTicket>>> getInvestTicketList(int type, int page, int page_size) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getInvestTicketList(type, page, page_size))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean<InvestTicket>> getInvestTicketDetail(String id) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getInvestTicketDetail(id))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> investProduct(String amount, String id, String password) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.investProduct(amount, id, password))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> withdrawVerify() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.withdrawVerify())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> refreshDeviceToken(String device_token) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.refreshDeviceToken(device_token))
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    /*
     * -------------------------- OAuthModel Over ------------------------------
     */

    /*
     * -------------------------- PayModel Start------------------------------
     */
    public Observable<PayBean> bindCard(String memberID, String cardNo, String phone) {
        return this.payModel.bind_card(memberID, cardNo,phone)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<PayBean> confirmBindCard(String requestId, String validatecode) {
        return this.payModel.confirm_bind_card(requestId, validatecode)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<PayBean> pay(String memberID, String cardID,String amount) {
        return this.payModel.pay(memberID, cardID,amount)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<PayBean> sendVerifyCode(String memberID, String cardID ,String amount) {
        return this.payModel.pay(memberID, cardID,amount)
                .flatMap(new Func1<PayBean, Observable<PayBean>>() {
                    @Override
                    public Observable<PayBean> call(PayBean payResult) {
                        Observable<PayBean> o = payModel.send_verify_code(memberID,payResult.getOrder_no());
                        return o.map(new Func1<PayBean, PayBean>() {
                            @Override
                            public PayBean call(PayBean payBean) {
                                payBean.setOrder_no(payResult.getOrder_no());
                                return payBean;
                            }
                        });
                    }
                })
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<PayBean> confirmPay(String memberID, String orderNo, String validateCode) {
        return this.payModel.confirm_pay(memberID, orderNo, validateCode)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<PayBean> checkCard(String cardNo) {
        return this.payModel.check_card(cardNo)
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    /*
     * -------------------------- ApiModel Over ------------------------------
     */


    private DataManager() {
        this.apiModel = ApiModel.getInstance();
        this.oAuthModel = OAuthModel.getInstance();
        this.payModel = PayModel.getInstance();
    }


    public class RetryWithUnAuth implements Func1<Observable<? extends Throwable>, Observable<?>> {

        private int retryCount;

        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable.flatMap(throwable -> {
                if (throwable instanceof HttpException && ++retryCount <= ApiConstant.MAX_RETRY_COUNT) {
                    return apiModel.refreshToken(SPUtils.getToken(App.getContext()).getRefresh_token())
                            .doOnNext(token -> {
                                SPUtils.putToken(App.getContext(), token);
                            })
                            .doOnError(e -> {
                                Logger.e(e.getMessage());
                                SPUtils.clear(App.getContext());

                            });
                }
                return Observable.error(throwable);
            });
        }
    }
}
