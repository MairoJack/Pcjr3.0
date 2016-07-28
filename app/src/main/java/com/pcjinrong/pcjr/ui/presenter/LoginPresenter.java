package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.App;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.utils.SPUtils;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class LoginPresenter extends BasePresenter<MvpView<Token>> {

    public LoginPresenter() {
        long time = System.currentTimeMillis();
    }

    public void login(String username, String password) {
        this.mCompositeSubscription.add(this.mDataManager.getAccessToken(username, password)
                .subscribe(new Subscriber<Token>() {
                    @Override
                    public void onCompleted() {
                        if (LoginPresenter.this.mCompositeSubscription != null) {
                            LoginPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        LoginPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(Token token) {
                        SPUtils.putToken(App.getContext(),token);
                        if (LoginPresenter.this.getMvpView() != null)
                            LoginPresenter.this.getMvpView().onSuccess(token);
                    }
                }));
    }
}
