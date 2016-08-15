package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.App;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.SplashView;
import com.pcjinrong.pcjr.utils.SPUtils;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class SplashPresenter extends BasePresenter<SplashView> {

    public SplashPresenter() {
    }

    public void refreshToken() {
        this.mCompositeSubscription.add(this.mDataManager.refreshToken((SPUtils.getToken(App.getContext()).getRefresh_token()))
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        if (SplashPresenter.this.mCompositeSubscription != null) {
                            SplashPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        SplashPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(Token data) {
                        if (SplashPresenter.this.getMvpView() != null)
                            SplashPresenter.this.getMvpView().onRefeshTokenSuccess(data);
                    }
                }));
    }

    public void getIndexFocusInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getIndexFocusInfo()
                .subscribe(new Subscriber<IndexFocusInfo>() {
                    @Override
                    public void onCompleted() {
                        if (SplashPresenter.this.mCompositeSubscription != null) {
                            SplashPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        SplashPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(IndexFocusInfo data) {
                        if (SplashPresenter.this.getMvpView() != null)
                            SplashPresenter.this.getMvpView().onGetIndexFocusSuccess(data);
                    }
                }));
    }
}
