package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Empty;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.utils.DateUtils;
import java.util.Date;
import retrofit2.Response;
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
                        if (LoginPresenter.this.getMvpView() != null)
                            LoginPresenter.this.getMvpView().onSuccess(token);
                    }
                }));
    }

    public void refreshDeviceToken(String device_token) {
        this.mCompositeSubscription.add(this.mDataManager.refreshDeviceToken(device_token)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (LoginPresenter.this.mCompositeSubscription != null) {
                            LoginPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        Logger.i(data.getMessage());
                    }
                }));
    }

    public void getCurrentTime() {
        this.mCompositeSubscription.add(this.mDataManager.getCurrentTime()
                .subscribe(new Subscriber<Response<Empty>>() {
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
                    public void onNext(Response<Empty> response) {
                        if (LoginPresenter.this.getMvpView() != null) {
                            Date date = response.headers().getDate("date");
                            Logger.d("date:" + date.getTime());
                            Logger.d("responseBody:" + DateUtils.dateTimeToStr(date, "yyyy-MM-dd HH:mm:ss"));
                        }
                        //LoginPresenter.this.getMvpView().onSuccess(token);
                    }
                }));
    }
}
