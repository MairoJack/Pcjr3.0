package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.RechargeView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class RechargePresenter extends BasePresenter<RechargeView> {

    public RechargePresenter() {
        long time = System.currentTimeMillis();
    }


    public void rechargeVerify(String memberID, String cardID ,String amount) {
        this.mCompositeSubscription.add(this.mDataManager.sendVerifyCode(memberID,cardID,amount)
                .subscribe(new Subscriber<PayBean>() {
                    @Override
                    public void onCompleted() {
                        if (RechargePresenter.this.mCompositeSubscription != null) {
                            RechargePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RechargePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(PayBean data) {
                        if (RechargePresenter.this.getMvpView() != null)
                            RechargePresenter.this.getMvpView().onRechargeVerifySuccess(data);
                    }
                }));
    }

    public void recharge(String memberID, String orderNo,String validateCode) {
        this.mCompositeSubscription.add(this.mDataManager.confirmPay(memberID, orderNo,validateCode)
                .subscribe(new Subscriber<PayBean>() {
                    @Override
                    public void onCompleted() {
                        if (RechargePresenter.this.mCompositeSubscription != null) {
                            RechargePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RechargePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(PayBean data) {
                        if (RechargePresenter.this.getMvpView() != null)
                            RechargePresenter.this.getMvpView().onRechargeSuccess(data);
                    }
                }));
    }

    public void getBankCardList() {
        this.mCompositeSubscription.add(this.mDataManager.getBankCardInfo()
                .subscribe(new Subscriber<BaseBean<List<BankCard>>>() {
                    @Override
                    public void onCompleted() {
                        if (RechargePresenter.this.mCompositeSubscription != null) {
                            RechargePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RechargePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<BankCard>> data) {
                        if (RechargePresenter.this.getMvpView() != null)
                            RechargePresenter.this.getMvpView().onBankCardListSuccess(data.getData());
                    }
                }));
    }
}
