package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.AddBankCardView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class AddBankCardPresenter extends BasePresenter<AddBankCardView> {

    public AddBankCardPresenter() {
        long time = System.currentTimeMillis();
    }

    public void getBankCardList() {
        this.mCompositeSubscription.add(this.mDataManager.getBankCardList()
                .subscribe(new Subscriber<BaseBean<List<BankCard>>>() {
                    @Override
                    public void onCompleted() {
                        if (AddBankCardPresenter.this.mCompositeSubscription != null) {
                            AddBankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        AddBankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<BankCard>> data) {
                        if (AddBankCardPresenter.this.getMvpView() != null)
                            AddBankCardPresenter.this.getMvpView().onBankCardListSuccess(data.getData());
                    }
                }));
    }

    public void addBankCard(String bank_id, String card_no, String real_name) {
        this.mCompositeSubscription.add(this.mDataManager.addBankCard(bank_id, card_no, real_name)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (AddBankCardPresenter.this.mCompositeSubscription != null) {
                            AddBankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        AddBankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (AddBankCardPresenter.this.getMvpView() != null)
                            AddBankCardPresenter.this.getMvpView().onAddBankCardSuccess(data);
                    }
                }));
    }
}
