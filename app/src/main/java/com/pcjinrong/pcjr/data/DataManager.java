package com.pcjinrong.pcjr.data;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.App;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.model.impl.ApiModel;
import com.pcjinrong.pcjr.model.impl.OAuthModel;
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

    public Observable<BaseBean<List<BankCard>>> getBankCardList() {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.getMemberBankCardInfo())
                .retryWhen(new RetryWithUnAuth())
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    public Observable<BaseBean> addBankCard(String bank_id,String card_no,String real_name) {
        return Observable.just(null)
                .flatMap(o -> oAuthModel.addBankCard(bank_id, card_no,real_name))
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
    /*
     * -------------------------- OAuthModel Over ------------------------------
     */
    private DataManager() {
        this.apiModel = ApiModel.getInstance();
        this.oAuthModel = OAuthModel.getInstance();
    }


    public class RetryWithUnAuth implements Func1<Observable<? extends Throwable>, Observable<?>> {

        private int retryCount;

        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable.flatMap(throwable -> {
                if (throwable instanceof HttpException && ++retryCount <= ApiConstant.MAX_RETRY_COUNT) {
                    return apiModel.refreshToken(SPUtils.getToken(App.getContext()).getRefresh_token())
                            .doOnNext(token -> {
                                Logger.d("refreshToken:" + token);
                                SPUtils.putToken(App.getContext(), token);
                            })
                            .doOnError(e -> {
                                SPUtils.clear(App.getContext());
                                Logger.d("refreshToken:" + e.getMessage());
                            });
                }
                return Observable.error(throwable);
            });
        }
    }
}
