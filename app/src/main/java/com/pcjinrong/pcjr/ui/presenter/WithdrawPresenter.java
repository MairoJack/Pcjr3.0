package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.WithdrawView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class WithdrawPresenter extends BasePresenter<WithdrawView> {

    public WithdrawPresenter() {
        long time = System.currentTimeMillis();
    }


    public void withdrawVerify() {
        this.mCompositeSubscription.add(this.mDataManager.withdrawVerify()
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (WithdrawPresenter.this.mCompositeSubscription != null) {
                            WithdrawPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        WithdrawPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (WithdrawPresenter.this.getMvpView() != null)
                            WithdrawPresenter.this.getMvpView().onWithdrawVerifySuccess(data);
                    }
                }));
    }

    public void withdraw(String amount, String bank_id, String verify) {
        this.mCompositeSubscription.add(this.mDataManager.withdraw(amount, bank_id, verify)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (WithdrawPresenter.this.mCompositeSubscription != null) {
                            WithdrawPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        WithdrawPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (WithdrawPresenter.this.getMvpView() != null)
                            WithdrawPresenter.this.getMvpView().onWithdrawSuccess(data);
                    }
                }));
    }

    public void getBankCardList() {
        this.mCompositeSubscription.add(this.mDataManager.getBankCardInfo()
                .subscribe(new Subscriber<BaseBean<List<BankCard>>>() {
                    @Override
                    public void onCompleted() {
                        if (WithdrawPresenter.this.mCompositeSubscription != null) {
                            WithdrawPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        WithdrawPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<BankCard>> data) {
                        if (WithdrawPresenter.this.getMvpView() != null)
                            WithdrawPresenter.this.getMvpView().onBankCardListSuccess(data.getData());
                    }
                }));
    }

    public void rechargeInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getRechargeInfo()
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (WithdrawPresenter.this.mCompositeSubscription != null) {
                            WithdrawPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        WithdrawPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (WithdrawPresenter.this.getMvpView() != null)
                            WithdrawPresenter.this.getMvpView().onRechargeInfoSuccess(data);
                    }
                }));
    }
}
