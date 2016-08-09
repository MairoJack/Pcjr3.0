package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.BindMobileView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class BindMobilePresenter extends BasePresenter<BindMobileView> {

    public BindMobilePresenter() {
        long time = System.currentTimeMillis();
    }


    public void bindMobileVerify(String mobile) {
        this.mCompositeSubscription.add(this.mDataManager.bindMobileVerify(mobile)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (BindMobilePresenter.this.mCompositeSubscription != null) {
                            BindMobilePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BindMobilePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (BindMobilePresenter.this.getMvpView() != null)
                            BindMobilePresenter.this.getMvpView().onBindMobileVerifySuccess(data);
                    }
                }));
    }

    public void bindMobile(String mobile,String verify) {
        this.mCompositeSubscription.add(this.mDataManager.bindMobile(mobile,verify)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (BindMobilePresenter.this.mCompositeSubscription != null) {
                            BindMobilePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BindMobilePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (BindMobilePresenter.this.getMvpView() != null)
                            BindMobilePresenter.this.getMvpView().onBindMobileSuccess(data);
                    }
                }));
    }

    public void unBindMobileVerify() {
        this.mCompositeSubscription.add(this.mDataManager.unBindMobileVerify()
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (BindMobilePresenter.this.mCompositeSubscription != null) {
                            BindMobilePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BindMobilePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (BindMobilePresenter.this.getMvpView() != null)
                            BindMobilePresenter.this.getMvpView().onUnBindMobileVerifySuccess(data);
                    }
                }));
    }

    public void unBindMobile(String verify) {
        this.mCompositeSubscription.add(this.mDataManager.unBindMobile(verify)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (BindMobilePresenter.this.mCompositeSubscription != null) {
                            BindMobilePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BindMobilePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (BindMobilePresenter.this.getMvpView() != null)
                            BindMobilePresenter.this.getMvpView().onUnBindMobileSuccess(data);
                    }
                }));
    }
}
