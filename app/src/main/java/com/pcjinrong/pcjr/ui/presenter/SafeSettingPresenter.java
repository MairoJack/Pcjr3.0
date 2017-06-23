package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.RiskAssessmentScore;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.SafeSettingView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class SafeSettingPresenter extends BasePresenter<SafeSettingView> {

    public SafeSettingPresenter() {
        long time = System.currentTimeMillis();
    }

    public void getMobileInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getMobileInfo()
                .subscribe(new Subscriber<BaseBean<MobileInfo>>() {
                    @Override
                    public void onCompleted() {
                        if (SafeSettingPresenter.this.mCompositeSubscription != null) {
                            SafeSettingPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        SafeSettingPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<MobileInfo> data) {
                        if (SafeSettingPresenter.this.getMvpView() != null)
                            SafeSettingPresenter.this.getMvpView().onMobileInfoSuccess(data);
                    }
                }));
    }

    public void getIdentityInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getIdentityInfo()
                .subscribe(new Subscriber<BaseBean<IdentityInfo>>() {
                    @Override
                    public void onCompleted() {
                        if (SafeSettingPresenter.this.mCompositeSubscription != null) {
                            SafeSettingPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        SafeSettingPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<IdentityInfo> data) {
                        if (SafeSettingPresenter.this.getMvpView() != null)
                            SafeSettingPresenter.this.getMvpView().onIdentityInfoSuccess(data);
                    }
                }));
    }

    public void getRiskAssessmentScore() {
        this.mCompositeSubscription.add(this.mDataManager.getRiskAssessmentScore()
                .subscribe(new Subscriber<RiskAssessmentScore>() {
                    @Override
                    public void onCompleted() {
                        if (SafeSettingPresenter.this.mCompositeSubscription != null) {
                            SafeSettingPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        SafeSettingPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(RiskAssessmentScore data) {
                        if (SafeSettingPresenter.this.getMvpView() != null)
                            SafeSettingPresenter.this.getMvpView().onRiskAssessmentScoreSuccess(data);
                    }
                }));
    }

    public void logout() {
        this.mCompositeSubscription.add(this.mDataManager.revoke_access_token()
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (SafeSettingPresenter.this.mCompositeSubscription != null) {
                            SafeSettingPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        SafeSettingPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (SafeSettingPresenter.this.getMvpView() != null){
                            SafeSettingPresenter.this.getMvpView().onLogoutSuccess(data);
                        }
                    }
                }));
    }
}
