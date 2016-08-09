package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
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

    public void getBankCardList() {
        this.mCompositeSubscription.add(this.mDataManager.getBankCardList()
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
                            BankCardPresenter.this.getMvpView().onBankCardListSuccess(data.getData());
                    }
                }));
    }

    public void addBankCard(String bank_id, String card_no, String real_name) {
        this.mCompositeSubscription.add(this.mDataManager.addBankCard(bank_id, card_no, real_name)
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
                            BankCardPresenter.this.getMvpView().onAddBankCardSuccess(data);
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
}
