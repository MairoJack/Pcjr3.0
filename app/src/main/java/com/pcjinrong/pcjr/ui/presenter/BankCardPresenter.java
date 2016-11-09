package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.BankCardView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class BankCardPresenter extends BasePresenter<BankCardView> {

    public BankCardPresenter() {
        long time = System.currentTimeMillis();
    }

    public void getBankCardInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getBankCardInfo()
                .subscribe(new Subscriber<BaseBean<List<BankCard>>>() {
                    @Override
                    public void onCompleted() {
                        if (BankCardPresenter.this.mCompositeSubscription != null) {
                            BankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<BankCard>> data) {
                        if (BankCardPresenter.this.getMvpView() != null)
                            BankCardPresenter.this.getMvpView().onBankCardInfoSuccess(data.getData(),data.getRealName());
                    }
                }));
    }

    public void delBankCard(String bank_id) {
        this.mCompositeSubscription.add(this.mDataManager.delBankCard(bank_id)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (BankCardPresenter.this.mCompositeSubscription != null) {
                            BankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (BankCardPresenter.this.getMvpView() != null)
                            BankCardPresenter.this.getMvpView().onDelBankCardSuccess(data);
                    }
                }));
    }

    public void getIdentityInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getIdentityInfo()
                .subscribe(new Subscriber<BaseBean<IdentityInfo>>() {
                    @Override
                    public void onCompleted() {
                        if (BankCardPresenter.this.mCompositeSubscription != null) {
                            BankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        BankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<IdentityInfo> data) {
                        if (BankCardPresenter.this.getMvpView() != null)
                            BankCardPresenter.this.getMvpView().onIdentityInfoSuccess(data);
                    }
                }));
    }
}
