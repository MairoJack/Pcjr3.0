package com.pcjinrong.pcjr.data;


import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.App;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.model.impl.ApiModel;
import com.pcjinrong.pcjr.model.impl.OAuthModel;
import com.pcjinrong.pcjr.utils.RxUtils;
import com.pcjinrong.pcjr.utils.SPUtils;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.functions.Action1;
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
                .flatMap(o -> oAuthModel.getMemberIndex(SPUtils.getToken(App.getContext()).getAccess_token()))
                .retryWhen(observable ->
                        observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (throwable instanceof HttpException || throwable instanceof NullPointerException) {
                                    return apiModel.refreshToken(SPUtils.getToken(App.getContext()).getRefresh_token())
                                            .doOnNext(token -> {
                                                Logger.d("refreshToken:" + token);
                                                SPUtils.putToken(App.getContext(), token);
                                            })
                                            .doOnError(e -> Logger.d("refreshToken:" + e.getMessage()));
                                }
                                return Observable.error(throwable);
                            }


                        }))
                .compose(RxUtils.applyIOToMainThreadSchedulers());
    }

    /*
     * -------------------------- OAuthModel Over ------------------------------
     */
    private DataManager() {
        this.apiModel = ApiModel.getInstance();
        this.oAuthModel = OAuthModel.getInstance();
    }

}
